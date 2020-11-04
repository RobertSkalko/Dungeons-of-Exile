package com.robertx22.world_of_exile.main;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.world_of_exile.blocks.stargate.packets.OpenStargateScreenPacket;
import com.robertx22.world_of_exile.blocks.stargate.packets.StargateInfoPacket;
import com.robertx22.world_of_exile.main.entities.ModEntityRenders;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class ClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        Packets.registerServerToClient(new StargateInfoPacket());
        Packets.registerServerToClient(new OpenStargateScreenPacket());

        ModEntityRenders.register();

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.INSTANCE.STARGATE, RenderLayer.getCutout());

    }
}
