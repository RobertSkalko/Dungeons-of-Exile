package com.robertx22.dungeons_of_exile.world_gen.processors.sign_processors;

import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.world.WorldView;

import java.util.List;

public abstract class SignTextProc {

    public abstract Structure.StructureBlockInfo getProcessed(WorldView worldView, Structure.StructureBlockInfo info, StructurePlacementData data, List<String> strings);

    public abstract boolean shouldProcess(List<String> strings);
}
