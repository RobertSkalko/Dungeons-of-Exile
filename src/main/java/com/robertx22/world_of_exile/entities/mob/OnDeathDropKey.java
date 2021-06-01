package com.robertx22.world_of_exile.entities.mob;

import com.robertx22.library_of_exile.events.base.ExileEvents;
import com.robertx22.world_of_exile.main.ModItems;
import net.minecraft.item.ItemStack;

import java.util.function.Consumer;

public class OnDeathDropKey implements Consumer<ExileEvents.OnMobDeath> {
    @Override
    public void accept(ExileEvents.OnMobDeath event) {
        ItemStack stack = new ItemStack(ModItems.INSTANCE.SILVER_KEY);
        event.mob.dropStack(stack);
    }
}
