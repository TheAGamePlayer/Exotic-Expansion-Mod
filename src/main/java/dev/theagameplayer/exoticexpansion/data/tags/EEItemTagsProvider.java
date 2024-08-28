package dev.theagameplayer.exoticexpansion.data.tags;

import java.util.concurrent.CompletableFuture;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import dev.theagameplayer.exoticexpansion.registries.EEItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public final class EEItemTagsProvider extends ItemTagsProvider {
	public EEItemTagsProvider(final PackOutput pOutput, final CompletableFuture<HolderLookup.Provider> pProvider, final CompletableFuture<TagsProvider.TagLookup<Block>> pBlockTags, final ExistingFileHelper pExistingFileHelper) {
		super(pOutput, pProvider, pBlockTags, ExoticExpansionMod.MODID, pExistingFileHelper);
	}
	
	@Override
	protected final void addTags(final HolderLookup.Provider pProvider) {
		//Minecraft
		this.tag(ItemTags.FENCES).add(EEItems.RED_NETHER_BRICK_FENCE.get());
		//NeoForge
		this.tag(Tags.Items.FENCES_NETHER_BRICK).add(EEItems.RED_NETHER_BRICK_FENCE.get());
	}
	
	@Override
	public final String getName() {
		return "Exotic Expansion Item Tags";
	}
}
