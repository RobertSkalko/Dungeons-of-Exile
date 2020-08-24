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
            elements.add(Pair.of(method_30435(id.toString(), ModWorldGen.INSTANCE.DEFAULT_PROCESSORS), 1));
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
        startBuilder.add(new Identifier(Ref.MODID, "dungeon/starts/start1"));
        startBuilder.add(new Identifier(Ref.MODID, "dungeon/starts/tiny_ruin"));
        STARTPOOL = startBuilder.build();

        ROOMS.add(new StrucInfo("dungeon/rooms/spider_basement_to_treasure"));
        ROOMS.add(new StrucInfo("dungeon/rooms/small_room_campfire"));
        ROOMS.add(new StrucInfo("dungeon/rooms/hall_and_room"));
        ROOMS.add(new StrucInfo("dungeon/rooms/lava"));
        ROOMS.add(new StrucInfo("dungeon/rooms/zombie_haystack"));

        HALLS.add(new StrucInfo("dungeon/halls/straight_hall"));
        HALLS.add(new StrucInfo("dungeon/halls/curved_hall"));

        PoolBuilder hallBuilder = new PoolBuilder(new Identifier(Ref.MODID, "dungeon/halls"));
        HALLS.forEach(x -> hallBuilder.add(new Identifier(x.id)));
        hallBuilder.build();

        PoolBuilder roomBuilder = new PoolBuilder(new Identifier(Ref.MODID, "dungeon/rooms"));
        ROOMS.forEach(x -> roomBuilder.add(new Identifier(x.id)));
        roomBuilder.build();

        PoolBuilder randomBuilder = new PoolBuilder(new Identifier(Ref.MODID, "dungeon/random"));
        HALLS.forEach(x -> randomBuilder.add(new Identifier(x.id)));
        ROOMS.forEach(x -> randomBuilder.add(new Identifier(x.id)));
        randomBuilder.build();

        PoolBuilder nearBuilder = new PoolBuilder(new Identifier(Ref.MODID, "dungeon/near_starts"));
        nearBuilder.add(new Identifier(Ref.MODID, "dungeon/near_starts/near_start1"));
        nearBuilder.build();

        PoolBuilder treasureBuilder = new PoolBuilder(new Identifier(Ref.MODID, "dungeon/treasure"));
        treasureBuilder.add(new Identifier(Ref.MODID, "dungeon/treasure/treasure1"));
        treasureBuilder.build();

        PoolBuilder walladdonsbuilder = new PoolBuilder(new Identifier(Ref.MODID, "dungeon/wall_addons"));
        walladdonsbuilder.add(new Identifier(Ref.MODID, "dungeon/wall_addons/hidden_chance_chest"));
        walladdonsbuilder.add(new Identifier(Ref.MODID, "dungeon/wall_addons/hidden_room_path"));
        walladdonsbuilder.add(new Identifier(Ref.MODID, "dungeon/wall_addons/addon_nothing"));
        walladdonsbuilder.add(new Identifier(Ref.MODID, "dungeon/wall_addons/lava_secret_zombie_room"));
        walladdonsbuilder.build();

    }

    static class StrucInfo {
        String id;

        public StrucInfo(String id) {
            this.id = Ref.MODID + ":" + id;
        }
    }

}
