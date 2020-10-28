package com.robertx22.dungeons_of_exile.world_gen.tower;

import com.mojang.serialization.Codec;
import com.robertx22.dungeons_of_exile.main.DungeonConfig;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class TowerFeature extends StructureFeature<DefaultFeatureConfig> {
    public TowerFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);

    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGen, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, int i, int j, Biome biome, ChunkPos cpos, DefaultFeatureConfig structurePoolFeatureConfig) {
        return DungeonConfig.get().ENABLE_MOD && DungeonConfig.get().ENABLE_TOWER;
    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return Start::new;
    }

    public static class Start extends StructureStart<DefaultFeatureConfig> {
        public Start(StructureFeature<DefaultFeatureConfig> structureFeature, int i, int j, BlockBox blockBox, int k, long l) {
            super(structureFeature, i, j, blockBox, k, l);
        }

        @Override
        public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, int i, int j, Biome biome, DefaultFeatureConfig defaultFeatureConfig) {
            int k = i * 16;
            int l = j * 16;
            BlockPos blockPos = new BlockPos(k, 0, l);
            BlockRotation blockRotation = BlockRotation.random(this.random);
            TowerPieces.addPieces(structureManager, blockPos, blockRotation, this.children, this.random);
            this.setBoundingBoxFromChildren();
        }
    }
}

