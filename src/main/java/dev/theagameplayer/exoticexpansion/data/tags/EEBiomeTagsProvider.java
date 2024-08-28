package dev.theagameplayer.exoticexpansion.data.tags;

import java.util.concurrent.CompletableFuture;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import dev.theagameplayer.exoticexpansion.tags.EEBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public final class EEBiomeTagsProvider extends BiomeTagsProvider {
	public EEBiomeTagsProvider(final PackOutput pOutput, final CompletableFuture<HolderLookup.Provider> pProvider, final ExistingFileHelper pExistingFileHelper) {
		super(pOutput, pProvider, ExoticExpansionMod.MODID, pExistingFileHelper);
	}

	@Override
	protected final void addTags(final HolderLookup.Provider pProvider) {
		//ExoticExpansion
		this.tag(EEBiomeTags.HAS_INFERNAL_NETHER_FORTRESS).addTag(BiomeTags.IS_NETHER);
	}
	
	@Override
	public final String getName() {
		return "Exotic Expansion Biome Tags";
	}

}
