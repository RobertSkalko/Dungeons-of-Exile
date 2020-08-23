package com.robertx22.dungeons_of_exile.main;

import com.robertx22.dungeons_of_exile.world_gen.jigsaw.dungeon.DungeonPools;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class CommonInit implements ModInitializer {

    @Override
    public void onInitialize() {

        AutoConfig.register(DungeonConfig.class, JanksonConfigSerializer::new);
        if (!DungeonConfig.get().ENABLE_MOD) {
            System.out.println("Dungeons of Exile mod disabled as per config.");
        }
        ModStuff.INSTANCE = new ModStuff();
        ModWorldGen.init();
        DungeonPools.init();

        System.out.println("Dungeons of Exile initialized.");
    }

}
