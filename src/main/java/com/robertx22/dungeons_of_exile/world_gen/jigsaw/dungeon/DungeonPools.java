package com.robertx22.dungeons_of_exile.world_gen.jigsaw.dungeon;

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

public class DungeonPools {

    public static StructurePool STARTPOOL;

    static List<StrucInfo> ROOMS = new ArrayList<>();

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

        PoolBuilder startBuilder = new PoolBuilder(new Identifier(Ref.MODID, "dungeon/starts"));
        startBuilder.add(new Identifier(Ref.MODID, "dungeon/starts/start"));
        STARTPOOL = startBuilder.build();

        ROOMS.add(new StrucInfo("dungeon/rooms/room1"));
        ROOMS.add(new StrucInfo("dungeon/rooms/room2"));
        ROOMS.add(new StrucInfo("dungeon/rooms/room3"));
        ROOMS.add(new StrucInfo("dungeon/rooms/pool1"));

        PoolBuilder roomBuilder = new PoolBuilder(new Identifier(Ref.MODID, "dungeon/rooms"));
        roomBuilder.add(new Identifier(Ref.MODID, "dungeon/halls/hall"), 3);
        roomBuilder.add(new Identifier(Ref.MODID, "dungeon/near_start/triple_hall"));

        ROOMS.forEach(x -> roomBuilder.add(new Identifier(x.id)));
        roomBuilder.build();

        PoolBuilder stairsBuilder = new PoolBuilder(new Identifier(Ref.MODID, "dungeon/stairs"));
        stairsBuilder.add(new Identifier(Ref.MODID, "dungeon/stairs/stairs"));
        stairsBuilder.build();

    }

    static class StrucInfo {
        String id;

        public StrucInfo(String id) {
            this.id = Ref.MODID + ":" + id;
        }
    }

}
