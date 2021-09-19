package com.robertx22.world_of_exile.main;

import com.robertx22.world_of_exile.items.SilverKeyItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static ModItems INSTANCE;

    public SilverKeyItem SILVER_KEY = item("silver_key", new SilverKeyItem());

    <T extends Item> T item(String id, T c) {
        Registry.register(Registry.ITEM, WOE.id(id), c);
        return c;
    }

}
