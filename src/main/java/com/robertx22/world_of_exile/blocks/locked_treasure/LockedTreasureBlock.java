package com.robertx22.world_of_exile.blocks.locked_treasure;

import com.robertx22.world_of_exile.blocks.AbstractLockedBlock;
import com.robertx22.world_of_exile.main.ModItems;
import com.robertx22.world_of_exile.main.ModLoottables;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomUtils;

public class LockedTreasureBlock extends AbstractLockedBlock {

    public LockedTreasureBlock() {
        super(AbstractBlock.Properties.of(Material.STONE)
            .strength(5000, 50000));
    }

    @Override
    public Item getKey() {
        return ModItems.INSTANCE.SILVER_KEY;
    }

    @Override
    public void onKeyUsed(World world, BlockPos pos, PlayerEntity player, ItemStack stack) {

        world.setBlockAndUpdate(pos, Blocks.CHEST.defaultBlockState());

        ChestTileEntity chest = (ChestTileEntity) world.getBlockEntity(pos);
        chest.setLootTable(ModLoottables.BIG_TREASURE, RandomUtils.nextLong());

    }
}


