package dev.theagameplayer.exoticexpansion.data.tags;

import java.util.concurrent.CompletableFuture;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import dev.theagameplayer.exoticexpansion.registries.data.EEStructures;
import dev.theagameplayer.exoticexpansion.tags.EEStructureTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.StructureTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public final class EEStructureTagsProvider extends StructureTagsProvider {
	public EEStructureTagsProvider(final PackOutput pOutput, final CompletableFuture<HolderLookup.Provider> pProvider, final ExistingFileHelper pExistingFileHelper) {
		super(pOutput, pProvider, ExoticExpansionMod.MODID, pExistingFileHelper);
	}
	
	@Override
	protected final void addTags(final HolderLookup.Provider pProvider) {
		//ExoticExpansion
		this.tag(EEStructureTags.ANCIENT_NETHER_DESTINATIONS).add(EEStructures.INFERNAL_FORTRESS);
	}
	
	@Override
	public final String getName() {
		return "Exotic Expansion Structure Tags";
	}
}
