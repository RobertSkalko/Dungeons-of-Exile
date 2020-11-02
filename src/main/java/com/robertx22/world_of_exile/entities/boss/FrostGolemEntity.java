package com.robertx22.world_of_exile.entities.boss;

import com.robertx22.world_of_exile.entities.ai.FrostProjectileShooterGoal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;

public class FrostGolemEntity extends BaseGolemEntity {

    public FrostGolemEntity(EntityType<? extends BaseGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void addExtraGoals() {

        this.goalSelector.add(1, new FrostProjectileShooterGoal(this, this));
    }

    @Override
    public void dealDamage(LivingEntity attacker, Entity target) {
        super.dealDamage(attacker, target);

        if (target instanceof LivingEntity) {
            LivingEntity en = (LivingEntity) target;
            en.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 20 * 100, 1));
        }
    }
}
