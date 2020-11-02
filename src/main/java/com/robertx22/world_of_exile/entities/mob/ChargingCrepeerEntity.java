package com.robertx22.world_of_exile.entities.mob;

import com.robertx22.library_of_exile.packets.defaults.EntityPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

public class ChargingCrepeerEntity extends CreeperEntity {
    public ChargingCrepeerEntity(EntityType<? extends CreeperEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntityPacket.createPacket(this);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35D)
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 5);
    }

    @Override
    public int getFuseSpeed() {
        return super.getFuseSpeed() * 2;
    }

}
