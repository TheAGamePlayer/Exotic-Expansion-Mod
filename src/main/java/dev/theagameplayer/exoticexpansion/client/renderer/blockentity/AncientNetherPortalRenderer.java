package dev.theagameplayer.exoticexpansion.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import dev.theagameplayer.exoticexpansion.client.renderer.block.ARGBModelBlockRenderer;
import dev.theagameplayer.exoticexpansion.world.level.block.entity.AncientNetherPortalBlockEntity;
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
import net.neoforged.neoforge.client.model.data.ModelData;

public final class AncientNetherPortalRenderer implements BlockEntityRenderer<AncientNetherPortalBlockEntity> {
	private final BlockRenderDispatcher dispatcher;
	private final ARGBModelBlockRenderer modelRenderer;

	public AncientNetherPortalRenderer(final BlockEntityRendererProvider.Context contextIn) {
		this.dispatcher = contextIn.getBlockRenderDispatcher();
		this.modelRenderer = new ARGBModelBlockRenderer(contextIn.getBlockRenderDispatcher().getModelRenderer());
	}
	
	@Override
	public final void render(final AncientNetherPortalBlockEntity pBlockEntity, final float pPartialTick, final PoseStack pPoseStack, final MultiBufferSource pBuffer, final int pPackedLight, final int pPackedOverlay) {
		final Level level = pBlockEntity.getLevel();
		final BlockPos pos = pBlockEntity.getBlockPos();
		final BlockState state = pBlockEntity.getBlockState();
		final BakedModel model = this.dispatcher.getBlockModel(state);
		final float scale = pBlockEntity.getPrevScale() + (pBlockEntity.getScale() - pBlockEntity.getPrevScale()) * pPartialTick;
		final float scaleResult = 2.5F + 0.5F * (float)Math.cos(Math.PI * scale - Math.PI);
		final float yRot = pBlockEntity.getPrevRotY() + (pBlockEntity.getRotY() - pBlockEntity.getPrevRotY()) * pPartialTick;
		final float xRot = pBlockEntity.getPrevRotX() + (pBlockEntity.getRotX() - pBlockEntity.getPrevRotX()) * pPartialTick;
		final float zRot = pBlockEntity.getPrevRotZ() + (pBlockEntity.getRotZ() - pBlockEntity.getPrevRotZ()) * pPartialTick;
		final float maxY = 360.0F * pBlockEntity.getYRotations();
		final float argb = this.getARGB(pBlockEntity, maxY);
		pPoseStack.pushPose();
        pPoseStack.translate(0.5F, -0.0625F, 0.5F);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(maxY/2 + (maxY/2) * (float)Math.tan((1/(maxY * 2)) * Math.PI * yRot - Math.PI/4)));
        pPoseStack.mulPose((pBlockEntity.getYFlag() ? Axis.XN : Axis.XP).rotationDegrees(pBlockEntity.getXRotations()/2 + (pBlockEntity.getXRotations()/2) * (float)Math.cos((1/(pBlockEntity.getXRotations() * 2)) * Math.PI * xRot - Math.PI/4)));
        pPoseStack.mulPose((pBlockEntity.getYFlag() ? Axis.ZN : Axis.ZP).rotationDegrees(pBlockEntity.getZRotations()/2 + (pBlockEntity.getZRotations()/2) * (float)Math.cos((1/(pBlockEntity.getZRotations() * 2)) * Math.PI * zRot - Math.PI/4)));
        pPoseStack.scale(scaleResult, scaleResult, scaleResult);
		pPoseStack.translate(-0.5F, 0.0625F, -0.5F);
		this.modelRenderer.setARGB(argb, argb, argb, argb);
		for (final RenderType renderType : model.getRenderTypes(state, RandomSource.create(state.getSeed(pos)), ModelData.EMPTY))
			this.modelRenderer.tesselateBlock(level, model, state, pos, pPoseStack, pBuffer.getBuffer(renderType), false, RandomSource.create(), state.getSeed(pos), OverlayTexture.NO_OVERLAY, ModelData.EMPTY, renderType);
		pPoseStack.popPose();
	}
	
	private final float getARGB(final AncientNetherPortalBlockEntity pBlockEntity, final float pMaxY) {
		float dist = Math.abs(pBlockEntity.getRotY() - pMaxY);
		if (pBlockEntity.getRotY() < dist) dist = pBlockEntity.getRotY();
		final float range = pMaxY/(pBlockEntity.getYRotations()/2);
		return Mth.clamp((dist - dist/2)/(range/2), 0.0F, 1.0F);
	}
}
