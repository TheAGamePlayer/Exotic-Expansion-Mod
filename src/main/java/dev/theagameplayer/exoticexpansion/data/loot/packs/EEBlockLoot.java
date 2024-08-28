package dev.theagameplayer.exoticexpansion.data.loot.packs;

import java.util.stream.Collectors;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import dev.theagameplayer.exoticexpansion.registries.EEBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public final class EEBlockLoot extends VanillaBlockLoot {
	public EEBlockLoot(final HolderLookup.Provider pRegistries) {
		super(pRegistries);
	}
	
	@Override
	protected final Iterable<Block> getKnownBlocks() {
		return BuiltInRegistries.BLOCK.stream()
				.filter(block -> ExoticExpansionMod.MODID.equals(BuiltInRegistries.BLOCK.getKey(block).getNamespace()))
				.collect(Collectors.toList());
	}
	
	@Override
	protected final void generate() {
		//Self Drop
		this.dropSelf(EEBlocks.RED_NETHER_BRICK_FENCE.get());
		//Misc
		this.add(EEBlocks.BLAZE_LANTERN.get(), block -> {
			return createSingleItemTableWithSilkTouch(block, Items.BLAZE_POWDER, UniformGenerator.between(1.0F, 3.0F));
		});
	}
}
