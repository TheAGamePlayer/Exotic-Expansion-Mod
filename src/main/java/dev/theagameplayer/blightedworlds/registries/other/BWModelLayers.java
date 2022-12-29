package dev.theagameplayer.blightedworlds.registries.other;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public final class BWModelLayers {
	//Block Entities
	//Entities
	public static final ModelLayerLocation SPAWNER_SLIME = register("spawner_slime");
	public static final ModelLayerLocation SPAWNER_SLIME_OUTER = register("spawner_slime", "outer");
	
	private static final ModelLayerLocation register(final String name1n) {
		return register(name1n, "main");
	}

	private static final ModelLayerLocation register(final String name1n, final String name2n) { //TODO: Add to list?
		return new ModelLayerLocation(new ResourceLocation(BlightedWorldsMod.MODID, name1n), name2n);
	}
}
