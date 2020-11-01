package com.robertx22.world_of_exile.main;

import com.google.common.collect.ImmutableList;
import com.robertx22.world_of_exile.world_gen.processors.BeaconProcessor;
import com.robertx22.world_of_exile.world_gen.processors.SignProcessor;
import net.minecraft.block.Blocks;
import net.minecraft.structure.processor.RuleStructureProcessor;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.structure.processor.StructureProcessorRule;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.RandomBlockMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;

public class ModProcessors {

    public static ModProcessors INSTANCE;

    public StructureProcessorList DEFAULT_PROCESSORS = regProcs("my_processors", ImmutableList.of(
        new BeaconProcessor(),
        new RuleStructureProcessor(ImmutableList.of(new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.STONE_BRICKS, 0.5F), AlwaysTrueRuleTest.INSTANCE, Blocks.MOSSY_STONE_BRICKS.getDefaultState())))
        , new RuleStructureProcessor(ImmutableList.of(new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.POLISHED_ANDESITE, 0.5F), AlwaysTrueRuleTest.INSTANCE, Blocks.ANDESITE.getDefaultState())))

    ));
    public StructureProcessorList TOWER_PROCESORS = regProcs("tower_processors", ImmutableList.of(
        new SignProcessor(),
        new RuleStructureProcessor(ImmutableList.of(new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.POLISHED_BLACKSTONE, 0.2F), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState())))
    ));

    private static StructureProcessorList regProcs(String id, ImmutableList<StructureProcessor> processorList) {
        Identifier identifier = new Identifier(WOE.MODID, id);

        StructureProcessorList structureProcessorList = new StructureProcessorList(processorList);

        return BuiltinRegistries.add(BuiltinRegistries.STRUCTURE_PROCESSOR_LIST, identifier, structureProcessorList);
    }
}
