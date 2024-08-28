package dev.theagameplayer.exoticexpansion.tags;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;

public final class EEStructureTags {
	public static final TagKey<Structure> ANCIENT_NETHER_DESTINATIONS = create("ancient_nether_destinations");
	
	private static final TagKey<Structure> create(final String nameIn) {
		return TagKey.create(Registries.STRUCTURE, ExoticExpansionMod.namespace(nameIn));
	}
}
