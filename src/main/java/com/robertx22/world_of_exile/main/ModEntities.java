package com.robertx22.world_of_exile.main;

import com.robertx22.world_of_exile.entities.boss.FireGolemEntity;
import com.robertx22.world_of_exile.entities.boss.FrostGolemEntity;
import com.robertx22.world_of_exile.entities.mob.ChargingCrepeerEntity;
import com.robertx22.world_of_exile.entities.mob.FrostBlazeEntity;
import com.robertx22.world_of_exile.entities.projectile.FrostProjectileEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;

public class ModEntities {
    public static ModEntities INSTANCE;

    public EntityType<FireGolemEntity> FIRE_GOLEM = mob(FireGolemEntity::new, "fire_golem", new EntityDimensions(1.4F, 2.7F, true));
    public EntityType<FrostGolemEntity> FROST_GOLEM = mob(FrostGolemEntity::new, "frost_golem", new EntityDimensions(1.4F, 2.7F, true));

    public EntityType<ChargingCrepeerEntity> CHARGING_CREEPER = mob(ChargingCrepeerEntity::new, "charging_creepeer", new EntityDimensions(0.5F, 2, true));
    public EntityType<FrostBlazeEntity> FROST_BLAZE = mob(FrostBlazeEntity::new, "frost_blaze", new EntityDimensions(0.5F, 2, true));

    public EntityType<FrostProjectileEntity> FROST_PROJECTILE = projectile(FrostProjectileEntity::new, "frost_projectile", new EntityDimensions(0.5F, 0.5F, true));

    private <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory,
                                                        String id, EntityDimensions size) {

        EntityType<T> type = FabricEntityTypeBuilder.<T>create(SpawnGroup.MONSTER, factory).dimensions(size)
            .trackedUpdateRate(1)
            .trackRangeChunks(4)
            .build();
        Registry.register(Registry.ENTITY_TYPE, new Identifier(WOE.MODID, id), type);

        return type;
    }

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
