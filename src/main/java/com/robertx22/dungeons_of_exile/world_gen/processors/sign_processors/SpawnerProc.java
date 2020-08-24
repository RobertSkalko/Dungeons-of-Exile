package com.robertx22.dungeons_of_exile.world_gen.processors.sign_processors;

import com.robertx22.dungeons_of_exile.mixin_ducks.MobSpawnerLogicDuck;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.Structure;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.MobSpawnerEntry;
import org.apache.commons.lang3.RandomUtils;

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
    public Structure.StructureBlockInfo getProcessed(Structure.StructureBlockInfo info, List<String> strings) {

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
            List<EntityType> firemobs = Registry.ENTITY_TYPE.stream()
                .filter(x -> x.isFireImmune() && x.getHeight() < 3)
                .collect(Collectors.toList());
            type = firemobs.get(RandomUtils.nextInt(0, firemobs.size()));
        } else {
            List<EntityType> mobs = Registry.ENTITY_TYPE.stream()
                .filter(x -> x.getSpawnGroup() == SpawnGroup.MONSTER && x.getHeight() < 3)
                .collect(Collectors.toList());
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
