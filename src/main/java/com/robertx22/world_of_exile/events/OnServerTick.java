package com.robertx22.world_of_exile.events;

import com.robertx22.world_of_exile.main.ModItems;
import com.robertx22.world_of_exile.main.WOE;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class OnServerTick implements ServerTickEvents.EndTick {
    static int ticks = 0;

    @Override
    public void onEndTick(MinecraftServer server) {

        ticks++;

        if (ticks % 40 == 0) {

            ItemStack stack = new ItemStack(ModItems.INSTANCE.TP_BACK);

            server.getPlayerManager()
                .getPlayerList()
                .forEach(x -> {
                    if (x.world.getRegistryKey()
                        .getValue()
                        .getNamespace()
                        .equals(WOE.MODID)) {

                        if (!x.inventory.contains(stack)) {
                            x.giveItemStack(stack.copy());
                        }
                    }
                });

        }

    }
}
