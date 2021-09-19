package com.robertx22.world_of_exile.main;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class CreativeTabs {

    public static ItemGroup MAIN = FabricItemGroupBuilder.build(
        new Identifier(WOE.MODID, "main")
        ,
        () -> new ItemStack(ModItems.INSTANCE.SILVER_KEY));

}
