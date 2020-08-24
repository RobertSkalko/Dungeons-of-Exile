package com.robertx22.dungeons_of_exile.world_gen.processors.sign_processors;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.Structure;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

public class TreasureProc extends SignTextProc {

    private TreasureProc() {
    }

    public static TreasureProc getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public Structure.StructureBlockInfo getProcessed(Structure.StructureBlockInfo info, List<String> strings) {
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
