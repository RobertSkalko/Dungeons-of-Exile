package com.robertx22.world_of_exile.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public abstract class AbstractLockedBlock extends Block {

    public AbstractLockedBlock(Properties set) {
        super(set);
    }

    public abstract Item getKey();

    public abstract void onKeyUsed(World world, BlockPos pos, PlayerEntity player, ItemStack stack);

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray) {
        if (world.isClientSide) {
            return ActionResultType.CONSUME;
        } else {

            try {

                ItemStack stack = player.getMainHandItem();

                if (stack
                    .getItem() == getKey()) {

                    stack.shrink(1);

                    onKeyUsed(world, pos, player, stack);

                } else {
                    player.displayClientMessage(new StringTextComponent("").append(new ItemStack(getKey()).getHoverName())
                        .append(" Required"), false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ActionResultType.SUCCESS;
        }
    }

}

