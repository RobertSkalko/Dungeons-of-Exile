package com.robertx22.world_of_exile.main.entities;

import com.robertx22.world_of_exile.entities.boss.FireGolemEntity;
import com.robertx22.world_of_exile.entities.boss.FrostGolemEntity;
import com.robertx22.world_of_exile.entities.mob.*;
import com.robertx22.world_of_exile.entities.projectile.FrostProjectileEntity;
import com.robertx22.world_of_exile.main.WOE;
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

    public EntityType<FireGolemEntity> FIRE_GOLEM = mob(FireGolemEntity::new, "fire_golem", new EntityDimensions(1.4F, 2.7F, true));
    public EntityType<FrostGolemEntity> FROST_GOLEM = mob(FrostGolemEntity::new, "frost_golem", new EntityDimensions(1.4F, 2.7F, true));
    public EntityType<RedstoneGolemEntity> REDSTONE_GOLEM = mob(RedstoneGolemEntity::new, "redstone_golem", new EntityDimensions(0.9F, 0.9F, true));

    public EntityType<ObsidianSkeleton> OBSIDIAN_SKELETON = mob(ObsidianSkeleton::new, "obsidian_skeleton", new EntityDimensions(0.5F, 2, true));
    public EntityType<DemonSkeleton> DEMON_SKELETON = mob(DemonSkeleton::new, "demon_skeleton", new EntityDimensions(0.5F, 2, true));

    public EntityType<ObsidianEnderman> OBSIDIAN_ENDERMAN = mob(ObsidianEnderman::new, "obsidian_enderman", new EntityDimensions(0.5F, 2, true));

    public EntityType<InfernalScorpionEntity> INFERNO_SCORPION = mob(InfernalScorpionEntity::new, "inferno_scorpion", new EntityDimensions(1.2F, 1.2F, true));
    public EntityType<PoisonSpiderEntity> POISON_SPIDER = mob(PoisonSpiderEntity::new, "poison_spider", new EntityDimensions(1.2F, 1.2F, true));

    public EntityType<InfernalBatEntity> INFERNO_BAT = mob(InfernalBatEntity::new, "infernal_bat", new EntityDimensions(3, 3, true));
    public EntityType<FrostBatEntity> FROST_BAT = mob(FrostBatEntity::new, "frost_bat", new EntityDimensions(3, 3, true));

    public EntityType<FrostBlazeEntity> FROST_BLAZE = mob(FrostBlazeEntity::new, "frost_blaze", new EntityDimensions(0.5F, 2, true));

    public EntityType<FrostProjectileEntity> FROST_PROJECTILE = projectile(FrostProjectileEntity::new, "frost_projectile", new EntityDimensions(0.5F, 0.5F, true));

    private <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory,
                                                        String id, EntityDimensions size) {

        EntityType<T> type = FabricEntityTypeBuilder.<T>create(SpawnGroup.MONSTER, factory).dimensions(size)
            .trackedUpdateRate(1)
            .trackRangeChunks(4)
            .build();
        Registry.register(Registry.ENTITY_TYPE, WOE.id(id), type);

        return type;
    }

    private <T extends MobEntity> EntityType<T> mob(EntityType.EntityFactory<T> factory,
                                                    String id, EntityDimensions size) {

        EntityType<T> type = FabricEntityTypeBuilder.<T>create(SpawnGroup.MONSTER, factory).dimensions(size)
            .fireImmune()
            .build();
        Registry.register(Registry.ENTITY_TYPE, WOE.id(id), type);
        //ENTITY_TYPES.add(type);

        SpawnRestrictionAccessor.callRegister(type, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new SpawnRestriction.SpawnPredicate<T>() {
            @Override
            public boolean test(EntityType<T> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {

                if (MobEntity.canMobSpawn(type, world, spawnReason, pos, random)) {

                    if (spawnReason == SpawnReason.SPAWNER) {
                        return true;
                    }

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
