package com.robertx22.world_of_exile.world_gen.processors.biome_processor;

import com.mojang.serialization.Codec;
import com.robertx22.world_of_exile.main.WOEProcessors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

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
    public Template.BlockInfo processBlock(IWorldReader worldView, BlockPos pos, BlockPos blockPos, Template.BlockInfo structureBlockInfo, Template.BlockInfo blockinfo2, PlacementSettings structurePlacementData) {

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
            BlockState blockState2 = block.defaultBlockState();
            if (blockState.hasProperty(StairsBlock.FACING)) {
                blockState2 = (BlockState) blockState2.setValue(StairsBlock.FACING, blockState.getValue(StairsBlock.FACING));
            }

            if (blockState.hasProperty(StairsBlock.HALF)) {
                blockState2 = (BlockState) blockState2.setValue(StairsBlock.HALF, blockState.getValue(StairsBlock.HALF));
            }

            if (blockState.hasProperty(SlabBlock.TYPE)) {
                blockState2 = (BlockState) blockState2.setValue(SlabBlock.TYPE, blockState.getValue(SlabBlock.TYPE));
            }

            return new Template.BlockInfo(blockinfo2.pos, blockState2, blockinfo2.nbt);
        }
    }

    @Override
    protected IStructureProcessorType<?> getType() {
        return WOEProcessors.INSTANCE.BIOME_PROCESSOR;
    }
}
