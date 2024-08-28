package dev.theagameplayer.exoticexpansion.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import dev.theagameplayer.exoticexpansion.client.model.SpawnerSlimeModel;
import dev.theagameplayer.exoticexpansion.client.renderer.entity.layers.SpawnerSlimeOuterLayer;
import dev.theagameplayer.exoticexpansion.registries.other.EEModelLayers;
import dev.theagameplayer.exoticexpansion.world.entity.monster.SpawnerSlime;
import dev.theagameplayer.exoticexpansion.world.level.SpawnerSlimeSpawner;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public final class SpawnerSlimeRenderer extends MobRenderer<SpawnerSlime, SpawnerSlimeModel> {
	private static final ResourceLocation SPAWNER_SLIME_TEXTURES = ExoticExpansionMod.namespace("textures/entity/spawner_slime.png");
	private static final ResourceLocation INACTIVE_SPAWNER_SLIME_TEXTURES = ExoticExpansionMod.namespace("textures/entity/inactive_spawner_slime.png");

	public SpawnerSlimeRenderer(final EntityRendererProvider.Context pContext) {
		super(pContext, new SpawnerSlimeModel(pContext.bakeLayer(EEModelLayers.SPAWNER_SLIME)), 0.25F);
		this.addLayer(new SpawnerSlimeOuterLayer(this, pContext.getModelSet()));
	}

	@Override
	public final void render(final SpawnerSlime pEntity, final float pEntityYaw, final float pPartialTicks, final PoseStack pPoseStack, final MultiBufferSource pBuffer, final int pPackedLight) {
		this.shadowRadius = 0.25F * pEntity.getSize();
		pPoseStack.pushPose();
		if (pEntity.hasChild() && pEntity.isAlive() && pEntity.isSpawnerActive()) {
			final SpawnerSlimeSpawner spawner = pEntity.getSpawnerBaseLogic();
			final Entity entity = spawner.getOrCreateDisplayEntity(pEntity.level(), pEntity.blockPosition());
			if (entity != null) {
				float f = 0.1328125F * pEntity.getSize();
				final float f1 = Math.max(entity.getBbWidth(), entity.getBbHeight());
				if (f1 > 1.0F)
					f /= f1;
				pPoseStack.translate(0.0D, 0.2D * pEntity.getSize(), 0.0D);
				pPoseStack.mulPose(Axis.YP.rotationDegrees((float)Mth.lerp(pPartialTicks, spawner.getoSpin(), spawner.getSpin()) * 10.0F));
				pPoseStack.translate(0.0D, -0.1D * pEntity.getSize(), 0.0D);
				pPoseStack.mulPose(Axis.XP.rotationDegrees(-30.0F));
				pPoseStack.scale(f, f, f);
				if (entity instanceof SpawnerSlime slime)
					slime.setHasChild(false);
				this.entityRenderDispatcher.render(entity, 0.0D, 0.0D, 0.0D, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
			}
		}
		pPoseStack.popPose();
		super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
	}

	@Override
	protected final void scale(final SpawnerSlime pEntity, final PoseStack pPoseStack, final float pPartialTicks) {
		pPoseStack.scale(0.999F, 0.999F, 0.999F);
		pPoseStack.translate(0.0D, 0.001D, 0.0D);
		final float size = pEntity.getSize();
		final float f = Mth.lerp(pPartialTicks, pEntity.oSquish, pEntity.squish) / (size * 0.5F + 1.0F);
		final float f1 = 1.0F / (f + 1.0F);
		pPoseStack.scale(f1 * size, 1.0F / f1 * size, f1 * size);
	}

	@Override
	public final ResourceLocation getTextureLocation(final SpawnerSlime pEntity) {
		return pEntity.isSpawnerActive() ? SPAWNER_SLIME_TEXTURES : INACTIVE_SPAWNER_SLIME_TEXTURES;
	}
}
