package com.robertx22.world_of_exile.main;

import net.minecraft.block.Blocks;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class ModBiomes {

    public static ModBiomes INSTANCE;

    public ModBiomes() {
    }

    ConfiguredSurfaceBuilder<TernarySurfaceConfig> CRYING_FOREST_SURFACE = Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, WOE.id("purple_surface"), SurfaceBuilder.DEFAULT
        .withConfig(new TernarySurfaceConfig(
            ModBlocks.INSTANCE.PURPLE_GRASS.getDefaultState(),
            Blocks.DIRT.getDefaultState(),
            Blocks.DIRT.getDefaultState())));

    public RegistryKey<Biome> CRYING_FOREST_KEY = RegistryKey.of(Registry.BIOME_KEY, WOE.id("crying_forest"));

}
