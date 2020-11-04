package com.robertx22.world_of_exile.blocks.stargate;

import com.robertx22.world_of_exile.main.ModBlocks;
import com.robertx22.world_of_exile.main.ModDimensions;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.DimensionType;

public class StargateBlockEntity extends BlockEntity implements Tickable {

    public BlockPos tpPos = new BlockPos(0, 90, 0);
    public Identifier dimensionId = ModDimensions.ABYSS;

    public StargateBlockEntity() {
        super(ModBlocks.INSTANCE.STARGATE_ENTITY);
    }

    int ticks = 0;

    @Override
    public void tick() {
        ticks++;

        if (ticks % 20 == 0) {
            if (!isDimensionAllowedToPlaceIn()) {
                this.world.breakBlock(pos, true);
            }
        }
    }

    public boolean isDimensionAllowedToPlaceIn() {
        if (this.world.getDimension() != world.getRegistryManager()
            .getDimensionTypes()
            .get(DimensionType.OVERWORLD_REGISTRY_KEY)) {
            return false;

        }
        return true;
    }

    public void randomizeTeleportLocation() {

        try {

            WorldBorder wb = world.getWorldBorder();

            int border = Math.abs((int) Math.min(Math.min(wb
                .getBoundSouth(), wb
                .getBoundNorth()), Math.min(wb
                .getBoundEast(), wb
                .getBoundWest())) / 10);

            int x = world.random.nextInt(border);
            int z = world.random.nextInt(border);

            int chunkX = x / 16;
            int chunkZ = z / 16;

            World dimworld = world.getServer()
                .getWorld(RegistryKey.of(Registry.DIMENSION, this.dimensionId));

            Chunk loadTheDamnChunk = dimworld.getChunk(chunkX, chunkZ);

            int y = dimworld.getTopY(Heightmap.Type.WORLD_SURFACE, x, z);

            this.tpPos = new BlockPos(x, y, z);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        tpPos = BlockPos.fromLong(tag.getLong("pos"));
        dimensionId = new Identifier(tag.getString("dim_id"));
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putLong("pos", tpPos.asLong());
        tag.putString("dim_id", dimensionId.toString());
        return super.toTag(tag);
    }

}
