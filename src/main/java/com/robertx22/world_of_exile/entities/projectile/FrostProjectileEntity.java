package com.robertx22.world_of_exile.entities.projectile;

import com.robertx22.library_of_exile.packets.defaults.EntityPacket;
import com.robertx22.world_of_exile.entities.renders.IMyRendersAsItem;
import com.robertx22.world_of_exile.main.ModEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.Packet;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class FrostProjectileEntity extends ThrownItemEntity implements IMyRendersAsItem {

    double xvel = 0;
    double yvel = 0;
    double zvel = 0;

    public FrostProjectileEntity(World world, LivingEntity owner, double x, double y, double z) {
        super(ModEntities.INSTANCE.FROST_PROJECTILE, owner.getX(), owner.getY(), owner.getZ(), world);
        this.setOwner(owner);
        this.setRotation(owner.pitch, owner.yaw);
        this.setNoGravity(true);

        this.xvel = x;
        this.yvel = y;
        this.zvel = z;
    }

    public FrostProjectileEntity(EntityType<FrostProjectileEntity> type, World world) {
        super(type, world);
    }

    public void tick() {
        super.tick();

        if (this.age > 20 * 7) {
            this.remove();
        }
        this.addVelocity(xvel * 0.001D, yvel * 0.001D, zvel * 0.001D);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SNOWBALL;
    }

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return (ParticleEffect) (itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntityPacket.createPacket(this);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for (int i = 0; i < 8; ++i) {
                this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        int i = 3;
        entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), (float) i);

        if (entity instanceof LivingEntity) {
            LivingEntity en = (LivingEntity) entity;
            en.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 3, 1));
        }

    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Items.SNOWBALL);
    }
}
