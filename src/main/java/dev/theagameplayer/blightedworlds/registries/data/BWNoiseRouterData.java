package dev.theagameplayer.blightedworlds.registries.data;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public final class BWNoiseRouterData {
	//Copied from NoiseRouterData with Modid inserted
	private static final ResourceKey<DensityFunction> SHIFT_X = ResourceKey.create(Registries.DENSITY_FUNCTION, BlightedWorldsMod.namespace("shift_x"));
	private static final ResourceKey<DensityFunction> SHIFT_Z = ResourceKey.create(Registries.DENSITY_FUNCTION, BlightedWorldsMod.namespace("shift_z"));

	public static final void bootstrap(final BootstapContext<DensityFunction> contextIn) {
		final HolderGetter<NormalNoise.NoiseParameters> noiseRegistry = contextIn.lookup(Registries.NOISE);
		final DensityFunction shiftX = registerAndWrap(contextIn, SHIFT_X, DensityFunctions.flatCache(DensityFunctions.cache2d(DensityFunctions.shiftA(noiseRegistry.getOrThrow(Noises.SHIFT)))));
		final DensityFunction shiftZ = registerAndWrap(contextIn, SHIFT_Z, DensityFunctions.flatCache(DensityFunctions.cache2d(DensityFunctions.shiftB(noiseRegistry.getOrThrow(Noises.SHIFT)))));
	}

	private static final DensityFunction registerAndWrap(final BootstapContext<DensityFunction> contextIn, final ResourceKey<DensityFunction> densityFunctionKeyIn, final DensityFunction densityFunctionIn) {
		return new DensityFunctions.HolderHolder(contextIn.register(densityFunctionKeyIn, densityFunctionIn));
	}

	private static final DensityFunction getFunction(final HolderGetter<DensityFunction> densityFunctionRegistryIn, final ResourceKey<DensityFunction> densityFunctionKeyIn) {
		return new DensityFunctions.HolderHolder(densityFunctionRegistryIn.getOrThrow(densityFunctionKeyIn));
	}

	private static final DensityFunction postProcess(final DensityFunction densityFunctionIn) {
		final DensityFunction densityFunction = DensityFunctions.blendDensity(densityFunctionIn);
		return DensityFunctions.mul(DensityFunctions.interpolated(densityFunction), DensityFunctions.constant(0.64D)).squeeze();
	}

	public static final NoiseRouter ancientNether(final HolderGetter<DensityFunction> densityFunctionRegistryIn, final HolderGetter<NormalNoise.NoiseParameters> noiseRegistryIn, final boolean largeBiomesIn, final boolean amplifiedIn) {
		final DensityFunction zero = DensityFunctions.zero();
		final DensityFunction shiftX = getFunction(densityFunctionRegistryIn, SHIFT_X);
		final DensityFunction shiftZ = getFunction(densityFunctionRegistryIn, SHIFT_Z);
		final DensityFunction temperature = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25D, noiseRegistryIn.getOrThrow(largeBiomesIn ? Noises.TEMPERATURE_LARGE : Noises.TEMPERATURE));
		final DensityFunction vegetation = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25D, noiseRegistryIn.getOrThrow(largeBiomesIn ? Noises.VEGETATION_LARGE : Noises.VEGETATION));
		//final DensityFunction densityfunction4 = postProcess(p_256378_);
		return new NoiseRouter(zero, zero, zero, zero, temperature, vegetation, zero, zero, zero, zero, zero, zero, zero, zero, zero);
	}

	private static final DensityFunction slideOverworld(final boolean p_224490_, final DensityFunction densityFunctionIn) {
		return slide(densityFunctionIn, -64, 384, p_224490_ ? 16 : 80, p_224490_ ? 0 : 64, -0.078125D, 0, 24, p_224490_ ? 0.4D : 0.1171875D);
	}

	private static DensityFunction slide(DensityFunction p_224444_, int p_224445_, int p_224446_, int p_224447_, int p_224448_, double p_224449_, int p_224450_, int p_224451_, double p_224452_) {
		DensityFunction densityfunction1 = DensityFunctions.yClampedGradient(p_224445_ + p_224446_ - p_224447_, p_224445_ + p_224446_ - p_224448_, 1.0D, 0.0D);
		DensityFunction $$9 = DensityFunctions.lerp(densityfunction1, p_224449_, p_224444_);
		DensityFunction densityfunction2 = DensityFunctions.yClampedGradient(p_224445_ + p_224450_, p_224445_ + p_224451_, 0.0D, 1.0D);
		return DensityFunctions.lerp(densityfunction2, p_224452_, $$9);
	}
}
