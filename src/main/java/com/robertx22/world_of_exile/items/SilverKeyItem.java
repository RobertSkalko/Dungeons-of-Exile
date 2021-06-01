package com.robertx22.world_of_exile.items;

import com.robertx22.world_of_exile.main.CreativeTabs;
import net.minecraft.item.Item;

public class SilverKeyItem extends Item {

    public SilverKeyItem() {
        super(new Settings().fireproof()
            .group(CreativeTabs.MAIN));
    }
}
