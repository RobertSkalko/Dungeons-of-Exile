package com.robertx22.world_of_exile.mixin_ducks;

import net.minecraft.util.WeightedSpawnerEntity;

import java.util.List;

public interface MobSpawnerLogicDuck {

    List<WeightedSpawnerEntity> getspawnPotentials();
}
