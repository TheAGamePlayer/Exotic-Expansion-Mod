package dev.theagameplayer.exoticexpansion.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import dev.theagameplayer.exoticexpansion.client.model.SpawnerSlimeModel;
import dev.theagameplayer.exoticexpansion.registries.other.EEModelLayers;
import dev.theagameplayer.exoticexpansion.world.entity.monster.SpawnerSlime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public final class SpawnerSlimeOuterLayer extends RenderLayer<SpawnerSlime, SpawnerSlimeModel> {
	private final SpawnerSlimeModel model;

	public SpawnerSlimeOuterLayer(final RenderLayerParent<SpawnerSlime, SpawnerSlimeModel> pRenderer, final EntityModelSet pModelSet) {
		super(pRenderer);
		this.model = new SpawnerSlimeModel(pModelSet.bakeLayer(EEModelLayers.SPAWNER_SLIME_OUTER));
	}

	@Override
	public final void render(final PoseStack pPoseStack, final MultiBufferSource pBuffer, final int pPackedLight, final SpawnerSlime pLivingEntity, final float pLimbSwing, final float pLimbSwingAmount, final float pPartialTicks, final float pAgeInTicks, final float pNetHeadYaw, final float pHeadPitch) {
		final Minecraft minecraft = Minecraft.getInstance();
		final boolean flag = minecraft.shouldEntityAppearGlowing(pLivingEntity) && pLivingEntity.isInvisible();
		if (!pLivingEntity.isInvisible() || flag) {
			VertexConsumer vertexConsumer;
			if (flag) {
				vertexConsumer = pBuffer.getBuffer(RenderType.outline(this.getTextureLocation(pLivingEntity)));
			} else {
				vertexConsumer = pBuffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(pLivingEntity)));
			}
			this.getParentModel().copyPropertiesTo(this.model);
			this.model.prepareMobModel(pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks);
			this.model.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
			this.model.renderToBuffer(pPoseStack, vertexConsumer, pPackedLight, LivingEntityRenderer.getOverlayCoords(pLivingEntity, 0.0F));
		}
	}
}
