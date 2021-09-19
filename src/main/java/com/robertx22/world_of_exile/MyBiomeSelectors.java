package com.robertx22.world_of_exile;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.function.Predicate;

public class MyBiomeSelectors {

    public static Predicate<BiomeLoadingEvent> OVERWORLD_LAND =
        x -> {

            Biome.Category cat = x.getCategory();
            
            return cat != Biome.Category.OCEAN &&
                cat != Biome.Category.BEACH &&
                cat != Biome.Category.NETHER &&
                cat != Biome.Category.THEEND &&
                cat != Biome.Category.RIVER;
        };
}
