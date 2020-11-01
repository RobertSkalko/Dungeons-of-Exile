package com.robertx22.world_of_exile.config;

import com.robertx22.library_of_exile.config_utils.PerBiomeCategoryConfig;
import com.robertx22.library_of_exile.config_utils.PerBiomeConfig;
import com.robertx22.library_of_exile.config_utils.PerDimensionConfig;
import com.robertx22.world_of_exile.mixins.StructuresConfigAccessor;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FeatureConfig {

    @ConfigEntry.Gui.CollapsibleObject
    public PerDimensionConfig PER_DIM = new PerDimensionConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public PerBiomeConfig PER_BIOME = new PerBiomeConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public PerBiomeCategoryConfig PER_BIOME_CATEGORY = new PerBiomeCategoryConfig();

    public String registry_id;
    public MyStructureConfig config;

    public FeatureConfig(MyStructureConfig config, Identifier id) {
        this.config = config;
        this.registry_id = id.toString();
    }

    public void onWorldLoad(ServerWorld world) {

        StructureFeature<?> structure = getFeature();

        Objects.requireNonNull(structure);

        Map<StructureFeature<?>, StructureConfig> tempMap = new HashMap<>(world.getChunkManager()
            .getChunkGenerator()
            .getStructuresConfig()
            .getStructures());

        if (!this.PER_DIM.isAllowedIn(world.getDimension(), world)) {
            tempMap.remove(structure);
        } else {
            tempMap.put(structure, config.get());
        }

        StructuresConfigAccessor acc =
            (StructuresConfigAccessor) world.getChunkManager()
                .getChunkGenerator()
                .getStructuresConfig();

        acc.setGSStructureFeatures(tempMap);
    }

    public StructureFeature<?> getFeature() {
        return Registry.STRUCTURE_FEATURE.get(new Identifier(registry_id));
    }

    public FeatureConfig() {
    }

    public boolean isAllowedIn(DimensionType type, Biome biome, World world) {
        return this.isAllowedIn(type, biome, world.getRegistryManager());
    }

    public boolean isAllowedIn(DimensionType type, Biome biome, DynamicRegistryManager world) {
        if (type != null) {
            if (!PER_DIM.isAllowedIn(type, world)) {
                return false;
            }
        }

        if (biome != null) {
            if (!PER_BIOME.isAllowedIn(biome, world)) {
                return false;
            }
            if (!PER_BIOME_CATEGORY.isAllowedIn(biome.getCategory(), world)) {
                return false;
            }
        }

        return true;
    }

    public static class MyStructureConfig {

        private int spacing;
        private int separation;
        private int salt;

        public MyStructureConfig(int spacing, int separation, int salt) {
            this.spacing = spacing;
            this.separation = separation;
            this.salt = salt;
        }

        public MyStructureConfig() {
        }

        public StructureConfig get() {
            return new StructureConfig(spacing, separation, salt);
        }
    }

}
