package com.robertx22.world_of_exile.entities.renders;

import com.robertx22.world_of_exile.entities.mob.base.GolemMob;
import com.robertx22.world_of_exile.entities.renders.models.ModGolemModel;
import com.robertx22.world_of_exile.main.WOE;
import com.robertx22.world_of_exile.main.entities.MobRenderInfo;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;

public class GolemRenderer<T extends GolemMob> extends MobEntityRenderer<T, ModGolemModel<T>> {
    private Identifier TEXTURE;

    float scale = 1;

    public GolemRenderer(MobRenderInfo info, EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new ModGolemModel(), 0.7F);
        TEXTURE = WOE.id("textures/entity/golem/" + info.texture + ".png");
        this.scale = info.scale;
    }

    @Override
    public Identifier getTexture(T ironGolemEntity) {
        return TEXTURE;
    }

    @Override
    protected void scale(T entity, MatrixStack matrices, float amount) {
        matrices.scale(scale, scale, scale);
    }

    @Override
    protected void setupTransforms(T ironGolemEntity, MatrixStack matrixStack, float f, float g, float h) {
        super.setupTransforms(ironGolemEntity, matrixStack, f, g, h);
        if ((double) ironGolemEntity.limbDistance >= 0.01D) {
            float i = 13.0F;
            float j = ironGolemEntity.limbAngle - ironGolemEntity.limbDistance * (1.0F - h) + 6.0F;
            float k = (Math.abs(j % 13.0F - 6.5F) - 3.25F) / 3.25F;
            matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(6.5F * k));
        }
    }
}

