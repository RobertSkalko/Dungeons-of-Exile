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

                for (int x = -1; x < 1; x++) {
                    for (int y = -1; y < 1; y++) {
                        for (int z = -1; z < 1; z++) {
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
