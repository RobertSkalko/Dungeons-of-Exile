package com.robertx22.world_of_exile.biomes;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.CaveCarver;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Random;
import java.util.function.Function;

// made because vanilla one only carves stone or something
public class MyCaveCarver extends CaveCarver {

    public MyCaveCarver(Codec<ProbabilityConfig> codec, int i) {
        super(codec, i);
        this.alwaysCarvableBlocks = new HashSet<>(this.alwaysCarvableBlocks); // mojang always uses IMMUTABLE
        this.alwaysCarvableBlocks.add(Blocks.BLACKSTONE);
    }

    @Override
    protected boolean carveAtPoint(Chunk chunk, Function<BlockPos, Biome> posToBiome, BitSet carvingMask, Random random, BlockPos.Mutable mutable, BlockPos.Mutable mutable2, BlockPos.Mutable mutable3, int seaLevel, int mainChunkX, int mainChunkZ, int x, int z, int relativeX, int y, int relativeZ, MutableBoolean mutableBoolean) {
        if (y < 15) {
            return false; // dont carve where it would put lava
        }
        return super.carveAtPoint(chunk, posToBiome, carvingMask, random, mutable, mutable2, mutable3, seaLevel, mainChunkX, mainChunkZ, x, z, relativeX, y, relativeZ, mutableBoolean);
    }
}
