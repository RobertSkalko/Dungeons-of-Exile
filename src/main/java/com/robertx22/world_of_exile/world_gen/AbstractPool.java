package com.robertx22.world_of_exile.world_gen;

import com.mojang.datafixers.util.Pair;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPatternRegistry;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.template.StructureProcessorList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractPool {
    ResourceLocation poolId;

    protected JigsawPattern.PlacementBehaviour proj = JigsawPattern.PlacementBehaviour.RIGID;

    boolean built = false;
    List<Pair<Function<JigsawPattern.PlacementBehaviour, ? extends JigsawPiece>, Integer>> elements = new ArrayList<>();

    public abstract StructureProcessorList processorList();

    public AbstractPool(ResourceLocation poolId) {
        this.poolId = poolId;
    }

    public AbstractPool add(ResourceLocation id) {

        crashIfAlreadyBuilt();

        elements.add(Pair.of(JigsawPiece.single(id.toString(), processorList()), 1000));
        return this;
    }

    public AbstractPool add(ResourceLocation id, int weight) {
        crashIfAlreadyBuilt();
        elements.add(Pair.of(JigsawPiece.single(id.toString(), processorList()), weight));
        return this;
    }

    public AbstractPool add(ResourceLocation id, StructureProcessorList processors) {
        crashIfAlreadyBuilt();
        elements.add(Pair.of(JigsawPiece.single(id.toString(), processors), 1000));
        return this;
    }

    private void crashIfAlreadyBuilt() {
        if (built) {
            throw new RuntimeException("Can't modify after building!");
        }
    }

    public JigsawPattern build() {
        crashIfAlreadyBuilt();
        this.built = true;
        return JigsawPatternRegistry.register(new JigsawPattern(poolId, new ResourceLocation("empty"), elements, proj));
    }
}
