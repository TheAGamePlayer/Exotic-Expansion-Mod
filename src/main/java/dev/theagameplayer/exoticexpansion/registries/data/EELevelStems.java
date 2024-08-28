package dev.theagameplayer.exoticexpansion.registries.data;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import dev.theagameplayer.exoticexpansion.registries.custom.EECavernSettings;
import dev.theagameplayer.exoticexpansion.world.level.levelgen.CavernChunkGenerator;
import dev.theagameplayer.exoticexpansion.world.level.levelgen.CavernSettings;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public final class EELevelStems {
	public static final ResourceKey<LevelStem> ANCIENT_NETHER = ResourceKey.create(Registries.LEVEL_STEM, ExoticExpansionMod.namespace("the_ancient_nether"));
	
	public static final void bootstrap(final BootstrapContext<LevelStem> pContext) {
		pContext.register(ANCIENT_NETHER, registerAncientNether(pContext, EEDimensionTypes.ANCIENT_NETHER, EEMultiNoiseBiomeSourceParameterLists.ANCIENT_NETHER, EECavernSettings.ANCIENT_NETHER, EENoiseGeneratorSettings.ANCIENT_NETHER));
	}
	
	private static final LevelStem registerAncientNether(final BootstrapContext<LevelStem> pContext, final ResourceKey<DimensionType> pDimType, final ResourceKey<MultiNoiseBiomeSourceParameterList> pMultiNoiseBiomeSourceParameterList, final ResourceKey<CavernSettings> pCavernSettings, final ResourceKey<NoiseGeneratorSettings> pSettings) {
		return new LevelStem(pContext.lookup(Registries.DIMENSION_TYPE).getOrThrow(pDimType), new CavernChunkGenerator(MultiNoiseBiomeSource.createFromPreset(pContext.lookup(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST).getOrThrow(pMultiNoiseBiomeSourceParameterList)), pContext.lookup(EECavernSettings.CAVERN_SETTINGS).getOrThrow(pCavernSettings), pContext.lookup(Registries.NOISE_SETTINGS).getOrThrow(pSettings), true));
	}
}
