package com.robertx22.world_of_exile.mixins;

import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(StructuresConfig.class)
public interface StructuresConfigAccessor {

    @Accessor("structures")
    Map<StructureFeature<?>, StructureConfig> getGSStructureFeatures();

    @Accessor("structures")
    void setGSStructureFeatures(Map<StructureFeature<?>, StructureConfig> structureFeatures);
}
