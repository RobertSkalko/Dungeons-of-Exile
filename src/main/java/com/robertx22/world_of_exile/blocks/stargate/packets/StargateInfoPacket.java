package com.robertx22.world_of_exile.blocks.stargate.packets;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Ref;
import com.robertx22.world_of_exile.blocks.stargate.StargateClientInfo;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class StargateInfoPacket extends MyPacket<StargateInfoPacket> {

    public BlockPos pos;
    public Identifier dim;

    public StargateInfoPacket() {

    }

    public StargateInfoPacket(BlockPos pos, Identifier dim) {
        this.pos = pos;
        this.dim = dim;
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "stargate");
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
        StargateClientInfo.SYNCED_INFO = new StargateClientInfo(pos, dim);
    }

    @Override
    public MyPacket<StargateInfoPacket> newInstance() {
        return new StargateInfoPacket();
    }
}
