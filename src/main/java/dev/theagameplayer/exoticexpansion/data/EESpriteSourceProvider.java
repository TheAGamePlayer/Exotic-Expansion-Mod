package dev.theagameplayer.exoticexpansion.data;

import java.util.concurrent.CompletableFuture;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;

public final class EESpriteSourceProvider extends SpriteSourceProvider {
	public EESpriteSourceProvider(final PackOutput pOutput, final CompletableFuture<HolderLookup.Provider> pProvider, final ExistingFileHelper pExistingFileHelper) {
		super(pOutput, pProvider, ExoticExpansionMod.MODID, pExistingFileHelper);
	}
	
	@Override
	protected final void gather() {
		atlas(BLOCKS_ATLAS).addSource(new DirectoryLister("blockentity", "blockentity/"));
	}
}
