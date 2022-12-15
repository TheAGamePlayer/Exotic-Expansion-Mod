package dev.theagameplayer.blightedworlds.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import dev.theagameplayer.blightedworlds.client.model.SpawnerSlimeModel;
import dev.theagameplayer.blightedworlds.registries.other.BWModelLayers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.monster.Slime;

public final class SpawnerSlimeOuterLayer <S extends Slime> extends RenderLayer<S, SpawnerSlimeModel<S>> {
	private final EntityModel<S> model;

	public SpawnerSlimeOuterLayer(final RenderLayerParent<S, SpawnerSlimeModel<S>> parentIn, final EntityModelSet modelsIn) {
		super(parentIn);
		this.model = new SpawnerSlimeModel<>(modelsIn.bakeLayer(BWModelLayers.SPAWNER_SLIME_OUTER));
	}

	@Override
	public final void render(final PoseStack poseStackIn, final MultiBufferSource bufferIn, final int packedLightIn, final S entityIn, final float limbSwingIn, final float limbSwingAmountIn, final float partialTicksIn, final float ageInTicksIn, final float netHeadYawIn, final float headPitchIn) {
		final Minecraft minecraft = Minecraft.getInstance();
		final boolean flag = minecraft.shouldEntityAppearGlowing(entityIn) && entityIn.isInvisible();
		if (!entityIn.isInvisible() || flag) {
			VertexConsumer vertexConsumer;
			if (flag) {
				vertexConsumer = bufferIn.getBuffer(RenderType.outline(this.getTextureLocation(entityIn)));
			} else {
				vertexConsumer = bufferIn.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(entityIn)));
			}
			this.getParentModel().copyPropertiesTo(this.model);
			this.model.prepareMobModel(entityIn, limbSwingIn, limbSwingAmountIn, partialTicksIn);
			this.model.setupAnim(entityIn, limbSwingIn, limbSwingAmountIn, ageInTicksIn, netHeadYawIn, headPitchIn);
			this.model.renderToBuffer(poseStackIn, vertexConsumer, packedLightIn, LivingEntityRenderer.getOverlayCoords(entityIn, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
