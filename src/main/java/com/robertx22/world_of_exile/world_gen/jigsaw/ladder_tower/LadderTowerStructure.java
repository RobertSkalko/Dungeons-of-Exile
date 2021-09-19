package com.robertx22.world_of_exile.world_gen.jigsaw.ladder_tower;

import com.mojang.serialization.Codec;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.JigsawStructure;
import net.minecraft.world.gen.feature.structure.VillageConfig;

public class LadderTowerStructure extends JigsawStructure {

    public LadderTowerStructure(Codec<VillageConfig> codec) {
        super(codec, 0, true, true);
    }

    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGen, BiomeProvider biomeSource, long l, SharedSeedRandom chunkRandom, int i, int j, Biome biome, ChunkPos cpos, VillageConfig structurePoolFeatureConfig) {
        return true;
    }
}



