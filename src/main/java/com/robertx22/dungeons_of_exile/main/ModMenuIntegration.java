package com.robertx22.dungeons_of_exile.main;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (screen) -> {
            return AutoConfig.getConfigScreen(DungeonConfig.class, screen)
                .get();
        };

    }

    @Override
    public String getModId() {
        return "dungeons_of_exile";
    }
}
