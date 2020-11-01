package com.robertx22.world_of_exile.world_gen.jigsaw.dungeon;

import com.robertx22.world_of_exile.main.ModProcessors;
import com.robertx22.world_of_exile.main.WOE;
import com.robertx22.world_of_exile.world_gen.AbstractPool;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class DungeonPools {

    public static StructurePool STARTPOOL;

    static List<StrucInfo> ROOMS = new ArrayList<>();

    public static void init() {

    }

    static class Pool extends AbstractPool {

        public Pool(Identifier poolId) {
            super(poolId);
        }

        @Override
        public StructureProcessorList processorList() {
            return ModProcessors.INSTANCE.DEFAULT_PROCESSORS;

        }
    }

    static {

        AbstractPool startBuilder = new Pool(new Identifier(WOE.MODID, "dungeon/starts"));
        startBuilder.add(new Identifier(WOE.MODID, "dungeon/starts/start"));
        STARTPOOL = startBuilder.build();

        ROOMS.add(new StrucInfo("dungeon/rooms/room1"));
        ROOMS.add(new StrucInfo("dungeon/rooms/room2"));
        ROOMS.add(new StrucInfo("dungeon/rooms/room3"));
        ROOMS.add(new StrucInfo("dungeon/rooms/pool1"));

        AbstractPool roomBuilder = new Pool(new Identifier(WOE.MODID, "dungeon/rooms"));
        roomBuilder.add(new Identifier(WOE.MODID, "dungeon/halls/hall"), 3);
        roomBuilder.add(new Identifier(WOE.MODID, "dungeon/near_start/triple_hall"));

        ROOMS.forEach(x -> roomBuilder.add(new Identifier(x.id)));
        roomBuilder.build();

        AbstractPool stairsBuilder = new Pool(new Identifier(WOE.MODID, "dungeon/stairs"));
        stairsBuilder.add(new Identifier(WOE.MODID, "dungeon/stairs/stairs"));
        stairsBuilder.build();

    }

    static class StrucInfo {
        String id;

        public StrucInfo(String id) {
            this.id = WOE.MODID + ":" + id;
        }
    }

}
