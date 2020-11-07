package com.robertx22.world_of_exile.entities.boss;

import com.robertx22.library_of_exile.packets.defaults.EntityPacket;
import com.robertx22.world_of_exile.entities.IShooter;
import com.robertx22.world_of_exile.main.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public abstract class BaseGolemEntity extends IronGolemEntity implements IShooter {

    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 100)
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 125)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.27D)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5)
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 60)
            .add(EntityAttributes.GENERIC_ARMOR, 1);

    }

    public BaseGolemEntity(EntityType<? extends BaseGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean shouldAngerAt(LivingEntity entity) {
        return entity instanceof PlayerEntity;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntityPacket.createPacket(this);
    }

    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        return true;
    }

    public int shootDelay = 0;

    @Override
    protected void initGoals() {

        addExtraGoals();

        this.goalSelector.add(2, new MeleeAttackGoal(this, 1, true));
        this.goalSelector.add(3, new WanderNearTargetGoal(this, 0.9D, 32.0F));
        this.goalSelector.add(5, new IronGolemLookGoal(this));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 20));
        this.goalSelector.add(8, new LookAroundGoal(this));

        this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));

    }

    public abstract void addExtraGoals();

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
    public void onDeath(DamageSource source) {
        this.dropStack(new ItemStack(ModItems.INSTANCE.SILVER_KEY), 1);
        super.onDeath(source);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!(source.getAttacker() instanceof PlayerEntity)) {
            return false; // dont get damaged by non player attacks
        }
        return super.damage(source, amount);
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


