package com.robertx22.world_of_exile.main;

import com.robertx22.world_of_exile.config.ModConfig;
import com.robertx22.world_of_exile.main.structures.StoneBrickTower;
import com.robertx22.world_of_exile.main.structures.base.StructureWrapper;
import com.robertx22.world_of_exile.world_gen.tower.TowerDestroyer;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;

public class CommonInit implements ModInitializer {
    public static MinecraftServer SERVER;

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

    @Override
    public void onInitialize() {

        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        if (!ModConfig.get().ENABLE_MOD) {
            System.out.println("Dungeons of Exile mod disabled as per config.");
            return;
        }

        ModProcessorLists.INSTANCE = new ModProcessorLists();
        ModBlocks.INSTANCE = new ModBlocks();
        ModItems.INSTANCE = new ModItems();

        ModWorldGen.init();

        ServerTickEvents.END_WORLD_TICK.register(x -> TowerDestroyer.tickAll(x));

        registerFeatures();

        ServerLifecycleEvents.SERVER_STARTING.register(s -> {
            CommonInit.SERVER = s;
        });

        System.out.println("Dungeons of Exile initialized.");
    }

}
