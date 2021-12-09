package com.robertx22.world_of_exile.config;

import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WOEConfig {

    public static final ForgeConfigSpec SPEC;
    public static final WOEConfig CONFIG;

    static {
        final Pair<WOEConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(WOEConfig::new);
        SPEC = specPair.getRight();
        CONFIG = specPair.getLeft();
    }

    WOEConfig(ForgeConfigSpec.Builder b) {
        b.comment("Dungeon Configs")
            .push("general");

        TOWER_SEPARATION = b.defineInRange("TOWER_SEPARATION", 10, 0, 100000);
        TOWER_SPACING = b.defineInRange("TOWER_SPACING", 20, 0, 1000);
        TOWER_SEED = b.defineInRange("TOWER_SEED", 270951955, 0, Integer.MAX_VALUE);

        AUTO_DESTROY_TOWERS = b.define("AUTO_DESTROY_TOWERS", true);

        ALLOWED_MOBS_FOR_SPAWNERS = b.defineList("ALLOWED_MOBS_FOR_SPAWNERS", defaultMobs(), x -> true);
        ALLOWED_BOSSES = b.defineList("ALLOWED_BOSSES", defaultBosses(), x -> true);

        b.pop();
    }

    public ForgeConfigSpec.IntValue TOWER_SEPARATION;
    public ForgeConfigSpec.IntValue TOWER_SPACING;
    public ForgeConfigSpec.IntValue TOWER_SEED;

    public ForgeConfigSpec.BooleanValue AUTO_DESTROY_TOWERS;

    public FeatureConfig getTowerConfig() {
        return new FeatureConfig(new FeatureConfig.MyStructureConfig(TOWER_SPACING, TOWER_SEPARATION, TOWER_SEED));
    }

    ForgeConfigSpec.ConfigValue<List<? extends String>> ALLOWED_MOBS_FOR_SPAWNERS;

    ForgeConfigSpec.ConfigValue<List<? extends String>> ALLOWED_BOSSES;

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

    static void addMob(List<String> list, EntityType type) {
        list.add(Registry.ENTITY_TYPE.getKey(type)
            .toString());
    }

    static void addBoss(List<String> list, EntityType type) {
        list.add(Registry.ENTITY_TYPE.getKey(type)
            .toString());
    }

    public Set<EntityType> getAllowedSpawnerMobs() {
        return ALLOWED_MOBS_FOR_SPAWNERS.get()
            .stream()
            .map(x -> Registry.ENTITY_TYPE.get(new ResourceLocation(x)))
            .collect(Collectors.toSet());
    }

    public Set<EntityType> getAllowedBosses() {
        return ALLOWED_BOSSES.get()
            .stream()
            .map(x -> Registry.ENTITY_TYPE.get(new ResourceLocation(x)))
            .collect(Collectors.toSet());
    }

    public static WOEConfig get() {
        return CONFIG;
    }

}
