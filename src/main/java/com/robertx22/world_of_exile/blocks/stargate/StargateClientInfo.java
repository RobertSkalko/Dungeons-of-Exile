package com.robertx22.world_of_exile.blocks.stargate;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class StargateClientInfo {

    public static StargateClientInfo SYNCED_INFO = new StargateClientInfo(BlockPos.ORIGIN, new Identifier(""));

    public BlockPos tpPos = BlockPos.ORIGIN;

    public Identifier dimensionId = new Identifier("");

    public StargateClientInfo(BlockPos tpPos, Identifier dimensionId) {
        this.tpPos = tpPos;
        this.dimensionId = dimensionId;
    }
}
