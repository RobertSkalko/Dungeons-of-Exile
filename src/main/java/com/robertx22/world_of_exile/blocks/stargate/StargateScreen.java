package com.robertx22.world_of_exile.blocks.stargate;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.world_of_exile.main.WOE;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

public class StargateScreen extends Screen {

    static int WIDTH = 176;
    static int HEIGHT = 207;

    protected StargateScreen() {
        super(new LiteralText(""));
        this.sizeX = WIDTH;
        this.sizeY = HEIGHT;
    }

    MinecraftClient mc = MinecraftClient.getInstance();

    public int guiLeft = 0;
    public int guiTop = 0;

    public int sizeX = 0;
    public int sizeY = 0;

    @Override
    protected void init() {
        super.init();

        this.guiLeft = (this.width - this.sizeX) / 2;
        this.guiTop = (this.height - this.sizeY) / 2;
    }

    private static final Identifier BACKGROUND = new Identifier(WOE.MODID, "textures/gui/stargate.png");

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        mc.getTextureManager()
            .bindTexture(BACKGROUND);

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        drawTexture(matrix, mc.getWindow()
                .getScaledWidth() / 2 - sizeX / 2,
            mc.getWindow()
                .getScaledHeight() / 2 - sizeY / 2, 0, 0, sizeX, sizeY
        );

        super.render(matrix, x, y, ticks);

        buttons.forEach(b -> b.renderToolTip(matrix, x, y));

    }

}
