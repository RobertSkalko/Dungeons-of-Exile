package com.robertx22.dungeons_of_exile.world_gen.jigsaw.dungeon;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.robertx22.dungeons_of_exile.main.Ref;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static net.minecraft.structure.pool.StructurePool.Projection.RIGID;
import static net.minecraft.structure.pool.StructurePoolElement.method_30435;
import static net.minecraft.structure.processor.StructureProcessorLists.EMPTY;

public class DungeonPools {

    public static StructurePool STARTPOOL;

    static List<StrucInfo> ROOMS = new ArrayList<>();
    static List<StrucInfo> HALLS = new ArrayList<>();

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
            elements.add(Pair.of(method_30435(id.toString(), EMPTY), 1));
            return this;
        }

        private void crashIfAlreadyBuilt() {
            if (built) {
                throw new RuntimeException("Can't modify after building!");
            }
        }

        public void build() {
            crashIfAlreadyBuilt();
            this.built = true;
            StructurePools.register(new StructurePool(poolId, new Identifier("empty"), elements, RIGID));
        }
    }

    static {

        ROOMS.add(new StrucInfo("dungeon/rooms/spider_basement_to_treasure"));
        ROOMS.add(new StrucInfo("dungeon/rooms/small_room_campfire"));

        HALLS.add(new StrucInfo("dungeon/halls/straight_hall"));
        HALLS.add(new StrucInfo("dungeon/halls/curved_hall"));

        PoolBuilder hallBuilder = new PoolBuilder(new Identifier(Ref.MODID, "dungeon/halls"));
        HALLS.forEach(x -> hallBuilder.add(new Identifier(x.id)));
        hallBuilder.build();

        PoolBuilder roomBuilder = new PoolBuilder(new Identifier(Ref.MODID, "dungeon/rooms"));
        HALLS.forEach(x -> roomBuilder.add(new Identifier(x.id)));
        roomBuilder.build();

        PoolBuilder randomBuilder = new PoolBuilder(new Identifier(Ref.MODID, "dungeon/random"));
        HALLS.forEach(x -> randomBuilder.add(new Identifier(x.id)));
        ROOMS.forEach(x -> randomBuilder.add(new Identifier(x.id)));
        randomBuilder.build();

        STARTPOOL = StructurePools.register(new StructurePool(new Identifier(Ref.MODID, "dungeon/starts"), new Identifier("empty"),
            ImmutableList.of(
                Pair.of(method_30435(Ref.MODID + ":dungeon/starts/start1", EMPTY), 1),
                Pair.of(method_30435(Ref.MODID + ":dungeon/starts/tiny_ruin", EMPTY), 1)
            ), RIGID)

        );

        /*
        StructurePools.register(new StructurePool(new Identifier(Ref.MODID, "dungeon/rooms"), new Identifier("empty"),
            ImmutableList.of(
                Pair.of(method_30435(Ref.MODID + ":dungeon/rooms/spider_basement_to_treasure", EMPTY), 1),
                Pair.of(method_30435(Ref.MODID + ":dungeon/rooms/small_room_campfire", EMPTY), 1)
            ), RIGID)
        );

         */

        StructurePools.register(new StructurePool(new Identifier(Ref.MODID, "dungeon/near_starts"), new Identifier("empty"),
            ImmutableList.of(
                Pair.of(method_30435(Ref.MODID + ":dungeon/near_starts/near_start1", EMPTY), 1)
            ), RIGID)
        );

        /*
        StructurePools.register(new StructurePool(new Identifier(Ref.MODID, "dungeon/halls"), new Identifier("empty"),
            ImmutableList.of(
                Pair.of(method_30435(Ref.MODID + ":dungeon/halls/straight_hall", EMPTY), 1),
                Pair.of(method_30435(Ref.MODID + ":dungeon/halls/curved_hall", EMPTY), 1)
            ), RIGID)
        );

         */

        StructurePools.register(new StructurePool(new Identifier(Ref.MODID, "dungeon/treasure"), new Identifier("empty"),
            ImmutableList.of(
                Pair.of(method_30435(Ref.MODID + ":dungeon/treasure/treasure1", EMPTY), 1)
            ), RIGID)
        );

    }

    static class StrucInfo {
        String id;

        public StrucInfo(String id) {
            this.id = Ref.MODID + ":" + id;
        }
    }

}
