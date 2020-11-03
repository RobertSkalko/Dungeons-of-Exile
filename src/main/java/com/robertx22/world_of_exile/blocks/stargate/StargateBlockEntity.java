package com.robertx22.world_of_exile.blocks.stargate;

import com.robertx22.world_of_exile.main.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;

public class StargateBlockEntity extends BlockEntity implements Tickable {

    public BlockPos tpPos = BlockPos.ORIGIN;
    public Identifier dimensionId = new Identifier("");

    public StargateBlockEntity() {
        super(ModBlocks.INSTANCE.STARGATE_ENTITY);
    }

    int ticks = 0;

    @Override
    public void tick() {
        ticks++;

    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        tpPos = BlockPos.fromLong(tag.getLong("pos"));
        dimensionId = new Identifier(tag.getString("dim_id"));
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putLong("pos", tpPos.asLong());
        tag.putString("dim_id", dimensionId.toString());
        return super.toTag(tag);
    }

}
