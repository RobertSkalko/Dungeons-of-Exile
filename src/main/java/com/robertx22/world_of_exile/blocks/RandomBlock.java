package com.robertx22.world_of_exile.blocks;

import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.world_of_exile.config.ModConfig;
import com.robertx22.world_of_exile.main.ModLoottables;
import net.minecraft.block.*;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class RandomBlock extends Block {

    public RandomBlock() {
        super(AbstractBlock.Settings.of(Material.STONE)
            .strength(50.0F, 1200.0F));
    }

    @Override
    @Deprecated
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
                              Hand hand, BlockHitResult ray) {
        if (world.isClient) {
            return ActionResult.CONSUME;
        }

        try {
            if (player != null) {
                SoundUtils.playSound(player, SoundEvents.ENTITY_PLAYER_LEVELUP, 1, 1);

                if (world.random.nextFloat() > 0.5F) {

                    world.setBlockState(pos, Blocks.CHEST.getDefaultState());

                    ChestBlockEntity chest = (ChestBlockEntity) world.getBlockEntity(pos);
                    chest.setLootTable(ModLoottables.DUNGEON_DEFAULT, RandomUtils.nextLong());

                    world.setBlockEntity(pos, chest);

                } else {
                    for (int i = 0; i < 10; i++) {
                        summonMob(world, pos);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ActionResult.SUCCESS;
    }

    public void summonMob(World world, BlockPos pos) {

        try {
            List<EntityType> mobs = new ArrayList<>(ModConfig.get()
                .getAllowedSpawnerMobs());
            EntityType type = mobs.get(RandomUtils.nextInt(0, mobs.size()));

            LivingEntity en = (LivingEntity) type.create(world);

            en.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), world.random.nextFloat() * 360.0F, 0.0F);
            ServerWorld sw = (ServerWorld) world;

            if (en instanceof MobEntity) {
                MobEntity mob = (MobEntity) en;
                mob.initialize(sw, sw.getLocalDifficulty(en.getBlockPos()), SpawnReason.SPAWNER, null, null);
            }

            en.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 40, 3));

            sw.spawnEntityAndPassengers(en);
        } catch (Exception e) {
            e.printStackTrace();
        }

        world.setBlockState(pos, Blocks.AIR.getDefaultState());

    }
}
