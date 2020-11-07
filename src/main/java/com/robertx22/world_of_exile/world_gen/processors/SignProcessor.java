package com.robertx22.world_of_exile.world_gen.processors;

import com.mojang.serialization.Codec;
import com.robertx22.world_of_exile.blocks.delay.DelayedBlockEntity;
import com.robertx22.world_of_exile.config.ModConfig;
import com.robertx22.world_of_exile.main.ModBlocks;
import com.robertx22.world_of_exile.main.ModLoottables;
import com.robertx22.world_of_exile.main.ModWorldGen;
import com.robertx22.world_of_exile.mixin_ducks.MobSpawnerLogicDuck;
import com.robertx22.world_of_exile.mixin_ducks.SignDuck;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.MobSpawnerEntry;
import net.minecraft.world.WorldView;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SignProcessor extends StructureProcessor {

    public static final Codec<SignProcessor> CODEC = Codec.unit(SignProcessor::new);

    @Override
    public Structure.StructureBlockInfo process(WorldView worldView, BlockPos pos, BlockPos blockPos, Structure.StructureBlockInfo structureBlockInfo, Structure.StructureBlockInfo info, StructurePlacementData data) {
        BlockState currentState = info.state;
        BlockState resultState = info.state;

        try {
            if (currentState.getBlock()
                .isIn(BlockTags.SIGNS)) {

                if (worldView.isClient()) {
                    return new Structure.StructureBlockInfo(info.pos, Blocks.SPAWNER.getDefaultState(), new CompoundTag());
                }

                SignBlockEntity sign = new SignBlockEntity();
                sign.fromTag(currentState, info.tag);

                SignDuck duck = (SignDuck) sign;

                List<String> texts = new ArrayList<>();
                for (Text x : duck.getText()) {
                    if (x != null) {
                        String s = x.asString();
                        texts.add(s);
                    }
                }

                /*
                String text = "";
                for (Text txt : duck.getText()) {
                    text += txt.asString();
                }
                 */

                Random random = data.getRandom(info.pos);

                for (String str : texts) {

                    if (str.contains("chance")) {
                        String number = str.replaceAll("[^0-9]", "");
                        float chance = Float.parseFloat(number);

                        if (chance < random.nextFloat() * 100F) {
                            return new Structure.StructureBlockInfo(info.pos, Blocks.AIR.getDefaultState(), new CompoundTag());
                        }

                    }
                }

                for (String str : texts) {

                    if (str.contains("[spawner]")) {
                        MobSpawnerBlockEntity spawner = new MobSpawnerBlockEntity();
                        MobSpawnerLogicDuck saccess = (MobSpawnerLogicDuck) spawner.getLogic();
                        saccess.getspawnPotentials()
                            .clear();

                        EntityType type = null;

                        List<EntityType> mobs = new ArrayList<>(ModConfig.get()
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

                    if (str.contains("[treasure]")) {
                        CompoundTag resultTag = new CompoundTag();
                        ChestBlockEntity chest = new ChestBlockEntity();
                        chest.setLootTable(ModLoottables.DUNGEON_DEFAULT, RandomUtils.nextLong());
                        chest.toTag(resultTag);
                        return new Structure.StructureBlockInfo(info.pos, Blocks.CHEST.getDefaultState(), resultTag);
                    }
                    if (str.contains("[boss]")) {
                        CompoundTag resultTag = new CompoundTag();
                        DelayedBlockEntity delay = new DelayedBlockEntity();
                        delay.executionString = "boss";
                        delay.toTag(resultTag);
                        return new Structure.StructureBlockInfo(info.pos, ModBlocks.INSTANCE.DELAY_BLOCK.getDefaultState(), resultTag);
                    }
                    if (str.contains("[deployer]")) {
                        CompoundTag resultTag = new CompoundTag();
                        DelayedBlockEntity delay = new DelayedBlockEntity();
                        delay.executionString = "deploy";
                        delay.toTag(resultTag);
                        return new Structure.StructureBlockInfo(info.pos, ModBlocks.INSTANCE.DELAY_BLOCK.getDefaultState(), resultTag);
                    }
                    if (str.contains("[final_treasure]")) {
                        return new Structure.StructureBlockInfo(info.pos, ModBlocks.INSTANCE.FINAL_TREASURE_BLOCK.getDefaultState(), new CompoundTag());
                    }
                    if (str.contains("[lock_treasure]")) {
                        return new Structure.StructureBlockInfo(info.pos, ModBlocks.INSTANCE.LOCKED_TREASURE_BLOCK.getDefaultState(), new CompoundTag());
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Structure.StructureBlockInfo(info.pos, resultState, info.tag);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModWorldGen.INSTANCE.SIGN_PROCESSOR;
    }
}


