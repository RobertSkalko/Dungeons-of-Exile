package com.robertx22.world_of_exile.mixins;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(Biome.class)
public interface BiomeAccessor {
    @Accessor
    Map<Integer, List<StructureFeature<?>>> getStructures();

    @Mutable
    @Accessor
    void setStructures(Map<Integer, List<StructureFeature<?>>> structures);
}
