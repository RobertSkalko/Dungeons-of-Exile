package com.robertx22.world_of_exile.blocks.stargate;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.world_of_exile.blocks.stargate.packets.OpenStargateScreenPacket;
import com.robertx22.world_of_exile.blocks.stargate.packets.StargateInfoPacket;
import com.robertx22.world_of_exile.main.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class StarGateBlock extends Block implements BlockEntityProvider {

    public StarGateBlock() {
        super(Settings.of(Material.STONE)
            .strength(500, 500));
    }

    @Deprecated
    public List<ItemStack> getDroppedStacks(BlockState blockstate, LootContext.Builder context) {
        ArrayList<ItemStack> items = new ArrayList();
        items.add(new ItemStack(this));
        return items;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult ray) {
        if (world.isClient) {
            return ActionResult.CONSUME;
        } else {

            try {
                StargateBlockEntity tile = (StargateBlockEntity) world.getBlockEntity(pos);

                ItemStack stack = player.getMainHandStack();

                if (stack.getItem() == ModItems.INSTANCE.TELEPORT_RANDOMIZER) {
                    stack.decrement(1);
                    tile.randomizeTeleportLocation();
                    return ActionResult.SUCCESS;
                }

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

