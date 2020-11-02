package com.robertx22.world_of_exile.config;

import com.robertx22.library_of_exile.config_utils.PerBiomeCategoryConfig;
import com.robertx22.world_of_exile.main.ModDimensions;
import com.robertx22.world_of_exile.main.ModEntities;
import com.robertx22.world_of_exile.main.ModStructures;
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

@Config(name = "world_of_exile")
public class ModConfig implements ConfigData {

    public boolean ENABLE_MOD = true;

    public boolean ENABLE_DUNGEON = true;

    @ConfigEntry.Gui.CollapsibleObject
    public FeatureConfig DUNGEON = new FeatureConfig(new FeatureConfig.MyStructureConfig(22, 0, 378235), ModStructures.DUNGEON_ID);

    @ConfigEntry.Gui.CollapsibleObject
    public FeatureConfig BLACKSTONE_TOWER = new FeatureConfig(new FeatureConfig.MyStructureConfig(8, 2, 2058146), ModStructures.BLACKSTONE_TOWER_ID);

    @ConfigEntry.Gui.CollapsibleObject
    public FeatureConfig STONE_BRICK_TOWER = new FeatureConfig(new FeatureConfig.MyStructureConfig(10, 2, 5240125), ModStructures.STONE_BRICK_TOWER_ID);

    @ConfigEntry.Gui.CollapsibleObject
    Set<String> ALLOWED_MOBS_FOR_SPAWNERS = new HashSet<>(); // todo turn into tag?

    @ConfigEntry.Gui.CollapsibleObject
    Set<String> ALLOWED_BOSSES = new HashSet<>(); // todo turn into tag?

    ModConfig() {

        BLACKSTONE_TOWER.PER_BIOME_CATEGORY = PerBiomeCategoryConfig.ofDefaultGroundStructure();
        BLACKSTONE_TOWER.PER_DIM.dimensions.add(ModDimensions.SOUL_SAND_VALLEY.toString());

        STONE_BRICK_TOWER.PER_BIOME_CATEGORY = PerBiomeCategoryConfig.ofDefaultGroundStructure();
        STONE_BRICK_TOWER.PER_DIM.dimensions.add(DimensionType.OVERWORLD_REGISTRY_KEY.getValue()
            .toString());

        DUNGEON.PER_BIOME_CATEGORY = PerBiomeCategoryConfig.ofDefaultGroundStructure();
        DUNGEON.PER_DIM.dimensions.add(DimensionType.OVERWORLD_REGISTRY_KEY.getValue()
            .toString());

        if (ALLOWED_MOBS_FOR_SPAWNERS.isEmpty()) {
            addMob(EntityType.ZOMBIE);
            addMob(EntityType.ZOMBIE_VILLAGER);
            addMob(EntityType.SPIDER);
            addMob(EntityType.CAVE_SPIDER);
            addMob(EntityType.SKELETON);
            addMob(EntityType.HUSK);
        }

        if (ALLOWED_BOSSES.isEmpty()) {
            addBoss(ModEntities.INSTANCE.FIRE_GOLEM);
            addBoss(ModEntities.INSTANCE.FROST_GOLEM);
        }
    }

    void addMob(EntityType type) {
        ALLOWED_MOBS_FOR_SPAWNERS.add(Registry.ENTITY_TYPE.getId(type)
            .toString());
    }

    void addBoss(EntityType type) {
        ALLOWED_BOSSES.add(Registry.ENTITY_TYPE.getId(type)
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
