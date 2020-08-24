package com.robertx22.dungeons_of_exile.world_gen.processors.sign_processors;

import com.mojang.serialization.Codec;
import com.robertx22.dungeons_of_exile.main.ModWorldGen;
import com.robertx22.dungeons_of_exile.mixin_ducks.SignDuck;
import net.minecraft.block.BlockState;
import net.minecraft.block.SignBlock;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SignProcessor extends StructureProcessor {

    public static final Codec<SignProcessor> CODEC = Codec.unit(SignProcessor::new);

    static List<SignTextProc> PROCS = Arrays.asList(TreasureProc.getInstance(), SpawnerProc.getInstance());

    @Override
    public Structure.StructureBlockInfo process(WorldView worldView, BlockPos pos, BlockPos blockPos, Structure.StructureBlockInfo structureBlockInfo, Structure.StructureBlockInfo blockinfo2, StructurePlacementData structurePlacementData) {
        BlockState currentState = blockinfo2.state;
        BlockState resultState = blockinfo2.state;

        try {
            if (currentState.getBlock() instanceof SignBlock) {

                SignBlockEntity signb = (SignBlockEntity) SignBlockEntity.createFromTag(currentState, blockinfo2.tag);

                SignDuck sign = (SignDuck) signb;

                List<Text> list = Arrays.asList(sign.getText());
                List<String> texts = list.stream()
                    .map(x -> x.getString())
                    .collect(Collectors.toList());

                for (SignTextProc x : PROCS) {
                    if (x.shouldProcess(texts)) {
                        return x.getProcessed(worldView, blockinfo2, structurePlacementData, texts);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Structure.StructureBlockInfo(blockinfo2.pos, resultState, blockinfo2.tag);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModWorldGen.INSTANCE.SIGN_PROCESSOR;
    }
}

