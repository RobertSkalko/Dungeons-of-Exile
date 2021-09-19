package com.robertx22.world_of_exile.mixins;

import com.robertx22.world_of_exile.mixin_ducks.SignDuck;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SignTileEntity.class)
public abstract class SignMixin implements SignDuck {

    @Accessor
    @Override
    public abstract ITextComponent[] getMessages();
}
