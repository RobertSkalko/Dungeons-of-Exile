package com.robertx22.world_of_exile.entities.test;

import com.robertx22.world_of_exile.entities.on_special_spawns.OnSpecialOreGolemSpawn;
import com.robertx22.world_of_exile.main.entities.*;
import com.robertx22.world_of_exile.main.entities.spawn.LavaTurnToObsidianSpawner;
import com.robertx22.world_of_exile.util.AttributeBuilder;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.PathAwareEntity;

public class ObsidianGolem extends MobData {

    public ObsidianGolem() {
        super("obsidian_golem");

        this.spawnEvents.lavaTurnToObsidian.add(new LavaTurnToObsidianSpawner(2));
        this.spawnEvents.onSpecialSpawn.add(new OnSpecialOreGolemSpawn());

    }

    @Override
    public MobType getMobType() {
        return MobType.GOLEM;
    }

    @Override
    public MobRenderInfo getRenderInfo() {
        return new MobRenderInfo(1, "obsidian");
    }

    @Override
    public MobRenderer getRenderer() {
        return MobRenderer.GOLEM;
    }

    @Override
    public AttributeBuilder getAttributes() {
        return DefaultAttributes.GOLEM_BOSS.getDefault();
    }

    @Override
    public MobData.Size getSizeDimensions() {
        return Size.BIG_GOLEM;
    }

    @Override
    public Goals getDefaultGoals() {
        return Goals.MELEE;
    }

    @Override
    public void addCustomGoals(PathAwareEntity en, GoalSelector goalSelector, GoalSelector targetSelector) {

    }

}