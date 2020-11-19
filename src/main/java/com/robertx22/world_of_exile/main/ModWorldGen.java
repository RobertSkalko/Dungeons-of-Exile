package com.robertx22.world_of_exile.main;

import com.robertx22.world_of_exile.biomes.MyCaveCarver;
import com.robertx22.world_of_exile.config.ModConfig;
import com.robertx22.world_of_exile.world_gen.jigsaw.blackstone_tower.BlackStoneTowerPools;
import com.robertx22.world_of_exile.world_gen.jigsaw.blackstone_tower.BlackstoneTowerStructure;
import com.robertx22.world_of_exile.world_gen.jigsaw.dungeon.DungeonPools;
import com.robertx22.world_of_exile.world_gen.jigsaw.dungeon.ModDungeonFeature;
import com.robertx22.world_of_exile.world_gen.jigsaw.ladder_tower.LadderTowerPools;
import com.robertx22.world_of_exile.world_gen.jigsaw.ladder_tower.LadderTowerStructure;
import com.robertx22.world_of_exile.world_gen.jigsaw.stone_brick_tower.StoneBrickTowerPools;
import com.robertx22.world_of_exile.world_gen.jigsaw.stone_brick_tower.StoneBrickTowerStructure;
import com.robertx22.world_of_exile.world_gen.processors.BeaconProcessor;
import com.robertx22.world_of_exile.world_gen.processors.SignProcessor;
import com.robertx22.world_of_exile.world_gen.processors.biome_processor.BiomeProcessor;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;

public class ModWorldGen {

    public static ModWorldGen INSTANCE = new ModWorldGen();

    public static void init() {

    }

    public MyCaveCarver MY_CAVE_CARVER = Registry.register(Registry.CARVER, WOE.id("my_cave"), new MyCaveCarver(ProbabilityConfig.CODEC, 256));
    ConfiguredCarver<ProbabilityConfig> CONFG_CAVE = BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_CARVER, WOE.id("my_cave"), MY_CAVE_CARVER.configure(new ProbabilityConfig(0.4F))); //0.14285715F

    HugeFungusFeatureConfig PURPLE_TREE_CONFIG = new HugeFungusFeatureConfig(ModBlocks.INSTANCE.PURPLE_GRASS.getDefaultState(), ModBlocks.INSTANCE.PURPLE_LOG.getDefaultState(), ModBlocks.INSTANCE.PURPLE_LEAVES.getDefaultState(), Blocks.SHROOMLIGHT.getDefaultState(), false);

    public ConfiguredFeature<?, ?> PURPLE_TREE = register(ModWorldGenIds.PURPLE_TREE_ID, Feature.HUGE_FUNGUS.configure(PURPLE_TREE_CONFIG)
        .decorate(Decorator.COUNT_MULTILAYER.configure(new CountConfig(5))));

    <T extends ConfiguredFeature<?, ?>> T register(Identifier id, T feature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, feature);
    }

    public StructureFeature<StructurePoolFeatureConfig> DUNGEON = new ModDungeonFeature(StructurePoolFeatureConfig.CODEC);
    public StructureFeature<StructurePoolFeatureConfig> BLACKSTONE_TOWER = new BlackstoneTowerStructure(StructurePoolFeatureConfig.CODEC);
    public StructureFeature<StructurePoolFeatureConfig> STONE_BRICK_TOWER = new StoneBrickTowerStructure(StructurePoolFeatureConfig.CODEC);
    public StructureFeature<StructurePoolFeatureConfig> LADDER_TOWER = new LadderTowerStructure(StructurePoolFeatureConfig.CODEC);

    public ConfiguredStructureFeature<StructurePoolFeatureConfig, ? extends StructureFeature<StructurePoolFeatureConfig>> CONFIG_DUNGEON =
        DUNGEON.configure(new StructurePoolFeatureConfig(() -> {
            return DungeonPools.STARTPOOL;
        }, 6));

    public ConfiguredStructureFeature<StructurePoolFeatureConfig, ? extends StructureFeature<StructurePoolFeatureConfig>> CONFIG_STONE_BRICK_TOWER =
        STONE_BRICK_TOWER.configure(new StructurePoolFeatureConfig(() -> {
            return StoneBrickTowerPools.STARTPOOL;
        }, 6));

    public ConfiguredStructureFeature<StructurePoolFeatureConfig, ? extends StructureFeature<StructurePoolFeatureConfig>> CONFIG_LADDER_TOWER =
        LADDER_TOWER.configure(new StructurePoolFeatureConfig(() -> {
            return LadderTowerPools.STARTPOOL;
        }, 15));

    public ConfiguredStructureFeature<StructurePoolFeatureConfig, ? extends StructureFeature<StructurePoolFeatureConfig>> CONFIG_BLACKSTONE_TOWER =
        BLACKSTONE_TOWER.configure(
            new StructurePoolFeatureConfig(() -> {
                return BlackStoneTowerPools.STARTPOOL;
            }, 25));

    public RegistryKey<ConfiguredFeature<?, ?>> PURPLE_TREE_KEY = RegistryKey.of(BuiltinRegistries.CONFIGURED_FEATURE.getKey(), ModWorldGenIds.PURPLE_TREE_ID);

    public StructureProcessorType<BiomeProcessor> BIOME_PROCESSOR = StructureProcessorType.register(WOE.MODID + ":biome_processor", BiomeProcessor.CODEC);
    public StructureProcessorType<BeaconProcessor> BEACON_PROCESSOR = StructureProcessorType.register(WOE.MODID + ":mob_processor", BeaconProcessor.CODEC);
    public StructureProcessorType<SignProcessor> SIGN_PROCESSOR = StructureProcessorType.register(WOE.MODID + ":sign_processor", SignProcessor.CODEC);

    public ModWorldGen() {

        FabricStructureBuilder.create(ModWorldGenIds.DUNGEON_ID, DUNGEON)
            .step(GenerationStep.Feature.SURFACE_STRUCTURES)
            .defaultConfig(ModConfig.get().DUNGEON.config.get())
            .superflatFeature(CONFIG_DUNGEON)
            .register();

        FabricStructureBuilder.create(ModWorldGenIds.BLACKSTONE_TOWER_ID, BLACKSTONE_TOWER)
            .step(GenerationStep.Feature.SURFACE_STRUCTURES)
            .defaultConfig(ModConfig.get().BLACKSTONE_TOWER.config.get())
            .superflatFeature(CONFIG_BLACKSTONE_TOWER)
            .adjustsSurface()
            .register();

        FabricStructureBuilder.create(ModWorldGenIds.STONE_BRICK_TOWER_ID, STONE_BRICK_TOWER)
            .step(GenerationStep.Feature.SURFACE_STRUCTURES)
            .defaultConfig(ModConfig.get().STONE_BRICK_TOWER.config.get())
            .superflatFeature(CONFIG_STONE_BRICK_TOWER)
            .adjustsSurface()
            .register();

        FabricStructureBuilder.create(ModWorldGenIds.LADDER_TOWER_ID, LADDER_TOWER)
            .step(GenerationStep.Feature.SURFACE_STRUCTURES)
            .defaultConfig(ModConfig.get().LADDER_TOWER.config.get())
            .superflatFeature(CONFIG_LADDER_TOWER)
            .adjustsSurface()
            .register();

    }

}
