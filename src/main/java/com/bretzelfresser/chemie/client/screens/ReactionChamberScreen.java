package com.bretzelfresser.chemie.client.screens;

import com.bretzelfresser.chemie.common.container.ReactionChamberContainer;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ReactionChamberScreen extends UtilContainerScreen<ReactionChamberContainer> {

	public ReactionChamberScreen(ReactionChamberContainer screenContainer, PlayerInventory inv,
			ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		drawBlankGuiWithInventory(matrixStack);
		drawSlotGrid(matrixStack, this.guiLeft + 5, this.guiTop + 17, 2, 2);
		drawSlotGrid(matrixStack, this.guiLeft +100, this.guiTop + 34, 2, 1);
		drawSlotGrid(matrixStack, this.guiLeft +50, this.guiTop + 51, 2, 1);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
		super.drawGuiContainerForegroundLayer(matrixStack, x, y);
		drawArrow(matrixStack, 50, 34, container.getProgressPercentage(), true);
		drawFlame(matrixStack, container.getFuelPercentage(), 6, 57);
		drawFlame(matrixStack, container.getFuelPercentage(), 25, 57);
	}

}
