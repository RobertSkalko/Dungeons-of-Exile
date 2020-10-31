package com.robertx22.dungeons_of_exile.main;

import com.robertx22.dungeons_of_exile.items.SilverKeyItem;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems extends Packets {
    public static ModItems INSTANCE;
    public SilverKeyItem SILVER_KEY = item("silver_key", new SilverKeyItem());

    <T extends Item> T item(String id, T c) {
        Registry.register(Registry.ITEM, new Identifier(WOE.MODID, id), c);
        return c;
    }

}
