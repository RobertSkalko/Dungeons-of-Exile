package com.robertx22.world_of_exile.main;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class WOECreativeTabs {

    public static ItemGroup MAIN = new ItemGroup(WOE.MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(WOEItems.SILVER_KEY.get());
        }
    };

}
