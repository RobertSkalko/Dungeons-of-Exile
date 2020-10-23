package com.robertx22.dungeons_of_exile.main;

import com.robertx22.dungeons_of_exile.world_gen.tower.TowerPieces;
import net.minecraft.structure.StructurePieceType;

public class ModStructurePieces {

    public static StructurePieceType TowerPiece = StructurePieceType.register(TowerPieces.Piece::new, Ref.MODID + ":tower");

    public static void init() {

    }

}
