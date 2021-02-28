package com.robertx22.world_of_exile.main;

import com.robertx22.world_of_exile.world_gen.processors.BeaconProcessor;
import com.robertx22.world_of_exile.world_gen.processors.SignProcessor;
import com.robertx22.world_of_exile.world_gen.processors.biome_processor.BiomeProcessor;
import net.minecraft.structure.processor.StructureProcessorType;

public class ModWorldGen {

    public static ModWorldGen INSTANCE = new ModWorldGen();

    public static void init() {

    }

    public StructureProcessorType<BiomeProcessor> BIOME_PROCESSOR = StructureProcessorType.register(WOE.MODID + ":biome_processor", BiomeProcessor.CODEC);
    public StructureProcessorType<BeaconProcessor> BEACON_PROCESSOR = StructureProcessorType.register(WOE.MODID + ":mob_processor", BeaconProcessor.CODEC);
    public StructureProcessorType<SignProcessor> SIGN_PROCESSOR = StructureProcessorType.register(WOE.MODID + ":sign_processor", SignProcessor.CODEC);

    public ModWorldGen() {

    }

}
