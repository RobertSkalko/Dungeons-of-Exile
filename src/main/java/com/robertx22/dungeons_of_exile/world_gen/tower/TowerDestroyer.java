package com.robertx22.dungeons_of_exile.world_gen.tower;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.concurrent.ConcurrentLinkedDeque;

public class TowerDestroyer {

    public static ConcurrentLinkedDeque<TowerDestroyer> list = new ConcurrentLinkedDeque<>();

    BlockPos pos;
    World world;

    int tick = 0;

    public TowerDestroyer(BlockPos pos, World world) {
        this.pos = pos;
        this.world = world;

    }

    int explosions = 0;

    public boolean isDone() {
        return explosions > 3;
    }

    public void onTick() {
        tick++;

        if (tick % 60 == 0) {
            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 5F, true, Explosion.DestructionType.BREAK);
            explosions++;
        }
    }
}
