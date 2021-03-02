package com.robertx22.world_of_exile.world_gen.processors.biome_processor;

import com.mojang.serialization.Codec;
import com.robertx22.world_of_exile.main.ModWorldGen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.Map;

public class BiomeProcessor extends StructureProcessor {

    public static HashMap<String, BiomeProcessorType> MAP;

    static {
        MAP = new HashMap<>();
        reg(BrickProc.getInstance());
        reg(MossyProc.getInstance());
        reg(PrismaProc.getInstance());
        reg(SandstoneProc.getInstance());
    }

    public HashMap<String, BiomeProcessorType> getMap() {
        return MAP;
    }

    static void reg(BiomeProcessorType proc) {
        MAP.put(proc.id(), proc);
    }

    public static final Codec<BiomeProcessor> CODEC = Codec.unit(BiomeProcessor::new);

    @Override
    public Structure.StructureBlockInfo process(WorldView worldView, BlockPos pos, BlockPos blockPos, Structure.StructureBlockInfo structureBlockInfo, Structure.StructureBlockInfo blockinfo2, StructurePlacementData structurePlacementData) {

        BiomeProcessorType type = null;

        Biome biome = worldView.getBiome(pos);

        for (BiomeProcessorType t : this.getMap()
            .values()) {
            if (t.isBiomeGood(biome) && !t.isDefault()) {
                type = t;
                break;
            }
        }
        if (type == null) {
            type = BrickProc.getInstance();
        }

        Map<Block, Block> map = type.getReplaceMap();

        Block block = (Block) map.get(blockinfo2.state.getBlock());
        if (block == null) {
            return blockinfo2;
        } else {
            BlockState blockState = blockinfo2.state;
            BlockState blockState2 = block.getDefaultState();
            if (blockState.contains(StairsBlock.FACING)) {
                blockState2 = (BlockState) blockState2.with(StairsBlock.FACING, blockState.get(StairsBlock.FACING));
            }

            if (blockState.contains(StairsBlock.HALF)) {
                blockState2 = (BlockState) blockState2.with(StairsBlock.HALF, blockState.get(StairsBlock.HALF));
            }

            if (blockState.contains(SlabBlock.TYPE)) {
                blockState2 = (BlockState) blockState2.with(SlabBlock.TYPE, blockState.get(SlabBlock.TYPE));
            }

            return new Structure.StructureBlockInfo(blockinfo2.pos, blockState2, blockinfo2.tag);
        }
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModWorldGen.INSTANCE.BIOME_PROCESSOR;
    }
}
