package com.robertx22.world_of_exile.world_gen.jigsaw.dungeon;

import com.mojang.serialization.Codec;
import com.robertx22.world_of_exile.config.ModConfig;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.JigsawFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class ModDungeonFeature extends JigsawFeature {
    public ModDungeonFeature(Codec<StructurePoolFeatureConfig> codec) {
        super(codec, 0, true, true);
    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGen, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, int i, int j, Biome biome, ChunkPos cpos, StructurePoolFeatureConfig structurePoolFeatureConfig) {
        return ModConfig.get().ENABLE_DUNGEON &&
            chunkGen.getHeightOnGround(cpos.getStartX(), cpos.getStartZ(), Heightmap.Type.MOTION_BLOCKING) < 68 &&
            chunkGen.getHeightOnGround(cpos.getEndX(), cpos.getEndZ(), Heightmap.Type.MOTION_BLOCKING) < 68 &&
            biome.getDepth() < 0.5F
            && biome.getCategory() != Biome.Category.OCEAN
            && biome.getCategory() != Biome.Category.BEACH
            && biome.getCategory() != Biome.Category.RIVER;

    }
}



