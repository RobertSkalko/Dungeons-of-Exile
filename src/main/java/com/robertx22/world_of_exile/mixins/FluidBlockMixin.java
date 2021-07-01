package com.robertx22.world_of_exile.mixins;

import com.robertx22.world_of_exile.mixin_methods.OnLavaTurnToObsidian;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidBlock.class)
public class FluidBlockMixin {

    @Inject(method = "receiveNeighborFluids", at = @At(value = "RETURN", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z"))
    public void on$setBlockState(World world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> ci) {
        Block block = world.getBlockState(pos)
            .getBlock();
        FluidBlock fluid = (FluidBlock) (Object) this;
        OnLavaTurnToObsidian.onTurnToObsidian(fluid, block, world, pos, state);

    }

}
