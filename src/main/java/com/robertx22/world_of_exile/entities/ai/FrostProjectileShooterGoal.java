package com.robertx22.world_of_exile.entities.ai;

import com.robertx22.world_of_exile.entities.IShooter;
import com.robertx22.world_of_exile.entities.projectile.FrostProjectileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class FrostProjectileShooterGoal extends ShootProjectileGoal {
    public FrostProjectileShooterGoal(MobEntity mob, IShooter shooter) {
        super(mob, shooter);
        this.goalCooldownWhenDone = 20 * 3;
    }

    @Override
    public void shootProjectile(World world, MobEntity mob, double e, double f, double g, float h) {
        FrostProjectileEntity projectile = new FrostProjectileEntity(mob.world, mob, e, f, g);

        projectile.updatePosition(projectile.getX(), mob.getBodyY(0.5D) + 0.5D, projectile.getZ());
        mob.world.spawnEntity(projectile);
    }

}
