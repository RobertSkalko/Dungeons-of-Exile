package com.robertx22.world_of_exile.blocks;

import com.robertx22.library_of_exile.utils.TeleportUtils;
import com.robertx22.world_of_exile.main.ModDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.Random;

public class HellPortalBlock extends Block {

    public HellPortalBlock() {
        super(Settings.of(Material.STONE)
            .noCollision()
            .nonOpaque()
            .strength(5F, 2));
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(100) == 0) {
            world.playSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
        }

        for (int i = 0; i < 4; ++i) {
            double d = (double) pos.getX() + random.nextDouble();
            double e = (double) pos.getY() + random.nextDouble();
            double f = (double) pos.getZ() + random.nextDouble();
            double g = ((double) random.nextFloat() - 0.5D) * 0.5D;
            double h = ((double) random.nextFloat() - 0.5D) * 0.5D;
            double j = ((double) random.nextFloat() - 0.5D) * 0.5D;
            int k = random.nextInt(2) * 2 - 1;
            if (!world.getBlockState(pos.west())
                .isOf(this) && !world.getBlockState(pos.east())
                .isOf(this)) {
                d = (double) pos.getX() + 0.5D + 0.25D * (double) k;
                g = (double) (random.nextFloat() * 2.0F * (float) k);
            } else {
                f = (double) pos.getZ() + 0.5D + 0.25D * (double) k;
                j = (double) (random.nextFloat() * 2.0F * (float) k);
            }

            world.addParticle(ParticleTypes.PORTAL, d, e, f, g, h, j);
        }

    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        try {

            if (world.isClient) {
                return;
            }

            if (entity.age % 20 != 0) {
                return;
            }

            if (!world.getRegistryManager()
                .getDimensionTypes()
                .getId(world.getDimension())
                .equals(DimensionType.OVERWORLD_ID)) {
                return;
            }

            if (entity instanceof PlayerEntity) {

                // todo delay this

                BlockPos p = entity.getBlockPos();

                Identifier dimid = ModDimensions.HELL1;

                World hellworld = world.getServer()
                    .getWorld(RegistryKey.of(Registry.WORLD_KEY, dimid));

                ChunkPos cp = new ChunkPos(p);
                hellworld.getChunk(cp.x, cp.z);

                p = hellworld.getTopPosition(Heightmap.Type.WORLD_SURFACE, p);

                p = p.up(25);

                ((PlayerEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 20 * 10));
                ((PlayerEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 20 * 30));

                TeleportUtils.teleport((ServerPlayerEntity) entity, p, dimid);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
