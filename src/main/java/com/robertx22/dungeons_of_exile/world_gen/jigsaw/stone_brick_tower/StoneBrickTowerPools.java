package com.robertx22.dungeons_of_exile.world_gen.jigsaw.stone_brick_tower;

import com.robertx22.dungeons_of_exile.main.ModProcessors;
import com.robertx22.dungeons_of_exile.main.WOE;
import com.robertx22.dungeons_of_exile.world_gen.AbstractPool;
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
            return ModProcessors.INSTANCE.TOWER_PROCESORS;
        }

    }

    static {

        Pool startBuilder = new Pool(new Identifier(WOE.NEWMODID, "tower_start"));
        startBuilder.add(new Identifier(WOE.MODID, "tower/start/start0"));
        STARTPOOL = startBuilder.build();

        Pool middleBuilder = new Pool(new Identifier(WOE.NEWMODID, "tower_middle"));
        middleBuilder.add(new Identifier(WOE.MODID, "tower/middle/middle0"));
        middleBuilder.build();

        Pool topBuilder = new Pool(new Identifier(WOE.NEWMODID, "tower_top"));
        topBuilder.add(new Identifier(WOE.MODID, "tower/top/topbig"));
        topBuilder.add(new Identifier(WOE.MODID, "tower/top/topsmall"));
        topBuilder.build();

    }

}
