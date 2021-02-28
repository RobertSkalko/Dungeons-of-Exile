package com.robertx22.world_of_exile.main.structures;

import com.robertx22.world_of_exile.config.ModConfig;
import com.robertx22.world_of_exile.main.ModProcessors;
import com.robertx22.world_of_exile.main.ModWorldGenIds;
import com.robertx22.world_of_exile.main.WOE;
import com.robertx22.world_of_exile.main.structures.base.StructureWrapper;
import com.robertx22.world_of_exile.world_gen.AbstractPool;
import com.robertx22.world_of_exile.world_gen.jigsaw.blackstone_tower.BlackstoneTowerStructure;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class BlackStoneTower extends StructureWrapper {

    public BlackStoneTower() {
        super(ModWorldGenIds.BLACKSTONE_TOWER_ID, true, ModConfig.get().BLACKSTONE_TOWER, GenerationStep.Feature.SURFACE_STRUCTURES);
    }

    @Override
    public ConfiguredStructureFeature createConfiguredFeature() {
        return feature.configure(new StructurePoolFeatureConfig(() -> {
            return this.startPool;
        }, 15));
    }

    @Override
    public StructureFeature createFeature() {
        return new BlackstoneTowerStructure(StructurePoolFeatureConfig.CODEC);
    }

    @Override
    public StructurePool createPoolAndInitPools() {

        AbstractPool startBuilder = new Pool(WOE.id("bigtower_start"));
        startBuilder.add(WOE.id("blackstone_tower/start/start0"));

        AbstractPool middleBuilder = new Pool(WOE.id("bigtower_middle"));
        middleBuilder.add(WOE.id("blackstone_tower/middle/middle0"));
        middleBuilder.add(WOE.id("blackstone_tower/middle/top"), 1);
        middleBuilder.build();

        AbstractPool sideBuilder = new Pool(WOE.id("bigtower_side"));
        sideBuilder.add(WOE.id("blackstone_tower/side/emptyside"));
        sideBuilder.add(WOE.id("blackstone_tower/side/bigside0"), 500);
        sideBuilder.add(WOE.id("blackstone_tower/side/side1"), 150);
        sideBuilder.build();

        return startBuilder.build();
    }

    static class Pool extends AbstractPool {

        public Pool(Identifier poolId) {
            super(poolId);
        }

        @Override
        public StructureProcessorList processorList() {
            return ModProcessors.INSTANCE.BLACKSTONE_TOWER;
        }

    }

}

