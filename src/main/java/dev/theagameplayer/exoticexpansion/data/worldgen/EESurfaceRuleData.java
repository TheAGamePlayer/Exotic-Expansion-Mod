package dev.theagameplayer.exoticexpansion.data.worldgen;

import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public final class EESurfaceRuleData {
	private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
	private static final SurfaceRules.RuleSource GRAVEL = makeStateRule(Blocks.GRAVEL);
	private static final SurfaceRules.RuleSource LAVA = makeStateRule(Blocks.LAVA);
	private static final SurfaceRules.RuleSource NETHERRACK = makeStateRule(Blocks.NETHERRACK);
	private static final SurfaceRules.RuleSource SOUL_SAND = makeStateRule(Blocks.SOUL_SAND);
	private static final SurfaceRules.RuleSource SOUL_SOIL = makeStateRule(Blocks.SOUL_SOIL);
	private static final SurfaceRules.RuleSource BASALT = makeStateRule(Blocks.BASALT);
	private static final SurfaceRules.RuleSource BLACKSTONE = makeStateRule(Blocks.BLACKSTONE);
	private static final SurfaceRules.RuleSource WARPED_WART_BLOCK = makeStateRule(Blocks.WARPED_WART_BLOCK);
	private static final SurfaceRules.RuleSource WARPED_NYLIUM = makeStateRule(Blocks.WARPED_NYLIUM);
	private static final SurfaceRules.RuleSource NETHER_WART_BLOCK = makeStateRule(Blocks.NETHER_WART_BLOCK);
	private static final SurfaceRules.RuleSource CRIMSON_NYLIUM = makeStateRule(Blocks.CRIMSON_NYLIUM);

	private static final SurfaceRules.RuleSource makeStateRule(final Block pBlock) {
		return SurfaceRules.state(pBlock.defaultBlockState());
	}

	public static final SurfaceRules.RuleSource ancientNether() {
		final SurfaceRules.ConditionSource surfaceRules$conditionSource = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(31), 0);
		final SurfaceRules.ConditionSource surfaceRules$conditionSource1 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(32), 0);
		final SurfaceRules.ConditionSource surfaceRules$conditionSource2 = SurfaceRules.yStartCheck(VerticalAnchor.absolute(30), 0);
		final SurfaceRules.ConditionSource surfaceRules$conditionSource3 = SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(35), 0));
		final SurfaceRules.ConditionSource surfaceRules$conditionSource4 = SurfaceRules.yBlockCheck(VerticalAnchor.belowTop(5), 0);
		final SurfaceRules.ConditionSource surfaceRules$conditionSource5 = SurfaceRules.hole();
		final SurfaceRules.ConditionSource surfaceRules$conditionSource6 = SurfaceRules.noiseCondition(Noises.SOUL_SAND_LAYER, -0.012);
		final SurfaceRules.ConditionSource surfaceRules$conditionSource7 = SurfaceRules.noiseCondition(Noises.GRAVEL_LAYER, -0.012);
		final SurfaceRules.ConditionSource surfaceRules$conditionSource8 = SurfaceRules.noiseCondition(Noises.PATCH, -0.012);
		final SurfaceRules.ConditionSource surfaceRules$conditionSource9 = SurfaceRules.noiseCondition(Noises.NETHERRACK, 0.54);
		final SurfaceRules.ConditionSource surfaceRules$conditionSource10 = SurfaceRules.noiseCondition(Noises.NETHER_WART, 1.17);
		final SurfaceRules.ConditionSource surfaceRules$conditionSource11 = SurfaceRules.noiseCondition(Noises.NETHER_STATE_SELECTOR, 0.0);
		final SurfaceRules.RuleSource surfaceRules$ruleSource = SurfaceRules.ifTrue(surfaceRules$conditionSource8, SurfaceRules.ifTrue(surfaceRules$conditionSource2, SurfaceRules.ifTrue(surfaceRules$conditionSource3, GRAVEL)));
		return SurfaceRules.sequence(
				SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK),
				SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())), BEDROCK),
				SurfaceRules.ifTrue(surfaceRules$conditionSource4, NETHERRACK),
				SurfaceRules.ifTrue(
						SurfaceRules.isBiome(Biomes.BASALT_DELTAS),
						SurfaceRules.sequence(
								SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING, BASALT),
								SurfaceRules.ifTrue(
										SurfaceRules.UNDER_FLOOR,
										SurfaceRules.sequence(surfaceRules$ruleSource, SurfaceRules.ifTrue(surfaceRules$conditionSource11, BASALT), BLACKSTONE)
										)
								)
						),
				SurfaceRules.ifTrue(
						SurfaceRules.isBiome(Biomes.SOUL_SAND_VALLEY),
						SurfaceRules.sequence(
								SurfaceRules.ifTrue(
										SurfaceRules.UNDER_CEILING, SurfaceRules.sequence(SurfaceRules.ifTrue(surfaceRules$conditionSource11, SOUL_SAND), SOUL_SOIL)
										),
								SurfaceRules.ifTrue(
										SurfaceRules.UNDER_FLOOR,
										SurfaceRules.sequence(surfaceRules$ruleSource, SurfaceRules.ifTrue(surfaceRules$conditionSource11, SOUL_SAND), SOUL_SOIL)
										)
								)
						),
				SurfaceRules.ifTrue(
						SurfaceRules.ON_FLOOR,
						SurfaceRules.sequence(
								SurfaceRules.ifTrue(SurfaceRules.not(surfaceRules$conditionSource1), SurfaceRules.ifTrue(surfaceRules$conditionSource5, LAVA)),
								SurfaceRules.ifTrue(
										SurfaceRules.isBiome(Biomes.WARPED_FOREST),
										SurfaceRules.ifTrue(
												SurfaceRules.not(surfaceRules$conditionSource9),
												SurfaceRules.ifTrue(
														surfaceRules$conditionSource,
														SurfaceRules.sequence(SurfaceRules.ifTrue(surfaceRules$conditionSource10, WARPED_WART_BLOCK), WARPED_NYLIUM)
														)
												)
										),
								SurfaceRules.ifTrue(
										SurfaceRules.isBiome(Biomes.CRIMSON_FOREST),
										SurfaceRules.ifTrue(
												SurfaceRules.not(surfaceRules$conditionSource9),
												SurfaceRules.ifTrue(
														surfaceRules$conditionSource,
														SurfaceRules.sequence(SurfaceRules.ifTrue(surfaceRules$conditionSource10, NETHER_WART_BLOCK), CRIMSON_NYLIUM)
														)
												)
										)
								)
						),
				SurfaceRules.ifTrue(
						SurfaceRules.isBiome(Biomes.NETHER_WASTES),
						SurfaceRules.sequence(
								SurfaceRules.ifTrue(
										SurfaceRules.UNDER_FLOOR,
										SurfaceRules.ifTrue(
												surfaceRules$conditionSource6,
												SurfaceRules.sequence(
														SurfaceRules.ifTrue(
																SurfaceRules.not(surfaceRules$conditionSource5),
																SurfaceRules.ifTrue(surfaceRules$conditionSource2, SurfaceRules.ifTrue(surfaceRules$conditionSource3, SOUL_SAND))
																),
														NETHERRACK
														)
												)
										),
								SurfaceRules.ifTrue(
										SurfaceRules.ON_FLOOR,
										SurfaceRules.ifTrue(
												surfaceRules$conditionSource,
												SurfaceRules.ifTrue(
														surfaceRules$conditionSource3,
														SurfaceRules.ifTrue(
																surfaceRules$conditionSource7,
																SurfaceRules.sequence(
																		SurfaceRules.ifTrue(surfaceRules$conditionSource1, GRAVEL),
																		SurfaceRules.ifTrue(SurfaceRules.not(surfaceRules$conditionSource5), GRAVEL)
																		)
																)
														)
												)
										)
								)
						),
				NETHERRACK
				);
	}
}
