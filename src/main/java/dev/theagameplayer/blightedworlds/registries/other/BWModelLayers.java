package dev.theagameplayer.blightedworlds.registries.other;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public final class BWModelLayers {
	//Entities
	public static final ModelLayerLocation SPAWNER_SLIME = register("spawner_slime");
	public static final ModelLayerLocation SPAWNER_SLIME_OUTER = register("spawner_slime", "outer");
	//Block Entities
	public static final ModelLayerLocation ANCIENT_NETHER_PORTAL = register("ancient_nether_portal");
	
	private static final ModelLayerLocation register(final String name1n) {
		return register(name1n, "main");
	}

	private static final ModelLayerLocation register(final String name1n, final String name2n) { //TODO: Add to list?
		return new ModelLayerLocation(new ResourceLocation(BlightedWorldsMod.MODID, name2n), name2n);
	}
}
