package com.robertx22.dungeons_of_exile.main;

import com.robertx22.dungeons_of_exile.mixins.GenerationSettingsAccessor;
import com.robertx22.dungeons_of_exile.mixins.StructuresConfigAccessor;
import com.robertx22.dungeons_of_exile.mobs.ai.FireGolemEntity;
import com.robertx22.dungeons_of_exile.world_gen.jigsaw.big_tower.BigTowerPools;
import com.robertx22.dungeons_of_exile.world_gen.jigsaw.dungeon.DungeonPools;
import com.robertx22.dungeons_of_exile.world_gen.tower.TowerDestroyer;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.mixin.structure.BiomeAccessor;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CommonInit implements ModInitializer {

    @Override
    public void onInitialize() {

        ModStuff.INSTANCE = new ModStuff();
        ModItems.INSTANCE = new ModItems();

        ModEntities.INSTANCE = new ModEntities();

        AutoConfig.register(DungeonConfig.class, JanksonConfigSerializer::new);
        if (!DungeonConfig.get().ENABLE_MOD) {
            System.out.println("Dungeons of Exile mod disabled as per config.");
        }

        ModStructurePieces.init();
        ModWorldGen.init();
        DungeonPools.init();
        BigTowerPools.init();

        ModBiomes.INSTANCE = new ModBiomes();

        FabricDefaultAttributeRegistry.register(ModEntities.INSTANCE.FIRE_GOLEM, FireGolemEntity.createAttributes());

        ServerTickEvents.END_WORLD_TICK.register(x -> TowerDestroyer.tickAll(x));

        ServerWorldEvents.LOAD.register(new ServerWorldEvents.Load() {
            @Override
            public void onWorldLoad(MinecraftServer server, ServerWorld world) {

                Map<StructureFeature<?>, StructureConfig> tempMap = new HashMap<>(world.getChunkManager()
                    .getChunkGenerator()
                    .getStructuresConfig()
                    .getStructures());

                if (!world.getRegistryKey()
                    .getValue()
                    .toString()
                    .equals(new Identifier(Ref.MODID, "blackstone_dim").toString())) {
                    tempMap.remove(ModWorldGen.INSTANCE.BIGTOWER);
                } else {
                    tempMap.put(ModWorldGen.INSTANCE.BIGTOWER, new StructureConfig(8, 0, 578235));
                }

                StructuresConfigAccessor acc =
                    (StructuresConfigAccessor) world.getChunkManager()
                        .getChunkGenerator()
                        .getStructuresConfig();

                acc.setGSStructureFeatures(tempMap);

            }
        });

        ServerLifecycleEvents.SERVER_STARTING.register(x -> {

            DynamicRegistryManager registryManager = x.getRegistryManager();

            int num = 0;

            if (registryManager.getOptional(Registry.BIOME_KEY)
                .isPresent()) {

                for (Biome biome : registryManager.get(Registry.BIOME_KEY)) {

                    if (biome.getCategory() == Biome.Category.NONE ||
                        biome.getCategory() == Biome.Category.OCEAN ||
                        biome.getCategory() == Biome.Category.THEEND) {
                        continue;
                    }
                    num++;

                    BiomeAccessor access = (BiomeAccessor) (Object) biome;
                    Map<Integer, List<StructureFeature<?>>> list = access.getStructureLists();
                    list = new HashMap<>(list);
                    list.get(GenerationStep.Feature.SURFACE_STRUCTURES.ordinal())
                        .add(ModWorldGen.INSTANCE.DUNGEON);
                    list.get(GenerationStep.Feature.SURFACE_STRUCTURES.ordinal())
                        .add(ModWorldGen.INSTANCE.TOWER);
                    list.get(GenerationStep.Feature.SURFACE_STRUCTURES.ordinal())
                        .add(ModWorldGen.INSTANCE.BIGTOWER);

                    access.setStructureLists(list);

                    GenerationSettingsAccessor gen = (GenerationSettingsAccessor) biome.getGenerationSettings();
                    List<Supplier<ConfiguredStructureFeature<?, ?>>> setlist = new ArrayList<>(gen.getGSStructureFeatures());
                    setlist.add(() -> ModWorldGen.INSTANCE.CONFIG_DUNGEON);
                    setlist.add(() -> ModWorldGen.INSTANCE.CONFIG_TOWER);
                    setlist.add(() -> ModWorldGen.INSTANCE.CONFIG_BIGTOWER);
                    gen.setGSStructureFeatures(setlist);

                }
            }

            if (num == 0) {
                System.out.print("Didn't find any biomes to add Dungeons of Exile structures to, means mod is broken!");
            }

            System.out.println("Added DOE structures to " + num + " biomes");

        });

        System.out.println("Dungeons of Exile initialized.");
    }

}
