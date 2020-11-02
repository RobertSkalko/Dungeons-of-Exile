package com.robertx22.world_of_exile.entities.renders;

import net.minecraft.client.render.entity.CreeperEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.util.Identifier;

public class ModCreeperRenderer extends CreeperEntityRenderer {
    Identifier tex;

    public ModCreeperRenderer(EntityRenderDispatcher entityRenderDispatcher, Identifier tex) {
        super(entityRenderDispatcher);
        this.tex = tex;
    }

    @Override
    public Identifier getTexture(CreeperEntity creeperEntity) {
        return tex;
    }
}
