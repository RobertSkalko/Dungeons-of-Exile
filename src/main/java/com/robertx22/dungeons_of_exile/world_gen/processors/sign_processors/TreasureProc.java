package com.robertx22.dungeons_of_exile.world_gen.processors.sign_processors;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.world.WorldView;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TreasureProc extends SignTextProc {

    private TreasureProc() {
    }

    public static TreasureProc getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public Structure.StructureBlockInfo getProcessed(WorldView worldView, Structure.StructureBlockInfo info, StructurePlacementData data, List<String> strings) {

        Random random = data.getRandom(info.pos);

        List<String> chances = strings.stream()
            .filter(x -> x.contains("chance"))
            .collect(Collectors.toList());

        if (!chances.isEmpty()) {
            String numbers = chances.get(0)
                .replaceAll("[^0-9]+", " ");

            Float chance = Float.parseFloat(numbers);

            if (chance <= random.nextFloat() * 100) {
                return new Structure.StructureBlockInfo(info.pos, Blocks.AIR.getDefaultState(), info.tag);
            }

        }

        CompoundTag resultTag = new CompoundTag();

        ChestBlockEntity chest = new ChestBlockEntity();

        chest.setLootTable(LootTables.SIMPLE_DUNGEON_CHEST, RandomUtils.nextLong());

        chest.toTag(resultTag);

        return new Structure.StructureBlockInfo(info.pos, Blocks.CHEST.getDefaultState(), resultTag);

    }

    @Override
    public boolean shouldProcess(List<String> strings) {
        return strings.contains("[treasure]");
    }

    private static class SingletonHolder {
        private static final TreasureProc INSTANCE = new TreasureProc();
    }
}
