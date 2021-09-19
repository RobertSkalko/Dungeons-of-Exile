package com.robertx22.world_of_exile.main;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.robertx22.library_of_exile.main.ForgeEvents;
import com.robertx22.world_of_exile.main.structures.StoneBrickTower;
import com.robertx22.world_of_exile.main.structures.base.StructureWrapper;
import com.robertx22.world_of_exile.world_gen.tower.TowerDestroyer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod(WOE.MODID)
public class CommonInit {
    public static MinecraftServer SERVER = ServerLifecycleHooks.getCurrentServer();

    private static List<StructureWrapper> STRUCTURES = new ArrayList<>();

    static void registerFeatures() {
        //  FEATURES = new ArrayList<>();

        STRUCTURES.add(new StoneBrickTower());

        STRUCTURES.forEach(x -> {
            x.register();
        });

    }

    public static void registerStructure(StructureWrapper wrap) {
        STRUCTURES.add(wrap);
    }

    public CommonInit() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get()
            .getModEventBus();

        modEventBus.addListener(this::commonSetup);

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        forgeBus.addListener(EventPriority.HIGH, this::biomeModification);

        ModProcessorLists.INSTANCE = new ModProcessorLists();
        ModBlocks.INSTANCE = new ModBlocks();
        ModItems.INSTANCE = new ModItems();

        ModWorldGen.init();

        ForgeEvents.registerForgeEvent(TickEvent.WorldTickEvent.class, (event) -> {
            TowerDestroyer.tickAll(event.world);
        });

        registerFeatures();

        System.out.println("Dungeons of Exile initialized.");
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            setupStructures();
            registerConfiguredStructures();
        });
    }

    public void biomeModification(final BiomeLoadingEvent event) {
        STRUCTURES.forEach(x -> {
            if (x.biomeSelector.test(event)) {
                event.getGeneration()
                    .getStructures()
                    .add(() -> x.configuredFeature);
            }
        });
    }

    public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;

        STRUCTURES.forEach(x -> {
            Registry.register(registry, x.id, x.configuredFeature);
            FlatGenerationSettings.STRUCTURE_FEATURES.put(x.feature, x.configuredFeature);
        });

    }

    public static void setupStructures() {

        STRUCTURES.forEach(x -> {
            setupMapSpacingAndLand(
                x.feature,
                x.config.config.get(),
                true
            );
        });

    }

    private static <F extends Structure<?>> void setupMapSpacingAndLand(
        F structure,
        StructureSeparationSettings structureSeparationSettings,
        boolean transformSurroundingLand) {

        Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName()
            .toString(), structure);

        if (transformSurroundingLand) {
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

                if (structureMap instanceof ImmutableMap) {
                    Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                    tempMap.put(structure, structureSeparationSettings);
                    settings.getValue()
                        .structureSettings().structureConfig = tempMap;
                } else {
                    structureMap.put(structure, structureSeparationSettings);
                }
            });
    }
}
