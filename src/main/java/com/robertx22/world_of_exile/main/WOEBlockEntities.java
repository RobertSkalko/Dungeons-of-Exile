package com.robertx22.world_of_exile.main;

import com.robertx22.world_of_exile.blocks.delay.DelayedBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class WOEBlockEntities {
    public static RegistryObject<TileEntityType<DelayedBlockEntity>> DELAY_ENTITY = WOEDeferred.BLOCK_ENTITIES.register("delay", () -> of(WOEBlocks.DELAY_BLOCK.get(), DelayedBlockEntity::new));

    public static <T extends TileEntity> TileEntityType<T> of(Block block, Supplier<T> en) {
        TileEntityType<T> type = (TileEntityType<T>) TileEntityType.Builder.of(en, block)
            .build(null);
        return type;
    }

    public static void init() {

    }
}
