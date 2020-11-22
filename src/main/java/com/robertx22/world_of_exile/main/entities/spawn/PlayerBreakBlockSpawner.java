package com.robertx22.world_of_exile.main.entities.spawn;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.Tag;
import org.apache.commons.lang3.RandomUtils;

import java.util.HashSet;
import java.util.Set;

public class PlayerBreakBlockSpawner extends BaseSpawner {

    Set<Tag.Identified<Block>> blockTagsIncluded = new HashSet<>();
    Set<Block> blocksIncluded = new HashSet<>();

    public PlayerBreakBlockSpawner(float chance, Set<Tag.Identified<Block>> blockTagsIncluded, Set<Block> blocksIncluded) {
        super(chance);
        this.blockTagsIncluded = blockTagsIncluded;
        this.blocksIncluded = blocksIncluded;
    }

    public boolean canSpawn(Block block, PlayerEntity player) {

        if (chance > RandomUtils.nextInt(0, 101)) {

            if (blocksIncluded.contains(block) || blockTagsIncluded.stream()
                .anyMatch(x -> block.isIn(x))) {
                return true;
            }

        }
        return false;
    }
}
