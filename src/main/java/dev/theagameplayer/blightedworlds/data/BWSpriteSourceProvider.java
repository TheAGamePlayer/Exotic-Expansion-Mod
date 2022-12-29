package dev.theagameplayer.blightedworlds.data;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SpriteSourceProvider;

public final class BWSpriteSourceProvider extends SpriteSourceProvider {
	public BWSpriteSourceProvider(final PackOutput packOutputIn, final ExistingFileHelper existingFileHelperIn) {
		super(packOutputIn, existingFileHelperIn, BlightedWorldsMod.MODID);
	}

	@Override
	protected void addSources() {
		atlas(BLOCKS_ATLAS).addSource(new DirectoryLister("blockentity", "blockentity/"));
	}
}
