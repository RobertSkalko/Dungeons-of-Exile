package com.robertx22.world_of_exile.main.entities.registration;

import com.robertx22.world_of_exile.entities.mob_data.*;
import com.robertx22.world_of_exile.main.entities.MobData;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MobManager {

    public static HashMap<EntityType, MobData> MAP = new HashMap<>();

    public static Set<MobData> SET = new HashSet<>();

    static {
        SET.add(new StoneGolem());
        SET.add(new WoodGolem());
        SET.add(new SnowGolem());
        SET.add(new IronOreGolem());
        SET.add(new RedstoneGolem());
        SET.add(new ObsidianGolem());
        SET.add(new SmallObsidianGolem());

        SET.add(new FireSlimeMob());
    }

    @Nullable
    public static MobData<LivingEntity> get(LivingEntity en) {

        if (en == null) {
            return null;
        }

        return MAP.getOrDefault(en.getType(), null);
    }

}
