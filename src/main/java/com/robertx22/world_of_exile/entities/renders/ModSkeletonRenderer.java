package com.robertx22.world_of_exile.entities.renders;

import com.robertx22.world_of_exile.main.WOE;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.util.Identifier;

public class ModSkeletonRenderer extends BipedEntityRenderer<AbstractSkeletonEntity, SkeletonEntityModel<AbstractSkeletonEntity>> {
    private Identifier TEXTURE;

    public ModSkeletonRenderer(EntityRenderDispatcher entityRenderDispatcher, String tex) {
        super(entityRenderDispatcher, new SkeletonEntityModel(), 0.5F);
        this.addFeature(new ArmorFeatureRenderer(this, new SkeletonEntityModel(0.5F, true), new SkeletonEntityModel(1.0F, true)));
        this.TEXTURE = new Identifier(WOE.MODID, "textures/entity/skeleton/" + tex + ".png");
    }

    @Override
    public Identifier getTexture(AbstractSkeletonEntity abstractSkeletonEntity) {
        return TEXTURE;
    }
}
