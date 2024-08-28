package dev.theagameplayer.exoticexpansion.registries.other;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import net.minecraft.client.model.geom.ModelLayerLocation;

public final class EEModelLayers {
	//Block Entities
	//Entities
	public static final ModelLayerLocation SPAWNER_SLIME = register("spawner_slime");
	public static final ModelLayerLocation SPAWNER_SLIME_OUTER = register("spawner_slime", "outer");
	
	private static final ModelLayerLocation register(final String name1n) {
		return register(name1n, "main");
	}

	private static final ModelLayerLocation register(final String name1n, final String name2n) {
		return new ModelLayerLocation(ExoticExpansionMod.namespace(name1n), name2n);
	}
}
