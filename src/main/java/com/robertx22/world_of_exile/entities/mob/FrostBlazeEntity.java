package com.robertx22.world_of_exile.entities.mob;

import com.robertx22.library_of_exile.packets.defaults.EntityPacket;
import com.robertx22.world_of_exile.entities.IShooter;
import com.robertx22.world_of_exile.entities.ai.FrostProjectileShooterGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

public class FrostBlazeEntity extends BlazeEntity implements IShooter {

    public FrostBlazeEntity(EntityType<? extends BlazeEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(4, new FrostProjectileShooterGoal(this, this));
        this.goalSelector.add(5, new GoToWalkTargetGoal(this, 1.0D));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0D, 0.0F));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(1, (new RevengeGoal(this, new Class[0])).setGroupRevenge());
        this.targetSelector.add(2, new FollowTargetGoal(this, PlayerEntity.class, true));
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntityPacket.createPacket(this);
    }

    int shootDelay = 0;

    @Override
    public int getShootDelay() {
        return shootDelay;
    }

    @Override
    public void setShootDelay(int delay) {
        shootDelay = delay;
    }

    @Override
    public void tick() {
        super.tick();

        if (!world.isClient) {
            shootDelay--;
        }
    }
}
