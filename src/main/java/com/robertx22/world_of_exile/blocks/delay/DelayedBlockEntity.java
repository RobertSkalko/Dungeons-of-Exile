package com.robertx22.world_of_exile.blocks.delay;

import com.robertx22.world_of_exile.config.ModConfig;
import com.robertx22.world_of_exile.main.WOEBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.server.ServerWorld;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class DelayedBlockEntity extends TileEntity implements ITickableTileEntity {

    public String executionString = "";

    public DelayedBlockEntity() {
        super(WOEBlockEntities.DELAY_ENTITY.get());
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
                    level.setBlockAndUpdate(worldPosition, Blocks.AIR.defaultBlockState());
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
                    deployer = new TowerDeployer(level, worldPosition);
                    return;
                }
            } else if (executionString.contains("mob")) {
                List<EntityType> mobs = new ArrayList<>(ModConfig.get()
                    .getAllowedSpawnerMobs());
                EntityType type = mobs.get(RandomUtils.nextInt(0, mobs.size()));

                Entity en = type.create(level);

                en.moveTo(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), level.random.nextFloat() * 360.0F, 0.0F);
                ServerWorld sw = (ServerWorld) level;

                if (en instanceof MobEntity) {
                    MobEntity mob = (MobEntity) en;
                    mob.finalizeSpawn(sw, sw.getCurrentDifficultyAt(en.blockPosition()), SpawnReason.SPAWNER, null, null);
                }

                sw.addFreshEntityWithPassengers(en);
                level.setBlockAndUpdate(worldPosition, Blocks.AIR.defaultBlockState());

            } else if (executionString.equals("boss")) {
                List<EntityType> mobs = new ArrayList<>(ModConfig.get()
                    .getAllowedBosses());
                EntityType type = mobs.get(RandomUtils.nextInt(0, mobs.size()));

                Entity en = type.create(level);

                if (en instanceof MobEntity) {
                    MobEntity mob = (MobEntity) en;
                    mob.setPersistenceRequired();
                }

                en.moveTo(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), level.random.nextFloat() * 360.0F, 0.0F);
                ServerWorld sw = (ServerWorld) level;

                if (en instanceof MobEntity) {
                    MobEntity mob = (MobEntity) en;
                    mob.finalizeSpawn(sw, sw.getCurrentDifficultyAt(en.blockPosition()), SpawnReason.SPAWNER, null, null);
                }

                sw.addFreshEntityWithPassengers(en);
                level.setBlockAndUpdate(worldPosition, Blocks.AIR.defaultBlockState());
            }

        } catch (Exception e) {
            e.printStackTrace();
            level.setBlockAndUpdate(worldPosition, Blocks.AIR.defaultBlockState());
        }
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        super.load(state, tag);
        executionString = tag.getString("ex");
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        tag.putString("ex", executionString);
        return super.save(tag);
    }

}
