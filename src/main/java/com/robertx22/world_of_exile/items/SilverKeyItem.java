package com.robertx22.world_of_exile.items;

import com.robertx22.world_of_exile.main.WOECreativeTabs;
import net.minecraft.item.Item;

public class SilverKeyItem extends Item {

    public SilverKeyItem() {
        super(new Properties().fireResistant()
            .tab(WOECreativeTabs.MAIN));
    }
}
