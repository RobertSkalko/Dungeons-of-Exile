package com.robertx22.dungeons_of_exile.world_gen.processors;

import com.mojang.serialization.Codec;
import com.robertx22.dungeons_of_exile.blocks.DelayedBlockEntity;
import com.robertx22.dungeons_of_exile.main.ModStuff;
import com.robertx22.dungeons_of_exile.main.ModWorldGen;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class MobProcessor extends StructureProcessor {

    public static final Codec<MobProcessor> CODEC = Codec.unit(MobProcessor::new);

    @Override
    public Structure.StructureBlockInfo process(WorldView worldView, BlockPos pos, BlockPos blockPos, Structure.StructureBlockInfo structureBlockInfo, Structure.StructureBlockInfo blockinfo2, StructurePlacementData structurePlacementData) {
        BlockState currentState = blockinfo2.state;
        BlockState resultState = blockinfo2.state;

        try {
            if (currentState.getBlock() == Blocks.ZOMBIE_HEAD) {
                resultState = Blocks.AIR.getDefaultState();

                DelayedBlockEntity en = new DelayedBlockEntity();
                en.executionString = "mob";

                CompoundTag tag = new CompoundTag();
                en.toTag(tag);

                return new Structure.StructureBlockInfo(blockinfo2.pos, ModStuff.INSTANCE.DELAY_BLOCK.getDefaultState(), tag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Structure.StructureBlockInfo(blockinfo2.pos, resultState, blockinfo2.tag);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModWorldGen.INSTANCE.MOB_PROCESSOR;
    }
}


