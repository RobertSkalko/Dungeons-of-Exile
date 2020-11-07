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

        try {
            if (!world.isClient) {
                if (ticks % 20 == 0) {
                    if (!isDimensionAllowedToPlaceIn()) {
                        this.world.breakBlock(pos, true);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    static Integer MAX_DISTANCE = null;

    static int getMaxDistance(World world) {
        // seems WORLD BORDER GETTING IS VERY LAGGY, SO CACHE IT
        if (MAX_DISTANCE == null) {
            WorldBorder wb = world.getWorldBorder();
            MAX_DISTANCE = Math.abs((int) Math.min(Math.min(wb
                .getBoundSouth(), wb
                .getBoundNorth()), Math.min(wb
                .getBoundEast(), wb
                .getBoundWest())) / 10);
        }

        if (MAX_DISTANCE == null) {
            MAX_DISTANCE = 10000;
            System.out.print("failed to get world border");
        }
        return MAX_DISTANCE;
    }

    public void randomizeTeleportLocation() {

        try {

            int border = getMaxDistance(world);

            int x = world.random.nextInt(border);
            int z = world.random.nextInt(border);

            int chunkX = x / 16;
            int chunkZ = z / 16;

            World dimworld = world.getServer()
                .getWorld(RegistryKey.of(Registry.DIMENSION, this.dimensionId));

            Chunk loadTheDamnChunk = dimworld.getChunk(chunkX, chunkZ);

            int y = dimworld.getTopY(Heightmap.Type.WORLD_SURFACE, x, z);

            if (!isPlaceValidForTp(world, new BlockPos(x, y, z))) {
                randomizeTeleportLocation();
                // if players would spawn in lava, randomize location again
            } else {
                this.tpPos = new BlockPos(x, y, z);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static boolean isPlaceValidForTp(World world, BlockPos pos) {
        return world.getBlockState(pos)
            .getFluidState()
            .isEmpty() && world.getBlockState(pos.down())
            .getFluidState()
            .isEmpty();
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
