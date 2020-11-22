package com.robertx22.world_of_exile.main.entities;

import com.robertx22.world_of_exile.entities.test.FireGolem;
import com.robertx22.world_of_exile.entities.test.FrostGolem;
import com.robertx22.world_of_exile.entities.test.IronOreGolem;
import com.robertx22.world_of_exile.entities.test.RedstoneGolem;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MobManager {

    public static HashMap<EntityType, MobData> MAP = new HashMap<>();

    public static List<MobData> LIST = new ArrayList<>();

    static {
        LIST.add(new FireGolem());
        LIST.add(new FrostGolem());
        LIST.add(new IronOreGolem());
        LIST.add(new RedstoneGolem());
    }

    @Nullable
    public static MobData<LivingEntity> get(LivingEntity en) {

        if (en == null) {
            return null;
        }

        return MAP.getOrDefault(en.getType(), null);
    }

}
