package com.robertx22.world_of_exile.entities.renders;

import com.robertx22.world_of_exile.main.WOE;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.SpiderEyesFeatureRenderer;
import net.minecraft.client.render.entity.model.SpiderEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.util.Identifier;

public class ModSpiderRenderer extends MobEntityRenderer<SpiderEntity, SpiderEntityModel<SpiderEntity>> {
    private Identifier TEXTURE;

    public ModSpiderRenderer(EntityRenderDispatcher entityRenderDispatcher, String tex) {
        super(entityRenderDispatcher, new SpiderEntityModel<SpiderEntity>(), 0.25F);
        this.TEXTURE = new Identifier(WOE.MODID, "textures/entity/spider/" + tex + ".png");
        this.addFeature(new SpiderEyesFeatureRenderer(this));

    }

    @Override
    protected void scale(SpiderEntity caveSpiderEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale(1.2F, 1.2F, 1.2F);
    }

    @Override
    protected float getLyingAngle(SpiderEntity spiderEntity) {
        return 180.0F;
    }

    @Override
    public Identifier getTexture(SpiderEntity spiderEntity) {
        return TEXTURE;
    }
}
