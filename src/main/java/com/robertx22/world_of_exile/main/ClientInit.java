package com.robertx22.world_of_exile.main;

import com.robertx22.world_of_exile.main.entities.registration.ModEntityRenders;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class ClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ModEntityRenders.register();

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.INSTANCE.HELL_PORTAL, RenderLayer.getTranslucent());

    }
}
