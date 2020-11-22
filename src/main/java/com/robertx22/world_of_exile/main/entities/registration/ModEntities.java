package com.robertx22.world_of_exile.main.entities.registration;

import com.robertx22.world_of_exile.entities.projectile.FrostProjectileEntity;
import com.robertx22.world_of_exile.main.WOE;
import com.robertx22.world_of_exile.main.entities.MobData;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;

import java.util.Random;

public class ModEntities {

    public static ModEntities INSTANCE;

    public EntityType<FrostProjectileEntity> FROST_PROJECTILE = projectile(FrostProjectileEntity::new, "frost_projectile", new EntityDimensions(0.5F, 0.5F, true));

    public ModEntities() {
        MobManager.SET.forEach(x -> {
            mob(x.getMobType().factory, x);
        });
    }

    private <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory,
                                                        String id, EntityDimensions size) {

        EntityType<T> type = FabricEntityTypeBuilder.<T>create(SpawnGroup.MONSTER, factory).dimensions(size)
            .trackedUpdateRate(1)
            .trackRangeChunks(4)
            .build();
        Registry.register(Registry.ENTITY_TYPE, WOE.id(id), type);

        return type;
    }

    public <T extends MobEntity> EntityType<T> mob(EntityType.EntityFactory<T> factory,
                                                   MobData data) {

        EntityType<T> type = FabricEntityTypeBuilder.<T>create(SpawnGroup.MONSTER, factory).dimensions(data.getSizeDimensions().dimensions)
            .fireImmune()
            .build();
        Registry.register(Registry.ENTITY_TYPE, data.identifier, type);

        MobManager.MAP.put(type, data);

        SpawnRestrictionAccessor.callRegister(type, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new SpawnRestriction.SpawnPredicate<T>() {
            @Override
            public boolean test(EntityType<T> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {

                if (spawnReason == SpawnReason.SPAWNER || spawnReason == SpawnReason.EVENT || spawnReason == SpawnReason.SPAWN_EGG || spawnReason == SpawnReason.REINFORCEMENT) {
                    return true;
                }

                if (MobEntity.canMobSpawn(type, world, spawnReason, pos, random)) {

                    if (world.toServerWorld()
                        .getRegistryKey()
                        .getValue()
                        .getNamespace()
                        .equals(WOE.MODID)) {
                        return true; // only spawn in my dimensions
                    }
                }
                return false;
            }
        });
        return type;
    }
}
