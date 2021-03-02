package com.robertx22.world_of_exile.world_gen.processors.biome_processor;

import com.mojang.serialization.Codec;
import com.robertx22.world_of_exile.main.CommonInit;
import com.robertx22.world_of_exile.main.ModWorldGen;
import com.robertx22.world_of_exile.main.WOE;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import java.util.HashMap;

public class HellOnlyBiomeProcessor extends BiomeProcessor {
    public static final Codec<HellOnlyBiomeProcessor> CODEC = Codec.unit(HellOnlyBiomeProcessor::new);

    public static HashMap<String, BiomeProcessorType> MAP;

    static {
        MAP = new HashMap<>();
        reg(StoneBricksToBlackstone.getInstance());
    }

    @Override
    public HashMap<String, BiomeProcessorType> getMap() {
        return MAP;
    }

    static void reg(BiomeProcessorType proc) {
        MAP.put(proc.id(), proc);
    }

    @Override
    public Structure.StructureBlockInfo process(WorldView worldView, BlockPos pos, BlockPos blockPos, Structure.StructureBlockInfo structureBlockInfo, Structure.StructureBlockInfo blockinfo2, StructurePlacementData structurePlacementData) {

        Identifier id = CommonInit.SERVER.getRegistryManager()
            .getDimensionTypes()
            .getId(worldView.getDimension());

        if (id.getNamespace()
            .equals(WOE.MODID)) {
            return super.process(worldView, pos, blockPos, structureBlockInfo, blockinfo2, structurePlacementData);

        } else {
            return blockinfo2;
        }

    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModWorldGen.INSTANCE.HELL_ONLY_BIOME_PROCESSOR;
    }
}