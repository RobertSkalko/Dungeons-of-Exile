package com.robertx22.world_of_exile.entities.mob;

import com.robertx22.world_of_exile.util.MobUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class InfernalBatEntity extends ModBatEntity {

    public InfernalBatEntity(EntityType<? extends VexEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void onAttacking(Entity target) {
        super.onAttacking(target);
        if (target instanceof LivingEntity) {
            LivingEntity en = (LivingEntity) target;
            en.setOnFireFor(3);
        }
    }

    @Override
    public void tick() {
        super.tick();

        MobUtils.particleInRadius(ParticleTypes.FLAME, this);
    }
}
