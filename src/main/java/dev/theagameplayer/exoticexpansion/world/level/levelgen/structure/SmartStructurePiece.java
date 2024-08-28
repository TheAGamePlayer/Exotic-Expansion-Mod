package dev.theagameplayer.exoticexpansion.world.level.levelgen.structure;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

public abstract class SmartStructurePiece<SP extends P, P extends SmartStructurePiece<?, ?>> extends StructurePiece {
	protected SmartStructurePiece(final StructurePieceType pType, final int pGenDepth, final BoundingBox pBoundingBox) {
		super(pType, pGenDepth, pBoundingBox);
	}

	public SmartStructurePiece(final StructurePieceType pType, final CompoundTag pTag) {
		super(pType, pTag);
	}

	@Override
	protected void fillColumnDown(final WorldGenLevel pLevel, final BlockState pState, final int pX, final int pY, final int pZ, final BoundingBox pBox) {
		final BlockPos.MutableBlockPos mutableBlockPos = this.getWorldPos(pX, pY, pZ);
		if (pBox.isInside(mutableBlockPos)) {
			while (this.isReplaceableByStructures(pLevel.getBlockState(mutableBlockPos)) && mutableBlockPos.getY() > pLevel.getMinBuildHeight() + 1) {
				pLevel.setBlock(mutableBlockPos, pState, 2);
				mutableBlockPos.move(Direction.DOWN);
			}
			if (this.isBottomOfColumn(pLevel.getBlockState(mutableBlockPos)))
				pLevel.setBlock(mutableBlockPos, pState, 2);
		}
	}

	protected boolean isBottomOfColumn(final BlockState pState) {
		return this.isReplaceableByStructures(pState) || (pState.getBlock() instanceof SlabBlock && pState.getValue(SlabBlock.TYPE) == SlabType.BOTTOM) || (pState.getBlock() instanceof StairBlock && pState.getValue(StairBlock.HALF) == Half.BOTTOM);
	}

	protected List<Class<? extends P>> getUnacceptableChildren() {
		return List.of();
	}

	protected int maxPlacementChunkRange() {
		return 7;
	}

	@Nullable
	protected abstract P generateChild(final SP pStart, final StructurePieceAccessor pPieces, final RandomSource pRandom, final int pIndex);
}
