package com.bretzelfresser.chemie.client.render.tileEntity;

import com.bretzelfresser.chemie.common.entities.tileEntities.ReactionChamberTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Vector3f;

public class ReactionChamberRenderer extends UtilTileEntityRenderer<ReactionChamberTileEntity> {

	public ReactionChamberRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(ReactionChamberTileEntity te, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

		if (!te.getStackInSlot(0).isEmpty()) {
			renderItems(te, partialTicks, matrixStackIn, bufferIn, combinedOverlayIn, 0,
					new double[] { 0.1, 0.7, 0.4 });
		}
		if (!te.getStackInSlot(1).isEmpty()) {
			renderItems(te, partialTicks, matrixStackIn, bufferIn, combinedOverlayIn, 1,
					new double[] { 0.3, 0.7, 0.4 });
		}
		if (!te.getStackInSlot(2).isEmpty()) {
			renderItems(te, partialTicks, matrixStackIn, bufferIn, combinedOverlayIn, 2,
					new double[] { 0.1, 0.7, 0.1 });
		}
		if (!te.getStackInSlot(3).isEmpty()) {
			renderItems(te, partialTicks, matrixStackIn, bufferIn, combinedOverlayIn, 3,
					new double[] { 0.3, 0.7, 0.1 });
		}
	}

	private void renderItems(ReactionChamberTileEntity te, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int combinedOverlayIn, int index, double[] offset) {
		ClientPlayerEntity player = mc.player;
		renderItem(te.getStackInSlot(index), offset, Vector3f.YP.rotationDegrees(180 - player.rotationYaw),
				matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, getLightLevel(te.getWorld(), te.getPos()),
				0.5f);
	}

}
