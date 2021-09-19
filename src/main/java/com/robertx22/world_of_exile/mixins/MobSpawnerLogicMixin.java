package com.robertx22.world_of_exile.mixins;

import com.robertx22.world_of_exile.mixin_ducks.MobSpawnerLogicDuck;
import net.minecraft.util.WeightedSpawnerEntity;
import net.minecraft.world.spawner.AbstractSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(AbstractSpawner.class)
public abstract class MobSpawnerLogicMixin implements MobSpawnerLogicDuck {

    @Accessor(value = "spawnPotentials")
    @Override
    public abstract List<WeightedSpawnerEntity> getspawnPotentials();

}
