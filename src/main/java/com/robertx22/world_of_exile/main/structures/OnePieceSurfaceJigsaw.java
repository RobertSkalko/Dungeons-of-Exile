package com.robertx22.world_of_exile.main.structures;

import com.robertx22.world_of_exile.config.ModConfig;
import com.robertx22.world_of_exile.main.ModProcessorLists;
import com.robertx22.world_of_exile.main.ModWorldGenIds;
import com.robertx22.world_of_exile.main.WOE;
import com.robertx22.world_of_exile.main.structures.OnePieceSurfaceJigsaw.Pool;
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

public class OnePieceSurfaceJigsaw extends StructureWrapper {

    public OnePieceSurfaceJigsaw() {
        super(ModWorldGenIds.ONE_PIECE_SURFACE_JIGSAW, true, ModConfig.get().ONE_PIECE_SURFACE, GenerationStep.Feature.SURFACE_STRUCTURES);
    }

    @Override
    public ConfiguredStructureFeature createConfiguredFeature() {
        return feature.configure(new StructurePoolFeatureConfig(() -> {
            return this.startPool;
        }, 1));
    }

    @Override
    public StructureFeature createFeature() {
        return new StoneBrickTowerStructure(StructurePoolFeatureConfig.CODEC);
    }

    @Override
    public StructurePool createPoolAndInitPools() {

        Pool startBuilder = new Pool(WOE.id("one_piece_surface"));
        startBuilder.add(WOE.id("one_piece_surface_jigsaws/shortfatfortress"));

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