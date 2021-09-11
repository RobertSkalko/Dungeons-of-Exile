package com.robertx22.world_of_exile.config;

import com.robertx22.world_of_exile.main.entities.MobTags;
import com.robertx22.world_of_exile.main.entities.registration.MobManager;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Config(name = "world_of_exile")
public class ModConfig implements ConfigData {

    public boolean ENABLE_MOD = true;

    public boolean ENABLE_DUNGEON = true;

    public boolean AUTO_DESTROY_TOWERS = true;

    public boolean ENABLE_BLOCK_BREAK_GOLEM_SPAWNS = true;
    public boolean ENABLE_WATERED_LAVA_GOLEM_SPAWNS = true;

    @ConfigEntry.Gui.CollapsibleObject
    public FeatureConfig DUNGEON = new FeatureConfig(new FeatureConfig.MyStructureConfig(50, 25, 378235));

    @ConfigEntry.Gui.CollapsibleObject
    public FeatureConfig STONE_BRICK_TOWER = new FeatureConfig(new FeatureConfig.MyStructureConfig(20, 7, 270951955));

    @ConfigEntry.Gui.CollapsibleObject
    public FeatureConfig LADDER_TOWER = new FeatureConfig(new FeatureConfig.MyStructureConfig(30, 6, 1092515512));

    @ConfigEntry.Gui.CollapsibleObject
    List<String> ALLOWED_MOBS_FOR_SPAWNERS = defaultMobs();

    @ConfigEntry.Gui.CollapsibleObject
    List<String> ALLOWED_BOSSES = defaultBosses();

    static List<String> defaultBosses() {
        List<String> list = new ArrayList<>();

        MobManager.MAP.entrySet()
            .stream()
            .filter(x -> x.getValue().tags.contains(MobTags.RANDOM_TOWER_BOSS))
            .forEach(b -> addBoss(list, b.getKey()));
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
        list.add(Registry.ENTITY_TYPE.getId(type)
            .toString());
    }

    static void addBoss(List<String> list, EntityType type) {
        list.add(Registry.ENTITY_TYPE.getId(type)
            .toString());
    }

    public Set<EntityType> getAllowedSpawnerMobs() {
        return ALLOWED_MOBS_FOR_SPAWNERS.stream()
            .map(x -> Registry.ENTITY_TYPE.get(new Identifier(x)))
            .collect(Collectors.toSet());
    }

    public Set<EntityType> getAllowedBosses() {
        return ALLOWED_BOSSES.stream()
            .map(x -> Registry.ENTITY_TYPE.get(new Identifier(x)))
            .collect(Collectors.toSet());
    }

    public static ModConfig get() {
        return AutoConfig.getConfigHolder(ModConfig.class)
            .getConfig();
    }

}
