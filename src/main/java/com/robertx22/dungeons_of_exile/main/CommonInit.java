package com.robertx22.dungeons_of_exile.main;

import com.robertx22.dungeons_of_exile.world_gen.jigsaw.dungeon.DungeonPools;
import com.robertx22.dungeons_of_exile.world_gen.tower.TowerDestroyer;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class CommonInit implements ModInitializer {

    @Override
    public void onInitialize() {

        AutoConfig.register(DungeonConfig.class, JanksonConfigSerializer::new);
        if (!DungeonConfig.get().ENABLE_MOD) {
            System.out.println("Dungeons of Exile mod disabled as per config.");
        }
        ModStuff.INSTANCE = new ModStuff();

        ModStructurePieces.init();
        ModWorldGen.init();
        DungeonPools.init();
        /*

        ServerWorldEvents.LOAD.register(
            (server, world) -> {

                Map<StructureFeature<?>, StructureConfig> map = world.getChunkManager()
                    .getChunkGenerator()
                    .getStructuresConfig()
                    .getStructures();

            }
        );

         */

        ServerTickEvents.END_WORLD_TICK.register(x -> TowerDestroyer.tickAll(x));

        System.out.println("Dungeons of Exile initialized.");
    }

}
