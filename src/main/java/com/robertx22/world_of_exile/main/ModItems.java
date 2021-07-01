package com.robertx22.world_of_exile.main;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.world_of_exile.items.HellStoneItem;
import com.robertx22.world_of_exile.items.SilverKeyItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class ModItems extends Packets {
    public static ModItems INSTANCE;

    public Item HELL_STONE = item("hell_stone", new HellStoneItem());
    public SilverKeyItem SILVER_KEY = item("silver_key", new SilverKeyItem());
    public Item HELL_PORTAL = item("hell_portal", new BlockItem(ModBlocks.INSTANCE.HELL_PORTAL, new Item.Settings().maxCount(1)
        .group(CreativeTabs.MAIN)));

    <T extends Item> T item(String id, T c) {
        Registry.register(Registry.ITEM, WOE.id(id), c);
        return c;
    }

}
