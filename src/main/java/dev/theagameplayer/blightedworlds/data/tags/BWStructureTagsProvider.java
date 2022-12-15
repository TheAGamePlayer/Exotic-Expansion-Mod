package dev.theagameplayer.blightedworlds.data.tags;

import java.util.concurrent.CompletableFuture;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import dev.theagameplayer.blightedworlds.registries.data.BWStructures;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.StructureTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public final class BWStructureTagsProvider extends StructureTagsProvider {
	public BWStructureTagsProvider(final PackOutput packOutputIn, final CompletableFuture<HolderLookup.Provider> lookupProviderIn, final ExistingFileHelper existingFileHelperIn) {
		super(packOutputIn, lookupProviderIn, BlightedWorldsMod.MODID, existingFileHelperIn);
	}
	
	@Override
	protected final void addTags(final HolderLookup.Provider lookupProviderIn) {
		//BlightedWorlds
		this.tag(BWStructureTags.ANCIENT_NETHER_DESTINATIONS).add(BWStructures.INFERNAL_FORTRESS);
	}
	
	@Override
	public String getName() {
		return "Blighted Worlds Structure Tags";
	}
}
