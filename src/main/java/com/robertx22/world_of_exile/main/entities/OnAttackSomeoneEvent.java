package com.robertx22.world_of_exile.main.entities;

import net.minecraft.entity.LivingEntity;

public class OnAttackSomeoneEvent {

    public LivingEntity entity;
    public LivingEntity victim;

    public OnAttackSomeoneEvent(LivingEntity entity, LivingEntity victim) {
        this.entity = entity;
        this.victim = victim;
    }
}
