package com.robertx22.world_of_exile.entities.ai;

import com.robertx22.world_of_exile.entities.IShooter;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.world.World;

public class FireballShooterGoal extends ShootProjectileGoal {
    public FireballShooterGoal(MobEntity mob, IShooter shooter) {
        super(mob, shooter);
    }

    @Override
    public void shootProjectile(World world, MobEntity mob, double e, double f, double g, float h) {

        FireballEntity fireball = new FireballEntity(mob.world, mob, e + mob.getRandom()
            .nextGaussian() * (double) h, f, g + mob.getRandom()
            .nextGaussian() * (double) h);

        fireball.explosionPower = 2;
        fireball.updatePosition(fireball.getX(), mob.getBodyY(0.5D) + 0.5D, fireball.getZ());
        mob.world.spawnEntity(fireball);
    }
}
