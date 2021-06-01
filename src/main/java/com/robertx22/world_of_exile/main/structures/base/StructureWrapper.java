package com.robertx22.world_of_exile.main.structures.base;

import com.robertx22.world_of_exile.config.FeatureConfig;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructureFeature;

public abstract class StructureWrapper {

    public Identifier id;
    public ConfiguredStructureFeature configuredFeature;
    public FeatureConfig config;
    public GenerationStep.Feature genStep;
    public StructureFeature feature;
    public boolean adjustsSurface;
    public StructurePool startPool;

    public StructureWrapper(Identifier id, boolean adjustsSurface, FeatureConfig config, GenerationStep.Feature genStep) {
        this.config = config;
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
        FabricStructureBuilder b = FabricStructureBuilder.create(id, feature)
            .step(this.genStep)
            .defaultConfig(this.config.config.get())
            .superflatFeature(this.configuredFeature);
        if (adjustsSurface) {
            b.adjustsSurface();
        }
        addExtratoRegisteration(b);
        b.register();

    }
}
