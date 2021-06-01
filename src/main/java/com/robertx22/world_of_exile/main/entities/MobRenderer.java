package com.robertx22.world_of_exile.main.entities;

import com.robertx22.world_of_exile.entities.renders.GolemRenderer;
import com.robertx22.world_of_exile.entities.renders.ModSlimeRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;

public enum MobRenderer {
    SLIME {
        @Override
        public MobEntityRenderer createRenderer(MobRenderInfo info, EntityRenderDispatcher mana) {
            return new ModSlimeRenderer(info, mana);
        }
    },
    GOLEM {
        @Override
        public MobEntityRenderer createRenderer(MobRenderInfo info, EntityRenderDispatcher mana) {
            return new GolemRenderer(info, mana);
        }
    };

    public abstract MobEntityRenderer createRenderer(MobRenderInfo info, EntityRenderDispatcher mana);

}
