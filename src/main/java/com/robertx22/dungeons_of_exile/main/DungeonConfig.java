package com.robertx22.dungeons_of_exile.main;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Config(name = "dungeons_of_exile")
public class DungeonConfig implements ConfigData {

    public boolean ENABLE_MOD = true;

    DungeonConfig() {
        addMob(EntityType.ZOMBIE);
        addMob(EntityType.ZOMBIE_VILLAGER);
        addMob(EntityType.SPIDER);
        addMob(EntityType.CAVE_SPIDER);
        addMob(EntityType.SKELETON);
        addMob(EntityType.HUSK);
        addMob(EntityType.BLAZE);
        addMob(EntityType.WITCH);
    }

    void addMob(EntityType type) {
        ALLOWED_MODS_FOR_SPAWNERS.add(Registry.ENTITY_TYPE.getId(type)
            .toString());
    }

    List<String> ALLOWED_MODS_FOR_SPAWNERS = new ArrayList<>(); // todo turn into tag?

    public List<EntityType> getAllowedSpawnerMobs() {
        return ALLOWED_MODS_FOR_SPAWNERS.stream()
            .map(x -> Registry.ENTITY_TYPE.get(new Identifier(x)))
            .collect(Collectors.toList());
    }

    public static DungeonConfig get() {
        return AutoConfig.getConfigHolder(DungeonConfig.class)
            .getConfig();
    }

}
