package com.robertx22.world_of_exile.entities.renders;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BlazeEntityModel;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModBlazeRenderer extends MobEntityRenderer<BlazeEntity, BlazeEntityModel<BlazeEntity>> {
    Identifier TEXTURE;

    public ModBlazeRenderer(EntityRenderDispatcher entityRenderDispatcher, Identifier tex) {
        super(entityRenderDispatcher, new BlazeEntityModel(), 0.5F);
        this.TEXTURE = tex;
    }

    @Override
    protected int getBlockLight(BlazeEntity blazeEntity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public Identifier getTexture(BlazeEntity blazeEntity) {
        return TEXTURE;
    }
}
