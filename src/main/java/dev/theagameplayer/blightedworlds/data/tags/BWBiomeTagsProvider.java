package dev.theagameplayer.blightedworlds.data.tags;

import java.util.concurrent.CompletableFuture;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public final class BWBiomeTagsProvider extends BiomeTagsProvider {
	public BWBiomeTagsProvider(final PackOutput packOutputIn, final CompletableFuture<HolderLookup.Provider> lookupProviderIn, final  ExistingFileHelper existingFileHelperIn) {
		super(packOutputIn, lookupProviderIn, BlightedWorldsMod.MODID, existingFileHelperIn);
	}

	@Override
	protected final void addTags(final HolderLookup.Provider lookupProviderIn) {
		//BlightedWorlds
		this.tag(BWBiomeTags.HAS_INFERNAL_NETHER_FORTRESS).addTag(BiomeTags.IS_NETHER);
	}
	
	@Override
	public String getName() {
		return "Blighted Worlds Biome Tags";
	}
}
