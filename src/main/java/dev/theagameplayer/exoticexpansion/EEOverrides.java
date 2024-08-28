package dev.theagameplayer.exoticexpansion;

import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import dev.theagameplayer.exoticexpansion.registries.EEBlocks;
import dev.theagameplayer.exoticexpansion.registries.data.EEMultiNoiseBiomeSourceParameterLists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.BasaltColumnsFeature;
import net.minecraft.world.level.levelgen.feature.DeltaFeature;
import net.minecraft.world.level.levelgen.structure.StructurePiece;

public final class EEOverrides {
	//You must include the original List so it is compatible with other mods!
	public static final void doOverrides() {
		StructurePiece.SHAPE_CHECK_BLOCKS = ImmutableSet.<Block>builder().addAll(StructurePiece.SHAPE_CHECK_BLOCKS).add(Blocks.RED_NETHER_BRICK_WALL, EEBlocks.RED_NETHER_BRICK_FENCE.get()).build();
		BasaltColumnsFeature.CANNOT_PLACE_ON = ImmutableList.<Block>builder().addAll(BasaltColumnsFeature.CANNOT_PLACE_ON).add(Blocks.RED_NETHER_BRICKS, Blocks.RED_NETHER_BRICK_SLAB, Blocks.RED_NETHER_BRICK_STAIRS, Blocks.RED_NETHER_BRICK_WALL, EEBlocks.RED_NETHER_BRICK_FENCE.get(), EEBlocks.BLAZE_LANTERN.get(), EEBlocks.ANCIENT_NETHER_ALTAR.get()).build();
		DeltaFeature.CANNOT_REPLACE = ImmutableList.<Block>builder().addAll(DeltaFeature.CANNOT_REPLACE).add(Blocks.RED_NETHER_BRICKS, Blocks.RED_NETHER_BRICK_SLAB, Blocks.RED_NETHER_BRICK_STAIRS, Blocks.RED_NETHER_BRICK_WALL, EEBlocks.RED_NETHER_BRICK_FENCE.get(), EEBlocks.BLAZE_LANTERN.get(), EEBlocks.ANCIENT_NETHER_ALTAR.get()).build();
		final HashMap<ResourceLocation, MultiNoiseBiomeSourceParameterList.Preset> byName = new HashMap<>();
		byName.putAll(MultiNoiseBiomeSourceParameterList.Preset.BY_NAME);
		byName.putAll(Stream.of(EEMultiNoiseBiomeSourceParameterLists.Presets.ANCIENT_NETHER)
				.collect(Collectors.toMap(MultiNoiseBiomeSourceParameterList.Preset::id, preset -> (MultiNoiseBiomeSourceParameterList.Preset)preset)));
		MultiNoiseBiomeSourceParameterList.Preset.BY_NAME = byName;
	}
}
