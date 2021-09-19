package com.robertx22.world_of_exile.main;

import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class MixinConnector implements IMixinConnector {

    @Override
    public void connect() {
        Mixins.addConfiguration(
            "world_of_exile-mixins.json"
        );
    }
}