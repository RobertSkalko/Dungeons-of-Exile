package com.robertx22.world_of_exile.util;

import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;

public class AttributeBuilder {

    double health, moveSpeed, damage;

    double knockbackResist, followRange = 25;

    public AttributeBuilder(double health, double moveSpeed, double damage) {
        this.health = health;
        this.moveSpeed = moveSpeed;
        this.damage = damage;
    }

    public AttributeBuilder followRange(double followRange) {
        this.followRange = followRange;
        return this;
    }

    public AttributeBuilder knockbackResist(double knockbackResist) {
        this.knockbackResist = knockbackResist;
        return this;
    }

    public DefaultAttributeContainer.Builder build() {
        return MobEntity.createMobAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, health)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, moveSpeed)
            .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, knockbackResist)
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, followRange)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, damage);
    }

}
