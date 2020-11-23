package com.robertx22.world_of_exile.main;

import com.robertx22.world_of_exile.main.entities.registration.ModEntityRenders;
import net.fabricmc.api.ClientModInitializer;

public class ClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ModEntityRenders.register();

    }
}
