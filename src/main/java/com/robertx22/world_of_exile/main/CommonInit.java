package com.robertx22.world_of_exile.main;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.world_of_exile.blocks.stargate.packets.RequestStargateTeleportPacket;
import com.robertx22.world_of_exile.config.ModConfig;
import com.robertx22.world_of_exile.events.OnServerTick;
import com.robertx22.world_of_exile.main.entities.MobSpawnsInit;
import com.robertx22.world_of_exile.main.entities.ModEntities;
import com.robertx22.world_of_exile.main.entities.ModEntityAttributes;
import com.robertx22.world_of_exile.mixins.GenerationSettingsAccessor;
import com.robertx22.world_of_exile.world_gen.jigsaw.blackstone_tower.BlackStoneTowerPools;
import com.robertx22.world_of_exile.world_gen.jigsaw.dungeon.DungeonPools;
import com.robertx22.world_of_exile.world_gen.jigsaw.ladder_tower.LadderTowerPools;
import com.robertx22.world_of_exile.world_gen.jigsaw.stone_brick_tower.StoneBrickTowerPools;
import com.robertx22.world_of_exile.world_gen.tower.TowerDestroyer;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.mixin.structure.BiomeAccessor;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CommonInit implements ModInitializer {

    public static MinecraftServer server;

    @Override
    public void onInitialize() {

        Packets.registerClientToServerPacket(new RequestStargateTeleportPacket());

        ModProcessors.INSTANCE = new ModProcessors();
        ModBlocks.INSTANCE = new ModBlocks();
        ModItems.INSTANCE = new ModItems();

        ModEntities.INSTANCE = new ModEntities();

        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        if (!ModConfig.get().ENABLE_MOD) {
            System.out.println("Dungeons of Exile mod disabled as per config.");
        }

        ModStructurePieces.init();
        ModWorldGen.init();
        DungeonPools.init();
        BlackStoneTowerPools.init();
        StoneBrickTowerPools.init();
        LadderTowerPools.init();

        ModBiomes.INSTANCE = new ModBiomes();

        ModEntityAttributes.register();

        MobSpawnsInit.register();

        ServerTickEvents.END_WORLD_TICK.register(x -> TowerDestroyer.tickAll(x));

        ServerTickEvents.END_SERVER_TICK.register(new OnServerTick());

        ServerWorldEvents.LOAD.register(new ServerWorldEvents.Load() {
            @Override
            public void onWorldLoad(MinecraftServer server, ServerWorld world) {

                ModConfig.get().BLACKSTONE_TOWER.onWorldLoad(world);
                ModConfig.get().STONE_BRICK_TOWER.onWorldLoad(world);
                ModConfig.get().DUNGEON.onWorldLoad(world);
                ModConfig.get().LADDER_TOWER.onWorldLoad(world);

            }
        });

        ServerLifecycleEvents.SERVER_STARTING.register(s -> {

            //BiomeModifications can replace this but lambda hell

            server = s;

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

            if (regManager.getOptional(Registry.BIOME_KEY)
                .isPresent()) {

                for (Biome biome : regManager.get(Registry.BIOME_KEY)) {

                    BiomeAccessor access = (BiomeAccessor) (Object) biome;
                    Map<Integer, List<StructureFeature<?>>> list = new HashMap<>(access.getStructureLists());
                    GenerationSettingsAccessor gen = (GenerationSettingsAccessor) biome.getGenerationSettings();
                    List<Supplier<ConfiguredStructureFeature<?, ?>>> setlist = new ArrayList<>(gen.getGSStructureFeatures());

                    if (ModConfig.get().DUNGEON.isAllowedIn(null, biome, regManager)) {
                        list.get(GenerationStep.Feature.SURFACE_STRUCTURES.ordinal())
                            .add(ModWorldGen.INSTANCE.DUNGEON);
                        setlist.add(() -> ModWorldGen.INSTANCE.CONFIG_DUNGEON);
                    }

                    if (ModConfig.get().STONE_BRICK_TOWER.isAllowedIn(null, biome, regManager)) {
                        list.get(GenerationStep.Feature.SURFACE_STRUCTURES.ordinal())
                            .add(ModWorldGen.INSTANCE.STONE_BRICK_TOWER);
                        setlist.add(() -> ModWorldGen.INSTANCE.CONFIG_STONE_BRICK_TOWER);

                    }
                    if (ModConfig.get().BLACKSTONE_TOWER.isAllowedIn(null, biome, regManager)) {
                        list.get(GenerationStep.Feature.SURFACE_STRUCTURES.ordinal())
                            .add(ModWorldGen.INSTANCE.BLACKSTONE_TOWER);
                        setlist.add(() -> ModWorldGen.INSTANCE.CONFIG_BLACKSTONE_TOWER);
                    }
                    if (ModConfig.get().LADDER_TOWER.isAllowedIn(null, biome, regManager)) {
                        list.get(GenerationStep.Feature.SURFACE_STRUCTURES.ordinal())
                            .add(ModWorldGen.INSTANCE.LADDER_TOWER);
                        setlist.add(() -> ModWorldGen.INSTANCE.CONFIG_LADDER_TOWER);
                    }

                    access.setStructureLists(list);
                    gen.setGSStructureFeatures(setlist);

                }
            }

            System.out.println("Added DOE structures to biomes");

        });

        System.out.println("Dungeons of Exile initialized.");
    }

}
