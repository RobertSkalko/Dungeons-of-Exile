package com.robertx22.world_of_exile.entities.mob;

import com.robertx22.library_of_exile.packets.defaults.EntityPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.EnumSet;

public abstract class ModBatEntity extends VexEntity {

    public ModBatEntity(EntityType<? extends VexEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntityPacket.createPacket(this);
    }

    @Override
    protected void initGoals() {

        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(4, new com.robertx22.world_of_exile.entities.mob.ModBatEntity.ChargeTargetGoal());
        this.goalSelector.add(8, new com.robertx22.world_of_exile.entities.mob.ModBatEntity.LookAtTargetGoal());
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
        this.targetSelector.add(3, new FollowTargetGoal(this, PlayerEntity.class, true));
    }

    class ChargeTargetGoal extends Goal {
        public ChargeTargetGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            if (ModBatEntity.this.getTarget() != null && !ModBatEntity.this.getMoveControl()
                .isMoving() && ModBatEntity.this.random.nextInt(7) == 0) {
                return ModBatEntity.this.squaredDistanceTo(ModBatEntity.this.getTarget()) > 4.0D;
            } else {
                return false;
            }
        }

        @Override
        public boolean shouldContinue() {
            return ModBatEntity.this.getMoveControl()
                .isMoving() && ModBatEntity.this.isCharging() && ModBatEntity.this.getTarget() != null && ModBatEntity.this.getTarget()
                .isAlive();
        }

        @Override
        public void start() {
            LivingEntity livingEntity = ModBatEntity.this.getTarget();
            Vec3d vec3d = livingEntity.getCameraPosVec(1.0F);
            ModBatEntity.this.moveControl.moveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
            ModBatEntity.this.setCharging(true);
            ModBatEntity.this.playSound(SoundEvents.ENTITY_VEX_CHARGE, 1.0F, 1.0F);
        }

        @Override
        public void stop() {
            ModBatEntity.this.setCharging(false);
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = ModBatEntity.this.getTarget();
            if (ModBatEntity.this.getBoundingBox()
                .expand(0.8D)
                .intersects(livingEntity.getBoundingBox())) {
                ModBatEntity.this.tryAttack(livingEntity);
                ModBatEntity.this.setCharging(false);
            } else {
                double d = ModBatEntity.this.squaredDistanceTo(livingEntity);
                if (d < 9.0D) {
                    Vec3d vec3d = livingEntity.getCameraPosVec(1.0F);
                    ModBatEntity.this.moveControl.moveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
                }
            }

        }
    }

    class LookAtTargetGoal extends Goal {
        public LookAtTargetGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            return !ModBatEntity.this.getMoveControl()
                .isMoving() && ModBatEntity.this.random.nextInt(7) == 0;
        }

        @Override
        public boolean shouldContinue() {
            return false;
        }

        @Override
        public void tick() {
            BlockPos blockPos = ModBatEntity.this.getBounds();
            if (blockPos == null) {
                blockPos = ModBatEntity.this.getBlockPos();
            }

            for (int i = 0; i < 3; ++i) {
                BlockPos blockPos2 = blockPos.add(ModBatEntity.this.random.nextInt(15) - 7, ModBatEntity.this.random.nextInt(11) - 5, ModBatEntity.this.random.nextInt(15) - 7);
                if (ModBatEntity.this.world.isAir(blockPos2)) {
                    ModBatEntity.this.moveControl.moveTo((double) blockPos2.getX() + 0.5D, (double) blockPos2.getY() + 0.5D, (double) blockPos2.getZ() + 0.5D, 0.25D);
                    if (ModBatEntity.this.getTarget() == null) {
                        ModBatEntity.this.getLookControl()
                            .lookAt((double) blockPos2.getX() + 0.5D, (double) blockPos2.getY() + 0.5D, (double) blockPos2.getZ() + 0.5D, 180.0F, 20.0F);
                    }
                    break;
                }
            }

        }
    }
}
