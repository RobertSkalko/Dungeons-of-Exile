package com.robertx22.world_of_exile.world_gen.jigsaw.blackstone_tower;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.JigsawFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class BlackstoneTowerStructure extends JigsawFeature {
    public BlackstoneTowerStructure(Codec<StructurePoolFeatureConfig> codec) {
        super(codec, 0, true, true);
    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGen, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, int i, int j, Biome biome, ChunkPos cpos, StructurePoolFeatureConfig structurePoolFeatureConfig) {
        int start = chunkGen.getHeightOnGround(cpos.getStartX(), cpos.getStartZ(), Heightmap.Type.WORLD_SURFACE_WG);
        int end = chunkGen.getHeightOnGround(cpos.getEndX(), cpos.getEndZ(), Heightmap.Type.WORLD_SURFACE_WG);

        return start > 25 && end > 25 && start < 150 && end < 150;

    }
}



