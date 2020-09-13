package com.robertx22.dungeons_of_exile.world_gen.jigsaw.dungeon;

import com.mojang.serialization.Codec;
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
        return chunkGen.getHeightOnGround(cpos.getStartX(), cpos.getStartZ(), Heightmap.Type.MOTION_BLOCKING) < 68 &&
            biome.getDepth() < 0.5F &&
            biome.getCategory() != Biome.Category.OCEAN; // no mountains

    }
}



