package com.robertx22.world_of_exile.main;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.world_of_exile.blocks.stargate.OpenStargateScreenPacket;
import com.robertx22.world_of_exile.blocks.stargate.StargateInfoPacket;
import com.robertx22.world_of_exile.entities.renders.GolemRenderer;
import com.robertx22.world_of_exile.entities.renders.ModBlazeRenderer;
import com.robertx22.world_of_exile.entities.renders.ModCreeperRenderer;
import com.robertx22.world_of_exile.entities.renders.MySpriteRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;

import static net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry.INSTANCE;

public class ClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        Packets.registerServerToClient(new StargateInfoPacket());
        Packets.registerServerToClient(new OpenStargateScreenPacket());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.INSTANCE.STARGATE, RenderLayer.getCutout());

        INSTANCE.register(ModEntities.INSTANCE.FIRE_GOLEM, (d, ctx) -> new GolemRenderer("fire", d));
        INSTANCE.register(ModEntities.INSTANCE.FROST_GOLEM, (d, ctx) -> new GolemRenderer("frost", d));

        INSTANCE.register(ModEntities.INSTANCE.CHARGING_CREEPER, (d, ctx) -> new ModCreeperRenderer(d, WOE.id("textures/entity/charging_creeper.png")));
        INSTANCE.register(ModEntities.INSTANCE.FROST_BLAZE, (d, ctx) -> new ModBlazeRenderer(d, WOE.id("textures/entity/frost_blaze.png")));

        INSTANCE.register(ModEntities.INSTANCE.FROST_PROJECTILE, (d, ctx) -> new MySpriteRenderer<>(d, MinecraftClient.getInstance()
            .getItemRenderer()));

    }
}
