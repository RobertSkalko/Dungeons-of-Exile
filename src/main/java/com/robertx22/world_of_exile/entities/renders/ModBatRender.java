package com.robertx22.world_of_exile.entities.renders;

import com.robertx22.world_of_exile.entities.mob.ModBatEntity;
import com.robertx22.world_of_exile.entities.renders.models.ModBatModel;
import com.robertx22.world_of_exile.main.WOE;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class ModBatRender extends MobEntityRenderer<ModBatEntity, ModBatModel> {
    private Identifier TEXTURE;

    public ModBatRender(EntityRenderDispatcher entityRenderDispatcher, String tex) {
        super(entityRenderDispatcher, new ModBatModel(), 0.25F);
        this.TEXTURE = new Identifier(WOE.MODID, "textures/entity/bat/" + tex + ".png");
    }

    @Override
    public Identifier getTexture(ModBatEntity batEntity) {
        return TEXTURE;
    }

    @Override
    protected void scale(ModBatEntity batEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale(1.2F, 1.2F, 1.2F);
    }

    @Override
    protected void setupTransforms(ModBatEntity batEntity, MatrixStack matrixStack, float f, float g, float h) {

        matrixStack.translate(0.0D, (double) (MathHelper.cos(f * 0.3F) * 0.1F), 0.0D);

        super.setupTransforms(batEntity, matrixStack, f, g, h);
    }
}
