package com.robertx22.world_of_exile.main;

import com.google.common.collect.ImmutableList;
import com.robertx22.world_of_exile.world_gen.processors.BeaconProcessor;
import com.robertx22.world_of_exile.world_gen.processors.SignProcessor;
import com.robertx22.world_of_exile.world_gen.processors.biome_processor.HellOnlyBiomeProcessor;
import net.minecraft.block.Blocks;
import net.minecraft.structure.processor.RuleStructureProcessor;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.structure.processor.StructureProcessorRule;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.RandomBlockMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;

public class ModProcessorLists {

    public static ModProcessorLists INSTANCE;

    public StructureProcessorList DEFAULT_PROCESSORS = regProcs("my_processors", ImmutableList.of(
        new BeaconProcessor(),
        new RuleStructureProcessor(ImmutableList.of(new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.STONE_BRICKS, 0.5F), AlwaysTrueRuleTest.INSTANCE, Blocks.MOSSY_STONE_BRICKS.getDefaultState())))
        , new RuleStructureProcessor(ImmutableList.of(new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.POLISHED_ANDESITE, 0.5F), AlwaysTrueRuleTest.INSTANCE, Blocks.ANDESITE.getDefaultState())))

    ));
    public StructureProcessorList BLACKSTONE_TOWER = regProcs("blackstone_tower", ImmutableList.of(
        new SignProcessor(),
        new RuleStructureProcessor(ImmutableList.of(new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.POLISHED_BLACKSTONE, 0.2F), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState())))
    ));

    public StructureProcessorList STONE_BRICK_TOWER = regProcs("stone_brick_tower", ImmutableList.of(
        new SignProcessor(),
        new HellOnlyBiomeProcessor(),
        new RuleStructureProcessor(ImmutableList.of(new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.SMOOTH_STONE, 0.3F), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState())))
    ));

    private static StructureProcessorList regProcs(String id, ImmutableList<StructureProcessor> processorList) {
        Identifier identifier = WOE.id(id);

        StructureProcessorList structureProcessorList = new StructureProcessorList(processorList);

        return BuiltinRegistries.add(BuiltinRegistries.STRUCTURE_PROCESSOR_LIST, identifier, structureProcessorList);
    }
}
