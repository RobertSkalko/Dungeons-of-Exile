package com.robertx22.dungeons_of_exile.main;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

@Config(name = "dungeons_of_exile")
public class DungeonConfig implements ConfigData {

    public boolean ENABLE_MOD = true;

    public static DungeonConfig get() {
        return AutoConfig.getConfigHolder(DungeonConfig.class)
            .getConfig();
    }

}
