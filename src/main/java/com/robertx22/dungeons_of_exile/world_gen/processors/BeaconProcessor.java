package com.robertx22.dungeons_of_exile.world_gen.processors;

import com.mojang.serialization.Codec;
import com.robertx22.dungeons_of_exile.main.DungeonConfig;
import com.robertx22.dungeons_of_exile.main.ModBlocks;
import com.robertx22.dungeons_of_exile.main.ModLoottables;
import com.robertx22.dungeons_of_exile.main.ModWorldGen;
import com.robertx22.dungeons_of_exile.mixin_ducks.MobSpawnerLogicDuck;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.MobSpawnerEntry;
import net.minecraft.world.WorldView;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BeaconProcessor extends StructureProcessor {

    public static final Codec<BeaconProcessor> CODEC = Codec.unit(BeaconProcessor::new);

    @Override
    public Structure.StructureBlockInfo process(WorldView worldView, BlockPos pos, BlockPos blockPos, Structure.StructureBlockInfo structureBlockInfo, Structure.StructureBlockInfo info, StructurePlacementData data) {
        BlockState currentState = info.state;
        BlockState resultState = info.state;

        try {
            if (currentState.getBlock() == Blocks.BEACON) {

                if (worldView.isClient()) {
                    return new Structure.StructureBlockInfo(info.pos, Blocks.SPAWNER.getDefaultState(), new CompoundTag());
                }
                Random random = data.getRandom(info.pos);

                if (random.nextFloat() < 0.5F) {
                    return new Structure.StructureBlockInfo(info.pos, Blocks.AIR.getDefaultState(), new CompoundTag());
                }

                if (random.nextFloat() > 0.95F) {
                    return new Structure.StructureBlockInfo(info.pos, ModBlocks.INSTANCE.RANDOM_BLOCK.getDefaultState(), new CompoundTag());
                }

                if (random.nextFloat() > 0.85F) {
                    CompoundTag resultTag = new CompoundTag();
                    ChestBlockEntity chest = new ChestBlockEntity();
                    chest.setLootTable(ModLoottables.DUNGEON_DEFAULT, RandomUtils.nextLong());
                    chest.toTag(resultTag);
                    return new Structure.StructureBlockInfo(info.pos, Blocks.CHEST.getDefaultState(), resultTag);

                } else {
                    MobSpawnerBlockEntity spawner = new MobSpawnerBlockEntity();
                    MobSpawnerLogicDuck saccess = (MobSpawnerLogicDuck) spawner.getLogic();
                    saccess.getspawnPotentials()
                        .clear();

                    EntityType type = null;

                    List<EntityType> mobs = new ArrayList<>(DungeonConfig.get()
                        .getAllowedSpawnerMobs());
                    type = mobs.get(RandomUtils.nextInt(0, mobs.size()));

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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Structure.StructureBlockInfo(info.pos, resultState, info.tag);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModWorldGen.INSTANCE.BEACON_PROCESSOR;
    }
}


