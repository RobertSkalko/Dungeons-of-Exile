package com.robertx22.world_of_exile.main;

import com.robertx22.world_of_exile.mobs.ai.FireGolemEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;

public class ModEntities {
    public static ModEntities INSTANCE;

    public EntityType<FireGolemEntity> FIRE_GOLEM = mob(FireGolemEntity::new, "fire_golem", new EntityDimensions(1.4F, 2.7F, true));

    private <T extends MobEntity> EntityType<T> mob(EntityType.EntityFactory<T> factory,
                                                    String id, EntityDimensions size) {

        EntityType<T> type = FabricEntityTypeBuilder.<T>create(SpawnGroup.MONSTER, factory).dimensions(size)
            .fireImmune()
            .build();
        Registry.register(Registry.ENTITY_TYPE, new Identifier(WOE.MODID, id), type);
        //ENTITY_TYPES.add(type);

        SpawnRestrictionAccessor.callRegister(type, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
        return type;
    }
}
