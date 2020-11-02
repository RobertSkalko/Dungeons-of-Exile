package com.robertx22.world_of_exile.main;

import com.robertx22.world_of_exile.entities.boss.BaseGolemEntity;
import com.robertx22.world_of_exile.entities.mob.ChargingCrepeerEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.mob.BlazeEntity;

public class ModEntityAttributes {

    public static void register() {

        FabricDefaultAttributeRegistry.register(ModEntities.INSTANCE.FIRE_GOLEM, BaseGolemEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.INSTANCE.FROST_GOLEM, BaseGolemEntity.createAttributes());

        FabricDefaultAttributeRegistry.register(ModEntities.INSTANCE.CHARGING_CREEPER, ChargingCrepeerEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.INSTANCE.FROST_BLAZE, BlazeEntity.createBlazeAttributes());

    }
}
