package com.robertx22.world_of_exile.main.entities;

import com.robertx22.world_of_exile.main.entities.spawn.LavaTurnToObsidianSpawner;
import com.robertx22.world_of_exile.main.entities.spawn.PlayerBreakBlockSpawner;

import java.util.HashSet;
import java.util.Set;

public class SpawnEvents {

    public Set<OnMobSpecialSpawn> onSpecialSpawn = new HashSet<>();

    public Set<PlayerBreakBlockSpawner> breakBlock = new HashSet<>();
    public Set<LavaTurnToObsidianSpawner> lavaTurnToObsidian = new HashSet<>();

}
