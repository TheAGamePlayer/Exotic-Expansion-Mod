package dev.theagameplayer.exoticexpansion.world.level.levelgen.structure.structures;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import dev.theagameplayer.exoticexpansion.data.loot.packs.EEChestLoot;
import dev.theagameplayer.exoticexpansion.registries.EEBlocks;
import dev.theagameplayer.exoticexpansion.registries.EEStructurePieceTypes;
import dev.theagameplayer.exoticexpansion.world.level.block.AncientNetherAltarBlock;
import dev.theagameplayer.exoticexpansion.world.level.block.BlazeLanternBlock;
import dev.theagameplayer.exoticexpansion.world.level.levelgen.structure.SmartStructurePiece;
import dev.theagameplayer.exoticexpansion.world.level.levelgen.structure.TeleportDestinationPiece;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public final class InfernalNetherFortressPieces {
	private static final PieceWeight[] PRIMARY_COMPONENTS = new PieceWeight[] {
			new InfernalNetherFortressPieces.PieceWeight(BridgeStraight1.class, 15, 0, true),
			new InfernalNetherFortressPieces.PieceWeight(BridgeStraight2.class, 5, 0),
			new InfernalNetherFortressPieces.PieceWeight(BridgeStraight3.class, 10, 0, true),
			new InfernalNetherFortressPieces.PieceWeight(BridgeCrossing1.class, 10, 8),
			new InfernalNetherFortressPieces.PieceWeight(BridgeCrossing2.class, 10, 8),
			new InfernalNetherFortressPieces.PieceWeight(BridgeCrossing3.class, 10, 6),
			new InfernalNetherFortressPieces.PieceWeight(RoomCrossing.class, 10, 8),
			new InfernalNetherFortressPieces.PieceWeight(LavaRoomCrossing.class, 5, 4),
			new InfernalNetherFortressPieces.PieceWeight(CastleEntrance.class, 5, 2),
			new InfernalNetherFortressPieces.PieceWeight(StairsRoom.class, 10, 6),
			new InfernalNetherFortressPieces.PieceWeight(MonsterThrone.class, 5, 4)
	};
	private static final PieceWeight[] SECONDARY_COMPONENTS = new PieceWeight[] {
			new InfernalNetherFortressPieces.PieceWeight(CastleSmallCorridorLeftTurnPiece.class, 5, 20),
			new InfernalNetherFortressPieces.PieceWeight(CastleSmallCorridorRightTurnPiece.class, 5, 20),
			new InfernalNetherFortressPieces.PieceWeight(CastleCorridorStairsPiece.class, 10, 6),
			new InfernalNetherFortressPieces.PieceWeight(CastleCorridorTBalconyPiece.class, 7, 4),
			new InfernalNetherFortressPieces.PieceWeight(CastleSmallCorridorPiece.class, 25, 0, true),
			new InfernalNetherFortressPieces.PieceWeight(CastleStalkRoom.class, 5, 3)
	};

	private static InfernalNetherBridgePiece findAndCreateBridgePieceFactory(final PieceWeight pWeight, final StructurePieceAccessor pPieces, final RandomSource pRandom, final int pX, final int pY, final int pZ, final Direction pDirection, final int pGenDepth, final EndType pEndType) {
		final Class<? extends InfernalNetherBridgePiece> oClass = pWeight.weightClass;
		InfernalNetherBridgePiece piece = null;
		if (oClass == BridgeEndFiller1.class) {
			piece = BridgeEndFiller1.createPiece(pPieces, pRandom, pX, pY, pZ, pDirection, pGenDepth, pEndType);
		} else if (oClass == BridgeEndFiller2.class) {
			piece = BridgeEndFiller2.createPiece(pPieces, pRandom, pX, pY, pZ, pDirection, pGenDepth, pEndType);
		} else if (oClass == BridgeStraight1.class) {
			piece = BridgeStraight1.createPiece(pPieces, pRandom, pX, pY, pZ, pDirection, pGenDepth);
		} else if (oClass == BridgeStraight2.class) {
			piece = BridgeStraight2.createPiece(pPieces, pRandom, pX, pY, pZ, pDirection, pGenDepth);
		} else if (oClass == BridgeStraight3.class) {
			piece = BridgeStraight3.createPiece(pPieces, pRandom, pX, pY, pZ, pDirection, pGenDepth);
		} else if (oClass == BridgeCrossing1.class) {
			piece = BridgeCrossing1.createPiece(pPieces, pX, pY, pZ, pDirection, pGenDepth);
		} else if (oClass == BridgeCrossing2.class) {
			piece = BridgeCrossing2.createPiece(pPieces, pX, pY, pZ, pDirection, pGenDepth);
		} else if (oClass == BridgeCrossing3.class) {
			piece = BridgeCrossing3.createPiece(pPieces, pX, pY, pZ, pDirection, pGenDepth);
		} else if (oClass == RoomCrossing.class) {
			piece = RoomCrossing.createPiece(pPieces, pX, pY, pZ, pDirection, pGenDepth);
		} else if (oClass == LavaRoomCrossing.class) {
			piece = LavaRoomCrossing.createPiece(pPieces, pX, pY, pZ, pDirection, pGenDepth);
		} else if (oClass == CastleEntrance.class) {
			piece = CastleEntrance.createPiece(pPieces, pRandom, pX, pY, pZ, pDirection, pGenDepth);
		} else if (oClass == StairsRoom.class) {
			piece = StairsRoom.createPiece(pPieces, pX, pY, pZ, pDirection, pGenDepth);
		} else if (oClass == MonsterThrone.class) {
			piece = MonsterThrone.createPiece(pPieces, pX, pY, pZ, pDirection, pGenDepth);
		} else if (oClass == GrandMonsterThrone.class) {
			piece = GrandMonsterThrone.createPiece(pPieces, pX, pY, pZ, pDirection, pGenDepth);
		} else if (oClass == CastleSmallCorridorLeftTurnPiece.class) {
			piece = CastleSmallCorridorLeftTurnPiece.createPiece(pPieces, pRandom, pX, pY, pZ, pDirection, pGenDepth);
		} else if (oClass == CastleSmallCorridorRightTurnPiece.class) {
			piece = CastleSmallCorridorRightTurnPiece.createPiece(pPieces, pRandom, pX, pY, pZ, pDirection, pGenDepth);
		} else if (oClass == CastleCorridorStairsPiece.class) {
			piece = CastleCorridorStairsPiece.createPiece(pPieces, pX, pY, pZ, pDirection, pGenDepth);
		} else if (oClass == CastleCorridorTBalconyPiece.class) {
			piece = CastleCorridorTBalconyPiece.createPiece(pPieces, pX, pY, pZ, pDirection, pGenDepth);
		} else if (oClass == CastleSmallCorridorPiece.class) {
			piece = CastleSmallCorridorPiece.createPiece(pPieces, pX, pY, pZ, pDirection, pGenDepth);
		} else if (oClass == CastleStalkRoom.class) {
			piece = CastleStalkRoom.createPiece(pPieces, pX, pY, pZ, pDirection, pGenDepth);
		}
		return piece;
	}

	public static class BridgeEndFiller1 extends InfernalNetherBridgePiece {
		private final int selfSeed;

		public BridgeEndFiller1(final int pGenDepth, final RandomSource pRandom, final BoundingBox pBoundingBox, final Direction pDirection) {
			super(EEStructurePieceTypes.INF_BRIDGE_END_FILLER1.get(), pGenDepth, pBoundingBox);
			this.setOrientation(pDirection);
			this.selfSeed = pRandom.nextInt();
		}

		public BridgeEndFiller1(final CompoundTag pCompoundTag) {
			super(EEStructurePieceTypes.INF_BRIDGE_END_FILLER1.get(), pCompoundTag);
			this.selfSeed = pCompoundTag.getInt("Seed");
		}

		public static final InfernalNetherBridgePiece createPiece(final StructurePieceAccessor pPieces, final RandomSource pRandom, final int pX, final int pY, final int pZ, final Direction pDirection, final int pGenDepth, final EndType pEndType) {
			final BoundingBox box = BoundingBox.orientBox(pX, pY, pZ, -1, -3, 0, 5, 10, 8, pDirection);
			return isOkBox(box) && pPieces.findCollisionPiece(box) == null && pEndType == EndType.THICK ? new BridgeEndFiller1(pGenDepth, pRandom, box, pDirection) : BridgeEndFiller2.createPiece(pPieces, pRandom, pX, pY, pZ, pDirection, pGenDepth, pEndType);
		}

		@Override
		protected void addAdditionalSaveData(final StructurePieceSerializationContext pContext, final CompoundTag pCompoundTag) {
			super.addAdditionalSaveData(pContext, pCompoundTag);
			pCompoundTag.putInt("Seed", this.selfSeed);
		}

		@Override
		public final void postProcess(final WorldGenLevel pLevel, final StructureManager pManager, final ChunkGenerator pGenerator, final RandomSource pRandom, final BoundingBox pBoundingBox, final ChunkPos pChunkPos, final BlockPos pPos) {
			final RandomSource random = RandomSource.create(this.selfSeed);
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = Blocks.NETHER_BRICK_FENCE.defaultBlockState();
			final BlockState block4 = Blocks.GLOWSTONE.defaultBlockState();
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair3 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			//air
			this.generateAirBox(pLevel, pBoundingBox, 1, 5, 0, 3, 9, 8);
			//left rail
			for (int w5 = 0; w5 <= 0; ++w5) {
				final int l3 = random.nextInt(5) + 1;
				this.generateBox(pLevel, pBoundingBox, w5, 5, 0, w5, 5, l3, block1, block1, false);
				this.generateBox(pLevel, pBoundingBox, w5, 5, l3 + 1, w5, 5, l3 + 1, stair1, stair1, false);
				this.placeBlock(pLevel, block3, w5, 6, 0, pBoundingBox);
				this.placeBlock(pLevel, block4, w5, 7, 0, pBoundingBox);
			}
			//right rail
			for (int w6 = 4; w6 <= 4; ++w6) {
				final int l3 = random.nextInt(5) + 1;
				this.generateBox(pLevel, pBoundingBox, w6, 5, 0, w6, 5, l3, block1, block1, false);
				this.generateBox(pLevel, pBoundingBox, w6, 5, l3 + 1, w6, 5, l3 + 1, stair1, stair1, false);
				this.placeBlock(pLevel, block3, w6, 6, 0, pBoundingBox);
				this.placeBlock(pLevel, block4, w6, 7, 0, pBoundingBox);
			}
			//top-left
			for (int w1 = 0; w1 <= 1; ++w1) {
				final int l = random.nextInt(8);
				this.generateBox(pLevel, pBoundingBox, w1, 4, 0, w1, 4, l, block1, block1, false);
				this.generateBox(pLevel, pBoundingBox, w1, 4, l + 1, w1, 4, l + 1, stair2, stair2, false);
			}
			//top-middle
			for (int w2 = 2; w2 <= 2; ++w2) {
				final int l = random.nextInt(8);
				this.generateBox(pLevel, pBoundingBox, w2, 4, 0, w2, 4, l, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, w2, 4, l + 1, w2, 4, l + 1, stair3, stair3, false);
			}
			//top-right
			for (int w3 = 3; w3 <= 4; ++w3) {
				final int l = random.nextInt(8);
				this.generateBox(pLevel, pBoundingBox, w3, 4, 0, w3, 4, l, block1, block1, false);
				this.generateBox(pLevel, pBoundingBox, w3, 4, l + 1, w3, 4, l + 1, stair2, stair2, false);
			}
			//bottom
			for (int w4 = 0; w4 <= 4; ++w4) {
				for (int h1 = 2; h1 <= 3; ++h1) {
					final int l2 = random.nextInt(2 + ((h1 - 1) * 2));
					this.generateBox(pLevel, pBoundingBox, w4, h1, 0, w4, h1, l2, block1, block1, false);
					this.generateBox(pLevel, pBoundingBox, w4, h1, l2 + 1, w4, h1, l2 + 1, stair2, stair2, false);
				}
			}
		}
	}

	public static class BridgeEndFiller2 extends InfernalNetherBridgePiece {
		private final boolean isThickEndPiece;
		private final int selfSeed;

		public BridgeEndFiller2(final int pGenDepth, final RandomSource pRandom, final BoundingBox pBoundingBox, final Direction pDirection, final EndType pEndType) {
			super(EEStructurePieceTypes.INF_BRIDGE_END_FILLER2.get(), pGenDepth, pBoundingBox);
			this.setOrientation(pDirection);
			this.selfSeed = pRandom.nextInt();
			this.isThickEndPiece = pEndType == EndType.THICK;
		}

		public BridgeEndFiller2(final CompoundTag pCompoundTag) {
			super(EEStructurePieceTypes.INF_BRIDGE_END_FILLER2.get(), pCompoundTag);
			this.selfSeed = pCompoundTag.getInt("Seed");
			this.isThickEndPiece = pCompoundTag.getBoolean("IsThickEndPiece");
		}

		public static final BridgeEndFiller2 createPiece(final StructurePieceAccessor pPieces, final RandomSource pRandom, final int pX, final int pY, final int pZ, final Direction pDirection, final int pGenDepth, final EndType pEndType) {
			final BoundingBox box = BoundingBox.orientBox(pX, pY, pZ, -1, -3, 0, 5, 10, 1, pDirection);
			return isOkBox(box) && pPieces.findCollisionPiece(box) == null ? new BridgeEndFiller2(pGenDepth, pRandom, box, pDirection, pEndType) : null;
		}

		@Override
		protected void addAdditionalSaveData(final StructurePieceSerializationContext pContext, final CompoundTag pCompoundTag) {
			super.addAdditionalSaveData(pContext, pCompoundTag);
			pCompoundTag.putInt("Seed", this.selfSeed);
			pCompoundTag.putBoolean("IsThickEndPiece", this.isThickEndPiece);
		}

		@Override
		public final void postProcess(final WorldGenLevel pLevel, final StructureManager pManager, final ChunkGenerator pGenerator, final RandomSource pRandom, final BoundingBox pBoundingBox, final ChunkPos pChunkPos, final BlockPos pPos) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair2 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			//air
			this.generateAirBox(pLevel, pBoundingBox, 1, 5, 0, 3, 9, 0);
			//other
			this.placeBlock(pLevel, stair1, 0, 5, 0, pBoundingBox);
			this.placeBlock(pLevel, stair1, 4, 5, 0, pBoundingBox);
			this.placeBlock(pLevel, block1, 0, 4, 0, pBoundingBox);
			this.placeBlock(pLevel, stair1, 1, 4, 0, pBoundingBox);
			this.placeBlock(pLevel, stair2, 2, 4, 0, pBoundingBox);
			this.placeBlock(pLevel, stair1, 3, 4, 0, pBoundingBox);
			this.placeBlock(pLevel, block1, 4, 4, 0, pBoundingBox);
			if (this.isThickEndPiece)
				this.generateBox(pLevel, pBoundingBox, 0, 3, 0, 4, 3, 0, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, this.isThickEndPiece ? 2 : 3, 0, 4, this.isThickEndPiece ? 2 : 3, 0, stair3, stair3, false);
		}
	}

	public static class BridgeStraight1 extends InfernalNetherBridgePiece {
		public BridgeStraight1(final int pGenDepth, final RandomSource pRandom, final BoundingBox pBoundingBox, final Direction pDirection) {
			super(EEStructurePieceTypes.INF_BRIDGE_STRAIGHT1.get(), pGenDepth, pBoundingBox);
			this.setOrientation(pDirection);
		}

		public BridgeStraight1(final CompoundTag pCompoundTag) {
			super(EEStructurePieceTypes.INF_BRIDGE_STRAIGHT1.get(), pCompoundTag);
		}

		@Override
		public final void addChildren(final StructurePiece pPiece, final StructurePieceAccessor pPieces, final RandomSource pRandom) {
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 0);
			super.addChildren(pPiece, pPieces, pRandom);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource pRandom) {
			return List.of(new ChildInfo(Direction.NORTH, 1, 4, false, EndType.THICK));
		}

		public static final BridgeStraight1 createPiece(final StructurePieceAccessor pPieces, final RandomSource pRandom, final int pX, final int pY, final int pZ, final Direction pDirection, final int pGenDepth) {
			final BoundingBox box = BoundingBox.orientBox(pX, pY, pZ, -1, -4, 0, 5, 11, 19, pDirection);
			return isOkBox(box) && pPieces.findCollisionPiece(box) == null ? new BridgeStraight1(pGenDepth, pRandom, box, pDirection) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel pLevel, final StructureManager pManager, final ChunkGenerator pGenerator, final RandomSource pRandom, final BoundingBox pBoundingBox, final ChunkPos pChunkPos, final BlockPos pPos) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState();
			final BlockState block4 = Blocks.RED_NETHER_BRICK_WALL.defaultBlockState();
			final BlockState block5 = Blocks.LANTERN.defaultBlockState().setValue(LanternBlock.HANGING, Boolean.valueOf(true));
			final BlockState block6 = Blocks.CHAIN.defaultBlockState();
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState slab1 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP);
			//air
			this.generateAirBox(pLevel, pBoundingBox, 1, 6, 0, 3, 10, 18);
			//top
			this.generateBox(pLevel, pBoundingBox, 0, 5, 0, 1, 5, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 2, 5, 0, 2, 5, 18, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 3, 5, 0, 4, 5, 18, block1, block1, false);
			//rails
			this.generateBox(pLevel, pBoundingBox, 0, 6, 0, 0, 6, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 7, 3, 0, 7, 3, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 0, 7, 4, 0, 7, 4, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 0, 7, 8, 0, 7, 8, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 0, 7, 9, 0, 7, 9, slab1, slab1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 7, 10, 0, 7, 10, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 0, 7, 14, 0, 7, 14, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 0, 7, 15, 0, 7, 15, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 4, 6, 0, 4, 6, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 4, 7, 3, 4, 7, 3, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 4, 7, 4, 4, 7, 4, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 4, 7, 8, 4, 7, 8, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 4, 7, 9, 4, 7, 9, slab1, slab1, false);
			this.generateBox(pLevel, pBoundingBox, 4, 7, 10, 4, 7, 10, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 4, 7, 14, 4, 7, 14, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 4, 7, 15, 4, 7, 15, stair4, stair4, false);
			//bottom
			this.generateBox(pLevel, pBoundingBox, 0, 4, 0, 4, 4, 18, block1, block1, false);
			//pillars
			this.generateBox(pLevel, pBoundingBox, 0, 3, 0, 4, 3, 0, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 4, 4, 3, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 18, 4, 3, 18, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 14, 4, 3, 14, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 3, 4, 3, 3, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 5, 4, 3, 5, stair2, stair2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 15, 4, 3, 15, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 13, 4, 3, 13, stair1, stair1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 1, 4, 2, 1, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 2, 4, 2, 2, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 0, 1, 1, 4, 1, 2, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 0, 4, 2, 0, stair1, stair1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 18, 4, 2, 18, stair2, stair2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 16, 4, 2, 16, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 17, 4, 2, 17, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 0, 1, 16, 4, 1, 17, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 3, 4, 2, 3, stair2, stair2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 15, 4, 2, 15, stair1, stair1, false);
			for (int w1 = 0; w1 <= 4; ++w1) {
				for (int l1 = 1; l1 <= 2; ++l1) {
					this.fillColumnDown(pLevel, block1, w1, 0, l1, pBoundingBox);
					this.fillColumnDown(pLevel, block1, w1, 0, 18 - l1, pBoundingBox);
				}
			}
			//decoration
			this.placeBlock(pLevel, block4, 0, 3, 9, pBoundingBox);
			this.placeBlock(pLevel, block3, 0, 2, 9, pBoundingBox);
			this.placeBlock(pLevel, block6, 0, 1, 9, pBoundingBox);
			this.placeBlock(pLevel, block5, 0, 0, 9, pBoundingBox);
			this.placeBlock(pLevel, block4, 4, 3, 9, pBoundingBox);
			this.placeBlock(pLevel, block3, 4, 2, 9, pBoundingBox);
			this.placeBlock(pLevel, block6, 4, 1, 9, pBoundingBox);
			this.placeBlock(pLevel, block5, 4, 0, 9, pBoundingBox);
			this.placeBlock(pLevel, block3, 0, 3, 7, pBoundingBox);
			this.placeBlock(pLevel, block6, 0, 2, 7, pBoundingBox);
			this.placeBlock(pLevel, block5, 0, 1, 7, pBoundingBox);
			this.placeBlock(pLevel, block3, 4, 3, 7, pBoundingBox);
			this.placeBlock(pLevel, block6, 4, 2, 7, pBoundingBox);
			this.placeBlock(pLevel, block5, 4, 1, 7, pBoundingBox);
			this.placeBlock(pLevel, block3, 0, 3, 11, pBoundingBox);
			this.placeBlock(pLevel, block6, 0, 2, 11, pBoundingBox);
			this.placeBlock(pLevel, block5, 0, 1, 11, pBoundingBox);
			this.placeBlock(pLevel, block3, 4, 3, 11, pBoundingBox);
			this.placeBlock(pLevel, block6, 4, 2, 11, pBoundingBox);
			this.placeBlock(pLevel, block5, 4, 1, 11, pBoundingBox);
		}
	}

	public static class BridgeStraight2 extends InfernalNetherBridgePiece {
		private final int selfSeed;

		public BridgeStraight2(final int pGenDepth, final RandomSource pRandom, final BoundingBox pBoundingBox, final Direction pDirection) {
			super(EEStructurePieceTypes.INF_BRIDGE_STRAIGHT2.get(), pGenDepth, pBoundingBox);
			this.setOrientation(pDirection);
			this.selfSeed = pRandom.nextInt();
		}

		public BridgeStraight2(final CompoundTag pCompoundTag) {
			super(EEStructurePieceTypes.INF_BRIDGE_STRAIGHT2.get(), pCompoundTag);
			this.selfSeed = pCompoundTag.getInt("Seed");
		}

		@Override
		public final void addChildren(final StructurePiece pPiece, final StructurePieceAccessor pPieces, final RandomSource pRandom) {
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 0);
			super.addChildren(pPiece, pPieces, pRandom);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource pRandom) {
			return List.of(new ChildInfo(Direction.NORTH, 1, 3, false, EndType.NONE));
		}

		@Override
		protected int maxPlacementChunkRange() {
			return 6;
		}

		public static final BridgeStraight2 createPiece(final StructurePieceAccessor pPieces, final RandomSource pRandom, final int pX, final int pY, final int pZ, final Direction pDirection, final int pGenDepth) {
			final BoundingBox box = BoundingBox.orientBox(pX, pY, pZ, -1, -3, 0, 5, 10, 19, pDirection);
			return isOkBox(box) && pPieces.findCollisionPiece(box) == null ? new BridgeStraight2(pGenDepth, pRandom, box, pDirection) : null;
		}

		@Override
		protected void addAdditionalSaveData(final StructurePieceSerializationContext pContext, final CompoundTag pCompoundTag) {
			super.addAdditionalSaveData(pContext, pCompoundTag);
			pCompoundTag.putInt("Seed", this.selfSeed);
		}

		@Override
		public final void postProcess(final WorldGenLevel pLevel, final StructureManager pManager, final ChunkGenerator pGenerator, final RandomSource pRandom, final BoundingBox pBoundingBox, final ChunkPos pChunkPos, final BlockPos pPos) {
			final RandomSource random = RandomSource.create(this.selfSeed);
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = Blocks.NETHER_BRICK_FENCE.defaultBlockState();
			final BlockState block4 = Blocks.GLOWSTONE.defaultBlockState();
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair3 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair5 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair6 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			//air
			this.generateAirBox(pLevel, pBoundingBox, 1, 5, 0, 3, 9, 18);
			//left rail
			for (int w5 = 0; w5 <= 0; ++w5) {
				final int l3 = random.nextInt(5) + 1;
				this.generateBox(pLevel, pBoundingBox, w5, 5, 0, w5, 5, l3, block1, block1, false);
				this.generateBox(pLevel, pBoundingBox, w5, 5, l3 + 1, w5, 5, l3 + 1, stair1, stair1, false);
				this.placeBlock(pLevel, block3, w5, 6, 0, pBoundingBox);
				this.placeBlock(pLevel, block4, w5, 7, 0, pBoundingBox);
				final int l6 = random.nextInt(5) + 1;
				this.generateBox(pLevel, pBoundingBox, w5, 5, 18 - l6, w5, 5, 18, block1, block1, false);
				this.generateBox(pLevel, pBoundingBox, w5, 5, (18 - l6) - 1, w5, 5, (18 - l6) - 1, stair4, stair4, false);
				this.placeBlock(pLevel, block3, w5, 6, 18, pBoundingBox);
				this.placeBlock(pLevel, block4, w5, 7, 18, pBoundingBox);
			}
			//right rail
			for (int w6 = 4; w6 <= 4; ++w6) {
				final int l3 = random.nextInt(5) + 1;
				this.generateBox(pLevel, pBoundingBox, w6, 5, 0, w6, 5, l3, block1, block1, false);
				this.generateBox(pLevel, pBoundingBox, w6, 5, l3 + 1, w6, 5, l3 + 1, stair1, stair1, false);
				this.placeBlock(pLevel, block3, w6, 6, 0, pBoundingBox);
				this.placeBlock(pLevel, block4, w6, 7, 0, pBoundingBox);
				final int l6 = random.nextInt(5) + 1;
				this.generateBox(pLevel, pBoundingBox, w6, 5, 18 - l6, w6, 5, 18, block1, block1, false);
				this.generateBox(pLevel, pBoundingBox, w6, 5, (18 - l6) - 1, w6, 5, (18 - l6) - 1, stair4, stair4, false);
				this.placeBlock(pLevel, block3, w6, 6, 18, pBoundingBox);
				this.placeBlock(pLevel, block4, w6, 7, 18, pBoundingBox);
			}
			//top-left
			for (int w1 = 0; w1 <= 1; ++w1) {
				final int l = random.nextInt(8);
				this.generateBox(pLevel, pBoundingBox, w1, 4, 0, w1, 4, l, block1, block1, false);
				this.generateBox(pLevel, pBoundingBox, w1, 4, l + 1, w1, 4, l + 1, stair2, stair2, false);
				final int l4 = random.nextInt(8);
				this.generateBox(pLevel, pBoundingBox, w1, 4, 18 - l4, w1, 4, 18, block1, block1, false);
				this.generateBox(pLevel, pBoundingBox, w1, 4, (18 - l4) - 1, w1, 4, (18 - l4) - 1, stair5, stair5, false);
			}
			//top-middle
			for (int w2 = 2; w2 <= 2; ++w2) {
				final int l = random.nextInt(8);
				this.generateBox(pLevel, pBoundingBox, w2, 4, 0, w2, 4, l, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, w2, 4, l + 1, w2, 4, l + 1, stair3, stair3, false);
				final int l4 = random.nextInt(8);
				this.generateBox(pLevel, pBoundingBox, w2, 4, 18 - l4, w2, 4, 18, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, w2, 4, (18 - l4) - 1, w2, 4, (18 - l4) - 1, stair6, stair6, false);
			}
			//top-right
			for (int w3 = 3; w3 <= 4; ++w3) {
				final int l = random.nextInt(8);
				this.generateBox(pLevel, pBoundingBox, w3, 4, 0, w3, 4, l, block1, block1, false);
				this.generateBox(pLevel, pBoundingBox, w3, 4, l + 1, w3, 4, l + 1, stair2, stair2, false);
				final int l4 = random.nextInt(8);
				this.generateBox(pLevel, pBoundingBox, w3, 4, 18 - l4, w3, 4, 18, block1, block1, false);
				this.generateBox(pLevel, pBoundingBox, w3, 4, (18 - l4) - 1, w3, 4, (18 - l4) - 1, stair5, stair5, false);
			}
			//bottom
			for (int w4 = 0; w4 <= 4; ++w4) {
				for (int h1 = 2; h1 <= 3; ++h1) {
					final int l2 = random.nextInt(2 + ((h1 - 1) * 2));
					this.generateBox(pLevel, pBoundingBox, w4, h1, 0, w4, h1, l2, block1, block1, false);
					this.generateBox(pLevel, pBoundingBox, w4, h1, l2 + 1, w4, h1, l2 + 1, stair2, stair2, false);
					final int l5 = random.nextInt(2 + ((h1 - 1) * 2));
					this.generateBox(pLevel, pBoundingBox, w4, h1, 18 - l5, w4, h1, 18, block1, block1, false);
					this.generateBox(pLevel, pBoundingBox, w4, h1, (18 - l5) - 1, w4, h1, (18 - l5) - 1, stair5, stair5, false);
				}
			}
		}
	}

	public static class BridgeStraight3 extends InfernalNetherBridgePiece {
		public BridgeStraight3(final int pGenDepth, final RandomSource pRandom, final BoundingBox pBoundingBox, final Direction pDirection) {
			super(EEStructurePieceTypes.INF_BRIDGE_STRAIGHT3.get(), pGenDepth, pBoundingBox);
			this.setOrientation(pDirection);
		}

		public BridgeStraight3(final CompoundTag pCompoundTag) {
			super(EEStructurePieceTypes.INF_BRIDGE_STRAIGHT3.get(), pCompoundTag);
		}

		@Override
		public final void addChildren(final StructurePiece pPiece, final StructurePieceAccessor pPieces, final RandomSource pRandom) {
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 0);
			super.addChildren(pPiece, pPieces, pRandom);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource pRandom) {
			return List.of(new ChildInfo(Direction.NORTH, 1, 4, false, EndType.THICK));
		}

		public static final BridgeStraight3 createPiece(final StructurePieceAccessor pPieces, final RandomSource pRandom, final int pX, final int pY, final int pZ, final Direction pDirection, final int pGenDepth) {
			final BoundingBox box = BoundingBox.orientBox(pX, pY, pZ, -1, -4, 0, 5, 11, 19, pDirection);
			return isOkBox(box) && pPieces.findCollisionPiece(box) == null ? new BridgeStraight3(pGenDepth, pRandom, box, pDirection) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel pLevel, final StructureManager pManager, final ChunkGenerator pGenerator, final RandomSource pRandom, final BoundingBox pBoundingBox, final ChunkPos pChunkPos, final BlockPos pPos) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState();
			final BlockState block4 = Blocks.RED_NETHER_BRICK_WALL.defaultBlockState();
			final BlockState block5 = Blocks.LANTERN.defaultBlockState().setValue(LanternBlock.HANGING, Boolean.valueOf(true));
			final BlockState block6 = Blocks.CHAIN.defaultBlockState();
			final BlockState block7 = Blocks.NETHER_BRICK_FENCE.defaultBlockState();
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState slab1 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP);
			final BlockState slab2 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM);
			//air
			this.generateAirBox(pLevel, pBoundingBox, 1, 6, 0, 3, 10, 18);
			//top
			this.generateBox(pLevel, pBoundingBox, 0, 5, 0, 1, 5, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 2, 5, 0, 2, 5, 18, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 3, 5, 0, 4, 5, 18, block1, block1, false);
			//rails
			this.generateBox(pLevel, pBoundingBox, 0, 6, 0, 0, 6, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 7, 3, 0, 7, 3, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 0, 7, 4, 0, 7, 4, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 0, 7, 8, 0, 7, 8, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 0, 7, 9, 0, 7, 9, slab1, slab1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 7, 10, 0, 7, 10, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 0, 7, 14, 0, 7, 14, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 0, 7, 15, 0, 7, 15, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 4, 6, 0, 4, 6, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 4, 7, 3, 4, 7, 3, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 4, 7, 4, 4, 7, 4, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 4, 7, 8, 4, 7, 8, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 4, 7, 9, 4, 7, 9, slab1, slab1, false);
			this.generateBox(pLevel, pBoundingBox, 4, 7, 10, 4, 7, 10, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 4, 7, 14, 4, 7, 14, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 4, 7, 15, 4, 7, 15, stair4, stair4, false);
			//top structure
			this.placeBlock(pLevel, block4, 0, 8, 9, pBoundingBox);
			this.placeBlock(pLevel, block4, 4, 8, 9, pBoundingBox);
			this.placeBlock(pLevel, block7, 0, 9, 9, pBoundingBox);
			this.placeBlock(pLevel, block7, 4, 9, 9, pBoundingBox);
			this.placeBlock(pLevel, block4, 0, 10, 9, pBoundingBox);
			this.placeBlock(pLevel, block4, 4, 10, 9, pBoundingBox);
			this.placeBlock(pLevel, slab2, 0, 11, 9, pBoundingBox);
			this.placeBlock(pLevel, slab2, 4, 11, 9, pBoundingBox);
			this.placeBlock(pLevel, block1, 1, 11, 9, pBoundingBox);
			this.placeBlock(pLevel, block2, 2, 11, 9, pBoundingBox);
			this.placeBlock(pLevel, block1, 3, 11, 9, pBoundingBox);
			//bottom
			this.generateBox(pLevel, pBoundingBox, 0, 4, 0, 4, 4, 18, block1, block1, false);
			//pillars
			this.generateBox(pLevel, pBoundingBox, 0, 3, 0, 4, 3, 0, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 4, 4, 3, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 18, 4, 3, 18, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 14, 4, 3, 14, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 3, 4, 3, 3, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 5, 4, 3, 5, stair2, stair2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 15, 4, 3, 15, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 13, 4, 3, 13, stair1, stair1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 1, 4, 2, 1, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 2, 4, 2, 2, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 0, 1, 1, 4, 1, 2, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 0, 4, 2, 0, stair1, stair1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 18, 4, 2, 18, stair2, stair2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 16, 4, 2, 16, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 17, 4, 2, 17, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 0, 1, 16, 4, 1, 17, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 3, 4, 2, 3, stair2, stair2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 15, 4, 2, 15, stair1, stair1, false);
			for (int w1 = 0; w1 <= 4; ++w1) {
				for (int l1 = 1; l1 <= 2; ++l1) {
					this.fillColumnDown(pLevel, block1, w1, 0, l1, pBoundingBox);
					this.fillColumnDown(pLevel, block1, w1, 0, 18 - l1, pBoundingBox);
				}
			}
			//decoration
			this.placeBlock(pLevel, block4, 0, 3, 9, pBoundingBox);
			this.placeBlock(pLevel, block3, 0, 2, 9, pBoundingBox);
			this.placeBlock(pLevel, block6, 0, 1, 9, pBoundingBox);
			this.placeBlock(pLevel, block5, 0, 0, 9, pBoundingBox);
			this.placeBlock(pLevel, block4, 4, 3, 9, pBoundingBox);
			this.placeBlock(pLevel, block3, 4, 2, 9, pBoundingBox);
			this.placeBlock(pLevel, block6, 4, 1, 9, pBoundingBox);
			this.placeBlock(pLevel, block5, 4, 0, 9, pBoundingBox);
			this.placeBlock(pLevel, block3, 0, 3, 7, pBoundingBox);
			this.placeBlock(pLevel, block6, 0, 2, 7, pBoundingBox);
			this.placeBlock(pLevel, block5, 0, 1, 7, pBoundingBox);
			this.placeBlock(pLevel, block3, 4, 3, 7, pBoundingBox);
			this.placeBlock(pLevel, block6, 4, 2, 7, pBoundingBox);
			this.placeBlock(pLevel, block5, 4, 1, 7, pBoundingBox);
			this.placeBlock(pLevel, block3, 0, 3, 11, pBoundingBox);
			this.placeBlock(pLevel, block6, 0, 2, 11, pBoundingBox);
			this.placeBlock(pLevel, block5, 0, 1, 11, pBoundingBox);
			this.placeBlock(pLevel, block3, 4, 3, 11, pBoundingBox);
			this.placeBlock(pLevel, block6, 4, 2, 11, pBoundingBox);
			this.placeBlock(pLevel, block5, 4, 1, 11, pBoundingBox);
		}
	}

	public static class BridgeCrossing1 extends InfernalNetherBridgePiece {
		public BridgeCrossing1(final int pGenDepth, final BoundingBox pBoundingBox, final Direction pDirection) {
			super(EEStructurePieceTypes.INF_BRIDGE_CROSSING1.get(), pGenDepth, pBoundingBox);
			this.setOrientation(pDirection);
		}

		public BridgeCrossing1(final CompoundTag pCompoundTag) {
			super(EEStructurePieceTypes.INF_BRIDGE_CROSSING1.get(), pCompoundTag);
		}

		@Override
		public final void addChildren(final StructurePiece pPiece, final StructurePieceAccessor pPieces, final RandomSource pRandom) {
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 0);
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 1);
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 2);
			super.addChildren(pPiece, pPieces, pRandom);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource pRandom) {
			return List.of(new ChildInfo(Direction.NORTH, 8, 3, false, EndType.THICK), new ChildInfo(Direction.WEST, 3, 8, false, EndType.THICK), new ChildInfo(Direction.EAST, 3, 8, false, EndType.THICK));
		}

		public static final BridgeCrossing1 createPiece(final StructurePieceAccessor pPieces, final int pX, final int pY, final int pZ, final Direction pDirection, final int pGenDepth) {
			final BoundingBox box = BoundingBox.orientBox(pX, pY, pZ, -8, -3, 0, 19, 10, 19, pDirection);
			return isOkBox(box) && pPieces.findCollisionPiece(box) == null ? new BridgeCrossing1(pGenDepth, box, pDirection) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel pLevel, final StructureManager pManager, final ChunkGenerator pGenerator, final RandomSource pRandom, final BoundingBox pBoundingBox, final ChunkPos pChunkPos, final BlockPos pPos) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair5 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair6 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair7 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST);
			final BlockState stair8 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST);
			//air
			this.generateAirBox(pLevel, pBoundingBox, 8, 5, 0, 10, 9, 18);
			this.generateAirBox(pLevel, pBoundingBox, 0, 5, 8, 18, 9, 10);
			//top
			this.generateBox(pLevel, pBoundingBox, 7, 4, 0, 8, 4, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 4, 11, 8, 4, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 9, 4, 0, 9, 4, 8, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 9, 4, 10, 9, 4, 18, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 10, 4, 0, 11, 4, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 10, 4, 11, 11, 4, 18, block1, block1, false);
			//center
			this.placeBlock(pLevel, block1, 9, 4, 9, pBoundingBox);
			this.placeBlock(pLevel, block2, 8, 4, 8, pBoundingBox);
			this.placeBlock(pLevel, block2, 8, 4, 10, pBoundingBox);
			this.placeBlock(pLevel, block2, 10, 4, 8, pBoundingBox);
			this.placeBlock(pLevel, block2, 10, 4, 10, pBoundingBox);
			//top2
			this.generateBox(pLevel, pBoundingBox, 0, 4, 7, 7, 4, 8, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 11, 4, 7, 18, 4, 8, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 4, 9, 8, 4, 9, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 10, 4, 9, 18, 4, 9, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 4, 10, 7, 4, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 11, 4, 10, 18, 4, 11, block1, block1, false);
			//bottom
			this.generateBox(pLevel, pBoundingBox, 7, 3, 0, 11, 3, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 7, 18, 3, 11, block1, block1, false);
			//rails
			this.generateBox(pLevel, pBoundingBox, 7, 5, 0, 7, 5, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 5, 11, 7, 5, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 11, 5, 0, 11, 5, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 11, 5, 11, 11, 5, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 5, 7, 7, 5, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 11, 5, 7, 18, 5, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 5, 11, 7, 5, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 11, 5, 11, 18, 5, 11, block1, block1, false);
			//pillars z-Axis
			this.generateBox(pLevel, pBoundingBox, 7, 2, 0, 11, 2, 0, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 4, 11, 2, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 18, 11, 2, 18, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 14, 11, 2, 14, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 3, 11, 2, 3, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 5, 11, 2, 5, stair2, stair2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 15, 11, 2, 15, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 13, 11, 2, 13, stair1, stair1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 1, 11, 1, 1, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 2, 11, 1, 2, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 7, 0, 1, 11, 0, 2, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 0, 11, 1, 0, stair1, stair1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 18, 11, 1, 18, stair2, stair2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 16, 11, 1, 16, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 17, 11, 1, 17, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 7, 0, 16, 11, 0, 17, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 3, 11, 1, 3, stair2, stair2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 15, 11, 1, 15, stair1, stair1, false);
			for (int w1 = 7; w1 <= 11; ++w1) {
				for (int l1 = 1; l1 <= 2; ++l1) {
					this.fillColumnDown(pLevel, block1, w1, -1, l1, pBoundingBox);
					this.fillColumnDown(pLevel, block1, w1, -1, 18 - l1, pBoundingBox);
				}
			}
			//pillars x-axis
			this.generateBox(pLevel, pBoundingBox, 0, 2, 7, 0, 2, 11, stair8, stair8, false);
			this.generateBox(pLevel, pBoundingBox, 4, 2, 7, 4, 2, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 18, 2, 7, 18, 2, 11, stair7, stair7, false);
			this.generateBox(pLevel, pBoundingBox, 14, 2, 7, 14, 2, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 2, 7, 3, 2, 11, stair7, stair7, false);
			this.generateBox(pLevel, pBoundingBox, 5, 2, 7, 5, 2, 11, stair6, stair6, false);
			this.generateBox(pLevel, pBoundingBox, 15, 2, 7, 15, 2, 11, stair8, stair8, false);
			this.generateBox(pLevel, pBoundingBox, 13, 2, 7, 13, 2, 11, stair5, stair5, false);
			this.generateBox(pLevel, pBoundingBox, 1, 1, 7, 1, 1, 11, stair8, stair8, false);
			this.generateBox(pLevel, pBoundingBox, 2, 1, 7, 2, 1, 11, stair7, stair7, false);
			this.generateBox(pLevel, pBoundingBox, 1, 0, 7, 2, 0, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 1, 7, 0, 1, 11, stair5, stair5, false);
			this.generateBox(pLevel, pBoundingBox, 18, 1, 7, 18, 1, 11, stair6, stair6, false);
			this.generateBox(pLevel, pBoundingBox, 16, 1, 7, 16, 1, 11, stair8, stair8, false);
			this.generateBox(pLevel, pBoundingBox, 17, 1, 7, 17, 1, 11, stair7, stair7, false);
			this.generateBox(pLevel, pBoundingBox, 16, 0, 7, 17, 0, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 1, 7, 3, 1, 11, stair6, stair6, false);
			this.generateBox(pLevel, pBoundingBox, 15, 1, 7, 15, 1, 11, stair5, stair5, false);
			for (int w2 = 1; w2 <= 2; ++w2) {
				for (int l2 = 7; l2 <= 11; ++l2) {
					this.fillColumnDown(pLevel, block1, w2, -1, l2, pBoundingBox);
					this.fillColumnDown(pLevel, block1, 18 - w2, -1, l2, pBoundingBox);
				}
			}
		}
	}

	public static class BridgeCrossing2 extends InfernalNetherBridgePiece {
		public BridgeCrossing2(final int pGenDepth, final BoundingBox pBoundingBox, final Direction pDirection) {
			super(EEStructurePieceTypes.INF_BRIDGE_CROSSING2.get(), pGenDepth, pBoundingBox);
			this.setOrientation(pDirection);
		}

		public BridgeCrossing2(final CompoundTag pCompoundTag) {
			super(EEStructurePieceTypes.INF_BRIDGE_CROSSING2.get(), pCompoundTag);
		}

		@Override
		public final void addChildren(final StructurePiece pPiece, final StructurePieceAccessor pPieces, final RandomSource pRandom) {
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 0);
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 1);
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 2);
			super.addChildren(pPiece, pPieces, pRandom);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource pRandom) {
			return List.of(new ChildInfo(Direction.NORTH, 8, 3, false, EndType.THICK), new ChildInfo(Direction.WEST, 3, 8, false, EndType.THICK), new ChildInfo(Direction.EAST, 3, 8, false, EndType.THICK));
		}

		public static final BridgeCrossing2 createPiece(final StructurePieceAccessor pPieces, final int pX, final int pY, final int pZ, final Direction pDirection, final int pGenDepth) {
			final BoundingBox box = BoundingBox.orientBox(pX, pY, pZ, -8, -3, 0, 19, 10, 19, pDirection);
			return isOkBox(box) && pPieces.findCollisionPiece(box) == null ? new BridgeCrossing2(pGenDepth, box, pDirection) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel pLevel, final StructureManager pManager, final ChunkGenerator pGenerator, final RandomSource pRandom, final BoundingBox pBoundingBox, final ChunkPos pChunkPos, final BlockPos pPos) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = Blocks.RED_NETHER_BRICK_WALL.defaultBlockState();
			final BlockState block4 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState();
			final BlockState block5 = EEBlocks.BLAZE_LANTERN.get().defaultBlockState().setValue(BlazeLanternBlock.HANGING, Boolean.valueOf(true));
			final BlockState block6 = Blocks.NETHER_BRICK_FENCE.defaultBlockState();
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair5 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair6 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair7 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST);
			final BlockState stair8 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST);
			final BlockState stair9 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair10 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair11 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST);
			final BlockState stair12 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST);
			final BlockState slab1 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM);
			final BlockState slab2 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP);
			final BlockState slab3 = Blocks.NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP);
			//air
			this.generateAirBox(pLevel, pBoundingBox, 8, 5, 0, 10, 9, 18);
			this.generateAirBox(pLevel, pBoundingBox, 0, 5, 8, 18, 9, 10);
			//top
			this.generateBox(pLevel, pBoundingBox, 7, 4, 0, 8, 4, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 4, 11, 8, 4, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 9, 4, 0, 9, 4, 8, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 9, 4, 10, 9, 4, 18, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 10, 4, 0, 11, 4, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 10, 4, 11, 11, 4, 18, block1, block1, false);
			//center
			this.placeBlock(pLevel, block1, 9, 4, 9, pBoundingBox);
			this.placeBlock(pLevel, block2, 8, 4, 8, pBoundingBox);
			this.placeBlock(pLevel, block2, 8, 4, 10, pBoundingBox);
			this.placeBlock(pLevel, block2, 10, 4, 8, pBoundingBox);
			this.placeBlock(pLevel, block2, 10, 4, 10, pBoundingBox);
			this.placeBlock(pLevel, block4, 9, 8, 9, pBoundingBox);
			this.placeBlock(pLevel, block5, 9, 7, 9, pBoundingBox);
			this.placeBlock(pLevel, block3, 9, 9, 9, pBoundingBox);
			//top center
			this.placeBlock(pLevel, block1, 9, 10, 9, pBoundingBox);
			this.placeBlock(pLevel, block2, 8, 10, 8, pBoundingBox);
			this.placeBlock(pLevel, block2, 8, 10, 10, pBoundingBox);
			this.placeBlock(pLevel, block2, 10, 10, 8, pBoundingBox);
			this.placeBlock(pLevel, block2, 10, 10, 10, pBoundingBox);
			this.placeBlock(pLevel, block2, 9, 10, 8, pBoundingBox);
			this.placeBlock(pLevel, block2, 8, 10, 9, pBoundingBox);
			this.placeBlock(pLevel, block2, 10, 10, 9, pBoundingBox);
			this.placeBlock(pLevel, block2, 9, 10, 10, pBoundingBox);
			this.placeBlock(pLevel, block1, 7, 9, 7, pBoundingBox);
			this.placeBlock(pLevel, block3, 7, 8, 7, pBoundingBox);
			this.placeBlock(pLevel, block6, 7, 7, 7, pBoundingBox);
			this.placeBlock(pLevel, block3, 7, 6, 7, pBoundingBox);
			this.placeBlock(pLevel, block1, 7, 9, 11, pBoundingBox);
			this.placeBlock(pLevel, block3, 7, 8, 11, pBoundingBox);
			this.placeBlock(pLevel, block6, 7, 7, 11, pBoundingBox);
			this.placeBlock(pLevel, block3, 7, 6, 11, pBoundingBox);
			this.placeBlock(pLevel, block1, 11, 9, 11, pBoundingBox);
			this.placeBlock(pLevel, block3, 11, 8, 11, pBoundingBox);
			this.placeBlock(pLevel, block6, 11, 7, 11, pBoundingBox);
			this.placeBlock(pLevel, block3, 11, 6, 11, pBoundingBox);
			this.placeBlock(pLevel, block1, 11, 9, 7, pBoundingBox);
			this.placeBlock(pLevel, block3, 11, 8, 7, pBoundingBox);
			this.placeBlock(pLevel, block6, 11, 7, 7, pBoundingBox);
			this.placeBlock(pLevel, block3, 11, 6, 7, pBoundingBox);
			//top center stairs & slabs
			this.placeBlock(pLevel, stair9, 9, 10, 7, pBoundingBox);
			this.placeBlock(pLevel, stair3, 8, 10, 7, pBoundingBox);
			this.placeBlock(pLevel, stair3, 10, 10, 7, pBoundingBox);
			this.placeBlock(pLevel, stair10, 9, 10, 11, pBoundingBox);
			this.placeBlock(pLevel, stair4, 8, 10, 11, pBoundingBox);
			this.placeBlock(pLevel, stair4, 10, 10, 11, pBoundingBox);
			this.placeBlock(pLevel, stair11, 7, 10, 9, pBoundingBox);
			this.placeBlock(pLevel, stair7, 7, 10, 8, pBoundingBox);
			this.placeBlock(pLevel, stair7, 7, 10, 10, pBoundingBox);
			this.placeBlock(pLevel, stair12, 11, 10, 9, pBoundingBox);
			this.placeBlock(pLevel, stair8, 11, 10, 8, pBoundingBox);
			this.placeBlock(pLevel, stair8, 11, 10, 10, pBoundingBox);
			this.placeBlock(pLevel, slab1, 7, 10, 7, pBoundingBox);
			this.placeBlock(pLevel, slab1, 7, 10, 11, pBoundingBox);
			this.placeBlock(pLevel, slab1, 11, 10, 11, pBoundingBox);
			this.placeBlock(pLevel, slab1, 11, 10, 7, pBoundingBox);
			this.placeBlock(pLevel, slab2, 8, 9, 7, pBoundingBox);
			this.placeBlock(pLevel, slab3, 9, 9, 7, pBoundingBox);
			this.placeBlock(pLevel, slab2, 10, 9, 7, pBoundingBox);
			this.placeBlock(pLevel, slab2, 8, 9, 11, pBoundingBox);
			this.placeBlock(pLevel, slab3, 9, 9, 11, pBoundingBox);
			this.placeBlock(pLevel, slab2, 10, 9, 11, pBoundingBox);
			this.placeBlock(pLevel, slab2, 7, 9, 8, pBoundingBox);
			this.placeBlock(pLevel, slab3, 7, 9, 9, pBoundingBox);
			this.placeBlock(pLevel, slab2, 7, 9, 10, pBoundingBox);
			this.placeBlock(pLevel, slab2, 11, 9, 8, pBoundingBox);
			this.placeBlock(pLevel, slab3, 11, 9, 9, pBoundingBox);
			this.placeBlock(pLevel, slab2, 11, 9, 10, pBoundingBox);
			//top2
			this.generateBox(pLevel, pBoundingBox, 0, 4, 7, 7, 4, 8, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 11, 4, 7, 18, 4, 8, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 4, 9, 8, 4, 9, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 10, 4, 9, 18, 4, 9, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 4, 10, 7, 4, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 11, 4, 10, 18, 4, 11, block1, block1, false);
			//bottom
			this.generateBox(pLevel, pBoundingBox, 7, 3, 0, 11, 3, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 7, 18, 3, 11, block1, block1, false);
			//rails
			this.generateBox(pLevel, pBoundingBox, 7, 5, 0, 7, 5, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 5, 11, 7, 5, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 11, 5, 0, 11, 5, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 11, 5, 11, 11, 5, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 5, 7, 7, 5, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 11, 5, 7, 18, 5, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 5, 11, 7, 5, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 11, 5, 11, 18, 5, 11, block1, block1, false);
			//pillars z-Axis
			this.generateBox(pLevel, pBoundingBox, 7, 2, 0, 11, 2, 0, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 4, 11, 2, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 18, 11, 2, 18, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 14, 11, 2, 14, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 3, 11, 2, 3, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 5, 11, 2, 5, stair2, stair2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 15, 11, 2, 15, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 13, 11, 2, 13, stair1, stair1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 1, 11, 1, 1, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 2, 11, 1, 2, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 7, 0, 1, 11, 0, 2, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 0, 11, 1, 0, stair1, stair1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 18, 11, 1, 18, stair2, stair2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 16, 11, 1, 16, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 17, 11, 1, 17, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 7, 0, 16, 11, 0, 17, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 3, 11, 1, 3, stair2, stair2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 15, 11, 1, 15, stair1, stair1, false);
			for (int w1 = 7; w1 <= 11; ++w1) {
				for (int l1 = 1; l1 <= 2; ++l1) {
					this.fillColumnDown(pLevel, block1, w1, -1, l1, pBoundingBox);
					this.fillColumnDown(pLevel, block1, w1, -1, 18 - l1, pBoundingBox);
				}
			}
			//pillars x-axis
			this.generateBox(pLevel, pBoundingBox, 0, 2, 7, 0, 2, 11, stair8, stair8, false);
			this.generateBox(pLevel, pBoundingBox, 4, 2, 7, 4, 2, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 18, 2, 7, 18, 2, 11, stair7, stair7, false);
			this.generateBox(pLevel, pBoundingBox, 14, 2, 7, 14, 2, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 2, 7, 3, 2, 11, stair7, stair7, false);
			this.generateBox(pLevel, pBoundingBox, 5, 2, 7, 5, 2, 11, stair6, stair6, false);
			this.generateBox(pLevel, pBoundingBox, 15, 2, 7, 15, 2, 11, stair8, stair8, false);
			this.generateBox(pLevel, pBoundingBox, 13, 2, 7, 13, 2, 11, stair5, stair5, false);
			this.generateBox(pLevel, pBoundingBox, 1, 1, 7, 1, 1, 11, stair8, stair8, false);
			this.generateBox(pLevel, pBoundingBox, 2, 1, 7, 2, 1, 11, stair7, stair7, false);
			this.generateBox(pLevel, pBoundingBox, 1, 0, 7, 2, 0, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 1, 7, 0, 1, 11, stair5, stair5, false);
			this.generateBox(pLevel, pBoundingBox, 18, 1, 7, 18, 1, 11, stair6, stair6, false);
			this.generateBox(pLevel, pBoundingBox, 16, 1, 7, 16, 1, 11, stair8, stair8, false);
			this.generateBox(pLevel, pBoundingBox, 17, 1, 7, 17, 1, 11, stair7, stair7, false);
			this.generateBox(pLevel, pBoundingBox, 16, 0, 7, 17, 0, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 1, 7, 3, 1, 11, stair6, stair6, false);
			this.generateBox(pLevel, pBoundingBox, 15, 1, 7, 15, 1, 11, stair5, stair5, false);
			for (int w2 = 1; w2 <= 2; ++w2) {
				for (int l2 = 7; l2 <= 11; ++l2) {
					this.fillColumnDown(pLevel, block1, w2, -1, l2, pBoundingBox);
					this.fillColumnDown(pLevel, block1, 18 - w2, -1, l2, pBoundingBox);
				}
			}
		}
	}

	public static class BridgeCrossing3 extends InfernalNetherBridgePiece {
		public BridgeCrossing3(final int pGenDepth, final BoundingBox pBoundingBox, final Direction pDirection) {
			super(EEStructurePieceTypes.INF_BRIDGE_CROSSING3.get(), pGenDepth, pBoundingBox);
			this.setOrientation(pDirection);
		}

		public BridgeCrossing3(final CompoundTag pCompoundTag) {
			super(EEStructurePieceTypes.INF_BRIDGE_CROSSING3.get(), pCompoundTag);
		}

		@Override
		public final void addChildren(final StructurePiece pPiece, final StructurePieceAccessor pPieces, final RandomSource pRandom) {
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 0);
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 1);
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 2);
			super.addChildren(pPiece, pPieces, pRandom);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource pRandom) {
			return List.of(new ChildInfo(Direction.NORTH, 8, 3, false, EndType.THICK), new ChildInfo(Direction.WEST, 3, 8, false, EndType.THICK), new ChildInfo(Direction.EAST, 3, 8, false, EndType.THICK));
		}

		public static final BridgeCrossing3 createPiece(final StructurePieceAccessor pPieces, final int pX, final int pY, final int pZ, final Direction pDirection, final int pGenDepth) {
			final BoundingBox box = BoundingBox.orientBox(pX, pY, pZ, -8, -3, 0, 19, 10, 19, pDirection);
			return isOkBox(box) && pPieces.findCollisionPiece(box) == null ? new BridgeCrossing3(pGenDepth, box, pDirection) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel pLevel, final StructureManager pManager, final ChunkGenerator pGenerator, final RandomSource pRandom, final BoundingBox pBoundingBox, final ChunkPos pChunkPos, final BlockPos pPos) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = Blocks.RED_NETHER_BRICK_WALL.defaultBlockState();
			final BlockState block4 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState();
			final BlockState block5 = EEBlocks.BLAZE_LANTERN.get().defaultBlockState();
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair5 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair6 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair7 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST);
			final BlockState stair8 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST);
			//air
			this.generateAirBox(pLevel, pBoundingBox, 8, 5, 0, 10, 9, 18);
			this.generateAirBox(pLevel, pBoundingBox, 0, 5, 8, 18, 9, 10);
			this.generateAirBox(pLevel, pBoundingBox, 6, 5, 5, 12, 9, 13);
			this.generateAirBox(pLevel, pBoundingBox, 5, 5, 6, 13, 9, 12);
			//top
			this.generateBox(pLevel, pBoundingBox, 7, 4, 0, 8, 4, 5, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 4, 6, 8, 4, 6, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 4, 13, 8, 4, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 4, 12, 8, 4, 12, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 9, 4, 0, 9, 4, 8, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 9, 4, 10, 9, 4, 18, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 10, 4, 0, 11, 4, 5, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 10, 4, 6, 11, 4, 6, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 10, 4, 13, 11, 4, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 10, 4, 12, 11, 4, 12, block2, block2, false);
			//center
			this.placeBlock(pLevel, block1, 9, 4, 9, pBoundingBox);
			this.placeBlock(pLevel, block3, 9, 5, 9, pBoundingBox);
			this.placeBlock(pLevel, block4, 9, 6, 9, pBoundingBox);
			this.placeBlock(pLevel, block5, 9, 7, 9, pBoundingBox);
			this.placeBlock(pLevel, block2, 8, 4, 8, pBoundingBox);
			this.placeBlock(pLevel, block2, 8, 4, 10, pBoundingBox);
			this.placeBlock(pLevel, block2, 10, 4, 8, pBoundingBox);
			this.placeBlock(pLevel, block2, 10, 4, 10, pBoundingBox);
			this.placeBlock(pLevel, block1, 7, 4, 7, pBoundingBox);
			this.placeBlock(pLevel, block1, 7, 4, 8, pBoundingBox);
			this.placeBlock(pLevel, block1, 8, 4, 7, pBoundingBox);
			this.placeBlock(pLevel, block1, 7, 4, 11, pBoundingBox);
			this.placeBlock(pLevel, block1, 7, 4, 10, pBoundingBox);
			this.placeBlock(pLevel, block1, 8, 4, 11, pBoundingBox);
			this.placeBlock(pLevel, block1, 11, 4, 7, pBoundingBox);
			this.placeBlock(pLevel, block1, 10, 4, 7, pBoundingBox);
			this.placeBlock(pLevel, block1, 11, 4, 8, pBoundingBox);
			this.placeBlock(pLevel, block1, 11, 4, 11, pBoundingBox);
			this.placeBlock(pLevel, block1, 10, 4, 11, pBoundingBox);
			this.placeBlock(pLevel, block1, 11, 4, 10, pBoundingBox);
			//center corners
			this.generateBox(pLevel, pBoundingBox, 6, 3, 6, 6, 4, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 3, 5, 6, 4, 5, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 5, 3, 6, 5, 4, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 3, 12, 6, 4, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 3, 13, 6, 4, 13, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 5, 3, 12, 5, 4, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 12, 3, 6, 12, 4, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 13, 3, 6, 13, 4, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 12, 3, 5, 12, 4, 5, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 12, 3, 12, 12, 4, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 12, 3, 13, 12, 4, 13, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 13, 3, 12, 13, 4, 12, block1, block1, false);
			//center rails
			this.generateBox(pLevel, pBoundingBox, 5, 4, 5, 5, 5, 5, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 4, 4, 6, 4, 5, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 4, 4, 6, 5, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 5, 4, 13, 5, 5, 13, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 4, 14, 6, 5, 14, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 4, 4, 12, 4, 5, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 13, 4, 5, 13, 5, 5, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 14, 4, 6, 14, 5, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 12, 4, 4, 12, 5, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 13, 4, 13, 13, 5, 13, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 14, 4, 12, 14, 5, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 12, 4, 14, 12, 5, 14, block1, block1, false);
			//top2
			this.generateBox(pLevel, pBoundingBox, 0, 4, 7, 5, 4, 8, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 4, 7, 6, 4, 8, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 13, 4, 7, 18, 4, 8, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 12, 4, 7, 12, 4, 8, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 4, 9, 8, 4, 9, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 10, 4, 9, 18, 4, 9, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 4, 10, 5, 4, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 4, 10, 6, 4, 11, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 13, 4, 10, 18, 4, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 12, 4, 10, 12, 4, 11, block2, block2, false);
			//bottom
			this.generateBox(pLevel, pBoundingBox, 7, 3, 0, 11, 3, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 7, 18, 3, 11, block1, block1, false);
			//rails
			this.generateBox(pLevel, pBoundingBox, 7, 5, 0, 7, 5, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 5, 14, 7, 5, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 11, 5, 0, 11, 5, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 11, 5, 14, 11, 5, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 5, 7, 4, 5, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 14, 5, 7, 18, 5, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 5, 11, 4, 5, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 14, 5, 11, 18, 5, 11, block1, block1, false);
			//pillars z-Axis
			this.generateBox(pLevel, pBoundingBox, 7, 2, 0, 11, 2, 0, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 4, 11, 2, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 18, 11, 2, 18, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 14, 11, 2, 14, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 3, 11, 2, 3, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 5, 11, 2, 5, stair2, stair2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 15, 11, 2, 15, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 13, 11, 2, 13, stair1, stair1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 1, 11, 1, 1, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 2, 11, 1, 2, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 7, 0, 1, 11, 0, 2, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 0, 11, 1, 0, stair1, stair1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 18, 11, 1, 18, stair2, stair2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 16, 11, 1, 16, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 17, 11, 1, 17, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 7, 0, 16, 11, 0, 17, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 3, 11, 1, 3, stair2, stair2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 15, 11, 1, 15, stair1, stair1, false);
			for (int w1 = 7; w1 <= 11; ++w1) {
				for (int l1 = 1; l1 <= 2; ++l1) {
					this.fillColumnDown(pLevel, block1, w1, -1, l1, pBoundingBox);
					this.fillColumnDown(pLevel, block1, w1, -1, 18 - l1, pBoundingBox);
				}
			}
			//pillars x-axis
			this.generateBox(pLevel, pBoundingBox, 0, 2, 7, 0, 2, 11, stair8, stair8, false);
			this.generateBox(pLevel, pBoundingBox, 4, 2, 7, 4, 2, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 18, 2, 7, 18, 2, 11, stair7, stair7, false);
			this.generateBox(pLevel, pBoundingBox, 14, 2, 7, 14, 2, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 2, 7, 3, 2, 11, stair7, stair7, false);
			this.generateBox(pLevel, pBoundingBox, 5, 2, 7, 5, 2, 11, stair6, stair6, false);
			this.generateBox(pLevel, pBoundingBox, 15, 2, 7, 15, 2, 11, stair8, stair8, false);
			this.generateBox(pLevel, pBoundingBox, 13, 2, 7, 13, 2, 11, stair5, stair5, false);
			this.generateBox(pLevel, pBoundingBox, 1, 1, 7, 1, 1, 11, stair8, stair8, false);
			this.generateBox(pLevel, pBoundingBox, 2, 1, 7, 2, 1, 11, stair7, stair7, false);
			this.generateBox(pLevel, pBoundingBox, 1, 0, 7, 2, 0, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 1, 7, 0, 1, 11, stair5, stair5, false);
			this.generateBox(pLevel, pBoundingBox, 18, 1, 7, 18, 1, 11, stair6, stair6, false);
			this.generateBox(pLevel, pBoundingBox, 16, 1, 7, 16, 1, 11, stair8, stair8, false);
			this.generateBox(pLevel, pBoundingBox, 17, 1, 7, 17, 1, 11, stair7, stair7, false);
			this.generateBox(pLevel, pBoundingBox, 16, 0, 7, 17, 0, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 1, 7, 3, 1, 11, stair6, stair6, false);
			this.generateBox(pLevel, pBoundingBox, 15, 1, 7, 15, 1, 11, stair5, stair5, false);
			for (int w2 = 1; w2 <= 2; ++w2) {
				for (int l2 = 7; l2 <= 11; ++l2) {
					this.fillColumnDown(pLevel, block1, w2, -1, l2, pBoundingBox);
					this.fillColumnDown(pLevel, block1, 18 - w2, -1, l2, pBoundingBox);
				}
			}
		}
	}

	public static class RoomCrossing extends InfernalNetherBridgePiece {
		public RoomCrossing(final int pGenDepth, final BoundingBox pBoundingBox, final Direction pDirection) {
			super(EEStructurePieceTypes.INF_ROOM_CROSSING.get(), pGenDepth, pBoundingBox);
			this.setOrientation(pDirection);
		}

		public RoomCrossing(final CompoundTag pCompoundTag) {
			super(EEStructurePieceTypes.INF_ROOM_CROSSING.get(), pCompoundTag);
		}

		@Override
		public final void addChildren(final StructurePiece pPiece, final StructurePieceAccessor pPieces, final RandomSource pRandom) {
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 0);
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 1);
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 2);
			super.addChildren(pPiece, pPieces, pRandom);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource pRandom) {
			return List.of(new ChildInfo(Direction.NORTH, 2, 0, false, EndType.DEFAULT), new ChildInfo(Direction.WEST, 0, 2, false, EndType.DEFAULT), new ChildInfo(Direction.EAST, 0, 2, false, EndType.DEFAULT));
		}

		public static final RoomCrossing createPiece(final StructurePieceAccessor pPieces, final int pX, final int pY, final int pZ, final Direction pDirection, final int pGenDepth) {
			final BoundingBox box = BoundingBox.orientBox(pX, pY, pZ, -2, 0, 0, 7, 9, 7, pDirection);
			return isOkBox(box) && pPieces.findCollisionPiece(box) == null ? new RoomCrossing(pGenDepth, box, pDirection) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel pLevel, final StructureManager pManager, final ChunkGenerator pGenerator, final RandomSource pRandom, final BoundingBox pBoundingBox, final ChunkPos pChunkPos, final BlockPos pPos) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState fence1 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence2 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence3 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence4 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);	
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			//air
			this.generateAirBox(pLevel, pBoundingBox, 0, 2, 0, 6, 7, 6);
			//floor
			this.generateBox(pLevel, pBoundingBox, 0, 0, 0, 6, 1, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 0, 0, 3, 1, 2, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 3, 0, 4, 3, 1, 6, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 0, 3, 2, 1, 3, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 4, 0, 3, 6, 1, 3, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 2, 0, 2, 2, 1, 2, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 2, 0, 4, 2, 1, 4, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 4, 0, 2, 4, 1, 2, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 4, 0, 4, 4, 1, 4, block2, block2, false);
			//walls
			this.generateBox(pLevel, pBoundingBox, 0, 2, 0, 1, 6, 0, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 6, 1, 6, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 5, 2, 0, 6, 6, 0, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 5, 2, 6, 6, 6, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 0, 0, 6, 1, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 5, 0, 6, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 2, 0, 6, 6, 1, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 2, 5, 6, 6, 6, block1, block1, false);
			//gates
			this.generateBox(pLevel, pBoundingBox, 2, 6, 0, 4, 6, 0, block1, block1, false);
			this.placeBlock(pLevel, block2, 3, 6, 0, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 2, 6, 6, 4, 6, 6, block1, block1, false);
			this.placeBlock(pLevel, block2, 3, 6, 6, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 0, 6, 2, 0, 6, 4, block1, block1, false);
			this.placeBlock(pLevel, block2, 0, 6, 3, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 6, 6, 2, 6, 6, 4, block1, block1, false);
			this.placeBlock(pLevel, block2, 6, 6, 3, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 2, 5, 0, 2, 5, 0, fence3, fence3, false);
			this.generateBox(pLevel, pBoundingBox, 3, 5, 0, 3, 5, 0, fence1, fence1, false);
			this.generateBox(pLevel, pBoundingBox, 4, 5, 0, 4, 5, 0, fence3, fence3, false);
			this.generateBox(pLevel, pBoundingBox, 2, 5, 6, 2, 5, 6, fence3, fence3, false);
			this.generateBox(pLevel, pBoundingBox, 3, 5, 6, 3, 5, 6, fence1, fence1, false);
			this.generateBox(pLevel, pBoundingBox, 4, 5, 6, 4, 5, 6, fence3, fence3, false);
			this.generateBox(pLevel, pBoundingBox, 0, 5, 2, 0, 5, 2, fence4, fence4, false);
			this.generateBox(pLevel, pBoundingBox, 0, 5, 3, 0, 5, 3, fence2, fence2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 5, 4, 0, 5, 4, fence4, fence4, false);
			this.generateBox(pLevel, pBoundingBox, 6, 5, 2, 6, 5, 2, fence4, fence4, false);
			this.generateBox(pLevel, pBoundingBox, 6, 5, 3, 6, 5, 3, fence2, fence2, false);
			this.generateBox(pLevel, pBoundingBox, 6, 5, 4, 6, 5, 4, fence4, fence4, false);
			//bottom
			this.fillColumnDown(pLevel, block1, 0, -1, 0, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 0, -1, 6, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 6, -1, 0, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 6, -1, 6, pBoundingBox);
			this.placeBlock(pLevel, stair1, 5, -1, 0, pBoundingBox);
			this.placeBlock(pLevel, stair1, 5, -1, 6, pBoundingBox);
			this.placeBlock(pLevel, stair2, 1, -1, 0, pBoundingBox);
			this.placeBlock(pLevel, stair2, 1, -1, 6, pBoundingBox);
			this.placeBlock(pLevel, stair3, 0, -1, 5, pBoundingBox);
			this.placeBlock(pLevel, stair3, 6, -1, 5, pBoundingBox);
			this.placeBlock(pLevel, stair4, 0, -1, 1, pBoundingBox);
			this.placeBlock(pLevel, stair4, 6, -1, 1, pBoundingBox);
		}
	}

	public static class LavaRoomCrossing extends InfernalNetherBridgePiece {
		private boolean hasPlacedSpawner;

		public LavaRoomCrossing(final int pGenDepth, final BoundingBox pBoundingBox, final Direction pDirection) {
			super(EEStructurePieceTypes.INF_LAVA_ROOM_CROSSING.get(), pGenDepth, pBoundingBox);
			this.setOrientation(pDirection);
		}

		public LavaRoomCrossing(final CompoundTag pCompoundTag) {
			super(EEStructurePieceTypes.INF_LAVA_ROOM_CROSSING.get(), pCompoundTag);
			this.hasPlacedSpawner = pCompoundTag.getBoolean("Mob");
		}

		@Override
		public final void addChildren(final StructurePiece pPiece, final StructurePieceAccessor pPieces, final RandomSource pRandom) {
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 0);
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 1);
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 2);
			super.addChildren(pPiece, pPieces, pRandom);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource pRandom) {
			return List.of(new ChildInfo(Direction.NORTH, 8, 3, false, EndType.THICK), new ChildInfo(Direction.WEST, 3, 8, false, EndType.THICK), new ChildInfo(Direction.EAST, 3, 8, false, EndType.THICK));
		}

		@Override
		protected int maxPlacementChunkRange() {
			return 6;
		}

		public static final LavaRoomCrossing createPiece(final StructurePieceAccessor pPieces, final int pX, final int pY, final int pZ, final Direction pDirection, final int pGenDepth) {
			final BoundingBox box = BoundingBox.orientBox(pX, pY, pZ, -8, -3, 0, 19, 11, 19, pDirection);
			return isOkBox(box) && pPieces.findCollisionPiece(box) == null ? new LavaRoomCrossing(pGenDepth, box, pDirection) : null;
		}

		@Override
		protected void addAdditionalSaveData(final StructurePieceSerializationContext pContext, final CompoundTag pCompoundTag) {
			super.addAdditionalSaveData(pContext, pCompoundTag);
			pCompoundTag.putBoolean("Mob", this.hasPlacedSpawner);
		}

		@Override
		public final void postProcess(final WorldGenLevel pLevel, final StructureManager pManager, final ChunkGenerator pGenerator, final RandomSource pRandom, final BoundingBox pBoundingBox, final ChunkPos pChunkPos, final BlockPos pPos) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = Blocks.RED_NETHER_BRICK_WALL.defaultBlockState();
			final BlockState block4 = Blocks.NETHER_BRICK_FENCE.defaultBlockState();
			final BlockState block5 = EEBlocks.BLAZE_LANTERN.get().defaultBlockState();
			final BlockState block6 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState();
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair5 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair6 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair7 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST);
			final BlockState stair8 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST);
			final BlockState stair9 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair10 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair11 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST);
			final BlockState stair12 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST);
			final BlockState slab1 = Blocks.NETHER_BRICK_SLAB.defaultBlockState();
			final BlockState slab2 = Blocks.NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP);
			final BlockState slab3 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState();
			final BlockState slab4 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP);
			final BlockState fluid1 = Blocks.LAVA.defaultBlockState();
			//air
			this.generateAirBox(pLevel, pBoundingBox, 8, 5, 0, 10, 10, 18);
			this.generateAirBox(pLevel, pBoundingBox, 0, 5, 8, 18, 10, 10);
			this.generateAirBox(pLevel, pBoundingBox, 6, 5, 5, 12, 10, 13);
			this.generateAirBox(pLevel, pBoundingBox, 5, 5, 6, 13, 10, 12);
			//structure
			this.placeBlock(pLevel, block3, 7, 6, 4, pBoundingBox);
			this.placeBlock(pLevel, block3, 7, 6, 14, pBoundingBox);
			this.placeBlock(pLevel, block3, 11, 6, 4, pBoundingBox);
			this.placeBlock(pLevel, block3, 11, 6, 14, pBoundingBox);
			this.placeBlock(pLevel, block3, 4, 6, 7, pBoundingBox);
			this.placeBlock(pLevel, block3, 14, 6, 7, pBoundingBox);
			this.placeBlock(pLevel, block3, 4, 6, 11, pBoundingBox);
			this.placeBlock(pLevel, block3, 14, 6, 11, pBoundingBox);
			this.placeBlock(pLevel, block4, 7, 7, 4, pBoundingBox);
			this.placeBlock(pLevel, block4, 7, 7, 14, pBoundingBox);
			this.placeBlock(pLevel, block4, 11, 7, 4, pBoundingBox);
			this.placeBlock(pLevel, block4, 11, 7, 14, pBoundingBox);
			this.placeBlock(pLevel, block4, 4, 7, 7, pBoundingBox);
			this.placeBlock(pLevel, block4, 14, 7, 7, pBoundingBox);
			this.placeBlock(pLevel, block4, 4, 7, 11, pBoundingBox);
			this.placeBlock(pLevel, block4, 14, 7, 11, pBoundingBox);
			this.placeBlock(pLevel, block3, 7, 8, 4, pBoundingBox);
			this.placeBlock(pLevel, block3, 7, 8, 14, pBoundingBox);
			this.placeBlock(pLevel, block3, 11, 8, 4, pBoundingBox);
			this.placeBlock(pLevel, block3, 11, 8, 14, pBoundingBox);
			this.placeBlock(pLevel, block3, 4, 8, 7, pBoundingBox);
			this.placeBlock(pLevel, block3, 14, 8, 7, pBoundingBox);
			this.placeBlock(pLevel, block3, 4, 8, 11, pBoundingBox);
			this.placeBlock(pLevel, block3, 14, 8, 11, pBoundingBox);
			this.placeBlock(pLevel, block6, 5, 6, 5, pBoundingBox);
			this.placeBlock(pLevel, block6, 5, 6, 13, pBoundingBox);
			this.placeBlock(pLevel, block6, 13, 6, 5, pBoundingBox);
			this.placeBlock(pLevel, block6, 13, 6, 13, pBoundingBox);
			this.placeBlock(pLevel, block5, 5, 7, 5, pBoundingBox);
			this.placeBlock(pLevel, block5, 5, 7, 13, pBoundingBox);
			this.placeBlock(pLevel, block5, 13, 7, 5, pBoundingBox);
			this.placeBlock(pLevel, block5, 13, 7, 13, pBoundingBox);
			//structure top
			this.placeBlock(pLevel, slab4, 5, 9, 5, pBoundingBox);
			this.placeBlock(pLevel, slab4, 5, 9, 13, pBoundingBox);
			this.placeBlock(pLevel, slab4, 13, 9, 5, pBoundingBox);
			this.placeBlock(pLevel, slab4, 13, 9, 13, pBoundingBox);
			this.placeBlock(pLevel, stair5, 6, 9, 4, pBoundingBox);
			this.placeBlock(pLevel, block1, 7, 9, 4, pBoundingBox);
			this.placeBlock(pLevel, stair6, 8, 9, 4, pBoundingBox);
			this.placeBlock(pLevel, stair5, 6, 9, 14, pBoundingBox);
			this.placeBlock(pLevel, block1, 7, 9, 14, pBoundingBox);
			this.placeBlock(pLevel, stair6, 8, 9, 14, pBoundingBox);
			this.placeBlock(pLevel, slab2, 9, 9, 4, pBoundingBox);
			this.placeBlock(pLevel, slab2, 9, 9, 14, pBoundingBox);
			this.placeBlock(pLevel, stair5, 10, 9, 4, pBoundingBox);
			this.placeBlock(pLevel, block1, 11, 9, 4, pBoundingBox);
			this.placeBlock(pLevel, stair6, 12, 9, 4, pBoundingBox);
			this.placeBlock(pLevel, stair5, 10, 9, 14, pBoundingBox);
			this.placeBlock(pLevel, block1, 11, 9, 14, pBoundingBox);
			this.placeBlock(pLevel, stair6, 12, 9, 14, pBoundingBox);
			this.placeBlock(pLevel, stair1, 4, 9, 6, pBoundingBox);
			this.placeBlock(pLevel, block1, 4, 9, 7, pBoundingBox);
			this.placeBlock(pLevel, stair2, 4, 9, 8, pBoundingBox);
			this.placeBlock(pLevel, stair1, 14, 9, 6, pBoundingBox);
			this.placeBlock(pLevel, block1, 14, 9, 7, pBoundingBox);
			this.placeBlock(pLevel, stair2, 14, 9, 8, pBoundingBox);
			this.placeBlock(pLevel, slab2, 4, 9, 9, pBoundingBox);
			this.placeBlock(pLevel, slab2, 14, 9, 9, pBoundingBox);
			this.placeBlock(pLevel, stair1, 4, 9, 10, pBoundingBox);
			this.placeBlock(pLevel, block1, 4, 9, 11, pBoundingBox);
			this.placeBlock(pLevel, stair2, 4, 9, 12, pBoundingBox);
			this.placeBlock(pLevel, stair1, 14, 9, 10, pBoundingBox);
			this.placeBlock(pLevel, block1, 14, 9, 11, pBoundingBox);
			this.placeBlock(pLevel, stair2, 14, 9, 12, pBoundingBox);
			//structure top2
			this.placeBlock(pLevel, slab4, 6, 9, 6, pBoundingBox);
			this.placeBlock(pLevel, slab4, 6, 9, 12, pBoundingBox);
			this.placeBlock(pLevel, slab4, 12, 9, 6, pBoundingBox);
			this.placeBlock(pLevel, slab4, 12, 9, 12, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 6, 9, 5, 8, 9, 5, slab4, slab4, false);
			this.generateBox(pLevel, pBoundingBox, 6, 9, 13, 8, 9, 13, slab4, slab4, false);
			this.placeBlock(pLevel, slab2, 9, 9, 5, pBoundingBox);
			this.placeBlock(pLevel, slab2, 9, 9, 13, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 10, 9, 5, 12, 9, 5, slab4, slab4, false);
			this.generateBox(pLevel, pBoundingBox, 10, 9, 13, 12, 9, 13, slab4, slab4, false);
			this.generateBox(pLevel, pBoundingBox, 5, 9, 6, 5, 9, 8, slab4, slab4, false);
			this.generateBox(pLevel, pBoundingBox, 13, 9, 6, 13, 9, 8, slab4, slab4, false);
			this.placeBlock(pLevel, slab2, 5, 9, 9, pBoundingBox);
			this.placeBlock(pLevel, slab2, 13, 9, 9, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 5, 9, 10, 5, 9, 12, slab4, slab4, false);
			this.generateBox(pLevel, pBoundingBox, 13, 9, 10, 13, 9, 12, slab4, slab4, false);
			this.placeBlock(pLevel, slab3, 6, 10, 6, pBoundingBox);
			this.placeBlock(pLevel, slab3, 6, 10, 12, pBoundingBox);
			this.placeBlock(pLevel, slab3, 12, 10, 6, pBoundingBox);
			this.placeBlock(pLevel, slab3, 12, 10, 12, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 6, 10, 5, 8, 10, 5, slab3, slab3, false);
			this.generateBox(pLevel, pBoundingBox, 6, 10, 13, 8, 10, 13, slab3, slab3, false);
			this.placeBlock(pLevel, slab1, 9, 10, 5, pBoundingBox);
			this.placeBlock(pLevel, slab1, 9, 10, 13, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 10, 10, 5, 12, 10, 5, slab3, slab3, false);
			this.generateBox(pLevel, pBoundingBox, 10, 10, 13, 12, 10, 13, slab3, slab3, false);
			this.generateBox(pLevel, pBoundingBox, 5, 10, 6, 5, 10, 8, slab3, slab3, false);
			this.generateBox(pLevel, pBoundingBox, 13, 10, 6, 13, 10, 8, slab3, slab3, false);
			this.placeBlock(pLevel, slab1, 5, 10, 9, pBoundingBox);
			this.placeBlock(pLevel, slab1, 13, 10, 9, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 5, 10, 10, 5, 10, 12, slab3, slab3, false);
			this.generateBox(pLevel, pBoundingBox, 13, 10, 10, 13, 10, 12, slab3, slab3, false);
			//structure top3
			this.placeBlock(pLevel, block1, 7, 10, 7, pBoundingBox);
			this.placeBlock(pLevel, block1, 7, 10, 11, pBoundingBox);
			this.placeBlock(pLevel, block1, 11, 10, 7, pBoundingBox);
			this.placeBlock(pLevel, block1, 11, 10, 11, pBoundingBox);
			this.placeBlock(pLevel, block2, 9, 10, 6, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 7, 10, 6, 8, 10, 6, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 10, 10, 6, 11, 10, 6, block2, block2, false);
			this.placeBlock(pLevel, block2, 9, 10, 12, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 7, 10, 12, 8, 10, 12, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 10, 10, 12, 11, 10, 12, block2, block2, false);
			this.placeBlock(pLevel, block2, 6, 10, 9, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 6, 10, 7, 6, 10, 8, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 6, 10, 10, 6, 10, 11, block2, block2, false);
			this.placeBlock(pLevel, block2, 12, 10, 9, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 12, 10, 7, 12, 10, 8, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 12, 10, 10, 12, 10, 11, block2, block2, false);
			//structure top4
			this.placeBlock(pLevel, slab2, 8, 10, 8, pBoundingBox);
			this.placeBlock(pLevel, slab2, 8, 10, 10, pBoundingBox);
			this.placeBlock(pLevel, slab2, 10, 10, 8, pBoundingBox);
			this.placeBlock(pLevel, slab2, 10, 10, 10, pBoundingBox);
			this.placeBlock(pLevel, slab2, 9, 10, 7, pBoundingBox);
			this.placeBlock(pLevel, slab4, 8, 10, 7, pBoundingBox);
			this.placeBlock(pLevel, slab4, 10, 10, 7, pBoundingBox);
			this.placeBlock(pLevel, slab2, 9, 10, 11, pBoundingBox);
			this.placeBlock(pLevel, slab4, 8, 10, 11, pBoundingBox);
			this.placeBlock(pLevel, slab4, 10, 10, 11, pBoundingBox);
			this.placeBlock(pLevel, slab2, 7, 10, 9, pBoundingBox);
			this.placeBlock(pLevel, slab4, 7, 10, 8, pBoundingBox);
			this.placeBlock(pLevel, slab4, 7, 10, 10, pBoundingBox);
			this.placeBlock(pLevel, slab2, 11, 10, 9, pBoundingBox);
			this.placeBlock(pLevel, slab4, 11, 10, 8, pBoundingBox);
			this.placeBlock(pLevel, slab4, 11, 10, 10, pBoundingBox);
			this.placeBlock(pLevel, slab1, 8, 11, 8, pBoundingBox);
			this.placeBlock(pLevel, slab1, 8, 11, 10, pBoundingBox);
			this.placeBlock(pLevel, slab1, 10, 11, 8, pBoundingBox);
			this.placeBlock(pLevel, slab1, 10, 11, 10, pBoundingBox);
			this.placeBlock(pLevel, slab1, 9, 11, 7, pBoundingBox);
			this.placeBlock(pLevel, slab3, 8, 11, 7, pBoundingBox);
			this.placeBlock(pLevel, slab3, 10, 11, 7, pBoundingBox);
			this.placeBlock(pLevel, slab1, 9, 11, 11, pBoundingBox);
			this.placeBlock(pLevel, slab3, 8, 11, 11, pBoundingBox);
			this.placeBlock(pLevel, slab3, 10, 11, 11, pBoundingBox);
			this.placeBlock(pLevel, slab1, 7, 11, 9, pBoundingBox);
			this.placeBlock(pLevel, slab3, 7, 11, 8, pBoundingBox);
			this.placeBlock(pLevel, slab3, 7, 11, 10, pBoundingBox);
			this.placeBlock(pLevel, slab1, 11, 11, 9, pBoundingBox);
			this.placeBlock(pLevel, slab3, 11, 11, 8, pBoundingBox);
			this.placeBlock(pLevel, slab3, 11, 11, 10, pBoundingBox);
			//structure top5
			this.placeBlock(pLevel, block2, 8, 11, 9, pBoundingBox);
			this.placeBlock(pLevel, block2, 9, 11, 8, pBoundingBox);
			this.placeBlock(pLevel, block2, 10, 11, 9, pBoundingBox);
			this.placeBlock(pLevel, block2, 9, 11, 10, pBoundingBox);
			this.placeBlock(pLevel, block1, 9, 11, 9, pBoundingBox);
			//top
			this.generateBox(pLevel, pBoundingBox, 7, 4, 0, 8, 4, 5, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 4, 6, 8, 4, 6, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 4, 13, 8, 4, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 4, 12, 8, 4, 12, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 9, 4, 0, 9, 4, 8, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 9, 4, 10, 9, 4, 18, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 10, 4, 0, 11, 4, 5, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 10, 4, 6, 11, 4, 6, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 10, 4, 13, 11, 4, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 10, 4, 12, 11, 4, 12, block2, block2, false);
			//center
			this.placeBlock(pLevel, stair9, 9, 5, 8, pBoundingBox);
			this.placeBlock(pLevel, stair10, 9, 5, 10, pBoundingBox);
			this.placeBlock(pLevel, stair11, 8, 5, 9, pBoundingBox);
			this.placeBlock(pLevel, stair12, 10, 5, 9, pBoundingBox);
			this.placeBlock(pLevel, slab1, 8, 5, 8, pBoundingBox);
			this.placeBlock(pLevel, slab1, 8, 5, 10, pBoundingBox);
			this.placeBlock(pLevel, slab1, 10, 5, 8, pBoundingBox);
			this.placeBlock(pLevel, slab1, 10, 5, 10, pBoundingBox);
			this.placeBlock(pLevel, block2, 8, 4, 8, pBoundingBox);
			this.placeBlock(pLevel, block2, 8, 4, 10, pBoundingBox);
			this.placeBlock(pLevel, block2, 10, 4, 8, pBoundingBox);
			this.placeBlock(pLevel, block2, 10, 4, 10, pBoundingBox);
			this.placeBlock(pLevel, block1, 7, 4, 7, pBoundingBox);
			this.placeBlock(pLevel, block1, 7, 4, 8, pBoundingBox);
			this.placeBlock(pLevel, block1, 8, 4, 7, pBoundingBox);
			this.placeBlock(pLevel, block1, 7, 4, 11, pBoundingBox);
			this.placeBlock(pLevel, block1, 7, 4, 10, pBoundingBox);
			this.placeBlock(pLevel, block1, 8, 4, 11, pBoundingBox);
			this.placeBlock(pLevel, block1, 11, 4, 7, pBoundingBox);
			this.placeBlock(pLevel, block1, 10, 4, 7, pBoundingBox);
			this.placeBlock(pLevel, block1, 11, 4, 8, pBoundingBox);
			this.placeBlock(pLevel, block1, 11, 4, 11, pBoundingBox);
			this.placeBlock(pLevel, block1, 10, 4, 11, pBoundingBox);
			this.placeBlock(pLevel, block1, 11, 4, 10, pBoundingBox);
			//center corners
			this.generateBox(pLevel, pBoundingBox, 6, 3, 6, 6, 4, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 3, 5, 6, 4, 5, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 5, 3, 6, 5, 4, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 3, 12, 6, 4, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 3, 13, 6, 4, 13, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 5, 3, 12, 5, 4, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 12, 3, 6, 12, 4, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 13, 3, 6, 13, 4, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 12, 3, 5, 12, 4, 5, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 12, 3, 12, 12, 4, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 12, 3, 13, 12, 4, 13, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 13, 3, 12, 13, 4, 12, block1, block1, false);
			//center rails
			this.generateBox(pLevel, pBoundingBox, 5, 4, 5, 5, 5, 5, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 4, 4, 6, 4, 5, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 4, 4, 6, 5, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 5, 4, 13, 5, 5, 13, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 4, 14, 6, 5, 14, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 4, 4, 12, 4, 5, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 13, 4, 5, 13, 5, 5, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 14, 4, 6, 14, 5, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 12, 4, 4, 12, 5, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 13, 4, 13, 13, 5, 13, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 14, 4, 12, 14, 5, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 12, 4, 14, 12, 5, 14, block1, block1, false);
			//top2
			this.generateBox(pLevel, pBoundingBox, 0, 4, 7, 5, 4, 8, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 4, 7, 6, 4, 8, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 13, 4, 7, 18, 4, 8, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 12, 4, 7, 12, 4, 8, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 4, 9, 8, 4, 9, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 10, 4, 9, 18, 4, 9, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 4, 10, 5, 4, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 4, 10, 6, 4, 11, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 13, 4, 10, 18, 4, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 12, 4, 10, 12, 4, 11, block2, block2, false);
			//bottom
			this.generateBox(pLevel, pBoundingBox, 7, 3, 0, 11, 3, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 7, 18, 3, 11, block1, block1, false);
			//rails
			this.generateBox(pLevel, pBoundingBox, 7, 5, 0, 7, 5, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 5, 14, 7, 5, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 11, 5, 0, 11, 5, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 11, 5, 14, 11, 5, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 5, 7, 4, 5, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 14, 5, 7, 18, 5, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 5, 11, 4, 5, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 14, 5, 11, 18, 5, 11, block1, block1, false);
			//pillars z-Axis
			this.generateBox(pLevel, pBoundingBox, 7, 2, 0, 11, 2, 0, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 4, 11, 2, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 18, 11, 2, 18, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 14, 11, 2, 14, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 3, 11, 2, 3, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 5, 11, 2, 5, stair2, stair2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 15, 11, 2, 15, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 13, 11, 2, 13, stair1, stair1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 1, 11, 1, 1, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 2, 11, 1, 2, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 7, 0, 1, 11, 0, 2, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 0, 11, 1, 0, stair1, stair1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 18, 11, 1, 18, stair2, stair2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 16, 11, 1, 16, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 17, 11, 1, 17, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 7, 0, 16, 11, 0, 17, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 3, 11, 1, 3, stair2, stair2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 1, 15, 11, 1, 15, stair1, stair1, false);
			for (int w1 = 7; w1 <= 11; ++w1) {
				for (int l1 = 1; l1 <= 2; ++l1) {
					this.fillColumnDown(pLevel, block1, w1, -1, l1, pBoundingBox);
					this.fillColumnDown(pLevel, block1, w1, -1, 18 - l1, pBoundingBox);
				}
			}
			//pillars x-axis
			this.generateBox(pLevel, pBoundingBox, 0, 2, 7, 0, 2, 11, stair8, stair8, false);
			this.generateBox(pLevel, pBoundingBox, 4, 2, 7, 4, 2, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 18, 2, 7, 18, 2, 11, stair7, stair7, false);
			this.generateBox(pLevel, pBoundingBox, 14, 2, 7, 14, 2, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 2, 7, 3, 2, 11, stair7, stair7, false);
			this.generateBox(pLevel, pBoundingBox, 5, 2, 7, 5, 2, 11, stair6, stair6, false);
			this.generateBox(pLevel, pBoundingBox, 15, 2, 7, 15, 2, 11, stair8, stair8, false);
			this.generateBox(pLevel, pBoundingBox, 13, 2, 7, 13, 2, 11, stair5, stair5, false);
			this.generateBox(pLevel, pBoundingBox, 1, 1, 7, 1, 1, 11, stair8, stair8, false);
			this.generateBox(pLevel, pBoundingBox, 2, 1, 7, 2, 1, 11, stair7, stair7, false);
			this.generateBox(pLevel, pBoundingBox, 1, 0, 7, 2, 0, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 1, 7, 0, 1, 11, stair5, stair5, false);
			this.generateBox(pLevel, pBoundingBox, 18, 1, 7, 18, 1, 11, stair6, stair6, false);
			this.generateBox(pLevel, pBoundingBox, 16, 1, 7, 16, 1, 11, stair8, stair8, false);
			this.generateBox(pLevel, pBoundingBox, 17, 1, 7, 17, 1, 11, stair7, stair7, false);
			this.generateBox(pLevel, pBoundingBox, 16, 0, 7, 17, 0, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 1, 7, 3, 1, 11, stair6, stair6, false);
			this.generateBox(pLevel, pBoundingBox, 15, 1, 7, 15, 1, 11, stair5, stair5, false);
			for (int w2 = 1; w2 <= 2; ++w2) {
				for (int l2 = 7; l2 <= 11; ++l2) {
					this.fillColumnDown(pLevel, block1, w2, -1, l2, pBoundingBox);
					this.fillColumnDown(pLevel, block1, 18 - w2, -1, l2, pBoundingBox);
				}
			}
			//fluids
			this.placeBlock(pLevel, fluid1, 9, 5, 9, pBoundingBox);
			//spawners
			if (!this.hasPlacedSpawner) {
				final BlockPos spawnerPos = new BlockPos(this.getWorldX(9, 9), this.getWorldY(4), this.getWorldZ(9, 9));
				if (pBoundingBox.isInside(spawnerPos)) {
					this.hasPlacedSpawner = true;
					pLevel.setBlock(spawnerPos, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = pLevel.getBlockEntity(spawnerPos);
					if (spawner instanceof SpawnerBlockEntity spawnerBlockEntity)
						spawnerBlockEntity.setEntityId(EntityType.MAGMA_CUBE, pRandom);
				}
			}
		}
	}

	public static class CastleEntrance extends InfernalNetherBridgePiece {
		public CastleEntrance(final int pGenDepth, final RandomSource pRandom, final BoundingBox pBoundingBox, final Direction pDirection) {
			super(EEStructurePieceTypes.INF_CASTLE_ENTRANCE.get(), pGenDepth, pBoundingBox);
			this.setOrientation(pDirection);
		}

		public CastleEntrance(final CompoundTag pCompoundTag) {
			super(EEStructurePieceTypes.INF_CASTLE_ENTRANCE.get(), pCompoundTag);
		}

		@Override
		public final void addChildren(final StructurePiece pPiece, final StructurePieceAccessor pPieces, final RandomSource pRandom) {
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 0);
			super.addChildren(pPiece, pPieces, pRandom);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource pRandom) {
			return List.of(new ChildInfo(Direction.NORTH, 5, 3, true, EndType.DEFAULT));
		}

		@Override
		protected int maxPlacementChunkRange() {
			return 5;
		}

		@Override
		protected final List<Class<? extends InfernalNetherBridgePiece>> getUnacceptableChildren() {
			return List.of(CastleCorridorTBalconyPiece.class, CastleStalkRoom.class);
		}

		public static final CastleEntrance createPiece(final StructurePieceAccessor pPieces, final RandomSource pRandom, final int pX, final int pY, final int pZ, final Direction pDirection, final int pGenDepth) {
			final BoundingBox box = BoundingBox.orientBox(pX, pY, pZ, -5, -3, 0, 13, 14, 13, pDirection);
			return isOkBox(box) && pPieces.findCollisionPiece(box) == null ? new CastleEntrance(pGenDepth, pRandom, box, pDirection) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel pLevel, final StructureManager pManager, final ChunkGenerator pGenerator, final RandomSource pRandom, final BoundingBox pBoundingBox, final ChunkPos pChunkPos, final BlockPos pPos) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block4 = EEBlocks.BLAZE_LANTERN.get().defaultBlockState().setValue(BlazeLanternBlock.HANGING, Boolean.valueOf(true));
			final BlockState block5 = Blocks.LANTERN.defaultBlockState();
			final BlockState stair1 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair2 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair3 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST);
			final BlockState stair4 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST);
			final BlockState stair5 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair6 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);	
			final BlockState stair7 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair8 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);	
			final BlockState stair9 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST);
			final BlockState stair10 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST);	
			final BlockState stair11 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair12 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);	
			final BlockState fence1 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence2 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence3 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence4 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence5 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence6 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence7 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true));
			final BlockState fence8 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true));
			final BlockState fence9 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState();
			final BlockState slab1 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState();
			final BlockState slab2 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP);
			final BlockState slab3 = Blocks.NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP);
			final BlockState wall1 = Blocks.NETHER_BRICK_WALL.defaultBlockState();
			final BlockState wall2 = Blocks.RED_NETHER_BRICK_WALL.defaultBlockState();
			final BlockState fluid1 = Blocks.LAVA.defaultBlockState();
			//air
			this.generateAirBox(pLevel, pBoundingBox, 0, 5, 0, 12, 13, 12);
			//floor
			this.generateBox(pLevel, pBoundingBox, 0, 3, 0, 12, 4, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 3, 0, 6, 4, 5, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 6, 3, 7, 6, 4, 12, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 4, 3, 6, 5, 4, 6, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 3, 6, 8, 4, 6, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 4, 3, 3, 8, 4, 3, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 4, 3, 9, 8, 4, 9, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 3, 3, 4, 3, 4, 8, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 9, 3, 4, 9, 4, 8, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 5, 3, 5, 5, 4, 5, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 5, 3, 7, 5, 4, 7, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 3, 5, 7, 4, 5, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 3, 7, 7, 4, 7, block2, block2, false);	
			this.generateBox(pLevel, pBoundingBox, 6, 4, 7, 6, 4, 7, stair1, stair1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 4, 5, 6, 4, 5, stair2, stair2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 4, 6, 7, 4, 6, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 5, 4, 6, 5, 4, 6, stair4, stair4, false);
			this.generateAirBox(pLevel, pBoundingBox, 6, 1, 6, 6, 4, 6);
			//walls
			this.generateBox(pLevel, pBoundingBox, 0, 5, 0, 1, 12, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 11, 5, 0, 12, 12, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 2, 5, 11, 4, 12, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 8, 5, 11, 10, 12, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 5, 9, 11, 7, 12, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 2, 5, 0, 4, 12, 1, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 8, 5, 0, 10, 12, 1, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 5, 9, 0, 7, 12, 1, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 8, 0, 7, 8, 1, stair5, stair5, false);
			this.generateBox(pLevel, pBoundingBox, 5, 8, 0, 5, 8, 1, stair6, stair6, false);
			this.generateBox(pLevel, pBoundingBox, 7, 8, 11, 7, 8, 12, stair5, stair5, false);
			this.generateBox(pLevel, pBoundingBox, 5, 8, 11, 5, 8, 12, stair6, stair6, false);
			this.generateBox(pLevel, pBoundingBox, 6, 9, 1, 6, 9, 1, block4, block4, false);
			this.generateBox(pLevel, pBoundingBox, 6, 9, 11, 6, 9, 11, block4, block4, false);
			//roof
			this.generateBox(pLevel, pBoundingBox, 0, 11, 0, 12, 12, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 11, 4, 6, 12, 5, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 6, 11, 7, 6, 12, 8, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 4, 11, 6, 5, 12, 6, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 11, 6, 8, 12, 6, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 4, 11, 3, 8, 12, 3, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 4, 11, 9, 8, 12, 9, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 3, 11, 4, 3, 12, 8, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 9, 11, 4, 9, 12, 8, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 5, 11, 5, 5, 12, 5, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 5, 11, 7, 5, 12, 7, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 11, 5, 7, 12, 5, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 11, 7, 7, 12, 7, block2, block2, false);
			//fences
			for (int i = 1; i <= 11; i += 2) {
				this.generateBox(pLevel, pBoundingBox, i, 10, 0, i, 11, 0, fence1, fence1, false);
				this.generateBox(pLevel, pBoundingBox, i, 9, 0, i, 9, 0, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, i, 12, 0, i, 12, 0, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, i, 10, 12, i, 11, 12, fence1, fence1, false);
				this.generateBox(pLevel, pBoundingBox, i, 9, 12, i, 9, 12, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, i, 12, 12, i, 12, 12, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, 0, 10, i, 0, 11, i, fence2, fence2, false);
				this.generateBox(pLevel, pBoundingBox, 0, 9, i, 0, 9, i, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, 0, 12, i, 0, 12, i, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, 12, 10, i, 12, 11, i, fence2, fence2, false);
				this.generateBox(pLevel, pBoundingBox, 12, 9, i, 12, 9, i, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, 12, 12, i, 12, 12, i, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, i, 13, 0, i, 13, 0, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, i, 13, 12, i, 13, 12, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, 0, 13, i, 0, 13, i, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, 12, 13, i, 12, 13, i, block2, block2, false);
				if (i != 11) {
					this.placeBlock(pLevel, fence3, i + 1, 13, 0, pBoundingBox);
					this.placeBlock(pLevel, fence3, i + 1, 13, 12, pBoundingBox);
					this.placeBlock(pLevel, fence4, 0, 13, i + 1, pBoundingBox);
					this.placeBlock(pLevel, fence4, 12, 13, i + 1, pBoundingBox);
				}
			}
			this.placeBlock(pLevel, fence5, 0, 13, 0, pBoundingBox);
			this.placeBlock(pLevel, fence6, 0, 13, 12, pBoundingBox);
			this.placeBlock(pLevel, fence7, 12, 13, 12, pBoundingBox);
			this.placeBlock(pLevel, fence8, 12, 13, 0, pBoundingBox);
			for (int k = 3; k <= 9; k += 2) {
				this.generateBox(pLevel, pBoundingBox, 1, 7, k, 1, 8, k, fence2.setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)), fence2.setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)), false);
				this.generateBox(pLevel, pBoundingBox, 1, 6, k, 1, 6, k, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, 1, 9, k, 1, 9, k, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, 11, 7, k, 11, 8, k, fence2.setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true)), fence2.setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true)), false);
				this.generateBox(pLevel, pBoundingBox, 11, 6, k, 11, 6, k, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, 11, 9, k, 11, 9, k, block2, block2, false);
			}
			//corners
			this.placeBlock(pLevel, slab1, 2, 5, 3, pBoundingBox);
			this.placeBlock(pLevel, slab1, 3, 5, 2, pBoundingBox);
			this.placeBlock(pLevel, slab1, 9, 5, 2, pBoundingBox);
			this.placeBlock(pLevel, slab1, 10, 5, 3, pBoundingBox);
			this.placeBlock(pLevel, slab1, 2, 5, 9, pBoundingBox);
			this.placeBlock(pLevel, slab1, 3, 5, 10, pBoundingBox);
			this.placeBlock(pLevel, slab1, 9, 5, 10, pBoundingBox);
			this.placeBlock(pLevel, slab1, 10, 5, 9, pBoundingBox);
			this.placeBlock(pLevel, slab2, 2, 10, 3, pBoundingBox);
			this.placeBlock(pLevel, slab2, 3, 10, 2, pBoundingBox);
			this.placeBlock(pLevel, slab2, 9, 10, 2, pBoundingBox);
			this.placeBlock(pLevel, slab2, 10, 10, 3, pBoundingBox);
			this.placeBlock(pLevel, slab2, 2, 10, 9, pBoundingBox);
			this.placeBlock(pLevel, slab2, 3, 10, 10, pBoundingBox);
			this.placeBlock(pLevel, slab2, 9, 10, 10, pBoundingBox);
			this.placeBlock(pLevel, slab2, 10, 10, 9, pBoundingBox);
			this.placeBlock(pLevel, fluid1, 2, 10, 2, pBoundingBox);
			this.placeBlock(pLevel, fluid1, 10, 10, 2, pBoundingBox);
			this.placeBlock(pLevel, fluid1, 2, 10, 10, pBoundingBox);
			this.placeBlock(pLevel, fluid1, 10, 10, 10, pBoundingBox);
			//center
			this.generateBox(pLevel, pBoundingBox, 4, 10, 5, 8, 10, 5, slab2, slab2, false);
			this.generateBox(pLevel, pBoundingBox, 5, 10, 4, 5, 10, 8, slab2, slab2, false);
			this.generateBox(pLevel, pBoundingBox, 4, 10, 7, 8, 10, 7, slab2, slab2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 10, 4, 7, 10, 8, slab2, slab2, false);
			this.generateBox(pLevel, pBoundingBox, 6, 10, 5, 6, 10, 5, slab3, slab3, false);
			this.generateBox(pLevel, pBoundingBox, 5, 10, 6, 5, 10, 6, slab3, slab3, false);
			this.generateBox(pLevel, pBoundingBox, 6, 10, 7, 6, 10, 7, slab3, slab3, false);
			this.generateBox(pLevel, pBoundingBox, 7, 10, 6, 7, 10, 6, slab3, slab3, false);
			this.placeBlock(pLevel, wall1, 5, 5, 5, pBoundingBox);
			this.placeBlock(pLevel, fence9, 5, 6, 5, pBoundingBox);
			this.placeBlock(pLevel, wall2, 5, 7, 5, pBoundingBox);
			this.placeBlock(pLevel, fence9, 5, 8, 5, pBoundingBox);
			this.placeBlock(pLevel, wall1, 5, 9, 5, pBoundingBox);
			this.placeBlock(pLevel, block2, 5, 10, 5, pBoundingBox);
			this.placeBlock(pLevel, wall1, 5, 5, 7, pBoundingBox);
			this.placeBlock(pLevel, fence9, 5, 6, 7, pBoundingBox);
			this.placeBlock(pLevel, wall2, 5, 7, 7, pBoundingBox);
			this.placeBlock(pLevel, fence9, 5, 8, 7, pBoundingBox);
			this.placeBlock(pLevel, wall1, 5, 9, 7, pBoundingBox);
			this.placeBlock(pLevel, block2, 5, 10, 7, pBoundingBox);
			this.placeBlock(pLevel, wall1, 7, 5, 5, pBoundingBox);
			this.placeBlock(pLevel, fence9, 7, 6, 5, pBoundingBox);
			this.placeBlock(pLevel, wall2, 7, 7, 5, pBoundingBox);
			this.placeBlock(pLevel, fence9, 7, 8, 5, pBoundingBox);
			this.placeBlock(pLevel, wall1, 7, 9, 5, pBoundingBox);
			this.placeBlock(pLevel, block2, 7, 10, 5, pBoundingBox);
			this.placeBlock(pLevel, wall1, 7, 5, 7, pBoundingBox);
			this.placeBlock(pLevel, fence9, 7, 6, 7, pBoundingBox);
			this.placeBlock(pLevel, wall2, 7, 7, 7, pBoundingBox);
			this.placeBlock(pLevel, fence9, 7, 8, 7, pBoundingBox);
			this.placeBlock(pLevel, wall1, 7, 9, 7, pBoundingBox);
			this.placeBlock(pLevel, block2, 7, 10, 7, pBoundingBox);
			this.placeBlock(pLevel, fluid1, 6, 10, 6, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 6, 0, 6, pBoundingBox);
			//base pillars
			this.fillColumnDown(pLevel, block1, 0, 2, 0, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 0, 2, 12, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 12, 2, 0, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 12, 2, 12, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 0, 1, 6, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 6, 1, 0, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 6, 1, 12, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 12, 1, 6, pBoundingBox);
			this.placeBlock(pLevel, block5, 0, 2, 6, pBoundingBox);
			this.placeBlock(pLevel, block5, 6, 2, 0, pBoundingBox);
			this.placeBlock(pLevel, block5, 6, 2, 12, pBoundingBox);
			this.placeBlock(pLevel, block5, 12, 2, 6, pBoundingBox);
			//pillars x
			this.placeBlock(pLevel, stair5, 11, 0, 0, pBoundingBox);
			this.placeBlock(pLevel, stair10, 11, 1, 0, pBoundingBox);
			this.placeBlock(pLevel, stair5, 10, 1, 0, pBoundingBox);
			this.placeBlock(pLevel, stair10, 10, 2, 0, pBoundingBox);
			this.placeBlock(pLevel, stair5, 9, 2, 0, pBoundingBox);
			this.placeBlock(pLevel, stair5, 11, 0, 12, pBoundingBox);
			this.placeBlock(pLevel, stair10, 11, 1, 12, pBoundingBox);
			this.placeBlock(pLevel, stair5, 10, 1, 12, pBoundingBox);
			this.placeBlock(pLevel, stair10, 10, 2, 12, pBoundingBox);
			this.placeBlock(pLevel, stair5, 9, 2, 12, pBoundingBox);
			this.placeBlock(pLevel, stair5, 5, 1, 0, pBoundingBox);
			this.placeBlock(pLevel, stair10, 5, 2, 0, pBoundingBox);
			this.placeBlock(pLevel, stair5, 4, 2, 0, pBoundingBox);
			this.placeBlock(pLevel, stair5, 5, 1, 12, pBoundingBox);
			this.placeBlock(pLevel, stair10, 5, 2, 12, pBoundingBox);
			this.placeBlock(pLevel, stair5, 4, 2, 12, pBoundingBox);
			this.placeBlock(pLevel, stair6, 1, 0, 0, pBoundingBox);
			this.placeBlock(pLevel, stair9, 1, 1, 0, pBoundingBox);
			this.placeBlock(pLevel, stair6, 2, 1, 0, pBoundingBox);
			this.placeBlock(pLevel, stair9, 2, 2, 0, pBoundingBox);
			this.placeBlock(pLevel, stair6, 3, 2, 0, pBoundingBox);
			this.placeBlock(pLevel, stair6, 1, 0, 12, pBoundingBox);
			this.placeBlock(pLevel, stair9, 1, 1, 12, pBoundingBox);
			this.placeBlock(pLevel, stair6, 2, 1, 12, pBoundingBox);
			this.placeBlock(pLevel, stair9, 2, 2, 12, pBoundingBox);
			this.placeBlock(pLevel, stair6, 3, 2, 12, pBoundingBox);
			this.placeBlock(pLevel, stair6, 7, 1, 0, pBoundingBox);
			this.placeBlock(pLevel, stair9, 7, 2, 0, pBoundingBox);
			this.placeBlock(pLevel, stair6, 8, 2, 0, pBoundingBox);
			this.placeBlock(pLevel, stair6, 7, 1, 12, pBoundingBox);
			this.placeBlock(pLevel, stair9, 7, 2, 12, pBoundingBox);
			this.placeBlock(pLevel, stair6, 8, 2, 12, pBoundingBox);
			//pillars z
			this.placeBlock(pLevel, stair7, 0, 0, 11, pBoundingBox);
			this.placeBlock(pLevel, stair12, 0, 1, 11, pBoundingBox);
			this.placeBlock(pLevel, stair7, 0, 1, 10, pBoundingBox);
			this.placeBlock(pLevel, stair12, 0, 2, 10, pBoundingBox);
			this.placeBlock(pLevel, stair7, 0, 2, 9, pBoundingBox);
			this.placeBlock(pLevel, stair7, 12, 0, 11, pBoundingBox);
			this.placeBlock(pLevel, stair12, 12, 1, 11, pBoundingBox);
			this.placeBlock(pLevel, stair7, 12, 1, 10, pBoundingBox);
			this.placeBlock(pLevel, stair12, 12, 2, 10, pBoundingBox);
			this.placeBlock(pLevel, stair7, 12, 2, 9, pBoundingBox);
			this.placeBlock(pLevel, stair7, 0, 1, 5, pBoundingBox);
			this.placeBlock(pLevel, stair12, 0, 2, 5, pBoundingBox);
			this.placeBlock(pLevel, stair7, 0, 2, 4, pBoundingBox);
			this.placeBlock(pLevel, stair7, 12, 1, 5, pBoundingBox);
			this.placeBlock(pLevel, stair12, 12, 2, 5, pBoundingBox);
			this.placeBlock(pLevel, stair7, 12, 2, 4, pBoundingBox);
			this.placeBlock(pLevel, stair8, 0, 0, 1, pBoundingBox);
			this.placeBlock(pLevel, stair11, 0, 1, 1, pBoundingBox);
			this.placeBlock(pLevel, stair8, 0, 1, 2, pBoundingBox);
			this.placeBlock(pLevel, stair11, 0, 2, 2, pBoundingBox);
			this.placeBlock(pLevel, stair8, 0, 2, 3, pBoundingBox);
			this.placeBlock(pLevel, stair8, 12, 0, 1, pBoundingBox);
			this.placeBlock(pLevel, stair11, 12, 1, 1, pBoundingBox);
			this.placeBlock(pLevel, stair8, 12, 1, 2, pBoundingBox);
			this.placeBlock(pLevel, stair11, 12, 2, 2, pBoundingBox);
			this.placeBlock(pLevel, stair8, 12, 2, 3, pBoundingBox);
			this.placeBlock(pLevel, stair8, 0, 1, 7, pBoundingBox);
			this.placeBlock(pLevel, stair11, 0, 2, 7, pBoundingBox);
			this.placeBlock(pLevel, stair8, 0, 2, 8, pBoundingBox);
			this.placeBlock(pLevel, stair8, 12, 1, 7, pBoundingBox);
			this.placeBlock(pLevel, stair11, 12, 2, 7, pBoundingBox);
			this.placeBlock(pLevel, stair8, 12, 2, 8, pBoundingBox);
		}
	}

	public static class StairsRoom extends InfernalNetherBridgePiece {
		private boolean hasPlacedSpawner;

		public StairsRoom(final int pGenDepth, final BoundingBox pBoundingBox, final Direction pDirection) {
			super(EEStructurePieceTypes.INF_STAIRS_ROOM.get(), pGenDepth, pBoundingBox);
			this.setOrientation(pDirection);
		}

		public StairsRoom(final CompoundTag pCompoundTag) {
			super(EEStructurePieceTypes.INF_STAIRS_ROOM.get(), pCompoundTag);
			this.hasPlacedSpawner = pCompoundTag.getBoolean("Mob");
		}

		@Override
		public void addChildren(final StructurePiece pPiece, final StructurePieceAccessor pPieces, final RandomSource pRandom) {
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 0);
			super.addChildren(pPiece, pPieces, pRandom);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource pRandom) {
			return List.of(new ChildInfo(Direction.EAST, 6, 2, false, EndType.DEFAULT));
		}

		public static final StairsRoom createPiece(final StructurePieceAccessor pPieces, final int pX, final int pY, final int pZ, final Direction pDirection, final int pGenDepth) {
			final BoundingBox box = BoundingBox.orientBox(pX, pY, pZ, -2, 0, 0, 7, 11, 7, pDirection);
			return isOkBox(box) && pPieces.findCollisionPiece(box) == null ? new StairsRoom(pGenDepth, box, pDirection) : null;
		}

		@Override
		protected void addAdditionalSaveData(final StructurePieceSerializationContext pContext, final CompoundTag pCompoundTag) {
			super.addAdditionalSaveData(pContext, pCompoundTag);
			pCompoundTag.putBoolean("Mob", this.hasPlacedSpawner);
		}

		@Override
		public final void postProcess(final WorldGenLevel pLevel, final StructureManager pManager, final ChunkGenerator pGenerator, final RandomSource pRandom, final BoundingBox pBoundingBox, final ChunkPos pChunkPos, final BlockPos pPos) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState fence1 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence2 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence3 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence4 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState wall1 = Blocks.RED_NETHER_BRICK_WALL.defaultBlockState().setValue(WallBlock.WEST_WALL, WallSide.LOW).setValue(WallBlock.EAST_WALL, WallSide.LOW).setValue(WallBlock.UP, Boolean.valueOf(false));
			final BlockState wall2 = Blocks.RED_NETHER_BRICK_WALL.defaultBlockState().setValue(WallBlock.NORTH_WALL, WallSide.LOW).setValue(WallBlock.SOUTH_WALL, WallSide.LOW).setValue(WallBlock.UP, Boolean.valueOf(false));
			final BlockState stair1 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);	
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair5 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair6 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST);
			final BlockState stair7 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState brewingStand = Blocks.BREWING_STAND.defaultBlockState();
			final BlockState skull1 = Blocks.SKELETON_WALL_SKULL.defaultBlockState().setValue(WallSkullBlock.FACING, Direction.SOUTH);
			final BlockState skull2 = Blocks.WITHER_SKELETON_WALL_SKULL.defaultBlockState().setValue(WallSkullBlock.FACING, Direction.SOUTH);
			//air
			this.generateAirBox(pLevel, pBoundingBox, 0, 2, 0, 6, 10, 6);
			//floor
			this.generateBox(pLevel, pBoundingBox, 0, 0, 0, 6, 1, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 0, 0, 3, 1, 2, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 2, 0, 2, 2, 1, 4, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 4, 0, 2, 4, 1, 4, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 3, 0, 4, 3, 1, 4, block2, block2, false);
			//walls
			this.generateBox(pLevel, pBoundingBox, 0, 2, 0, 1, 8, 0, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 5, 2, 0, 6, 8, 0, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 1, 0, 8, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 2, 1, 6, 8, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 1, 2, 6, 5, 8, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 2, 0, 3, 4, fence2, fence2, false);
			this.generateBox(pLevel, pBoundingBox, 6, 3, 2, 6, 3, 2, fence2, fence2, false);
			this.generateBox(pLevel, pBoundingBox, 6, 3, 4, 6, 3, 4, fence2, fence2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 4, 2, 0, 4, 4, fence4, fence4, false);
			this.generateBox(pLevel, pBoundingBox, 6, 4, 2, 6, 4, 2, fence4, fence4, false);
			this.generateBox(pLevel, pBoundingBox, 6, 4, 4, 6, 4, 4, fence4, fence4, false);
			this.generateBox(pLevel, pBoundingBox, 0, 5, 2, 0, 5, 4, fence2, fence2, false);
			this.generateBox(pLevel, pBoundingBox, 6, 5, 2, 6, 5, 2, fence2, fence2, false);
			this.generateBox(pLevel, pBoundingBox, 6, 5, 4, 6, 5, 4, fence2, fence2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 6, 2, 0, 6, 4, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 6, 6, 2, 6, 6, 2, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 6, 6, 4, 6, 6, 4, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 2, 0, 2, 4, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 6, 2, 2, 6, 2, 2, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 6, 2, 4, 6, 2, 4, block2, block2, false);
			//gate
			this.generateAirBox(pLevel, pBoundingBox, 6, 8, 2, 6, 8, 4);
			this.generateBox(pLevel, pBoundingBox, 2, 6, 0, 4, 8, 0, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 2, 5, 0, 2, 5, 0, fence1, fence1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 5, 0, 3, 5, 0, fence3, fence3, false);
			this.generateBox(pLevel, pBoundingBox, 4, 5, 0, 4, 5, 0, fence1, fence1, false);
			//roof
			this.generateBox(pLevel, pBoundingBox, 1, 7, 1, 5, 7, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 4, 7, 3, 6, 7, 3, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 2, 7, 2, 4, 7, 2, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 2, 7, 4, 4, 7, 4, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 2, 7, 3, 2, 7, 3, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 1, 8, 0, 5, 8, 0, wall1, wall1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 8, 1, 0, 8, 5, wall2, wall2, false);
			this.generateBox(pLevel, pBoundingBox, 1, 8, 6, 5, 8, 6, wall1, wall1, false);
			//stairs
			final boolean rand1 = pRandom.nextInt(4) == 0;
			final boolean rand2 = pRandom.nextBoolean();
			this.placeBlock(pLevel, block1, 5, 2, 5, pBoundingBox);
			this.placeBlock(pLevel, stair7, 5, 2, 4, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 4, 2, 5, 4, 2, 5, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 3, 2, 5, 3, 3, 5, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 2, 2, 5, 2, 4, 5, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 1, 2, 5, 1, 5, 5, block2, block2, false);
			this.placeBlock(pLevel, stair6, 4, 3, 5, pBoundingBox);
			this.placeBlock(pLevel, stair6, 3, 4, 5, pBoundingBox);
			this.placeBlock(pLevel, stair6, 2, 5, 5, pBoundingBox);
			this.placeBlock(pLevel, stair6, 1, 6, 5, pBoundingBox);
			if (rand1) {
				this.generateAirBox(pLevel, pBoundingBox, 1, 3, 5, 2, 4, 5);
				this.placeBlock(pLevel, stair1, 2, 4, 5, pBoundingBox);
				this.placeBlock(pLevel, brewingStand, 1, 3, 5, pBoundingBox);
				this.placeBlock(pLevel, rand2 ? skull1 : skull2, 2, 3, 5, pBoundingBox);
			}
			//bottom
			this.fillColumnDown(pLevel, block1, 0, -1, 0, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 0, -1, 6, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 6, -1, 0, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 6, -1, 6, pBoundingBox);
			this.placeBlock(pLevel, stair2, 5, -1, 0, pBoundingBox);
			this.placeBlock(pLevel, stair2, 5, -1, 6, pBoundingBox);
			this.placeBlock(pLevel, stair3, 1, -1, 0, pBoundingBox);
			this.placeBlock(pLevel, stair3, 1, -1, 6, pBoundingBox);
			this.placeBlock(pLevel, stair4, 0, -1, 5, pBoundingBox);
			this.placeBlock(pLevel, stair4, 6, -1, 5, pBoundingBox);
			this.placeBlock(pLevel, stair5, 0, -1, 1, pBoundingBox);
			this.placeBlock(pLevel, stair5, 6, -1, 1, pBoundingBox);
			//spawners
			if (!this.hasPlacedSpawner && rand1) {
				final BlockPos spawnerPos = new BlockPos(this.getWorldX(2, 5), this.getWorldY(2), this.getWorldZ(2, 5));
				if (pBoundingBox.isInside(spawnerPos)) {
					this.hasPlacedSpawner = true;
					pLevel.setBlock(spawnerPos, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = pLevel.getBlockEntity(spawnerPos);
					if (spawner instanceof SpawnerBlockEntity spawnerBlockEntity)
						spawnerBlockEntity.setEntityId(rand2 ? EntityType.SKELETON : EntityType.WITHER_SKELETON, pRandom);
				}
			}
		}
	}

	public static class MonsterThrone extends InfernalNetherBridgePiece {
		private boolean hasPlacedSpawner;

		public MonsterThrone(final int pGenDepth, final BoundingBox pBoundingBox, final Direction pDirection) {
			super(EEStructurePieceTypes.INF_MONSTER_THRONE.get(), pGenDepth, pBoundingBox);
			this.setOrientation(pDirection);
		}

		public MonsterThrone(final CompoundTag pCompoundTag) {
			super(EEStructurePieceTypes.INF_MONSTER_THRONE.get(), pCompoundTag);
			this.hasPlacedSpawner = pCompoundTag.getBoolean("Mob");
		}

		@Override
		protected final void addAdditionalSaveData(final StructurePieceSerializationContext pContext, final CompoundTag pCompoundTag) {
			super.addAdditionalSaveData(pContext, pCompoundTag);
			pCompoundTag.putBoolean("Mob", this.hasPlacedSpawner);
		}

		public static final MonsterThrone createPiece(final StructurePieceAccessor pPieces, final int pX, final int pY, final int pZ, final Direction pDirection, final int pGenDepth) {
			final BoundingBox box = BoundingBox.orientBox(pX, pY, pZ, -2, 0, 0, 7, 8, 9, pDirection);
			return isOkBox(box) && pPieces.findCollisionPiece(box) == null ? new MonsterThrone(pGenDepth, box, pDirection) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel pLevel, final StructureManager pManager, final ChunkGenerator pGenerator, final RandomSource pRandom, final BoundingBox pBoundingBox, final ChunkPos pChunkPos, final BlockPos pPos) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = EEBlocks.BLAZE_LANTERN.get().defaultBlockState();
			final BlockState block4 = EEBlocks.BLAZE_LANTERN.get().defaultBlockState().setValue(BlazeLanternBlock.HANGING, Boolean.valueOf(true));
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair2 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);	
			final BlockState stair5 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair6 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState fence1 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState();
			final BlockState fence2 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence3 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence4 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true));
			final BlockState fence5 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence6 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true));
			final BlockState fence7 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true));
			final BlockState fence8 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence9 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence10 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true));
			final BlockState fence11 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence12 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			//air
			this.generateAirBox(pLevel, pBoundingBox, 0, 2, 0, 6, 7, 7);
			//stairs
			this.generateBox(pLevel, pBoundingBox, 1, 0, 0, 5, 1, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 0, 0, 3, 1, 4, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 2, 0, 5, 4, 1, 6, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 1, 2, 1, 5, 2, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 2, 1, 3, 2, 4, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 2, 2, 5, 4, 2, 6, block2, block2, false);
			this.placeBlock(pLevel, stair1, 2, 2, 0, pBoundingBox);
			this.placeBlock(pLevel, stair2, 3, 2, 0, pBoundingBox);
			this.placeBlock(pLevel, stair1, 4, 2, 0, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 1, 3, 2, 5, 3, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 3, 2, 3, 3, 4, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 2, 3, 5, 4, 3, 6, block2, block2, false);
			this.placeBlock(pLevel, stair1, 2, 3, 1, pBoundingBox);
			this.placeBlock(pLevel, stair2, 3, 3, 1, pBoundingBox);
			this.placeBlock(pLevel, stair1, 4, 3, 1, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 1, 4, 3, 5, 4, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 4, 3, 3, 4, 4, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 2, 4, 5, 4, 4, 6, block2, block2, false);
			this.placeBlock(pLevel, stair1, 2, 4, 2, pBoundingBox);
			this.placeBlock(pLevel, stair2, 3, 4, 2, pBoundingBox);
			this.placeBlock(pLevel, stair1, 4, 4, 2, pBoundingBox);
			//rails
			this.generateBox(pLevel, pBoundingBox, 1, 2, 0, 1, 4, 2, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 5, 2, 0, 5, 4, 2, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 1, 4, 0, 1, 4, 0, stair1, stair1, false);
			this.generateBox(pLevel, pBoundingBox, 5, 4, 0, 5, 4, 0, stair1, stair1, false);
			this.generateBox(pLevel, pBoundingBox, 1, 5, 3, 1, 5, 3, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 5, 5, 3, 5, 5, 3, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 1, 6, 3, 1, 6, 3, fence1, fence1, false);
			this.generateBox(pLevel, pBoundingBox, 5, 6, 3, 5, 6, 3, fence1, fence1, false);
			this.generateBox(pLevel, pBoundingBox, 1, 7, 3, 1, 7, 3, block3, block3, false);
			this.generateBox(pLevel, pBoundingBox, 5, 7, 3, 5, 7, 3, block3, block3, false);
			this.generateBox(pLevel, pBoundingBox, 1, 5, 2, 1, 5, 2, stair1, stair1, false);
			this.generateBox(pLevel, pBoundingBox, 5, 5, 2, 5, 5, 2, stair1, stair1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 5, 3, 0, 5, 8, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 5, 3, 6, 5, 8, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 1, 5, 8, 5, 5, 8, block1, block1, false);
			//back
			this.placeBlock(pLevel, fence10, 1, 6, 3, pBoundingBox);
			this.placeBlock(pLevel, fence11, 5, 6, 3, pBoundingBox);
			this.placeBlock(pLevel, fence6, 0, 6, 3, pBoundingBox);
			this.placeBlock(pLevel, fence7, 6, 6, 3, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 0, 6, 4, 0, 6, 7, fence3, fence3, false);
			this.generateBox(pLevel, pBoundingBox, 6, 6, 4, 6, 6, 7, fence3, fence3, false);
			this.placeBlock(pLevel, fence8, 0, 6, 8, pBoundingBox);
			this.placeBlock(pLevel, fence9, 6, 6, 8, pBoundingBox);
			this.placeBlock(pLevel, fence12, 1, 6, 8, pBoundingBox);
			this.placeBlock(pLevel, fence2, 2, 6, 8, pBoundingBox);
			this.placeBlock(pLevel, fence12, 3, 6, 8, pBoundingBox);
			this.placeBlock(pLevel, fence2, 4, 6, 8, pBoundingBox);
			this.placeBlock(pLevel, fence12, 5, 6, 8, pBoundingBox);
			this.placeBlock(pLevel, fence11, 1, 7, 8, pBoundingBox);
			this.placeBlock(pLevel, fence2, 2, 7, 8, pBoundingBox);
			this.placeBlock(pLevel, fence12, 3, 7, 8, pBoundingBox);
			this.placeBlock(pLevel, fence2, 4, 7, 8, pBoundingBox);
			this.placeBlock(pLevel, fence4, 2, 7, 8, pBoundingBox);
			this.placeBlock(pLevel, block4, 3, 7, 8, pBoundingBox);
			this.placeBlock(pLevel, fence5, 4, 7, 8, pBoundingBox);
			this.placeBlock(pLevel, fence10, 5, 7, 8, pBoundingBox);
			this.placeBlock(pLevel, fence5, 2, 8, 8, pBoundingBox);
			this.placeBlock(pLevel, fence12, 3, 8, 8, pBoundingBox);
			this.placeBlock(pLevel, fence4, 4, 8, 8, pBoundingBox);
			//spawner
			if (!this.hasPlacedSpawner) {
				final BlockPos blockPos = new BlockPos(this.getWorldX(3, 5), this.getWorldY(5), this.getWorldZ(3, 5));
				if (pBoundingBox.isInside(blockPos)) {
					this.hasPlacedSpawner = true;
					pLevel.setBlock(blockPos, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = pLevel.getBlockEntity(blockPos);
					if (spawner instanceof SpawnerBlockEntity spawnerBlockEntity)
						spawnerBlockEntity.setEntityId(EntityType.BLAZE, pRandom);
				}
			}
			//bottom
			this.generateBox(pLevel, pBoundingBox, 1, -1, 0, 5, -1, 0, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 1, -1, 1, 5, -1, 1, stair6, stair6, false);
			this.placeBlock(pLevel, stair5, 1, -1, 2, pBoundingBox);
			this.placeBlock(pLevel, stair5, 5, -1, 2, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 1, -1, 3, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 1, -1, 7, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 5, -1, 3, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 5, -1, 7, pBoundingBox);
			this.placeBlock(pLevel, stair3, 4, -1, 3, pBoundingBox);
			this.placeBlock(pLevel, stair3, 4, -1, 7, pBoundingBox);
			this.placeBlock(pLevel, stair4, 2, -1, 3, pBoundingBox);
			this.placeBlock(pLevel, stair4, 2, -1, 7, pBoundingBox);
			this.placeBlock(pLevel, stair5, 1, -1, 6, pBoundingBox);
			this.placeBlock(pLevel, stair5, 5, -1, 6, pBoundingBox);
			this.placeBlock(pLevel, stair6, 1, -1, 4, pBoundingBox);
			this.placeBlock(pLevel, stair6, 5, -1, 4, pBoundingBox);
		}
	}

	public static class GrandMonsterThrone extends InfernalNetherBridgePiece implements TeleportDestinationPiece {
		private final boolean[] hasPlacedSpawner = new boolean[5];
		private final boolean[] isNeedingChest = new boolean[7];

		public GrandMonsterThrone(final int pGenDepth, final BoundingBox pBoundingBox, final Direction pDirection) {
			super(EEStructurePieceTypes.INF_GRAND_MONSTER_THRONE.get(), pGenDepth, pBoundingBox);
			this.setOrientation(pDirection);
			for (int i = 0; i < 5; i++)
				this.hasPlacedSpawner[i] = false;
			for (int i = 0; i < 7; i++)
				this.isNeedingChest[i] = true;
		}

		protected GrandMonsterThrone(final int pX, final int pZ, final Direction pDirection) {
			super(EEStructurePieceTypes.INF_GRAND_MONSTER_THRONE.get(), 0, StructurePiece.makeBoundingBox(pX, 64, pZ, pDirection, 15, 13, 23));
			this.setOrientation(pDirection);
			for (int i = 0; i < 5; i++)
				this.hasPlacedSpawner[i] = false;
			for (int i = 0; i < 7; i++)
				this.isNeedingChest[i] = true;
		}

		protected GrandMonsterThrone(final StructurePieceType pieceTypeIn, final CompoundTag pCompoundTag) {
			super(pieceTypeIn, pCompoundTag);
			for (int i = 0; i < 5; i++)
				this.hasPlacedSpawner[i] = pCompoundTag.getBoolean("Mob" + String.valueOf(i));
			for (int i = 0; i < 7; i++)
				this.isNeedingChest[i] = pCompoundTag.getBoolean("Chest" + String.valueOf(i));
		}

		public GrandMonsterThrone(final CompoundTag pCompoundTag) {
			this(EEStructurePieceTypes.INF_GRAND_MONSTER_THRONE.get(), pCompoundTag);
		}

		@Override
		public final void addChildren(final StructurePiece pPiece, final StructurePieceAccessor pPieces, final RandomSource pRandom) {
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 0);
			super.addChildren(pPiece, pPieces, pRandom);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource pRandom) {
			return List.of(new ChildInfo(Direction.NORTH, 6, 1, false, EndType.THICK));
		}

		@Override
		protected final List<Class<? extends InfernalNetherBridgePiece>> getUnacceptableChildren() {
			return List.of(RoomCrossing.class, CastleEntrance.class, StairsRoom.class, MonsterThrone.class);
		}

		@Override
		protected final void addAdditionalSaveData(final StructurePieceSerializationContext pContext, final CompoundTag pCompoundTag) {
			super.addAdditionalSaveData(pContext, pCompoundTag);
			for (int i = 0; i < 5; i++)
				pCompoundTag.putBoolean("Mob" + String.valueOf(i), this.hasPlacedSpawner[i]);
			for (int i = 0; i < 7; i++)
				pCompoundTag.putBoolean("Chest" + String.valueOf(i), this.isNeedingChest[i]);
		}

		public static final GrandMonsterThrone createPiece(final StructurePieceAccessor pPieces, final int pX, final int pY, final int pZ, final Direction pDirection, final int pGenDepth) {
			final BoundingBox box = BoundingBox.orientBox(pX, pY, pZ, -6, -1, 0, 15, 13, 23, pDirection);
			return isOkBox(box) && pPieces.findCollisionPiece(box) == null ? new GrandMonsterThrone(pGenDepth, box, pDirection) : null;
		}

		@Override
		public final BlockPos getDestination() {
			return this.getWorldPos(7, 6, 8);
		}

		@Override
		public final void postProcess(final WorldGenLevel pLevel, final StructureManager pManager, final ChunkGenerator pGenerator, final RandomSource pRandom, final BoundingBox pBoundingBox, final ChunkPos pChunkPos, final BlockPos pPos) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = EEBlocks.BLAZE_LANTERN.get().defaultBlockState();
			final BlockState block4 = EEBlocks.ANCIENT_NETHER_ALTAR.get().defaultBlockState().setValue(AncientNetherAltarBlock.FACING, Direction.NORTH);
			final BlockState block5 = Blocks.LANTERN.defaultBlockState().setValue(LanternBlock.HANGING, Boolean.valueOf(true));
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST);	
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair5 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST);
			final BlockState stair6 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST);
			final BlockState stair7 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair8 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair9 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair10 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);	
			final BlockState stair11 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair12 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair13 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState fence1 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState();
			final BlockState fence2 = Blocks.NETHER_BRICK_FENCE.defaultBlockState();
			final BlockState fence3 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence4 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true));
			final BlockState fence5 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true));
			final BlockState fence6 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true));
			final BlockState fence7 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence8 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true));
			final BlockState wall1 = Blocks.RED_NETHER_BRICK_WALL.defaultBlockState();
			final BlockState wall2 = Blocks.NETHER_BRICK_WALL.defaultBlockState();
			final BlockState slab1 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState();
			//air
			this.generateAirBox(pLevel, pBoundingBox, 6, 3, 20, 8, 7, 22);
			this.generateAirBox(pLevel, pBoundingBox, 6, 4, 18, 8, 8, 20);
			this.generateAirBox(pLevel, pBoundingBox, 6, 5, 15, 8, 9, 18);
			this.generateAirBox(pLevel, pBoundingBox, 1, 5, 6, 13, 13, 10);
			this.generateAirBox(pLevel, pBoundingBox, 2, 5, 4, 12, 13, 12);
			this.generateAirBox(pLevel, pBoundingBox, 3, 5, 3, 11, 13, 13);
			this.generateAirBox(pLevel, pBoundingBox, 4, 5, 2, 10, 13, 14);
			//stairs
			this.placeBlock(pLevel, stair12, 5, 0, 22, pBoundingBox);
			this.placeBlock(pLevel, stair12, 6, 0, 22, pBoundingBox);
			this.placeBlock(pLevel, stair13, 7, 0, 22, pBoundingBox);
			this.placeBlock(pLevel, stair12, 8, 0, 22, pBoundingBox);
			this.placeBlock(pLevel, stair12, 9, 0, 22, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 5, 1, 22, 9, 1, 22, block1, block1, false);
			this.placeBlock(pLevel, stair12, 5, 1, 21, pBoundingBox);
			this.placeBlock(pLevel, stair12, 6, 1, 21, pBoundingBox);
			this.placeBlock(pLevel, stair13, 7, 1, 21, pBoundingBox);
			this.placeBlock(pLevel, stair12, 8, 1, 21, pBoundingBox);
			this.placeBlock(pLevel, stair12, 9, 1, 21, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 5, 2, 20, 6, 2, 22, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 20, 7, 2, 22, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 8, 2, 20, 9, 2, 22, block1, block1, false);
			this.placeBlock(pLevel, stair3, 6, 3, 20, pBoundingBox);
			this.placeBlock(pLevel, stair7, 7, 3, 20, pBoundingBox);
			this.placeBlock(pLevel, stair3, 8, 3, 20, pBoundingBox);
			this.placeBlock(pLevel, stair12, 5, 2, 19, pBoundingBox);
			this.placeBlock(pLevel, stair12, 6, 2, 19, pBoundingBox);
			this.placeBlock(pLevel, stair13, 7, 2, 19, pBoundingBox);
			this.placeBlock(pLevel, stair12, 8, 2, 19, pBoundingBox);
			this.placeBlock(pLevel, stair12, 9, 2, 19, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 5, 3, 18, 6, 3, 19, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 3, 18, 7, 3, 19, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 8, 3, 18, 9, 3, 19, block1, block1, false);
			this.placeBlock(pLevel, stair3, 6, 4, 18, pBoundingBox);
			this.placeBlock(pLevel, stair7, 7, 4, 18, pBoundingBox);
			this.placeBlock(pLevel, stair3, 8, 4, 18, pBoundingBox);
			this.placeBlock(pLevel, stair12, 5, 3, 17, pBoundingBox);
			this.placeBlock(pLevel, stair12, 6, 3, 17, pBoundingBox);
			this.placeBlock(pLevel, stair13, 7, 3, 17, pBoundingBox);
			this.placeBlock(pLevel, stair12, 8, 3, 17, pBoundingBox);
			this.placeBlock(pLevel, stair12, 9, 3, 17, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 5, 4, 15, 6, 4, 17, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 4, 15, 7, 4, 17, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 8, 4, 15, 9, 4, 17, block1, block1, false);
			//rails
			this.generateBox(pLevel, pBoundingBox, 5, 3, 20, 5, 3, 22, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 9, 3, 20, 9, 3, 22, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 5, 4, 18, 5, 4, 20, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 9, 4, 18, 9, 4, 20, block1, block1, false);
			this.placeBlock(pLevel, stair3, 5, 4, 21, pBoundingBox);
			this.placeBlock(pLevel, stair3, 9, 4, 21, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 5, 5, 15, 5, 5, 18, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 9, 5, 15, 9, 5, 18, block1, block1, false);
			this.placeBlock(pLevel, stair3, 5, 5, 19, pBoundingBox);
			this.placeBlock(pLevel, stair3, 9, 5, 19, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 4, 4, 15, 4, 5, 15, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 10, 4, 15, 10, 5, 15, block1, block1, false);
			//center rails
			this.generateBox(pLevel, pBoundingBox, 3, 4, 14, 3, 5, 14, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 11, 4, 14, 11, 5, 14, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 2, 4, 13, 2, 5, 13, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 12, 4, 13, 12, 5, 13, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 1, 4, 11, 1, 5, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 13, 4, 11, 13, 5, 12, block1, block1, false);
			this.placeBlock(pLevel, wall2, 1, 6, 11, pBoundingBox);
			this.placeBlock(pLevel, fence2, 1, 7, 11, pBoundingBox);
			if (!this.hasPlacedSpawner[0]) {
				final BlockPos blockPos1 = new BlockPos(this.getWorldX(1, 11), this.getWorldY(8), this.getWorldZ(1, 11));
				if (pBoundingBox.isInside(blockPos1)) {
					this.hasPlacedSpawner[0] = true;
					pLevel.setBlock(blockPos1, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = pLevel.getBlockEntity(blockPos1);
					if (spawner instanceof SpawnerBlockEntity spawnerBlockEntity)
						spawnerBlockEntity.setEntityId(EntityType.WITHER_SKELETON, pRandom);
				}
			}
			this.placeBlock(pLevel, wall2, 13, 6, 11, pBoundingBox);
			this.placeBlock(pLevel, fence2, 13, 7, 11, pBoundingBox);
			if (!this.hasPlacedSpawner[1]) {
				final BlockPos blockPos2 = new BlockPos(this.getWorldX(13, 11), this.getWorldY(8), this.getWorldZ(13, 11));
				if (pBoundingBox.isInside(blockPos2)) {
					this.hasPlacedSpawner[1] = true;
					pLevel.setBlock(blockPos2, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = pLevel.getBlockEntity(blockPos2);
					if (spawner instanceof SpawnerBlockEntity spawnerBlockEntity)
						spawnerBlockEntity.setEntityId(EntityType.WITHER_SKELETON, pRandom);
				}
			}
			this.generateBox(pLevel, pBoundingBox, 0, 4, 6, 0, 5, 10, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 14, 4, 6, 14, 5, 10, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 1, 4, 4, 1, 5, 5, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 13, 4, 4, 13, 5, 5, block1, block1, false);
			this.placeBlock(pLevel, wall2, 1, 6, 5, pBoundingBox);
			this.placeBlock(pLevel, fence2, 1, 7, 5, pBoundingBox);
			if (!this.hasPlacedSpawner[2]) {
				final BlockPos blockPos3 = new BlockPos(this.getWorldX(1, 5), this.getWorldY(8), this.getWorldZ(1, 5));
				if (pBoundingBox.isInside(blockPos3)) {
					this.hasPlacedSpawner[2] = true;
					pLevel.setBlock(blockPos3, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = pLevel.getBlockEntity(blockPos3);
					if (spawner instanceof SpawnerBlockEntity spawnerBlockEntity)
						spawnerBlockEntity.setEntityId(EntityType.BLAZE, pRandom);
				}
			}
			this.placeBlock(pLevel, wall2, 13, 6, 5, pBoundingBox);
			this.placeBlock(pLevel, fence2, 13, 7, 5, pBoundingBox);
			if (!this.hasPlacedSpawner[3]) {
				final BlockPos blockPos4 = new BlockPos(this.getWorldX(13, 5), this.getWorldY(8), this.getWorldZ(13, 5));
				if (pBoundingBox.isInside(blockPos4)) {
					this.hasPlacedSpawner[3] = true;
					pLevel.setBlock(blockPos4, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = pLevel.getBlockEntity(blockPos4);
					if (spawner instanceof SpawnerBlockEntity spawnerBlockEntity)
						spawnerBlockEntity.setEntityId(EntityType.BLAZE, pRandom);
				}
			}
			this.generateBox(pLevel, pBoundingBox, 2, 4, 3, 2, 5, 3, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 12, 4, 3, 12, 5, 3, block1, block1, false);
			this.placeBlock(pLevel, fence1, 2, 6, 3, pBoundingBox);
			this.placeBlock(pLevel, fence1, 12, 6, 3, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 3, 4, 2, 3, 5, 2, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 11, 4, 2, 11, 5, 2, block1, block1, false);
			this.placeBlock(pLevel, wall1, 3, 6, 2, pBoundingBox);
			this.placeBlock(pLevel, wall1, 11, 6, 2, pBoundingBox);
			this.placeBlock(pLevel, fence1, 3, 7, 2, pBoundingBox);
			this.placeBlock(pLevel, fence1, 11, 7, 2, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 4, 4, 1, 10, 5, 1, block1, block1, false);
			this.placeBlock(pLevel, wall2, 4, 6, 1, pBoundingBox);
			this.placeBlock(pLevel, fence1, 5, 6, 1, pBoundingBox);
			this.placeBlock(pLevel, wall2, 6, 6, 1, pBoundingBox);
			this.placeBlock(pLevel, fence1, 7, 6, 1, pBoundingBox);
			this.placeBlock(pLevel, wall2, 8, 6, 1, pBoundingBox);
			this.placeBlock(pLevel, fence1, 9, 6, 1, pBoundingBox);
			this.placeBlock(pLevel, wall2, 10, 6, 1, pBoundingBox);
			this.placeBlock(pLevel, fence2, 4, 7, 1, pBoundingBox);
			this.placeBlock(pLevel, wall1, 5, 7, 1, pBoundingBox);
			this.placeBlock(pLevel, fence3, 6, 7, 1, pBoundingBox);
			if (!this.hasPlacedSpawner[4]) {
				final BlockPos blockPos5 = new BlockPos(this.getWorldX(7, 1), this.getWorldY(7), this.getWorldZ(7, 1));
				if (pBoundingBox.isInside(blockPos5)) {
					this.hasPlacedSpawner[4] = true;
					pLevel.setBlock(blockPos5, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = pLevel.getBlockEntity(blockPos5);
					if (spawner instanceof SpawnerBlockEntity spawnerBlockEntity)
						spawnerBlockEntity.setEntityId(EntityType.BLAZE, pRandom);
				}
			}
			this.placeBlock(pLevel, fence4, 8, 7, 1, pBoundingBox);
			this.placeBlock(pLevel, wall1, 9, 7, 1, pBoundingBox);
			this.placeBlock(pLevel, fence2, 10, 7, 1, pBoundingBox);
			this.placeBlock(pLevel, fence4, 4, 8, 1, pBoundingBox);
			this.placeBlock(pLevel, fence5, 5, 8, 1, pBoundingBox);
			this.placeBlock(pLevel, fence3, 6, 8, 1, pBoundingBox);
			this.placeBlock(pLevel, wall1, 7, 8, 1, pBoundingBox);
			this.placeBlock(pLevel, fence4, 8, 8, 1, pBoundingBox);
			this.placeBlock(pLevel, fence5, 9, 8, 1, pBoundingBox);
			this.placeBlock(pLevel, fence3, 10, 8, 1, pBoundingBox);
			this.placeBlock(pLevel, fence7, 5, 9, 1, pBoundingBox);
			this.placeBlock(pLevel, fence6, 6, 9, 1, pBoundingBox);
			this.placeBlock(pLevel, fence5, 7, 9, 1, pBoundingBox);
			this.placeBlock(pLevel, fence6, 8, 9, 1, pBoundingBox);
			this.placeBlock(pLevel, fence8, 9, 9, 1, pBoundingBox);
			this.placeBlock(pLevel, fence1, 7, 10, 1, pBoundingBox);
			//center
			this.generateBox(pLevel, pBoundingBox, 4, 4, 14, 6, 4, 14, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 4, 14, 7, 4, 14, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 8, 4, 14, 10, 4, 14, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 4, 13, 4, 4, 13, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 5, 4, 13, 9, 4, 13, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 10, 4, 13, 11, 4, 13, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 2, 4, 12, 3, 4, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 4, 4, 12, 5, 4, 12, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 6, 4, 12, 6, 4, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 4, 12, 7, 4, 12, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 8, 4, 12, 8, 4, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 9, 4, 12, 10, 4, 12, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 11, 4, 12, 12, 4, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 2, 4, 11, 2, 4, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 4, 11, 11, 4, 11, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 12, 4, 11, 12, 4, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 1, 4, 10, 2, 4, 10, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 4, 10, 4, 4, 10, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 5, 4, 10, 6, 4, 10, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 4, 10, 7, 4, 10, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 8, 4, 10, 9, 4, 10, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 10, 4, 10, 11, 4, 10, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 12, 4, 10, 13, 4, 10, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 1, 4, 9, 1, 4, 9, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 2, 4, 9, 4, 4, 9, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 5, 4, 9, 5, 4, 9, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 4, 9, 8, 4, 9, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 9, 4, 9, 9, 4, 9, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 10, 4, 9, 12, 4, 9, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 13, 4, 9, 13, 4, 9, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 1, 4, 8, 1, 4, 8, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 2, 4, 8, 2, 4, 8, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 3, 4, 8, 3, 4, 8, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 4, 4, 8, 6, 4, 8, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 4, 8, 7, 4, 8, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 8, 4, 8, 10, 4, 8, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 11, 4, 8, 11, 4, 8, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 12, 4, 8, 12, 4, 8, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 13, 4, 8, 13, 4, 8, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 1, 4, 7, 1, 4, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 2, 4, 7, 4, 4, 7, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 5, 4, 7, 5, 4, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 4, 7, 8, 4, 7, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 9, 4, 7, 9, 4, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 10, 4, 7, 12, 4, 7, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 13, 4, 7, 13, 4, 7, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 1, 4, 6, 2, 4, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 4, 6, 4, 4, 6, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 5, 4, 6, 6, 4, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 4, 6, 7, 4, 6, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 8, 4, 6, 9, 4, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 10, 4, 6, 11, 4, 6, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 12, 4, 6, 13, 4, 6, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 2, 4, 5, 2, 4, 5, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 4, 5, 11, 4, 5, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 12, 4, 5, 12, 4, 5, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 2, 4, 4, 3, 4, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 4, 4, 4, 5, 4, 4, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 6, 4, 4, 6, 4, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 4, 4, 7, 4, 4, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 8, 4, 4, 8, 4, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 9, 4, 4, 10, 4, 4, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 11, 4, 4, 12, 4, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 4, 3, 4, 4, 3, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 5, 4, 3, 9, 4, 3, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 10, 4, 3, 11, 4, 3, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 4, 4, 2, 10, 4, 2, block1, block1, false);
			this.placeBlock(pLevel, wall1, 3, 5, 8, pBoundingBox);
			this.placeBlock(pLevel, fence1, 3, 6, 8, pBoundingBox);
			this.placeBlock(pLevel, block3, 3, 7, 8, pBoundingBox);
			this.placeBlock(pLevel, wall1, 11, 5, 8, pBoundingBox);
			this.placeBlock(pLevel, fence1, 11, 6, 8, pBoundingBox);
			this.placeBlock(pLevel, block3, 11, 7, 8, pBoundingBox);
			this.placeBlock(pLevel, slab1, 5, 5, 10, pBoundingBox);
			this.placeBlock(pLevel, stair3, 6, 5, 10, pBoundingBox);
			this.placeBlock(pLevel, stair7, 7, 5, 10, pBoundingBox);
			this.placeBlock(pLevel, stair3, 8, 5, 10, pBoundingBox);
			this.placeBlock(pLevel, slab1, 9, 5, 10, pBoundingBox);
			this.placeBlock(pLevel, stair1, 5, 5, 9, pBoundingBox);
			this.placeBlock(pLevel, block2, 6, 5, 9, pBoundingBox);
			this.placeBlock(pLevel, block2, 7, 5, 9, pBoundingBox);
			this.placeBlock(pLevel, block2, 8, 5, 9, pBoundingBox);
			this.placeBlock(pLevel, stair2, 9, 5, 9, pBoundingBox);
			this.placeBlock(pLevel, stair5, 5, 5, 8, pBoundingBox);
			this.placeBlock(pLevel, block2, 6, 5, 8, pBoundingBox);
			this.placeBlock(pLevel, block1, 7, 5, 8, pBoundingBox);
			this.placeBlock(pLevel, block4, 7, 6, 8, pBoundingBox);
			this.placeBlock(pLevel, block2, 8, 5, 8, pBoundingBox);
			this.placeBlock(pLevel, stair6, 9, 5, 8, pBoundingBox);
			this.placeBlock(pLevel, stair1, 5, 5, 7, pBoundingBox);
			this.placeBlock(pLevel, block2, 6, 5, 7, pBoundingBox);
			this.placeBlock(pLevel, block2, 7, 5, 7, pBoundingBox);
			this.placeBlock(pLevel, block2, 8, 5, 7, pBoundingBox);
			this.placeBlock(pLevel, stair2, 9, 5, 7, pBoundingBox);
			this.placeBlock(pLevel, slab1, 5, 5, 6, pBoundingBox);
			this.placeBlock(pLevel, stair4, 6, 5, 6, pBoundingBox);
			this.placeBlock(pLevel, stair8, 7, 5, 6, pBoundingBox);
			this.placeBlock(pLevel, stair4, 8, 5, 6, pBoundingBox);
			this.placeBlock(pLevel, slab1, 9, 5, 6, pBoundingBox);
			//chests
			if (this.isNeedingChest[0] && pBoundingBox.isInside(new BlockPos(this.getWorldX(5, 2), this.getWorldY(5), this.getWorldZ(5, 2)))) {
				this.isNeedingChest[0] = false;
				this.createChest(pLevel, pBoundingBox, pRandom, 5, 5, 2, EEChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE_GRAND_THRONE);
			}
			if (this.isNeedingChest[1] && pBoundingBox.isInside(new BlockPos(this.getWorldX(7, 2), this.getWorldY(5), this.getWorldZ(7, 2)))) {
				this.isNeedingChest[1] = false;
				this.createChest(pLevel, pBoundingBox, pRandom, 7, 5, 2, EEChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE_GRAND_THRONE);
			}
			if (this.isNeedingChest[2] && pBoundingBox.isInside(new BlockPos(this.getWorldX(9, 2), this.getWorldY(5), this.getWorldZ(9, 2)))) {
				this.isNeedingChest[2] = false;
				this.createChest(pLevel, pBoundingBox, pRandom, 9, 5, 2, EEChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE_GRAND_THRONE);
			}
			if (this.isNeedingChest[3] && pBoundingBox.isInside(new BlockPos(this.getWorldX(1, 9), this.getWorldY(5), this.getWorldZ(1, 9)))) {
				this.isNeedingChest[3] = false;
				this.createChest(pLevel, pBoundingBox, pRandom, 1, 5, 9, EEChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE_THRONE);
			}
			if (this.isNeedingChest[4] && pBoundingBox.isInside(new BlockPos(this.getWorldX(1, 7), this.getWorldY(5), this.getWorldZ(1, 7)))) {
				this.isNeedingChest[4] = false;
				this.createChest(pLevel, pBoundingBox, pRandom, 1, 5, 7, EEChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE_THRONE);
			}
			if (this.isNeedingChest[5] && pBoundingBox.isInside(new BlockPos(this.getWorldX(13, 9), this.getWorldY(5), this.getWorldZ(13, 9)))) {
				this.isNeedingChest[5] = false;
				this.createChest(pLevel, pBoundingBox, pRandom, 13, 5, 9, EEChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE_THRONE);
			}
			if (this.isNeedingChest[6] && pBoundingBox.isInside(new BlockPos(this.getWorldX(13, 7), this.getWorldY(5), this.getWorldZ(13, 7)))) {
				this.isNeedingChest[6] = false;
				this.createChest(pLevel, pBoundingBox, pRandom, 13, 5, 7, EEChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE_THRONE);
			}
			//bottom pillars
			this.fillColumnDown(pLevel, block1, 5, 3, 15, pBoundingBox);
			this.placeBlock(pLevel, stair12, 5, 3, 14, pBoundingBox);
			this.placeBlock(pLevel, stair9, 4, 3, 15, pBoundingBox);
			this.placeBlock(pLevel, stair10, 6, 3, 15, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 9, 3, 15, pBoundingBox);
			this.placeBlock(pLevel, stair12, 9, 3, 14, pBoundingBox);
			this.placeBlock(pLevel, stair9, 8, 3, 15, pBoundingBox);
			this.placeBlock(pLevel, stair10, 10, 3, 15, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 2, 3, 12, pBoundingBox);
			this.placeBlock(pLevel, stair12, 2, 3, 11, pBoundingBox);
			this.placeBlock(pLevel, stair11, 2, 3, 13, pBoundingBox);
			this.placeBlock(pLevel, stair9, 1, 3, 12, pBoundingBox);
			this.placeBlock(pLevel, stair10, 3, 3, 12, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 12, 3, 12, pBoundingBox);
			this.placeBlock(pLevel, stair12, 12, 3, 11, pBoundingBox);
			this.placeBlock(pLevel, stair11, 12, 3, 13, pBoundingBox);
			this.placeBlock(pLevel, stair9, 11, 3, 12, pBoundingBox);
			this.placeBlock(pLevel, stair10, 13, 3, 12, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 1, 3, 8, pBoundingBox);
			this.placeBlock(pLevel, stair12, 1, 3, 7, pBoundingBox);
			this.placeBlock(pLevel, stair11, 1, 3, 9, pBoundingBox);
			this.placeBlock(pLevel, stair9, 0, 3, 8, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 13, 3, 8, pBoundingBox);
			this.placeBlock(pLevel, stair12, 13, 3, 7, pBoundingBox);
			this.placeBlock(pLevel, stair11, 13, 3, 9, pBoundingBox);
			this.placeBlock(pLevel, stair10, 14, 3, 8, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 2, 3, 4, pBoundingBox);
			this.placeBlock(pLevel, stair12, 2, 3, 3, pBoundingBox);
			this.placeBlock(pLevel, stair11, 2, 3, 5, pBoundingBox);
			this.placeBlock(pLevel, stair9, 1, 3, 4, pBoundingBox);
			this.placeBlock(pLevel, stair10, 3, 3, 4, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 12, 3, 4, pBoundingBox);
			this.placeBlock(pLevel, stair12, 12, 3, 3, pBoundingBox);
			this.placeBlock(pLevel, stair11, 12, 3, 5, pBoundingBox);
			this.placeBlock(pLevel, stair9, 11, 3, 4, pBoundingBox);
			this.placeBlock(pLevel, stair10, 13, 3, 4, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 7, 3, 2, pBoundingBox);
			this.placeBlock(pLevel, stair12, 7, 3, 1, pBoundingBox);
			this.placeBlock(pLevel, stair9, 6, 3, 2, pBoundingBox);
			this.placeBlock(pLevel, stair10, 8, 3, 2, pBoundingBox);
			//bottom
			this.placeBlock(pLevel, fence1, 6, 3, 12, pBoundingBox);
			this.placeBlock(pLevel, block5, 6, 2, 12, pBoundingBox);
			this.placeBlock(pLevel, fence1, 8, 3, 12, pBoundingBox);
			this.placeBlock(pLevel, block5, 8, 2, 12, pBoundingBox);
			this.placeBlock(pLevel, fence1, 3, 3, 8, pBoundingBox);
			this.placeBlock(pLevel, block5, 3, 2, 8, pBoundingBox);
			this.placeBlock(pLevel, wall1, 7, 3, 8, pBoundingBox);
			this.placeBlock(pLevel, fence1, 7, 2, 8, pBoundingBox);
			this.placeBlock(pLevel, block5, 7, 1, 8, pBoundingBox);
			this.placeBlock(pLevel, fence1, 11, 3, 8, pBoundingBox);
			this.placeBlock(pLevel, block5, 11, 2, 8, pBoundingBox);
			this.placeBlock(pLevel, fence1, 6, 3, 4, pBoundingBox);
			this.placeBlock(pLevel, block5, 6, 2, 4, pBoundingBox);
			this.placeBlock(pLevel, fence1, 8, 3, 4, pBoundingBox);
			this.placeBlock(pLevel, block5, 8, 2, 4, pBoundingBox);
		}
	}

	public static class CastleSmallCorridorLeftTurnPiece extends InfernalNetherBridgePiece {
		private boolean isNeedingChest;

		public CastleSmallCorridorLeftTurnPiece(final int pGenDepth, final RandomSource pRandom, final BoundingBox pBoundingBox, final Direction pDirection) {
			super(EEStructurePieceTypes.INF_CASTLE_SMALL_CORRIDOR_LEFT_TURN.get(), pGenDepth, pBoundingBox);
			this.setOrientation(pDirection);
			this.isNeedingChest = pRandom.nextInt(3) == 0;
		}

		public CastleSmallCorridorLeftTurnPiece(final CompoundTag pCompoundTag) {
			super(EEStructurePieceTypes.INF_CASTLE_SMALL_CORRIDOR_LEFT_TURN.get(), pCompoundTag);
			this.isNeedingChest = pCompoundTag.getBoolean("Chest");
		}

		@Override
		protected final void addAdditionalSaveData(final StructurePieceSerializationContext pContext, final CompoundTag pCompoundTag) {
			super.addAdditionalSaveData(pContext, pCompoundTag);
			pCompoundTag.putBoolean("Chest", this.isNeedingChest);
		}

		@Override
		public final void addChildren(final StructurePiece pPiece, final StructurePieceAccessor pPieces, final RandomSource pRandom) {
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 0);
			super.addChildren(pPiece, pPieces, pRandom);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource pRandom) {
			return List.of(new ChildInfo(Direction.WEST, 0, 1, true, EndType.DEFAULT));
		}

		public static final CastleSmallCorridorLeftTurnPiece createPiece(final StructurePieceAccessor pPieces, final RandomSource pRandom, final int pX, final int pY, final int pZ, final Direction pDirection, final int pGenDepth) {
			final BoundingBox box = BoundingBox.orientBox(pX, pY, pZ, -1, 0, 0, 5, 7, 5, pDirection);
			return isOkBox(box) && pPieces.findCollisionPiece(box) == null ? new CastleSmallCorridorLeftTurnPiece(pGenDepth, pRandom, box, pDirection) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel pLevel, final StructureManager pManager, final ChunkGenerator pGenerator, final RandomSource pRandom, final BoundingBox pBoundingBox, final ChunkPos pChunkPos, final BlockPos pPos) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState fence1 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence2 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState slab1 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP);
			final BlockState slab2 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM);
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair5 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair6 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			//air
			this.generateAirBox(pLevel, pBoundingBox, 0, 2, 0, 4, 5, 4);
			//floor
			this.generateBox(pLevel, pBoundingBox, 0, 0, 0, 4, 1, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 2, 0, 0, 2, 1, 2, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 0, 2, 2, 1, 2, block2, block2, false);
			//walls
			this.generateBox(pLevel, pBoundingBox, 4, 2, 0, 4, 5, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 4, 2, 1, 4, 5, 1, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 4, 3, 1, 4, 4, 1, fence2, fence2, false);
			this.generateBox(pLevel, pBoundingBox, 4, 2, 3, 4, 5, 3, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 4, 3, 3, 4, 4, 3, fence2, fence2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 0, 0, 5, 0, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 4, 3, 5, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 1, 2, 4, 1, 5, 4, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 1, 3, 4, 1, 4, 4, fence1, fence1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 2, 4, 3, 5, 4, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 3, 3, 4, 3, 4, 4, fence1, fence1, false);
			//chest
			if (this.isNeedingChest && pBoundingBox.isInside(new BlockPos(this.getWorldX(3, 3), this.getWorldY(2), this.getWorldZ(3, 3)))) {
				this.isNeedingChest = false;
				this.createChest(pLevel, pBoundingBox, pRandom, 3, 2, 3, EEChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE);
			}
			//roof
			this.generateBox(pLevel, pBoundingBox, 0, 6, 0, 4, 6, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 2, 6, 0, 2, 6, 2, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 6, 2, 2, 6, 2, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 6, 0, 0, 6, 0, slab2, slab2, false);
			this.generateBox(pLevel, pBoundingBox, 4, 6, 0, 4, 6, 4, slab2, slab2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 6, 4, 4, 6, 4, slab2, slab2, false);
			//top
			this.generateBox(pLevel, pBoundingBox, 1, 5, 1, 1, 5, 1, slab1, slab1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 5, 3, 3, 5, 3, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 5, 3, 2, 5, 3, stair1, stair1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 5, 1, 0, 5, 1, stair2, stair2, false);
			this.generateBox(pLevel, pBoundingBox, 3, 5, 0, 3, 5, 2, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 1, 5, 0, 1, 5, 0, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 1, 5, 3, 1, 5, 3, stair5, stair5, false);
			this.generateBox(pLevel, pBoundingBox, 3, 5, 1, 3, 5, 1, stair6, stair6, false);
			//bottom
			this.fillColumnDown(pLevel, block1, 4, -1, 4, pBoundingBox);
			this.placeBlock(pLevel, stair1, 4, -1, 3, pBoundingBox);
			this.placeBlock(pLevel, stair3, 3, -1, 4, pBoundingBox);
		}
	}

	public static class CastleSmallCorridorRightTurnPiece extends InfernalNetherBridgePiece {
		private boolean isNeedingChest;

		public CastleSmallCorridorRightTurnPiece(final int pGenDepth, final RandomSource pRandom, final BoundingBox pBoundingBox, final Direction pDirection) {
			super(EEStructurePieceTypes.INF_CASTLE_SMALL_CORRIDOR_RIGHT_TURN.get(), pGenDepth, pBoundingBox);
			this.setOrientation(pDirection);
			this.isNeedingChest = pRandom.nextInt(3) == 0;
		}

		public CastleSmallCorridorRightTurnPiece(final CompoundTag pCompoundTag) {
			super(EEStructurePieceTypes.INF_CASTLE_SMALL_CORRIDOR_RIGHT_TURN.get(), pCompoundTag);
			this.isNeedingChest = pCompoundTag.getBoolean("Chest");
		}

		@Override
		protected final void addAdditionalSaveData(final StructurePieceSerializationContext pContext, final CompoundTag pCompoundTag) {
			super.addAdditionalSaveData(pContext, pCompoundTag);
			pCompoundTag.putBoolean("Chest", this.isNeedingChest);
		}

		@Override
		public final void addChildren(final StructurePiece pPiece, final StructurePieceAccessor pPieces, final RandomSource pRandom) {
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 0);
			super.addChildren(pPiece, pPieces, pRandom);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource pRandom) {
			return List.of(new ChildInfo(Direction.EAST, 0, 1, true, EndType.DEFAULT));
		}

		public static final CastleSmallCorridorRightTurnPiece createPiece(final StructurePieceAccessor pPieces, final RandomSource pRandom, final int pX, final int pY, final int pZ, final Direction pDirection, final int pGenDepth) {
			final BoundingBox box = BoundingBox.orientBox(pX, pY, pZ, -1, 0, 0, 5, 7, 5, pDirection);
			return isOkBox(box) && pPieces.findCollisionPiece(box) == null ? new CastleSmallCorridorRightTurnPiece(pGenDepth, pRandom, box, pDirection) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel pLevel, final StructureManager pManager, final ChunkGenerator pGenerator, final RandomSource pRandom, final BoundingBox pBoundingBox, final ChunkPos pChunkPos, final BlockPos pPos) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState fence1 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence2 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState slab1 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP);
			final BlockState slab2 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM);
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair5 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair6 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);
			//air
			this.generateAirBox(pLevel, pBoundingBox, 0, 2, 0, 4, 5, 4);
			//floor
			this.generateBox(pLevel, pBoundingBox, 0, 0, 0, 4, 1, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 2, 0, 0, 2, 1, 2, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 2, 0, 2, 4, 1, 2, block2, block2, false);
			//walls
			this.generateBox(pLevel, pBoundingBox, 0, 2, 0, 0, 5, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 1, 0, 5, 1, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 1, 0, 4, 1, fence2, fence2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 3, 0, 5, 3, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 3, 0, 4, 3, fence2, fence2, false);
			this.generateBox(pLevel, pBoundingBox, 4, 2, 0, 4, 5, 0, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 1, 2, 4, 4, 5, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 3, 2, 4, 3, 5, 4, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 3, 3, 4, 3, 4, 4, fence1, fence1, false);
			this.generateBox(pLevel, pBoundingBox, 1, 2, 4, 1, 5, 4, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 1, 3, 4, 1, 4, 4, fence1, fence1, false);
			//chest
			if (this.isNeedingChest && pBoundingBox.isInside(new BlockPos(this.getWorldX(1, 3), this.getWorldY(2), this.getWorldZ(1, 3)))) {
				this.isNeedingChest = false;
				this.createChest(pLevel, pBoundingBox, pRandom, 1, 2, 3, EEChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE);
			}
			//roof
			this.generateBox(pLevel, pBoundingBox, 0, 6, 0, 4, 6, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 2, 6, 0, 2, 6, 2, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 2, 6, 2, 4, 6, 2, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 4, 6, 0, 4, 6, 0, slab2, slab2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 6, 0, 0, 6, 4, slab2, slab2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 6, 4, 4, 6, 4, slab2, slab2, false);
			//top
			this.generateBox(pLevel, pBoundingBox, 3, 5, 1, 3, 5, 1, slab1, slab1, false);
			this.generateBox(pLevel, pBoundingBox, 1, 5, 3, 1, 5, 3, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 2, 5, 3, 4, 5, 3, stair1, stair1, false);
			this.generateBox(pLevel, pBoundingBox, 4, 5, 1, 4, 5, 1, stair2, stair2, false);
			this.generateBox(pLevel, pBoundingBox, 1, 5, 0, 1, 5, 2, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 3, 5, 0, 3, 5, 0, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 3, 5, 3, 3, 5, 3, stair5, stair5, false);
			this.generateBox(pLevel, pBoundingBox, 1, 5, 1, 1, 5, 1, stair6, stair6, false);
			//bottom
			this.fillColumnDown(pLevel, block1, 0, -1, 4, pBoundingBox);
			this.placeBlock(pLevel, stair1, 0, -1, 3, pBoundingBox);
			this.placeBlock(pLevel, stair3, 1, -1, 4, pBoundingBox);
		}
	}

	public static class CastleCorridorStairsPiece extends InfernalNetherBridgePiece {
		public CastleCorridorStairsPiece(final int pGenDepth, final BoundingBox pBoundingBox, final Direction pDirection) {
			super(EEStructurePieceTypes.INF_CASTLE_CORRIDOR_STAIRS.get(), pGenDepth, pBoundingBox);
			this.setOrientation(pDirection);
		}

		public CastleCorridorStairsPiece(final CompoundTag pCompoundTag) {
			super(EEStructurePieceTypes.INF_CASTLE_CORRIDOR_STAIRS.get(), pCompoundTag);
		}

		@Override
		public final void addChildren(final StructurePiece pPiece, final StructurePieceAccessor pPieces, final RandomSource pRandom) {
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 0);
			super.addChildren(pPiece, pPieces, pRandom);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource pRandom) {
			return List.of(new ChildInfo(Direction.NORTH, 1, 0, true, EndType.DEFAULT));
		}

		public static final CastleCorridorStairsPiece createPiece(final StructurePieceAccessor pPieces, final int pX, final int pY, final int pZ, final Direction pDirection, final int pGenDepth) {
			final BoundingBox box = BoundingBox.orientBox(pX, pY, pZ, -1, -7, 0, 5, 14, 10, pDirection);
			return isOkBox(box) && pPieces.findCollisionPiece(box) == null ? new CastleCorridorStairsPiece(pGenDepth, box, pDirection) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel pLevel, final StructureManager pManager, final ChunkGenerator pGenerator, final RandomSource pRandom, final BoundingBox pBoundingBox, final ChunkPos pChunkPos, final BlockPos pPos) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair2 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair4 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair5 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair6 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair7 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState fence1 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState slab1 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState();
			final BlockState slab2 = Blocks.NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP);
			//stairs
			for (int i = 0; i <= 9; ++i) {
				int j = Math.max(1, 7 - i);
				int k = Math.min(Math.max(j + 5, 14 - i), 13);
				//bottom
				this.generateBox(pLevel, pBoundingBox, 0, j - 1, i, 1, j, i, block1, block1, false);
				this.generateBox(pLevel, pBoundingBox, 2, j - 1, i, 2, j, i, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, 3, j - 1, i, 4, j, i, block1, block1, false);
				this.generateAirBox(pLevel, pBoundingBox, 1, j + 1, i, 3, k - 1, i);
				if (i <= 6) {
					this.placeBlock(pLevel, stair1, 1, j + 1, i, pBoundingBox);
					this.placeBlock(pLevel, stair2, 2, j + 1, i, pBoundingBox);
					this.placeBlock(pLevel, stair1, 3, j + 1, i, pBoundingBox);
				}
				//top
				if (i < 9 && i > 1) {
					this.generateBox(pLevel, pBoundingBox, 0, k, i, 0, k, i, stair1, stair1, false);
					this.generateBox(pLevel, pBoundingBox, 1, k, i, 1, k, i, block1, block1, false);
					this.generateBox(pLevel, pBoundingBox, 2, k, i, 2, k, i, block2, block2, false);
					this.generateBox(pLevel, pBoundingBox, 3, k, i, 3, k, i, block1, block1, false);
					this.generateBox(pLevel, pBoundingBox, 4, k, i, 4, k, i, stair1, stair1, false);
					if (i == 2) {
						this.generateBox(pLevel, pBoundingBox, 1, k, i - 2, 1, k, i - 2, stair5, stair5, false);
						this.generateBox(pLevel, pBoundingBox, 1, k, i - 1, 1, k, i - 1, block1, block1, false);
						this.generateBox(pLevel, pBoundingBox, 2, k, i - 1, 2, k, i - 1, stair4, stair4, false);
						this.generateBox(pLevel, pBoundingBox, 3, k, i - 1, 3, k, i - 1, block1, block1, false);
						this.generateBox(pLevel, pBoundingBox, 3, k, i - 2, 3, k, i - 2, stair6, stair6, false);
					} else {
						this.generateBox(pLevel, pBoundingBox, 1, k, i - 1, 1, k, i - 1, stair3, stair3, false);
						this.generateBox(pLevel, pBoundingBox, 2, k, i - 1, 2, k, i - 1, stair4, stair4, false);
						this.generateBox(pLevel, pBoundingBox, 3, k, i - 1, 3, k, i - 1, stair3, stair3, false);
					}
				} else {
					if (i == 9) {
						this.generateBox(pLevel, pBoundingBox, 1, k - 1, i, 1, k - 1, i, stair5, stair5, false);
						this.generateBox(pLevel, pBoundingBox, 1, k - 1, i - 1, 1, k - 1, i - 1, slab2, slab2, false);
						this.generateBox(pLevel, pBoundingBox, 3, k - 1, i, 3, k - 1, i, stair6, stair6, false);
						this.generateBox(pLevel, pBoundingBox, 3, k - 1, i - 1, 3, k - 1, i - 1, slab2, slab2, false);
					}
					this.generateBox(pLevel, pBoundingBox, 0, k, i, 0, k, i, slab1, slab1, false);
					this.generateBox(pLevel, pBoundingBox, 1, k, i, 1, k, i, block1, block1, false);
					this.generateBox(pLevel, pBoundingBox, 2, k, i, 2, k, i, block2, block2, false);
					this.generateBox(pLevel, pBoundingBox, 3, k, i, 3, k, i, block1, block1, false);
					this.generateBox(pLevel, pBoundingBox, 4, k, i, 4, k, i, slab1, slab1, false);
				}
				this.generateBox(pLevel, pBoundingBox, 1, k, i + 1, 1, k, i + 1, stair1, stair1, false);
				this.generateBox(pLevel, pBoundingBox, 2, k, i + 1, 2, k, i + 1, stair2, stair2, false);
				this.generateBox(pLevel, pBoundingBox, 3, k, i + 1, 3, k, i + 1, stair1, stair1, false);
				this.generateBox(pLevel, pBoundingBox, 0, j + 1, i, 0, k - 1, i, block1, block1, false);
				this.generateBox(pLevel, pBoundingBox, 4, j + 1, i, 4, k - 1, i, block1, block1, false);
				if ((i & 1) == 0) {
					//fences
					this.generateBox(pLevel, pBoundingBox, 0, j + 1, i, 0, j + 4, i, block2, block2, false);
					this.generateBox(pLevel, pBoundingBox, 4, j + 1, i, 4, j + 4, i, block2, block2, false);
					this.generateBox(pLevel, pBoundingBox, 0, j + 2, i, 0, j + 3, i, fence1, fence1, false);
					this.generateBox(pLevel, pBoundingBox, 4, j + 2, i, 4, j + 3, i, fence1, fence1, false);
				}
				if (i == 9) {
					this.fillColumnDown(pLevel, block1, 0, j - 2, i - 2, pBoundingBox);
					this.placeBlock(pLevel, stair3, 0, j - 2, i - 3, pBoundingBox);
					this.placeBlock(pLevel, stair7, 0, j - 2, i - 1, pBoundingBox);
					this.placeBlock(pLevel, stair5, 1, j - 2, i - 2, pBoundingBox);
					this.fillColumnDown(pLevel, block1, 4, j - 2, i - 2, pBoundingBox);
					this.placeBlock(pLevel, stair3, 4, j - 2, i - 3, pBoundingBox);
					this.placeBlock(pLevel, stair7, 4, j - 2, i - 1, pBoundingBox);
					this.placeBlock(pLevel, stair6, 3, j - 2, i - 2, pBoundingBox);
				}
			}
		}
	}

	public static class CastleCorridorTBalconyPiece extends InfernalNetherBridgePiece {
		public CastleCorridorTBalconyPiece(final int pGenDepth, final BoundingBox pBoundingBox, final Direction pDirection) {
			super(EEStructurePieceTypes.INF_CASTLE_CORRIDOR_T_BALCONY.get(), pGenDepth, pBoundingBox);
			this.setOrientation(pDirection);
		}

		public CastleCorridorTBalconyPiece(final CompoundTag pCompoundTag) {
			super(EEStructurePieceTypes.INF_CASTLE_CORRIDOR_T_BALCONY.get(), pCompoundTag);
		}

		@Override
		public final void addChildren(final StructurePiece pPiece, final StructurePieceAccessor pPieces, final RandomSource pRandom) {
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 0);
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 1);
			super.addChildren(pPiece, pPieces, pRandom);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource pRandom) {
			int i = 1;
			Direction direction = this.getOrientation();
			if (direction == Direction.WEST || direction == Direction.NORTH)
				i = 5;
			return List.of(new ChildInfo(Direction.WEST, 0, i, pRandom.nextInt(8) > 0, EndType.DEFAULT), new ChildInfo(Direction.EAST, 0, i, pRandom.nextInt(8) > 0, EndType.DEFAULT));
		}

		@Override
		protected final List<Class<? extends InfernalNetherBridgePiece>> getUnacceptableChildren() {
			return List.of(BridgeStraight1.class, BridgeStraight2.class, BridgeStraight3.class, BridgeCrossing1.class, BridgeCrossing2.class, BridgeCrossing3.class, LavaRoomCrossing.class);
		}

		public static final CastleCorridorTBalconyPiece createPiece(final StructurePieceAccessor pPieces, final int pX, final int pY, final int pZ, final Direction pDirection, final int pGenDepth) {
			final BoundingBox box = BoundingBox.orientBox(pX, pY, pZ, -3, 0, 0, 9, 7, 9, pDirection);
			return isOkBox(box) && pPieces.findCollisionPiece(box) == null ? new CastleCorridorTBalconyPiece(pGenDepth, box, pDirection) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel pLevel, final StructureManager pManager, final ChunkGenerator pGenerator, final RandomSource pRandom, final BoundingBox pBoundingBox, final ChunkPos pChunkPos, final BlockPos pPos) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = EEBlocks.BLAZE_LANTERN.get().defaultBlockState();
			final BlockState fence1 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence2 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence3 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence4 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence5 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState slab1 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState();
			final BlockState slab2 = Blocks.NETHER_BRICK_SLAB.defaultBlockState();
			final BlockState slab3 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP);
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair2 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair4 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair5 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair6 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair7 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			//floor
			this.generateBox(pLevel, pBoundingBox, 0, 0, 0, 8, 1, 5, block1, block1, false);
			this.generateAirBox(pLevel, pBoundingBox, 0, 2, 0, 8, 5, 8);
			this.generateBox(pLevel, pBoundingBox, 4, 0, 0, 4, 1, 5, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 0, 2, 8, 1, 2, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 4, 8, 2, 7, block1, block1, false);
			this.placeBlock(pLevel, stair1, 3, 2, 4, pBoundingBox);
			this.placeBlock(pLevel, stair2, 4, 2, 4, pBoundingBox);
			this.placeBlock(pLevel, stair1, 5, 2, 4, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 4, 2, 5, 4, 2, 7, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 1, 2, 7, 7, 2, 7, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 8, 8, 2, 8, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 0, 1, 7, 8, 1, 7, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 0, 1, 6, 8, 1, 6, stair1, stair1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 0, 6, 8, 0, 6, stair3, stair3, false);
			//top
			this.generateBox(pLevel, pBoundingBox, 0, 6, 0, 8, 6, 5, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 4, 6, 0, 4, 6, 5, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 6, 2, 8, 6, 2, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 6, 0, 2, 6, 0, slab1, slab1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 6, 0, 8, 6, 0, slab1, slab1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 6, 5, 2, 6, 5, slab1, slab1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 6, 5, 8, 6, 5, slab1, slab1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 6, 4, 0, 6, 5, slab1, slab1, false);
			this.generateBox(pLevel, pBoundingBox, 8, 6, 4, 8, 6, 5, slab1, slab1, false);
			this.placeBlock(pLevel, stair3, 0, 5, 1, pBoundingBox);
			this.placeBlock(pLevel, slab3, 0, 5, 3, pBoundingBox);
			this.placeBlock(pLevel, stair4, 1, 5, 1, pBoundingBox);
			this.placeBlock(pLevel, stair3, 2, 5, 1, pBoundingBox);
			this.placeBlock(pLevel, slab3, 3, 5, 1, pBoundingBox);
			this.placeBlock(pLevel, stair5, 3, 5, 0, pBoundingBox);
			this.placeBlock(pLevel, stair6, 5, 5, 0, pBoundingBox);
			this.placeBlock(pLevel, slab3, 5, 5, 1, pBoundingBox);
			this.placeBlock(pLevel, stair3, 6, 5, 1, pBoundingBox);
			this.placeBlock(pLevel, stair4, 7, 5, 1, pBoundingBox);
			this.placeBlock(pLevel, stair3, 8, 5, 1, pBoundingBox);
			this.placeBlock(pLevel, slab3, 8, 5, 3, pBoundingBox);
			this.placeBlock(pLevel, stair5, 3, 5, 5, pBoundingBox);
			this.placeBlock(pLevel, slab3, 3, 5, 4, pBoundingBox);
			this.placeBlock(pLevel, slab3, 2, 5, 4, pBoundingBox);
			this.placeBlock(pLevel, stair6, 5, 5, 5, pBoundingBox);
			this.placeBlock(pLevel, slab3, 5, 5, 4, pBoundingBox);
			this.placeBlock(pLevel, slab3, 6, 5, 4, pBoundingBox);
			//walls
			this.generateBox(pLevel, pBoundingBox, 0, 2, 0, 2, 5, 0, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 2, 0, 8, 5, 0, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 1, 2, 0, 1, 5, 0, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 1, 3, 0, 1, 4, 0, fence1, fence1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 2, 0, 7, 5, 0, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 3, 0, 7, 4, 0, fence1, fence1, false);
			this.generateAirBox(pLevel, pBoundingBox, 1, 1, 4, 2, 2, 4);
			this.generateAirBox(pLevel, pBoundingBox, 6, 1, 4, 7, 2, 4);
			//front
			this.generateBox(pLevel, pBoundingBox, 1, 3, 8, 7, 3, 8, fence3, fence3, false);
			this.placeBlock(pLevel, fence4, 0, 3, 8, pBoundingBox);
			this.placeBlock(pLevel, fence5, 8, 3, 8, pBoundingBox);
			this.placeBlock(pLevel, block3, 0, 4, 8, pBoundingBox);
			this.placeBlock(pLevel, block3, 8, 4, 8, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 6, 0, 3, 7, fence2, fence2, false);
			this.generateBox(pLevel, pBoundingBox, 8, 3, 6, 8, 3, 7, fence2, fence2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 4, 0, 5, 5, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 8, 3, 4, 8, 5, 5, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 1, 3, 5, 2, 5, 5, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 3, 5, 7, 5, 5, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 1, 3, 5, 1, 3, 5, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 1, 4, 5, 1, 5, 5, fence1, fence1, false);
			this.generateBox(pLevel, pBoundingBox, 1, 6, 5, 1, 6, 5, slab2, slab2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 3, 5, 7, 3, 5, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 4, 5, 7, 5, 5, fence1, fence1, false);
			this.generateBox(pLevel, pBoundingBox, 7, 6, 5, 7, 6, 5, slab2, slab2, false);
			//bottom
			this.fillColumnDown(pLevel, block1, 0, -1, 0, pBoundingBox);
			this.placeBlock(pLevel, stair5, 1, -1, 0, pBoundingBox);
			this.placeBlock(pLevel, stair3, 0, -1, 1, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 8, -1, 0, pBoundingBox);
			this.placeBlock(pLevel, stair6, 7, -1, 0, pBoundingBox);
			this.placeBlock(pLevel, stair3, 8, -1, 1, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 0, -1, 5, pBoundingBox);
			this.placeBlock(pLevel, stair5, 1, -1, 5, pBoundingBox);
			this.placeBlock(pLevel, stair7, 0, -1, 4, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 8, -1, 5, pBoundingBox);
			this.placeBlock(pLevel, stair6, 7, -1, 5, pBoundingBox);
			this.placeBlock(pLevel, stair7, 8, -1, 4, pBoundingBox);
		}
	}

	public static class CastleSmallCorridorPiece extends InfernalNetherBridgePiece {
		public CastleSmallCorridorPiece(final int pGenDepth, final BoundingBox pBoundingBox, final Direction pDirection) {
			super(EEStructurePieceTypes.INF_CASTLE_SMALL_CORRIDOR.get(), pGenDepth, pBoundingBox);
			this.setOrientation(pDirection);
		}

		public CastleSmallCorridorPiece(final CompoundTag pCompoundTag) {
			super(EEStructurePieceTypes.INF_CASTLE_SMALL_CORRIDOR.get(), pCompoundTag);
		}

		@Override
		public final void addChildren(final StructurePiece pPiece, final StructurePieceAccessor pPieces, final RandomSource pRandom) {
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 0);
			super.addChildren(pPiece, pPieces, pRandom);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource pRandom) {
			return List.of(new ChildInfo(Direction.NORTH, 1, 0, true, EndType.DEFAULT));
		}

		public static final CastleSmallCorridorPiece createPiece(final StructurePieceAccessor pPieces, final int pX, final int pY, final int pZ, final Direction pDirection, final int pGenDepth) {
			final BoundingBox box = BoundingBox.orientBox(pX, pY, pZ, -1, 0, 0, 5, 7, 5, pDirection);
			return isOkBox(box) && pPieces.findCollisionPiece(box) == null ? new CastleSmallCorridorPiece(pGenDepth, box, pDirection) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel pLevel, final StructureManager pManager, final ChunkGenerator pGenerator, final RandomSource pRandom, final BoundingBox pBoundingBox, final ChunkPos pChunkPos, final BlockPos pPos) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState fence1 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState slab1 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState();
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair5 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair6 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			//floor
			this.generateBox(pLevel, pBoundingBox, 0, 0, 0, 4, 1, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 2, 0, 0, 2, 1, 4, block2, block2, false);
			this.generateAirBox(pLevel, pBoundingBox, 0, 2, 0, 4, 5, 4);
			//walls
			this.generateBox(pLevel, pBoundingBox, 0, 2, 0, 0, 5, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 4, 2, 0, 4, 5, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 1, 0, 5, 1, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 1, 0, 4, 1, fence1, fence1, false);
			this.generateBox(pLevel, pBoundingBox, 0, 2, 3, 0, 5, 3, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 3, 3, 0, 4, 3, fence1, fence1, false);
			this.generateBox(pLevel, pBoundingBox, 4, 2, 1, 4, 5, 1, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 4, 3, 1, 4, 4, 1, fence1, fence1, false);
			this.generateBox(pLevel, pBoundingBox, 4, 2, 3, 4, 5, 3, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 4, 3, 3, 4, 4, 3, fence1, fence1, false);
			//top
			this.generateBox(pLevel, pBoundingBox, 0, 6, 0, 4, 6, 4, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 2, 6, 0, 2, 6, 4, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 0, 6, 0, 0, 6, 4, slab1, slab1, false);
			this.generateBox(pLevel, pBoundingBox, 4, 6, 0, 4, 6, 4, slab1, slab1, false);
			this.placeBlock(pLevel, stair3, 1, 5, 0, pBoundingBox);
			this.placeBlock(pLevel, stair5, 1, 5, 1, pBoundingBox);
			this.placeBlock(pLevel, stair3, 1, 5, 2, pBoundingBox);
			this.placeBlock(pLevel, stair5, 1, 5, 3, pBoundingBox);
			this.placeBlock(pLevel, stair3, 1, 5, 4, pBoundingBox);
			this.placeBlock(pLevel, stair4, 3, 5, 0, pBoundingBox);
			this.placeBlock(pLevel, stair6, 3, 5, 1, pBoundingBox);
			this.placeBlock(pLevel, stair4, 3, 5, 2, pBoundingBox);
			this.placeBlock(pLevel, stair6, 3, 5, 3, pBoundingBox);
			this.placeBlock(pLevel, stair4, 3, 5, 4, pBoundingBox);
			//bottom
			this.fillColumnDown(pLevel, block1, 0, -1, 2, pBoundingBox);
			this.placeBlock(pLevel, stair1, 0, -1, 3, pBoundingBox);
			this.placeBlock(pLevel, stair2, 0, -1, 1, pBoundingBox);
			this.placeBlock(pLevel, stair3, 1, -1, 2, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 4, -1, 2, pBoundingBox);
			this.placeBlock(pLevel, stair1, 4, -1, 3, pBoundingBox);
			this.placeBlock(pLevel, stair2, 4, -1, 1, pBoundingBox);
			this.placeBlock(pLevel, stair4, 3, -1, 2, pBoundingBox);
		}
	}

	public static class CastleStalkRoom extends InfernalNetherBridgePiece {
		private boolean hasPlacedSpawner;
		private boolean isNeedingChest;

		public CastleStalkRoom(final int pGenDepth, final BoundingBox pBoundingBox, final Direction pDirection) {
			super(EEStructurePieceTypes.INF_CASTLE_STALK_ROOM.get(), pGenDepth, pBoundingBox);
			this.setOrientation(pDirection);
			this.hasPlacedSpawner = false;
			this.isNeedingChest = true;
		}

		public CastleStalkRoom(final CompoundTag pCompoundTag) {
			super(EEStructurePieceTypes.INF_CASTLE_STALK_ROOM.get(), pCompoundTag);
			this.hasPlacedSpawner = pCompoundTag.getBoolean("Mob");
			this.isNeedingChest = pCompoundTag.getBoolean("Chest");
		}

		@Override
		protected final void addAdditionalSaveData(final StructurePieceSerializationContext pContext, final CompoundTag pCompoundTag) {
			super.addAdditionalSaveData(pContext, pCompoundTag);
			pCompoundTag.putBoolean("Mob", this.hasPlacedSpawner);
			pCompoundTag.putBoolean("Chest", this.isNeedingChest);
		}

		@Override
		public final void addChildren(final StructurePiece pPiece, final StructurePieceAccessor pPieces, final RandomSource pRandom) {
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 0);
			this.generateChild((StartPiece)pPiece, pPieces, pRandom, 1);
			super.addChildren(pPiece, pPieces, pRandom);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource pRandom) {
			return List.of(new ChildInfo(Direction.NORTH, 5, 3, true, EndType.DEFAULT), new ChildInfo(Direction.NORTH, 5, 11, true, EndType.DEFAULT));
		}

		@Override
		protected int maxPlacementChunkRange() {
			return 6;
		}

		public static final CastleStalkRoom createPiece(final StructurePieceAccessor pPieces, final int pX, final int pY, final int pZ, final Direction pDirection, final int pGenDepth) {
			final BoundingBox box = BoundingBox.orientBox(pX, pY, pZ, -5, -3, 0, 13, 14, 13, pDirection);
			return isOkBox(box) && pPieces.findCollisionPiece(box) == null ? new CastleStalkRoom(pGenDepth, box, pDirection) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel pLevel, final StructureManager pManager, final ChunkGenerator pGenerator, final RandomSource pRandom, final BoundingBox pBoundingBox, final ChunkPos pChunkPos, final BlockPos pPos) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = EEBlocks.BLAZE_LANTERN.get().defaultBlockState().setValue(BlazeLanternBlock.HANGING, Boolean.valueOf(true));
			final BlockState block4 = Blocks.SOUL_SAND.defaultBlockState();
			final BlockState block5 = Blocks.NETHER_WART.defaultBlockState();
			final BlockState block6 = Blocks.LANTERN.defaultBlockState().setValue(LanternBlock.HANGING, Boolean.valueOf(true));
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair5 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair6 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair7 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair8 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair9 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair10 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST);
			final BlockState stair11 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST);
			final BlockState stair12 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST);
			final BlockState stair13 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST);
			final BlockState stair14 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState fence1 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence2 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence3 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence4 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence5 = fence2.setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true));
			final BlockState fence6 = fence2.setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence7 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence8 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence9 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true));
			final BlockState fence10 = EEBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true));
			//outLine
			this.generateBox(pLevel, pBoundingBox, 0, 3, 0, 12, 4, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 3, 0, 6, 4, 12, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 3, 3, 3, 9, 4, 3, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 9, 3, 3, 9, 4, 9, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 3, 3, 3, 3, 4, 9, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 3, 3, 9, 9, 4, 9, block2, block2, false);
			this.generateAirBox(pLevel, pBoundingBox, 0, 5, 0, 12, 13, 12);
			this.generateBox(pLevel, pBoundingBox, 0, 5, 0, 1, 12, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 11, 5, 0, 12, 12, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 2, 5, 11, 4, 12, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 8, 5, 11, 10, 12, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 5, 9, 11, 7, 12, 12, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 2, 5, 0, 4, 12, 1, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 8, 5, 0, 10, 12, 1, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 5, 9, 0, 7, 12, 1, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 2, 11, 2, 10, 12, 10, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 11, 3, 6, 11, 9, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 3, 11, 3, 9, 11, 3, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 9, 11, 3, 9, 11, 9, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 3, 11, 3, 3, 11, 9, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 3, 11, 9, 9, 11, 9, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 6, 12, 4, 6, 12, 5, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 6, 12, 7, 6, 12, 8, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 4, 12, 6, 5, 12, 6, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 12, 6, 8, 12, 6, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 4, 12, 3, 8, 12, 3, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 4, 12, 9, 8, 12, 9, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 3, 12, 4, 3, 12, 8, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 9, 12, 4, 9, 12, 8, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 5, 12, 5, 5, 12, 5, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 5, 12, 7, 5, 12, 7, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 12, 5, 7, 12, 5, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 12, 7, 7, 12, 7, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 5, 8, 0, 5, 8, 1, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 7, 8, 0, 7, 8, 1, stair4, stair4, false);
			this.placeBlock(pLevel, block3, 6, 9, 1, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 5, 8, 12, 5, 8, 12, stair3, stair3, false);
			this.generateBox(pLevel, pBoundingBox, 7, 8, 12, 7, 8, 12, stair4, stair4, false);
			this.generateBox(pLevel, pBoundingBox, 5, 8, 11, 5, 8, 11, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 6, 8, 11, 6, 8, 11, stair9, stair9, false);
			this.generateBox(pLevel, pBoundingBox, 7, 8, 11, 7, 8, 11, block1, block1, false);
			//walls
			for (int i = 1; i <= 11; i += 2) {
				this.generateBox(pLevel, pBoundingBox, i, 9, 0, i, 12, 0, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, i, 10, 0, i, 11, 0, fence1, fence1, false);
				this.generateBox(pLevel, pBoundingBox, i, 9, 12, i, 12, 12, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, i, 10, 12, i, 11, 12, fence1, fence1, false);
				this.generateBox(pLevel, pBoundingBox, 0, 9, i, 0, 12, i, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, 0, 10, i, 0, 11, i, fence2, fence2, false);
				this.generateBox(pLevel, pBoundingBox, 12, 9, i, 12, 12, i, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, 12, 10, i, 12, 11, i, fence2, fence2, false);
				this.placeBlock(pLevel, block2, i, 13, 0, pBoundingBox);
				this.placeBlock(pLevel, block2, i, 13, 12, pBoundingBox);
				this.placeBlock(pLevel, block2, 0, 13, i, pBoundingBox);
				this.placeBlock(pLevel, block2, 12, 13, i, pBoundingBox);
				if (i != 11) {
					this.placeBlock(pLevel, fence3, i + 1, 13, 0, pBoundingBox);
					this.placeBlock(pLevel, fence3, i + 1, 13, 12, pBoundingBox);
					this.placeBlock(pLevel, fence4, 0, 13, i + 1, pBoundingBox);
					this.placeBlock(pLevel, fence4, 12, 13, i + 1, pBoundingBox);
				}
			}
			this.placeBlock(pLevel, fence7, 0, 13, 0, pBoundingBox);
			this.placeBlock(pLevel, fence8, 0, 13, 12, pBoundingBox);
			this.placeBlock(pLevel, fence9, 12, 13, 12, pBoundingBox);
			this.placeBlock(pLevel, fence10, 12, 13, 0, pBoundingBox);
			for (int j1 = 3; j1 <= 9; j1 += 2) {
				this.generateBox(pLevel, pBoundingBox, 1, 6, j1, 1, 9, j1, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, 1, 7, j1, 1, 8, j1, fence5, fence5, false);
				this.generateBox(pLevel, pBoundingBox, 11, 6, j1, 11, 9, j1, block2, block2, false);
				this.generateBox(pLevel, pBoundingBox, 11, 7, j1, 11, 8, j1, fence6, fence6, false);
			}
			//stairs
			for (int j = 0; j <= 6; ++j) {
				int k = j + 4;
				this.placeBlock(pLevel, stair7, 5, 5 + j, k, pBoundingBox);
				this.placeBlock(pLevel, stair8, 6, 5 + j, k, pBoundingBox);
				this.placeBlock(pLevel, stair7, 7, 5 + j, k, pBoundingBox);
				if (k >= 5 && k <= 8) {
					this.generateBox(pLevel, pBoundingBox, 5, 5, k, 5, j + 4, k, block1, block1, false);
					this.generateBox(pLevel, pBoundingBox, 6, 5, k, 6, j + 4, k, block2, block2, false);
					this.generateBox(pLevel, pBoundingBox, 7, 5, k, 7, j + 4, k, block1, block1, false);
				}
				else if (k >= 9 && k <= 10) {
					this.generateBox(pLevel, pBoundingBox, 5, 8, k, 5, j + 4, k, block1, block1, false);
					this.generateBox(pLevel, pBoundingBox, 6, 8, k, 6, j + 4, k, block2, block2, false);
					this.generateBox(pLevel, pBoundingBox, 7, 8, k, 7, j + 4, k, block1, block1, false);
				}
				if (j >= 1)
					this.generateAirBox(pLevel, pBoundingBox, 5, 6 + j, k, 7, 9 + j, k);
			}
			this.placeBlock(pLevel, stair7, 5, 12, 11, pBoundingBox);
			this.placeBlock(pLevel, stair8, 6, 12, 11, pBoundingBox);
			this.placeBlock(pLevel, stair7, 7, 12, 11, pBoundingBox);
			this.placeBlock(pLevel, block2, 6, 12, 12, pBoundingBox);
			this.placeBlock(pLevel, fence10, 4, 13, 12, pBoundingBox);
			this.placeBlock(pLevel, fence7, 8, 13, 12, pBoundingBox);
			this.placeBlock(pLevel, block3, 6, 7, 8, pBoundingBox);
			this.generateAirBox(pLevel, pBoundingBox, 6, 5, 8, 6, 6, 8);
			if (this.isNeedingChest && pBoundingBox.isInside(new BlockPos(this.getWorldX(1, 3), this.getWorldY(2), this.getWorldZ(1, 3)))) {
				this.isNeedingChest = false;
				this.createChest(pLevel, pBoundingBox, pRandom, 6, 5, 8, EEChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE_STALK_ROOM);
			}
			if (!this.hasPlacedSpawner) {
				final BlockPos blockPos = new BlockPos(this.getWorldX(6, 7), this.getWorldY(5), this.getWorldZ(6, 7));
				if (pBoundingBox.isInside(blockPos)) {
					this.hasPlacedSpawner = true;
					pLevel.setBlock(blockPos, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = pLevel.getBlockEntity(blockPos);
					if (spawner instanceof SpawnerBlockEntity spawnerBlockEntity)
						spawnerBlockEntity.setEntityId(EntityType.SKELETON, pRandom);
				}
			}
			this.generateBox(pLevel, pBoundingBox, 5, 5, 7, 5, 5, 7, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 5, 6, 7, 5, 7, 7, fence6, fence6, false);
			this.generateBox(pLevel, pBoundingBox, 5, 8, 7, 5, 8, 7, stair8, stair8, false);
			this.generateBox(pLevel, pBoundingBox, 7, 5, 7, 7, 5, 7, block2, block2, false);
			this.generateBox(pLevel, pBoundingBox, 7, 6, 7, 7, 7, 7, fence5, fence5, false);
			this.generateBox(pLevel, pBoundingBox, 7, 8, 7, 7, 8, 7, stair8, stair8, false);
			this.generateAirBox(pLevel, pBoundingBox, 5, 13, 12, 7, 13, 12);
			this.placeBlock(pLevel, stair9, 5, 12, 7, pBoundingBox);
			this.placeBlock(pLevel, stair9, 6, 12, 7, pBoundingBox);
			this.placeBlock(pLevel, stair9, 7, 12, 7, pBoundingBox);
			this.placeBlock(pLevel, stair1, 5, 11, 6, pBoundingBox);
			this.placeBlock(pLevel, stair9, 6, 11, 6, pBoundingBox);
			this.placeBlock(pLevel, stair1, 7, 11, 6, pBoundingBox);
			this.placeBlock(pLevel, stair6, 5, 8, 9, pBoundingBox);
			this.placeBlock(pLevel, stair4, 5, 8, 10, pBoundingBox);
			this.placeBlock(pLevel, stair5, 7, 8, 9, pBoundingBox);
			this.placeBlock(pLevel, stair3, 7, 8, 10, pBoundingBox);
			//farm
			this.generateBox(pLevel, pBoundingBox, 2, 5, 2, 3, 5, 3, block1, block1, false);
			this.placeBlock(pLevel, block2, 3, 5, 3, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 2, 5, 9, 3, 5, 10, block1, block1, false);
			this.placeBlock(pLevel, block2, 3, 5, 9, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 2, 5, 4, 2, 5, 8, block1, block1, false);
			this.generateBox(pLevel, pBoundingBox, 9, 5, 2, 10, 5, 3, block1, block1, false);
			this.placeBlock(pLevel, block2, 9, 5, 3, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 9, 5, 9, 10, 5, 10, block1, block1, false);
			this.placeBlock(pLevel, block2, 9, 5, 9, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 10, 5, 4, 10, 5, 8, block1, block1, false);
			this.placeBlock(pLevel, stair11, 4, 5, 2, pBoundingBox);
			this.placeBlock(pLevel, stair13, 4, 5, 3, pBoundingBox);
			this.placeBlock(pLevel, stair13, 4, 5, 9, pBoundingBox);
			this.placeBlock(pLevel, stair11, 4, 5, 10, pBoundingBox);
			this.placeBlock(pLevel, stair10, 8, 5, 2, pBoundingBox);
			this.placeBlock(pLevel, stair12, 8, 5, 3, pBoundingBox);
			this.placeBlock(pLevel, stair12, 8, 5, 9, pBoundingBox);
			this.placeBlock(pLevel, stair10, 8, 5, 10, pBoundingBox);
			this.generateBox(pLevel, pBoundingBox, 3, 4, 4, 4, 4, 8, block4, block4, false);
			this.generateBox(pLevel, pBoundingBox, 8, 4, 4, 9, 4, 8, block4, block4, false);
			this.generateBox(pLevel, pBoundingBox, 3, 5, 4, 4, 5, 8, block5, block5, false);
			this.generateBox(pLevel, pBoundingBox, 8, 5, 4, 9, 5, 8, block5, block5, false);
			//base pillars
			this.fillColumnDown(pLevel, block1, 0, 2, 0, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 0, 2, 12, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 12, 2, 0, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 12, 2, 12, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 0, 1, 6, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 6, 1, 0, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 6, 1, 12, pBoundingBox);
			this.fillColumnDown(pLevel, block1, 12, 1, 6, pBoundingBox);
			this.placeBlock(pLevel, block6, 0, 2, 6, pBoundingBox);
			this.placeBlock(pLevel, block6, 6, 2, 0, pBoundingBox);
			this.placeBlock(pLevel, block6, 6, 2, 12, pBoundingBox);
			this.placeBlock(pLevel, block6, 12, 2, 6, pBoundingBox);
			//pillars x
			this.placeBlock(pLevel, stair4, 11, 0, 0, pBoundingBox);
			this.placeBlock(pLevel, stair11, 11, 1, 0, pBoundingBox);
			this.placeBlock(pLevel, stair4, 10, 1, 0, pBoundingBox);
			this.placeBlock(pLevel, stair11, 10, 2, 0, pBoundingBox);
			this.placeBlock(pLevel, stair4, 9, 2, 0, pBoundingBox);
			this.placeBlock(pLevel, stair4, 11, 0, 12, pBoundingBox);
			this.placeBlock(pLevel, stair11, 11, 1, 12, pBoundingBox);
			this.placeBlock(pLevel, stair4, 10, 1, 12, pBoundingBox);
			this.placeBlock(pLevel, stair11, 10, 2, 12, pBoundingBox);
			this.placeBlock(pLevel, stair4, 9, 2, 12, pBoundingBox);
			this.placeBlock(pLevel, stair4, 5, 1, 0, pBoundingBox);
			this.placeBlock(pLevel, stair11, 5, 2, 0, pBoundingBox);
			this.placeBlock(pLevel, stair4, 4, 2, 0, pBoundingBox);
			this.placeBlock(pLevel, stair4, 5, 1, 12, pBoundingBox);
			this.placeBlock(pLevel, stair11, 5, 2, 12, pBoundingBox);
			this.placeBlock(pLevel, stair4, 4, 2, 12, pBoundingBox);
			this.placeBlock(pLevel, stair3, 1, 0, 0, pBoundingBox);
			this.placeBlock(pLevel, stair10, 1, 1, 0, pBoundingBox);
			this.placeBlock(pLevel, stair3, 2, 1, 0, pBoundingBox);
			this.placeBlock(pLevel, stair10, 2, 2, 0, pBoundingBox);
			this.placeBlock(pLevel, stair3, 3, 2, 0, pBoundingBox);
			this.placeBlock(pLevel, stair3, 1, 0, 12, pBoundingBox);
			this.placeBlock(pLevel, stair10, 1, 1, 12, pBoundingBox);
			this.placeBlock(pLevel, stair3, 2, 1, 12, pBoundingBox);
			this.placeBlock(pLevel, stair10, 2, 2, 12, pBoundingBox);
			this.placeBlock(pLevel, stair3, 3, 2, 12, pBoundingBox);
			this.placeBlock(pLevel, stair3, 7, 1, 0, pBoundingBox);
			this.placeBlock(pLevel, stair10, 7, 2, 0, pBoundingBox);
			this.placeBlock(pLevel, stair3, 8, 2, 0, pBoundingBox);
			this.placeBlock(pLevel, stair3, 7, 1, 12, pBoundingBox);
			this.placeBlock(pLevel, stair10, 7, 2, 12, pBoundingBox);
			this.placeBlock(pLevel, stair3, 8, 2, 12, pBoundingBox);
			//pillars z
			this.placeBlock(pLevel, stair2, 0, 0, 11, pBoundingBox);
			this.placeBlock(pLevel, stair14, 0, 1, 11, pBoundingBox);
			this.placeBlock(pLevel, stair2, 0, 1, 10, pBoundingBox);
			this.placeBlock(pLevel, stair14, 0, 2, 10, pBoundingBox);
			this.placeBlock(pLevel, stair2, 0, 2, 9, pBoundingBox);
			this.placeBlock(pLevel, stair2, 12, 0, 11, pBoundingBox);
			this.placeBlock(pLevel, stair14, 12, 1, 11, pBoundingBox);
			this.placeBlock(pLevel, stair2, 12, 1, 10, pBoundingBox);
			this.placeBlock(pLevel, stair14, 12, 2, 10, pBoundingBox);
			this.placeBlock(pLevel, stair2, 12, 2, 9, pBoundingBox);
			this.placeBlock(pLevel, stair2, 0, 1, 5, pBoundingBox);
			this.placeBlock(pLevel, stair14, 0, 2, 5, pBoundingBox);
			this.placeBlock(pLevel, stair2, 0, 2, 4, pBoundingBox);
			this.placeBlock(pLevel, stair2, 12, 1, 5, pBoundingBox);
			this.placeBlock(pLevel, stair14, 12, 2, 5, pBoundingBox);
			this.placeBlock(pLevel, stair2, 12, 2, 4, pBoundingBox);
			this.placeBlock(pLevel, stair1, 0, 0, 1, pBoundingBox);
			this.placeBlock(pLevel, stair7, 0, 1, 1, pBoundingBox);
			this.placeBlock(pLevel, stair1, 0, 1, 2, pBoundingBox);
			this.placeBlock(pLevel, stair7, 0, 2, 2, pBoundingBox);
			this.placeBlock(pLevel, stair1, 0, 2, 3, pBoundingBox);
			this.placeBlock(pLevel, stair1, 12, 0, 1, pBoundingBox);
			this.placeBlock(pLevel, stair7, 12, 1, 1, pBoundingBox);
			this.placeBlock(pLevel, stair1, 12, 1, 2, pBoundingBox);
			this.placeBlock(pLevel, stair7, 12, 2, 2, pBoundingBox);
			this.placeBlock(pLevel, stair1, 12, 2, 3, pBoundingBox);
			this.placeBlock(pLevel, stair1, 0, 1, 7, pBoundingBox);
			this.placeBlock(pLevel, stair7, 0, 2, 7, pBoundingBox);
			this.placeBlock(pLevel, stair1, 0, 2, 8, pBoundingBox);
			this.placeBlock(pLevel, stair1, 12, 1, 7, pBoundingBox);
			this.placeBlock(pLevel, stair7, 12, 2, 7, pBoundingBox);
			this.placeBlock(pLevel, stair1, 12, 2, 8, pBoundingBox);
		}
	}

	private abstract static class InfernalNetherBridgePiece extends SmartStructurePiece<StartPiece, InfernalNetherBridgePiece> {
		protected InfernalNetherBridgePiece(final StructurePieceType pType, final int pGenDepth, final BoundingBox pBoundingBox) {
			super(pType, pGenDepth, pBoundingBox);
		}

		public InfernalNetherBridgePiece(final StructurePieceType pType, final CompoundTag pCompoundTag) {
			super(pType, pCompoundTag);
		}

		protected List<ChildInfo> getChildInfo(final RandomSource pRandom) {
			return List.of();
		}

		@Override
		protected void addAdditionalSaveData(final StructurePieceSerializationContext pContext, final CompoundTag pCompoundTag) {};

		private final int updatePieceWeight(final List<PieceWeight> weightsIn) {
			boolean flag = false;
			int i = 0;
			for (final PieceWeight pieceWeight : weightsIn) {
				if (pieceWeight.maxPlaceCount > 0 && pieceWeight.placeCount < pieceWeight.maxPlaceCount)
					flag = true;
				i += pieceWeight.weight;
			}
			return flag ? i : -1;
		}

		private final InfernalNetherBridgePiece generatePiece(final ChildInfo pChildInfo, final StartPiece pStart, final List<PieceWeight> weightsIn, final StructurePiecesBuilder pPieces, final RandomSource pRandom, final int pX, final int pY, final int pZ, final Direction pDirection, final int pGenDepth) {
			final int i = this.updatePieceWeight(weightsIn);
			final boolean flag = i > 0 && pGenDepth <= 30;
			int j = 0;
			while (j < 5 && flag) {
				++j;
				int k = pRandom.nextInt(i);
				for (final PieceWeight pieceWeight : weightsIn) {
					k -= pieceWeight.weight;
					if (k < 0) {
						if (!pieceWeight.doPlace() || (pieceWeight.weightClass == this.getClass() && !pieceWeight.allowInRow))
							break;
						final InfernalNetherBridgePiece piece = findAndCreateBridgePieceFactory(pieceWeight, pPieces, pRandom, pX, pY, pZ, pDirection, pGenDepth, pChildInfo.endType);
						if (piece != null) {
							++pieceWeight.placeCount;
							if (!pieceWeight.isValid())
								weightsIn.remove(pieceWeight);
							return piece;
						}
					}
				}
			}
			return BridgeEndFiller1.createPiece(pPieces, pRandom, pX, pY, pZ, pDirection, pGenDepth, pChildInfo.endType);
		}

		private final InfernalNetherBridgePiece generateAndAddPiece(final ChildInfo pChildInfo, final StartPiece pStart, final StructurePieceAccessor pPieces, final RandomSource pRandom, final int pX, final int pY, final int pZ, final @Nullable Direction pDirection, final int pGenDepth) {
			if (pPieces instanceof StructurePiecesBuilder pieces) {
				if (Math.abs(pX - pStart.getBoundingBox().minX()) <= 16 * this.maxPlacementChunkRange() && Math.abs(pZ - pStart.getBoundingBox().minZ()) <= 16 * this.maxPlacementChunkRange()) {
					final ArrayList<PieceWeight> weights = new ArrayList<>(pChildInfo.secondary ? pStart.availableCastlePieces : pStart.availableBridgePieces);
					if (!this.getUnacceptableChildren().isEmpty())
						weights.removeIf(pieceWeight -> this.getUnacceptableChildren().contains(pieceWeight.weightClass));
					if (!pStart.hasPrerequisites && !pStart.getUnsatisfactoryPieces().isEmpty())
						weights.removeIf(pieceWeight -> pStart.getUnsatisfactoryPieces().contains(pieceWeight.weightClass));
					final InfernalNetherBridgePiece piece = this.generatePiece(pChildInfo, pStart, weights, pieces, pRandom, pX, pY, pZ, pDirection, pGenDepth + 1);
					if (piece != null) {
						if (!pStart.hasPrerequisites && !pStart.getSatisfactoryPieces().isEmpty() && pStart.getSatisfactoryPieces().contains(piece.getClass()))
							pStart.hasPrerequisites = true;
						pPieces.addPiece(piece);
						pStart.pendingChildren.add(piece);
					}
					return piece;
				}
			}
			return BridgeEndFiller1.createPiece(pPieces, pRandom, pX, pY, pZ, pDirection, pGenDepth, pChildInfo.endType);
		}

		@Override
		@Nullable
		protected InfernalNetherBridgePiece generateChild(final StartPiece pStart, final StructurePieceAccessor pPieces, final RandomSource pRandom, final int pIndex) {
			final Direction facing = this.getOrientation();
			if (facing != null) {
				final ChildInfo childInfo = this.getChildInfo(pRandom).get(pIndex);
				switch (childInfo.genDir) {
				case NORTH:
					switch (facing) {
					case NORTH:
						return this.generateAndAddPiece(childInfo, pStart, pPieces, pRandom, this.boundingBox.minX() + childInfo.x, this.boundingBox.minY() + childInfo.y, this.boundingBox.minZ() - 1, facing, this.getGenDepth());
					case SOUTH:
						return this.generateAndAddPiece(childInfo, pStart, pPieces, pRandom, this.boundingBox.minX() + childInfo.x, this.boundingBox.minY() + childInfo.y, this.boundingBox.maxZ() + 1, facing, this.getGenDepth());
					case WEST:
						return this.generateAndAddPiece(childInfo, pStart, pPieces, pRandom, this.boundingBox.minX() - 1, this.boundingBox.minY() + childInfo.y, this.boundingBox.minZ() + childInfo.x, facing, this.getGenDepth());
					case EAST:
						return this.generateAndAddPiece(childInfo, pStart, pPieces, pRandom, this.boundingBox.maxX() + 1, this.boundingBox.minY() + childInfo.y, this.boundingBox.minZ() + childInfo.x, facing, this.getGenDepth());
					default:
						break;
					}
				case WEST:
					switch (facing) {
					case NORTH:
						return this.generateAndAddPiece(childInfo, pStart, pPieces, pRandom, this.boundingBox.minX() - 1, this.boundingBox.minY() + childInfo.x, this.boundingBox.minZ() + childInfo.y, Direction.WEST, this.getGenDepth());
					case SOUTH:
						return this.generateAndAddPiece(childInfo, pStart, pPieces, pRandom, this.boundingBox.minX() - 1, this.boundingBox.minY() + childInfo.x, this.boundingBox.minZ() + childInfo.y, Direction.WEST, this.getGenDepth());
					case WEST:
						return this.generateAndAddPiece(childInfo, pStart, pPieces, pRandom, this.boundingBox.minX() + childInfo.y, this.boundingBox.minY() + childInfo.x, this.boundingBox.minZ() - 1, Direction.NORTH, this.getGenDepth());
					case EAST:
						return this.generateAndAddPiece(childInfo, pStart, pPieces, pRandom, this.boundingBox.minX() + childInfo.y, this.boundingBox.minY() + childInfo.x, this.boundingBox.minZ() - 1, Direction.NORTH, this.getGenDepth());
					default:
						break;
					}
				case EAST:
					switch (facing) {
					case NORTH:
						return this.generateAndAddPiece(childInfo, pStart, pPieces, pRandom, this.boundingBox.maxX() + 1, this.boundingBox.minY() + childInfo.x, this.boundingBox.minZ() + childInfo.y, Direction.EAST, this.getGenDepth());
					case SOUTH:
						return this.generateAndAddPiece(childInfo, pStart, pPieces, pRandom, this.boundingBox.maxX() + 1, this.boundingBox.minY() + childInfo.x, this.boundingBox.minZ() + childInfo.y, Direction.EAST, this.getGenDepth());
					case WEST:
						return this.generateAndAddPiece(childInfo, pStart, pPieces, pRandom, this.boundingBox.minX() + childInfo.y, this.boundingBox.minY() + childInfo.x, this.boundingBox.maxZ() + 1, Direction.SOUTH, this.getGenDepth());
					case EAST:
						return this.generateAndAddPiece(childInfo, pStart, pPieces, pRandom, this.boundingBox.minX() + childInfo.y, this.boundingBox.minY() + childInfo.x, this.boundingBox.maxZ() + 1, Direction.SOUTH, this.getGenDepth());
					default:
						break;
					}
				default:
					break;
				}
			}
			return null;
		}

		protected static final boolean isOkBox(final BoundingBox pBoundingBox) {
			return pBoundingBox != null && pBoundingBox.minY() > 10;
		}
	}

	private static final class PieceWeight {
		protected final Class<? extends InfernalNetherBridgePiece> weightClass;
		protected final int weight;
		protected int placeCount;
		protected final int maxPlaceCount;
		protected final boolean allowInRow;

		public PieceWeight(final Class<? extends InfernalNetherBridgePiece> pWeightClass, final int pWeight, final int pMaxPlaceCount, final boolean pAllowInRow) {
			this.weightClass = pWeightClass;
			this.weight = pWeight;
			this.maxPlaceCount = pMaxPlaceCount;
			this.allowInRow = pAllowInRow;
		}

		public PieceWeight(final Class<? extends InfernalNetherBridgePiece> pWeightClass, final int pWeight, final int pMaxPlaceCount) {
			this(pWeightClass, pWeight, pMaxPlaceCount, false);
		}

		public final boolean doPlace() {
			return this.maxPlaceCount == 0 || this.placeCount < this.maxPlaceCount;
		}

		public final boolean isValid() {
			return this.maxPlaceCount == 0 || this.placeCount < this.maxPlaceCount;
		}
	}

	private static enum EndType {
		NONE,
		DEFAULT,
		THICK
	}

	private static class ChildInfo {
		private final Direction genDir;
		private final int x, y;
		private final boolean secondary;
		private final EndType endType;

		private ChildInfo(final Direction pGenDir, final int pX, final int pY, final boolean pSecondary, final EndType pEndType) {
			this.genDir = pGenDir;
			this.x = pX;
			this.y = pY;
			this.secondary = pSecondary;
			this.endType = pEndType;
		}
	}

	public static final class StartPiece extends GrandMonsterThrone {
		protected final ArrayList<SmartStructurePiece<?, ?>> pendingChildren = new ArrayList<>();
		protected Structure.GenerationContext context;
		private ArrayList<PieceWeight> availableBridgePieces;
		private ArrayList<PieceWeight> availableCastlePieces;
		private boolean hasPrerequisites;

		public StartPiece(final Structure.GenerationContext pContext, final int pX, final int pZ) {
			super(pX, pZ, getRandomHorizontalDirection(pContext.random()));
			this.context = pContext;
			this.availableBridgePieces = new ArrayList<>();
			for (final PieceWeight pieceWeight : InfernalNetherFortressPieces.PRIMARY_COMPONENTS) {
				pieceWeight.placeCount = 0;
				this.availableBridgePieces.add(pieceWeight);
			}
			this.availableCastlePieces = new ArrayList<>();
			for (final PieceWeight pieceWeight1 : InfernalNetherFortressPieces.SECONDARY_COMPONENTS) {
				pieceWeight1.placeCount = 0;
				this.availableCastlePieces.add(pieceWeight1);
			}
		}

		public StartPiece(final CompoundTag pCompoundTag) {
			super(EEStructurePieceTypes.INF_START.get(), pCompoundTag);
		}

		public List<Class<? extends InfernalNetherBridgePiece>> getUnsatisfactoryPieces() {
			return List.of(CastleEntrance.class, MonsterThrone.class);
		}

		public List<Class<? extends InfernalNetherBridgePiece>> getSatisfactoryPieces() {
			return List.of(BridgeCrossing1.class, BridgeCrossing2.class, BridgeCrossing3.class, RoomCrossing.class, LavaRoomCrossing.class);
		}
	}
}
