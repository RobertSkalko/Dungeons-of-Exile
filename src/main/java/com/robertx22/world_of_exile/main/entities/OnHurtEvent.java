package com.robertx22.world_of_exile.main.entities;

import net.minecraft.entity.LivingEntity;

public class OnHurtEvent {

    LivingEntity entity;
    LivingEntity attacker;

    public OnHurtEvent(LivingEntity entity, LivingEntity attacker) {
        this.entity = entity;
        this.attacker = attacker;
    }
}
