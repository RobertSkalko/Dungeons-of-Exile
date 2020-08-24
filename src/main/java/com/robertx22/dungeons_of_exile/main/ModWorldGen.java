package com.robertx22.dungeons_of_exile.main;

import com.google.common.collect.ImmutableList;
import com.robertx22.dungeons_of_exile.world_gen.jigsaw.dungeon.DungeonPools;
import com.robertx22.dungeons_of_exile.world_gen.jigsaw.dungeon.ModDungeonFeature;
import com.robertx22.dungeons_of_exile.world_gen.processors.MobProcessor;
import com.robertx22.dungeons_of_exile.world_gen.processors.biome_processor.BiomeProcessor;
import com.robertx22.dungeons_of_exile.world_gen.processors.floor_processor.FloorProcessor;
import com.robertx22.dungeons_of_exile.world_gen.processors.on_floor_processor.OnFloorProcessor;
import com.robertx22.dungeons_of_exile.world_gen.processors.sign_processors.SignProcessor;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.structure.processor.BlockAgeStructureProcessor;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class ModWorldGen {

    public static ModWorldGen INSTANCE = new ModWorldGen();

    public static void init() {

    }

    public StructureProcessorList DEFAULT_PROCESSORS = regProcs("my_processors", ImmutableList.of(new MobProcessor(), new OnFloorProcessor(), new FloorProcessor(), new BiomeProcessor(), new BlockAgeStructureProcessor(0.1F), new SignProcessor()));

    public StructureFeature<StructurePoolFeatureConfig> DUNGEON = new ModDungeonFeature(StructurePoolFeatureConfig.CODEC);

    public ConfiguredStructureFeature<StructurePoolFeatureConfig, ? extends StructureFeature<StructurePoolFeatureConfig>> CONFIG_DUNGEON = DUNGEON.configure(new StructurePoolFeatureConfig(() -> {
        return DungeonPools.STARTPOOL;
    }, 7));

    ///

    public StructureProcessorType<BiomeProcessor> BIOME_PROCESSOR = StructureProcessorType.register(Ref.MODID + ":biome_processor", BiomeProcessor.CODEC);
    public StructureProcessorType<SignProcessor> SIGN_PROCESSOR = StructureProcessorType.register(Ref.MODID + ":sign_processor", SignProcessor.CODEC);
    public StructureProcessorType<FloorProcessor> FLOOR_PROCESSOR = StructureProcessorType.register(Ref.MODID + ":floor_processor", FloorProcessor.CODEC);
    public StructureProcessorType<OnFloorProcessor> ON_FLOOR_PROCESSOR = StructureProcessorType.register(Ref.MODID + ":on_floor_processor", OnFloorProcessor.CODEC);
    public StructureProcessorType<MobProcessor> MOB_PROCESSOR = StructureProcessorType.register(Ref.MODID + ":mob_processor", MobProcessor.CODEC);

    public ModWorldGen() {

        FabricStructureBuilder.create(new Identifier(Ref.MODID, "dungeon"), DUNGEON)
            .step(GenerationStep.Feature.SURFACE_STRUCTURES)
            .defaultConfig(10, 0, 378235)
            .superflatFeature(CONFIG_DUNGEON)
            .register();

    }

    private static StructureProcessorList regProcs(String id, ImmutableList<StructureProcessor> processorList) {
        Identifier identifier = new Identifier(Ref.MODID, id);
        StructureProcessorList structureProcessorList = new StructureProcessorList(processorList);
        return (StructureProcessorList) BuiltinRegistries.add(BuiltinRegistries.STRUCTURE_PROCESSOR_LIST, (Identifier) identifier, structureProcessorList);
    }
}
