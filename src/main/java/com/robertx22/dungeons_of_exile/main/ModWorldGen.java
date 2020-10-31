package com.robertx22.dungeons_of_exile.main;

import com.robertx22.dungeons_of_exile.world_gen.jigsaw.blackstone_tower.BlackStoneTowerPools;
import com.robertx22.dungeons_of_exile.world_gen.jigsaw.blackstone_tower.BlackstoneTowerStructure;
import com.robertx22.dungeons_of_exile.world_gen.jigsaw.dungeon.DungeonPools;
import com.robertx22.dungeons_of_exile.world_gen.jigsaw.dungeon.ModDungeonFeature;
import com.robertx22.dungeons_of_exile.world_gen.jigsaw.stone_brick_tower.StoneBrickTowerPools;
import com.robertx22.dungeons_of_exile.world_gen.jigsaw.stone_brick_tower.StoneBrickTowerStructure;
import com.robertx22.dungeons_of_exile.world_gen.processors.BeaconProcessor;
import com.robertx22.dungeons_of_exile.world_gen.processors.SignProcessor;
import com.robertx22.dungeons_of_exile.world_gen.processors.biome_processor.BiomeProcessor;
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
    public StructureFeature<StructurePoolFeatureConfig> BLACKSTONE_TOWER = new BlackstoneTowerStructure(StructurePoolFeatureConfig.CODEC);
    public StructureFeature<StructurePoolFeatureConfig> STONE_BRICK_TOWER = new StoneBrickTowerStructure(StructurePoolFeatureConfig.CODEC);

    public ConfiguredStructureFeature<StructurePoolFeatureConfig, ? extends StructureFeature<StructurePoolFeatureConfig>> CONFIG_DUNGEON =
        DUNGEON.configure(new StructurePoolFeatureConfig(() -> {
            return DungeonPools.STARTPOOL;
        }, 6));

    public ConfiguredStructureFeature<StructurePoolFeatureConfig, ? extends StructureFeature<StructurePoolFeatureConfig>> CONFIG_STONE_BRICK_TOWER =
        STONE_BRICK_TOWER.configure(new StructurePoolFeatureConfig(() -> {
            return StoneBrickTowerPools.STARTPOOL;
        }, 6));

    public ConfiguredStructureFeature<StructurePoolFeatureConfig, ? extends StructureFeature<StructurePoolFeatureConfig>> CONFIG_BLACKSTONE_TOWER =
        BLACKSTONE_TOWER.configure(
            new StructurePoolFeatureConfig(() -> {
                return BlackStoneTowerPools.STARTPOOL;
            }, 25));

    public StructureProcessorType<BiomeProcessor> BIOME_PROCESSOR = StructureProcessorType.register(WOE.MODID + ":biome_processor", BiomeProcessor.CODEC);
    public StructureProcessorType<BeaconProcessor> BEACON_PROCESSOR = StructureProcessorType.register(WOE.MODID + ":mob_processor", BeaconProcessor.CODEC);
    public StructureProcessorType<SignProcessor> SIGN_PROCESSOR = StructureProcessorType.register(WOE.MODID + ":sign_processor", SignProcessor.CODEC);

    public ModWorldGen() {

        FabricStructureBuilder.create(new Identifier(WOE.MODID, "dungeon"), DUNGEON)
            .step(GenerationStep.Feature.SURFACE_STRUCTURES)
            .defaultConfig(22, 0, 378235)
            .superflatFeature(CONFIG_DUNGEON)
            .register();

        FabricStructureBuilder.create(ModStructures.BLACKSTONE_TOWER_ID, BLACKSTONE_TOWER)
            .step(GenerationStep.Feature.SURFACE_STRUCTURES)
            .defaultConfig(DungeonConfig.get().BLACKSTONE_TOWER.config.get())
            .superflatFeature(CONFIG_BLACKSTONE_TOWER)
            .adjustsSurface()
            .register();

        FabricStructureBuilder.create(ModStructures.STONE_BRICK_TOWER_ID, STONE_BRICK_TOWER)
            .step(GenerationStep.Feature.SURFACE_STRUCTURES)
            .defaultConfig(DungeonConfig.get().STONE_BRICK_TOWER.config.get())
            .superflatFeature(CONFIG_STONE_BRICK_TOWER)
            .adjustsSurface()
            .register();

    }

}
