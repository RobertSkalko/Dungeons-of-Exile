package com.robertx22.world_of_exile.config;

import net.minecraft.world.gen.chunk.StructureConfig;

public class FeatureConfig {

    public MyStructureConfig config;

    public FeatureConfig(MyStructureConfig config) {
        this.config = config;
    }

    public FeatureConfig() {
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
