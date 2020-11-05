package com.robertx22.world_of_exile.main.entities;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.BiomeKeys;

public class MobSpawnsInit {

    public static void register() {

        BiomeModifications.addSpawn(
            BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY, BiomeKeys.SNOWY_MOUNTAINS),
            SpawnGroup.MONSTER,
            ModEntities.INSTANCE.FROST_BLAZE,
            2, 1, 1);

        BiomeModifications.addSpawn(
            BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY, BiomeKeys.SNOWY_MOUNTAINS),
            SpawnGroup.MONSTER,
            ModEntities.INSTANCE.FROST_BAT,
            1, 1, 3);

        BiomeModifications.addSpawn(
            BiomeSelectors.includeByKey(BiomeKeys.CRIMSON_FOREST),
            SpawnGroup.MONSTER,
            ModEntities.INSTANCE.INFERNO_BAT,
            1, 1, 3);

        BiomeModifications.addSpawn(
            BiomeSelectors.includeByKey(BiomeKeys.CRIMSON_FOREST),
            SpawnGroup.MONSTER,
            ModEntities.INSTANCE.INFERNO_SCORPION,
            5, 1, 1);

        BiomeModifications.addSpawn(
            BiomeSelectors.includeByKey(BiomeKeys.WARPED_FOREST),
            SpawnGroup.MONSTER,
            ModEntities.INSTANCE.POISON_SPIDER,
            5, 1, 1);
    }

}

