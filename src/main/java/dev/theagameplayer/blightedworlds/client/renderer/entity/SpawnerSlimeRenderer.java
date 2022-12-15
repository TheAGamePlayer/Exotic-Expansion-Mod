package dev.theagameplayer.blightedworlds.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import dev.theagameplayer.blightedworlds.client.model.SpawnerSlimeModel;
import dev.theagameplayer.blightedworlds.client.renderer.entity.layers.SpawnerSlimeOuterLayer;
import dev.theagameplayer.blightedworlds.registries.other.BWModelLayers;
import dev.theagameplayer.blightedworlds.world.entity.monster.SpawnerSlime;
import dev.theagameplayer.blightedworlds.world.level.spawner.SpawnerSlimeSpawner;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public final class SpawnerSlimeRenderer extends MobRenderer<SpawnerSlime, SpawnerSlimeModel<SpawnerSlime>> {
	private static final ResourceLocation SPAWNER_SLIME_TEXTURES = new ResourceLocation(BlightedWorldsMod.MODID, "textures/entity/spawner_slime.png");
	private static final ResourceLocation INACTIVE_SPAWNER_SLIME_TEXTURES = new ResourceLocation(BlightedWorldsMod.MODID, "textures/entity/inactive_spawner_slime.png");

	public SpawnerSlimeRenderer(final EntityRendererProvider.Context contextIn) {
		super(contextIn, new SpawnerSlimeModel<>(contextIn.bakeLayer(BWModelLayers.SPAWNER_SLIME)), 0.25F);
		this.addLayer(new SpawnerSlimeOuterLayer<>(this, contextIn.getModelSet()));
	}

	@Override
	public final void render(final SpawnerSlime entityIn, final float entityYawIn, final float partialTicksIn, final PoseStack poseStackIn, final MultiBufferSource bufferIn, final int packedLightIn) {
		this.shadowRadius = 0.25F * entityIn.getSize();
		poseStackIn.pushPose();
		if (entityIn.hasChild() && entityIn.isAlive() && entityIn.isSpawnerActive()) {
			final SpawnerSlimeSpawner spawner = entityIn.getSpawnerBaseLogic();
			final Entity entity = spawner.getOrCreateDisplayEntity(entityIn.getLevel());
			if (entity != null) {
				float f = 0.1328125F * entityIn.getSize();
				final float f1 = Math.max(entity.getBbWidth(), entity.getBbHeight());
				if (f1 > 1.0F)
					f /= f1;
				poseStackIn.translate(0.0D, 0.2D * entityIn.getSize(), 0.0D);
				poseStackIn.mulPose(Axis.YP.rotationDegrees((float)Mth.lerp(partialTicksIn, spawner.getoSpin(), spawner.getSpin()) * 10.0F));
				poseStackIn.translate(0.0D, -0.1D * entityIn.getSize(), 0.0D);
				poseStackIn.mulPose(Axis.XP.rotationDegrees(-30.0F));
				poseStackIn.scale(f, f, f);
				if (entity instanceof SpawnerSlime)
					((SpawnerSlime)entity).setHasChild(false);
				this.entityRenderDispatcher.render(entity, 0.0D, 0.0D, 0.0D, entityYawIn, partialTicksIn, poseStackIn, bufferIn, packedLightIn);
			}
		}
		poseStackIn.popPose();
		super.render(entityIn, entityYawIn, partialTicksIn, poseStackIn, bufferIn, packedLightIn);
	}

	@Override
	protected final void scale(final SpawnerSlime entityIn, final PoseStack poseStackIn, final float partialTicksIn) {
		poseStackIn.scale(0.999F, 0.999F, 0.999F);
		poseStackIn.translate(0.0D, 0.001D, 0.0D);
		final float size = entityIn.getSize();
		final float f = Mth.lerp(partialTicksIn, entityIn.oSquish, entityIn.squish) / (size * 0.5F + 1.0F);
		final float f1 = 1.0F / (f + 1.0F);
		poseStackIn.scale(f1 * size, 1.0F / f1 * size, f1 * size);
	}

	@Override
	public final ResourceLocation getTextureLocation(final SpawnerSlime entityIn) {
		return entityIn.isSpawnerActive() ? SPAWNER_SLIME_TEXTURES : INACTIVE_SPAWNER_SLIME_TEXTURES;
	}
}
