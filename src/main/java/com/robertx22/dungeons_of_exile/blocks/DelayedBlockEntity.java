package com.robertx22.dungeons_of_exile.blocks;

import com.robertx22.dungeons_of_exile.main.DungeonConfig;
import com.robertx22.dungeons_of_exile.main.ModStuff;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Tickable;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class DelayedBlockEntity extends BlockEntity implements Tickable {

    public String executionString = "";

    public DelayedBlockEntity() {
        super(ModStuff.INSTANCE.DELAY_ENTITY);
    }

    @Override
    public void tick() {

        if (executionString.contains("mob")) {
            List<EntityType> mobs = new ArrayList<>(DungeonConfig.get()
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

        }

    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        executionString = tag.getString("ex");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putString("ex", executionString);
        return super.toTag(tag);
    }

}
