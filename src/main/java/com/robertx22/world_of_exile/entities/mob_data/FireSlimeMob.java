package com.robertx22.world_of_exile.entities.mob_data;

import com.robertx22.world_of_exile.main.entities.*;
import com.robertx22.world_of_exile.util.AttributeBuilder;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.PathAwareEntity;

public class FireSlimeMob extends MobData {

    public FireSlimeMob() {
        super("fire_slime");
    }

    @Override
    public MobType getMobType() {
        return MobType.SLIME;
    }

    @Override
    public MobRenderInfo getRenderInfo() {
        return new MobRenderInfo(1F, "fire");
    }

    @Override
    public MobRenderer getRenderer() {
        return MobRenderer.SLIME;
    }

    @Override
    public AttributeBuilder getAttributes() {
        return DefaultAttributes.SLIME.getDefault();
    }

    @Override
    public MobData.Size getSizeDimensions() {
        return Size.SLIME;
    }

    @Override
    public Goals getDefaultGoals() {
        return Goals.NONE;
    }

    @Override
    public void addCustomGoals(PathAwareEntity en, GoalSelector goalSelector, GoalSelector targetSelector) {

    }

}
