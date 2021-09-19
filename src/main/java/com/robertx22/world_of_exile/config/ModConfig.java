package com.robertx22.world_of_exile.config;

import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ModConfig {

    public boolean ENABLE_MOD = true;

    public boolean ENABLE_DUNGEON = true;

    public boolean AUTO_DESTROY_TOWERS = true;

    public FeatureConfig DUNGEON = new FeatureConfig(new FeatureConfig.MyStructureConfig(50, 25, 378235));

    public FeatureConfig STONE_BRICK_TOWER = new FeatureConfig(new FeatureConfig.MyStructureConfig(20, 7, 270951955));

    public FeatureConfig LADDER_TOWER = new FeatureConfig(new FeatureConfig.MyStructureConfig(30, 6, 1092515512));

    List<String> ALLOWED_MOBS_FOR_SPAWNERS = defaultMobs();

    List<String> ALLOWED_BOSSES = defaultBosses();

    static List<String> defaultBosses() {
        List<String> list = new ArrayList<>();
        addBoss(list, EntityType.WITHER_SKELETON);
        return list;

    }

    static List<String> defaultMobs() {
        List<String> list = new ArrayList<>();
        addMob(list, EntityType.ZOMBIE);
        addMob(list, EntityType.ZOMBIE_VILLAGER);
        addMob(list, EntityType.SKELETON);
        addMob(list, EntityType.STRAY);
        addMob(list, EntityType.HUSK);
        return list;
    }

    ModConfig() {

    }

    static void addMob(List<String> list, EntityType type) {
        list.add(Registry.ENTITY_TYPE.getKey(type)
            .toString());
    }

    static void addBoss(List<String> list, EntityType type) {
        list.add(Registry.ENTITY_TYPE.getKey(type)
            .toString());
    }

    public Set<EntityType> getAllowedSpawnerMobs() {
        return ALLOWED_MOBS_FOR_SPAWNERS.stream()
            .map(x -> Registry.ENTITY_TYPE.get(new ResourceLocation(x)))
            .collect(Collectors.toSet());
    }

    public Set<EntityType> getAllowedBosses() {
        return ALLOWED_BOSSES.stream()
            .map(x -> Registry.ENTITY_TYPE.get(new ResourceLocation(x)))
            .collect(Collectors.toSet());
    }

    public static ModConfig get() {
        return new ModConfig(); // TODO
    }

}
