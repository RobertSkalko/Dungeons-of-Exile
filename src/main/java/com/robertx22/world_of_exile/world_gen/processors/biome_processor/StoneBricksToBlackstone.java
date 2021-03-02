package com.robertx22.world_of_exile.world_gen.processors.biome_processor;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Util;
import net.minecraft.world.biome.Biome;

import java.util.Map;

public class StoneBricksToBlackstone extends BiomeProcessorType {

    public final Map<Block, Block> MAP = Util.make(Maps.newHashMap(), (map) -> {
        map.put(Blocks.STONE_BRICKS, Blocks.POLISHED_BLACKSTONE_BRICKS);
        map.put(Blocks.STONE_BRICK_STAIRS, Blocks.POLISHED_BLACKSTONE_STAIRS);
        map.put(Blocks.STONE_BRICK_SLAB, Blocks.POLISHED_BLACKSTONE_BRICK_SLAB);
        map.put(Blocks.CRACKED_STONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS);
        //map.put(Blocks.SMOOTH_STONE, Blocks.POLISHED_BLACKSTONE); tower uses processor to % remove floor blocks
    });

    private StoneBricksToBlackstone() {
    }

    @Override
    public boolean isDefault() {
        return false;
    }

    public static StoneBricksToBlackstone getInstance() {
        return StoneBricksToBlackstone.SingletonHolder.INSTANCE;
    }

    @Override
    public String id() {
        return "bricks_to_hell";
    }

    @Override
    public boolean isBiomeGood(Biome biome) {
        return true;
    }

    @Override
    public Map<Block, Block> getReplaceMap() {
        return MAP;
    }

    private static class SingletonHolder {
        private static final StoneBricksToBlackstone INSTANCE = new StoneBricksToBlackstone();
    }
}