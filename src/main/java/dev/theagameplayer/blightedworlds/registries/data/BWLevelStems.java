package dev.theagameplayer.blightedworlds.registries.data;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public final class BWLevelStems {
	public static final ResourceKey<LevelStem> ANCIENT_NETHER = ResourceKey.create(Registries.LEVEL_STEM, new ResourceLocation(BlightedWorldsMod.MODID, "the_ancient_nether"));
	
	public static final void bootstrap(final BootstapContext<LevelStem> contextIn) {
		contextIn.register(ANCIENT_NETHER, registerAncientNether(contextIn, BWDimensionTypes.ANCIENT_NETHER, BWNoiseGeneratorSettings.ANCIENT_NETHER));
	}
	
	private static final LevelStem registerAncientNether(final BootstapContext<LevelStem> contextIn, final ResourceKey<DimensionType> dimTypeIn, final ResourceKey<NoiseGeneratorSettings> settingsIn) {
		return new LevelStem(contextIn.lookup(Registries.DIMENSION_TYPE).getOrThrow(dimTypeIn), new NoiseBasedChunkGenerator(MultiNoiseBiomeSource.Preset.NETHER.biomeSource(contextIn.lookup(Registries.BIOME)), contextIn.lookup(Registries.NOISE_SETTINGS).getOrThrow(settingsIn)));
	}
}
