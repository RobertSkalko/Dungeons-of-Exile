package com.robertx22.world_of_exile.entities.mob;

import com.robertx22.world_of_exile.util.MobUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class FrostBatEntity extends ModBatEntity {

    public FrostBatEntity(EntityType<? extends VexEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void onAttacking(Entity target) {
        super.onAttacking(target);
        if (target instanceof LivingEntity) {
            LivingEntity en = (LivingEntity) target;
            en.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 1));
        }
    }

    @Override
    public void tick() {
        super.tick();

        MobUtils.particleInRadius(ParticleTypes.ITEM_SNOWBALL, this);
    }
}

