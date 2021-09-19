package com.robertx22.world_of_exile.main.structures;

import com.robertx22.world_of_exile.MyBiomeSelectors;
import com.robertx22.world_of_exile.config.ModConfig;
import com.robertx22.world_of_exile.main.ModProcessorLists;
import com.robertx22.world_of_exile.main.ModWorldGenIds;
import com.robertx22.world_of_exile.main.WOE;
import com.robertx22.world_of_exile.main.structures.base.StructureWrapper;
import com.robertx22.world_of_exile.world_gen.AbstractPool;
import com.robertx22.world_of_exile.world_gen.jigsaw.stone_brick_tower.StoneBrickTowerStructure;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.StructureProcessorList;

public class StoneBrickTower extends StructureWrapper {

    public StoneBrickTower() {
        super(MyBiomeSelectors.OVERWORLD_LAND, ModWorldGenIds.STONE_BRICK_TOWER_ID, true, ModConfig.get().STONE_BRICK_TOWER, GenerationStage.Decoration.SURFACE_STRUCTURES);
    }

    @Override
    public StructureFeature createConfiguredFeature() {
        return feature.configured(new VillageConfig(() -> {
            return this.startPool;
        }, 6));
    }

    @Override
    public Structure createFeature() {
        return new StoneBrickTowerStructure(VillageConfig.CODEC);
    }

    @Override
    public JigsawPattern createPoolAndInitPools() {

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

        public Pool(ResourceLocation poolId) {
            super(poolId);
        }

        @Override
        public StructureProcessorList processorList() {
            return ModProcessorLists.INSTANCE.STONE_BRICK_TOWER;
        }

    }

}