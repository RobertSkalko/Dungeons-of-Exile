package com.robertx22.dungeons_of_exile.main;

import com.robertx22.dungeons_of_exile.world_gen.jigsaw.dungeon.DungeonPools;
import com.robertx22.dungeons_of_exile.world_gen.jigsaw.dungeon.ModDungeonFeature;
import com.robertx22.dungeons_of_exile.world_gen.processors.BiomeProcessor;
import com.robertx22.dungeons_of_exile.world_gen.processors.DungeonProcessor;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class ModWorldGen {

    public static ModWorldGen INSTANCE = new ModWorldGen();

    public static void init() {

    }

    public StructureFeature<StructurePoolFeatureConfig> DUNGEON = new ModDungeonFeature(StructurePoolFeatureConfig.CODEC);

    public ConfiguredStructureFeature<StructurePoolFeatureConfig, ? extends StructureFeature<StructurePoolFeatureConfig>> CONFIG_DUNGEON = DUNGEON.configure(new StructurePoolFeatureConfig(() -> {
        return DungeonPools.STARTPOOL;
    }, 10));

    ///

    public StructureProcessorType<BiomeProcessor> BIOME_PROCESSOR = StructureProcessorType.register(Ref.MODID + ":biome_processor", BiomeProcessor.CODEC);
    public StructureProcessorType<DungeonProcessor> SIGN_PROCESSOR = StructureProcessorType.register(Ref.MODID + ":sign_processor", DungeonProcessor.CODEC);

    public ModWorldGen() {

        FabricStructureBuilder.create(new Identifier(Ref.MODID, "dungeon"), DUNGEON)
            .step(GenerationStep.Feature.SURFACE_STRUCTURES)
            .defaultConfig(10, 0, 378235)
            .superflatFeature(CONFIG_DUNGEON)
            .register();

    }

}
