package com.robertx22.dungeons_of_exile.world_gen.processors.floor_processor;

import com.mojang.serialization.Codec;
import com.robertx22.dungeons_of_exile.main.ModWorldGen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import java.util.Random;

public class FloorProcessor extends StructureProcessor {

    public static final Codec<FloorProcessor> CODEC = Codec.unit(FloorProcessor::new);

    public static Block MAGMA_PLACEHOLDER = Blocks.GOLD_BLOCK;
    public static Block COBBLE_PLACEHOLDER = Blocks.IRON_BLOCK;
    public static Block SOUL_SAND_PLACEHOLDER = Blocks.DIAMOND_BLOCK;

    public static boolean isFloorPlaceholder(Block block) {
        return block == MAGMA_PLACEHOLDER || block == SOUL_SAND_PLACEHOLDER || block == COBBLE_PLACEHOLDER;
    }

    @Override
    public Structure.StructureBlockInfo process(WorldView worldView, BlockPos pos, BlockPos blockPos, Structure.StructureBlockInfo structureBlockInfo, Structure.StructureBlockInfo blockinfo2, StructurePlacementData structurePlacementData) {
        BlockState currentState = blockinfo2.state;
        BlockState resultState = blockinfo2.state;

        Random random = structurePlacementData.getRandom(blockinfo2.pos);

        if (currentState.getBlock() == MAGMA_PLACEHOLDER) {
            if (random.nextBoolean()) {
                resultState = Blocks.MAGMA_BLOCK.getDefaultState();
            } else {
                resultState = Blocks.COBBLESTONE.getDefaultState();
            }
        } else if (currentState.getBlock() == SOUL_SAND_PLACEHOLDER) {
            if (random.nextBoolean()) {
                resultState = Blocks.SOUL_SAND.getDefaultState();
            } else {
                resultState = Blocks.COBBLESTONE.getDefaultState();
            }
        }

        return new Structure.StructureBlockInfo(blockinfo2.pos, resultState, blockinfo2.tag);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModWorldGen.INSTANCE.FLOOR_PROCESSOR;
    }
}

