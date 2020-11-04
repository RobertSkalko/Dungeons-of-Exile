package com.robertx22.world_of_exile.items;

import com.robertx22.world_of_exile.main.CreativeTabs;
import com.robertx22.world_of_exile.main.WOE;
import com.robertx22.world_of_exile.util.CommandTeleportUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TeleportBackItem extends Item {

    public TeleportBackItem() {
        super(new Settings().group(CreativeTabs.MAIN));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        if (!world.isClient) {
            try {

                if (player.world.getRegistryKey()
                    .getValue()
                    .getNamespace()
                    .equals(WOE.MODID)) {

                    ServerPlayerEntity p = (ServerPlayerEntity) player;
                    BlockPos pos = p.getSpawnPointPosition();
                    if (pos == null) {
                        pos = p.server.getWorld(p.getSpawnPointDimension())
                            .getSpawnPos();
                    }
                    CommandTeleportUtil.teleport(p, pos, p.getSpawnPointDimension()
                        .getValue());

                    return new TypedActionResult<ItemStack>(ActionResult.CONSUME, player.getStackInHand(hand));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new TypedActionResult<ItemStack>(ActionResult.PASS, player.getStackInHand(hand));
    }
}
