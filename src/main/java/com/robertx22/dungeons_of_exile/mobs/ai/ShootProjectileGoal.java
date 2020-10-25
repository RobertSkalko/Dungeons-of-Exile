package com.robertx22.dungeons_of_exile.mobs.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.math.MathHelper;

import java.util.EnumSet;

/// copied from blaze
public class ShootProjectileGoal extends Goal {
    private final FireGolemEntity mob;
    private int fireballsFired;
    private int fireballCooldown;
    private int targetNotVisibleTicks;

    public ShootProjectileGoal(FireGolemEntity mob) {
        this.mob = mob;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {

        if (mob.shootDelay > 0) {
            return false;
        }
        LivingEntity livingEntity = this.mob.getTarget();
        return livingEntity != null && livingEntity.isAlive() && this.mob.canTarget(livingEntity);
    }

    @Override
    public void start() {
        this.fireballsFired = 0;
    }

    @Override
    public void stop() {
        //this.blaze.setFireActive(false);
        this.targetNotVisibleTicks = 0;
    }

    @Override
    public void tick() {
        --this.fireballCooldown;
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

                if (this.fireballCooldown <= 0) {
                    this.fireballCooldown = 20;
                    this.mob.tryAttack(livingEntity);
                }

                this.mob.getMoveControl()
                    .moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0D);
            } else if (d < this.getFollowRange() * this.getFollowRange() && bl) {
                double e = livingEntity.getX() - this.mob.getX();
                double f = livingEntity.getBodyY(0.5D) - this.mob.getBodyY(0.5D);
                double g = livingEntity.getZ() - this.mob.getZ();
                if (this.fireballCooldown <= 0) {
                    ++this.fireballsFired;
                    if (this.fireballsFired == 1) {
                        this.fireballCooldown = 60;
                        //this.blaze.setFireActive(true);
                    } else if (this.fireballsFired <= 4) {
                        this.fireballCooldown = 6;
                    } else {
                        this.fireballCooldown = 100;
                        this.fireballsFired = 0;
                        //this.blaze.setFireActive(false);
                    }

                    if (this.fireballsFired > 1) {
                        float h = MathHelper.sqrt(MathHelper.sqrt(d)) * 0.5F;
                        if (!this.mob.isSilent()) {
                            this.mob.world.syncWorldEvent((PlayerEntity) null, 1018, this.mob.getBlockPos(), 0);
                        }

                        for (int i = 0; i < 1; ++i) {
                            FireballEntity fireball = new FireballEntity(this.mob.world, this.mob, e + this.mob.getRandom()
                                .nextGaussian() * (double) h, f, g + this.mob.getRandom()
                                .nextGaussian() * (double) h);

                            fireball.explosionPower = 2;
                            fireball.updatePosition(fireball.getX(), this.mob.getBodyY(0.5D) + 0.5D, fireball.getZ());
                            this.mob.world.spawnEntity(fireball);

                            if (fireballsFired > 3) {
                                mob.shootDelay = 20 * 10;
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
