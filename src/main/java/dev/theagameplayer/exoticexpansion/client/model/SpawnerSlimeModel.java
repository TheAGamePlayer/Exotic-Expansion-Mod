package dev.theagameplayer.exoticexpansion.client.model;

import dev.theagameplayer.exoticexpansion.world.entity.monster.SpawnerSlime;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public final class SpawnerSlimeModel extends HierarchicalModel<SpawnerSlime> {
	private final ModelPart root;

	public SpawnerSlimeModel(final ModelPart pRoot) {
		super(RenderType::entityTranslucent);
		this.root = pRoot;
	}

	public static final LayerDefinition createOuterBodyLayer() {
		final MeshDefinition meshDefinition = new MeshDefinition();
		final PartDefinition partDefinition = meshDefinition.getRoot();
		partDefinition.addOrReplaceChild("cube", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 16.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
		return LayerDefinition.create(meshDefinition, 64, 32);
	}

	public static final LayerDefinition createInnerBodyLayer() {
		final MeshDefinition meshDefinition = new MeshDefinition();
		final PartDefinition partDefinition = meshDefinition.getRoot();
		partDefinition.addOrReplaceChild("cube", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, 17.0F, -3.0F, 6.0F, 6.0F, 6.0F), PartPose.ZERO);
		partDefinition.addOrReplaceChild("right_eye", CubeListBuilder.create().texOffs(32, 0).addBox(-3.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F), PartPose.ZERO);
		partDefinition.addOrReplaceChild("left_eye", CubeListBuilder.create().texOffs(32, 4).addBox(1.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F), PartPose.ZERO);
		return LayerDefinition.create(meshDefinition, 64, 32);
	}

	@Override
	public final void setupAnim(final SpawnerSlime pEntity, final float pLimbSwing, final float pLimbSwingAmount, final float pAgeInTicks, final float pNetHeadYaw, final float pHeadPitch) {}

	@Override
	public final ModelPart root() {
		return this.root;
	}
}
