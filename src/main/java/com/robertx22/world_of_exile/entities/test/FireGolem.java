package com.robertx22.world_of_exile.entities.test;

import com.robertx22.world_of_exile.entities.IShooter;
import com.robertx22.world_of_exile.entities.ai.FireballShooterGoal;
import com.robertx22.world_of_exile.main.entities.*;
import com.robertx22.world_of_exile.util.AttributeBuilder;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.PathAwareEntity;

public class FireGolem extends MobData {

    public FireGolem() {
        super("fire_golem");

        this.tags.add(MobTags.RANDOM_TOWER_BOSS);
    }

    @Override
    public MobType getMobType() {
        return MobType.GOLEM;
    }

    @Override
    public MobRenderInfo getRenderInfo() {
        return new MobRenderInfo("fire");
    }

    @Override
    public MobRenderer getRenderer() {
        return MobRenderer.GOLEM;
    }

    @Override
    public AttributeBuilder getAttributes() {
        return DefaultAttributes.GOLEM_BOSS.getDefault();
    }

    @Override
    public MobData.Size getSizeDimensions() {
        return Size.BIG_GOLEM;
    }

    @Override
    public Goals getDefaultGoals() {
        return Goals.MELEE;
    }

    @Override
    public void addCustomGoals(PathAwareEntity en, GoalSelector goalSelector, GoalSelector targetSelector) {
        goalSelector.add(1, new FireballShooterGoal(en, (IShooter) en));
    }

}
