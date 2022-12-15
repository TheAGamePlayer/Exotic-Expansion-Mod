package dev.theagameplayer.blightedworlds.data.loot.packs;

import java.util.stream.Collectors;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import dev.theagameplayer.blightedworlds.registries.BWBlocks;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;

public final class BWBlockLoot extends VanillaBlockLoot {
	@Override
	protected final Iterable<Block> getKnownBlocks() {
		return ForgeRegistries.BLOCKS.getValues().stream()
				.filter(block -> BlightedWorldsMod.MODID.equals(ForgeRegistries.BLOCKS.getKey(block).getNamespace()))
				.collect(Collectors.toList());
	}
	
	@Override
	protected final void generate() {
		//Self Drop
		this.dropSelf(BWBlocks.RED_NETHER_BRICK_FENCE.get());
		//Misc
		this.add(BWBlocks.BLAZE_LANTERN.get(), block -> {
			return createSingleItemTableWithSilkTouch(block, Items.BLAZE_POWDER, UniformGenerator.between(1.0F, 3.0F));
		});
	}
}
