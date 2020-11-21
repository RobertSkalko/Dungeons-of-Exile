package com.robertx22.world_of_exile.entities.boss;

import com.robertx22.world_of_exile.entities.ai.FireballShooterGoal;
import com.robertx22.world_of_exile.entities.mob.base.BaseGolem;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class FireGolemEntity extends BaseGolem {

    public FireGolemEntity(EntityType<? extends BaseGolem> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void addExtraGoals() {
        this.goalSelector.add(1, new FireballShooterGoal(this, this));
    }
}
