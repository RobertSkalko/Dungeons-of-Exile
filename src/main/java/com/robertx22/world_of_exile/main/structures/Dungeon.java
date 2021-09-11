package com.robertx22.world_of_exile.main.structures;

import com.robertx22.world_of_exile.config.ModConfig;
import com.robertx22.world_of_exile.main.ModProcessorLists;
import com.robertx22.world_of_exile.main.ModWorldGenIds;
import com.robertx22.world_of_exile.main.WOE;
import com.robertx22.world_of_exile.main.structures.base.StructureWrapper;
import com.robertx22.world_of_exile.world_gen.AbstractPool;
import com.robertx22.world_of_exile.world_gen.jigsaw.dungeon.ModDungeonFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class Dungeon extends StructureWrapper {

    public Dungeon() {
        super(BiomeSelectors.foundInOverworld(), ModWorldGenIds.DUNGEON_ID, true, ModConfig.get().DUNGEON, GenerationStep.Feature.UNDERGROUND_STRUCTURES);
    }

    @Override
    public ConfiguredStructureFeature createConfiguredFeature() {
        return feature.configure(new StructurePoolFeatureConfig(() -> {
            return this.startPool;
        }, 6));
    }

    @Override
    public StructureFeature createFeature() {
        return new ModDungeonFeature(StructurePoolFeatureConfig.CODEC);
    }

    @Override
    public StructurePool createPoolAndInitPools() {
        AbstractPool startBuilder = new Pool(WOE.id("dungeon/starts"));
        startBuilder.add(WOE.id("dungeon/starts/start"));

        AbstractPool roomBuilder = new Pool(WOE.id("dungeon/rooms"));
        roomBuilder.add(WOE.id("dungeon/halls/hall"), 3);
        roomBuilder.add(WOE.id("dungeon/near_start/triple_hall"));
        roomBuilder.add(WOE.id("dungeon/rooms/room1"));
        roomBuilder.add(WOE.id("dungeon/rooms/room2"));
        roomBuilder.add(WOE.id("dungeon/rooms/room3"));
        roomBuilder.add(WOE.id("dungeon/rooms/pool1"));

        roomBuilder.build();

        AbstractPool stairsBuilder = new Pool(WOE.id("dungeon/stairs"));
        stairsBuilder.add(WOE.id("dungeon/stairs/stairs"));
        stairsBuilder.build();

        return startBuilder.build();
    }

    static class Pool extends AbstractPool {

        public Pool(Identifier poolId) {
            super(poolId);
        }

        @Override
        public StructureProcessorList processorList() {
            return ModProcessorLists.INSTANCE.DEFAULT_PROCESSORS;

        }
    }

}
