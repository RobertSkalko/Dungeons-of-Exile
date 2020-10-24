package com.robertx22.dungeons_of_exile.main;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Config(name = "dungeons_of_exile")
public class DungeonConfig implements ConfigData {

    public boolean ENABLE_MOD = true;

    public boolean ENABLE_DUNGEON = true;
    public boolean ENABLE_TOWER = true;

    DungeonConfig() {

        if (ALLOWED_MODS_FOR_SPAWNERS.isEmpty()) {
            addMob(EntityType.ZOMBIE);
            addMob(EntityType.ZOMBIE_VILLAGER);
            addMob(EntityType.SPIDER);
            addMob(EntityType.CAVE_SPIDER);
            addMob(EntityType.SKELETON);
            addMob(EntityType.HUSK);
        }
    }

    void addMob(EntityType type) {
        ALLOWED_MODS_FOR_SPAWNERS.add(Registry.ENTITY_TYPE.getId(type)
            .toString());
    }

    Set<String> ALLOWED_MODS_FOR_SPAWNERS = new HashSet<>(); // todo turn into tag?

    public Set<EntityType> getAllowedSpawnerMobs() {
        return ALLOWED_MODS_FOR_SPAWNERS.stream()
            .map(x -> Registry.ENTITY_TYPE.get(new Identifier(x)))
            .collect(Collectors.toSet());
    }

    public static DungeonConfig get() {
        return AutoConfig.getConfigHolder(DungeonConfig.class)
            .getConfig();
    }

}
