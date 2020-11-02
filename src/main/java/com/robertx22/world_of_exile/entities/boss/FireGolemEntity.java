package com.robertx22.world_of_exile.entities.boss;

import com.robertx22.world_of_exile.entities.ai.FireballShooterGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class FireGolemEntity extends BaseGolemEntity {

    public FireGolemEntity(EntityType<? extends BaseGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void addExtraGoals() {
        this.goalSelector.add(1, new FireballShooterGoal(this, this));
    }
}
