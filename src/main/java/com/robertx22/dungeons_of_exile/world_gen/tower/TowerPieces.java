package com.robertx22.dungeons_of_exile.world_gen.tower;

import com.google.common.collect.ImmutableList;
import com.robertx22.dungeons_of_exile.main.ModStructurePieces;
import com.robertx22.dungeons_of_exile.main.Ref;
import com.robertx22.dungeons_of_exile.world_gen.processors.SignProcessor;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.*;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.structure.processor.RuleStructureProcessor;
import net.minecraft.structure.processor.StructureProcessorRule;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.RandomBlockMatchRuleTest;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TowerPieces {
    private static final List<Identifier> TOPS = Arrays.asList(new Identifier(Ref.MODID, "tower/top/top0"));
    private static final List<Identifier> BOTTOMS = Arrays.asList(new Identifier(Ref.MODID, "tower/bottom/bottom0"));
    private static final List<Identifier> MIDDLES = Arrays.asList(new Identifier(Ref.MODID, "tower/middle/middle0"), new Identifier(Ref.MODID, "tower/middle/middle1"), new Identifier(Ref.MODID, "tower/middle/middle2"));

    static Identifier random(List<Identifier> list, Random ran) {
        if (list.size() == 1) {
            return list.get(0);
        }
        return list.get(ran.nextInt(list.size()));
    }

    public static void addPieces(StructureManager manager, BlockPos pos, BlockRotation rotation, List<StructurePiece> pieces, Random random) {

        int amount = 2 + random.nextInt(3);

        int y = 0;

        Identifier bottom = random(BOTTOMS, random);
        pieces.add(new TowerPieces.Piece(manager, bottom, pos, rotation, y));
        y += manager.getStructure(bottom)
            .getSize()
            .getY();

        for (int i = 0; i < amount; i++) {
            Identifier id = random(MIDDLES, random);
            pieces.add(new TowerPieces.Piece(manager, id, pos, rotation, y));
            y += manager.getStructure(id)
                .getSize()
                .getY();
        }

        Identifier top = random(TOPS, random);
        pieces.add(new TowerPieces.Piece(manager, top, pos, rotation, y));

    }

    public static class Piece extends SimpleStructurePiece {
        private final Identifier template;
        private final BlockRotation rotation;

        public Piece(StructureManager manager, Identifier identifier, BlockPos pos, BlockRotation rotation, int yOffset) {
            super(ModStructurePieces.TowerPiece, 0);
            this.template = identifier;
            this.pos = pos.add(0, yOffset, 0);
            this.rotation = rotation;
            this.initializeStructureData(manager);
        }

        public Piece(StructureManager manager, CompoundTag tag) {
            super(ModStructurePieces.TowerPiece, tag);
            this.template = new Identifier(tag.getString("Template"));
            this.rotation = BlockRotation.valueOf(tag.getString("Rot"));
            this.initializeStructureData(manager);
        }

        private void initializeStructureData(StructureManager manager) {
            Structure structure = manager.getStructureOrBlank(this.template);
            StructurePlacementData structurePlacementData = (new StructurePlacementData()).setRotation(this.rotation)
                .setMirror(BlockMirror.NONE)
                .setPosition(pos)
                .addProcessor(new SignProcessor())
                .addProcessor(new RuleStructureProcessor(ImmutableList.of(new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.SMOOTH_STONE, 0.4F), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState())))
                )
                .addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);

            this.setStructureData(structure, this.pos, structurePlacementData);
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putString("Template", this.template.toString());
            tag.putString("Rot", this.rotation.name());
        }

        @Override
        protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess serverWorldAccess, Random random, BlockBox boundingBox) {
        }

        @Override
        public boolean generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {

            StructurePlacementData structurePlacementData = (new StructurePlacementData()).setRotation(this.rotation)
                .setMirror(BlockMirror.NONE)
                .setPosition(this.pos)
                .addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);

            BlockPos save = pos;

            BlockPos bpos = this.pos.add(Structure.transform(structurePlacementData, new BlockPos(0, 0, 0)));

            int yheight = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, bpos.getX(), bpos.getZ());
            this.pos = bpos.add(0, yheight - 90 - 1, 0);

            boolean bl = super.generate(world, structureAccessor, chunkGenerator, random, boundingBox, chunkPos, blockPos);

            this.pos = save;

            return bl;
        }
    }
}
