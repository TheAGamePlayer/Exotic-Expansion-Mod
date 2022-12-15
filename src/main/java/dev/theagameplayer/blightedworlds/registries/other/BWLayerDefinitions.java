package dev.theagameplayer.blightedworlds.registries.other;

import dev.theagameplayer.blightedworlds.client.model.SpawnerSlimeModel;
import dev.theagameplayer.blightedworlds.client.renderer.blockentity.AncientNetherPortalRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;

public final class BWLayerDefinitions {
	public static final void registerLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions eventIn) {
		//Entities
		eventIn.registerLayerDefinition(BWModelLayers.SPAWNER_SLIME, SpawnerSlimeModel::createInnerBodyLayer);
		eventIn.registerLayerDefinition(BWModelLayers.SPAWNER_SLIME_OUTER, SpawnerSlimeModel::createOuterBodyLayer);
		//Block Entities
		eventIn.registerLayerDefinition(BWModelLayers.ANCIENT_NETHER_PORTAL, AncientNetherPortalRenderer::createBodyLayer);
	}
}
