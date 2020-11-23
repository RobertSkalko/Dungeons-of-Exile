package com.robertx22.world_of_exile.entities.on_special_spawns;

import com.robertx22.world_of_exile.main.entities.MobData;
import com.robertx22.world_of_exile.main.entities.OnMobSpecialSpawn;
import com.robertx22.world_of_exile.main.entities.spawn.PlayerBreakBlockSpawner;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.RandomUtils;

public class OnSpecialOreGolemSpawn extends OnMobSpecialSpawn {

    public float chance;

    public OnSpecialOreGolemSpawn(float chance) {
        this.chance = chance;
    }

    @Override
    public void onSpecialSpawn(LivingEntity en, MobData data) {

        if (chance > RandomUtils.nextInt(0, 101)) {
            try {
                PlayerBreakBlockSpawner spawner = data.spawnEvents.breakBlock.stream()
                    .filter(s -> s instanceof PlayerBreakBlockSpawner)
                    .findFirst()
                    .get();

                for (int x = -2; x < 2; x++) {
                    for (int y = -2; y < 2; y++) {
                        for (int z = -2; z < 2; z++) {
                            BlockPos pos = en.getBlockPos()
                                .add(x, y, z);
                            Block block = en.world.getBlockState(pos)
                                .getBlock();

                            if (spawner.blockMatches(block)) {
                                en.world.breakBlock(pos, true);
                                data.spawn(en.world, pos);
                            }

                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
