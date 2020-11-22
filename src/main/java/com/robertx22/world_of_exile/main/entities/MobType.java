package com.robertx22.world_of_exile.main.entities;

import com.robertx22.world_of_exile.entities.mob.base.GolemMob;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;

public enum MobType {

    GOLEM(GolemMob::new);

    <T extends MobEntity> MobType(EntityType.EntityFactory<T> factory) {
        this.factory = factory;
    }

    EntityType.EntityFactory factory;
}
