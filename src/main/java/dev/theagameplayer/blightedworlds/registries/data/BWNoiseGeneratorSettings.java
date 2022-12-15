package dev.theagameplayer.blightedworlds.registries.data;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseSettings;

public final class BWNoiseGeneratorSettings {
	public static final ResourceKey<NoiseGeneratorSettings> ANCIENT_NETHER = ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(BlightedWorldsMod.MODID, "ancient_nether"));
	
	public static final void bootstrap(final BootstapContext<NoiseGeneratorSettings> contextIn) {
		contextIn.register(ANCIENT_NETHER, new NoiseGeneratorSettings(new NoiseSettings(-64, 384, 1, 2), Blocks.STONE.defaultBlockState(), Blocks.LAVA.defaultBlockState(), noiseRouterZero(), SurfaceRuleData.overworld(), new OverworldBiomeBuilder().spawnTarget(), 32, false, true, true, false));
	}
	
	private static final NoiseRouter noiseRouterZero() {
		final DensityFunction zero = DensityFunctions.zero();
		return new NoiseRouter(zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero, zero);
	}
}
