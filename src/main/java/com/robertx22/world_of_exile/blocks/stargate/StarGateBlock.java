package com.robertx22.world_of_exile.blocks.stargate;

import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class StarGateBlock extends Block implements BlockEntityProvider {

    public StarGateBlock() {
        super(Settings.of(Material.STONE)
            .strength(500, 500));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult ray) {
        if (world.isClient) {
            return ActionResult.CONSUME;
        } else {

            try {

                ItemStack stack = player.getMainHandStack();

                StargateBlockEntity tile = (StargateBlockEntity) world.getBlockEntity(pos);

                Packets.sendToClient(player, new StargateInfoPacket(tile.tpPos, tile.dimensionId));
                Packets.sendToClient(player, new OpenStargateScreenPacket());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return ActionResult.SUCCESS;
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new StargateBlockEntity();
    }
}

