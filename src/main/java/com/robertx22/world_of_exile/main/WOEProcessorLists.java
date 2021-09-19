package com.robertx22.world_of_exile.main;

import com.google.common.collect.ImmutableList;
import com.robertx22.world_of_exile.world_gen.processors.SignProcessor;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.template.*;

public class WOEProcessorLists {

    public static WOEProcessorLists INSTANCE;

    public StructureProcessorList STONE_BRICK_TOWER = regProcs("stone_brick_tower", ImmutableList.of(
        new SignProcessor(),
        new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.SMOOTH_STONE, 0.3F), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.defaultBlockState())))
    ));

    private static StructureProcessorList regProcs(String id, ImmutableList<StructureProcessor> processorList) {
        ResourceLocation identifier = WOE.id(id);

        StructureProcessorList structureProcessorList = new StructureProcessorList(processorList);

        return WorldGenRegistries.register(WorldGenRegistries.PROCESSOR_LIST, identifier, structureProcessorList);
    }
}
