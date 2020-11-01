package com.robertx22.world_of_exile.mixin_ducks;

import net.minecraft.world.MobSpawnerEntry;

import java.util.List;

public interface MobSpawnerLogicDuck {

    List<MobSpawnerEntry> getspawnPotentials();
}
