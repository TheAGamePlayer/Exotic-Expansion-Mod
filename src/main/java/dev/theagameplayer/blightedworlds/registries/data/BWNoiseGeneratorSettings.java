package dev.theagameplayer.blightedworlds.registries.data;

import java.util.List;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;

public final class BWNoiseGeneratorSettings {
	public static final ResourceKey<NoiseGeneratorSettings> ANCIENT_NETHER = ResourceKey.create(Registries.NOISE_SETTINGS, BlightedWorldsMod.namespace("ancient_nether"));
	
	public static final void bootstrap(final BootstapContext<NoiseGeneratorSettings> contextIn) {
		contextIn.register(ANCIENT_NETHER, new NoiseGeneratorSettings(new NoiseSettings(-64, 384, 1, 2), Blocks.NETHERRACK.defaultBlockState(), Blocks.LAVA.defaultBlockState(), BWNoiseRouterData.ancientNether(contextIn.lookup(Registries.DENSITY_FUNCTION), contextIn.lookup(Registries.NOISE), false, false), SurfaceRuleData.nether(), List.of(), -48, false, true, true, false));
	}
}
