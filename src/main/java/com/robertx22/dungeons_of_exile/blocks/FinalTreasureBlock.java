package com.robertx22.dungeons_of_exile.blocks;

import com.robertx22.dungeons_of_exile.main.DungeonConfig;
import com.robertx22.dungeons_of_exile.main.ModLoottables;
import com.robertx22.dungeons_of_exile.world_gen.tower.TowerDestroyer;
import net.minecraft.block.*;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomUtils;

public class FinalTreasureBlock extends Block {

    public FinalTreasureBlock() {
        super(AbstractBlock.Settings.of(Material.STONE)
            .strength(500, 5000));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult ray) {
        if (world.isClient) {
            return ActionResult.CONSUME;
        } else {

            try {

                if (!world.getEntitiesByClass(MobEntity.class, player.getBoundingBox()
                    .expand(25), x -> true)
                    .stream()
                    .anyMatch(e -> DungeonConfig.get()
                        .getAllowedBosses()
                        .stream()
                        .anyMatch(b -> b.equals(e.getType())))) {

                    world.setBlockState(pos.up(), Blocks.CHEST.getDefaultState());

                    ChestBlockEntity chest = (ChestBlockEntity) world.getBlockEntity(pos.up());
                    chest.setLootTable(ModLoottables.DUNGEON_DEFAULT, RandomUtils.nextLong());

                    // todo drop loot
                    TowerDestroyer.list.add(new TowerDestroyer(pos, world));
                } else {
                    player.sendMessage(new LiteralText("The tower guardian is waiting for you."), false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ActionResult.SUCCESS;
        }
    }
}

