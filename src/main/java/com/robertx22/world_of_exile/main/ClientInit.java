package com.robertx22.world_of_exile.main;

import com.robertx22.world_of_exile.mobs.renders.GolemRenderer;
import com.robertx22.world_of_exile.mobs.renders.ModCreeperRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;

import static net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry.INSTANCE;

public class ClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        INSTANCE.register(ModEntities.INSTANCE.FIRE_GOLEM, (d, ctx) -> new GolemRenderer("fire", d));
        INSTANCE.register(ModEntities.INSTANCE.CHARGING_CREEPER, (d, ctx) -> new ModCreeperRenderer(d, new Identifier(WOE.MODID, "textures/entity/charging_creeper.png")));

    }
}
