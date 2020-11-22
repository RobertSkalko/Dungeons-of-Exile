package com.robertx22.world_of_exile.entities.test;

import com.google.common.collect.ImmutableSet;
import com.robertx22.world_of_exile.main.entities.*;
import com.robertx22.world_of_exile.main.entities.spawn.PlayerBreakBlockSpawner;
import com.robertx22.world_of_exile.util.AttributeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.PathAwareEntity;

public class IronOreGolem extends MobData {

    public IronOreGolem() {
        super("iron_ore_golem");

        this.spawnEvents.breakBlock.add(new PlayerBreakBlockSpawner(50, ImmutableSet.of(), ImmutableSet.of(Blocks.IRON_ORE)));

    }

    @Override
    public MobType getMobType() {
        return MobType.GOLEM;
    }

    @Override
    public MobRenderInfo getRenderInfo() {
        return new MobRenderInfo(0.5F, "iron");
    }

    @Override
    public MobRenderer getRenderer() {
        return MobRenderer.GOLEM;
    }

    @Override
    public AttributeBuilder getAttributes() {
        return DefaultAttributes.SMALL_GOLEM.getDefault();
    }

    @Override
    public Size getSizeDimensions() {
        return Size.SMALL_GOLEM;
    }

    @Override
    public Goals getDefaultGoals() {
        return Goals.MELEE;
    }

    @Override
    public void addCustomGoals(PathAwareEntity en, GoalSelector goalSelector, GoalSelector targetSelector) {

    }

}
