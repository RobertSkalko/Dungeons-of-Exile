package com.robertx22.world_of_exile.main.entities;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;

@FunctionalInterface
public interface MobRenderFactory {
    EntityRenderer<? extends Entity> create(EntityRenderDispatcher var1, MobRenderInfo info);
}
