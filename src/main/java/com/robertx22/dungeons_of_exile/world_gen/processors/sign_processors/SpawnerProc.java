package com.robertx22.dungeons_of_exile.world_gen.processors.sign_processors;

import com.robertx22.dungeons_of_exile.main.DungeonConfig;
import com.robertx22.dungeons_of_exile.mixin_ducks.MobSpawnerLogicDuck;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.MobSpawnerEntry;
import net.minecraft.world.WorldView;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// i filter for height so i dont spawn withers, as they are 3.5 blocks high
// (its also a small dungeon so i dont want huge mobs anyway)
public class SpawnerProc extends SignTextProc {

    private SpawnerProc() {
    }

    public static SpawnerProc getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public Structure.StructureBlockInfo getProcessed(WorldView worldView, Structure.StructureBlockInfo info, StructurePlacementData data, List<String> strings) {

        if (worldView.isClient()) {
            return new Structure.StructureBlockInfo(info.pos, Blocks.SPAWNER.getDefaultState(), new CompoundTag());
        }

        MobSpawnerBlockEntity spawner = new MobSpawnerBlockEntity();
        MobSpawnerLogicDuck saccess = (MobSpawnerLogicDuck) spawner.getLogic();
        saccess.getspawnPotentials()
            .clear();

        EntityType type = null;

        if (strings.contains("[spider]")) {
            if (RandomUtils.nextBoolean()) {
                type = EntityType.SPIDER;
            } else {
                type = EntityType.CAVE_SPIDER;
            }
        } else if (strings.contains("[fire]")) {
            List<EntityType> firemobs = DungeonConfig.get()
                .getAllowedSpawnerMobs()
                .stream()
                .filter(x -> x.isFireImmune())
                .collect(Collectors.toList());
            type = firemobs.get(RandomUtils.nextInt(0, firemobs.size()));
        } else {
            List<EntityType> mobs = new ArrayList<>(DungeonConfig.get()
                .getAllowedSpawnerMobs());
            type = mobs.get(RandomUtils.nextInt(0, mobs.size()));
        }

        MobSpawnerEntry entry = new MobSpawnerEntry();
        entry.getEntityTag()
            .putString("id", Registry.ENTITY_TYPE.getId(type)
                .toString());

        saccess.getspawnPotentials()
            .add(entry);

        spawner.getLogic()
            .setSpawnEntry(entry);

        CompoundTag resultTag = new CompoundTag();
        spawner.toTag(resultTag);

        return new Structure.StructureBlockInfo(info.pos, Blocks.SPAWNER.getDefaultState(), resultTag);
    }

    @Override
    public boolean shouldProcess(List<String> strings) {
        return strings.contains("[spawner]");
    }

    private static class SingletonHolder {
        private static final SpawnerProc INSTANCE = new SpawnerProc();
    }
}
