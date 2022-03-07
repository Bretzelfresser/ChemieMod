package com.bretzelfresser.chemie.client.screens;

import com.bretzelfresser.chemie.Chemie;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class UtilContainerScreen<T extends Container> extends ContainerScreen<T> {

	protected static final ResourceLocation FLAME = new ResourceLocation(Chemie.MODID, "textures/gui/flame.png");
	protected static final ResourceLocation SLOT = new ResourceLocation(Chemie.MODID, "textures/gui/slot.png");
	protected static final ResourceLocation SLOT_FIELD = new ResourceLocation(Chemie.MODID, "textures/gui/slot_field.png");
	protected static final ResourceLocation ARROW = new ResourceLocation(Chemie.MODID, "textures/gui/arrow.png");
	protected static final ResourceLocation ARROW_LONG = new ResourceLocation(Chemie.MODID, "textures/gui/arrow_long.png");
	protected static final ResourceLocation BLANK_GUI = new ResourceLocation(Chemie.MODID, "textures/gui/inventory_blank.png");

	public UtilContainerScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}

	/**
	 * 
	 * @param matrixStack
	 * @param lit         - whether the Flame should be lit or not
	 * @param x           - the x-Coordinate of the upper left corner
	 * @param y           - the y-Coordinate of the upper left corner
	 */
	protected void drawFlame(MatrixStack matrixStack, boolean lit, int x, int y) {
		this.minecraft.textureManager.bindTexture(FLAME);
		blit(matrixStack, x, y, 0, 0, 13, 13, 26, 13);
		if (lit) {
			blit(matrixStack, x, y, 13, 0, 13, 13, 26, 13);
		}
	}
	
	protected void drawFlame(MatrixStack matrixStack, double percentageFilled, int x, int y) {
		if(percentageFilled < 0 || percentageFilled > 100)
			throw new IllegalArgumentException("percentage has to be between 0 and 100 in order to draw the Flame");
		this.minecraft.textureManager.bindTexture(FLAME);
		blit(matrixStack, x, y, 0, 0, 13, 13, 26, 13);
		blit(matrixStack, x, y, 13, 0, 13, (int)(13d * percentageFilled), 26, 13);
		
	}

	/**
	 * draws a Slot Grid, dont use to draw a single Slot
	 * 
	 * @param matrixStack
	 * @param x                       - the x-Coordinate of the upper left corner
	 * @param y                       - the y-Coordinate of the upper left corner
	 * @param horizontalAmountOfSlots - the Horizontal amount of Slots u want,
	 *                                Max=16
	 * @param verticalAmountOfSlots   - the vertical amount of slots u wan, Max = 16
	 */
	protected void drawSlotGrid(MatrixStack matrixStack, int x, int y, int horizontalAmountOfSlots,
			int verticalAmountOfSlots) {
		this.minecraft.textureManager.bindTexture(SLOT_FIELD);
		if (horizontalAmountOfSlots > 16 || verticalAmountOfSlots > 16) {
			throw new IllegalArgumentException("vertical or horizontal amount of slots are greater then 16");
		}
		blit(matrixStack, x, y, 0, 0, 18 * horizontalAmountOfSlots, 18 * verticalAmountOfSlots, 288, 288);

	}

	protected void drawSingleSlot(MatrixStack matrixStack, int x, int y) {
		this.minecraft.textureManager.bindTexture(SLOT);
		this.blit(matrixStack, x, y, 0, 0, 18, 18);
	}

	/**
	 * draws an arrow that can be filled. if percentag > 100 or < 0, it will throw
	 * an illegalArgumentException
	 * 
	 * @param matrixStack
	 * @param x                       - the y-Coordinate of the upper left corner
	 * @param y                       - the x-Coordinate of the upper left corner
	 * @param percentageOfArrowFilled - how much the arrow is processed
	 * @param longArrow               - whether the length of the arrow should be
	 *                                doubled or not
	 */
	protected void drawArrow(MatrixStack matrixStack, int x, int y, double percentageOfArrowFilled, boolean longArrow) {
		if (percentageOfArrowFilled > 100 || percentageOfArrowFilled < 0) {
			throw new IllegalArgumentException("percentag has to be between 0 and 100 not: " + percentageOfArrowFilled);
		}
		if (!longArrow) {
			this.minecraft.textureManager.bindTexture(ARROW);
			blit(matrixStack, x, y, 22, 0, 22, 16, 44, 16);
			blit(matrixStack, x, y, 0, 0, (int) (22d * (percentageOfArrowFilled / 100d)), 16, 44, 16);
		}else {
			this.minecraft.textureManager.bindTexture(ARROW_LONG);
			blit(matrixStack, x, y, 0, 0, 44, 16, 88, 16);
			blit(matrixStack, x, y, 44, 0, (int) (44d * (percentageOfArrowFilled / 100d)), 16, 88, 16);
		}
	}

	/**
	 * draws a Blank Inventory in the Normal Size of an vanilla Gui without any
	 * Slots or other elements
	 * 
	 * @requires the x-Size and ySize to be set
	 * @param stack
	 */
	protected void drawBlankGuiWithInventory(MatrixStack stack) {
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		drawBlankGuiWithInventory(stack, x, y);
	}

	/**
	 * draws a blank Gui in the Size of vanilla guis, without any elements on it
	 * 
	 * @requires the x-Size and ySize to be set
	 * 
	 * @param stack
	 * @param x     - the x-Coordinate of upper left corner of the Gui
	 * @param y     - the x-Coordinate of upper left corner of the Gui
	 */
	protected void drawBlankGuiWithInventory(MatrixStack stack, int x, int y) {
		this.minecraft.textureManager.bindTexture(BLANK_GUI);
		this.blit(stack, x, y, 0, 0, this.xSize, this.ySize);
	}

}
