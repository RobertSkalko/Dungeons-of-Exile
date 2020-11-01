package com.robertx22.world_of_exile.main;

import com.robertx22.world_of_exile.config.ModConfig;
import com.robertx22.world_of_exile.mixins.GenerationSettingsAccessor;
import com.robertx22.world_of_exile.mobs.ai.FireGolemEntity;
import com.robertx22.world_of_exile.world_gen.jigsaw.blackstone_tower.BlackStoneTowerPools;
import com.robertx22.world_of_exile.world_gen.jigsaw.dungeon.DungeonPools;
import com.robertx22.world_of_exile.world_gen.jigsaw.stone_brick_tower.StoneBrickTowerPools;
import com.robertx22.world_of_exile.world_gen.tower.TowerDestroyer;
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

    @Override
    public void onInitialize() {

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

        ModBiomes.INSTANCE = new ModBiomes();

        FabricDefaultAttributeRegistry.register(ModEntities.INSTANCE.FIRE_GOLEM, FireGolemEntity.createAttributes());

        ServerTickEvents.END_WORLD_TICK.register(x -> TowerDestroyer.tickAll(x));

        ServerWorldEvents.LOAD.register(new ServerWorldEvents.Load() {
            @Override
            public void onWorldLoad(MinecraftServer server, ServerWorld world) {

                ModConfig.get().BLACKSTONE_TOWER.onWorldLoad(world);
                ModConfig.get().STONE_BRICK_TOWER.onWorldLoad(world);

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

                    if (biome.getCategory() != Biome.Category.NETHER) {
                        list.get(GenerationStep.Feature.SURFACE_STRUCTURES.ordinal())
                            .add(ModWorldGen.INSTANCE.DUNGEON);
                    }
                    list.get(GenerationStep.Feature.SURFACE_STRUCTURES.ordinal())
                        .add(ModWorldGen.INSTANCE.STONE_BRICK_TOWER);
                    list.get(GenerationStep.Feature.SURFACE_STRUCTURES.ordinal())
                        .add(ModWorldGen.INSTANCE.BLACKSTONE_TOWER);

                    access.setStructureLists(list);

                    GenerationSettingsAccessor gen = (GenerationSettingsAccessor) biome.getGenerationSettings();
                    List<Supplier<ConfiguredStructureFeature<?, ?>>> setlist = new ArrayList<>(gen.getGSStructureFeatures());
                    if (biome.getCategory() != Biome.Category.NETHER) {
                        setlist.add(() -> ModWorldGen.INSTANCE.CONFIG_DUNGEON);
                    }
                    setlist.add(() -> ModWorldGen.INSTANCE.CONFIG_STONE_BRICK_TOWER);
                    setlist.add(() -> ModWorldGen.INSTANCE.CONFIG_BLACKSTONE_TOWER);
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
