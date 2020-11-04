package com.robertx22.world_of_exile.entities.mob;

import com.robertx22.world_of_exile.util.MobUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;

public class InfernalScorpionEntity extends ModSpiderEntity {

    public InfernalScorpionEntity(EntityType<? extends SpiderEntity> entityType, World world) {
        super(entityType, world);

    }

    @Override
    public void onAttacking(Entity target) {
        super.onAttacking(target);
        target.setOnFireFor(5);
    }

    @Override
    public void tick() {
        super.tick();

        MobUtils.particleInRadius(ParticleTypes.FLAME, this);
    }

    @Override
    public void onDeath(DamageSource source) {
        Explosion explosion = new Explosion(world, this, null, new ExplosionBehavior(), getX(), getY(), getZ(), 1, true, Explosion.DestructionType.BREAK);
        explosion.collectBlocksAndDamageEntities();
        explosion.affectWorld(true);
        super.onDeath(source);
    }

}
