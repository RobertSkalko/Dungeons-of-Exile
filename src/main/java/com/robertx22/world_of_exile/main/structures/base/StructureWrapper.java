package com.robertx22.world_of_exile.main.structures.base;

import com.robertx22.world_of_exile.config.FeatureConfig;
import com.robertx22.world_of_exile.main.WOEDeferred;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Predicate;

public abstract class StructureWrapper {

    public ResourceLocation id;
    public StructureFeature configuredFeature;
    public FeatureConfig config;
    public GenerationStage.Decoration genStep;
    public RegistryObject<Structure<VillageConfig>> feature;
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

    public abstract Structure<VillageConfig> createFeature();

    public abstract JigsawPattern createPoolAndInitPools();

    public final void initFeature() {

        this.feature = WOEDeferred.STRUCTURES.register(id.getPath(), () -> createFeature());
        this.startPool = createPoolAndInitPools();

    }

    public final void initConfigured() {
        this.configuredFeature = createConfiguredFeature();
    }
}
