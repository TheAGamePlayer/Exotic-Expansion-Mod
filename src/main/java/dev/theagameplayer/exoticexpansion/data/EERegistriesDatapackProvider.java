package dev.theagameplayer.exoticexpansion.data;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import dev.theagameplayer.exoticexpansion.registries.custom.EECavernSettings;
import dev.theagameplayer.exoticexpansion.registries.data.EEDimensionTypes;
import dev.theagameplayer.exoticexpansion.registries.data.EELevelStems;
import dev.theagameplayer.exoticexpansion.registries.data.EEMultiNoiseBiomeSourceParameterLists;
import dev.theagameplayer.exoticexpansion.registries.data.EENoiseGeneratorSettings;
import dev.theagameplayer.exoticexpansion.registries.data.EENoiseRouterData;
import dev.theagameplayer.exoticexpansion.registries.data.EEStructureSets;
import dev.theagameplayer.exoticexpansion.registries.data.EEStructures;
import dev.theagameplayer.exoticexpansion.world.level.levelgen.CavernSettings;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

public final class EERegistriesDatapackProvider extends DatapackBuiltinEntriesProvider {
	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(EECavernSettings.CAVERN_SETTINGS, EECavernSettings::bootstrap)
			.add(Registries.STRUCTURE, EEStructures::bootstrap)
			.add(Registries.STRUCTURE_SET, EEStructureSets::bootstrap)
			.add(Registries.DENSITY_FUNCTION, EENoiseRouterData::bootstrap)
			.add(Registries.DIMENSION_TYPE, EEDimensionTypes::bootstrap)
			.add(Registries.NOISE_SETTINGS, EENoiseGeneratorSettings::bootstrap)
			.add(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST, EEMultiNoiseBiomeSourceParameterLists::bootstrap)
			.add(Registries.LEVEL_STEM, EELevelStems::bootstrap);
	
	public EERegistriesDatapackProvider(final PackOutput pOutput, final CompletableFuture<HolderLookup.Provider> pProvider) {
		super(pOutput, pProvider, BUILDER, Set.of(ExoticExpansionMod.MODID));
	}
	
	public static final void registerDatapackRegistries(final DataPackRegistryEvent.NewRegistry pEvent) {
		pEvent.dataPackRegistry(EECavernSettings.CAVERN_SETTINGS, CavernSettings.DIRECT_CODEC);
	}
}
