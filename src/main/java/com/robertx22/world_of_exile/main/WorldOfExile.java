package com.robertx22.world_of_exile.main;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.robertx22.library_of_exile.main.ForgeEvents;
import com.robertx22.world_of_exile.main.structures.StoneBrickTower;
import com.robertx22.world_of_exile.main.structures.base.StructureWrapper;
import com.robertx22.world_of_exile.world_gen.tower.TowerDestroyer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod(WOE.MODID)
public class WorldOfExile {

    public static List<StructureWrapper> STRUCTURES = new ArrayList<>();

    static void registerFeatures() {
        STRUCTURES.forEach(x -> {
            x.initFeature();
        });
    }

    public static void registerStructure(StructureWrapper wrap) {
        STRUCTURES.add(wrap);
    }

    public WorldOfExile() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get()
            .getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        WOEDeferred.registerDefferedAtStartOfModLoading();

        STRUCTURES.add(new StoneBrickTower());

        WOEProcessorLists.INSTANCE = new WOEProcessorLists();

        modEventBus.addListener(this::commonSetup);

        forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
        forgeBus.addListener(EventPriority.HIGH, this::biomeModification);

        WOEProcessors.init();

        ForgeEvents.registerForgeEvent(TickEvent.WorldTickEvent.class, (event) -> {
            TowerDestroyer.tickAll(event.world);
        });

        registerFeatures();

        System.out.println("Dungeons of Exile initialized.");
    }

    // STRUCTURE REGISTRATION STUFF
    public void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            setupStructures();
            registerConfiguredStructures();
        });
    }

    public void biomeModification(final BiomeLoadingEvent event) {
        STRUCTURES.forEach(x -> {
            if (x.biomeSelector.test(event)) {

                Preconditions.checkNotNull(x.configuredFeature);

                event.getGeneration()
                    .getStructures()
                    .add(() -> x.configuredFeature);
            }
        });
    }

    public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;

        STRUCTURES.forEach(x -> {

            x.initConfigured();

            Preconditions.checkNotNull(x.feature.get());
            Preconditions.checkNotNull(x.configuredFeature.feature);

            Registry.register(registry, x.id, x.configuredFeature);
            FlatGenerationSettings.STRUCTURE_FEATURES.put(x.feature.get(), x.configuredFeature);
        });

    }

    public static void setupStructures() {

        for (StructureWrapper x : STRUCTURES) {

            Preconditions.checkNotNull(x.feature);
            Preconditions.checkNotNull(x.feature.get());

            setupMapSpacingAndLand(
                x.feature.get(),
                x.config.config.get(),
                true
            );
        }

    }

    private static <F extends Structure<?>> void setupMapSpacingAndLand(
        F structure,
        StructureSeparationSettings structureSeparationSettings,
        boolean flattenLand) {

        Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName()
            .toString(), structure);

        if (flattenLand) {
            Structure.NOISE_AFFECTING_FEATURES =
                ImmutableList.<Structure<?>>builder()
                    .addAll(Structure.NOISE_AFFECTING_FEATURES)
                    .add(structure)
                    .build();
        }

        DimensionStructuresSettings.DEFAULTS =
            ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                .putAll(DimensionStructuresSettings.DEFAULTS)
                .put(structure, structureSeparationSettings)
                .build();

        WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet()
            .forEach(settings -> {
                Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue()
                    .structureSettings()
                    .structureConfig();

                Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, structureSeparationSettings);
                settings.getValue()
                    .structureSettings().structureConfig = tempMap;

            });
    }

    private static Method GETCODEC_METHOD;

    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) event.getWorld();

            try {
                if (GETCODEC_METHOD == null)
                    GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
                ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkSource().generator));
                if (cgRL != null && cgRL.getNamespace()
                    .equals("terraforged")) return;
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (serverWorld.getChunkSource()
                .getGenerator() instanceof FlatChunkGenerator &&
                serverWorld.dimension()
                    .equals(World.OVERWORLD)) {
                return;
            }

            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkSource().generator.getSettings()
                .structureConfig());

            STRUCTURES.forEach(x -> {
                tempMap.putIfAbsent(x.feature.get(), x.config.config.get());
            });

            serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;
        }
    }
    // STRUCTURE REGISTRATION STUFF
}
