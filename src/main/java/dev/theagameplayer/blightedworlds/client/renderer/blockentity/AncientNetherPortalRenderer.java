package dev.theagameplayer.blightedworlds.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

public final class AncientNetherPortalRenderer<BE extends BlockEntity> implements BlockEntityRenderer<BE> {
	private final BlockRenderDispatcher dispatcher;
	private float rotY, rotX, rotZ, scale;
	private int rotationsY, rotationsX, rotationsZ;
	private boolean flag, flag2, flag3, flag4;

	public AncientNetherPortalRenderer(final BlockEntityRendererProvider.Context contextIn) {
		this.dispatcher = contextIn.getBlockRenderDispatcher();
		this.rotationsY = 1;
		this.rotationsX = 1;
		this.rotationsZ = 1;
	}
	
	@Override
	public final void render(final BE blockEntityIn, final float partialTicksIn, final PoseStack poseStackIn, final MultiBufferSource bufferIn, final int combinedLightIn, final int combinedOverlayIn) {
		final Level level = blockEntityIn.getLevel();
		final BlockPos pos = blockEntityIn.getBlockPos();
		final BlockState state = blockEntityIn.getBlockState();
		final BakedModel model = this.dispatcher.getBlockModel(state);
		final float scale = 2.0F + 0.5F * (float)Math.cos(Math.PI * this.scale - Math.PI);
		final float maxY = 360.0F * this.rotationsY;
		final float argb = this.getARGB(maxY);
		poseStackIn.pushPose();
        poseStackIn.translate(0.5F, 0.5F, 0.5F);
        poseStackIn.mulPose(Axis.YP.rotationDegrees(maxY/2 + (maxY/2) * (float)Math.cos((1/maxY) * Math.PI * this.rotY - Math.PI)));
        poseStackIn.mulPose(Axis.XP.rotationDegrees(this.rotationsX/2 + (this.rotationsX/2) * (float)Math.cos((1/this.rotationsX) * Math.PI * this.rotX - Math.PI)));
        poseStackIn.mulPose(Axis.ZP.rotationDegrees(this.rotationsZ/2 + (this.rotationsZ/2) * (float)Math.cos((1/this.rotationsZ) * Math.PI * this.rotZ - Math.PI)));
        poseStackIn.scale(scale, scale, scale);
		poseStackIn.translate(-0.5F, -0.5F, -0.5F);
		for (final RenderType renderType : model.getRenderTypes(state, RandomSource.create(state.getSeed(pos)), ModelData.EMPTY))
			this.dispatcher.getModelRenderer().tesselateBlock(level, model, state, pos, poseStackIn, bufferIn.getBuffer(renderType), false, RandomSource.create(), state.getSeed(pos), OverlayTexture.NO_OVERLAY, ModelData.EMPTY, renderType);
		poseStackIn.popPose();
		final float rotYMult = 3.0F - this.scale;
		final float rotXMult = this.scale * 0.0001F;
		final float rotZMult = this.scale * 0.0001F;
		final float scaleMult = 0.001F;
		this.rotY += this.flag ? -rotYMult : rotYMult;
		this.rotX += this.flag2 ? -rotXMult : rotXMult;
		this.rotZ += this.flag3 ? -rotZMult : rotZMult;
		this.scale += this.flag4 ? -scaleMult : scaleMult;
		if (this.rotY >= maxY) {
			this.flag = true;
		} else if (this.rotY < 0.0F) {
			this.flag = false;
			this.rotationsY = 5 + (4 - level.random.nextInt(level.random.nextInt(5) + 1));
		}
		if (this.rotX >= this.rotationsX) {
			this.flag2 = true;
		} else if (this.rotX < 0.0F) {
			this.flag2 = false;
			this.rotationsX = level.random.nextInt(level.random.nextInt(10) + 1) + 1;
		}
		if (this.rotZ >= this.rotationsZ) {
			this.flag3 = true;
		} else if (this.rotZ < 0.0F) {
			this.flag3 = false;
			this.rotationsZ = level.random.nextInt(level.random.nextInt(10) + 1) + 1;
		}
		if (this.scale >= 1.0F) {
			this.flag4 = true;
		} else if (this.scale < 0.0F) {
			this.flag4 = false;
		}
	}
	
	private final float getARGB(final float maxYIn) {
		float dist = Math.abs(this.rotY - maxYIn);
		if (this.rotY < dist) dist = this.rotY;
		final float range = maxYIn/15;
		return dist < range ? dist/range : 1.0F;
	}
}
