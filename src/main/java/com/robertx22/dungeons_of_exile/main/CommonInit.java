package com.robertx22.dungeons_of_exile.main;

import com.robertx22.dungeons_of_exile.mixins.GenerationSettingsAccessor;
import com.robertx22.dungeons_of_exile.mobs.ai.FireGolemEntity;
import com.robertx22.dungeons_of_exile.world_gen.jigsaw.dungeon.DungeonPools;
import com.robertx22.dungeons_of_exile.world_gen.tower.TowerDestroyer;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.mixin.structure.BiomeAccessor;
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

        FabricDefaultAttributeRegistry.register(ModEntities.INSTANCE.FIRE_GOLEM, FireGolemEntity.createAttributes());

        ServerTickEvents.END_WORLD_TICK.register(x -> TowerDestroyer.tickAll(x));

        ServerLifecycleEvents.SERVER_STARTING.register(x -> {

            DynamicRegistryManager registryManager = x.getRegistryManager();

            if (registryManager.getOptional(Registry.BIOME_KEY)
                .isPresent()) {

                for (Biome biome : registryManager.get(Registry.BIOME_KEY)) {

                    if (biome.getCategory() == Biome.Category.NONE ||
                        biome.getCategory() == Biome.Category.OCEAN ||
                        biome.getCategory() == Biome.Category.NETHER ||
                        biome.getCategory() == Biome.Category.THEEND) {
                        continue;
                    }

                    BiomeAccessor access = (BiomeAccessor) (Object) biome;
                    Map<Integer, List<StructureFeature<?>>> list = access.getStructureLists();
                    list = new HashMap<>(list);
                    list.get(GenerationStep.Feature.SURFACE_STRUCTURES.ordinal())
                        .add(ModWorldGen.INSTANCE.DUNGEON);
                    list.get(GenerationStep.Feature.SURFACE_STRUCTURES.ordinal())
                        .add(ModWorldGen.INSTANCE.TOWER);

                    access.setStructureLists(list);

                    GenerationSettingsAccessor gen = (GenerationSettingsAccessor) biome.getGenerationSettings();
                    List<Supplier<ConfiguredStructureFeature<?, ?>>> setlist = new ArrayList<>(gen.getGSStructureFeatures());
                    setlist.add(() -> ModWorldGen.INSTANCE.CONFIG_DUNGEON);
                    setlist.add(() -> ModWorldGen.INSTANCE.CONFIG_TOWER);
                    gen.setGSStructureFeatures(setlist);

                }
            }
        });

        System.out.println("Dungeons of Exile initialized.");
    }

}
