package com.robertx22.world_of_exile.main.entities;

import com.robertx22.world_of_exile.entities.renders.*;
import com.robertx22.world_of_exile.main.WOE;
import net.minecraft.client.MinecraftClient;

import static net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry.INSTANCE;

public class ModEntityRenders {

    public static void register() {

        INSTANCE.register(ModEntities.INSTANCE.INFERNO_BAT, (d, ctx) -> new ModBatRender(d, "fire"));
        INSTANCE.register(ModEntities.INSTANCE.FROST_BAT, (d, ctx) -> new ModBatRender(d, "frost"));

        INSTANCE.register(ModEntities.INSTANCE.INFERNO_SCORPION, (d, ctx) -> new ModSpiderRenderer(d, "fire"));
        INSTANCE.register(ModEntities.INSTANCE.POISON_SPIDER, (d, ctx) -> new ModSpiderRenderer(d, "poison"));

        INSTANCE.register(ModEntities.INSTANCE.FIRE_GOLEM, (d, ctx) -> new GolemRenderer("fire", d));
        INSTANCE.register(ModEntities.INSTANCE.FROST_GOLEM, (d, ctx) -> new GolemRenderer("frost", d));

        INSTANCE.register(ModEntities.INSTANCE.OBSIDIAN_SKELETON, (d, ctx) -> new ModSkeletonRenderer(d, "obsidian"));
        INSTANCE.register(ModEntities.INSTANCE.DEMON_SKELETON, (d, ctx) -> new ModSkeletonRenderer(d, "demon"));

        INSTANCE.register(ModEntities.INSTANCE.OBSIDIAN_ENDERMAN, (d, ctx) -> new ModEndermanRenderer(d, "obsidian"));

        INSTANCE.register(ModEntities.INSTANCE.FROST_BLAZE, (d, ctx) -> new ModBlazeRenderer(d, WOE.id("textures/entity/frost_blaze.png")));

        INSTANCE.register(ModEntities.INSTANCE.FROST_PROJECTILE, (d, ctx) -> new MySpriteRenderer<>(d, MinecraftClient.getInstance()
            .getItemRenderer()));
    }
}
