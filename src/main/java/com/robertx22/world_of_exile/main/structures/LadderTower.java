package com.robertx22.world_of_exile.main.structures;

import com.robertx22.world_of_exile.MyBiomeSelectors;
import com.robertx22.world_of_exile.config.ModConfig;
import com.robertx22.world_of_exile.main.ModProcessorLists;
import com.robertx22.world_of_exile.main.ModWorldGenIds;
import com.robertx22.world_of_exile.main.WOE;
import com.robertx22.world_of_exile.main.structures.base.StructureWrapper;
import com.robertx22.world_of_exile.world_gen.AbstractPool;
import com.robertx22.world_of_exile.world_gen.jigsaw.ladder_tower.LadderTowerStructure;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.StructureProcessorList;

// this doesnt work cus it needs very big pool size to make the TOP piece, but when i increase the size game crashes and says cant find structure registry
public class LadderTower extends StructureWrapper {

    public LadderTower() {
        super(MyBiomeSelectors.OVERWORLD_LAND, ModWorldGenIds.LADDER_TOWER_ID, true, ModConfig.get().LADDER_TOWER, GenerationStage.Decoration.SURFACE_STRUCTURES);
    }

    @Override
    public StructureFeature createConfiguredFeature() {
        return feature.configured(new VillageConfig(() -> {
            return this.startPool;
        }, 7));
    }

    @Override
    public Structure createFeature() {
        return new LadderTowerStructure(VillageConfig.CODEC);
    }

    @Override
    public JigsawPattern createPoolAndInitPools() {
        Pool startBuilder = new Pool(WOE.id("ladder_tower_start"));
        startBuilder.add(WOE.id("ladder_tower/start/start0"));

        Pool middleBuilder = new Pool(WOE.id("ladder_tower_middle"));
        middleBuilder.add(WOE.id("ladder_tower/middle/middle0"), 1000);
        middleBuilder.add(WOE.id("ladder_tower/middle/dark_room0"), 1000);
        middleBuilder.add(WOE.id("ladder_tower/middle/lava_pool"), 1000);
        middleBuilder.add(WOE.id("ladder_tower/middle/web_halls0"), 1000);
        middleBuilder.add(WOE.id("ladder_tower/middle/topsmall"), 1);
        middleBuilder.build();

        return startBuilder.build();
    }

    static class Pool extends AbstractPool {

        public Pool(ResourceLocation poolId) {
            super(poolId);
        }

        @Override
        public StructureProcessorList processorList() {
            return ModProcessorLists.INSTANCE.STONE_BRICK_TOWER;
        }

    }

}
