package com.robertx22.world_of_exile.entities.mob_data;

import com.robertx22.world_of_exile.entities.IShooter;
import com.robertx22.world_of_exile.entities.ai.FireballShooterGoal;
import com.robertx22.world_of_exile.entities.mob.OnDeathDropKey;
import com.robertx22.world_of_exile.entities.on_attack.EffectOnAttack;
import com.robertx22.world_of_exile.entities.on_special_spawns.OnBigMobSpawnRemoveBlocksAround;
import com.robertx22.world_of_exile.main.entities.*;
import com.robertx22.world_of_exile.main.entities.spawn.LavaTurnToObsidianSpawner;
import com.robertx22.world_of_exile.util.AttributeBuilder;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.PathAwareEntity;

public class ObsidianGolem extends MobData {

    public ObsidianGolem() {
        super("obsidian_golem");

        this.tags.add(MobTags.RANDOM_TOWER_BOSS);

        this.spawnEvents.lavaTurnToObsidian.add(new LavaTurnToObsidianSpawner(0.025F));
        this.spawnEvents.onSpecialSpawn.add(new OnBigMobSpawnRemoveBlocksAround());

        this.onAttackSomeoneEvents.add(new EffectOnAttack(StatusEffects.MINING_FATIGUE, 25, 20 * 6));
        this.onDeathEvents.add(new OnDeathDropKey());

    }

    @Override
    public MobType getMobType() {
        return MobType.GOLEM;
    }

    @Override
    public MobRenderInfo getRenderInfo() {
        return new MobRenderInfo(0.9F, "obsidian");
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
        goalSelector.add(1, new FireballShooterGoal(en, (IShooter) en));
    }

}