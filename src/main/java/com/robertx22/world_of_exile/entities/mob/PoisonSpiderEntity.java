package com.robertx22.world_of_exile.entities.mob;

import com.robertx22.library_of_exile.packets.particles.ParticleEnum;
import com.robertx22.library_of_exile.packets.particles.ParticlePacketData;
import com.robertx22.world_of_exile.util.MobUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class PoisonSpiderEntity extends ModSpiderEntity {

    public PoisonSpiderEntity(EntityType<? extends SpiderEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void onAttacking(Entity target) {
        super.onAttacking(target);
    }

    @Override
    public void tick() {
        super.tick();

        MobUtils.particleInRadius(ParticleTypes.ITEM_SLIME, this);
    }

    @Override
    public void onDeath(DamageSource source) {

        ParticleEnum.sendToClients(this,
            new ParticlePacketData(getPos(), ParticleEnum.AOE).type(ParticleTypes.ITEM_SLIME)
                .amount(100)
                .radius(2)
        );

        world.getOtherEntities(this, this.getBoundingBox()
            .expand(2), x -> x instanceof PlayerEntity)
            .forEach(e -> {

                PlayerEntity p = (PlayerEntity) e;
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 1));
            });

        super.onDeath(source);
    }

}
