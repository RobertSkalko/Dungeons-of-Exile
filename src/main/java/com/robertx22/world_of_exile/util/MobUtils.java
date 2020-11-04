package com.robertx22.world_of_exile.util;

import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class MobUtils {

    public static void particleInRadius(ParticleEffect type, Entity en) {

        if (en.world.isClient) {
            en.world.addParticle(type,
                en.getX() - 0.5F + en.world.random.nextFloat(),
                en.getY() - 0.5F + en.world.random.nextFloat(),
                en.getZ() - 0.5F + en.world.random.nextFloat(), 0, 0, 0);
        }

    }

    public static void tryLeap(Entity en, float range, double leapHeight, BlockPos targetPos) {
        if (targetPos == null) {
            return;
        }

        double distance = targetPos.getSquaredDistance(en.getBlockPos());

        if (distance > 2.0F * 2.0F && distance <= range * range) {
            double xDist = targetPos.getX() - en.getBlockPos()
                .getX();
            double zDist = targetPos.getZ() - en.getBlockPos()
                .getZ();
            if (xDist == 0) {
                xDist = 0.05D;
            }
            if (zDist == 0) {
                zDist = 0.05D;
            }
            double xzDist = MathHelper.sqrt(xDist * xDist + zDist * zDist);

            en.addVelocity(
                xDist / xzDist * 0.5D * 0.8D + en.getVelocity()
                    .getX() * 0.2D,
                leapHeight,
                zDist / xzDist * 0.5D * 0.8D + en.getVelocity()
                    .getZ() * 0.2D
            );

        }
    }
}
