package dev.theagameplayer.blightedworlds.data.tags;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;

public final class BWStructureTags {
	public static final TagKey<Structure> ANCIENT_NETHER_DESTINATIONS = create("ancient_nether_destinations");
	
	private static final TagKey<Structure> create(final String nameIn) {
		return TagKey.create(Registries.STRUCTURE, new ResourceLocation(BlightedWorldsMod.MODID, nameIn));
	}
}
