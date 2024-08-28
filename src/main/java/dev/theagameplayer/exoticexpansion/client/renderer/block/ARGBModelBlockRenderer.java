package dev.theagameplayer.exoticexpansion.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

public final class ARGBModelBlockRenderer extends ModelBlockRenderer {
	private final float[] argb = new float[]{1.0F, 1.0F, 1.0F, 1.0F};
	
	public ARGBModelBlockRenderer(final ModelBlockRenderer pModelBlockRenderer) {
		super(pModelBlockRenderer.blockColors);
	}
	
	public final void setARGB(final float pR, final float pG, final float pB, final float pA) {
		this.argb[0] = pR;
		this.argb[1] = pG;
		this.argb[2] = pB;
		this.argb[3] = pA;
	}

	@Override
	protected final void putQuadData(final BlockAndTintGetter pLevel, final BlockState pState, final BlockPos pPos, final VertexConsumer pConsumer, final PoseStack.Pose pPose, final BakedQuad pQuad, final float pR, final float pG, final float pB, final float pA, final int pLR, final int pLG, final int pLB, final int pLA, final int pPackedOverlay) {
		float r, g, b;
		if (pQuad.isTinted()) {
			int i = this.blockColors.getColor(pState, pLevel, pPos, pQuad.getTintIndex());
			r = (float)(i >> 16 & 255) / 255.0F;
			g = (float)(i >> 8 & 255) / 255.0F;
			b = (float)(i & 255) / 255.0F;
		} else {
			r = this.argb[0];
			g = this.argb[1];
			b = this.argb[2];
		}
		pConsumer.putBulkData(pPose, pQuad, new float[]{pR, pG, pB, pA}, (r + this.argb[0])/2, (g + this.argb[1])/2, (b + this.argb[2])/2, this.argb[3], new int[]{pLR, pLG, pLB, pLA}, pPackedOverlay, true);
	}
}
