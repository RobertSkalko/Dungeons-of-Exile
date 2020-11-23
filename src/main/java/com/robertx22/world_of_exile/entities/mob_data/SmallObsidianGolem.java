package com.robertx22.world_of_exile.entities.mob_data;

import com.robertx22.world_of_exile.entities.IShooter;
import com.robertx22.world_of_exile.entities.ai.FireballShooterGoal;
import com.robertx22.world_of_exile.entities.on_attack.EffectOnAttack;
import com.robertx22.world_of_exile.main.entities.*;
import com.robertx22.world_of_exile.main.entities.spawn.LavaTurnToObsidianSpawner;
import com.robertx22.world_of_exile.util.AttributeBuilder;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.PathAwareEntity;

public class SmallObsidianGolem extends MobData {

    public SmallObsidianGolem() {
        super("small_obsidian_golem");

        this.spawnEvents.lavaTurnToObsidian.add(new LavaTurnToObsidianSpawner(2));

        this.onAttackSomeoneEvents.add(new EffectOnAttack(StatusEffects.SLOWNESS, 25, 20 * 6));
    }

    @Override
    public MobType getMobType() {
        return MobType.GOLEM;
    }

    @Override
    public MobRenderInfo getRenderInfo() {
        return new MobRenderInfo(0.5F, "obsidian");
    }

    @Override
    public MobRenderer getRenderer() {
        return MobRenderer.GOLEM;
    }

    @Override
    public AttributeBuilder getAttributes() {
        return DefaultAttributes.SMALL_GOLEM.getDefault();
    }

    @Override
    public MobData.Size getSizeDimensions() {
        return Size.SMALL_GOLEM;
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