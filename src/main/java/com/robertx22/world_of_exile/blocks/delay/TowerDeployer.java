package com.robertx22.world_of_exile.blocks.delay;

import com.robertx22.world_of_exile.config.ModConfig;
import com.robertx22.world_of_exile.main.ModLoottables;
import com.robertx22.world_of_exile.mixin_ducks.MobSpawnerLogicDuck;
import net.minecraft.block.Blocks;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.MobSpawnerEntry;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomUtils;

import java.util.*;

public class TowerDeployer {

    private final static int RADIUS = 8;
    private final static int MAX_CHESTS = 2;
    private final static int MAX_SPAWNERS = 2;

    private final static List<BlockPos> CIRCULAR_POSITIONS = new ArrayList<>();

    static {
        // add all positions in a 7 block circle to our circular positions list for chest placement

        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int z = -RADIUS; z <= RADIUS; z++) {
                double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));

                // check that block is within radius
                if (distance <= RADIUS) {

                    // don't allow chests at 0,0 (results in chest floating above deployer)
                    if (x != 0 || z != 0) {
                        CIRCULAR_POSITIONS.add(new BlockPos(x, 0, z));
                    }
                }
            }
        }
    }

    static List<Direction> DIRECTIONS = Arrays.asList(Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST);
    static int LADDERS_NEEDED = 6;

    World world;
    BlockPos deployBlockPos;

    int currentHeight;
    int towerBottom;

    Direction lastLadderDirection = Direction.UP;

    HashMap<Direction, BlockPos> sides = new HashMap<>();

    public TowerDeployer(World world, BlockPos deployBlockPos) {
        this.world = world;
        this.deployBlockPos = deployBlockPos;

        currentHeight = deployBlockPos.getY();

        findSides();
        findBottom();
    }

    public void onTick() {

        try {
            placeLaddersForFloor();

            placeSpawnersAndChests(currentHeight == deployBlockPos.getY() ? FloorType.BOSS : FloorType.NORMAL);

            currentHeight -= LADDERS_NEEDED;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isDone() {
        return currentHeight <= towerBottom || currentHeight < 50;
    }

    enum FloorType {
        BOSS, NORMAL;
    }

    private void placeSpawnersAndChests(FloorType floorType) {

        ArrayList<BlockPos> clonedPositions = new ArrayList<>(CIRCULAR_POSITIONS);
        Collections.shuffle(clonedPositions);

        int TOTAL_CHESTS = 0 + world.random.nextInt(MAX_CHESTS);
        int TOTAL_SPAWNERS = 1 + world.random.nextInt(MAX_SPAWNERS);

        if (floorType == FloorType.BOSS) {
            if (TOTAL_CHESTS == 0) {
                TOTAL_CHESTS++;
            }
        }

        int chests = 0;
        int spawners = 0;

        for (BlockPos x : clonedPositions) {

            if (chests > TOTAL_CHESTS && spawners > TOTAL_SPAWNERS) {
                break;
            }

            BlockPos pos = new BlockPos(x.getX() + deployBlockPos.getX(), currentHeight, x.getZ() + deployBlockPos.getZ());

            if (world.getBlockState(pos.down())
                .isSolidBlock(world, pos.down()) && world.getBlockState(pos)
                .isAir()) {

                if (spawners < TOTAL_SPAWNERS) {
                    MobSpawnerBlockEntity spawner = new MobSpawnerBlockEntity();
                    MobSpawnerLogicDuck saccess = (MobSpawnerLogicDuck) spawner.getLogic();
                    saccess.getspawnPotentials()
                        .clear();

                    EntityType type = null;

                    List<EntityType> mobs = new ArrayList<>(ModConfig.get()
                        .getAllowedSpawnerMobs());
                    type = mobs.get(RandomUtils.nextInt(0, mobs.size()));

                    MobSpawnerEntry entry = new MobSpawnerEntry();
                    entry.getEntityTag()
                        .putString("id", Registry.ENTITY_TYPE.getId(type)
                            .toString());

                    saccess.getspawnPotentials()
                        .add(entry);
                    spawner.getLogic()
                        .setSpawnEntry(entry);

                    world.setBlockState(pos, Blocks.SPAWNER.getDefaultState());
                    world.setBlockEntity(pos, spawner);

                    spawners++;
                } else if (chests < TOTAL_CHESTS) {

                    ChestBlockEntity chest = new ChestBlockEntity();

                    if (floorType == FloorType.NORMAL) {
                        chest.setLootTable(ModLoottables.DUNGEON_DEFAULT, RandomUtils.nextLong());
                    } else {
                        chest.setLootTable(ModLoottables.BIG_TREASURE, RandomUtils.nextLong());
                        chests++;
                    }

                    world.setBlockState(pos, Blocks.CHEST.getDefaultState());
                    world.setBlockEntity(pos, chest);

                    chests++;
                }

            }

        }

    }

    private void placeLaddersForFloor() {

        List<Direction> possible = new ArrayList<>(DIRECTIONS);
        possible.removeIf(x -> x == lastLadderDirection);
        // no same ladder position always

        Direction dir = possible.get(world.random.nextInt(possible.size()));

        this.lastLadderDirection = dir;

        BlockPos pos = new BlockPos(sides.get(dir)
            .getX(), currentHeight - 1, sides.get(dir)
            .getZ());

        for (int i = 0; i < LADDERS_NEEDED; i++) {
            world.setBlockState(pos, Blocks.LADDER.getDefaultState()
                .with(LadderBlock.FACING, dir.getOpposite()));
            pos = pos.down();
        }

    }

    private void findBottom() {

        BlockPos pos = deployBlockPos;

        for (int i = 0; i < 200; i++) {

            if (world.getBlockState(pos)
                .getBlock() == Blocks.BEACON) {
                this.towerBottom = pos.getY();
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                break;
            }

            pos = pos.down();

        }
    }

    private void findSides() {

        for (Direction dir : DIRECTIONS) {

            BlockPos test = deployBlockPos.offset(dir, 0);

            for (int i = 0; i < 15; i++) {
                test = deployBlockPos.offset(dir, i)
                    .down();

                if (world.getBlockState(test)
                    .isSolidBlock(world, test) && world.getBlockState(test.offset(dir, 3))
                    .isAir()) {
                    sides.put(dir, test);
                    break;

                }

            }
        }

    }

}
