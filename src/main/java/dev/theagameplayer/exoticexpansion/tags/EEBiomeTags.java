package dev.theagameplayer.exoticexpansion.tags;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public final class EEBiomeTags {
	public static final TagKey<Biome> HAS_INFERNAL_NETHER_FORTRESS = create("has_structure/infernal_nether_fortress");
	
	private static final TagKey<Biome> create(final String pName) {
		return TagKey.create(Registries.BIOME, ExoticExpansionMod.namespace(pName));
	}
}
