package com.robertx22.world_of_exile.entities.ai;

import com.robertx22.world_of_exile.entities.IShooter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.EnumSet;

/// copied from blaze
public abstract class ShootProjectileGoal extends Goal {
    private final MobEntity mob;
    private int projectilesFired;
    private int projectileCooldown;
    private int targetNotVisibleTicks;
    IShooter shooter;

    public int goalCooldownWhenDone = 20 * 10;

    public abstract void shootProjectile(World world, MobEntity mob, double e, double f, double g, float h);

    public ShootProjectileGoal(MobEntity mob, IShooter shooter) {
        this.mob = mob;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        this.shooter = shooter;
    }

    @Override
    public boolean canStart() {

        if (shooter.getShootDelay() > 0) {
            return false;
        }
        LivingEntity livingEntity = this.mob.getTarget();
        return livingEntity != null && livingEntity.isAlive() && this.mob.canTarget(livingEntity);
    }

    @Override
    public void start() {
        this.projectilesFired = 0;
    }

    @Override
    public void stop() {
        //this.blaze.setFireActive(false);
        this.targetNotVisibleTicks = 0;
    }

    @Override
    public void tick() {
        --this.projectileCooldown;
        LivingEntity livingEntity = this.mob.getTarget();
        if (livingEntity != null) {
            boolean bl = this.mob.getVisibilityCache()
                .canSee(livingEntity);
            if (bl) {
                this.targetNotVisibleTicks = 0;
            } else {
                ++this.targetNotVisibleTicks;
            }

            double d = this.mob.squaredDistanceTo(livingEntity);
            if (d < 4.0D) {
                if (!bl) {
                    return;
                }

                if (this.projectileCooldown <= 0) {
                    this.projectileCooldown = 20;
                    this.mob.tryAttack(livingEntity);
                }

                this.mob.getMoveControl()
                    .moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0D);
            } else if (d < this.getFollowRange() * this.getFollowRange() && bl) {
                double e = livingEntity.getX() - this.mob.getX();
                double f = livingEntity.getBodyY(0.5D) - this.mob.getBodyY(0.5D);
                double g = livingEntity.getZ() - this.mob.getZ();
                if (this.projectileCooldown <= 0) {
                    ++this.projectilesFired;
                    if (this.projectilesFired == 1) {
                        this.projectileCooldown = 60;
                        //this.blaze.setFireActive(true);
                    } else if (this.projectilesFired <= 4) {
                        this.projectileCooldown = 6;
                    } else {
                        this.projectileCooldown = 100;
                        this.projectilesFired = 0;
                        //this.blaze.setFireActive(false);
                    }

                    if (this.projectilesFired > 1) {
                        float h = MathHelper.sqrt(MathHelper.sqrt(d)) * 0.5F;
                        if (!this.mob.isSilent()) {
                            this.mob.world.syncWorldEvent((PlayerEntity) null, 1018, this.mob.getBlockPos(), 0);
                        }

                        for (int i = 0; i < 1; ++i) {

                            this.shootProjectile(mob.world, mob, e, f, g, h);

                            if (projectilesFired > 3) {
                                shooter.setShootDelay(goalCooldownWhenDone);
                                stop();
                            }
                        }
                    }
                }

                this.mob.getLookControl()
                    .lookAt(livingEntity, 10.0F, 10.0F);
            } else if (this.targetNotVisibleTicks < 5) {
                this.mob.getMoveControl()
                    .moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0D);
            }

            super.tick();
        }
    }

    private double getFollowRange() {
        return this.mob.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
    }
}
