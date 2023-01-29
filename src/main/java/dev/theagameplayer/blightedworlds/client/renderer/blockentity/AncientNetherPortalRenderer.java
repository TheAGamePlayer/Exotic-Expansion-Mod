package dev.theagameplayer.blightedworlds.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import dev.theagameplayer.blightedworlds.client.renderer.block.ARGBModelBlockRenderer;
import dev.theagameplayer.blightedworlds.world.level.block.entity.AncientNetherPortalBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

public final class AncientNetherPortalRenderer implements BlockEntityRenderer<AncientNetherPortalBlockEntity> {
	private final BlockRenderDispatcher dispatcher;
	private final ARGBModelBlockRenderer modelRenderer;

	public AncientNetherPortalRenderer(final BlockEntityRendererProvider.Context contextIn) {
		this.dispatcher = contextIn.getBlockRenderDispatcher();
		this.modelRenderer = new ARGBModelBlockRenderer(contextIn.getBlockRenderDispatcher().getModelRenderer());
	}
	
	@Override
	public final void render(final AncientNetherPortalBlockEntity blockEntityIn, final float partialTicksIn, final PoseStack poseStackIn, final MultiBufferSource bufferIn, final int combinedLightIn, final int combinedOverlayIn) {
		final Level level = blockEntityIn.getLevel();
		final BlockPos pos = blockEntityIn.getBlockPos();
		final BlockState state = blockEntityIn.getBlockState();
		final BakedModel model = this.dispatcher.getBlockModel(state);
		final float scale = blockEntityIn.getPrevScale() + (blockEntityIn.getScale() - blockEntityIn.getPrevScale()) * partialTicksIn;
		final float scaleResult = 2.5F + 0.5F * (float)Math.cos(Math.PI * scale - Math.PI);
		final float yRot = blockEntityIn.getPrevRotY() + (blockEntityIn.getRotY() - blockEntityIn.getPrevRotY()) * partialTicksIn;
		final float xRot = blockEntityIn.getPrevRotX() + (blockEntityIn.getRotX() - blockEntityIn.getPrevRotX()) * partialTicksIn;
		final float zRot = blockEntityIn.getPrevRotZ() + (blockEntityIn.getRotZ() - blockEntityIn.getPrevRotZ()) * partialTicksIn;
		final float maxY = 360.0F * blockEntityIn.getYRotations();
		final float argb = this.getARGB(blockEntityIn, maxY);
		poseStackIn.pushPose();
        poseStackIn.translate(0.5F, -0.0625F, 0.5F);
        poseStackIn.mulPose(Axis.YP.rotationDegrees(maxY/2 + (maxY/2) * (float)Math.tan((1/(maxY * 2)) * Math.PI * yRot - Math.PI/4)));
        poseStackIn.mulPose((blockEntityIn.getYFlag() ? Axis.XN : Axis.XP).rotationDegrees(blockEntityIn.getXRotations()/2 + (blockEntityIn.getXRotations()/2) * (float)Math.cos((1/(blockEntityIn.getXRotations() * 2)) * Math.PI * xRot - Math.PI/4)));
        poseStackIn.mulPose((blockEntityIn.getYFlag() ? Axis.ZN : Axis.ZP).rotationDegrees(blockEntityIn.getZRotations()/2 + (blockEntityIn.getZRotations()/2) * (float)Math.cos((1/(blockEntityIn.getZRotations() * 2)) * Math.PI * zRot - Math.PI/4)));
        poseStackIn.scale(scaleResult, scaleResult, scaleResult);
		poseStackIn.translate(-0.5F, 0.0625F, -0.5F);
		this.modelRenderer.setARGB(argb, argb, argb, argb);
		for (final RenderType renderType : model.getRenderTypes(state, RandomSource.create(state.getSeed(pos)), ModelData.EMPTY))
			this.modelRenderer.tesselateBlock(level, model, state, pos, poseStackIn, bufferIn.getBuffer(renderType), false, RandomSource.create(), state.getSeed(pos), OverlayTexture.NO_OVERLAY, ModelData.EMPTY, renderType);
		poseStackIn.popPose();
	}
	
	private final float getARGB(final AncientNetherPortalBlockEntity blockEntityIn, final float maxYIn) {
		float dist = Math.abs(blockEntityIn.getRotY() - maxYIn);
		if (blockEntityIn.getRotY() < dist) dist = blockEntityIn.getRotY();
		final float range = maxYIn/(blockEntityIn.getYRotations()/2);
		return Mth.clamp((dist - dist/2)/(range/2), 0.0F, 1.0F);
	}
}
