package com.robertx22.world_of_exile.main;

import com.robertx22.world_of_exile.mobs.renders.GolemRenderer;
import net.fabricmc.api.ClientModInitializer;

import static net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry.INSTANCE;

public class ClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        INSTANCE.register(ModEntities.INSTANCE.FIRE_GOLEM, (d, ctx) -> new GolemRenderer("fire", d));

    }
}
