package com.robertx22.world_of_exile.main;

import com.robertx22.world_of_exile.world_gen.processors.BeaconProcessor;
import com.robertx22.world_of_exile.world_gen.processors.SignProcessor;
import com.robertx22.world_of_exile.world_gen.processors.biome_processor.BiomeProcessor;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;

public class WOEProcessors {

    public static WOEProcessors INSTANCE = new WOEProcessors();

    public static void init() {

    }

    public IStructureProcessorType<BiomeProcessor> BIOME_PROCESSOR = IStructureProcessorType.register(WOE.MODID + ":biome_processor", BiomeProcessor.CODEC);
    public IStructureProcessorType<BeaconProcessor> BEACON_PROCESSOR = IStructureProcessorType.register(WOE.MODID + ":mob_processor", BeaconProcessor.CODEC);
    public IStructureProcessorType<SignProcessor> SIGN_PROCESSOR = IStructureProcessorType.register(WOE.MODID + ":sign_processor", SignProcessor.CODEC);

    public WOEProcessors() {

    }

}
