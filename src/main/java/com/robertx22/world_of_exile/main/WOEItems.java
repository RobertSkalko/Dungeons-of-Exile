package com.robertx22.world_of_exile.main;

import com.robertx22.world_of_exile.items.SilverKeyItem;
import net.minecraftforge.fml.RegistryObject;

public class WOEItems {

    public static RegistryObject<SilverKeyItem> SILVER_KEY = WOEDeferred.ITEMS.register("silver_key", () -> new SilverKeyItem());

    public static void init() {

    }
}
