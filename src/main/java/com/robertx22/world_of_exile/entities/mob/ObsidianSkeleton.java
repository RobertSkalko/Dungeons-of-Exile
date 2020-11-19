package com.robertx22.world_of_exile.entities.mob;

import com.robertx22.library_of_exile.packets.defaults.EntityPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

public class ObsidianSkeleton extends SkeletonEntity {

    public ObsidianSkeleton(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntityPacket.createPacket(this);
    }

}
