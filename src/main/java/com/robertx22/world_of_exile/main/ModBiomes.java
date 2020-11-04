package com.robertx22.world_of_exile.main;

public class ModBiomes {

    public static ModBiomes INSTANCE;

    public ModBiomes() {

    }

    /*
    public static final RegistryKey<Biome> WARPED_KEY = RegistryKey.of(Registry.BIOME_KEY, WOE.id("mod_warped_forest"));

    public Biome MOD_WARPED_FOREST = register(WARPED_KEY, createWarpedForest());



    private Biome register(RegistryKey<Biome> registryKey, Biome biome) {


        Registry.register(Registry.BIOME_KEY, registryKey.getValue(), biome);
        BuiltInBiomesAccessor.getRawIdMap()
            .put(BuiltinRegistries.BIOME.getRawId(biome), registryKey);


        return biome;
    }

    public static Biome createWarpedForest() {
        SpawnSettings spawnSettings = (new SpawnSettings.Builder())
            .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 1, 4, 4))
            .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.WITHER, 1, 4, 4))
            .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SKELETON, 1, 4, 4))
            .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 1, 4, 4))
            .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.STRIDER, 60, 1, 2))
            .spawnCost(EntityType.ENDERMAN, 1.0D, 0.12D)
            .spawnCost(EntityType.WITHER, 1.0D, 0.12D)
            .spawnCost(EntityType.SKELETON, 1.0D, 0.12D)
            .build();
        GenerationSettings.Builder builder = (new GenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.WARPED_FOREST)
            .structureFeature(ConfiguredStructureFeatures.FORTRESS)
            .structureFeature(ConfiguredStructureFeatures.BASTION_REMNANT)
            .structureFeature(ModWorldGen.INSTANCE.CONFIG_BLACKSTONE_TOWER)
            //           .carver(GenerationStep.Carver.AIR, ConfiguredCarvers.NETHER_CAVE)
            .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SPRING_LAVA);
        DefaultBiomeFeatures.addDefaultMushrooms(builder);
        builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.SPRING_OPEN)
            .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.WARPED_FUNGI)
            .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.WARPED_FOREST_VEGETATION)
            .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.NETHER_SPROUTS)
            .feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.TWISTING_VINES);
        DefaultBiomeFeatures.addNetherMineables(builder);
        return (new Biome.Builder()).precipitation(Biome.Precipitation.NONE)
            .category(Biome.Category.NETHER)
            .depth(0.1F)
            .scale(0.2F)
            .temperature(2.0F)
            .downfall(0.0F)
            .effects((new BiomeEffects.Builder()).waterColor(4159204)
                .waterFogColor(329011)
                .fogColor(1705242)
                .skyColor(getSkyColor(2.0F))
                .particleConfig(new BiomeParticleConfig(ParticleTypes.WARPED_SPORE, 0.01428F))
                .loopSound(SoundEvents.AMBIENT_WARPED_FOREST_LOOP)
                .moodSound(new BiomeMoodSound(SoundEvents.AMBIENT_WARPED_FOREST_MOOD, 6000, 8, 2.0D))
                .additionsSound(new BiomeAdditionsSound(SoundEvents.AMBIENT_WARPED_FOREST_ADDITIONS, 0.0111D))
                .music(MusicType.createIngameMusic(SoundEvents.MUSIC_NETHER_WARPED_FOREST))
                .build())
            .spawnSettings(spawnSettings)
            .generationSettings(builder.build())
            .build();
    }

    private static int getSkyColor(float temperature) {
        float f = temperature / 3.0F;
        f = MathHelper.clamp(f, -1.0F, 1.0F);
        return MathHelper.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }
    */
}
