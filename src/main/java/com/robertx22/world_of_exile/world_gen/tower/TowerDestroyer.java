package com.robertx22.world_of_exile.world_gen.tower;

import com.robertx22.world_of_exile.main.WOE;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
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

    public TowerDestroyer(BlockPos pos, World world, ChestBlockEntity chest) {
        this.pos = pos;
        this.world = world;

        this.chestPos = chest.getPos();

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

    static List<Tag.Identified<Block>> TAGS = Arrays.asList(BlockTags.SIGNS, BlockTags.STONE_BRICKS, BlockTags.STAIRS, BlockTags.SLABS, BlockTags.WALLS, BlockTags.FENCES);
    static List<Block> BLOCKS = Arrays.asList(Blocks.SMOOTH_STONE, Blocks.SPAWNER, Blocks.CHEST, Blocks.LADDER, Blocks.OBSIDIAN);

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

        if (Registry.BLOCK.getId(block)
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

        if (!this.world.equals(world)) {
            return;
        }

        tick++;

        if (!started && tick % 20 == 0) {
            world.playSound(null, pos, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1F, 1F);
        }
        if (!started && tick > 100) {
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
                            ChestBlockEntity chest = (ChestBlockEntity) world.getBlockEntity(p);
                            chest.setLootTable(null, 0);
                            world.setBlockState(p, Blocks.AIR.getDefaultState());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (shouldDestroyBlock(block)) {
                            destroyed = true;
                            world.breakBlock(p, shouldKeepDrops(world, block));
                        }
                    }
                }

            }

            if (!destroyed) {
                timesDidntDestroy++;
            }

            pos = pos.down(1);

            if (isDone(world)) {
                world.breakBlock(this.chestPos, true);
            }
        }
    }
}
