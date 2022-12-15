package dev.theagameplayer.blightedworlds.registries.data;

import java.util.HashMap;
import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public final class BWLevelStems {
	public static final ResourceKey<LevelStem> ANCIENT_NETHER = ResourceKey.create(Registries.LEVEL_STEM, new ResourceLocation(BlightedWorldsMod.MODID, "the_ancient_nether"));
	
	public static final HashMap<ResourceLocation, LevelStem> generate(final HolderLookup.Provider lookupIn) {
		final HashMap<ResourceLocation, LevelStem> map = new HashMap<>();
		map.put(ANCIENT_NETHER.location(), registerAncientNether(lookupIn, BWDimensionTypes.ANCIENT_NETHER, BWNoiseGeneratorSettings.ANCIENT_NETHER));
		return map;
	}
	
	private static final LevelStem registerAncientNether(final HolderLookup.Provider lookupIn, final ResourceKey<DimensionType> dimTypeIn, final ResourceKey<NoiseGeneratorSettings> settingsIn) {
		return new LevelStem(lookupIn.lookupOrThrow(Registries.DIMENSION_TYPE).getOrThrow(dimTypeIn), new NoiseBasedChunkGenerator(MultiNoiseBiomeSource.Preset.NETHER.biomeSource(lookupIn.lookupOrThrow(Registries.BIOME)), lookupIn.lookupOrThrow(Registries.NOISE_SETTINGS).getOrThrow(settingsIn)));
	}
}
