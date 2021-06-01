package com.robertx22.world_of_exile.entities.mob.base;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.PounceAtTargetGoal;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class SlimeMob extends SlimeEntity {

    public static int START_SIZE = 5;

    public SlimeMob(EntityType<? extends SlimeEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {

        super.initGoals();

        this.goalSelector.add(1, new PounceAtTargetGoal(this, 0.4F));
    }

    // this makes slime fast and scary
    @Override
    public void setForwardSpeed(float forwardSpeed) {
        this.forwardSpeed = 1.5F * forwardSpeed;
    }

    @Override
    protected Identifier getLootTableId() {
        return this.getSize() == 1 ? this.getType()
            .getLootTableId() : LootTables.EMPTY;
    }

    @Override
    protected boolean canAttack() {
        return this.canMoveVoluntarily();
    }
}
