package com.robertx22.world_of_exile.blocks.stargate.packets;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Ref;
import com.robertx22.world_of_exile.util.CommandTeleportUtil;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class RequestStargateTeleportPacket extends MyPacket<RequestStargateTeleportPacket> {

    public BlockPos pos;
    public Identifier dim;

    public RequestStargateTeleportPacket() {

    }

    public RequestStargateTeleportPacket(BlockPos pos, Identifier dim) {
        this.pos = pos;
        this.dim = dim;
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "tp_with_stargate");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        dim = tag.readIdentifier();
        pos = tag.readBlockPos();
    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeIdentifier(dim);
        tag.writeBlockPos(pos);
    }

    @Override
    public void onReceived(PacketContext ctx) {
        CommandTeleportUtil.teleport((ServerPlayerEntity) ctx.getPlayer(), pos, dim);
    }

    @Override
    public MyPacket<RequestStargateTeleportPacket> newInstance() {
        return new RequestStargateTeleportPacket();
    }
}
