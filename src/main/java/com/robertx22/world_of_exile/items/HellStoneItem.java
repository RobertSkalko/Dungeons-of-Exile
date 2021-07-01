package com.robertx22.world_of_exile.items;

import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.library_of_exile.utils.TeleportUtils;
import com.robertx22.world_of_exile.main.CreativeTabs;
import com.robertx22.world_of_exile.main.ModDimensions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

import java.util.List;

public class HellStoneItem extends Item {

    public HellStoneItem() {
        super(new Item.Settings().group(CreativeTabs.MAIN)
            .maxCount(64));
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity en) {

        if (world.isClient) {
            return stack;
        }
        if (!(en instanceof PlayerEntity)) {
            return stack;
        }
        if (!world.getRegistryManager()
            .getDimensionTypes()
            .getId(world.getDimension())
            .equals(ModDimensions.HELL1)) {
            return stack;
        }
        ServerPlayerEntity player = (ServerPlayerEntity) en;

        World spawnworld = world.getServer()
            .getWorld(player.getSpawnPointDimension());

        BlockPos p = player.getSpawnPointPosition();

        if (p == null) {
            p = player.getBlockPos();
            p = spawnworld.getTopPosition(Heightmap.Type.WORLD_SURFACE, p);
        }

        Identifier dim = player.getSpawnPointDimension()
            .getValue();

        ChunkPos cp = new ChunkPos(p);

        spawnworld.getChunk(cp.x, cp.z);

        stack.decrement(1);

        TeleportUtils.teleport((ServerPlayerEntity) en, p, dim);
        SoundUtils.playSound(en, SoundEvents.BLOCK_PORTAL_TRAVEL, 1, 1);

        return stack;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
    }

    @Override
    public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand handIn) {
        ItemStack itemStack = player.getStackInHand(handIn);

        player.setCurrentHand(handIn);
        return TypedActionResult.success(itemStack);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 60;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        tooltip.add(new LiteralText("Use to teleport back from hell to your home dimension."));
        tooltip.add(new LiteralText("Obtained by killing mobs in hell."));
    }

}
