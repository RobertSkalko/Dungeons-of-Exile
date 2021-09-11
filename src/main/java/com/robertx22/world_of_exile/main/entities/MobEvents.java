package com.robertx22.world_of_exile.main.entities;

import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import com.robertx22.world_of_exile.config.ModConfig;
import com.robertx22.world_of_exile.main.ModItems;
import com.robertx22.world_of_exile.main.entities.registration.MobManager;
import com.robertx22.world_of_exile.main.entities.spawn.PlayerBreakBlockSpawner;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class MobEvents {

    public static void register() {

        PlayerBlockBreakEvents.AFTER.register(new PlayerBlockBreakEvents.After() {

            @Override
            public void afterBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity) {
                try {
                    if (ModConfig.get().ENABLE_BLOCK_BREAK_GOLEM_SPAWNS) {
                        for (MobData data : MobManager.SET) {
                            for (PlayerBreakBlockSpawner spawner : data.spawnEvents.breakBlock) {
                                if (spawner.canSpawn(state.getBlock(), player)) {
                                    LivingEntity en = data.spawn(world, pos);
                                    data.spawnEvents.onSpecialSpawn.forEach(x -> x.onSpecialSpawn(en, data));
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ExileEvents.MOB_DEATH.register(new EventConsumer<ExileEvents.OnMobDeath>() {
            @Override
            public void accept(ExileEvents.OnMobDeath event) {

                if (ModConfig.get()
                    .getAllowedBosses()
                    .contains(event.mob.getType())) {
                    ItemStack stack = new ItemStack(ModItems.INSTANCE.SILVER_KEY);
                    event.mob.dropStack(stack);
                }

                if (MobManager.get(event.mob) != null) {

                    MobManager.get(event.mob).onDeathEvents.forEach(x -> x.accept(event));
                }

            }
        });

        ExileEvents.DAMAGE_AFTER_CALC.register(new EventConsumer<ExileEvents.OnDamageEntity>() {
            @Override
            public void accept(ExileEvents.OnDamageEntity event) {
                if (event.source.getAttacker() instanceof LivingEntity) {
                    MobData<LivingEntity> data = MobManager.get((LivingEntity) event.source.getAttacker());
                    if (data != null) {
                        for (Consumer<OnAttackSomeoneEvent> consumer : data.onAttackSomeoneEvents) {
                            consumer.accept(new OnAttackSomeoneEvent((LivingEntity) event.source.getAttacker(), event.mob));
                        }
                    }
                }
                if (event.mob instanceof LivingEntity) {
                    MobData<LivingEntity> data = MobManager.get((LivingEntity) event.mob);
                    if (data != null) {
                        for (Consumer<OnHurtEvent> consumer : data.onHurtEvents) {
                            consumer.accept(new OnHurtEvent(event.mob, (LivingEntity) event.source.getAttacker()));
                        }
                    }
                }
            }
        });

    }
}
