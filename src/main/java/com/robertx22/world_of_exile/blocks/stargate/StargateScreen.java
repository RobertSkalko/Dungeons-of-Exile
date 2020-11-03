package com.robertx22.world_of_exile.blocks.stargate;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.GuiUtils;
import com.robertx22.world_of_exile.blocks.stargate.packets.RequestStargateTeleportPacket;
import com.robertx22.world_of_exile.main.WOE;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

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

        this.addButton(new TeleportButton(this.guiLeft + WIDTH / 2 - BUTTON_SIZE_X / 2, this.guiTop + HEIGHT - BUTTON_SIZE_Y - 10));
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

        renderDimension(matrix);
        renderPos(matrix);

    }

    private void renderDimension(MatrixStack matrix) {
        double scale = 1;
        String str = "Dimension: " + StargateClientInfo.SYNCED_INFO.dimensionId.toString();
        int xp = (int) (guiLeft + (WIDTH / 2F));
        int yp = (int) (guiTop + 20);
        GuiUtils.renderScaledText(matrix, xp, yp, scale, str, Formatting.GOLD);
    }

    private void renderPos(MatrixStack matrix) {
        double scale = 1;
        String str = "Teleport Position: ";
        int xp = (int) (guiLeft + (WIDTH / 2F));
        int yp = (int) (guiTop + 50);
        GuiUtils.renderScaledText(matrix, xp, yp, scale, str, Formatting.GOLD);

        str = StargateClientInfo.SYNCED_INFO.tpPos.toString();
        yp += 20;
        GuiUtils.renderScaledText(matrix, xp, yp, scale, str, Formatting.GOLD);

    }

    private static final Identifier BUTTON_TEX = new Identifier(WOE.MODID, "textures/gui/teleportbutton.png");
    static int BUTTON_SIZE_X = 61;
    static int BUTTON_SIZE_Y = 20;

    public class TeleportButton extends TexturedButtonWidget {

        public TeleportButton(int xPos, int yPos) {
            super(xPos, yPos, BUTTON_SIZE_X, BUTTON_SIZE_Y, 0, 0, BUTTON_SIZE_Y, BUTTON_TEX, (button) -> {
                Packets.sendToServer(new RequestStargateTeleportPacket(StargateClientInfo.SYNCED_INFO.tpPos, StargateClientInfo.SYNCED_INFO.dimensionId));
            });
        }

        @Override
        public void renderToolTip(MatrixStack matrix, int x, int y) {
            if (isInside(x, y)) {

                try {
                    List<Text> tooltip = new ArrayList<>();

                    tooltip.add(new LiteralText("Teleport to dimension").formatted(Formatting.LIGHT_PURPLE));

                    GuiUtils.renderTooltip(matrix,
                        tooltip, x, y);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        public boolean isInside(int x, int y) {
            return GuiUtils.isInRect(this.x, this.y, BUTTON_SIZE_X, BUTTON_SIZE_Y, x, y);
        }

    }

}
