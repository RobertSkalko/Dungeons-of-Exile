package com.robertx22.world_of_exile.entities.test;

import com.robertx22.world_of_exile.main.entities.OnAttackSomeoneEvent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.apache.commons.lang3.RandomUtils;

import java.util.function.Consumer;

public class EffectOnAttack implements Consumer<OnAttackSomeoneEvent> {

    StatusEffect effect;
    float chance;
    int durationInTicks;
    int amp = 1;

    public EffectOnAttack(StatusEffect effect, float chance, int durationInTicks) {
        this.effect = effect;
        this.chance = chance;
        this.durationInTicks = durationInTicks;
    }

    @Override
    public void accept(OnAttackSomeoneEvent event) {
        if (chance > RandomUtils.nextInt(0, 101)) {
            event.victim.addStatusEffect(new StatusEffectInstance(effect, durationInTicks, amp));
        }
    }
}
