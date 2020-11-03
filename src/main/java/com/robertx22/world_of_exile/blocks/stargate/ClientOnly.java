package com.robertx22.world_of_exile.blocks.stargate;

import net.minecraft.client.MinecraftClient;

public class ClientOnly {

    public static void openStargateScreen() {
        MinecraftClient.getInstance()
            .openScreen(new StargateScreen());
    }
}
