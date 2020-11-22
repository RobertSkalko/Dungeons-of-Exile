package com.robertx22.world_of_exile.mixin_methods;

import com.robertx22.world_of_exile.main.entities.MobData;
import com.robertx22.world_of_exile.main.entities.OnMobSpecialSpawn;
import com.robertx22.world_of_exile.main.entities.registration.MobManager;
import com.robertx22.world_of_exile.main.entities.spawn.LavaTurnToObsidianSpawner;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OnLavaTurnToObsidian {

    public static Block onTurnToObsidian(FluidBlock fluid, Block block, World world, BlockPos pos, BlockState state) {
        if (fluid.is(Blocks.LAVA)) {
            if (block == Blocks.OBSIDIAN) {
                PlayerEntity player = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 50, x -> true);

                if (player != null) {
                    for (MobData data : MobManager.SET) {
                        for (LavaTurnToObsidianSpawner spawner : data.spawnEvents.lavaTurnToObsidian) {
                            if (spawner.chance > world.random.nextInt(100)) {
                                LivingEntity en = data.spawn(world, pos.up());
                                for (OnMobSpecialSpawn specialspawn : data.spawnEvents.onSpecialSpawn) {
                                    specialspawn.onSpecialSpawn(en, data);
                                }
                                return block;
                            }
                        }
                    }

                }

            }
        }
        return block;
    }

}
