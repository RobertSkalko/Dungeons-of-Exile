package com.robertx22.world_of_exile.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractLockedBlock extends Block {

    public AbstractLockedBlock(Settings set) {
        super(set);
    }

    public abstract Item getKey();

    public abstract void onKeyUsed(World world, BlockPos pos, PlayerEntity player, ItemStack stack);

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult ray) {
        if (world.isClient) {
            return ActionResult.CONSUME;
        } else {

            try {

                ItemStack stack = player.getMainHandStack();

                if (stack
                    .getItem() == getKey()) {

                    stack.decrement(1);

                    onKeyUsed(world, pos, player, stack);

                } else {
                    player.sendMessage(new LiteralText("").append(getKey().getName())
                        .append(" Required"), false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ActionResult.SUCCESS;
        }
    }

}

