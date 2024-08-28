package dev.theagameplayer.exoticexpansion.registries.data;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.TerrainProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public final class EENoiseRouterData {
	//Copied from NoiseRouterData with Modid inserted
	private static final DensityFunction ZERO = DensityFunctions.zero();
	private static final DensityFunction BLENDING_FACTOR = DensityFunctions.constant(10.0);
	private static final DensityFunction BLENDING_JAGGEDNESS = DensityFunctions.zero();
	private static final ResourceKey<DensityFunction> Y = createKey("y");
	private static final ResourceKey<DensityFunction> SHIFT_X = createKey("shift_x");
	private static final ResourceKey<DensityFunction> SHIFT_Z = createKey("shift_z");
	private static final ResourceKey<DensityFunction> BASE_3D_NOISE_ANCIENT_NETHER = createKey("ancient_nether/base_3d_noise");
	private static final ResourceKey<DensityFunction> CONTINENTS = createKey("ancient_nether/continents");
	private static final ResourceKey<DensityFunction> EROSION = createKey("ancient_nether/erosion");
	private static final ResourceKey<DensityFunction> RIDGES = createKey("ancient_nether/ridges");
	private static final ResourceKey<DensityFunction> RIDGES_FOLDED = createKey("ancient_nether/ridges_folded");
	private static final ResourceKey<DensityFunction> OFFSET = createKey("ancient_nether/offset");
	private static final ResourceKey<DensityFunction> FACTOR = createKey("ancient_nether/factor");
	private static final ResourceKey<DensityFunction> JAGGEDNESS = createKey("ancient_nether/jaggedness");
	private static final ResourceKey<DensityFunction> DEPTH = createKey("ancient_nether/depth");
	private static final ResourceKey<DensityFunction> SLOPED_CHEESE = createKey("ancient_nether/sloped_cheese");
	private static final ResourceKey<DensityFunction> CONTINENTS_LARGE = createKey("ancient_nether_large_biomes/continents");
	private static final ResourceKey<DensityFunction> EROSION_LARGE = createKey("ancient_nether_large_biomes/erosion");
	private static final ResourceKey<DensityFunction> OFFSET_LARGE = createKey("ancient_nether_large_biomes/offset");
	private static final ResourceKey<DensityFunction> FACTOR_LARGE = createKey("ancient_nether_large_biomes/factor");
	private static final ResourceKey<DensityFunction> JAGGEDNESS_LARGE = createKey("ancient_nether_large_biomes/jaggedness");
	private static final ResourceKey<DensityFunction> DEPTH_LARGE = createKey("ancient_nether_large_biomes/depth");
	private static final ResourceKey<DensityFunction> SLOPED_CHEESE_LARGE = createKey("ancient_nether_large_biomes/sloped_cheese");
	private static final ResourceKey<DensityFunction> OFFSET_AMPLIFIED = createKey("ancient_nether_amplified/offset");
	private static final ResourceKey<DensityFunction> FACTOR_AMPLIFIED = createKey("ancient_nether_amplified/factor");
	private static final ResourceKey<DensityFunction> JAGGEDNESS_AMPLIFIED = createKey("ancient_nether_amplified/jaggedness");
	private static final ResourceKey<DensityFunction> DEPTH_AMPLIFIED = createKey("ancient_nether_amplified/depth");
	private static final ResourceKey<DensityFunction> SLOPED_CHEESE_AMPLIFIED = createKey("ancient_nether_amplified/sloped_cheese");
	private static final ResourceKey<DensityFunction> SPAGHETTI_ROUGHNESS_FUNCTION = createKey("ancient_nether/caves/spaghetti_roughness_function");
	private static final ResourceKey<DensityFunction> ENTRANCES = createKey("ancient_nether/caves/entrances");
	private static final ResourceKey<DensityFunction> PILLARS = createKey("ancient_nether/caves/pillars");
	private static final ResourceKey<DensityFunction> NOODLE = createKey("ancient_nether/caves/noodle");
	private static final ResourceKey<DensityFunction> SPAGHETTI_2D_THICKNESS_MODULATOR = createKey("ancient_nether/caves/spaghetti_2d_thickness_modulator");
	private static final ResourceKey<DensityFunction> SPAGHETTI_2D = createKey("ancient_nether/caves/spaghetti_2d");

	private static final ResourceKey<DensityFunction> createKey(final String pLocation) {
		return ResourceKey.create(Registries.DENSITY_FUNCTION, ExoticExpansionMod.namespace(pLocation));
	}

	public static final void bootstrap(final BootstrapContext<DensityFunction> pContext) {
		final HolderGetter<NormalNoise.NoiseParameters> noiseRegistry = pContext.lookup(Registries.NOISE);
		final HolderGetter<DensityFunction> densityFunctionRegistry = pContext.lookup(Registries.DENSITY_FUNCTION);
		final int i = DimensionType.MIN_Y * 2;
		final int j = DimensionType.MAX_Y * 2;
		pContext.register(Y, DensityFunctions.yClampedGradient(i, j, (double)i, (double)j));
		final DensityFunction shiftX = registerAndWrap(pContext, SHIFT_X, DensityFunctions.flatCache(DensityFunctions.cache2d(DensityFunctions.shiftA(noiseRegistry.getOrThrow(Noises.SHIFT)))));
		final DensityFunction shiftZ = registerAndWrap(pContext, SHIFT_Z, DensityFunctions.flatCache(DensityFunctions.cache2d(DensityFunctions.shiftB(noiseRegistry.getOrThrow(Noises.SHIFT)))));
		pContext.register(BASE_3D_NOISE_ANCIENT_NETHER, BlendedNoise.createUnseeded(0.25, 0.375, 80.0, 160.0, 8.0));
		final Holder<DensityFunction> continents = pContext.register(
				CONTINENTS,
				DensityFunctions.flatCache(
						DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noiseRegistry.getOrThrow(Noises.CONTINENTALNESS))
						));
		final Holder<DensityFunction> erosion = pContext.register(
				EROSION,
				DensityFunctions.flatCache(DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noiseRegistry.getOrThrow(Noises.EROSION))));
		final DensityFunction ridges = registerAndWrap(
				pContext,
				RIDGES,
				DensityFunctions.flatCache(DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noiseRegistry.getOrThrow(Noises.RIDGE))));
		pContext.register(RIDGES_FOLDED, peaksAndValleys(ridges));
		final DensityFunction jagged = DensityFunctions.noise(noiseRegistry.getOrThrow(Noises.JAGGED), 1500.0, 0.0);
		registerTerrainNoises(pContext, densityFunctionRegistry, jagged, continents, erosion, OFFSET, FACTOR, JAGGEDNESS, DEPTH, SLOPED_CHEESE, false);
		final Holder<DensityFunction> continentsLarge = pContext.register(
				CONTINENTS_LARGE,
				DensityFunctions.flatCache(
						DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noiseRegistry.getOrThrow(Noises.CONTINENTALNESS_LARGE))
						));
		final Holder<DensityFunction> erosionLarge = pContext.register(
				EROSION_LARGE,
				DensityFunctions.flatCache(DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noiseRegistry.getOrThrow(Noises.EROSION_LARGE))));
		registerTerrainNoises(pContext, densityFunctionRegistry, jagged, continentsLarge, erosionLarge, OFFSET_LARGE, FACTOR_LARGE, JAGGEDNESS_LARGE, DEPTH_LARGE, SLOPED_CHEESE_LARGE, false);
		registerTerrainNoises(pContext, densityFunctionRegistry, jagged, continents, erosion, OFFSET_AMPLIFIED, FACTOR_AMPLIFIED, JAGGEDNESS_AMPLIFIED, DEPTH_AMPLIFIED, SLOPED_CHEESE_AMPLIFIED, true);
		pContext.register(SPAGHETTI_ROUGHNESS_FUNCTION, spaghettiRoughnessFunction(noiseRegistry));
        pContext.register(SPAGHETTI_2D_THICKNESS_MODULATOR, DensityFunctions.cacheOnce(DensityFunctions.mappedNoise(noiseRegistry.getOrThrow(Noises.SPAGHETTI_2D_THICKNESS), 2.0, 1.0, -0.6, -1.3)));
		pContext.register(SPAGHETTI_2D, spaghetti2D(densityFunctionRegistry, noiseRegistry));
		pContext.register(ENTRANCES, entrances(densityFunctionRegistry, noiseRegistry));
		pContext.register(NOODLE, noodle(densityFunctionRegistry, noiseRegistry));
		pContext.register(PILLARS, pillars(noiseRegistry));
	}

	private static final void registerTerrainNoises(final BootstrapContext<DensityFunction> pContext, final HolderGetter<DensityFunction> pDensityFunctionRegistry, final DensityFunction pJaggedNoise, final Holder<DensityFunction> pContinents, final Holder<DensityFunction> pErosion, final ResourceKey<DensityFunction> pOffsetKey, final ResourceKey<DensityFunction> pFactorKey, final ResourceKey<DensityFunction> pJaggednessKey, final ResourceKey<DensityFunction> pDepthKey, final ResourceKey<DensityFunction> pSlopedCheeseKey, final boolean pAmplified) {
		final DensityFunctions.Spline.Coordinate continentsCoord = new DensityFunctions.Spline.Coordinate(pContinents);
		final DensityFunctions.Spline.Coordinate erosionCoord = new DensityFunctions.Spline.Coordinate(pErosion);
		final DensityFunctions.Spline.Coordinate ridgesCoord = new DensityFunctions.Spline.Coordinate(pDensityFunctionRegistry.getOrThrow(RIDGES));
		final DensityFunctions.Spline.Coordinate ridgesFoldedCoord = new DensityFunctions.Spline.Coordinate(pDensityFunctionRegistry.getOrThrow(RIDGES_FOLDED));
		DensityFunction offset = registerAndWrap(
				pContext,
				pOffsetKey,
				DensityFunctions.add(DensityFunctions.spline(TerrainProvider.overworldOffset(continentsCoord, erosionCoord, ridgesFoldedCoord, pAmplified)),
						DensityFunctions.blendOffset()));
		final DensityFunction factor = registerAndWrap(
				pContext,
				pFactorKey,
				splineWithBlending(DensityFunctions.spline(
						TerrainProvider.overworldFactor(
								continentsCoord,
								erosionCoord,
								ridgesCoord,
								ridgesFoldedCoord,
								pAmplified)), BLENDING_FACTOR));
		final DensityFunction depth = registerAndWrap(
				pContext, 
				pDepthKey, 
				DensityFunctions.add(DensityFunctions.yClampedGradient(-64, 320, 2.5, -2.5), offset));
		final DensityFunction jaggedness = registerAndWrap(
				pContext,
				pJaggednessKey,
				splineWithBlending(DensityFunctions.spline(
						TerrainProvider.overworldJaggedness(
								continentsCoord,
								erosionCoord,
								ridgesCoord,
								ridgesFoldedCoord,
								pAmplified)), BLENDING_JAGGEDNESS));
		final DensityFunction jaggedness2 = DensityFunctions.mul(jaggedness, pJaggedNoise.halfNegative());
		final DensityFunction depthJaggedness = noiseGradientDensity(factor, DensityFunctions.add(depth, jaggedness2));
		pContext.register(pSlopedCheeseKey, DensityFunctions.add(depthJaggedness, getFunction(pDensityFunctionRegistry, BASE_3D_NOISE_ANCIENT_NETHER)));
	}

	private static final DensityFunction registerAndWrap(final BootstrapContext<DensityFunction> pContext, final ResourceKey<DensityFunction> pKey, final DensityFunction pDensityFunction) {
		return new DensityFunctions.HolderHolder(pContext.register(pKey, pDensityFunction));
	}

	private static final DensityFunction getFunction(final HolderGetter<DensityFunction> pDensityFunctions, final ResourceKey<DensityFunction> pKey) {
		return new DensityFunctions.HolderHolder(pDensityFunctions.getOrThrow(pKey));
	}

	private static final DensityFunction peaksAndValleys(final DensityFunction pDensityFunction) {
		return DensityFunctions.mul(
				DensityFunctions.add(
						DensityFunctions.add(pDensityFunction.abs(), DensityFunctions.constant(-0.6666666666666666)).abs(), DensityFunctions.constant(-0.3333333333333333)
						),
				DensityFunctions.constant(-3.0)
				);
	}

	private static final DensityFunction spaghettiRoughnessFunction(final HolderGetter<NormalNoise.NoiseParameters> pNoiseParameters) {
		final DensityFunction densityfunction = DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.SPAGHETTI_ROUGHNESS));
		final DensityFunction densityfunction1 = DensityFunctions.mappedNoise(pNoiseParameters.getOrThrow(Noises.SPAGHETTI_ROUGHNESS_MODULATOR), 0.0, -0.1);
		return DensityFunctions.cacheOnce(DensityFunctions.mul(densityfunction1, DensityFunctions.add(densityfunction.abs(), DensityFunctions.constant(-0.4))));
	}

	private static final DensityFunction entrances(final HolderGetter<DensityFunction> pDensityFunction, final HolderGetter<NormalNoise.NoiseParameters> pNoiseParameters) {
		final DensityFunction spaghetti3DRarity = DensityFunctions.cacheOnce(DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.SPAGHETTI_3D_RARITY), 2.0, 1.0));
		final DensityFunction spaghetti3DThickness = DensityFunctions.mappedNoise(pNoiseParameters.getOrThrow(Noises.SPAGHETTI_3D_THICKNESS), -0.065, -0.088);
		final DensityFunction spaghetti3D1 = DensityFunctions.weirdScaledSampler(spaghetti3DRarity, pNoiseParameters.getOrThrow(Noises.SPAGHETTI_3D_1), DensityFunctions.WeirdScaledSampler.RarityValueMapper.TYPE1);
		final DensityFunction spaghetti3D2 = DensityFunctions.weirdScaledSampler(spaghetti3DRarity, pNoiseParameters.getOrThrow(Noises.SPAGHETTI_3D_2), DensityFunctions.WeirdScaledSampler.RarityValueMapper.TYPE1);
		final DensityFunction spaghetti3D3 = DensityFunctions.add(DensityFunctions.max(spaghetti3D1, spaghetti3D2), spaghetti3DThickness).clamp(-1.0, 1.0);
		final DensityFunction spaghettiRoughness = getFunction(pDensityFunction, SPAGHETTI_ROUGHNESS_FUNCTION);
		final DensityFunction caveEntrance = DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.CAVE_ENTRANCE), 0.75, 0.5);
		final DensityFunction caveEntrance2 = DensityFunctions.add(DensityFunctions.add(caveEntrance, DensityFunctions.constant(0.37)), DensityFunctions.yClampedGradient(-10, 30, 0.3, 0.0));
		return DensityFunctions.cacheOnce(DensityFunctions.min(caveEntrance2, DensityFunctions.add(spaghettiRoughness, spaghetti3D3)));
	}

	private static final DensityFunction noodle(final HolderGetter<DensityFunction> pDensityFunctions, final HolderGetter<NormalNoise.NoiseParameters> pNoiseParameters) {
		final DensityFunction y = getFunction(pDensityFunctions, Y);
		final DensityFunction noodle = yLimitedInterpolatable(y, DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.NOODLE), 1.0, 1.0), -60, 320, -1);
		final DensityFunction noodleThickness = yLimitedInterpolatable(y, DensityFunctions.mappedNoise(pNoiseParameters.getOrThrow(Noises.NOODLE_THICKNESS), 1.0, 1.0, -0.05, -0.1), -60, 320, 0);
		final DensityFunction noodleRidgeA = yLimitedInterpolatable(y, DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.NOODLE_RIDGE_A), 2.6666666666666665, 2.6666666666666665), -60, 320, 0);
		final DensityFunction noodleRidgeB = yLimitedInterpolatable(y, DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.NOODLE_RIDGE_B), 2.6666666666666665, 2.6666666666666665), -60, 320, 0);
		final DensityFunction noodleRidgeAB = DensityFunctions.mul(DensityFunctions.constant(1.5), DensityFunctions.max(noodleRidgeA.abs(), noodleRidgeB.abs()));
		return DensityFunctions.rangeChoice(noodle, -1000000.0, 0.0, DensityFunctions.constant(64.0), DensityFunctions.add(noodleThickness, noodleRidgeAB));
	}

	private static final DensityFunction pillars(final HolderGetter<NormalNoise.NoiseParameters> pNoiseParameters) {
		final DensityFunction pillar = DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.PILLAR), 25.0, 0.3);
		final DensityFunction pillarRareness = DensityFunctions.mappedNoise(pNoiseParameters.getOrThrow(Noises.PILLAR_RARENESS), 0.0, -2.0);
		final DensityFunction pillarThickness = DensityFunctions.mappedNoise(pNoiseParameters.getOrThrow(Noises.PILLAR_THICKNESS), 0.0, 1.1);
		final DensityFunction pillar2 = DensityFunctions.add(DensityFunctions.mul(pillar, DensityFunctions.constant(2.0)), pillarRareness);
		return DensityFunctions.cacheOnce(DensityFunctions.mul(pillar2, pillarThickness.cube()));
	}

	private static final DensityFunction spaghetti2D(final HolderGetter<DensityFunction> pDensityFunctions, final HolderGetter<NormalNoise.NoiseParameters> pNoiseParameters) {
		final DensityFunction spaghetti2DModulator = DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.SPAGHETTI_2D_MODULATOR), 2.0, 1.0);
		final DensityFunction spaghetti2D = DensityFunctions.weirdScaledSampler(spaghetti2DModulator, pNoiseParameters.getOrThrow(Noises.SPAGHETTI_2D), DensityFunctions.WeirdScaledSampler.RarityValueMapper.TYPE2);
		final DensityFunction spaghetti2DElevation = DensityFunctions.mappedNoise(pNoiseParameters.getOrThrow(Noises.SPAGHETTI_2D_ELEVATION), 0.0, (double)Math.floorDiv(-64, 8), 8.0);
		final DensityFunction spaghetti2DThicknessModulator = getFunction(pDensityFunctions, SPAGHETTI_2D_THICKNESS_MODULATOR);
		final DensityFunction spaghetti2DElevation2 = DensityFunctions.add(spaghetti2DElevation, DensityFunctions.yClampedGradient(-64, 320, 8.0, -40.0)).abs();
		final DensityFunction spaghetti2D2 = DensityFunctions.add(spaghetti2DElevation2, spaghetti2DThicknessModulator).cube();
		final DensityFunction spaghetti2D3 = DensityFunctions.add(spaghetti2D, DensityFunctions.mul(DensityFunctions.constant(0.083), spaghetti2DThicknessModulator));
		return DensityFunctions.max(spaghetti2D3, spaghetti2D2).clamp(-1.0, 1.0);
	}

	private static final DensityFunction underground(final HolderGetter<DensityFunction> pDensityFunctions, final HolderGetter<NormalNoise.NoiseParameters> pNoiseParameters, final DensityFunction pSlopedCheese) {
		final DensityFunction spaghetti2D = getFunction(pDensityFunctions, SPAGHETTI_2D);
		final DensityFunction spaghettiRoughness = getFunction(pDensityFunctions, SPAGHETTI_ROUGHNESS_FUNCTION);
		final DensityFunction caveLayer = DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.CAVE_LAYER), 8.0);
		final DensityFunction caveLayer2 = DensityFunctions.mul(DensityFunctions.constant(4.0), caveLayer.square());
		final DensityFunction caveCheese = DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.CAVE_CHEESE), 0.6666666666666666);
		final DensityFunction caveCheese2 = DensityFunctions.add(
				DensityFunctions.add(DensityFunctions.constant(0.27), caveCheese).clamp(-1.0, 1.0),
				DensityFunctions.add(DensityFunctions.constant(1.5), DensityFunctions.mul(DensityFunctions.constant(-0.64), pSlopedCheese)).clamp(0.0, 0.5));
		final DensityFunction caveLayerCheese = caveLayer2;//DensityFunctions.add(caveLayer2, caveCheese2);
		final DensityFunction underground = caveLayer2;/*DensityFunctions.min(caveLayerCheese, getFunction(pDensityFunctions, ENTRANCES));/*DensityFunctions.min(
				DensityFunctions.min(caveLayerCheese, getFunction(pDensityFunctions, ENTRANCES)), DensityFunctions.add(spaghetti2D, spaghettiRoughness));*/
		final DensityFunction pillars = getFunction(pDensityFunctions, PILLARS);
		final DensityFunction pillars2 = DensityFunctions.rangeChoice(pillars, -1000000.0, 0.03, DensityFunctions.constant(-1000000.0), pillars);
		return underground;//DensityFunctions.max(underground, pillars2);
	}

	private static final DensityFunction postProcess(final DensityFunction pDensityFunction) {
		final DensityFunction densityFunction = DensityFunctions.blendDensity(pDensityFunction);
		return DensityFunctions.mul(DensityFunctions.interpolated(densityFunction), DensityFunctions.constant(0.64D)).squeeze();
	}

	public static final NoiseRouter ancientNether(final HolderGetter<DensityFunction> pDensityFunctions, final HolderGetter<NormalNoise.NoiseParameters> pNoiseParameters, final boolean pLarge, final boolean pAmplified) {
		final DensityFunction shiftX = getFunction(pDensityFunctions, SHIFT_X);
		final DensityFunction shiftZ = getFunction(pDensityFunctions, SHIFT_Z);
		final DensityFunction temperature = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25D, pNoiseParameters.getOrThrow(pLarge ? Noises.TEMPERATURE_LARGE : Noises.TEMPERATURE));
		final DensityFunction vegetation = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25D, pNoiseParameters.getOrThrow(pLarge ? Noises.VEGETATION_LARGE : Noises.VEGETATION));
		final DensityFunction factor = getFunction(pDensityFunctions, pLarge ? FACTOR_LARGE : (pAmplified ? FACTOR_AMPLIFIED : FACTOR));
		final DensityFunction continents = getFunction(pDensityFunctions, pLarge ? CONTINENTS_LARGE : CONTINENTS);
		final DensityFunction erosion = getFunction(pDensityFunctions, pLarge ? EROSION_LARGE : EROSION);
		final DensityFunction depth = getFunction(pDensityFunctions, pLarge ? DEPTH_LARGE : (pAmplified ? DEPTH_AMPLIFIED : DEPTH));
		final DensityFunction ridges = getFunction(pDensityFunctions, RIDGES);
		final DensityFunction factorDepth = noiseGradientDensity(DensityFunctions.cache2d(factor), depth);
		final DensityFunction initialDensityWithoutJaggedness = slideAncientNether(pAmplified, DensityFunctions.add(factorDepth, DensityFunctions.constant(-0.703125)).clamp(-64.0, 64.0));
		final DensityFunction slopedCheese = getFunction(pDensityFunctions, pLarge ? SLOPED_CHEESE_LARGE : (pAmplified ? SLOPED_CHEESE_AMPLIFIED : SLOPED_CHEESE));
		final DensityFunction entrances = DensityFunctions.min(slopedCheese, DensityFunctions.mul(DensityFunctions.constant(5.0), getFunction(pDensityFunctions, ENTRANCES)));
		final DensityFunction underground = underground(pDensityFunctions, pNoiseParameters, slopedCheese);//DensityFunctions.rangeChoice(slopedCheese, -1000000.0, 1.5625, entrances, underground(pDensityFunctions, pNoiseParameters, slopedCheese));
		final DensityFunction finalDensity = postProcess(slideAncientNether(pAmplified, underground));//DensityFunctions.min(postProcess(slideAncientNether(pAmplified, underground)), getFunction(pDensityFunctions, NOODLE));
		return new NoiseRouter(ZERO, ZERO, ZERO, ZERO, temperature, vegetation, ZERO, ZERO, ZERO, ZERO, ZERO, finalDensity, ZERO, ZERO, ZERO);
		//return new NoiseRouter(ZERO, ZERO, ZERO, ZERO, temperature, vegetation, continents, erosion, depth, ridges, initialDensityWithoutJaggedness, finalDensity, ZERO, ZERO, ZERO);
	}

	private static final DensityFunction slideAncientNether(final boolean pAmplified, final DensityFunction pDensityFunction) {
		return slide(pDensityFunction, -64, 384, pAmplified ? 16 : 80, pAmplified ? 0 : 48, -0.078125D, 0, 4, pAmplified ? 0.4D : 0.1171875D);
	}

	private static final DensityFunction splineWithBlending(final DensityFunction pMinFunction, final DensityFunction pMaxFunction) {
		final DensityFunction densityFunction = DensityFunctions.lerp(DensityFunctions.blendAlpha(), pMaxFunction, pMinFunction);
		return DensityFunctions.flatCache(DensityFunctions.cache2d(densityFunction));
	}

	private static final DensityFunction noiseGradientDensity(final DensityFunction pMinFunction, final DensityFunction pMaxFunction) {
		final DensityFunction densityFunction = DensityFunctions.mul(pMaxFunction, pMinFunction);
		return DensityFunctions.mul(DensityFunctions.constant(4.0), densityFunction.quarterNegative());
	}

	private static final DensityFunction yLimitedInterpolatable(final DensityFunction p_209472_, final DensityFunction p_209473_, final int p_209474_, final int p_209475_, final int p_209476_) {
		return DensityFunctions.interpolated(DensityFunctions.rangeChoice(p_209472_, (double)p_209474_, (double)(p_209475_ + 1), p_209473_, DensityFunctions.constant((double)p_209476_)));
	}

	private static final DensityFunction slide(final DensityFunction pDensityFunction, final int pMinY, final int pMaxY, final int p_224447_, final int p_224448_, final double p_224449_, final int p_224450_, final int p_224451_, final double p_224452_) {
		final DensityFunction densityfunction1 = DensityFunctions.yClampedGradient(pMinY + pMaxY - p_224447_, pMinY + pMaxY - p_224448_, 1.0D, 0.0D);
		final DensityFunction $$9 = DensityFunctions.lerp(densityfunction1, p_224449_, pDensityFunction);
		final DensityFunction densityfunction2 = DensityFunctions.yClampedGradient(pMinY + p_224450_, pMinY + p_224451_, 0.0D, 1.0D);
		return DensityFunctions.lerp(densityfunction2, p_224452_, $$9);
	}
}
