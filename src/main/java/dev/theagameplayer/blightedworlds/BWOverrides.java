package dev.theagameplayer.blightedworlds;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import dev.theagameplayer.blightedworlds.registries.BWBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.BasaltColumnsFeature;
import net.minecraft.world.level.levelgen.feature.DeltaFeature;
import net.minecraft.world.level.levelgen.structure.StructurePiece;

public final class BWOverrides {
	//You must include the original List so it is compatible with other mods!
	public static void doOverrides() {
		StructurePiece.SHAPE_CHECK_BLOCKS = ImmutableSet.<Block>builder().addAll(StructurePiece.SHAPE_CHECK_BLOCKS).add(Blocks.RED_NETHER_BRICK_WALL, BWBlocks.RED_NETHER_BRICK_FENCE.get()).build();
		BasaltColumnsFeature.CANNOT_PLACE_ON = ImmutableList.<Block>builder().addAll(BasaltColumnsFeature.CANNOT_PLACE_ON).add(Blocks.RED_NETHER_BRICKS, Blocks.RED_NETHER_BRICK_SLAB, Blocks.RED_NETHER_BRICK_STAIRS, Blocks.RED_NETHER_BRICK_WALL, BWBlocks.RED_NETHER_BRICK_FENCE.get(), BWBlocks.BLAZE_LANTERN.get(), BWBlocks.ANCIENT_NETHER_ALTAR.get()).build();
		DeltaFeature.CANNOT_REPLACE = ImmutableList.<Block>builder().addAll(DeltaFeature.CANNOT_REPLACE).add(Blocks.RED_NETHER_BRICKS, Blocks.RED_NETHER_BRICK_SLAB, Blocks.RED_NETHER_BRICK_STAIRS, Blocks.RED_NETHER_BRICK_WALL, BWBlocks.RED_NETHER_BRICK_FENCE.get(), BWBlocks.BLAZE_LANTERN.get(), BWBlocks.ANCIENT_NETHER_ALTAR.get()).build();
	}
}
