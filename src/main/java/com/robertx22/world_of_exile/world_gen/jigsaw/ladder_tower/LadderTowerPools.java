package com.robertx22.world_of_exile.world_gen.jigsaw.ladder_tower;

import com.robertx22.world_of_exile.main.ModProcessors;
import com.robertx22.world_of_exile.main.WOE;
import com.robertx22.world_of_exile.world_gen.AbstractPool;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;

public class LadderTowerPools {

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

        Pool startBuilder = new Pool(WOE.id("ladder_tower_start"));
        startBuilder.add(WOE.id("ladder_tower/start/start0"));
        STARTPOOL = startBuilder.build();

        Pool middleBuilder = new Pool(WOE.id("ladder_tower_middle"));
        middleBuilder.add(WOE.id("ladder_tower/middle/middle0"), 1000);
        middleBuilder.add(WOE.id("ladder_tower/middle/dark_room0"), 1000);
        middleBuilder.add(WOE.id("ladder_tower/middle/lava_pool"), 1000);
        middleBuilder.add(WOE.id("ladder_tower/middle/web_halls0"), 1000);
        middleBuilder.add(WOE.id("ladder_tower/middle/topsmall"), 1);
        middleBuilder.build();

    }

}
