package dev.theagameplayer.blightedworlds.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import dev.theagameplayer.blightedworlds.registries.other.BWModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class AncientNetherPortalRenderer<BE extends BlockEntity> implements BlockEntityRenderer<BE> {
	private static final Material PORTAL_LOCATION = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation(BlightedWorldsMod.MODID, "entity/blockentity/ancient_nether_portal"));
	private static final Material PORTAL_RIM_LOCATION = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation(BlightedWorldsMod.MODID, "entity/blockentity/ancient_nether_portal_2"));
	private final ModelPart portal;
	private final ModelPart rim, rim2, rim3;

	public AncientNetherPortalRenderer(final BlockEntityRendererProvider.Context contextIn) {
		final ModelPart modelPart = contextIn.bakeLayer(BWModelLayers.ANCIENT_NETHER_PORTAL);
		this.portal = modelPart.getChild("portal");
		this.rim = modelPart.getChild("rim");
		this.rim2 = modelPart.getChild("rim2");
		this.rim3 = modelPart.getChild("rim3");
	}

	public static final LayerDefinition createBodyLayer() {
		final MeshDefinition meshDefinition = new MeshDefinition();
		final PartDefinition partDefinition = meshDefinition.getRoot();
		partDefinition.addOrReplaceChild("portal", CubeListBuilder.create()
				.texOffs(0, 4).addBox(-16.0F, -2.0F, 0.0F, 16.0F, 0.0F, 16.0F)
				.texOffs(0, 12).addBox(-16.0F, -2.0F, -1.0F, 16.0F, 0.0F, 1.0F)
				.texOffs(0, 13).addBox(-15.0F, -2.0F, -2.0F, 14.0F, 0.0F, 1.0F)
				.texOffs(0, 14).addBox(-13.0F, -2.0F, -3.0F, 10.0F, 0.0F, 1.0F)
				.texOffs(0, 15).addBox(-10.0F, -2.0F, -4.0F, 4.0F, 0.0F, 1.0F)
				.texOffs(0, 0).addBox(-10.0F, -2.0F, 19.0F, 4.0F, 0.0F, 1.0F)
				.texOffs(0, 1).addBox(-13.0F, -2.0F, 18.0F, 10.0F, 0.0F, 1.0F)
				.texOffs(0, 2).addBox(-15.0F, -2.0F, 17.0F, 14.0F, 0.0F, 1.0F)
				.texOffs(0, 3).addBox(-16.0F, -2.0F, 16.0F, 16.0F, 0.0F, 1.0F)
				.texOffs(0, 4).addBox(0.0F, -2.0F, 0.0F, 1.0F, 0.0F, 16.0F)
				.texOffs(0, 5).addBox(1.0F, -2.0F, 1.0F, 1.0F, 0.0F, 14.0F)
				.texOffs(0, 6).addBox(2.0F, -2.0F, 3.0F, 1.0F, 0.0F, 10.0F)
				.texOffs(0, 7).addBox(3.0F, -2.0F, 6.0F, 1.0F, 0.0F, 4.0F)
				.texOffs(0, 7).addBox(-20.0F, -2.0F, 6.0F, 1.0F, 0.0F, 4.0F)
				.texOffs(0, 6).addBox(-19.0F, -2.0F, 3.0F, 1.0F, 0.0F, 10.0F)
				.texOffs(0, 5).addBox(-18.0F, -2.0F, 1.0F, 1.0F, 0.0F, 14.0F)
				.texOffs(0, 4).addBox(-17.0F, -2.0F, 0.0F, 1.0F, 0.0F, 16.0F), PartPose.offset(0.0F, 24.0F, 0.0F));
		partDefinition.addOrReplaceChild("rim", CubeListBuilder.create()
				.texOffs(0, 0).addBox(-10.0F, 0.0F, -3.0F, 4.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-6.0F, 0.0F, -2.0F, 3.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-3.0F, 0.0F, -1.0F, 2.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(0.0F, 0.0F, 1.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(1.0F, 0.0F, 2.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(2.0F, 0.0F, 3.0F, 1.0F, 2.0F, 2.0F)
				.texOffs(0, 0).addBox(3.0F, 0.0F, 5.0F, 1.0F, 2.0F, 3.0F)
				.texOffs(0, 0).addBox(4.0F, 0.0F, 8.0F, 1.0F, 2.0F, 4.0F)
				.texOffs(0, 0).addBox(3.0F, 0.0F, 12.0F, 1.0F, 2.0F, 3.0F)
				.texOffs(0, 0).addBox(2.0F, 0.0F, 15.0F, 1.0F, 2.0F, 2.0F)
				.texOffs(0, 0).addBox(1.0F, 0.0F, 17.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(0.0F, 0.0F, 18.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-1.0F, 0.0F, 19.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-3.0F, 0.0F, 20.0F, 2.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-6.0F, 0.0F, 21.0F, 3.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-10.0F, 0.0F, 22.0F, 4.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-13.0F, 0.0F, 21.0F, 3.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-15.0F, 0.0F, 20.0F, 2.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-16.0F, 0.0F, 19.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-17.0F, 0.0F, 18.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-18.0F, 0.0F, 17.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-19.0F, 0.0F, 15.0F, 1.0F, 2.0F, 2.0F)
				.texOffs(0, 0).addBox(-20.0F, 0.0F, 12.0F, 1.0F, 2.0F, 3.0F)
				.texOffs(0, 0).addBox(-21.0F, 0.0F, 8.0F, 1.0F, 2.0F, 4.0F)
				.texOffs(0, 0).addBox(-20.0F, 0.0F, 5.0F, 1.0F, 2.0F, 3.0F)
				.texOffs(0, 0).addBox(-19.0F, 0.0F, 3.0F, 1.0F, 2.0F, 2.0F)
				.texOffs(0, 0).addBox(-18.0F, 0.0F, 2.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-17.0F, 0.0F, 1.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-16.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-15.0F, 0.0F, -1.0F, 2.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-13.0F, 0.0F, -2.0F, 3.0F, 2.0F, 1.0F), PartPose.offset(0.0F, 21.0F, -2.0F));
		partDefinition.addOrReplaceChild("rim2", CubeListBuilder.create()
				.texOffs(0, 0).addBox(-23.0F, -4.0F, 6.0F, 2.0F, 4.0F, 4.0F)
				.texOffs(0, 0).addBox(-22.0F, -4.0F, 3.0F, 2.0F, 4.0F, 3.0F)
				.texOffs(0, 0).addBox(-21.0F, -4.0F, 1.0F, 2.0F, 4.0F, 2.0F)
				.texOffs(0, 0).addBox(-20.0F, -4.0F, 0.0F, 2.0F, 4.0F, 1.0F)
				.texOffs(0, 0).addBox(-19.0F, -4.0F, -1.0F, 2.0F, 4.0F, 1.0F)
				.texOffs(0, 0).addBox(-17.0F, -4.0F, -3.0F, 1.0F, 4.0F, 2.0F)
				.texOffs(0, 0).addBox(-16.0F, -4.0F, -4.0F, 1.0F, 4.0F, 2.0F)
				.texOffs(0, 0).addBox(-15.0F, -4.0F, -5.0F, 2.0F, 4.0F, 2.0F)
				.texOffs(0, 0).addBox(-13.0F, -4.0F, -6.0F, 3.0F, 4.0F, 2.0F)
				.texOffs(0, 0).addBox(-10.0F, -4.0F, -7.0F, 4.0F, 4.0F, 2.0F)
				.texOffs(0, 0).addBox(-6.0F, -4.0F, -6.0F, 3.0F, 4.0F, 2.0F)
				.texOffs(0, 0).addBox(-3.0F, -4.0F, -5.0F, 2.0F, 4.0F, 2.0F)
				.texOffs(0, 0).addBox(-1.0F, -4.0F, -4.0F, 1.0F, 4.0F, 2.0F)
				.texOffs(0, 0).addBox(0.0F, -4.0F, -3.0F, 1.0F, 4.0F, 2.0F)
				.texOffs(0, 0).addBox(1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 1.0F)
				.texOffs(0, 0).addBox(2.0F, -4.0F, 0.0F, 2.0F, 4.0F, 1.0F)
				.texOffs(0, 0).addBox(3.0F, -4.0F, 1.0F, 2.0F, 4.0F, 2.0F)
				.texOffs(0, 0).addBox(4.0F, -4.0F, 3.0F, 2.0F, 4.0F, 3.0F)
				.texOffs(0, 0).addBox(5.0F, -4.0F, 6.0F, 2.0F, 4.0F, 4.0F)
				.texOffs(0, 0).addBox(4.0F, -4.0F, 10.0F, 2.0F, 4.0F, 3.0F)
				.texOffs(0, 0).addBox(3.0F, -4.0F, 13.0F, 2.0F, 4.0F, 2.0F)
				.texOffs(0, 0).addBox(2.0F, -4.0F, 15.0F, 2.0F, 4.0F, 1.0F)
				.texOffs(0, 0).addBox(1.0F, -4.0F, 16.0F, 2.0F, 4.0F, 1.0F)
				.texOffs(0, 0).addBox(0.0F, -4.0F, 17.0F, 1.0F, 4.0F, 2.0F)
				.texOffs(0, 0).addBox(-1.0F, -4.0F, 18.0F, 1.0F, 4.0F, 2.0F)
				.texOffs(0, 0).addBox(-3.0F, -4.0F, 19.0F, 2.0F, 4.0F, 2.0F)
				.texOffs(0, 0).addBox(-6.0F, -4.0F, 20.0F, 3.0F, 4.0F, 2.0F)
				.texOffs(0, 0).addBox(-10.0F, -4.0F, 21.0F, 4.0F, 4.0F, 2.0F)
				.texOffs(0, 0).addBox(-13.0F, -4.0F, 20.0F, 3.0F, 4.0F, 2.0F)
				.texOffs(0, 0).addBox(-15.0F, -4.0F, 19.0F, 2.0F, 4.0F, 2.0F)
				.texOffs(0, 0).addBox(-16.0F, -4.0F, 18.0F, 1.0F, 4.0F, 2.0F)
				.texOffs(0, 0).addBox(-17.0F, -4.0F, 17.0F, 1.0F, 4.0F, 2.0F)
				.texOffs(0, 0).addBox(-19.0F, -4.0F, 16.0F, 2.0F, 4.0F, 1.0F)
				.texOffs(0, 0).addBox(-20.0F, -4.0F, 15.0F, 2.0F, 4.0F, 1.0F)
				.texOffs(0, 0).addBox(-21.0F, -4.0F, 13.0F, 2.0F, 4.0F, 2.0F)
				.texOffs(0, 0).addBox(-22.0F, -4.0F, 10.0F, 2.0F, 4.0F, 3.0F)
				.texOffs(0, 0).addBox(-18.0F, -4.0F, 17.0F, 1.0F, 4.0F, 1.0F)
				.texOffs(0, 0).addBox(-18.0F, -4.0F, -2.0F, 1.0F, 4.0F, 1.0F)
				.texOffs(0, 0).addBox(1.0F, -4.0F, -2.0F, 1.0F, 4.0F, 1.0F)
				.texOffs(0, 0).addBox(1.0F, -4.0F, 17.0F, 1.0F, 4.0F, 1.0F), PartPose.offset(0.0F, 24.0F, 0.0F));
		partDefinition.addOrReplaceChild("rim3", CubeListBuilder.create()
				.texOffs(0, 0).addBox(-10.0F, -3.0F, -8.0F, 4.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-6.0F, -3.0F, -7.0F, 3.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-3.0F, -3.0F, -6.0F, 2.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-1.0F, -3.0F, -5.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(0.0F, -3.0F, -4.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(1.0F, -3.0F, -3.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(2.0F, -3.0F, -2.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(3.0F, -3.0F, -1.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(4.0F, -3.0F, 0.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(5.0F, -3.0F, 1.0F, 1.0F, 2.0F, 2.0F)
				.texOffs(0, 0).addBox(5.0F, -3.0F, 13.0F, 1.0F, 2.0F, 2.0F)
				.texOffs(0, 0).addBox(4.0F, -3.0F, 15.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(3.0F, -3.0F, 16.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(2.0F, -3.0F, 17.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(1.0F, -3.0F, 18.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(0.0F, -3.0F, 19.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-1.0F, -3.0F, 20.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-3.0F, -3.0F, 21.0F, 2.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-6.0F, -3.0F, 22.0F, 3.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-10.0F, -3.0F, 23.0F, 4.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-13.0F, -3.0F, 22.0F, 3.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-15.0F, -3.0F, 21.0F, 2.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-16.0F, -3.0F, 20.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-17.0F, -3.0F, 19.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-18.0F, -3.0F, 18.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-19.0F, -3.0F, 17.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-20.0F, -3.0F, 16.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-21.0F, -3.0F, 15.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-22.0F, -3.0F, 13.0F, 1.0F, 2.0F, 2.0F)
				.texOffs(0, 0).addBox(-23.0F, -3.0F, 10.0F, 1.0F, 2.0F, 3.0F)
				.texOffs(0, 0).addBox(-24.0F, -3.0F, 6.0F, 1.0F, 2.0F, 4.0F)
				.texOffs(0, 0).addBox(-23.0F, -3.0F, 3.0F, 1.0F, 2.0F, 3.0F)
				.texOffs(0, 0).addBox(-22.0F, -3.0F, 1.0F, 1.0F, 2.0F, 2.0F)
				.texOffs(0, 0).addBox(-21.0F, -3.0F, 0.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-20.0F, -3.0F, -1.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-19.0F, -3.0F, -2.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-18.0F, -3.0F, -3.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-17.0F, -3.0F, -4.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-16.0F, -3.0F, -5.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-15.0F, -3.0F, -6.0F, 2.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(-13.0F, -3.0F, -7.0F, 3.0F, 2.0F, 1.0F), PartPose.offset(0.0F, 24.0F, 0.0F));
		return LayerDefinition.create(meshDefinition, 16, 16);
	}

	@Override
	public final void render(final BE blockEntityIn, final float partialTicksIn, final PoseStack poseStackIn, final MultiBufferSource bufferIn, final int combinedLightIn, final int combinedOverlayIn) {
		final VertexConsumer vertexConsumer = PORTAL_LOCATION.buffer(bufferIn, RenderType::entityTranslucent);
		final VertexConsumer vertexConsumer1 = PORTAL_RIM_LOCATION.buffer(bufferIn, RenderType::entityTranslucent);
		poseStackIn.pushPose();
		this.portal.render(poseStackIn, vertexConsumer, combinedLightIn, combinedOverlayIn);
		this.rim.render(poseStackIn, vertexConsumer1, combinedLightIn, combinedOverlayIn);
		this.rim2.render(poseStackIn, vertexConsumer1, combinedLightIn, combinedOverlayIn);
		this.rim3.render(poseStackIn, vertexConsumer1, combinedLightIn, combinedOverlayIn);
		poseStackIn.popPose();
	}
}
