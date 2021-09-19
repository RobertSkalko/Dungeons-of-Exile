package com.robertx22.world_of_exile.main;

import com.google.common.collect.ImmutableList;
import com.robertx22.world_of_exile.world_gen.processors.BeaconProcessor;
import com.robertx22.world_of_exile.world_gen.processors.SignProcessor;
import com.robertx22.world_of_exile.world_gen.processors.biome_processor.HellOnlyBiomeProcessor;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.template.*;

public class ModProcessorLists {

    public static ModProcessorLists INSTANCE;

    public StructureProcessorList DEFAULT_PROCESSORS = regProcs("my_processors", ImmutableList.of(
        new BeaconProcessor(),
        new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.STONE_BRICKS, 0.5F), AlwaysTrueRuleTest.INSTANCE, Blocks.MOSSY_STONE_BRICKS.defaultBlockState())))
        , new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.POLISHED_ANDESITE, 0.5F), AlwaysTrueRuleTest.INSTANCE, Blocks.ANDESITE.defaultBlockState())))

    ));
    public StructureProcessorList BLACKSTONE_TOWER = regProcs("blackstone_tower", ImmutableList.of(
        new SignProcessor(),
        new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.POLISHED_BLACKSTONE, 0.2F), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.defaultBlockState())))
    ));

    public StructureProcessorList STONE_BRICK_TOWER = regProcs("stone_brick_tower", ImmutableList.of(
        new SignProcessor(),
        new HellOnlyBiomeProcessor(),
        new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.SMOOTH_STONE, 0.3F), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.defaultBlockState())))
    ));

    private static StructureProcessorList regProcs(String id, ImmutableList<StructureProcessor> processorList) {
        ResourceLocation identifier = WOE.id(id);

        StructureProcessorList structureProcessorList = new StructureProcessorList(processorList);

        return WorldGenRegistries.register(WorldGenRegistries.PROCESSOR_LIST, identifier, structureProcessorList);
    }
}
