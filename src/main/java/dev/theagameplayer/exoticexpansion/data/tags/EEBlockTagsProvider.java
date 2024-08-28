package dev.theagameplayer.exoticexpansion.data.tags;

import java.util.concurrent.CompletableFuture;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import dev.theagameplayer.exoticexpansion.registries.EEBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public final class EEBlockTagsProvider extends BlockTagsProvider {
	public EEBlockTagsProvider(final PackOutput pOutput, final CompletableFuture<HolderLookup.Provider> pProvider, final ExistingFileHelper pExistingFileHelper) {
		super(pOutput, pProvider, ExoticExpansionMod.MODID, pExistingFileHelper);
	}

	@Override
	protected final void addTags(final HolderLookup.Provider pProvider) {
		//Minecraft
		this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(EEBlocks.RED_NETHER_BRICK_FENCE.get());
		this.tag(BlockTags.NEEDS_DIAMOND_TOOL);
		this.tag(BlockTags.FENCES).add(EEBlocks.RED_NETHER_BRICK_FENCE.get());
		//NeoForge
		this.tag(Tags.Blocks.FENCES_NETHER_BRICK).add(EEBlocks.RED_NETHER_BRICK_FENCE.get());
	}
	
	@Override
	public final String getName() {
		return "Exotic Expansion Block Tags";
	}

}
