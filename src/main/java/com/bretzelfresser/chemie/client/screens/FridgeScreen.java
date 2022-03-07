package com.bretzelfresser.chemie.client.screens;

import com.bretzelfresser.chemie.Chemie;
import com.bretzelfresser.chemie.common.container.FridgeContainer;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FridgeScreen extends ContainerScreen<FridgeContainer> {

	private static final ResourceLocation DISPLAY_CASE_GUI = new ResourceLocation(Chemie.MODID,
			"textures/gui/bigger_chest_gui.png");

	public FridgeScreen(FridgeContainer screenContainer, PlayerInventory inv,
			ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		ySize = 150;
		xSize = 176;
		//guiTop = 10;
		//guiLeft = 10;
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
				(float) this.playerInventoryTitleY - 16, 4210752);
		this.font.func_243248_b(matrixStack, this.getTitle(), (float) 6,
				(float) 7, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX,
			int mouseY) {
		this.minecraft.textureManager.bindTexture(DISPLAY_CASE_GUI);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);
	}

}
