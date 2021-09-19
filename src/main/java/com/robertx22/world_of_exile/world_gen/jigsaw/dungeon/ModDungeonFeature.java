package com.robertx22.world_of_exile.world_gen.jigsaw.dungeon;

import com.mojang.serialization.Codec;
import com.robertx22.world_of_exile.config.ModConfig;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.JigsawStructure;
import net.minecraft.world.gen.feature.structure.VillageConfig;

public class ModDungeonFeature extends JigsawStructure {
    public ModDungeonFeature(Codec<VillageConfig> codec) {
        super(codec, 0, true, true);
    }

    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGen, BiomeProvider biomeSource, long l, SharedSeedRandom chunkRandom, int i, int j, Biome biome, ChunkPos cpos, VillageConfig structurePoolFeatureConfig) {
        return ModConfig.get().ENABLE_DUNGEON &&
            chunkGen.getFirstFreeHeight(cpos.getMinBlockX(), cpos.getMinBlockZ(), Heightmap.Type.MOTION_BLOCKING) < 68 &&
            chunkGen.getFirstFreeHeight(cpos.getMaxBlockX(), cpos.getMaxBlockZ(), Heightmap.Type.MOTION_BLOCKING) < 68 &&
            biome.getDepth() < 0.5F
            && biome.getBiomeCategory() != Biome.Category.OCEAN
            && biome.getBiomeCategory() != Biome.Category.BEACH
            && biome.getBiomeCategory() != Biome.Category.RIVER;

    }
}



