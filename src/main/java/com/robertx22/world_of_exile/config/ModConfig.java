package com.robertx22.world_of_exile.config;

import com.robertx22.library_of_exile.config_utils.PerBiomeCategoryConfig;
import com.robertx22.library_of_exile.config_utils.PerDimensionConfig;
import com.robertx22.world_of_exile.main.ModDimensions;
import com.robertx22.world_of_exile.main.entities.MobTags;
import com.robertx22.world_of_exile.main.entities.registration.MobManager;
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

    public boolean AUTO_DESTROY_TOWERS = true;

    public boolean ENABLE_BLOCK_BREAK_GOLEM_SPAWNS = true;

    @ConfigEntry.Gui.CollapsibleObject
    public FeatureConfig DUNGEON = new FeatureConfig(new FeatureConfig.MyStructureConfig(22, 15, 378235));

    @ConfigEntry.Gui.CollapsibleObject
    public FeatureConfig BLACKSTONE_TOWER = new FeatureConfig(new FeatureConfig.MyStructureConfig(15, 6, 2058146));

    @ConfigEntry.Gui.CollapsibleObject
    public FeatureConfig STONE_BRICK_TOWER = new FeatureConfig(new FeatureConfig.MyStructureConfig(15, 5, 270951955));

    @ConfigEntry.Gui.CollapsibleObject
    public FeatureConfig ONE_PIECE_SURFACE = new FeatureConfig(new FeatureConfig.MyStructureConfig(20, 15, 447023887));

    @ConfigEntry.Gui.CollapsibleObject
    public FeatureConfig LADDER_TOWER = new FeatureConfig(new FeatureConfig.MyStructureConfig(25, 15, 1092515512));

    @ConfigEntry.Gui.CollapsibleObject
    Set<String> ALLOWED_MOBS_FOR_SPAWNERS = new HashSet<>(); // todo turn into tag?

    @ConfigEntry.Gui.CollapsibleObject
    Set<String> ALLOWED_BOSSES = new HashSet<>(); // todo turn into tag?

    ModConfig() {

        ONE_PIECE_SURFACE.PER_BIOME_CATEGORY = PerBiomeCategoryConfig.ofDefaultGroundStructure();
        ONE_PIECE_SURFACE.PER_DIM.dimensions.add(ModDimensions.HELL1.toString());

        BLACKSTONE_TOWER.PER_BIOME_CATEGORY = PerBiomeCategoryConfig.ofDefaultGroundStructure();
        BLACKSTONE_TOWER.PER_DIM.dimensions.add(ModDimensions.HELL1.toString());

        STONE_BRICK_TOWER.PER_BIOME_CATEGORY = PerBiomeCategoryConfig.ofDefaultGroundStructure();
        STONE_BRICK_TOWER.PER_DIM.dimensions.add(DimensionType.OVERWORLD_REGISTRY_KEY.getValue()
            .toString());
        addHellDimensions(STONE_BRICK_TOWER.PER_DIM);

        LADDER_TOWER.PER_BIOME_CATEGORY = PerBiomeCategoryConfig.ofDefaultGroundStructure();
        LADDER_TOWER.PER_DIM.dimensions.add(DimensionType.OVERWORLD_REGISTRY_KEY.getValue()
            .toString());
        addHellDimensions(LADDER_TOWER.PER_DIM);

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

            MobManager.MAP.entrySet()
                .stream()
                .filter(x -> x.getValue().tags.contains(MobTags.RANDOM_TOWER_BOSS))
                .forEach(b -> addBoss(b.getKey()));
        }
    }

    public static void addHellDimensions(PerDimensionConfig c) {
        c.dimensions.add(ModDimensions.HELL1.toString());
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
