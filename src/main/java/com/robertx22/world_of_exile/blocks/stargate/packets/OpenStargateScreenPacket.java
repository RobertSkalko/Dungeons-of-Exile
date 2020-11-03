package com.robertx22.world_of_exile.blocks.stargate.packets;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Ref;
import com.robertx22.world_of_exile.blocks.stargate.ClientOnly;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class OpenStargateScreenPacket extends MyPacket<OpenStargateScreenPacket> {

    public OpenStargateScreenPacket() {

    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "open_stargate");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {

    }

    @Override
    public void saveToData(PacketByteBuf tag) {

    }

    @Override
    public void onReceived(PacketContext ctx) {
        ClientOnly.openStargateScreen();
    }

    @Override
    public MyPacket<OpenStargateScreenPacket> newInstance() {
        return new OpenStargateScreenPacket();
    }
}

