package com.robertx22.world_of_exile.entities.on_special_spawns;

import com.robertx22.world_of_exile.main.entities.MobData;
import com.robertx22.world_of_exile.main.entities.OnMobSpecialSpawn;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

public class OnBigMobSpawnRemoveBlocksAround extends OnMobSpecialSpawn {

    @Override
    public void onSpecialSpawn(LivingEntity en, MobData data) {

        for (int x = -2; x < 2; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = -2; z < 2; z++) {
                    BlockPos pos = en.getBlockPos()
                        .add(x, y, z);
                    en.world.breakBlock(pos, true);
                }
            }
        }
    }
}
