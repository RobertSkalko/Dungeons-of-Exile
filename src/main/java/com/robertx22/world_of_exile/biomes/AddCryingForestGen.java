package com.robertx22.world_of_exile.biomes;

import com.robertx22.world_of_exile.main.ModBiomes;
import com.robertx22.world_of_exile.main.ModWorldGen;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;

import java.util.function.Predicate;

public class AddCryingForestGen {

    static Predicate<BiomeSelectionContext> CRYING_FOREST = BiomeSelectors.includeByKey(ModBiomes.INSTANCE.CRYING_FOREST_KEY);

    public static void add() {

        BiomeModifications.addFeature(
            CRYING_FOREST,
            GenerationStep.Feature.VEGETAL_DECORATION,
            ModWorldGen.INSTANCE.PURPLE_TREE_KEY);

        BiomeModifications.addFeature(
            CRYING_FOREST,
            GenerationStep.Feature.UNDERGROUND_ORES,
            RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, new Identifier("ore_coal")));

        BiomeModifications.addCarver(
            CRYING_FOREST,
            GenerationStep.Carver.AIR,
            RegistryKey.of(Registry.CONFIGURED_CARVER_WORLDGEN, new Identifier("nether_cave")));
        BiomeModifications.addCarver(
            CRYING_FOREST,
            GenerationStep.Carver.AIR,
            RegistryKey.of(Registry.CONFIGURED_CARVER_WORLDGEN, new Identifier("cave")));

        //BiomeModifications.addSpawn(CRYING_FOREST, SpawnGroup.MONSTER, EntityType.ENDERMAN, 5, 1, 3);

    }
}
