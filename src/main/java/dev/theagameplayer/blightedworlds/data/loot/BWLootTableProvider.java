package dev.theagameplayer.blightedworlds.data.loot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import dev.theagameplayer.blightedworlds.data.loot.packs.BWBlockLoot;
import dev.theagameplayer.blightedworlds.data.loot.packs.BWChestLoot;
import dev.theagameplayer.blightedworlds.data.loot.packs.BWEntityLoot;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public final class BWLootTableProvider extends LootTableProvider {
	public BWLootTableProvider(final PackOutput packOutputIn) { //TODO: fix Immutable Set
		super(packOutputIn, ImmutableSet.of(), ImmutableList.of(new LootTableProvider.SubProviderEntry(BWBlockLoot::new, LootContextParamSets.BLOCK), new LootTableProvider.SubProviderEntry(BWChestLoot::new, LootContextParamSets.CHEST), new LootTableProvider.SubProviderEntry(BWEntityLoot::new, LootContextParamSets.ENTITY)));
	}
}
