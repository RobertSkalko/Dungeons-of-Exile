package com.robertx22.dungeons_of_exile.world_gen.jigsaw.big_tower;

import com.mojang.datafixers.util.Pair;
import com.robertx22.dungeons_of_exile.main.ModWorldGen;
import com.robertx22.dungeons_of_exile.main.Ref;
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

public class BigTowerPools {

    public static StructurePool STARTPOOL;

    public static void init() {

    }

    static class PoolBuilder {
        Identifier poolId;

        boolean built = false;
        List<Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer>> elements = new ArrayList<>();

        public PoolBuilder(Identifier poolId) {
            this.poolId = poolId;
        }

        public PoolBuilder add(Identifier id) {
            crashIfAlreadyBuilt();
            elements.add(Pair.of(method_30435(id.toString(), ModWorldGen.INSTANCE.DEFAULT_PROCESSORS), 1));
            return this;
        }

        public PoolBuilder add(Identifier id, int weight) {
            crashIfAlreadyBuilt();
            elements.add(Pair.of(method_30435(id.toString(), ModWorldGen.INSTANCE.DEFAULT_PROCESSORS), weight));
            return this;
        }

        public PoolBuilder add(Identifier id, StructureProcessorList processors) {
            crashIfAlreadyBuilt();
            elements.add(Pair.of(method_30435(id.toString(), processors), 1));
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
            return StructurePools.register(new StructurePool(poolId, new Identifier("empty"), elements, RIGID));
        }
    }

    static {

        PoolBuilder startBuilder = new PoolBuilder(new Identifier(Ref.NEWMODID, "bigtower_start"));
        startBuilder.add(new Identifier(Ref.MODID, "bigtower/start/start0"));
        STARTPOOL = startBuilder.build();

        PoolBuilder middleBuilder = new PoolBuilder(new Identifier(Ref.NEWMODID, "bigtower_middle"));
        middleBuilder.add(new Identifier(Ref.MODID, "bigtower/middle/middle0"));
        middleBuilder.build();

    }

    static class StrucInfo {
        String id;

        public StrucInfo(String id) {
            this.id = Ref.MODID + ":" + id;
        }
    }

}
