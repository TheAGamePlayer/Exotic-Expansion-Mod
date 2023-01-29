package dev.theagameplayer.blightedworlds.world.level.levelgen.structure;

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
	protected SmartStructurePiece(final StructurePieceType typeIn, final int genDepthIn, final BoundingBox boundingBoxIn) {
		super(typeIn, genDepthIn, boundingBoxIn);
	}

	public SmartStructurePiece(final StructurePieceType typeIn, final CompoundTag compoundIn) {
		super(typeIn, compoundIn);
	}

	@Override
	protected void fillColumnDown(final WorldGenLevel levelIn, final BlockState stateIn, final int xIn, final int yIn, final int zIn, final BoundingBox boxIn) {
		final BlockPos.MutableBlockPos mutableBlockPos = this.getWorldPos(xIn, yIn, zIn);
		if (boxIn.isInside(mutableBlockPos)) {
			while (this.isReplaceableByStructures(levelIn.getBlockState(mutableBlockPos)) && mutableBlockPos.getY() > levelIn.getMinBuildHeight() + 1) {
				levelIn.setBlock(mutableBlockPos, stateIn, 2);
				mutableBlockPos.move(Direction.DOWN);
			}
			if (this.isBottomOfColumn(levelIn.getBlockState(mutableBlockPos)))
				levelIn.setBlock(mutableBlockPos, stateIn, 2);
		}
	}

	protected boolean isBottomOfColumn(final BlockState stateIn) {
		return this.isReplaceableByStructures(stateIn) || (stateIn.getBlock() instanceof SlabBlock && stateIn.getValue(SlabBlock.TYPE) == SlabType.BOTTOM) || (stateIn.getBlock() instanceof StairBlock && stateIn.getValue(StairBlock.HALF) == Half.BOTTOM);
	}

	protected List<Class<? extends P>> getUnacceptableChildren() {
		return List.of();
	}

	protected int maxPlacementChunkRange() {
		return 7;
	}
	
	@Nullable
	protected abstract P generateChild(final SP startIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int indexIn);
}
