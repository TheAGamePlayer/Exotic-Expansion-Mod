package dev.theagameplayer.exoticexpansion.registries.data;

import java.util.List;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import dev.theagameplayer.exoticexpansion.data.worldgen.EESurfaceRuleData;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;

public final class EENoiseGeneratorSettings {
public static final ResourceKey<NoiseGeneratorSettings> ANCIENT_NETHER = ResourceKey.create(Registries.NOISE_SETTINGS, ExoticExpansionMod.namespace("ancient_nether"));
	
	public static final void bootstrap(final BootstrapContext<NoiseGeneratorSettings> pContext) {
		pContext.register(ANCIENT_NETHER, new NoiseGeneratorSettings(new NoiseSettings(-64, 384, 1, 2), Blocks.NETHERRACK.defaultBlockState(), Blocks.LAVA.defaultBlockState(), EENoiseRouterData.ancientNether(pContext.lookup(Registries.DENSITY_FUNCTION), pContext.lookup(Registries.NOISE), false, false), EESurfaceRuleData.ancientNether(), List.of(), -48, false, true, true, false));
	}
}
