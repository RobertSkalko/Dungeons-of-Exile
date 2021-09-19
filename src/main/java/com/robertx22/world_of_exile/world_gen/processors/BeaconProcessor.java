package com.robertx22.world_of_exile.world_gen.processors;

import com.mojang.serialization.Codec;
import com.robertx22.world_of_exile.config.ModConfig;
import com.robertx22.world_of_exile.main.WOELootTables;
import com.robertx22.world_of_exile.main.WOEProcessors;
import com.robertx22.world_of_exile.mixin_ducks.MobSpawnerLogicDuck;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.util.WeightedSpawnerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BeaconProcessor extends StructureProcessor {

    public static final Codec<BeaconProcessor> CODEC = Codec.unit(BeaconProcessor::new);

    @Override
    public Template.BlockInfo processBlock(IWorldReader worldView, BlockPos pos, BlockPos blockPos, Template.BlockInfo structureBlockInfo, Template.BlockInfo info, PlacementSettings data) {
        BlockState currentState = info.state;
        BlockState resultState = info.state;

        try {
            if (currentState.getBlock() == Blocks.BEACON) {

                if (worldView.isClientSide()) {
                    return new Template.BlockInfo(info.pos, Blocks.SPAWNER.defaultBlockState(), new CompoundNBT());
                }
                Random random = data.getRandom(info.pos);

                if (random.nextFloat() < 0.5F) {
                    return new Template.BlockInfo(info.pos, Blocks.AIR.defaultBlockState(), new CompoundNBT());
                }

                if (random.nextFloat() > 0.85F) {
                    CompoundNBT resultTag = new CompoundNBT();
                    ChestTileEntity chest = new ChestTileEntity();
                    chest.setLootTable(WOELootTables.DUNGEON_DEFAULT, RandomUtils.nextLong());
                    chest.save(resultTag);
                    return new Template.BlockInfo(info.pos, Blocks.CHEST.defaultBlockState(), resultTag);

                } else {
                    MobSpawnerTileEntity spawner = new MobSpawnerTileEntity();
                    MobSpawnerLogicDuck saccess = (MobSpawnerLogicDuck) spawner.getSpawner();
                    saccess.getspawnPotentials()
                        .clear();

                    EntityType type = null;

                    List<EntityType> mobs = new ArrayList<>(ModConfig.get()
                        .getAllowedSpawnerMobs());
                    type = mobs.get(RandomUtils.nextInt(0, mobs.size()));

                    WeightedSpawnerEntity entry = new WeightedSpawnerEntity();
                    entry.getTag()
                        .putString("id", Registry.ENTITY_TYPE.getKey(type)
                            .toString());

                    saccess.getspawnPotentials()
                        .add(entry);
                    spawner.getSpawner()
                        .setNextSpawnData(entry);

                    CompoundNBT resultTag = new CompoundNBT();
                    spawner.save(resultTag);

                    return new Template.BlockInfo(info.pos, Blocks.SPAWNER.defaultBlockState(), resultTag);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Template.BlockInfo(info.pos, resultState, info.nbt);
    }

    @Override
    protected IStructureProcessorType<?> getType() {
        return WOEProcessors.INSTANCE.BEACON_PROCESSOR;
    }
}


