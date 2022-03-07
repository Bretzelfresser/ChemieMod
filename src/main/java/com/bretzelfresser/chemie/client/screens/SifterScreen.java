package com.bretzelfresser.chemie.client.screens;

import com.bretzelfresser.chemie.Chemie;
import com.bretzelfresser.chemie.common.container.SifterContainer;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SifterScreen extends UtilContainerScreen<SifterContainer> {

	private static final ResourceLocation GUI = new ResourceLocation(Chemie.MODID, "textures/gui/sifter_gui.png");

	public SifterScreen(SifterContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);

	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
		this.font.func_243248_b(matrixStack, this.playerInventory.getDisplayName(), (float) this.playerInventoryTitleX,
				(float) this.playerInventoryTitleY, 4210752);
		this.font.func_243248_b(matrixStack, this.getTitle(), (float) 6, (float) 7, 4210752);
		drawFlame(matrixStack, container.isPowered(), 78, 54);
		drawArrow(matrixStack, 76, 36, container.getCounterPercentage(), false);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX,
			int mouseY) {
		this.minecraft.textureManager.bindTexture(GUI);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);
	}

}
