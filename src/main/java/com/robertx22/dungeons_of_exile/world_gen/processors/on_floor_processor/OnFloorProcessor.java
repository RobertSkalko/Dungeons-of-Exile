package com.robertx22.dungeons_of_exile.world_gen.processors.on_floor_processor;

import com.mojang.serialization.Codec;
import com.robertx22.dungeons_of_exile.main.ModWorldGen;
import com.robertx22.dungeons_of_exile.world_gen.processors.floor_processor.FloorProcessor;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.apache.commons.lang3.RandomUtils;

import java.util.Random;

public class OnFloorProcessor extends StructureProcessor {

    public static final Codec<OnFloorProcessor> CODEC = Codec.unit(OnFloorProcessor::new);

    @Override
    public Structure.StructureBlockInfo process(WorldView worldView, BlockPos pos, BlockPos blockPos, Structure.StructureBlockInfo structureBlockInfo, Structure.StructureBlockInfo blockinfo2, StructurePlacementData structurePlacementData) {
        BlockState currentState = blockinfo2.state;
        BlockState resultState = blockinfo2.state;

        Random random = structurePlacementData.getRandom(blockinfo2.pos);
// no air blocks are processed.. HMMM

        if (currentState.isAir()) {
            if (FloorProcessor.isFloorPlaceholder(worldView.getBlockState(pos.down())
                .getBlock())) {
                if (true) { // todo
                    ChestBlockEntity chest = new ChestBlockEntity();
                    CompoundTag resultTag = new CompoundTag();
                    chest.setLootTable(LootTables.SIMPLE_DUNGEON_CHEST, RandomUtils.nextLong());
                    chest.toTag(resultTag);
                    return new Structure.StructureBlockInfo(blockinfo2.pos, Blocks.CHEST.getDefaultState(), resultTag);

                }
            }
        }

        return new Structure.StructureBlockInfo(blockinfo2.pos, resultState, blockinfo2.tag);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModWorldGen.INSTANCE.ON_FLOOR_PROCESSOR;
    }
}


