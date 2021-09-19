package com.robertx22.world_of_exile.main;

import com.robertx22.world_of_exile.blocks.delay.DelayedBlock;
import com.robertx22.world_of_exile.blocks.delay.DelayedBlockEntity;
import com.robertx22.world_of_exile.blocks.final_treasure.FinalTreasureBlock;
import com.robertx22.world_of_exile.blocks.locked_treasure.LockedTreasureBlock;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class ModBlocks {

    public static ModBlocks INSTANCE;

    public DelayedBlock DELAY_BLOCK = of("delay", new DelayedBlock());
    public FinalTreasureBlock FINAL_TREASURE_BLOCK = of("final_treasure", new FinalTreasureBlock());
    public LockedTreasureBlock LOCKED_TREASURE_BLOCK = of("locked_treasure", new LockedTreasureBlock());

    <T extends Block> T of(String id, T c) {
        Registry.register(Registry.BLOCK, WOE.id(id), c);
        return c;
    }

    public TileEntityType<DelayedBlockEntity> DELAY_ENTITY = of(DELAY_BLOCK, DelayedBlockEntity::new);

    private <T extends TileEntity> TileEntityType<T> of(Block block, Supplier<TileEntity> en) {
        TileEntityType<T> type = (TileEntityType<T>) TileEntityType.Builder.of(en, block)
            .build(null);
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, Registry.BLOCK.getKey(block)
            .toString(), type);
    }

    public ModBlocks() {

    }

}