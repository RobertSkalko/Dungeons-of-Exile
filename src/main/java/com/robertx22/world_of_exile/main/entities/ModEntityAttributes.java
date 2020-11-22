package com.robertx22.world_of_exile.main.entities;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class ModEntityAttributes {

    public static void register() {

        MobManager.MAP.entrySet()
            .forEach(x -> {
                FabricDefaultAttributeRegistry.register(x.getKey(), x.getValue()
                    .getAttributes()
                    .build());
            });

    }
}
