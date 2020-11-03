package com.robertx22.world_of_exile.world_gen.jigsaw.stone_brick_tower;

import com.robertx22.world_of_exile.main.ModProcessors;
import com.robertx22.world_of_exile.main.WOE;
import com.robertx22.world_of_exile.world_gen.AbstractPool;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;

public class StoneBrickTowerPools {

    public static StructurePool STARTPOOL;

    public static void init() {

    }

    static class Pool extends AbstractPool {

        public Pool(Identifier poolId) {
            super(poolId);
        }

        @Override
        public StructureProcessorList processorList() {
            return ModProcessors.INSTANCE.STONE_BRICK_TOWER;
        }

    }

    static {

        Pool startBuilder = new Pool(WOE.id("tower_start"));
        startBuilder.add(WOE.id("stone_brick_tower/start/start0"));
        STARTPOOL = startBuilder.build();

        Pool middleBuilder = new Pool(WOE.id("tower_middle"));
        middleBuilder.add(WOE.id("stone_brick_tower/middle/middle0"));
        middleBuilder.build();

        Pool topBuilder = new Pool(WOE.id("tower_top"));
        topBuilder.add(WOE.id("stone_brick_tower/top/topbig"));
        topBuilder.add(WOE.id("stone_brick_tower/top/topsmall"));
        topBuilder.build();

    }

}
