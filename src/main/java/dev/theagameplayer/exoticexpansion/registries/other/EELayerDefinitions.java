package dev.theagameplayer.exoticexpansion.registries.other;

import dev.theagameplayer.exoticexpansion.client.model.SpawnerSlimeModel;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public final class EELayerDefinitions {
	public static final void registerLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions eventIn) {
		//Block Entities
		//Entities
		eventIn.registerLayerDefinition(EEModelLayers.SPAWNER_SLIME, SpawnerSlimeModel::createInnerBodyLayer);
		eventIn.registerLayerDefinition(EEModelLayers.SPAWNER_SLIME_OUTER, SpawnerSlimeModel::createOuterBodyLayer);
	}
}
