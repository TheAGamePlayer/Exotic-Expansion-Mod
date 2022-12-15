package dev.theagameplayer.blightedworlds.data.tags;

import java.util.concurrent.CompletableFuture;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import dev.theagameplayer.blightedworlds.registries.BWItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public final class BWItemTagsProvider extends ItemTagsProvider {
	public BWItemTagsProvider(final PackOutput packOutputIn, final CompletableFuture<HolderLookup.Provider> lookupProviderIn, final ExistingFileHelper existingFileHelperIn) {
		super(packOutputIn, lookupProviderIn, new BWBlockTagsProvider(packOutputIn, lookupProviderIn, existingFileHelperIn), BlightedWorldsMod.MODID, existingFileHelperIn);
	}
	
	@Override
	protected final void addTags(final HolderLookup.Provider lookupProviderIn) {
		//Minecraft
		this.tag(ItemTags.FENCES).add(BWItems.RED_NETHER_BRICK_FENCE.get());
		//Forge
		this.tag(Tags.Items.FENCES_NETHER_BRICK).add(BWItems.RED_NETHER_BRICK_FENCE.get());
	}
	
	@Override
	public String getName() {
		return "Blighted Worlds Item Tags";
	}
}
