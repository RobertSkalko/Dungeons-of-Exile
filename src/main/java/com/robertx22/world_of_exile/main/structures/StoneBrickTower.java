package com.robertx22.world_of_exile.main.structures;

import com.robertx22.world_of_exile.config.ModConfig;
import com.robertx22.world_of_exile.main.ModProcessorLists;
import com.robertx22.world_of_exile.main.ModWorldGenIds;
import com.robertx22.world_of_exile.main.WOE;
import com.robertx22.world_of_exile.main.structures.base.StructureWrapper;
import com.robertx22.world_of_exile.world_gen.AbstractPool;
import com.robertx22.world_of_exile.world_gen.jigsaw.stone_brick_tower.StoneBrickTowerStructure;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class StoneBrickTower extends StructureWrapper {

    public StoneBrickTower() {
        super(ModWorldGenIds.STONE_BRICK_TOWER_ID, true, ModConfig.get().STONE_BRICK_TOWER, GenerationStep.Feature.SURFACE_STRUCTURES);
    }

    @Override
    public ConfiguredStructureFeature createConfiguredFeature() {
        return feature.configure(new StructurePoolFeatureConfig(() -> {
            return this.startPool;
        }, 6));
    }

    @Override
    public StructureFeature createFeature() {
        return new StoneBrickTowerStructure(StructurePoolFeatureConfig.CODEC);
    }

    @Override
    public StructurePool createPoolAndInitPools() {

        Pool startBuilder = new Pool(WOE.id("tower_start"));
        startBuilder.add(WOE.id("stone_brick_tower/start/start0"));

        Pool middleBuilder = new Pool(WOE.id("tower_middle"));
        middleBuilder.add(WOE.id("stone_brick_tower/middle/middle0"));
        middleBuilder.build();

        Pool topBuilder = new Pool(WOE.id("tower_top"));
        topBuilder.add(WOE.id("stone_brick_tower/top/topbig"));
        topBuilder.add(WOE.id("stone_brick_tower/top/topsmall"));
        topBuilder.build();

        return startBuilder.build();
    }

    static class Pool extends AbstractPool {

        public Pool(Identifier poolId) {
            super(poolId);
        }

        @Override
        public StructureProcessorList processorList() {
            return ModProcessorLists.INSTANCE.STONE_BRICK_TOWER;
        }

    }

}