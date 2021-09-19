package com.robertx22.world_of_exile.world_gen.tower;

import com.robertx22.world_of_exile.main.WOE;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class TowerDestroyer {

    public static ConcurrentLinkedDeque<TowerDestroyer> list = new ConcurrentLinkedDeque<>();

    public static void tickAll(World world) {

        list.forEach(x -> x.onTick(world));
        list.removeIf(x -> x.isDone(world));
    }

    BlockPos pos;
    World world;
    boolean started = false;

    BlockPos chestPos;

    int tick = 0;

    public TowerDestroyer(BlockPos pos, World world, ChestTileEntity chest) {
        this.pos = pos;
        this.world = world;

        this.chestPos = chest.getBlockPos();

    }

    boolean stop = false;

    public boolean isDone(World world) {
        if (stop) {
            return true;
        }

        if (pos.getY() < 50) {
            // at end dont leave chest at top standing lol
            return true;
        }
        return timesDidntDestroy > 2;
    }

    static List<ITag.INamedTag<Block>> TAGS = Arrays.asList(BlockTags.SIGNS, BlockTags.STONE_BRICKS, BlockTags.STAIRS, BlockTags.SLABS, BlockTags.WALLS, BlockTags.FENCES);
    static List<Block> BLOCKS = Arrays.asList(Blocks.COBWEB, Blocks.LAVA, Blocks.WATER, Blocks.SMOOTH_STONE, Blocks.SPAWNER, Blocks.CHEST, Blocks.LADDER, Blocks.OBSIDIAN);

    static boolean shouldDestroyBlock(Block block) {

        if (block == Blocks.AIR) {
            return false;
        }

        if (TAGS.stream()
            .anyMatch(x -> x.contains(block))) {
            return true;
        }
        if (BLOCKS.stream()
            .anyMatch(x -> x.equals(block))) {
            return true;
        }

        if (Registry.BLOCK.getKey(block)
            .getNamespace()
            .equals(WOE.MODID)) {
            return true;
        }
        return false;
    }

    static boolean shouldKeepDrops(World world, Block block) {

        return false;
        /*
        if (block != Blocks.CHEST && world.random.nextBoolean()) {
            return true; // drop only half the stones
        }
        return false;

         */
    }

    int timesDidntDestroy = 0;

    public void onTick(World world) {

        try {
            if (!this.world.equals(world)) {
                return;
            }

            tick++;

            if (!started && tick % 20 == 0) {
                world.playSound(null, pos, SoundEvents.TNT_PRIMED, SoundCategory.BLOCKS, 1F, 1F);
            }
            if (!started && tick > 160) {
                started = true;
                return;
            }

            if (started && tick % 20 == 0) {

                boolean destroyed = false;

                for (int x = -9; x < 9; x++) {
                    for (int z = -9; z < 9; z++) {
                        BlockPos p = new BlockPos(pos.getX() + x, pos.getY(), pos.getZ() + z);

                        Block block = world.getBlockState(p)
                            .getBlock();

                        if (block == Blocks.CHEST) {
                            try {
                                // set table to null so players arent rewarded for going straight to top
                                ChestTileEntity chest = (ChestTileEntity) world.getBlockEntity(p);
                                chest.setLootTable(null, 0);
                                world.setBlockAndUpdate(p, Blocks.AIR.defaultBlockState());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (shouldDestroyBlock(block)) {
                                destroyed = true;
                                world.destroyBlock(p, shouldKeepDrops(world, block));
                                world.setBlockAndUpdate(p, Blocks.AIR.defaultBlockState());
                            }
                        }
                    }

                }

                if (!destroyed) {
                    timesDidntDestroy++;
                }

                pos = pos.below(1);

                if (isDone(world)) {
                    world.destroyBlock(this.chestPos, true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
