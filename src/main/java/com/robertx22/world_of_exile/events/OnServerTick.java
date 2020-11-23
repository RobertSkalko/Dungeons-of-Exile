package com.robertx22.world_of_exile.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

public class OnServerTick implements ServerTickEvents.EndTick {
    static int ticks = 0;

    @Override
    public void onEndTick(MinecraftServer server) {

        ticks++;

        if (ticks % 40 == 0) {

        }

    }
}
