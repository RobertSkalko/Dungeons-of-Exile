package com.robertx22.world_of_exile.main.structures.base;

import com.robertx22.world_of_exile.config.FeatureConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.function.Predicate;

public abstract class StructureWrapper {

    public ResourceLocation id;
    public StructureFeature configuredFeature;
    public FeatureConfig config;
    public GenerationStage.Decoration genStep;
    public Structure feature;
    public boolean adjustsSurface;
    public JigsawPattern startPool;
    public Predicate<BiomeLoadingEvent> biomeSelector;

    public StructureWrapper(Predicate<BiomeLoadingEvent> biomeSelector, ResourceLocation id, boolean adjustsSurface, FeatureConfig config, GenerationStage.Decoration genStep) {
        this.config = config;
        this.biomeSelector = biomeSelector;
        this.genStep = genStep;
        this.id = id;
        this.adjustsSurface = adjustsSurface;
    }

    public abstract StructureFeature createConfiguredFeature();

    public abstract Structure createFeature();

    public abstract JigsawPattern createPoolAndInitPools();

    public final void init() {
        this.feature = createFeature();
        this.configuredFeature = createConfiguredFeature();
        this.startPool = createPoolAndInitPools();
    }

    public void register() {
        init();

        /*
        FabricStructureBuilder b = FabricStructureBuilder.create(id, feature)
            .step(genStep)
            .defaultConfig(config.config.get())
            .superflatFeature(configuredFeature);
        if (adjustsSurface) {
            b.adjustsSurface();
        }
        addExtratoRegisteration(b);
        b.register();

        RegistryKey<StructureFeature<?, ?>> key = RegistryKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, id);

        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, id, configuredFeature);

        BiomeModifications.addStructure(biomeSelector, key);

         */

    }
}
