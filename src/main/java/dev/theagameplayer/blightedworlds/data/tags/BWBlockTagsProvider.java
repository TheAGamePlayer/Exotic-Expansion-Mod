package dev.theagameplayer.blightedworlds.data.tags;

import java.util.concurrent.CompletableFuture;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import dev.theagameplayer.blightedworlds.registries.BWBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public final class BWBlockTagsProvider extends BlockTagsProvider {
	public BWBlockTagsProvider(final PackOutput packOutputIn, final CompletableFuture<HolderLookup.Provider> lookupProviderIn, final ExistingFileHelper existingFileHelperIn) {
		super(packOutputIn, lookupProviderIn, BlightedWorldsMod.MODID, existingFileHelperIn);
	}

	@Override
	protected final void addTags(final HolderLookup.Provider lookupProviderIn) {
		//Minecraft
		this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BWBlocks.RED_NETHER_BRICK_FENCE.get());
		this.tag(BlockTags.NEEDS_DIAMOND_TOOL);
		this.tag(BlockTags.FENCES).add(BWBlocks.RED_NETHER_BRICK_FENCE.get());
		//Forge
		this.tag(Tags.Blocks.FENCES_NETHER_BRICK).add(BWBlocks.RED_NETHER_BRICK_FENCE.get());
	}
	
	@Override
	public String getName() {
		return "Blighted Worlds Block Tags";
	}
}
