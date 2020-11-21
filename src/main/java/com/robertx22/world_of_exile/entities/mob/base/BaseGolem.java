package com.robertx22.world_of_exile.entities.mob.base;

import com.robertx22.library_of_exile.packets.defaults.EntityPacket;
import com.robertx22.world_of_exile.entities.IShooter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.UUID;

public abstract class BaseGolem extends GolemEntity implements IShooter {

    private int attackTicksLeft;
    private int lookingAtVillagerTicksLeft;
    private int angerTime;
    private UUID angryAt;

    public BaseGolem(EntityType<? extends BaseGolem> entityType, World world) {
        super(entityType, world);
        this.stepHeight = 1.0F;
    }

    protected void initGoals() {
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, true));
        this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));
        this.goalSelector.add(2, new WanderAroundPointOfInterestGoal(this, 0.6D, false));
        this.goalSelector.add(4, new IronGolemWanderAroundGoal(this, 0.6D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(2, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(4, new UniversalAngerGoal(this, false));

        addExtraGoals();
    }

    public abstract void addExtraGoals();

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }

    public int shootDelay = 0;

    @Override
    public void tick() {
        super.tick();

        if (!world.isClient) {
            if (this.age % 20 == 0) {
                float max = getMaxHealth();
                this.heal(max * 0.0001F);
            }

            shootDelay--;

        }
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntityPacket.createPacket(this);
    }

    public static DefaultAttributeContainer.Builder createBossAttributes() {
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 100)
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 125)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.27D)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5)
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 60)
            .add(EntityAttributes.GENERIC_ARMOR, 1);

    }

    public static DefaultAttributeContainer.Builder createBabyIronGolemAttributes() {
        return MobEntity.createMobAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 25)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
            .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7);
    }

    @Override
    protected int getNextAirUnderwater(int air) {
        return air;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.attackTicksLeft > 0) {
            --this.attackTicksLeft;
        }

        if (this.lookingAtVillagerTicksLeft > 0) {
            --this.lookingAtVillagerTicksLeft;
        }

        if (squaredHorizontalLength(this.getVelocity()) > 2.500000277905201E-7D && this.random.nextInt(5) == 0) {
            int i = MathHelper.floor(this.getX());
            int j = MathHelper.floor(this.getY() - 0.20000000298023224D);
            int k = MathHelper.floor(this.getZ());
            BlockState blockState = this.world.getBlockState(new BlockPos(i, j, k));
            if (!blockState.isAir()) {
                this.world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), this.getX() + ((double) this.random.nextFloat() - 0.5D) * (double) this.getWidth(), this.getY() + 0.1D, this.getZ() + ((double) this.random.nextFloat() - 0.5D) * (double) this.getWidth(), 4.0D * ((double) this.random.nextFloat() - 0.5D), 0.5D, ((double) this.random.nextFloat() - 0.5D) * 4.0D);
            }
        }

    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);

    }

    private float getAttackDamage() {
        return (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
    }

    @Override
    public boolean tryAttack(Entity target) {
        this.attackTicksLeft = 10;
        this.world.sendEntityStatus(this, (byte) 4);
        float f = this.getAttackDamage();
        float g = (int) f > 0 ? f / 2.0F + (float) this.random.nextInt((int) f) : f;
        boolean bl = target.damage(DamageSource.mob(this), g);
        if (bl) {
            target.setVelocity(target.getVelocity()
                .add(0.0D, 0.4000000059604645D, 0.0D));
            this.dealDamage(this, target);
        }

        this.playSound(SoundEvents.ENTITY_IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        return bl;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 4) {
            this.attackTicksLeft = 10;
            this.playSound(SoundEvents.ENTITY_IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        } else if (status == 11) {
            this.lookingAtVillagerTicksLeft = 400;
        } else if (status == 34) {
            this.lookingAtVillagerTicksLeft = 0;
        } else {
            super.handleStatus(status);
        }

    }

    @Environment(EnvType.CLIENT)
    public int getAttackTicksLeft() {
        return this.attackTicksLeft;
    }

    public void setLookingAtVillager(boolean lookingAtVillager) {
        if (lookingAtVillager) {
            this.lookingAtVillagerTicksLeft = 400;
            this.world.sendEntityStatus(this, (byte) 11);
        } else {
            this.lookingAtVillagerTicksLeft = 0;
            this.world.sendEntityStatus(this, (byte) 34);
        }

    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_IRON_GOLEM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_IRON_GOLEM_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_IRON_GOLEM_STEP, 1.0F, 1.0F);
    }

    @Environment(EnvType.CLIENT)
    public int getLookingAtVillagerTicks() {
        return this.lookingAtVillagerTicksLeft;
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
    }

    public boolean canSpawn(WorldView world) {
        BlockPos blockPos = this.getBlockPos();
        BlockPos blockPos2 = blockPos.down();
        BlockState blockState = world.getBlockState(blockPos2);
        if (!blockState.hasSolidTopSurface(world, blockPos2, this)) {
            return false;
        } else {
            for (int i = 1; i < 3; ++i) {
                BlockPos blockPos3 = blockPos.up(i);
                BlockState blockState2 = world.getBlockState(blockPos3);
                if (!SpawnHelper.isClearForSpawn(world, blockPos3, blockState2, blockState2.getFluidState(), EntityType.IRON_GOLEM)) {
                    return false;
                }
            }

            return SpawnHelper.isClearForSpawn(world, blockPos, world.getBlockState(blockPos), Fluids.EMPTY.getDefaultState(), EntityType.IRON_GOLEM) && world.intersectsEntities(this);
        }
    }

    @Environment(EnvType.CLIENT)
    public Vec3d method_29919() {
        return new Vec3d(0.0D, (double) (0.875F * this.getStandingEyeHeight()), (double) (this.getWidth() * 0.4F));
    }

    @Override
    protected boolean isDisallowedInPeaceful() {
        return true;
    }

    @Override
    public boolean canUsePortals() {
        return false;
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return this.world.getDifficulty() == Difficulty.PEACEFUL;
    }

    @Override
    public int getShootDelay() {
        return shootDelay;
    }

    @Override
    public void setShootDelay(int delay) {
        this.shootDelay = delay;
    }

}
