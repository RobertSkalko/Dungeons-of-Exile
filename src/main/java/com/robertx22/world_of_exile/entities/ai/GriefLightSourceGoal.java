package com.robertx22.world_of_exile.entities.ai;

import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;

import java.util.Random;

public class GriefLightSourceGoal extends MoveToTargetPosGoal {
    private int counter;

    public GriefLightSourceGoal(PathAwareEntity mob, double speed, int range) {
        super(mob, speed, range);
    }

    @Override
    public double getDesiredSquaredDistanceToTarget() {
        return 3;
    }

    @Override
    public void start() {
        super.start();
        this.counter = 0;
    }

    @Override
    public void tick() {
        super.tick();

        if (hasReached()) {
            World world = this.mob.world;
            BlockPos blockPos = this.targetPos;

            Random random = this.mob.getRandom();

            Vec3d vec3d2;
            double e;
            if (this.counter > 0) {
                vec3d2 = this.mob.getVelocity();
                this.mob.setVelocity(vec3d2.x, 0.3D, vec3d2.z);
                if (!world.isClient) {
                    e = 0.08D;
                    ((ServerWorld) world).spawnParticles(new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(Items.STONE)), (double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.7D, (double) blockPos.getZ() + 0.5D, 3, ((double) random.nextFloat() - 0.5D) * 0.08D, ((double) random.nextFloat() - 0.5D) * 0.08D, ((double) random.nextFloat() - 0.5D) * 0.08D, 0.15000000596046448D);
                }
            }
            if (this.counter % 2 == 0) {
                vec3d2 = this.mob.getVelocity();
                this.mob.setVelocity(vec3d2.x, -0.3D, vec3d2.z);
            }
            if (this.counter > 60) {
                world.breakBlock(blockPos, true);
                if (!world.isClient) {
                    for (int i = 0; i < 20; ++i) {
                        e = random.nextGaussian() * 0.02D;
                        double f = random.nextGaussian() * 0.02D;
                        double g = random.nextGaussian() * 0.02D;
                        ((ServerWorld) world).spawnParticles(ParticleTypes.POOF, (double) blockPos.getX() + 0.5D, (double) blockPos.getY(), (double) blockPos.getZ() + 0.5D, 1, e, f, g, 0.15000000596046448D);
                    }

                }
            }
            ++this.counter;
        }

    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {

        if (pos.getY() > 60) {
            return false; // we only want them to grief underground
        }
        Chunk chunk = world.getChunk(pos.getX() >> 4, pos.getZ() >> 4, ChunkStatus.FULL, false);
        if (chunk == null) {
            return false;
        } else {
            return chunk.getBlockState(pos)
                .getLuminance() > 2 && chunk.getFluidState(pos)
                .isEmpty();
        }
    }
}
