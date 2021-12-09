package com.robertx22.world_of_exile.config;

import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.common.ForgeConfigSpec;

public class FeatureConfig {

    public MyStructureConfig config;

    public FeatureConfig(MyStructureConfig config) {
        this.config = config;
    }

    public FeatureConfig() {
    }

    public static class MyStructureConfig {

        private ForgeConfigSpec.IntValue spacing;
        private ForgeConfigSpec.IntValue separation;
        private ForgeConfigSpec.IntValue salt;

        public MyStructureConfig(ForgeConfigSpec.IntValue spacing, ForgeConfigSpec.IntValue separation, ForgeConfigSpec.IntValue salt) {
            this.spacing = spacing;
            this.separation = separation;
            this.salt = salt;
        }

        public MyStructureConfig() {
        }

        public StructureSeparationSettings get() {
            return new StructureSeparationSettings(spacing.get(), separation.get(), salt.get());
        }
    }

}
