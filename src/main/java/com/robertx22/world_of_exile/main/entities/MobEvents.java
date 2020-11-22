package com.robertx22.world_of_exile.main.entities;

import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.entity.LivingEntity;

import java.util.function.Consumer;

public class MobEvents {

    public static void register() {

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
