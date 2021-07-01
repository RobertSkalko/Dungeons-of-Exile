package com.robertx22.world_of_exile.main;

import com.robertx22.world_of_exile.config.ModConfig;
import com.robertx22.world_of_exile.events.OnServerTick;
import com.robertx22.world_of_exile.main.entities.MobEvents;
import com.robertx22.world_of_exile.main.entities.ModEntityAttributes;
import com.robertx22.world_of_exile.main.entities.registration.MobSpawnsInit;
import com.robertx22.world_of_exile.main.entities.registration.ModEntities;
import com.robertx22.world_of_exile.main.structures.BlackStoneTower;
import com.robertx22.world_of_exile.main.structures.LadderTower;
import com.robertx22.world_of_exile.main.structures.OnePieceSurfaceJigsaw;
import com.robertx22.world_of_exile.main.structures.StoneBrickTower;
import com.robertx22.world_of_exile.main.structures.base.StructureWrapper;
import com.robertx22.world_of_exile.mixins.BiomeAccessor;
import com.robertx22.world_of_exile.mixins.GenerationSettingsAccessor;
import com.robertx22.world_of_exile.world_gen.tower.TowerDestroyer;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CommonInit implements ModInitializer {
    public static MinecraftServer SERVER;

    private static List<StructureWrapper> STRUCTURES = new ArrayList<>();

    static void registerFeatures() {
        //  FEATURES = new ArrayList<>();

        // TODO  FEATURES.add(new Dungeon());
        STRUCTURES.add(new BlackStoneTower());
        STRUCTURES.add(new LadderTower());
        STRUCTURES.add(new StoneBrickTower());
        STRUCTURES.add(new OnePieceSurfaceJigsaw());

        STRUCTURES.forEach(x -> {
            x.init();
            x.register();
        });

    }

    public static void registerStructure(StructureWrapper wrap) {
        STRUCTURES.add(wrap);
    }

    @Override
    public void onInitialize() {

        ModProcessorLists.INSTANCE = new ModProcessorLists();
        ModBlocks.INSTANCE = new ModBlocks();
        ModItems.INSTANCE = new ModItems();

        ModEntities.INSTANCE = new ModEntities();
        ModEntityAttributes.register();

        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        if (!ModConfig.get().ENABLE_MOD) {
            System.out.println("Dungeons of Exile mod disabled as per config.");
        }

        ModStructurePieces.init();
        ModWorldGen.init();

        ModBiomes.INSTANCE = new ModBiomes();

        MobSpawnsInit.register();

        ServerTickEvents.END_WORLD_TICK.register(x -> TowerDestroyer.tickAll(x));

        ServerTickEvents.END_SERVER_TICK.register(new OnServerTick());

        registerFeatures();

        ServerWorldEvents.LOAD.register(new ServerWorldEvents.Load() {
            @Override
            public void onWorldLoad(MinecraftServer server, ServerWorld world) {
                STRUCTURES.forEach(x -> x.config.onWorldLoad(world, x.feature));
            }
        });

        MobEvents.register();

        ServerLifecycleEvents.SERVER_STARTING.register(s -> {

            CommonInit.SERVER = s;

            //BiomeModifications
            // can replace this but lambda hell





            /*

            BiomeModifications.addStructure(x -> {
                return ModConfig.get().DUNGEON.isAllowedIn(null, x.getBiome(), server.getRegistryManager());
            }, ModWorldGen.INSTANCE.DUNGEON_KEY);

            BiomeModifications.addStructure(x -> {
                return ModConfig.get().BLACKSTONE_TOWER.isAllowedIn(null, x.getBiome(), server.getRegistryManager());
            }, ModWorldGen.INSTANCE.BLANKSTONE_TOWER_KEY);

            BiomeModifications.addStructure(x -> {
                return ModConfig.get().STONE_BRICK_TOWER.isAllowedIn(null, x.getBiome(), server.getRegistryManager());
            }, ModWorldGen.INSTANCE.STONE_BRICK_TOWER_KEY);

            BiomeModificationImpl.INSTANCE.modifyBiomes((DynamicRegistryManager.Impl) s.getRegistryManager());


             */

            DynamicRegistryManager regManager = s.getRegistryManager();

            if (regManager.getOptionalMutable(Registry.BIOME_KEY)
                .isPresent()) {

                for (Biome biome : regManager.get(Registry.BIOME_KEY)) {

                    BiomeAccessor access = (BiomeAccessor) (Object) biome;
                    Map<Integer, List<StructureFeature<?>>> list = new HashMap<>(access.getStructures());
                    GenerationSettingsAccessor gen = (GenerationSettingsAccessor) biome.getGenerationSettings();
                    List<Supplier<ConfiguredStructureFeature<?, ?>>> setlist = new ArrayList<>(gen.getGSStructureFeatures());

                    STRUCTURES.forEach(x -> {
                        if (x.config.isAllowedIn(null, biome, regManager)) {
                            list.get(x.genStep.ordinal())
                                .add(x.feature);
                            setlist.add(() -> x.configuredFeature);
                        }
                    });

                    access.setStructures(list);
                    gen.setGSStructureFeatures(setlist);

                }
            }

            System.out.println("Added World of Exile structures to biomes");

        });

        System.out.println("World of Exile initialized.");
    }

}
