package com.robertx22.world_of_exile.world_gen.processors.biome_processor;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Util;
import net.minecraft.world.biome.Biome;

import java.util.Map;

public class BrickProc extends BiomeProcessorType {

    public final Map<Block, Block> MAP = Util.make(Maps.newHashMap(), (map) -> {
        map.put(Blocks.COBBLESTONE, Blocks.STONE_BRICKS);
        map.put(Blocks.COBBLESTONE_STAIRS, Blocks.STONE_BRICK_STAIRS);
        map.put(Blocks.COBBLESTONE_SLAB, Blocks.STONE_BRICK_SLAB);
    });

    private BrickProc() {
    }

    @Override
    public boolean isDefault() {
        return true;
    }

    public static BrickProc getInstance() {
        return BrickProc.SingletonHolder.INSTANCE;
    }

    @Override
    public String id() {
        return "brick";
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
        private static final BrickProc INSTANCE = new BrickProc();
    }
}
