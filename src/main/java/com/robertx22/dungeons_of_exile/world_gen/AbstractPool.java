package com.robertx22.dungeons_of_exile.world_gen;

import com.mojang.datafixers.util.Pair;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static net.minecraft.structure.pool.StructurePool.Projection.RIGID;
import static net.minecraft.structure.pool.StructurePoolElement.method_30435;

public abstract class AbstractPool {
    Identifier poolId;

    protected StructurePool.Projection proj = RIGID;

    boolean built = false;
    List<Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer>> elements = new ArrayList<>();

    public abstract StructureProcessorList processorList();

    public AbstractPool(Identifier poolId) {
        this.poolId = poolId;
    }

    public AbstractPool add(Identifier id) {

        crashIfAlreadyBuilt();

        elements.add(Pair.of(method_30435(id.toString(), processorList()), 1000));
        return this;
    }

    public AbstractPool add(Identifier id, int weight) {
        crashIfAlreadyBuilt();
        elements.add(Pair.of(method_30435(id.toString(), processorList()), weight));
        return this;
    }

    public AbstractPool add(Identifier id, StructureProcessorList processors) {
        crashIfAlreadyBuilt();
        elements.add(Pair.of(method_30435(id.toString(), processors), 1000));
        return this;
    }

    private void crashIfAlreadyBuilt() {
        if (built) {
            throw new RuntimeException("Can't modify after building!");
        }
    }

    public StructurePool build() {
        crashIfAlreadyBuilt();
        this.built = true;
        return StructurePools.register(new StructurePool(poolId, new Identifier("empty"), elements, proj));
    }
}
