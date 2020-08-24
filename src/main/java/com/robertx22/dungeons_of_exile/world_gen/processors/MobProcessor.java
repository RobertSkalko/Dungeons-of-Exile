package com.robertx22.dungeons_of_exile.world_gen.processors;

import com.mojang.serialization.Codec;
import com.robertx22.dungeons_of_exile.main.ModWorldGen;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class MobProcessor extends StructureProcessor {

    public static final Codec<MobProcessor> CODEC = Codec.unit(MobProcessor::new);

    @Override
    public Structure.StructureBlockInfo process(WorldView worldView, BlockPos pos, BlockPos blockPos, Structure.StructureBlockInfo structureBlockInfo, Structure.StructureBlockInfo blockinfo2, StructurePlacementData structurePlacementData) {
        BlockState currentState = blockinfo2.state;
        BlockState resultState = blockinfo2.state;

        try {
            if (currentState.getBlock() == Blocks.ZOMBIE_HEAD) {
                resultState = Blocks.AIR.getDefaultState();

                /*
                ChunkRegion chunk = (ChunkRegion) worldView;

                List<EntityType> mobs = Registry.ENTITY_TYPE.stream()
                    .filter(x -> x.getSpawnGroup() == SpawnGroup.MONSTER && x.getHeight() < 3)
                    .collect(Collectors.toList());
                EntityType type = mobs.get(RandomUtils.nextInt(0, mobs.size()));

                Entity en = type.create(chunk.toServerWorld());

                en.setPos(blockinfo2.pos.getX(), blockinfo2.pos.getY(), blockinfo2.pos.getZ());

                chunk.toServerWorld()
                    .spawnEntity(en);

                 */

                // CHUNK.TOSERVEWORLD() HERE FREEZES GAME! FIND ANOTHER WAY TO SPAWN MOBS

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Structure.StructureBlockInfo(blockinfo2.pos, resultState, blockinfo2.tag);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModWorldGen.INSTANCE.MOB_PROCESSOR;
    }
}


