package com.robertx22.world_of_exile.entities.renders;

import com.robertx22.world_of_exile.main.WOE;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.EndermanBlockFeatureRenderer;
import net.minecraft.client.render.entity.feature.EndermanEyesFeatureRenderer;
import net.minecraft.client.render.entity.model.EndermanEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class ModEndermanRenderer extends MobEntityRenderer<EndermanEntity, EndermanEntityModel<EndermanEntity>> {
    private final Identifier TEXTURE;
    private final Random random = new Random();

    public ModEndermanRenderer(EntityRenderDispatcher entityRenderDispatcher, String tex) {
        super(entityRenderDispatcher, new EndermanEntityModel(0.0F), 0.5F);
        this.addFeature(new EndermanEyesFeatureRenderer(this));
        this.addFeature(new EndermanBlockFeatureRenderer(this));
        this.TEXTURE = new Identifier(WOE.MODID, "textures/entity/enderman/" + tex + ".png");

    }

    @Override
    public void render(EndermanEntity endermanEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        BlockState blockState = endermanEntity.getCarriedBlock();
        EndermanEntityModel<EndermanEntity> endermanEntityModel = (EndermanEntityModel) this.getModel();
        endermanEntityModel.carryingBlock = blockState != null;
        endermanEntityModel.angry = endermanEntity.isAngry();
        super.render(endermanEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Vec3d getPositionOffset(EndermanEntity endermanEntity, float f) {
        if (endermanEntity.isAngry()) {
            double d = 0.02D;
            return new Vec3d(this.random.nextGaussian() * 0.02D, 0.0D, this.random.nextGaussian() * 0.02D);
        } else {
            return super.getPositionOffset(endermanEntity, f);
        }
    }

    @Override
    public Identifier getTexture(EndermanEntity endermanEntity) {
        return TEXTURE;
    }
}
