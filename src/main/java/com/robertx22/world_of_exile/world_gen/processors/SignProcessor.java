package com.robertx22.world_of_exile.world_gen.processors;

import com.mojang.serialization.Codec;
import com.robertx22.world_of_exile.blocks.delay.DelayedBlockEntity;
import com.robertx22.world_of_exile.config.ModConfig;
import com.robertx22.world_of_exile.main.WOEBlocks;
import com.robertx22.world_of_exile.main.WOELootTables;
import com.robertx22.world_of_exile.main.WOEProcessors;
import com.robertx22.world_of_exile.mixin_ducks.MobSpawnerLogicDuck;
import com.robertx22.world_of_exile.mixin_ducks.SignDuck;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.WeightedSpawnerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SignProcessor extends StructureProcessor {

    public static final Codec<SignProcessor> CODEC = Codec.unit(SignProcessor::new);

    @Override
    public Template.BlockInfo processBlock(IWorldReader worldView, BlockPos pos, BlockPos blockPos, Template.BlockInfo structureBlockInfo, Template.BlockInfo info, PlacementSettings data) {
        BlockState currentState = info.state;
        BlockState resultState = info.state;

        try {
            if (currentState.getBlock()
                .is(BlockTags.SIGNS)) {

                if (worldView.isClientSide()) {
                    return new Template.BlockInfo(info.pos, Blocks.SPAWNER.defaultBlockState(), new CompoundNBT());
                }

                SignTileEntity sign = new SignTileEntity();
                sign.load(currentState, info.nbt);

                SignDuck duck = (SignDuck) sign;

                List<String> texts = new ArrayList<>();
                for (ITextComponent x : duck.getMessages()) {
                    if (x != null) {
                        String s = x.getContents();
                        texts.add(s);
                    }
                }

                Random random = data.getRandom(info.pos);

                for (String str : texts) {

                    if (str.contains("chance")) {
                        String number = str.replaceAll("[^0-9]", "");
                        float chance = Float.parseFloat(number);

                        if (chance < random.nextFloat() * 100F) {
                            return new Template.BlockInfo(info.pos, Blocks.AIR.defaultBlockState(), new CompoundNBT());
                        }

                    }
                }

                for (String str : texts) {

                    if (str.contains("[spawner]")) {
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

                    if (str.contains("[treasure]")) {
                        CompoundNBT resultTag = new CompoundNBT();
                        ChestTileEntity chest = new ChestTileEntity();
                        chest.setLootTable(WOELootTables.DUNGEON_DEFAULT, RandomUtils.nextLong());
                        chest.save(resultTag);
                        return new Template.BlockInfo(info.pos, Blocks.CHEST.defaultBlockState(), resultTag);
                    }
                    if (str.contains("[boss]")) {
                        CompoundNBT resultTag = new CompoundNBT();
                        DelayedBlockEntity delay = new DelayedBlockEntity();
                        delay.executionString = "boss";
                        delay.save(resultTag);
                        return new Template.BlockInfo(info.pos, WOEBlocks.DELAY_BLOCK.get()
                            .defaultBlockState(), resultTag);
                    }
                    if (str.contains("[deployer]")) {
                        CompoundNBT resultTag = new CompoundNBT();
                        DelayedBlockEntity delay = new DelayedBlockEntity();
                        delay.executionString = "deploy";
                        delay.save(resultTag);
                        return new Template.BlockInfo(info.pos, WOEBlocks.DELAY_BLOCK.get()
                            .defaultBlockState(), resultTag);
                    }
                    if (str.contains("[final_treasure]")) {
                        return new Template.BlockInfo(info.pos, WOEBlocks.FINAL_TREASURE_BLOCK.get()
                            .defaultBlockState(), new CompoundNBT());
                    }
                    if (str.contains("[lock_treasure]")) {
                        return new Template.BlockInfo(info.pos, WOEBlocks.LOCKED_TREASURE_BLOCK.get()
                            .defaultBlockState(), new CompoundNBT());
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Template.BlockInfo(info.pos, resultState, info.nbt);
    }

    @Override
    protected IStructureProcessorType<?> getType() {
        return WOEProcessors.INSTANCE.SIGN_PROCESSOR;
    }
}


