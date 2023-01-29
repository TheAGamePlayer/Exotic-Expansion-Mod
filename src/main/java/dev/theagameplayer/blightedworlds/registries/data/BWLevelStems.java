package dev.theagameplayer.blightedworlds.registries.data;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import dev.theagameplayer.blightedworlds.registries.custom.BWCavernSettings;
import dev.theagameplayer.blightedworlds.world.level.levelgen.CavernChunkGenerator;
import dev.theagameplayer.blightedworlds.world.level.levelgen.CavernSettings;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public final class BWLevelStems {
	public static final ResourceKey<LevelStem> ANCIENT_NETHER = ResourceKey.create(Registries.LEVEL_STEM, BlightedWorldsMod.namespace("the_ancient_nether"));
	
	public static final void bootstrap(final BootstapContext<LevelStem> contextIn) {
		contextIn.register(ANCIENT_NETHER, registerAncientNether(contextIn, BWDimensionTypes.ANCIENT_NETHER, BWCavernSettings.ANCIENT_NETHER, BWNoiseGeneratorSettings.ANCIENT_NETHER));
	}
	
	private static final LevelStem registerAncientNether(final BootstapContext<LevelStem> contextIn, final ResourceKey<DimensionType> dimTypeIn, final ResourceKey<CavernSettings> cavernSettingsIn, final ResourceKey<NoiseGeneratorSettings> settingsIn) {
		return new LevelStem(contextIn.lookup(Registries.DIMENSION_TYPE).getOrThrow(dimTypeIn), new CavernChunkGenerator(MultiNoiseBiomeSource.Preset.NETHER.biomeSource(contextIn.lookup(Registries.BIOME)), contextIn.lookup(BWCavernSettings.CAVERN_SETTINGS).getOrThrow(cavernSettingsIn), contextIn.lookup(Registries.NOISE_SETTINGS).getOrThrow(settingsIn)));
	}
}
