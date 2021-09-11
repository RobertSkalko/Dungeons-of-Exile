package com.robertx22.world_of_exile;

import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.Biome;

import java.util.function.Predicate;

public class MyBiomeSelectors {

    public static Predicate<BiomeSelectionContext> OVERWORLD_LAND = BiomeSelectors.foundInOverworld()
        .and(x -> {
            Biome.Category cat = x.getBiome()
                .getCategory();

            return cat != Biome.Category.OCEAN && cat != Biome.Category.BEACH && cat != Biome.Category.RIVER;
        });
}
