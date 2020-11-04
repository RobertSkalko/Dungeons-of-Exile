package com.robertx22.world_of_exile.entities.mob;

import com.robertx22.library_of_exile.packets.defaults.EntityPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.network.Packet;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModSpiderEntity extends SpiderEntity {

    public ModSpiderEntity(EntityType<? extends SpiderEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 25)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3);
    }

    @Override
    public void tick() {
        super.tick();
        if (!world.isClient) {
            if (age % 60 == 0) {
                if (getAttacker() != null) {
                    // MobUtils.tryLeap(this, 3, 0.3F, getAttacker().getBlockPos());
                }
            }
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {

        return super.damage(source, amount);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntityPacket.createPacket(this);
    }

    @Override
    protected Identifier getLootTableId() {
        return EntityType.SPIDER.getLootTableId(); // TODO, add loot tables later
    }

}