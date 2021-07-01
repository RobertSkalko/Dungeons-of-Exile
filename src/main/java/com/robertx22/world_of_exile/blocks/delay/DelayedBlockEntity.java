package com.robertx22.world_of_exile.blocks.delay;

import com.robertx22.world_of_exile.config.ModConfig;
import com.robertx22.world_of_exile.main.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Tickable;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class DelayedBlockEntity extends BlockEntity implements Tickable {

    public String executionString = "";

    public DelayedBlockEntity() {
        super(ModBlocks.INSTANCE.DELAY_ENTITY);
    }

    TowerDeployer deployer;

    int ticks = 0;

    @Override
    public void tick() {
        ticks++;

        if (ticks < 5) {
            return;
        }

        if (ticks % 5 == 0) {
            if (deployer != null) {
                if (deployer.isDone()) {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    return;
                } else {
                    deployer.onTick();
                    return;
                }
            }
        }

        try {

            if (executionString.contains("deploy")) {
                if (deployer == null) {
                    deployer = new TowerDeployer(world, pos);
                    return;
                }
            } else if (executionString.contains("mob")) {
                List<EntityType> mobs = new ArrayList<>(ModConfig.get()
                    .getAllowedSpawnerMobs());
                EntityType type = mobs.get(RandomUtils.nextInt(0, mobs.size()));

                Entity en = type.create(world);

                en.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), world.random.nextFloat() * 360.0F, 0.0F);
                ServerWorld sw = (ServerWorld) world;

                if (en instanceof MobEntity) {
                    MobEntity mob = (MobEntity) en;
                    mob.initialize(sw, sw.getLocalDifficulty(en.getBlockPos()), SpawnReason.SPAWNER, null, null);
                }

                sw.spawnEntityAndPassengers(en);
                world.setBlockState(pos, Blocks.AIR.getDefaultState());

            } else if (executionString.equals("boss")) {
                List<EntityType> mobs = new ArrayList<>(ModConfig.get()
                    .getAllowedBosses());
                EntityType type = mobs.get(RandomUtils.nextInt(0, mobs.size()));

                Entity en = type.create(world);

                if (en instanceof MobEntity) {
                    MobEntity mob = (MobEntity) en;
                    mob.setPersistent();
                }

                en.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), world.random.nextFloat() * 360.0F, 0.0F);
                ServerWorld sw = (ServerWorld) world;

                if (en instanceof MobEntity) {
                    MobEntity mob = (MobEntity) en;
                    mob.initialize(sw, sw.getLocalDifficulty(en.getBlockPos()), SpawnReason.SPAWNER, null, null);
                }

                sw.spawnEntityAndPassengers(en);
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }

        } catch (Exception e) {
            e.printStackTrace();
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }
    }

    @Override
    public void fromTag(BlockState state, NbtCompound tag) {
        super.fromTag(state, tag);
        executionString = tag.getString("ex");
    }

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        tag.putString("ex", executionString);
        return super.writeNbt(tag);
    }

}
