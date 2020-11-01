package com.robertx22.world_of_exile.main;

import com.robertx22.world_of_exile.config.ModConfig;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (screen) -> {
            return AutoConfig.getConfigScreen(ModConfig.class, screen)
                .get();
        };

    }

    @Override
    public String getModId() {
        return WOE.MODID;
    }
}
