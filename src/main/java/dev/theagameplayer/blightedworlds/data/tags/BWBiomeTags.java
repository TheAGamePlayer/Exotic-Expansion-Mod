package dev.theagameplayer.blightedworlds.data.tags;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public final class BWBiomeTags {
	public static final TagKey<Biome> HAS_INFERNAL_NETHER_FORTRESS = create("has_structure/infernal_nether_fortress");
	
	private static final TagKey<Biome> create(final String nameIn) {
		return TagKey.create(Registries.BIOME, new ResourceLocation(BlightedWorldsMod.MODID, nameIn));
	}
}
