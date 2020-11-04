package com.robertx22.world_of_exile.entities.renders.models;

import com.google.common.collect.ImmutableList;
import com.robertx22.world_of_exile.entities.mob.ModBatEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

// literally copied from vanilla just cus need different class
public class ModBatModel extends CompositeEntityModel<ModBatEntity> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart rightWingTip;
    private final ModelPart leftWingTip;

    public ModBatModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.head = new ModelPart(this, 0, 0);
        this.head.addCuboid(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F);
        ModelPart modelPart = new ModelPart(this, 24, 0);
        modelPart.addCuboid(-4.0F, -6.0F, -2.0F, 3.0F, 4.0F, 1.0F);
        this.head.addChild(modelPart);
        ModelPart modelPart2 = new ModelPart(this, 24, 0);
        modelPart2.mirror = true;
        modelPart2.addCuboid(1.0F, -6.0F, -2.0F, 3.0F, 4.0F, 1.0F);
        this.head.addChild(modelPart2);
        this.body = new ModelPart(this, 0, 16);
        this.body.addCuboid(-3.0F, 4.0F, -3.0F, 6.0F, 12.0F, 6.0F);
        this.body.setTextureOffset(0, 34)
            .addCuboid(-5.0F, 16.0F, 0.0F, 10.0F, 6.0F, 1.0F);
        this.rightWing = new ModelPart(this, 42, 0);
        this.rightWing.addCuboid(-12.0F, 1.0F, 1.5F, 10.0F, 16.0F, 1.0F);
        this.rightWingTip = new ModelPart(this, 24, 16);
        this.rightWingTip.setPivot(-12.0F, 1.0F, 1.5F);
        this.rightWingTip.addCuboid(-8.0F, 1.0F, 0.0F, 8.0F, 12.0F, 1.0F);
        this.leftWing = new ModelPart(this, 42, 0);
        this.leftWing.mirror = true;
        this.leftWing.addCuboid(2.0F, 1.0F, 1.5F, 10.0F, 16.0F, 1.0F);
        this.leftWingTip = new ModelPart(this, 24, 16);
        this.leftWingTip.mirror = true;
        this.leftWingTip.setPivot(12.0F, 1.0F, 1.5F);
        this.leftWingTip.addCuboid(0.0F, 1.0F, 0.0F, 8.0F, 12.0F, 1.0F);
        this.body.addChild(this.rightWing);
        this.body.addChild(this.leftWing);
        this.rightWing.addChild(this.rightWingTip);
        this.leftWing.addChild(this.leftWingTip);
    }

    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.head, this.body);
    }

    public void setAngles(ModBatEntity batEntity, float f, float g, float h, float i, float j) {

        this.head.pitch = j * 0.017453292F;
        this.head.yaw = i * 0.017453292F;
        this.head.roll = 0.0F;
        this.head.setPivot(0.0F, 0.0F, 0.0F);
        this.rightWing.setPivot(0.0F, 0.0F, 0.0F);
        this.leftWing.setPivot(0.0F, 0.0F, 0.0F);
        this.body.pitch = 0.7853982F + MathHelper.cos(h * 0.1F) * 0.15F;
        this.body.yaw = 0.0F;
        this.rightWing.yaw = MathHelper.cos(h * 1.3F) * 3.1415927F * 0.25F;
        this.leftWing.yaw = -this.rightWing.yaw;
        this.rightWingTip.yaw = this.rightWing.yaw * 0.5F;
        this.leftWingTip.yaw = -this.rightWing.yaw * 0.5F;

    }
}
