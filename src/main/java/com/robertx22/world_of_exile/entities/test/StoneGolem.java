package com.robertx22.world_of_exile.entities.test;

import com.google.common.collect.ImmutableSet;
import com.robertx22.world_of_exile.entities.on_special_spawns.OnSpecialOreGolemSpawn;
import com.robertx22.world_of_exile.main.entities.*;
import com.robertx22.world_of_exile.main.entities.spawn.PlayerBreakBlockSpawner;
import com.robertx22.world_of_exile.util.AttributeBuilder;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.tag.BlockTags;

public class StoneGolem extends MobData {

    public StoneGolem() {
        super("stone_golem");

        this.spawnEvents.breakBlock.add(new PlayerBreakBlockSpawner(1, ImmutableSet.of(BlockTags.BASE_STONE_OVERWORLD), ImmutableSet.of()));
        this.spawnEvents.onSpecialSpawn.add(new OnSpecialOreGolemSpawn(3));
    }

    @Override
    public MobType getMobType() {
        return MobType.GOLEM;
    }

    @Override
    public MobRenderInfo getRenderInfo() {
        return new MobRenderInfo(0.5F, "stone");
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
    public MobData.Size getSizeDimensions() {
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
