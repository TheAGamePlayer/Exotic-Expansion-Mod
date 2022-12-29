package dev.theagameplayer.blightedworlds.registries.other;

import dev.theagameplayer.blightedworlds.client.model.SpawnerSlimeModel;
import net.minecraftforge.client.event.EntityRenderersEvent;

public final class BWLayerDefinitions {
	public static final void registerLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions eventIn) {
		//Block Entities
		//Entities
		eventIn.registerLayerDefinition(BWModelLayers.SPAWNER_SLIME, SpawnerSlimeModel::createInnerBodyLayer);
		eventIn.registerLayerDefinition(BWModelLayers.SPAWNER_SLIME_OUTER, SpawnerSlimeModel::createOuterBodyLayer);
	}
}
