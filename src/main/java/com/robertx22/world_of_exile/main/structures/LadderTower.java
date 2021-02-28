package com.robertx22.world_of_exile.main.structures;

import com.robertx22.world_of_exile.config.ModConfig;
import com.robertx22.world_of_exile.main.ModProcessors;
import com.robertx22.world_of_exile.main.ModWorldGenIds;
import com.robertx22.world_of_exile.main.WOE;
import com.robertx22.world_of_exile.main.structures.base.StructureWrapper;
import com.robertx22.world_of_exile.world_gen.AbstractPool;
import com.robertx22.world_of_exile.world_gen.jigsaw.ladder_tower.LadderTowerStructure;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class LadderTower extends StructureWrapper {

    public LadderTower() {
        super(ModWorldGenIds.LADDER_TOWER_ID, true, ModConfig.get().LADDER_TOWER, GenerationStep.Feature.SURFACE_STRUCTURES);
    }

    @Override
    public ConfiguredStructureFeature createConfiguredFeature() {
        return feature.configure(new StructurePoolFeatureConfig(() -> {
            return this.startPool;
        }, 15));
    }

    @Override
    public StructureFeature createFeature() {
        return new LadderTowerStructure(StructurePoolFeatureConfig.CODEC);
    }

    @Override
    public StructurePool createPoolAndInitPools() {
        Pool startBuilder = new Pool(WOE.id("ladder_tower_start"));
        startBuilder.add(WOE.id("ladder_tower/start/start0"));

        Pool middleBuilder = new Pool(WOE.id("ladder_tower_middle"));
        middleBuilder.add(WOE.id("ladder_tower/middle/middle0"), 1000);
        middleBuilder.add(WOE.id("ladder_tower/middle/dark_room0"), 1000);
        middleBuilder.add(WOE.id("ladder_tower/middle/lava_pool"), 1000);
        middleBuilder.add(WOE.id("ladder_tower/middle/web_halls0"), 1000);
        middleBuilder.add(WOE.id("ladder_tower/middle/topsmall"), 1);
        middleBuilder.build();

        return startBuilder.build();
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

}
