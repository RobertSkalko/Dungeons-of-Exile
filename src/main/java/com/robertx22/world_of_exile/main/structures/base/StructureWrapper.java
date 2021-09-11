package com.robertx22.world_of_exile.main.structures.base;

import com.robertx22.world_of_exile.config.FeatureConfig;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.function.Predicate;

public abstract class StructureWrapper {

    public Identifier id;
    public ConfiguredStructureFeature configuredFeature;
    public FeatureConfig config;
    public GenerationStep.Feature genStep;
    public StructureFeature feature;
    public boolean adjustsSurface;
    public StructurePool startPool;
    Predicate<BiomeSelectionContext> biomeSelector;

    public StructureWrapper(Predicate<BiomeSelectionContext> biomeSelector, Identifier id, boolean adjustsSurface, FeatureConfig config, GenerationStep.Feature genStep) {
        this.config = config;
        this.biomeSelector = biomeSelector;
        this.genStep = genStep;
        this.id = id;
        this.adjustsSurface = adjustsSurface;
    }

    public abstract ConfiguredStructureFeature createConfiguredFeature();

    public abstract StructureFeature createFeature();

    public abstract StructurePool createPoolAndInitPools();

    public final void init() {
        this.feature = createFeature();
        this.configuredFeature = createConfiguredFeature();
        this.startPool = createPoolAndInitPools();
    }

    public void addExtratoRegisteration(FabricStructureBuilder b) {

    }

    public void register() {
        init();

        FabricStructureBuilder b = FabricStructureBuilder.create(id, feature)
            .step(genStep)
            .defaultConfig(config.config.get())
            .superflatFeature(configuredFeature);
        if (adjustsSurface) {
            b.adjustsSurface();
        }
        addExtratoRegisteration(b);
        b.register();

        RegistryKey<ConfiguredStructureFeature<?, ?>> key = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_WORLDGEN, id);

        BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, id, configuredFeature);

        BiomeModifications.addStructure(biomeSelector, key);

    }
}
