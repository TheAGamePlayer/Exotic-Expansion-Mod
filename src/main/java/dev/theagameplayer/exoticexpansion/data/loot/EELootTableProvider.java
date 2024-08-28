package dev.theagameplayer.exoticexpansion.data.loot;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import dev.theagameplayer.exoticexpansion.data.loot.packs.EEBlockLoot;
import dev.theagameplayer.exoticexpansion.data.loot.packs.EEChestLoot;
import dev.theagameplayer.exoticexpansion.data.loot.packs.EEEntityLoot;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public final class EELootTableProvider extends LootTableProvider {
	public EELootTableProvider(final PackOutput pOutput, final CompletableFuture<HolderLookup.Provider> pRegistries) { //TODO: fix Immutable Set
		super(pOutput, Set.of(), List.of(
				new LootTableProvider.SubProviderEntry(EEBlockLoot::new, LootContextParamSets.BLOCK),
				new LootTableProvider.SubProviderEntry(EEChestLoot::new, LootContextParamSets.CHEST), 
				new LootTableProvider.SubProviderEntry(EEEntityLoot::new, LootContextParamSets.ENTITY)),
				pRegistries);
	}

}
