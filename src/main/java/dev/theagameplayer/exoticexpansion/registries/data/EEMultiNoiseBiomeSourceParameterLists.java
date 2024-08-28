package dev.theagameplayer.exoticexpansion.registries.data;

import java.util.List;
import java.util.function.Function;

import com.mojang.datafixers.util.Pair;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;

public final class EEMultiNoiseBiomeSourceParameterLists {
	public static final ResourceKey<MultiNoiseBiomeSourceParameterList> ANCIENT_NETHER = ResourceKey.create(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST, ExoticExpansionMod.namespace("ancient_nether"));

	public static final void bootstrap(final BootstrapContext<MultiNoiseBiomeSourceParameterList> pContext) {
		final HolderGetter<Biome> holdergetter = pContext.lookup(Registries.BIOME);
		pContext.register(ANCIENT_NETHER, new MultiNoiseBiomeSourceParameterList(Presets.ANCIENT_NETHER, holdergetter));
	}

	public static final class Presets {
		public static final MultiNoiseBiomeSourceParameterList.Preset ANCIENT_NETHER = new MultiNoiseBiomeSourceParameterList.Preset(ExoticExpansionMod.namespace("ancient_nether"), new MultiNoiseBiomeSourceParameterList.Preset.SourceProvider() {
			@Override
			public final <T> Climate.ParameterList<T> apply(final Function<ResourceKey<Biome>, T> pBiomes) {
				return new Climate.ParameterList<>(
						List.of(Pair.of(Climate.parameters(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), pBiomes.apply(Biomes.NETHER_WASTES)),
	                            Pair.of(Climate.parameters(0.0F, -0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), pBiomes.apply(Biomes.SOUL_SAND_VALLEY)),
	                            Pair.of(Climate.parameters(0.4F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), pBiomes.apply(Biomes.CRIMSON_FOREST)),
	                            Pair.of(Climate.parameters(0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.375F), pBiomes.apply(Biomes.WARPED_FOREST)),
	                            Pair.of(Climate.parameters(-0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.175F), pBiomes.apply(Biomes.BASALT_DELTAS))));
			}
		});
	}
}
