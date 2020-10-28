package com.robertx22.dungeons_of_exile.main;

import com.robertx22.library_of_exile.config_utils.BlackOrWhiteList;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionType;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Config(name = "dungeons_of_exile")
public class DungeonConfig implements ConfigData {

    public boolean ENABLE_MOD = true;

    public boolean ENABLE_DUNGEON = true;
    public boolean ENABLE_TOWER = true;

    @ConfigEntry.Gui.CollapsibleObject
    public FeatureConfig TOWER_CONFIG = new FeatureConfig();

    public int TOWER_SEPARATION = 8;

    DungeonConfig() {
        TOWER_CONFIG.PER_DIM.dimensions.add(DimensionType.OVERWORLD_ID.toString());
        TOWER_CONFIG.PER_DIM.blackOrWhiteList = BlackOrWhiteList.WHITELIST;
        TOWER_CONFIG.PER_BIOME.blackOrWhiteList = BlackOrWhiteList.BLACKLIST;

        if (ALLOWED_MODS_FOR_SPAWNERS.isEmpty()) {
            addMob(EntityType.ZOMBIE);
            addMob(EntityType.ZOMBIE_VILLAGER);
            addMob(EntityType.SPIDER);
            addMob(EntityType.CAVE_SPIDER);
            addMob(EntityType.SKELETON);
            addMob(EntityType.HUSK);
        }

        if (ALLOWED_BOSSES.isEmpty()) {
            addBoss(ModEntities.INSTANCE.FIRE_GOLEM);
        }
    }

    void addMob(EntityType type) {
        ALLOWED_MODS_FOR_SPAWNERS.add(Registry.ENTITY_TYPE.getId(type)
            .toString());
    }

    void addBoss(EntityType type) {
        ALLOWED_BOSSES.add(Registry.ENTITY_TYPE.getId(type)
            .toString());
    }

    @ConfigEntry.Gui.CollapsibleObject
    Set<String> ALLOWED_MODS_FOR_SPAWNERS = new HashSet<>(); // todo turn into tag?

    @ConfigEntry.Gui.CollapsibleObject
    Set<String> ALLOWED_BOSSES = new HashSet<>(); // todo turn into tag?

    public Set<EntityType> getAllowedSpawnerMobs() {
        return ALLOWED_MODS_FOR_SPAWNERS.stream()
            .map(x -> Registry.ENTITY_TYPE.get(new Identifier(x)))
            .collect(Collectors.toSet());
    }

    public Set<EntityType> getAllowedBosses() {
        return ALLOWED_BOSSES.stream()
            .map(x -> Registry.ENTITY_TYPE.get(new Identifier(x)))
            .collect(Collectors.toSet());
    }

    public static DungeonConfig get() {
        return AutoConfig.getConfigHolder(DungeonConfig.class)
            .getConfig();
    }

}
