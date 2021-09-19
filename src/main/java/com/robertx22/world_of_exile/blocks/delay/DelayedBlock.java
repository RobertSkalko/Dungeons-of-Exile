package com.robertx22.world_of_exile.blocks.delay;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class DelayedBlock extends Block implements ITileEntityProvider {
    public DelayedBlock() {
        super(AbstractBlock.Properties.of(Material.STONE));
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader world) {
        return new DelayedBlockEntity();
    }

}
