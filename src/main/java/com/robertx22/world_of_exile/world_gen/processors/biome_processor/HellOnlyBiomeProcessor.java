package com.robertx22.world_of_exile.world_gen.processors.biome_processor;

import com.mojang.serialization.Codec;
import com.robertx22.world_of_exile.main.CommonInit;
import com.robertx22.world_of_exile.main.ModWorldGen;
import com.robertx22.world_of_exile.main.WOE;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

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
    public Template.BlockInfo processBlock(IWorldReader worldView, BlockPos pos, BlockPos blockPos, Template.BlockInfo structureBlockInfo, Template.BlockInfo blockinfo2, PlacementSettings structurePlacementData) {

        ResourceLocation id = CommonInit.SERVER.registryAccess()
            .dimensionTypes()
            .getKey(worldView.dimensionType());

        if (id.getNamespace()
            .equals(WOE.MODID)) {
            return super.processBlock(worldView, pos, blockPos, structureBlockInfo, blockinfo2, structurePlacementData);

        } else {
            return blockinfo2;
        }

    }

    @Override
    protected IStructureProcessorType<?> getType() {
        return ModWorldGen.INSTANCE.HELL_ONLY_BIOME_PROCESSOR;
    }
}