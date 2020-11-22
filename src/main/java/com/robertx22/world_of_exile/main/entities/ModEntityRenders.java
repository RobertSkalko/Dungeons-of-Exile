package com.robertx22.world_of_exile.main.entities;

import com.robertx22.world_of_exile.entities.renders.MySpriteRenderer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;

public class ModEntityRenders {

    public static void register() {

        MobManager.MAP.entrySet()
            .forEach(x -> {
                EntityRendererRegistry.INSTANCE.register(
                    x.getKey(),
                    (m, c) -> x.getValue()
                        .getRenderer()
                        .createRenderer(x.getValue()
                            .getRenderInfo(), m));

            });

        EntityRendererRegistry.INSTANCE.register(ModEntities.INSTANCE.FROST_PROJECTILE, (d, ctx) -> new MySpriteRenderer<>(d, MinecraftClient.getInstance()
            .getItemRenderer()));
    }
}
