package com.robertx22.dungeons_of_exile.world_gen.processors.sign_processors;

import net.minecraft.structure.Structure;

import java.util.List;

public abstract class SignTextProc {

    public abstract Structure.StructureBlockInfo getProcessed(Structure.StructureBlockInfo info, List<String> strings);

    public abstract boolean shouldProcess(List<String> strings);
}
