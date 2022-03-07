package com.bretzelfresser.chemie.client.render.tileEntity;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public abstract class UtilTileEntityRenderer<T extends TileEntity> extends TileEntityRenderer<T> {

	protected Minecraft mc = Minecraft.getInstance();

	public UtilTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	protected int getLightLevel(World world, BlockPos pos) {
		int bLight = world.getLightFor(LightType.BLOCK, pos);
		int sLight = world.getLightFor(LightType.SKY, pos);
		return LightTexture.packLight(bLight, sLight);
	}

	protected void renderItem(ItemStack stack, double[] translation, Quaternion rotation, MatrixStack matrixStack,
			IRenderTypeBuffer buffer, float partialTicks, int combinedOverlay, int lightLevel, float scale) {
		matrixStack.push();
		matrixStack.translate(translation[0], translation[1], translation[2]);
		matrixStack.rotate(rotation);
		matrixStack.scale(scale, scale, scale);

		IBakedModel model = mc.getItemRenderer().getItemModelWithOverrides(stack, null, null);
		mc.getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.GROUND, true, matrixStack, buffer,
				lightLevel, combinedOverlay, model);
		matrixStack.pop();
	}

	protected void renderLabel(StringTextComponent text, MatrixStack stack, IRenderTypeBuffer buffer, int lightLevel,
			double[] corner, int color) {
		FontRenderer fontRenderer = mc.fontRenderer;
		stack.push();
		float scale = 0.01f;
		int opacity = (int)(.4f * 255.0f) << 24;
		float offset = (float) (-fontRenderer.getStringPropertyWidth(text)/2);
		Matrix4f matrix = stack.getLast().getMatrix();
		
		stack.translate(corner[0], corner[1] + .4f, corner[2]);
		stack.scale(scale, scale, scale);
		stack.rotate(mc.getRenderManager().getCameraOrientation());
		stack.rotate(Vector3f.ZP.rotationDegrees(180));
		
		fontRenderer.func_243247_a(text, offset, 0, color, false, matrix, buffer, false, opacity, lightLevel);
	}

}
