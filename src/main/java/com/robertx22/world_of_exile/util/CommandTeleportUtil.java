package com.robertx22.world_of_exile.util;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;

public class CommandTeleportUtil {

    public static void teleport(ServerPlayerEntity player, BlockPos pos) {
        teleport(player, pos, player.level.dimensionType());
    }

    public static void teleport(ServerPlayerEntity player, BlockPos pos, DimensionType dimension) {
        teleport(player, pos, player.level.registryAccess()
            .dimensionTypes()
            .getKey(dimension));
    }

    public static void teleport(ServerPlayerEntity player, BlockPos pos, ResourceLocation dimension) {
        try {
            String command = "/execute in " + dimension.toString() + " run tp " + player
                .getDisplayName()
                .getContents() + " " + pos.getX() + " " + pos.getY() + " " + pos.getZ();

            player
                .getServer()
                .getCommands()
                .performCommand(player
                    .getServer()
                    .createCommandSourceStack(), command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
