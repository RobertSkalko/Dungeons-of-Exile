package com.robertx22.world_of_exile.entities.mob;

import com.robertx22.world_of_exile.entities.mob.base.BaseGolem;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class RedstoneGolemEntity extends BaseGolem {

    public RedstoneGolemEntity(EntityType<? extends BaseGolem> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void addExtraGoals() {

    }
}
