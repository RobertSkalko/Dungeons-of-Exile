package com.robertx22.world_of_exile.main;

import com.robertx22.world_of_exile.main.entities.ModEntities;
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
            10, 1, 1);
    }

}

