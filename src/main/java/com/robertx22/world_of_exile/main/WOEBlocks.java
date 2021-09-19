package com.robertx22.world_of_exile.main;

import com.robertx22.world_of_exile.blocks.delay.DelayedBlock;
import com.robertx22.world_of_exile.blocks.final_treasure.FinalTreasureBlock;
import com.robertx22.world_of_exile.blocks.locked_treasure.LockedTreasureBlock;
import net.minecraftforge.fml.RegistryObject;

public class WOEBlocks {

    public static RegistryObject<DelayedBlock> DELAY_BLOCK = WOEDeferred.BLOCKS.register("delay", () -> new DelayedBlock());
    public static RegistryObject<FinalTreasureBlock> FINAL_TREASURE_BLOCK = WOEDeferred.BLOCKS.register("final_treasure", () -> new FinalTreasureBlock());
    public static RegistryObject<LockedTreasureBlock> LOCKED_TREASURE_BLOCK = WOEDeferred.BLOCKS.register("locked_treasure", () -> new LockedTreasureBlock());

    public static void init() {

    }

}