package com.robertx22.world_of_exile.entities.mob_data;

import com.robertx22.world_of_exile.entities.IShooter;
import com.robertx22.world_of_exile.entities.ai.FrostProjectileShooterGoal;
import com.robertx22.world_of_exile.entities.mob.OnDeathDropKey;
import com.robertx22.world_of_exile.entities.on_attack.EffectOnAttack;
import com.robertx22.world_of_exile.main.entities.*;
import com.robertx22.world_of_exile.util.AttributeBuilder;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.PathAwareEntity;

public class SnowGolem extends MobData {

    public SnowGolem() {
        super("snow_golem");

        this.tags.add(MobTags.RANDOM_TOWER_BOSS);
        this.onAttackSomeoneEvents.add(new EffectOnAttack(StatusEffects.SLOWNESS, 25, 20 * 6));

        this.onDeathEvents.add(new OnDeathDropKey());
    }

    @Override
    public MobType getMobType() {
        return MobType.GOLEM;
    }

    @Override
    public MobRenderInfo getRenderInfo() {
        return new MobRenderInfo(0.9F, "snow");
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
        goalSelector.add(1, new FrostProjectileShooterGoal(en, (IShooter) en));
    }

}
