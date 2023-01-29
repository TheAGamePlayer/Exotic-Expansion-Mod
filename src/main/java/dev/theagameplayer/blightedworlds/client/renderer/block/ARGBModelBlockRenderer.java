package dev.theagameplayer.blightedworlds.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

public final class ARGBModelBlockRenderer extends ModelBlockRenderer {
	private final float[] argb = new float[]{1.0F, 1.0F, 1.0F, 1.0F};
	
	public ARGBModelBlockRenderer(final ModelBlockRenderer modelBlockRendererIn) {
		super(modelBlockRendererIn.blockColors);
	}
	
	public final void setARGB(final float rIn, final float gIn, final float bIn, final float aIn) {
		this.argb[0] = rIn;
		this.argb[1] = gIn;
		this.argb[2] = bIn;
		this.argb[3] = aIn;
	}

	@Override
	protected final void putQuadData(final BlockAndTintGetter blockTintIn, final BlockState stateIn, final BlockPos posIn, final VertexConsumer vertexConsumerIn, final PoseStack.Pose poseIn, final BakedQuad bakedQuadIn, final float rIn, final float gIn, final float bIn, final float aIn, final int lrIn, final int lgIn, final int lbIn, final int laIn, final int lightOverlayIn) {
		float r, g, b;
		if (bakedQuadIn.isTinted()) {
			int i = this.blockColors.getColor(stateIn, blockTintIn, posIn, bakedQuadIn.getTintIndex());
			r = (float)(i >> 16 & 255) / 255.0F;
			g = (float)(i >> 8 & 255) / 255.0F;
			b = (float)(i & 255) / 255.0F;
		} else {
			r = this.argb[0];
			g = this.argb[1];
			b = this.argb[2];
		}
		vertexConsumerIn.putBulkData(poseIn, bakedQuadIn, new float[]{rIn, gIn, bIn, aIn}, (r + this.argb[0])/2, (g + this.argb[1])/2, (b + this.argb[2])/2, this.argb[3], new int[]{lrIn, lgIn, lbIn, laIn}, lightOverlayIn, true);
	}
}
