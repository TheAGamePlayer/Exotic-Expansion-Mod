package dev.theagameplayer.blightedworlds.world.level.levelgen.structure.structures;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

import org.apache.logging.log4j.Logger;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import dev.theagameplayer.blightedworlds.data.loot.packs.BWChestLoot;
import dev.theagameplayer.blightedworlds.registries.BWBlocks;
import dev.theagameplayer.blightedworlds.registries.BWStructurePieceTypes;
import dev.theagameplayer.blightedworlds.world.level.block.AncientNetherAltarBlock;
import dev.theagameplayer.blightedworlds.world.level.block.BlazeLanternBlock;
import dev.theagameplayer.blightedworlds.world.level.levelgen.structure.SmartStructurePiece;
import dev.theagameplayer.blightedworlds.world.level.levelgen.structure.TeleportDestinationPiece;
import dev.theagameplayer.blightedworlds.world.level.levelgen.structure.pieces.SmartStructurePiecesBuilder;
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

	private static InfernalNetherBridgePiece findAndCreateBridgePieceFactory(final PieceWeight weightIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int genDepthIn, final EndType endTypeIn) {
		final Class<? extends InfernalNetherBridgePiece> oClass = weightIn.weightClass;
		InfernalNetherBridgePiece piece = null;
		if (oClass == BridgeEndFiller1.class) {
			piece = BridgeEndFiller1.createPiece(piecesIn, randomIn, xIn, yIn, zIn, facingIn, genDepthIn, endTypeIn);
		} else if (oClass == BridgeEndFiller2.class) {
			piece = BridgeEndFiller2.createPiece(piecesIn, randomIn, xIn, yIn, zIn, facingIn, genDepthIn, endTypeIn);
		} else if (oClass == BridgeStraight1.class) {
			piece = BridgeStraight1.createPiece(piecesIn, randomIn, xIn, yIn, zIn, facingIn, genDepthIn);
		} else if (oClass == BridgeStraight2.class) {
			piece = BridgeStraight2.createPiece(piecesIn, randomIn, xIn, yIn, zIn, facingIn, genDepthIn);
		} else if (oClass == BridgeStraight3.class) {
			piece = BridgeStraight3.createPiece(piecesIn, randomIn, xIn, yIn, zIn, facingIn, genDepthIn);
		} else if (oClass == BridgeCrossing1.class) {
			piece = BridgeCrossing1.createPiece(piecesIn, xIn, yIn, zIn, facingIn, genDepthIn);
		} else if (oClass == BridgeCrossing2.class) {
			piece = BridgeCrossing2.createPiece(piecesIn, xIn, yIn, zIn, facingIn, genDepthIn);
		} else if (oClass == BridgeCrossing3.class) {
			piece = BridgeCrossing3.createPiece(piecesIn, xIn, yIn, zIn, facingIn, genDepthIn);
		} else if (oClass == RoomCrossing.class) {
			piece = RoomCrossing.createPiece(piecesIn, xIn, yIn, zIn, facingIn, genDepthIn);
		} else if (oClass == LavaRoomCrossing.class) {
			piece = LavaRoomCrossing.createPiece(piecesIn, xIn, yIn, zIn, facingIn, genDepthIn);
		} else if (oClass == CastleEntrance.class) {
			piece = CastleEntrance.createPiece(piecesIn, randomIn, xIn, yIn, zIn, facingIn, genDepthIn);
		} else if (oClass == StairsRoom.class) {
			piece = StairsRoom.createPiece(piecesIn, xIn, yIn, zIn, facingIn, genDepthIn);
		} else if (oClass == MonsterThrone.class) {
			piece = MonsterThrone.createPiece(piecesIn, xIn, yIn, zIn, facingIn, genDepthIn);
		} else if (oClass == GrandMonsterThrone.class) {
			piece = GrandMonsterThrone.createPiece(piecesIn, xIn, yIn, zIn, facingIn, genDepthIn);
		} else if (oClass == CastleSmallCorridorLeftTurnPiece.class) {
			piece = CastleSmallCorridorLeftTurnPiece.createPiece(piecesIn, randomIn, xIn, yIn, zIn, facingIn, genDepthIn);
		} else if (oClass == CastleSmallCorridorRightTurnPiece.class) {
			piece = CastleSmallCorridorRightTurnPiece.createPiece(piecesIn, randomIn, xIn, yIn, zIn, facingIn, genDepthIn);
		} else if (oClass == CastleCorridorStairsPiece.class) {
			piece = CastleCorridorStairsPiece.createPiece(piecesIn, xIn, yIn, zIn, facingIn, genDepthIn);
		} else if (oClass == CastleCorridorTBalconyPiece.class) {
			piece = CastleCorridorTBalconyPiece.createPiece(piecesIn, xIn, yIn, zIn, facingIn, genDepthIn);
		} else if (oClass == CastleSmallCorridorPiece.class) {
			piece = CastleSmallCorridorPiece.createPiece(piecesIn, xIn, yIn, zIn, facingIn, genDepthIn);
		} else if (oClass == CastleStalkRoom.class) {
			piece = CastleStalkRoom.createPiece(piecesIn, xIn, yIn, zIn, facingIn, genDepthIn);
		}
		return piece;
	}

	public static class BridgeEndFiller1 extends InfernalNetherBridgePiece {
		private final int selfSeed;

		public BridgeEndFiller1(final int genDepthIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_END_FILLER1.get(), genDepthIn, boundingBoxIn);
			this.setOrientation(facingIn);
			this.selfSeed = randomIn.nextInt();
		}

		public BridgeEndFiller1(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_END_FILLER1.get(), compoundIn);
			this.selfSeed = compoundIn.getInt("Seed");
		}

		public static final InfernalNetherBridgePiece createPiece(final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int genDepthIn, final EndType endTypeIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -1, -3, 0, 5, 10, 8, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null && endTypeIn == EndType.THICK ? new BridgeEndFiller1(genDepthIn, randomIn, box, facingIn) : BridgeEndFiller2.createPiece(piecesIn, randomIn, xIn, yIn, zIn, facingIn, genDepthIn, endTypeIn);
		}

		@Override
		protected void addAdditionalSaveData(final StructurePieceSerializationContext contextIn, final CompoundTag compoundIn) {
			super.addAdditionalSaveData(contextIn, compoundIn);
			compoundIn.putInt("Seed", this.selfSeed);
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final RandomSource random = RandomSource.create(this.selfSeed);
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = Blocks.NETHER_BRICK_FENCE.defaultBlockState();
			final BlockState block4 = Blocks.GLOWSTONE.defaultBlockState();
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair3 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			//air
			this.generateAirBox(levelIn, boundingBoxIn, 1, 5, 0, 3, 9, 8);
			//left rail
			for (int w5 = 0; w5 <= 0; ++w5) {
				final int l3 = random.nextInt(5) + 1;
				this.generateBox(levelIn, boundingBoxIn, w5, 5, 0, w5, 5, l3, block1, block1, false);
				this.generateBox(levelIn, boundingBoxIn, w5, 5, l3 + 1, w5, 5, l3 + 1, stair1, stair1, false);
				this.placeBlock(levelIn, block3, w5, 6, 0, boundingBoxIn);
				this.placeBlock(levelIn, block4, w5, 7, 0, boundingBoxIn);
			}
			//right rail
			for (int w6 = 4; w6 <= 4; ++w6) {
				final int l3 = random.nextInt(5) + 1;
				this.generateBox(levelIn, boundingBoxIn, w6, 5, 0, w6, 5, l3, block1, block1, false);
				this.generateBox(levelIn, boundingBoxIn, w6, 5, l3 + 1, w6, 5, l3 + 1, stair1, stair1, false);
				this.placeBlock(levelIn, block3, w6, 6, 0, boundingBoxIn);
				this.placeBlock(levelIn, block4, w6, 7, 0, boundingBoxIn);
			}
			//top-left
			for (int w1 = 0; w1 <= 1; ++w1) {
				final int l = random.nextInt(8);
				this.generateBox(levelIn, boundingBoxIn, w1, 4, 0, w1, 4, l, block1, block1, false);
				this.generateBox(levelIn, boundingBoxIn, w1, 4, l + 1, w1, 4, l + 1, stair2, stair2, false);
			}
			//top-middle
			for (int w2 = 2; w2 <= 2; ++w2) {
				final int l = random.nextInt(8);
				this.generateBox(levelIn, boundingBoxIn, w2, 4, 0, w2, 4, l, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, w2, 4, l + 1, w2, 4, l + 1, stair3, stair3, false);
			}
			//top-right
			for (int w3 = 3; w3 <= 4; ++w3) {
				final int l = random.nextInt(8);
				this.generateBox(levelIn, boundingBoxIn, w3, 4, 0, w3, 4, l, block1, block1, false);
				this.generateBox(levelIn, boundingBoxIn, w3, 4, l + 1, w3, 4, l + 1, stair2, stair2, false);
			}
			//bottom
			for (int w4 = 0; w4 <= 4; ++w4) {
				for (int h1 = 2; h1 <= 3; ++h1) {
					final int l2 = random.nextInt(2 + ((h1 - 1) * 2));
					this.generateBox(levelIn, boundingBoxIn, w4, h1, 0, w4, h1, l2, block1, block1, false);
					this.generateBox(levelIn, boundingBoxIn, w4, h1, l2 + 1, w4, h1, l2 + 1, stair2, stair2, false);
				}
			}
		}
	}

	public static class BridgeEndFiller2 extends InfernalNetherBridgePiece {
		private final boolean isThickEndPiece;
		private final int selfSeed;

		public BridgeEndFiller2(final int genDepthIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final Direction facingIn, final EndType endTypeIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_END_FILLER2.get(), genDepthIn, boundingBoxIn);
			this.setOrientation(facingIn);
			this.selfSeed = randomIn.nextInt();
			this.isThickEndPiece = endTypeIn == EndType.THICK;
		}

		public BridgeEndFiller2(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_END_FILLER2.get(), compoundIn);
			this.selfSeed = compoundIn.getInt("Seed");
			this.isThickEndPiece = compoundIn.getBoolean("IsThickEndPiece");
		}

		public static final BridgeEndFiller2 createPiece(final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int genDepthIn, final EndType endTypeIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -1, -3, 0, 5, 10, 1, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new BridgeEndFiller2(genDepthIn, randomIn, box, facingIn, endTypeIn) : null;
		}

		@Override
		protected void addAdditionalSaveData(final StructurePieceSerializationContext contextIn, final CompoundTag compoundIn) {
			super.addAdditionalSaveData(contextIn, compoundIn);
			compoundIn.putInt("Seed", this.selfSeed);
			compoundIn.putBoolean("IsThickEndPiece", this.isThickEndPiece);
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair2 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			//air
			this.generateAirBox(levelIn, boundingBoxIn, 1, 5, 0, 3, 9, 0);
			//other
			this.placeBlock(levelIn, stair1, 0, 5, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 4, 5, 0, boundingBoxIn);
			this.placeBlock(levelIn, block1, 0, 4, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 1, 4, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 2, 4, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 3, 4, 0, boundingBoxIn);
			this.placeBlock(levelIn, block1, 4, 4, 0, boundingBoxIn);
			if (this.isThickEndPiece)
				this.generateBox(levelIn, boundingBoxIn, 0, 3, 0, 4, 3, 0, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, this.isThickEndPiece ? 2 : 3, 0, 4, this.isThickEndPiece ? 2 : 3, 0, stair3, stair3, false);
		}
	}

	public static class BridgeStraight1 extends InfernalNetherBridgePiece {
		public BridgeStraight1(final int genDepthIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_STRAIGHT1.get(), genDepthIn, boundingBoxIn);
			this.setOrientation(facingIn);
		}

		public BridgeStraight1(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_STRAIGHT1.get(), compoundIn);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 0);
			super.addChildren(pieceIn, piecesIn, randomIn);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource randomIn) {
			return List.of(new ChildInfo(Direction.NORTH, 1, 4, false, EndType.THICK));
		}

		public static final BridgeStraight1 createPiece(final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int genDepthIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -1, -4, 0, 5, 11, 19, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new BridgeStraight1(genDepthIn, randomIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState();
			final BlockState block4 = Blocks.RED_NETHER_BRICK_WALL.defaultBlockState();
			final BlockState block5 = Blocks.LANTERN.defaultBlockState().setValue(LanternBlock.HANGING, Boolean.valueOf(true));
			final BlockState block6 = Blocks.CHAIN.defaultBlockState();
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState slab1 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP);
			//air
			this.generateAirBox(levelIn, boundingBoxIn, 1, 6, 0, 3, 10, 18);
			//top
			this.generateBox(levelIn, boundingBoxIn, 0, 5, 0, 1, 5, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 5, 0, 2, 5, 18, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 5, 0, 4, 5, 18, block1, block1, false);
			//rails
			this.generateBox(levelIn, boundingBoxIn, 0, 6, 0, 0, 6, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 7, 3, 0, 7, 3, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 7, 4, 0, 7, 4, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 7, 8, 0, 7, 8, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 7, 9, 0, 7, 9, slab1, slab1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 7, 10, 0, 7, 10, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 7, 14, 0, 7, 14, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 7, 15, 0, 7, 15, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 6, 0, 4, 6, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 7, 3, 4, 7, 3, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 7, 4, 4, 7, 4, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 7, 8, 4, 7, 8, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 7, 9, 4, 7, 9, slab1, slab1, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 7, 10, 4, 7, 10, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 7, 14, 4, 7, 14, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 7, 15, 4, 7, 15, stair4, stair4, false);
			//bottom
			this.generateBox(levelIn, boundingBoxIn, 0, 4, 0, 4, 4, 18, block1, block1, false);
			//pillars
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 0, 4, 3, 0, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 4, 4, 3, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 18, 4, 3, 18, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 14, 4, 3, 14, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 3, 4, 3, 3, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 5, 4, 3, 5, stair2, stair2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 15, 4, 3, 15, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 13, 4, 3, 13, stair1, stair1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 1, 4, 2, 1, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 2, 4, 2, 2, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 1, 1, 4, 1, 2, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 0, 4, 2, 0, stair1, stair1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 18, 4, 2, 18, stair2, stair2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 16, 4, 2, 16, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 17, 4, 2, 17, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 1, 16, 4, 1, 17, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 3, 4, 2, 3, stair2, stair2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 15, 4, 2, 15, stair1, stair1, false);
			for (int w1 = 0; w1 <= 4; ++w1) {
				for (int l1 = 1; l1 <= 2; ++l1) {
					this.fillColumnDown(levelIn, block1, w1, 0, l1, boundingBoxIn);
					this.fillColumnDown(levelIn, block1, w1, 0, 18 - l1, boundingBoxIn);
				}
			}
			//decoration
			this.placeBlock(levelIn, block4, 0, 3, 9, boundingBoxIn);
			this.placeBlock(levelIn, block3, 0, 2, 9, boundingBoxIn);
			this.placeBlock(levelIn, block6, 0, 1, 9, boundingBoxIn);
			this.placeBlock(levelIn, block5, 0, 0, 9, boundingBoxIn);
			this.placeBlock(levelIn, block4, 4, 3, 9, boundingBoxIn);
			this.placeBlock(levelIn, block3, 4, 2, 9, boundingBoxIn);
			this.placeBlock(levelIn, block6, 4, 1, 9, boundingBoxIn);
			this.placeBlock(levelIn, block5, 4, 0, 9, boundingBoxIn);
			this.placeBlock(levelIn, block3, 0, 3, 7, boundingBoxIn);
			this.placeBlock(levelIn, block6, 0, 2, 7, boundingBoxIn);
			this.placeBlock(levelIn, block5, 0, 1, 7, boundingBoxIn);
			this.placeBlock(levelIn, block3, 4, 3, 7, boundingBoxIn);
			this.placeBlock(levelIn, block6, 4, 2, 7, boundingBoxIn);
			this.placeBlock(levelIn, block5, 4, 1, 7, boundingBoxIn);
			this.placeBlock(levelIn, block3, 0, 3, 11, boundingBoxIn);
			this.placeBlock(levelIn, block6, 0, 2, 11, boundingBoxIn);
			this.placeBlock(levelIn, block5, 0, 1, 11, boundingBoxIn);
			this.placeBlock(levelIn, block3, 4, 3, 11, boundingBoxIn);
			this.placeBlock(levelIn, block6, 4, 2, 11, boundingBoxIn);
			this.placeBlock(levelIn, block5, 4, 1, 11, boundingBoxIn);
		}
	}

	public static class BridgeStraight2 extends InfernalNetherBridgePiece {
		private final int selfSeed;

		public BridgeStraight2(final int genDepthIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_STRAIGHT2.get(), genDepthIn, boundingBoxIn);
			this.setOrientation(facingIn);
			this.selfSeed = randomIn.nextInt();
		}

		public BridgeStraight2(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_STRAIGHT2.get(), compoundIn);
			this.selfSeed = compoundIn.getInt("Seed");
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 0);
			super.addChildren(pieceIn, piecesIn, randomIn);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource randomIn) {
			return List.of(new ChildInfo(Direction.NORTH, 1, 3, false, EndType.NONE));
		}

		@Override
		protected int maxPlacementChunkRange() {
			return 6;
		}

		public static final BridgeStraight2 createPiece(final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int genDepthIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -1, -3, 0, 5, 10, 19, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new BridgeStraight2(genDepthIn, randomIn, box, facingIn) : null;
		}

		@Override
		protected void addAdditionalSaveData(final StructurePieceSerializationContext contextIn, final CompoundTag compoundIn) {
			super.addAdditionalSaveData(contextIn, compoundIn);
			compoundIn.putInt("Seed", this.selfSeed);
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
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
			this.generateAirBox(levelIn, boundingBoxIn, 1, 5, 0, 3, 9, 18);
			//left rail
			for (int w5 = 0; w5 <= 0; ++w5) {
				final int l3 = random.nextInt(5) + 1;
				this.generateBox(levelIn, boundingBoxIn, w5, 5, 0, w5, 5, l3, block1, block1, false);
				this.generateBox(levelIn, boundingBoxIn, w5, 5, l3 + 1, w5, 5, l3 + 1, stair1, stair1, false);
				this.placeBlock(levelIn, block3, w5, 6, 0, boundingBoxIn);
				this.placeBlock(levelIn, block4, w5, 7, 0, boundingBoxIn);
				final int l6 = random.nextInt(5) + 1;
				this.generateBox(levelIn, boundingBoxIn, w5, 5, 18 - l6, w5, 5, 18, block1, block1, false);
				this.generateBox(levelIn, boundingBoxIn, w5, 5, (18 - l6) - 1, w5, 5, (18 - l6) - 1, stair4, stair4, false);
				this.placeBlock(levelIn, block3, w5, 6, 18, boundingBoxIn);
				this.placeBlock(levelIn, block4, w5, 7, 18, boundingBoxIn);
			}
			//right rail
			for (int w6 = 4; w6 <= 4; ++w6) {
				final int l3 = random.nextInt(5) + 1;
				this.generateBox(levelIn, boundingBoxIn, w6, 5, 0, w6, 5, l3, block1, block1, false);
				this.generateBox(levelIn, boundingBoxIn, w6, 5, l3 + 1, w6, 5, l3 + 1, stair1, stair1, false);
				this.placeBlock(levelIn, block3, w6, 6, 0, boundingBoxIn);
				this.placeBlock(levelIn, block4, w6, 7, 0, boundingBoxIn);
				final int l6 = random.nextInt(5) + 1;
				this.generateBox(levelIn, boundingBoxIn, w6, 5, 18 - l6, w6, 5, 18, block1, block1, false);
				this.generateBox(levelIn, boundingBoxIn, w6, 5, (18 - l6) - 1, w6, 5, (18 - l6) - 1, stair4, stair4, false);
				this.placeBlock(levelIn, block3, w6, 6, 18, boundingBoxIn);
				this.placeBlock(levelIn, block4, w6, 7, 18, boundingBoxIn);
			}
			//top-left
			for (int w1 = 0; w1 <= 1; ++w1) {
				final int l = random.nextInt(8);
				this.generateBox(levelIn, boundingBoxIn, w1, 4, 0, w1, 4, l, block1, block1, false);
				this.generateBox(levelIn, boundingBoxIn, w1, 4, l + 1, w1, 4, l + 1, stair2, stair2, false);
				final int l4 = random.nextInt(8);
				this.generateBox(levelIn, boundingBoxIn, w1, 4, 18 - l4, w1, 4, 18, block1, block1, false);
				this.generateBox(levelIn, boundingBoxIn, w1, 4, (18 - l4) - 1, w1, 4, (18 - l4) - 1, stair5, stair5, false);
			}
			//top-middle
			for (int w2 = 2; w2 <= 2; ++w2) {
				final int l = random.nextInt(8);
				this.generateBox(levelIn, boundingBoxIn, w2, 4, 0, w2, 4, l, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, w2, 4, l + 1, w2, 4, l + 1, stair3, stair3, false);
				final int l4 = random.nextInt(8);
				this.generateBox(levelIn, boundingBoxIn, w2, 4, 18 - l4, w2, 4, 18, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, w2, 4, (18 - l4) - 1, w2, 4, (18 - l4) - 1, stair6, stair6, false);
			}
			//top-right
			for (int w3 = 3; w3 <= 4; ++w3) {
				final int l = random.nextInt(8);
				this.generateBox(levelIn, boundingBoxIn, w3, 4, 0, w3, 4, l, block1, block1, false);
				this.generateBox(levelIn, boundingBoxIn, w3, 4, l + 1, w3, 4, l + 1, stair2, stair2, false);
				final int l4 = random.nextInt(8);
				this.generateBox(levelIn, boundingBoxIn, w3, 4, 18 - l4, w3, 4, 18, block1, block1, false);
				this.generateBox(levelIn, boundingBoxIn, w3, 4, (18 - l4) - 1, w3, 4, (18 - l4) - 1, stair5, stair5, false);
			}
			//bottom
			for (int w4 = 0; w4 <= 4; ++w4) {
				for (int h1 = 2; h1 <= 3; ++h1) {
					final int l2 = random.nextInt(2 + ((h1 - 1) * 2));
					this.generateBox(levelIn, boundingBoxIn, w4, h1, 0, w4, h1, l2, block1, block1, false);
					this.generateBox(levelIn, boundingBoxIn, w4, h1, l2 + 1, w4, h1, l2 + 1, stair2, stair2, false);
					final int l5 = random.nextInt(2 + ((h1 - 1) * 2));
					this.generateBox(levelIn, boundingBoxIn, w4, h1, 18 - l5, w4, h1, 18, block1, block1, false);
					this.generateBox(levelIn, boundingBoxIn, w4, h1, (18 - l5) - 1, w4, h1, (18 - l5) - 1, stair5, stair5, false);
				}
			}
		}
	}

	public static class BridgeStraight3 extends InfernalNetherBridgePiece {
		public BridgeStraight3(final int genDepthIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_STRAIGHT3.get(), genDepthIn, boundingBoxIn);
			this.setOrientation(facingIn);
		}

		public BridgeStraight3(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_STRAIGHT3.get(), compoundIn);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 0);
			super.addChildren(pieceIn, piecesIn, randomIn);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource randomIn) {
			return List.of(new ChildInfo(Direction.NORTH, 1, 4, false, EndType.THICK));
		}

		public static final BridgeStraight3 createPiece(final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int genDepthIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -1, -4, 0, 5, 11, 19, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new BridgeStraight3(genDepthIn, randomIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState();
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
			this.generateAirBox(levelIn, boundingBoxIn, 1, 6, 0, 3, 10, 18);
			//top
			this.generateBox(levelIn, boundingBoxIn, 0, 5, 0, 1, 5, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 5, 0, 2, 5, 18, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 5, 0, 4, 5, 18, block1, block1, false);
			//rails
			this.generateBox(levelIn, boundingBoxIn, 0, 6, 0, 0, 6, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 7, 3, 0, 7, 3, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 7, 4, 0, 7, 4, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 7, 8, 0, 7, 8, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 7, 9, 0, 7, 9, slab1, slab1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 7, 10, 0, 7, 10, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 7, 14, 0, 7, 14, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 7, 15, 0, 7, 15, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 6, 0, 4, 6, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 7, 3, 4, 7, 3, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 7, 4, 4, 7, 4, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 7, 8, 4, 7, 8, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 7, 9, 4, 7, 9, slab1, slab1, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 7, 10, 4, 7, 10, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 7, 14, 4, 7, 14, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 7, 15, 4, 7, 15, stair4, stair4, false);
			//top structure
			this.placeBlock(levelIn, block4, 0, 8, 9, boundingBoxIn);
			this.placeBlock(levelIn, block4, 4, 8, 9, boundingBoxIn);
			this.placeBlock(levelIn, block7, 0, 9, 9, boundingBoxIn);
			this.placeBlock(levelIn, block7, 4, 9, 9, boundingBoxIn);
			this.placeBlock(levelIn, block4, 0, 10, 9, boundingBoxIn);
			this.placeBlock(levelIn, block4, 4, 10, 9, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 0, 11, 9, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 4, 11, 9, boundingBoxIn);
			this.placeBlock(levelIn, block1, 1, 11, 9, boundingBoxIn);
			this.placeBlock(levelIn, block2, 2, 11, 9, boundingBoxIn);
			this.placeBlock(levelIn, block1, 3, 11, 9, boundingBoxIn);
			//bottom
			this.generateBox(levelIn, boundingBoxIn, 0, 4, 0, 4, 4, 18, block1, block1, false);
			//pillars
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 0, 4, 3, 0, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 4, 4, 3, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 18, 4, 3, 18, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 14, 4, 3, 14, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 3, 4, 3, 3, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 5, 4, 3, 5, stair2, stair2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 15, 4, 3, 15, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 13, 4, 3, 13, stair1, stair1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 1, 4, 2, 1, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 2, 4, 2, 2, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 1, 1, 4, 1, 2, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 0, 4, 2, 0, stair1, stair1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 18, 4, 2, 18, stair2, stair2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 16, 4, 2, 16, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 17, 4, 2, 17, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 1, 16, 4, 1, 17, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 3, 4, 2, 3, stair2, stair2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 15, 4, 2, 15, stair1, stair1, false);
			for (int w1 = 0; w1 <= 4; ++w1) {
				for (int l1 = 1; l1 <= 2; ++l1) {
					this.fillColumnDown(levelIn, block1, w1, 0, l1, boundingBoxIn);
					this.fillColumnDown(levelIn, block1, w1, 0, 18 - l1, boundingBoxIn);
				}
			}
			//decoration
			this.placeBlock(levelIn, block4, 0, 3, 9, boundingBoxIn);
			this.placeBlock(levelIn, block3, 0, 2, 9, boundingBoxIn);
			this.placeBlock(levelIn, block6, 0, 1, 9, boundingBoxIn);
			this.placeBlock(levelIn, block5, 0, 0, 9, boundingBoxIn);
			this.placeBlock(levelIn, block4, 4, 3, 9, boundingBoxIn);
			this.placeBlock(levelIn, block3, 4, 2, 9, boundingBoxIn);
			this.placeBlock(levelIn, block6, 4, 1, 9, boundingBoxIn);
			this.placeBlock(levelIn, block5, 4, 0, 9, boundingBoxIn);
			this.placeBlock(levelIn, block3, 0, 3, 7, boundingBoxIn);
			this.placeBlock(levelIn, block6, 0, 2, 7, boundingBoxIn);
			this.placeBlock(levelIn, block5, 0, 1, 7, boundingBoxIn);
			this.placeBlock(levelIn, block3, 4, 3, 7, boundingBoxIn);
			this.placeBlock(levelIn, block6, 4, 2, 7, boundingBoxIn);
			this.placeBlock(levelIn, block5, 4, 1, 7, boundingBoxIn);
			this.placeBlock(levelIn, block3, 0, 3, 11, boundingBoxIn);
			this.placeBlock(levelIn, block6, 0, 2, 11, boundingBoxIn);
			this.placeBlock(levelIn, block5, 0, 1, 11, boundingBoxIn);
			this.placeBlock(levelIn, block3, 4, 3, 11, boundingBoxIn);
			this.placeBlock(levelIn, block6, 4, 2, 11, boundingBoxIn);
			this.placeBlock(levelIn, block5, 4, 1, 11, boundingBoxIn);
		}
	}

	public static class BridgeCrossing1 extends InfernalNetherBridgePiece {
		public BridgeCrossing1(final int genDepthIn, final BoundingBox boundingBoxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_CROSSING1.get(), genDepthIn, boundingBoxIn);
			this.setOrientation(facingIn);
		}

		public BridgeCrossing1(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_CROSSING1.get(), compoundIn);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 0);
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 1);
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 2);
			super.addChildren(pieceIn, piecesIn, randomIn);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource randomIn) {
			return List.of(new ChildInfo(Direction.NORTH, 8, 3, false, EndType.THICK), new ChildInfo(Direction.WEST, 3, 8, false, EndType.THICK), new ChildInfo(Direction.EAST, 3, 8, false, EndType.THICK));
		}

		public static final BridgeCrossing1 createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int genDepthIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -8, -3, 0, 19, 10, 19, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new BridgeCrossing1(genDepthIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
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
			this.generateAirBox(levelIn, boundingBoxIn, 8, 5, 0, 10, 9, 18);
			this.generateAirBox(levelIn, boundingBoxIn, 0, 5, 8, 18, 9, 10);
			//top
			this.generateBox(levelIn, boundingBoxIn, 7, 4, 0, 8, 4, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 4, 11, 8, 4, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 9, 4, 0, 9, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 9, 4, 10, 9, 4, 18, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 4, 0, 11, 4, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 4, 11, 11, 4, 18, block1, block1, false);
			//center
			this.placeBlock(levelIn, block1, 9, 4, 9, boundingBoxIn);
			this.placeBlock(levelIn, block2, 8, 4, 8, boundingBoxIn);
			this.placeBlock(levelIn, block2, 8, 4, 10, boundingBoxIn);
			this.placeBlock(levelIn, block2, 10, 4, 8, boundingBoxIn);
			this.placeBlock(levelIn, block2, 10, 4, 10, boundingBoxIn);
			//top2
			this.generateBox(levelIn, boundingBoxIn, 0, 4, 7, 7, 4, 8, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 11, 4, 7, 18, 4, 8, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 4, 9, 8, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 4, 9, 18, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 4, 10, 7, 4, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 11, 4, 10, 18, 4, 11, block1, block1, false);
			//bottom
			this.generateBox(levelIn, boundingBoxIn, 7, 3, 0, 11, 3, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 7, 18, 3, 11, block1, block1, false);
			//rails
			this.generateBox(levelIn, boundingBoxIn, 7, 5, 0, 7, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 5, 11, 7, 5, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 11, 5, 0, 11, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 11, 5, 11, 11, 5, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 5, 7, 7, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 11, 5, 7, 18, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 5, 11, 7, 5, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 11, 5, 11, 18, 5, 11, block1, block1, false);
			//pillars z-Axis
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 0, 11, 2, 0, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 4, 11, 2, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 18, 11, 2, 18, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 14, 11, 2, 14, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 3, 11, 2, 3, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 5, 11, 2, 5, stair2, stair2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 15, 11, 2, 15, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 13, 11, 2, 13, stair1, stair1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 1, 11, 1, 1, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 2, 11, 1, 2, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 0, 1, 11, 0, 2, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 0, 11, 1, 0, stair1, stair1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 18, 11, 1, 18, stair2, stair2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 16, 11, 1, 16, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 17, 11, 1, 17, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 0, 16, 11, 0, 17, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 3, 11, 1, 3, stair2, stair2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 15, 11, 1, 15, stair1, stair1, false);
			for (int w1 = 7; w1 <= 11; ++w1) {
				for (int l1 = 1; l1 <= 2; ++l1) {
					this.fillColumnDown(levelIn, block1, w1, -1, l1, boundingBoxIn);
					this.fillColumnDown(levelIn, block1, w1, -1, 18 - l1, boundingBoxIn);
				}
			}
			//pillars x-axis
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 7, 0, 2, 11, stair8, stair8, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 2, 7, 4, 2, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 18, 2, 7, 18, 2, 11, stair7, stair7, false);
			this.generateBox(levelIn, boundingBoxIn, 14, 2, 7, 14, 2, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 2, 7, 3, 2, 11, stair7, stair7, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 2, 7, 5, 2, 11, stair6, stair6, false);
			this.generateBox(levelIn, boundingBoxIn, 15, 2, 7, 15, 2, 11, stair8, stair8, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 2, 7, 13, 2, 11, stair5, stair5, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 1, 7, 1, 1, 11, stair8, stair8, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 1, 7, 2, 1, 11, stair7, stair7, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 0, 7, 2, 0, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 1, 7, 0, 1, 11, stair5, stair5, false);
			this.generateBox(levelIn, boundingBoxIn, 18, 1, 7, 18, 1, 11, stair6, stair6, false);
			this.generateBox(levelIn, boundingBoxIn, 16, 1, 7, 16, 1, 11, stair8, stair8, false);
			this.generateBox(levelIn, boundingBoxIn, 17, 1, 7, 17, 1, 11, stair7, stair7, false);
			this.generateBox(levelIn, boundingBoxIn, 16, 0, 7, 17, 0, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 1, 7, 3, 1, 11, stair6, stair6, false);
			this.generateBox(levelIn, boundingBoxIn, 15, 1, 7, 15, 1, 11, stair5, stair5, false);
			for (int w2 = 1; w2 <= 2; ++w2) {
				for (int l2 = 7; l2 <= 11; ++l2) {
					this.fillColumnDown(levelIn, block1, w2, -1, l2, boundingBoxIn);
					this.fillColumnDown(levelIn, block1, 18 - w2, -1, l2, boundingBoxIn);
				}
			}
		}
	}

	public static class BridgeCrossing2 extends InfernalNetherBridgePiece {
		public BridgeCrossing2(final int genDepthIn, final BoundingBox boundingBoxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_CROSSING2.get(), genDepthIn, boundingBoxIn);
			this.setOrientation(facingIn);
		}

		public BridgeCrossing2(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_CROSSING2.get(), compoundIn);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 0);
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 1);
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 2);
			super.addChildren(pieceIn, piecesIn, randomIn);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource randomIn) {
			return List.of(new ChildInfo(Direction.NORTH, 8, 3, false, EndType.THICK), new ChildInfo(Direction.WEST, 3, 8, false, EndType.THICK), new ChildInfo(Direction.EAST, 3, 8, false, EndType.THICK));
		}

		public static final BridgeCrossing2 createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int genDepthIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -8, -3, 0, 19, 10, 19, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new BridgeCrossing2(genDepthIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = Blocks.RED_NETHER_BRICK_WALL.defaultBlockState();
			final BlockState block4 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState();
			final BlockState block5 = BWBlocks.BLAZE_LANTERN.get().defaultBlockState().setValue(BlazeLanternBlock.HANGING, Boolean.valueOf(true));
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
			this.generateAirBox(levelIn, boundingBoxIn, 8, 5, 0, 10, 9, 18);
			this.generateAirBox(levelIn, boundingBoxIn, 0, 5, 8, 18, 9, 10);
			//top
			this.generateBox(levelIn, boundingBoxIn, 7, 4, 0, 8, 4, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 4, 11, 8, 4, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 9, 4, 0, 9, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 9, 4, 10, 9, 4, 18, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 4, 0, 11, 4, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 4, 11, 11, 4, 18, block1, block1, false);
			//center
			this.placeBlock(levelIn, block1, 9, 4, 9, boundingBoxIn);
			this.placeBlock(levelIn, block2, 8, 4, 8, boundingBoxIn);
			this.placeBlock(levelIn, block2, 8, 4, 10, boundingBoxIn);
			this.placeBlock(levelIn, block2, 10, 4, 8, boundingBoxIn);
			this.placeBlock(levelIn, block2, 10, 4, 10, boundingBoxIn);
			this.placeBlock(levelIn, block4, 9, 8, 9, boundingBoxIn);
			this.placeBlock(levelIn, block5, 9, 7, 9, boundingBoxIn);
			this.placeBlock(levelIn, block3, 9, 9, 9, boundingBoxIn);
			//top center
			this.placeBlock(levelIn, block1, 9, 10, 9, boundingBoxIn);
			this.placeBlock(levelIn, block2, 8, 10, 8, boundingBoxIn);
			this.placeBlock(levelIn, block2, 8, 10, 10, boundingBoxIn);
			this.placeBlock(levelIn, block2, 10, 10, 8, boundingBoxIn);
			this.placeBlock(levelIn, block2, 10, 10, 10, boundingBoxIn);
			this.placeBlock(levelIn, block2, 9, 10, 8, boundingBoxIn);
			this.placeBlock(levelIn, block2, 8, 10, 9, boundingBoxIn);
			this.placeBlock(levelIn, block2, 10, 10, 9, boundingBoxIn);
			this.placeBlock(levelIn, block2, 9, 10, 10, boundingBoxIn);
			this.placeBlock(levelIn, block1, 7, 9, 7, boundingBoxIn);
			this.placeBlock(levelIn, block3, 7, 8, 7, boundingBoxIn);
			this.placeBlock(levelIn, block6, 7, 7, 7, boundingBoxIn);
			this.placeBlock(levelIn, block3, 7, 6, 7, boundingBoxIn);
			this.placeBlock(levelIn, block1, 7, 9, 11, boundingBoxIn);
			this.placeBlock(levelIn, block3, 7, 8, 11, boundingBoxIn);
			this.placeBlock(levelIn, block6, 7, 7, 11, boundingBoxIn);
			this.placeBlock(levelIn, block3, 7, 6, 11, boundingBoxIn);
			this.placeBlock(levelIn, block1, 11, 9, 11, boundingBoxIn);
			this.placeBlock(levelIn, block3, 11, 8, 11, boundingBoxIn);
			this.placeBlock(levelIn, block6, 11, 7, 11, boundingBoxIn);
			this.placeBlock(levelIn, block3, 11, 6, 11, boundingBoxIn);
			this.placeBlock(levelIn, block1, 11, 9, 7, boundingBoxIn);
			this.placeBlock(levelIn, block3, 11, 8, 7, boundingBoxIn);
			this.placeBlock(levelIn, block6, 11, 7, 7, boundingBoxIn);
			this.placeBlock(levelIn, block3, 11, 6, 7, boundingBoxIn);
			//top center stairs & slabs
			this.placeBlock(levelIn, stair9, 9, 10, 7, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 8, 10, 7, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 10, 10, 7, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 9, 10, 11, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 8, 10, 11, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 10, 10, 11, boundingBoxIn);
			this.placeBlock(levelIn, stair11, 7, 10, 9, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 7, 10, 8, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 7, 10, 10, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 11, 10, 9, boundingBoxIn);
			this.placeBlock(levelIn, stair8, 11, 10, 8, boundingBoxIn);
			this.placeBlock(levelIn, stair8, 11, 10, 10, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 7, 10, 7, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 7, 10, 11, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 11, 10, 11, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 11, 10, 7, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 8, 9, 7, boundingBoxIn);
			this.placeBlock(levelIn, slab3, 9, 9, 7, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 10, 9, 7, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 8, 9, 11, boundingBoxIn);
			this.placeBlock(levelIn, slab3, 9, 9, 11, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 10, 9, 11, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 7, 9, 8, boundingBoxIn);
			this.placeBlock(levelIn, slab3, 7, 9, 9, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 7, 9, 10, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 11, 9, 8, boundingBoxIn);
			this.placeBlock(levelIn, slab3, 11, 9, 9, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 11, 9, 10, boundingBoxIn);
			//top2
			this.generateBox(levelIn, boundingBoxIn, 0, 4, 7, 7, 4, 8, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 11, 4, 7, 18, 4, 8, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 4, 9, 8, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 4, 9, 18, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 4, 10, 7, 4, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 11, 4, 10, 18, 4, 11, block1, block1, false);
			//bottom
			this.generateBox(levelIn, boundingBoxIn, 7, 3, 0, 11, 3, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 7, 18, 3, 11, block1, block1, false);
			//rails
			this.generateBox(levelIn, boundingBoxIn, 7, 5, 0, 7, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 5, 11, 7, 5, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 11, 5, 0, 11, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 11, 5, 11, 11, 5, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 5, 7, 7, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 11, 5, 7, 18, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 5, 11, 7, 5, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 11, 5, 11, 18, 5, 11, block1, block1, false);
			//pillars z-Axis
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 0, 11, 2, 0, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 4, 11, 2, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 18, 11, 2, 18, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 14, 11, 2, 14, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 3, 11, 2, 3, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 5, 11, 2, 5, stair2, stair2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 15, 11, 2, 15, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 13, 11, 2, 13, stair1, stair1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 1, 11, 1, 1, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 2, 11, 1, 2, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 0, 1, 11, 0, 2, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 0, 11, 1, 0, stair1, stair1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 18, 11, 1, 18, stair2, stair2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 16, 11, 1, 16, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 17, 11, 1, 17, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 0, 16, 11, 0, 17, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 3, 11, 1, 3, stair2, stair2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 15, 11, 1, 15, stair1, stair1, false);
			for (int w1 = 7; w1 <= 11; ++w1) {
				for (int l1 = 1; l1 <= 2; ++l1) {
					this.fillColumnDown(levelIn, block1, w1, -1, l1, boundingBoxIn);
					this.fillColumnDown(levelIn, block1, w1, -1, 18 - l1, boundingBoxIn);
				}
			}
			//pillars x-axis
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 7, 0, 2, 11, stair8, stair8, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 2, 7, 4, 2, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 18, 2, 7, 18, 2, 11, stair7, stair7, false);
			this.generateBox(levelIn, boundingBoxIn, 14, 2, 7, 14, 2, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 2, 7, 3, 2, 11, stair7, stair7, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 2, 7, 5, 2, 11, stair6, stair6, false);
			this.generateBox(levelIn, boundingBoxIn, 15, 2, 7, 15, 2, 11, stair8, stair8, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 2, 7, 13, 2, 11, stair5, stair5, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 1, 7, 1, 1, 11, stair8, stair8, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 1, 7, 2, 1, 11, stair7, stair7, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 0, 7, 2, 0, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 1, 7, 0, 1, 11, stair5, stair5, false);
			this.generateBox(levelIn, boundingBoxIn, 18, 1, 7, 18, 1, 11, stair6, stair6, false);
			this.generateBox(levelIn, boundingBoxIn, 16, 1, 7, 16, 1, 11, stair8, stair8, false);
			this.generateBox(levelIn, boundingBoxIn, 17, 1, 7, 17, 1, 11, stair7, stair7, false);
			this.generateBox(levelIn, boundingBoxIn, 16, 0, 7, 17, 0, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 1, 7, 3, 1, 11, stair6, stair6, false);
			this.generateBox(levelIn, boundingBoxIn, 15, 1, 7, 15, 1, 11, stair5, stair5, false);
			for (int w2 = 1; w2 <= 2; ++w2) {
				for (int l2 = 7; l2 <= 11; ++l2) {
					this.fillColumnDown(levelIn, block1, w2, -1, l2, boundingBoxIn);
					this.fillColumnDown(levelIn, block1, 18 - w2, -1, l2, boundingBoxIn);
				}
			}
		}
	}

	public static class BridgeCrossing3 extends InfernalNetherBridgePiece {
		public BridgeCrossing3(final int genDepthIn, final BoundingBox boundingBoxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_CROSSING3.get(), genDepthIn, boundingBoxIn);
			this.setOrientation(facingIn);
		}

		public BridgeCrossing3(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_CROSSING3.get(), compoundIn);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 0);
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 1);
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 2);
			super.addChildren(pieceIn, piecesIn, randomIn);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource randomIn) {
			return List.of(new ChildInfo(Direction.NORTH, 8, 3, false, EndType.THICK), new ChildInfo(Direction.WEST, 3, 8, false, EndType.THICK), new ChildInfo(Direction.EAST, 3, 8, false, EndType.THICK));
		}

		public static final BridgeCrossing3 createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int genDepthIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -8, -3, 0, 19, 10, 19, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new BridgeCrossing3(genDepthIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = Blocks.RED_NETHER_BRICK_WALL.defaultBlockState();
			final BlockState block4 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState();
			final BlockState block5 = BWBlocks.BLAZE_LANTERN.get().defaultBlockState();
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair5 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair6 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair7 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST);
			final BlockState stair8 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST);
			//air
			this.generateAirBox(levelIn, boundingBoxIn, 8, 5, 0, 10, 9, 18);
			this.generateAirBox(levelIn, boundingBoxIn, 0, 5, 8, 18, 9, 10);
			this.generateAirBox(levelIn, boundingBoxIn, 6, 5, 5, 12, 9, 13);
			this.generateAirBox(levelIn, boundingBoxIn, 5, 5, 6, 13, 9, 12);
			//top
			this.generateBox(levelIn, boundingBoxIn, 7, 4, 0, 8, 4, 5, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 4, 6, 8, 4, 6, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 4, 13, 8, 4, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 4, 12, 8, 4, 12, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 9, 4, 0, 9, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 9, 4, 10, 9, 4, 18, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 4, 0, 11, 4, 5, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 4, 6, 11, 4, 6, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 4, 13, 11, 4, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 4, 12, 11, 4, 12, block2, block2, false);
			//center
			this.placeBlock(levelIn, block1, 9, 4, 9, boundingBoxIn);
			this.placeBlock(levelIn, block3, 9, 5, 9, boundingBoxIn);
			this.placeBlock(levelIn, block4, 9, 6, 9, boundingBoxIn);
			this.placeBlock(levelIn, block5, 9, 7, 9, boundingBoxIn);
			this.placeBlock(levelIn, block2, 8, 4, 8, boundingBoxIn);
			this.placeBlock(levelIn, block2, 8, 4, 10, boundingBoxIn);
			this.placeBlock(levelIn, block2, 10, 4, 8, boundingBoxIn);
			this.placeBlock(levelIn, block2, 10, 4, 10, boundingBoxIn);
			this.placeBlock(levelIn, block1, 7, 4, 7, boundingBoxIn);
			this.placeBlock(levelIn, block1, 7, 4, 8, boundingBoxIn);
			this.placeBlock(levelIn, block1, 8, 4, 7, boundingBoxIn);
			this.placeBlock(levelIn, block1, 7, 4, 11, boundingBoxIn);
			this.placeBlock(levelIn, block1, 7, 4, 10, boundingBoxIn);
			this.placeBlock(levelIn, block1, 8, 4, 11, boundingBoxIn);
			this.placeBlock(levelIn, block1, 11, 4, 7, boundingBoxIn);
			this.placeBlock(levelIn, block1, 10, 4, 7, boundingBoxIn);
			this.placeBlock(levelIn, block1, 11, 4, 8, boundingBoxIn);
			this.placeBlock(levelIn, block1, 11, 4, 11, boundingBoxIn);
			this.placeBlock(levelIn, block1, 10, 4, 11, boundingBoxIn);
			this.placeBlock(levelIn, block1, 11, 4, 10, boundingBoxIn);
			//center corners
			this.generateBox(levelIn, boundingBoxIn, 6, 3, 6, 6, 4, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 3, 5, 6, 4, 5, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 3, 6, 5, 4, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 3, 12, 6, 4, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 3, 13, 6, 4, 13, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 3, 12, 5, 4, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 3, 6, 12, 4, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 3, 6, 13, 4, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 3, 5, 12, 4, 5, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 3, 12, 12, 4, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 3, 13, 12, 4, 13, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 3, 12, 13, 4, 12, block1, block1, false);
			//center rails
			this.generateBox(levelIn, boundingBoxIn, 5, 4, 5, 5, 5, 5, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 4, 6, 4, 5, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 4, 4, 6, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 4, 13, 5, 5, 13, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 4, 14, 6, 5, 14, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 4, 12, 4, 5, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 4, 5, 13, 5, 5, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 14, 4, 6, 14, 5, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 4, 4, 12, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 4, 13, 13, 5, 13, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 14, 4, 12, 14, 5, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 4, 14, 12, 5, 14, block1, block1, false);
			//top2
			this.generateBox(levelIn, boundingBoxIn, 0, 4, 7, 5, 4, 8, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 4, 7, 6, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 4, 7, 18, 4, 8, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 4, 7, 12, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 4, 9, 8, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 4, 9, 18, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 4, 10, 5, 4, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 4, 10, 6, 4, 11, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 4, 10, 18, 4, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 4, 10, 12, 4, 11, block2, block2, false);
			//bottom
			this.generateBox(levelIn, boundingBoxIn, 7, 3, 0, 11, 3, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 7, 18, 3, 11, block1, block1, false);
			//rails
			this.generateBox(levelIn, boundingBoxIn, 7, 5, 0, 7, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 5, 14, 7, 5, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 11, 5, 0, 11, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 11, 5, 14, 11, 5, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 5, 7, 4, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 14, 5, 7, 18, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 5, 11, 4, 5, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 14, 5, 11, 18, 5, 11, block1, block1, false);
			//pillars z-Axis
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 0, 11, 2, 0, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 4, 11, 2, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 18, 11, 2, 18, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 14, 11, 2, 14, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 3, 11, 2, 3, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 5, 11, 2, 5, stair2, stair2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 15, 11, 2, 15, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 13, 11, 2, 13, stair1, stair1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 1, 11, 1, 1, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 2, 11, 1, 2, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 0, 1, 11, 0, 2, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 0, 11, 1, 0, stair1, stair1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 18, 11, 1, 18, stair2, stair2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 16, 11, 1, 16, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 17, 11, 1, 17, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 0, 16, 11, 0, 17, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 3, 11, 1, 3, stair2, stair2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 15, 11, 1, 15, stair1, stair1, false);
			for (int w1 = 7; w1 <= 11; ++w1) {
				for (int l1 = 1; l1 <= 2; ++l1) {
					this.fillColumnDown(levelIn, block1, w1, -1, l1, boundingBoxIn);
					this.fillColumnDown(levelIn, block1, w1, -1, 18 - l1, boundingBoxIn);
				}
			}
			//pillars x-axis
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 7, 0, 2, 11, stair8, stair8, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 2, 7, 4, 2, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 18, 2, 7, 18, 2, 11, stair7, stair7, false);
			this.generateBox(levelIn, boundingBoxIn, 14, 2, 7, 14, 2, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 2, 7, 3, 2, 11, stair7, stair7, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 2, 7, 5, 2, 11, stair6, stair6, false);
			this.generateBox(levelIn, boundingBoxIn, 15, 2, 7, 15, 2, 11, stair8, stair8, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 2, 7, 13, 2, 11, stair5, stair5, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 1, 7, 1, 1, 11, stair8, stair8, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 1, 7, 2, 1, 11, stair7, stair7, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 0, 7, 2, 0, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 1, 7, 0, 1, 11, stair5, stair5, false);
			this.generateBox(levelIn, boundingBoxIn, 18, 1, 7, 18, 1, 11, stair6, stair6, false);
			this.generateBox(levelIn, boundingBoxIn, 16, 1, 7, 16, 1, 11, stair8, stair8, false);
			this.generateBox(levelIn, boundingBoxIn, 17, 1, 7, 17, 1, 11, stair7, stair7, false);
			this.generateBox(levelIn, boundingBoxIn, 16, 0, 7, 17, 0, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 1, 7, 3, 1, 11, stair6, stair6, false);
			this.generateBox(levelIn, boundingBoxIn, 15, 1, 7, 15, 1, 11, stair5, stair5, false);
			for (int w2 = 1; w2 <= 2; ++w2) {
				for (int l2 = 7; l2 <= 11; ++l2) {
					this.fillColumnDown(levelIn, block1, w2, -1, l2, boundingBoxIn);
					this.fillColumnDown(levelIn, block1, 18 - w2, -1, l2, boundingBoxIn);
				}
			}
		}
	}

	public static class RoomCrossing extends InfernalNetherBridgePiece {
		public RoomCrossing(final int genDepthIn, final BoundingBox boundingBoxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_ROOM_CROSSING.get(), genDepthIn, boundingBoxIn);
			this.setOrientation(facingIn);
		}

		public RoomCrossing(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_ROOM_CROSSING.get(), compoundIn);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 0);
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 1);
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 2);
			super.addChildren(pieceIn, piecesIn, randomIn);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource randomIn) {
			return List.of(new ChildInfo(Direction.NORTH, 2, 0, false, EndType.DEFAULT), new ChildInfo(Direction.WEST, 0, 2, false, EndType.DEFAULT), new ChildInfo(Direction.EAST, 0, 2, false, EndType.DEFAULT));
		}

		public static final RoomCrossing createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int genDepthIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -2, 0, 0, 7, 9, 7, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new RoomCrossing(genDepthIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState fence1 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence2 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence3 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence4 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);	
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			//air
			this.generateAirBox(levelIn, boundingBoxIn, 0, 2, 0, 6, 7, 6);
			//floor
			this.generateBox(levelIn, boundingBoxIn, 0, 0, 0, 6, 1, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 0, 0, 3, 1, 2, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 0, 4, 3, 1, 6, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 0, 3, 2, 1, 3, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 0, 3, 6, 1, 3, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 0, 2, 2, 1, 2, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 0, 4, 2, 1, 4, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 0, 2, 4, 1, 2, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 0, 4, 4, 1, 4, block2, block2, false);
			//walls
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 0, 1, 6, 0, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 6, 1, 6, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 2, 0, 6, 6, 0, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 2, 6, 6, 6, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 0, 0, 6, 1, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 5, 0, 6, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 2, 0, 6, 6, 1, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 2, 5, 6, 6, 6, block1, block1, false);
			//gates
			this.generateBox(levelIn, boundingBoxIn, 2, 6, 0, 4, 6, 0, block1, block1, false);
			this.placeBlock(levelIn, block2, 3, 6, 0, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 2, 6, 6, 4, 6, 6, block1, block1, false);
			this.placeBlock(levelIn, block2, 3, 6, 6, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 0, 6, 2, 0, 6, 4, block1, block1, false);
			this.placeBlock(levelIn, block2, 0, 6, 3, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 6, 6, 2, 6, 6, 4, block1, block1, false);
			this.placeBlock(levelIn, block2, 6, 6, 3, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 2, 5, 0, 2, 5, 0, fence3, fence3, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 5, 0, 3, 5, 0, fence1, fence1, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 5, 0, 4, 5, 0, fence3, fence3, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 5, 6, 2, 5, 6, fence3, fence3, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 5, 6, 3, 5, 6, fence1, fence1, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 5, 6, 4, 5, 6, fence3, fence3, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 5, 2, 0, 5, 2, fence4, fence4, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 5, 3, 0, 5, 3, fence2, fence2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 5, 4, 0, 5, 4, fence4, fence4, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 5, 2, 6, 5, 2, fence4, fence4, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 5, 3, 6, 5, 3, fence2, fence2, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 5, 4, 6, 5, 4, fence4, fence4, false);
			//bottom
			this.fillColumnDown(levelIn, block1, 0, -1, 0, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 0, -1, 6, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 6, -1, 0, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 6, -1, 6, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 5, -1, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 5, -1, 6, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 1, -1, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 1, -1, 6, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 0, -1, 5, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 6, -1, 5, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 0, -1, 1, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 6, -1, 1, boundingBoxIn);
		}
	}

	public static class LavaRoomCrossing extends InfernalNetherBridgePiece {
		private boolean hasPlacedSpawner;

		public LavaRoomCrossing(final int genDepthIn, final BoundingBox boundingBoxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_LAVA_ROOM_CROSSING.get(), genDepthIn, boundingBoxIn);
			this.setOrientation(facingIn);
		}

		public LavaRoomCrossing(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_LAVA_ROOM_CROSSING.get(), compoundIn);
			this.hasPlacedSpawner = compoundIn.getBoolean("Mob");
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 0);
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 1);
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 2);
			super.addChildren(pieceIn, piecesIn, randomIn);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource randomIn) {
			return List.of(new ChildInfo(Direction.NORTH, 8, 3, false, EndType.THICK), new ChildInfo(Direction.WEST, 3, 8, false, EndType.THICK), new ChildInfo(Direction.EAST, 3, 8, false, EndType.THICK));
		}

		@Override
		protected int maxPlacementChunkRange() {
			return 6;
		}

		public static final LavaRoomCrossing createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int genDepthIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -8, -3, 0, 19, 11, 19, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new LavaRoomCrossing(genDepthIn, box, facingIn) : null;
		}

		@Override
		protected void addAdditionalSaveData(final StructurePieceSerializationContext contextIn, final CompoundTag compoundIn) {
			super.addAdditionalSaveData(contextIn, compoundIn);
			compoundIn.putBoolean("Mob", this.hasPlacedSpawner);
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = Blocks.RED_NETHER_BRICK_WALL.defaultBlockState();
			final BlockState block4 = Blocks.NETHER_BRICK_FENCE.defaultBlockState();
			final BlockState block5 = BWBlocks.BLAZE_LANTERN.get().defaultBlockState();
			final BlockState block6 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState();
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
			this.generateAirBox(levelIn, boundingBoxIn, 8, 5, 0, 10, 10, 18);
			this.generateAirBox(levelIn, boundingBoxIn, 0, 5, 8, 18, 10, 10);
			this.generateAirBox(levelIn, boundingBoxIn, 6, 5, 5, 12, 10, 13);
			this.generateAirBox(levelIn, boundingBoxIn, 5, 5, 6, 13, 10, 12);
			//structure
			this.placeBlock(levelIn, block3, 7, 6, 4, boundingBoxIn);
			this.placeBlock(levelIn, block3, 7, 6, 14, boundingBoxIn);
			this.placeBlock(levelIn, block3, 11, 6, 4, boundingBoxIn);
			this.placeBlock(levelIn, block3, 11, 6, 14, boundingBoxIn);
			this.placeBlock(levelIn, block3, 4, 6, 7, boundingBoxIn);
			this.placeBlock(levelIn, block3, 14, 6, 7, boundingBoxIn);
			this.placeBlock(levelIn, block3, 4, 6, 11, boundingBoxIn);
			this.placeBlock(levelIn, block3, 14, 6, 11, boundingBoxIn);
			this.placeBlock(levelIn, block4, 7, 7, 4, boundingBoxIn);
			this.placeBlock(levelIn, block4, 7, 7, 14, boundingBoxIn);
			this.placeBlock(levelIn, block4, 11, 7, 4, boundingBoxIn);
			this.placeBlock(levelIn, block4, 11, 7, 14, boundingBoxIn);
			this.placeBlock(levelIn, block4, 4, 7, 7, boundingBoxIn);
			this.placeBlock(levelIn, block4, 14, 7, 7, boundingBoxIn);
			this.placeBlock(levelIn, block4, 4, 7, 11, boundingBoxIn);
			this.placeBlock(levelIn, block4, 14, 7, 11, boundingBoxIn);
			this.placeBlock(levelIn, block3, 7, 8, 4, boundingBoxIn);
			this.placeBlock(levelIn, block3, 7, 8, 14, boundingBoxIn);
			this.placeBlock(levelIn, block3, 11, 8, 4, boundingBoxIn);
			this.placeBlock(levelIn, block3, 11, 8, 14, boundingBoxIn);
			this.placeBlock(levelIn, block3, 4, 8, 7, boundingBoxIn);
			this.placeBlock(levelIn, block3, 14, 8, 7, boundingBoxIn);
			this.placeBlock(levelIn, block3, 4, 8, 11, boundingBoxIn);
			this.placeBlock(levelIn, block3, 14, 8, 11, boundingBoxIn);
			this.placeBlock(levelIn, block6, 5, 6, 5, boundingBoxIn);
			this.placeBlock(levelIn, block6, 5, 6, 13, boundingBoxIn);
			this.placeBlock(levelIn, block6, 13, 6, 5, boundingBoxIn);
			this.placeBlock(levelIn, block6, 13, 6, 13, boundingBoxIn);
			this.placeBlock(levelIn, block5, 5, 7, 5, boundingBoxIn);
			this.placeBlock(levelIn, block5, 5, 7, 13, boundingBoxIn);
			this.placeBlock(levelIn, block5, 13, 7, 5, boundingBoxIn);
			this.placeBlock(levelIn, block5, 13, 7, 13, boundingBoxIn);
			//structure top
			this.placeBlock(levelIn, slab4, 5, 9, 5, boundingBoxIn);
			this.placeBlock(levelIn, slab4, 5, 9, 13, boundingBoxIn);
			this.placeBlock(levelIn, slab4, 13, 9, 5, boundingBoxIn);
			this.placeBlock(levelIn, slab4, 13, 9, 13, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 6, 9, 4, boundingBoxIn);
			this.placeBlock(levelIn, block1, 7, 9, 4, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 8, 9, 4, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 6, 9, 14, boundingBoxIn);
			this.placeBlock(levelIn, block1, 7, 9, 14, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 8, 9, 14, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 9, 9, 4, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 9, 9, 14, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 10, 9, 4, boundingBoxIn);
			this.placeBlock(levelIn, block1, 11, 9, 4, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 12, 9, 4, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 10, 9, 14, boundingBoxIn);
			this.placeBlock(levelIn, block1, 11, 9, 14, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 12, 9, 14, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 4, 9, 6, boundingBoxIn);
			this.placeBlock(levelIn, block1, 4, 9, 7, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 4, 9, 8, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 14, 9, 6, boundingBoxIn);
			this.placeBlock(levelIn, block1, 14, 9, 7, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 14, 9, 8, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 4, 9, 9, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 14, 9, 9, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 4, 9, 10, boundingBoxIn);
			this.placeBlock(levelIn, block1, 4, 9, 11, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 4, 9, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 14, 9, 10, boundingBoxIn);
			this.placeBlock(levelIn, block1, 14, 9, 11, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 14, 9, 12, boundingBoxIn);
			//structure top2
			this.placeBlock(levelIn, slab4, 6, 9, 6, boundingBoxIn);
			this.placeBlock(levelIn, slab4, 6, 9, 12, boundingBoxIn);
			this.placeBlock(levelIn, slab4, 12, 9, 6, boundingBoxIn);
			this.placeBlock(levelIn, slab4, 12, 9, 12, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 6, 9, 5, 8, 9, 5, slab4, slab4, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 9, 13, 8, 9, 13, slab4, slab4, false);
			this.placeBlock(levelIn, slab2, 9, 9, 5, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 9, 9, 13, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 10, 9, 5, 12, 9, 5, slab4, slab4, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 9, 13, 12, 9, 13, slab4, slab4, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 9, 6, 5, 9, 8, slab4, slab4, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 9, 6, 13, 9, 8, slab4, slab4, false);
			this.placeBlock(levelIn, slab2, 5, 9, 9, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 13, 9, 9, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 5, 9, 10, 5, 9, 12, slab4, slab4, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 9, 10, 13, 9, 12, slab4, slab4, false);
			this.placeBlock(levelIn, slab3, 6, 10, 6, boundingBoxIn);
			this.placeBlock(levelIn, slab3, 6, 10, 12, boundingBoxIn);
			this.placeBlock(levelIn, slab3, 12, 10, 6, boundingBoxIn);
			this.placeBlock(levelIn, slab3, 12, 10, 12, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 6, 10, 5, 8, 10, 5, slab3, slab3, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 10, 13, 8, 10, 13, slab3, slab3, false);
			this.placeBlock(levelIn, slab1, 9, 10, 5, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 9, 10, 13, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 10, 10, 5, 12, 10, 5, slab3, slab3, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 10, 13, 12, 10, 13, slab3, slab3, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 10, 6, 5, 10, 8, slab3, slab3, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 10, 6, 13, 10, 8, slab3, slab3, false);
			this.placeBlock(levelIn, slab1, 5, 10, 9, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 13, 10, 9, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 5, 10, 10, 5, 10, 12, slab3, slab3, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 10, 10, 13, 10, 12, slab3, slab3, false);
			//structure top3
			this.placeBlock(levelIn, block1, 7, 10, 7, boundingBoxIn);
			this.placeBlock(levelIn, block1, 7, 10, 11, boundingBoxIn);
			this.placeBlock(levelIn, block1, 11, 10, 7, boundingBoxIn);
			this.placeBlock(levelIn, block1, 11, 10, 11, boundingBoxIn);
			this.placeBlock(levelIn, block2, 9, 10, 6, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 7, 10, 6, 8, 10, 6, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 10, 6, 11, 10, 6, block2, block2, false);
			this.placeBlock(levelIn, block2, 9, 10, 12, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 7, 10, 12, 8, 10, 12, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 10, 12, 11, 10, 12, block2, block2, false);
			this.placeBlock(levelIn, block2, 6, 10, 9, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 6, 10, 7, 6, 10, 8, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 10, 10, 6, 10, 11, block2, block2, false);
			this.placeBlock(levelIn, block2, 12, 10, 9, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 12, 10, 7, 12, 10, 8, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 10, 10, 12, 10, 11, block2, block2, false);
			//structure top4
			this.placeBlock(levelIn, slab2, 8, 10, 8, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 8, 10, 10, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 10, 10, 8, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 10, 10, 10, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 9, 10, 7, boundingBoxIn);
			this.placeBlock(levelIn, slab4, 8, 10, 7, boundingBoxIn);
			this.placeBlock(levelIn, slab4, 10, 10, 7, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 9, 10, 11, boundingBoxIn);
			this.placeBlock(levelIn, slab4, 8, 10, 11, boundingBoxIn);
			this.placeBlock(levelIn, slab4, 10, 10, 11, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 7, 10, 9, boundingBoxIn);
			this.placeBlock(levelIn, slab4, 7, 10, 8, boundingBoxIn);
			this.placeBlock(levelIn, slab4, 7, 10, 10, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 11, 10, 9, boundingBoxIn);
			this.placeBlock(levelIn, slab4, 11, 10, 8, boundingBoxIn);
			this.placeBlock(levelIn, slab4, 11, 10, 10, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 8, 11, 8, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 8, 11, 10, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 10, 11, 8, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 10, 11, 10, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 9, 11, 7, boundingBoxIn);
			this.placeBlock(levelIn, slab3, 8, 11, 7, boundingBoxIn);
			this.placeBlock(levelIn, slab3, 10, 11, 7, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 9, 11, 11, boundingBoxIn);
			this.placeBlock(levelIn, slab3, 8, 11, 11, boundingBoxIn);
			this.placeBlock(levelIn, slab3, 10, 11, 11, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 7, 11, 9, boundingBoxIn);
			this.placeBlock(levelIn, slab3, 7, 11, 8, boundingBoxIn);
			this.placeBlock(levelIn, slab3, 7, 11, 10, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 11, 11, 9, boundingBoxIn);
			this.placeBlock(levelIn, slab3, 11, 11, 8, boundingBoxIn);
			this.placeBlock(levelIn, slab3, 11, 11, 10, boundingBoxIn);
			//structure top5
			this.placeBlock(levelIn, block2, 8, 11, 9, boundingBoxIn);
			this.placeBlock(levelIn, block2, 9, 11, 8, boundingBoxIn);
			this.placeBlock(levelIn, block2, 10, 11, 9, boundingBoxIn);
			this.placeBlock(levelIn, block2, 9, 11, 10, boundingBoxIn);
			this.placeBlock(levelIn, block1, 9, 11, 9, boundingBoxIn);
			//top
			this.generateBox(levelIn, boundingBoxIn, 7, 4, 0, 8, 4, 5, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 4, 6, 8, 4, 6, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 4, 13, 8, 4, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 4, 12, 8, 4, 12, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 9, 4, 0, 9, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 9, 4, 10, 9, 4, 18, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 4, 0, 11, 4, 5, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 4, 6, 11, 4, 6, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 4, 13, 11, 4, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 4, 12, 11, 4, 12, block2, block2, false);
			//center
			this.placeBlock(levelIn, stair9, 9, 5, 8, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 9, 5, 10, boundingBoxIn);
			this.placeBlock(levelIn, stair11, 8, 5, 9, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 10, 5, 9, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 8, 5, 8, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 8, 5, 10, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 10, 5, 8, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 10, 5, 10, boundingBoxIn);
			this.placeBlock(levelIn, block2, 8, 4, 8, boundingBoxIn);
			this.placeBlock(levelIn, block2, 8, 4, 10, boundingBoxIn);
			this.placeBlock(levelIn, block2, 10, 4, 8, boundingBoxIn);
			this.placeBlock(levelIn, block2, 10, 4, 10, boundingBoxIn);
			this.placeBlock(levelIn, block1, 7, 4, 7, boundingBoxIn);
			this.placeBlock(levelIn, block1, 7, 4, 8, boundingBoxIn);
			this.placeBlock(levelIn, block1, 8, 4, 7, boundingBoxIn);
			this.placeBlock(levelIn, block1, 7, 4, 11, boundingBoxIn);
			this.placeBlock(levelIn, block1, 7, 4, 10, boundingBoxIn);
			this.placeBlock(levelIn, block1, 8, 4, 11, boundingBoxIn);
			this.placeBlock(levelIn, block1, 11, 4, 7, boundingBoxIn);
			this.placeBlock(levelIn, block1, 10, 4, 7, boundingBoxIn);
			this.placeBlock(levelIn, block1, 11, 4, 8, boundingBoxIn);
			this.placeBlock(levelIn, block1, 11, 4, 11, boundingBoxIn);
			this.placeBlock(levelIn, block1, 10, 4, 11, boundingBoxIn);
			this.placeBlock(levelIn, block1, 11, 4, 10, boundingBoxIn);
			//center corners
			this.generateBox(levelIn, boundingBoxIn, 6, 3, 6, 6, 4, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 3, 5, 6, 4, 5, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 3, 6, 5, 4, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 3, 12, 6, 4, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 3, 13, 6, 4, 13, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 3, 12, 5, 4, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 3, 6, 12, 4, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 3, 6, 13, 4, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 3, 5, 12, 4, 5, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 3, 12, 12, 4, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 3, 13, 12, 4, 13, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 3, 12, 13, 4, 12, block1, block1, false);
			//center rails
			this.generateBox(levelIn, boundingBoxIn, 5, 4, 5, 5, 5, 5, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 4, 6, 4, 5, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 4, 4, 6, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 4, 13, 5, 5, 13, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 4, 14, 6, 5, 14, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 4, 12, 4, 5, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 4, 5, 13, 5, 5, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 14, 4, 6, 14, 5, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 4, 4, 12, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 4, 13, 13, 5, 13, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 14, 4, 12, 14, 5, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 4, 14, 12, 5, 14, block1, block1, false);
			//top2
			this.generateBox(levelIn, boundingBoxIn, 0, 4, 7, 5, 4, 8, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 4, 7, 6, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 4, 7, 18, 4, 8, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 4, 7, 12, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 4, 9, 8, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 4, 9, 18, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 4, 10, 5, 4, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 4, 10, 6, 4, 11, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 4, 10, 18, 4, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 4, 10, 12, 4, 11, block2, block2, false);
			//bottom
			this.generateBox(levelIn, boundingBoxIn, 7, 3, 0, 11, 3, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 7, 18, 3, 11, block1, block1, false);
			//rails
			this.generateBox(levelIn, boundingBoxIn, 7, 5, 0, 7, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 5, 14, 7, 5, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 11, 5, 0, 11, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 11, 5, 14, 11, 5, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 5, 7, 4, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 14, 5, 7, 18, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 5, 11, 4, 5, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 14, 5, 11, 18, 5, 11, block1, block1, false);
			//pillars z-Axis
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 0, 11, 2, 0, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 4, 11, 2, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 18, 11, 2, 18, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 14, 11, 2, 14, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 3, 11, 2, 3, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 5, 11, 2, 5, stair2, stair2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 15, 11, 2, 15, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 13, 11, 2, 13, stair1, stair1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 1, 11, 1, 1, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 2, 11, 1, 2, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 0, 1, 11, 0, 2, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 0, 11, 1, 0, stair1, stair1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 18, 11, 1, 18, stair2, stair2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 16, 11, 1, 16, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 17, 11, 1, 17, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 0, 16, 11, 0, 17, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 3, 11, 1, 3, stair2, stair2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 1, 15, 11, 1, 15, stair1, stair1, false);
			for (int w1 = 7; w1 <= 11; ++w1) {
				for (int l1 = 1; l1 <= 2; ++l1) {
					this.fillColumnDown(levelIn, block1, w1, -1, l1, boundingBoxIn);
					this.fillColumnDown(levelIn, block1, w1, -1, 18 - l1, boundingBoxIn);
				}
			}
			//pillars x-axis
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 7, 0, 2, 11, stair8, stair8, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 2, 7, 4, 2, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 18, 2, 7, 18, 2, 11, stair7, stair7, false);
			this.generateBox(levelIn, boundingBoxIn, 14, 2, 7, 14, 2, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 2, 7, 3, 2, 11, stair7, stair7, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 2, 7, 5, 2, 11, stair6, stair6, false);
			this.generateBox(levelIn, boundingBoxIn, 15, 2, 7, 15, 2, 11, stair8, stair8, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 2, 7, 13, 2, 11, stair5, stair5, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 1, 7, 1, 1, 11, stair8, stair8, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 1, 7, 2, 1, 11, stair7, stair7, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 0, 7, 2, 0, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 1, 7, 0, 1, 11, stair5, stair5, false);
			this.generateBox(levelIn, boundingBoxIn, 18, 1, 7, 18, 1, 11, stair6, stair6, false);
			this.generateBox(levelIn, boundingBoxIn, 16, 1, 7, 16, 1, 11, stair8, stair8, false);
			this.generateBox(levelIn, boundingBoxIn, 17, 1, 7, 17, 1, 11, stair7, stair7, false);
			this.generateBox(levelIn, boundingBoxIn, 16, 0, 7, 17, 0, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 1, 7, 3, 1, 11, stair6, stair6, false);
			this.generateBox(levelIn, boundingBoxIn, 15, 1, 7, 15, 1, 11, stair5, stair5, false);
			for (int w2 = 1; w2 <= 2; ++w2) {
				for (int l2 = 7; l2 <= 11; ++l2) {
					this.fillColumnDown(levelIn, block1, w2, -1, l2, boundingBoxIn);
					this.fillColumnDown(levelIn, block1, 18 - w2, -1, l2, boundingBoxIn);
				}
			}
			//fluids
			this.placeBlock(levelIn, fluid1, 9, 5, 9, boundingBoxIn);
			//spawners
			if (!this.hasPlacedSpawner) {
				final BlockPos spawnerPos = new BlockPos(this.getWorldX(9, 9), this.getWorldY(4), this.getWorldZ(9, 9));
				if (boundingBoxIn.isInside(spawnerPos)) {
					this.hasPlacedSpawner = true;
					levelIn.setBlock(spawnerPos, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = levelIn.getBlockEntity(spawnerPos);
					if (spawner instanceof SpawnerBlockEntity)
						((SpawnerBlockEntity)spawner).setEntityId(EntityType.MAGMA_CUBE, randomIn);
				}
			}
		}
	}

	public static class CastleEntrance extends InfernalNetherBridgePiece {
		public CastleEntrance(final int genDepthIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_CASTLE_ENTRANCE.get(), genDepthIn, boundingBoxIn);
			this.setOrientation(facingIn);
		}

		public CastleEntrance(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_CASTLE_ENTRANCE.get(), compoundIn);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 0);
			super.addChildren(pieceIn, piecesIn, randomIn);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource randomIn) {
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

		public static final CastleEntrance createPiece(final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int genDepthIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -5, -3, 0, 13, 14, 13, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new CastleEntrance(genDepthIn, randomIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block4 = BWBlocks.BLAZE_LANTERN.get().defaultBlockState().setValue(BlazeLanternBlock.HANGING, Boolean.valueOf(true));
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
			final BlockState fence3 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence4 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence5 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence6 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence7 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true));
			final BlockState fence8 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true));
			final BlockState fence9 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState();
			final BlockState slab1 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState();
			final BlockState slab2 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP);
			final BlockState slab3 = Blocks.NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP);
			final BlockState wall1 = Blocks.NETHER_BRICK_WALL.defaultBlockState();
			final BlockState wall2 = Blocks.RED_NETHER_BRICK_WALL.defaultBlockState();
			final BlockState fluid1 = Blocks.LAVA.defaultBlockState();
			//air
			this.generateAirBox(levelIn, boundingBoxIn, 0, 5, 0, 12, 13, 12);
			//floor
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 0, 12, 4, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 3, 0, 6, 4, 5, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 3, 7, 6, 4, 12, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 3, 6, 5, 4, 6, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 3, 6, 8, 4, 6, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 3, 3, 8, 4, 3, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 3, 9, 8, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 3, 4, 3, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 9, 3, 4, 9, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 3, 5, 5, 4, 5, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 3, 7, 5, 4, 7, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 3, 5, 7, 4, 5, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 3, 7, 7, 4, 7, block2, block2, false);	
			this.generateBox(levelIn, boundingBoxIn, 6, 4, 7, 6, 4, 7, stair1, stair1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 4, 5, 6, 4, 5, stair2, stair2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 4, 6, 7, 4, 6, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 4, 6, 5, 4, 6, stair4, stair4, false);
			this.generateAirBox(levelIn, boundingBoxIn, 6, 1, 6, 6, 4, 6);
			//walls
			this.generateBox(levelIn, boundingBoxIn, 0, 5, 0, 1, 12, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 11, 5, 0, 12, 12, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 5, 11, 4, 12, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 8, 5, 11, 10, 12, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 9, 11, 7, 12, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 5, 0, 4, 12, 1, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 8, 5, 0, 10, 12, 1, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 9, 0, 7, 12, 1, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 8, 0, 7, 8, 1, stair5, stair5, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 8, 0, 5, 8, 1, stair6, stair6, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 8, 11, 7, 8, 12, stair5, stair5, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 8, 11, 5, 8, 12, stair6, stair6, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 9, 1, 6, 9, 1, block4, block4, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 9, 11, 6, 9, 11, block4, block4, false);
			//roof
			this.generateBox(levelIn, boundingBoxIn, 0, 11, 0, 12, 12, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 11, 4, 6, 12, 5, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 11, 7, 6, 12, 8, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 11, 6, 5, 12, 6, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 11, 6, 8, 12, 6, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 11, 3, 8, 12, 3, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 11, 9, 8, 12, 9, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 11, 4, 3, 12, 8, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 9, 11, 4, 9, 12, 8, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 11, 5, 5, 12, 5, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 11, 7, 5, 12, 7, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 11, 5, 7, 12, 5, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 11, 7, 7, 12, 7, block2, block2, false);
			//fences
			for (int i = 1; i <= 11; i += 2) {
				this.generateBox(levelIn, boundingBoxIn, i, 10, 0, i, 11, 0, fence1, fence1, false);
				this.generateBox(levelIn, boundingBoxIn, i, 9, 0, i, 9, 0, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, i, 12, 0, i, 12, 0, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, i, 10, 12, i, 11, 12, fence1, fence1, false);
				this.generateBox(levelIn, boundingBoxIn, i, 9, 12, i, 9, 12, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, i, 12, 12, i, 12, 12, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, 0, 10, i, 0, 11, i, fence2, fence2, false);
				this.generateBox(levelIn, boundingBoxIn, 0, 9, i, 0, 9, i, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, 0, 12, i, 0, 12, i, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, 12, 10, i, 12, 11, i, fence2, fence2, false);
				this.generateBox(levelIn, boundingBoxIn, 12, 9, i, 12, 9, i, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, 12, 12, i, 12, 12, i, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, i, 13, 0, i, 13, 0, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, i, 13, 12, i, 13, 12, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, 0, 13, i, 0, 13, i, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, 12, 13, i, 12, 13, i, block2, block2, false);
				if (i != 11) {
					this.placeBlock(levelIn, fence3, i + 1, 13, 0, boundingBoxIn);
					this.placeBlock(levelIn, fence3, i + 1, 13, 12, boundingBoxIn);
					this.placeBlock(levelIn, fence4, 0, 13, i + 1, boundingBoxIn);
					this.placeBlock(levelIn, fence4, 12, 13, i + 1, boundingBoxIn);
				}
			}
			this.placeBlock(levelIn, fence5, 0, 13, 0, boundingBoxIn);
			this.placeBlock(levelIn, fence6, 0, 13, 12, boundingBoxIn);
			this.placeBlock(levelIn, fence7, 12, 13, 12, boundingBoxIn);
			this.placeBlock(levelIn, fence8, 12, 13, 0, boundingBoxIn);
			for (int k = 3; k <= 9; k += 2) {
				this.generateBox(levelIn, boundingBoxIn, 1, 7, k, 1, 8, k, fence2.setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)), fence2.setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)), false);
				this.generateBox(levelIn, boundingBoxIn, 1, 6, k, 1, 6, k, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, 1, 9, k, 1, 9, k, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, 11, 7, k, 11, 8, k, fence2.setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true)), fence2.setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true)), false);
				this.generateBox(levelIn, boundingBoxIn, 11, 6, k, 11, 6, k, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, 11, 9, k, 11, 9, k, block2, block2, false);
			}
			//corners
			this.placeBlock(levelIn, slab1, 2, 5, 3, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 3, 5, 2, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 9, 5, 2, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 10, 5, 3, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 2, 5, 9, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 3, 5, 10, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 9, 5, 10, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 10, 5, 9, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 2, 10, 3, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 3, 10, 2, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 9, 10, 2, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 10, 10, 3, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 2, 10, 9, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 3, 10, 10, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 9, 10, 10, boundingBoxIn);
			this.placeBlock(levelIn, slab2, 10, 10, 9, boundingBoxIn);
			this.placeBlock(levelIn, fluid1, 2, 10, 2, boundingBoxIn);
			this.placeBlock(levelIn, fluid1, 10, 10, 2, boundingBoxIn);
			this.placeBlock(levelIn, fluid1, 2, 10, 10, boundingBoxIn);
			this.placeBlock(levelIn, fluid1, 10, 10, 10, boundingBoxIn);
			//center
			this.generateBox(levelIn, boundingBoxIn, 4, 10, 5, 8, 10, 5, slab2, slab2, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 10, 4, 5, 10, 8, slab2, slab2, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 10, 7, 8, 10, 7, slab2, slab2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 10, 4, 7, 10, 8, slab2, slab2, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 10, 5, 6, 10, 5, slab3, slab3, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 10, 6, 5, 10, 6, slab3, slab3, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 10, 7, 6, 10, 7, slab3, slab3, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 10, 6, 7, 10, 6, slab3, slab3, false);
			this.placeBlock(levelIn, wall1, 5, 5, 5, boundingBoxIn);
			this.placeBlock(levelIn, fence9, 5, 6, 5, boundingBoxIn);
			this.placeBlock(levelIn, wall2, 5, 7, 5, boundingBoxIn);
			this.placeBlock(levelIn, fence9, 5, 8, 5, boundingBoxIn);
			this.placeBlock(levelIn, wall1, 5, 9, 5, boundingBoxIn);
			this.placeBlock(levelIn, block2, 5, 10, 5, boundingBoxIn);
			this.placeBlock(levelIn, wall1, 5, 5, 7, boundingBoxIn);
			this.placeBlock(levelIn, fence9, 5, 6, 7, boundingBoxIn);
			this.placeBlock(levelIn, wall2, 5, 7, 7, boundingBoxIn);
			this.placeBlock(levelIn, fence9, 5, 8, 7, boundingBoxIn);
			this.placeBlock(levelIn, wall1, 5, 9, 7, boundingBoxIn);
			this.placeBlock(levelIn, block2, 5, 10, 7, boundingBoxIn);
			this.placeBlock(levelIn, wall1, 7, 5, 5, boundingBoxIn);
			this.placeBlock(levelIn, fence9, 7, 6, 5, boundingBoxIn);
			this.placeBlock(levelIn, wall2, 7, 7, 5, boundingBoxIn);
			this.placeBlock(levelIn, fence9, 7, 8, 5, boundingBoxIn);
			this.placeBlock(levelIn, wall1, 7, 9, 5, boundingBoxIn);
			this.placeBlock(levelIn, block2, 7, 10, 5, boundingBoxIn);
			this.placeBlock(levelIn, wall1, 7, 5, 7, boundingBoxIn);
			this.placeBlock(levelIn, fence9, 7, 6, 7, boundingBoxIn);
			this.placeBlock(levelIn, wall2, 7, 7, 7, boundingBoxIn);
			this.placeBlock(levelIn, fence9, 7, 8, 7, boundingBoxIn);
			this.placeBlock(levelIn, wall1, 7, 9, 7, boundingBoxIn);
			this.placeBlock(levelIn, block2, 7, 10, 7, boundingBoxIn);
			this.placeBlock(levelIn, fluid1, 6, 10, 6, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 6, 0, 6, boundingBoxIn);
			//base pillars
			this.fillColumnDown(levelIn, block1, 0, 2, 0, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 0, 2, 12, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 12, 2, 0, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 12, 2, 12, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 0, 1, 6, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 6, 1, 0, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 6, 1, 12, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 12, 1, 6, boundingBoxIn);
			this.placeBlock(levelIn, block5, 0, 2, 6, boundingBoxIn);
			this.placeBlock(levelIn, block5, 6, 2, 0, boundingBoxIn);
			this.placeBlock(levelIn, block5, 6, 2, 12, boundingBoxIn);
			this.placeBlock(levelIn, block5, 12, 2, 6, boundingBoxIn);
			//pillars x
			this.placeBlock(levelIn, stair5, 11, 0, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 11, 1, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 10, 1, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 10, 2, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 9, 2, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 11, 0, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 11, 1, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 10, 1, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 10, 2, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 9, 2, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 5, 1, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 5, 2, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 4, 2, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 5, 1, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 5, 2, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 4, 2, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 1, 0, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair9, 1, 1, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 2, 1, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair9, 2, 2, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 3, 2, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 1, 0, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair9, 1, 1, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 2, 1, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair9, 2, 2, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 3, 2, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 7, 1, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair9, 7, 2, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 8, 2, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 7, 1, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair9, 7, 2, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 8, 2, 12, boundingBoxIn);
			//pillars z
			this.placeBlock(levelIn, stair7, 0, 0, 11, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 0, 1, 11, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 0, 1, 10, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 0, 2, 10, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 0, 2, 9, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 12, 0, 11, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 12, 1, 11, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 12, 1, 10, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 12, 2, 10, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 12, 2, 9, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 0, 1, 5, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 0, 2, 5, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 0, 2, 4, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 12, 1, 5, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 12, 2, 5, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 12, 2, 4, boundingBoxIn);
			this.placeBlock(levelIn, stair8, 0, 0, 1, boundingBoxIn);
			this.placeBlock(levelIn, stair11, 0, 1, 1, boundingBoxIn);
			this.placeBlock(levelIn, stair8, 0, 1, 2, boundingBoxIn);
			this.placeBlock(levelIn, stair11, 0, 2, 2, boundingBoxIn);
			this.placeBlock(levelIn, stair8, 0, 2, 3, boundingBoxIn);
			this.placeBlock(levelIn, stair8, 12, 0, 1, boundingBoxIn);
			this.placeBlock(levelIn, stair11, 12, 1, 1, boundingBoxIn);
			this.placeBlock(levelIn, stair8, 12, 1, 2, boundingBoxIn);
			this.placeBlock(levelIn, stair11, 12, 2, 2, boundingBoxIn);
			this.placeBlock(levelIn, stair8, 12, 2, 3, boundingBoxIn);
			this.placeBlock(levelIn, stair8, 0, 1, 7, boundingBoxIn);
			this.placeBlock(levelIn, stair11, 0, 2, 7, boundingBoxIn);
			this.placeBlock(levelIn, stair8, 0, 2, 8, boundingBoxIn);
			this.placeBlock(levelIn, stair8, 12, 1, 7, boundingBoxIn);
			this.placeBlock(levelIn, stair11, 12, 2, 7, boundingBoxIn);
			this.placeBlock(levelIn, stair8, 12, 2, 8, boundingBoxIn);
		}
	}

	public static class StairsRoom extends InfernalNetherBridgePiece {
		private boolean hasPlacedSpawner;

		public StairsRoom(final int genDepthIn, final BoundingBox boundingBoxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_STAIRS_ROOM.get(), genDepthIn, boundingBoxIn);
			this.setOrientation(facingIn);
		}

		public StairsRoom(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_STAIRS_ROOM.get(), compoundIn);
			this.hasPlacedSpawner = compoundIn.getBoolean("Mob");
		}

		@Override
		public void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 0);
			super.addChildren(pieceIn, piecesIn, randomIn);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource randomIn) {
			return List.of(new ChildInfo(Direction.EAST, 6, 2, false, EndType.DEFAULT));
		}

		public static final StairsRoom createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int genDepthIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -2, 0, 0, 7, 11, 7, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new StairsRoom(genDepthIn, box, facingIn) : null;
		}

		@Override
		protected void addAdditionalSaveData(final StructurePieceSerializationContext contextIn, final CompoundTag compoundIn) {
			super.addAdditionalSaveData(contextIn, compoundIn);
			compoundIn.putBoolean("Mob", this.hasPlacedSpawner);
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState fence1 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence2 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence3 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence4 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
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
			this.generateAirBox(levelIn, boundingBoxIn, 0, 2, 0, 6, 10, 6);
			//floor
			this.generateBox(levelIn, boundingBoxIn, 0, 0, 0, 6, 1, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 0, 0, 3, 1, 2, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 0, 2, 2, 1, 4, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 0, 2, 4, 1, 4, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 0, 4, 3, 1, 4, block2, block2, false);
			//walls
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 0, 1, 8, 0, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 2, 0, 6, 8, 0, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 1, 0, 8, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 2, 1, 6, 8, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 2, 6, 5, 8, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 2, 0, 3, 4, fence2, fence2, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 3, 2, 6, 3, 2, fence2, fence2, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 3, 4, 6, 3, 4, fence2, fence2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 4, 2, 0, 4, 4, fence4, fence4, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 4, 2, 6, 4, 2, fence4, fence4, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 4, 4, 6, 4, 4, fence4, fence4, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 5, 2, 0, 5, 4, fence2, fence2, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 5, 2, 6, 5, 2, fence2, fence2, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 5, 4, 6, 5, 4, fence2, fence2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 6, 2, 0, 6, 4, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 6, 2, 6, 6, 2, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 6, 4, 6, 6, 4, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 2, 0, 2, 4, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 2, 2, 6, 2, 2, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 2, 4, 6, 2, 4, block2, block2, false);
			//gate
			this.generateAirBox(levelIn, boundingBoxIn, 6, 8, 2, 6, 8, 4);
			this.generateBox(levelIn, boundingBoxIn, 2, 6, 0, 4, 8, 0, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 5, 0, 2, 5, 0, fence1, fence1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 5, 0, 3, 5, 0, fence3, fence3, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 5, 0, 4, 5, 0, fence1, fence1, false);
			//roof
			this.generateBox(levelIn, boundingBoxIn, 1, 7, 1, 5, 7, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 7, 3, 6, 7, 3, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 7, 2, 4, 7, 2, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 7, 4, 4, 7, 4, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 7, 3, 2, 7, 3, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 8, 0, 5, 8, 0, wall1, wall1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 8, 1, 0, 8, 5, wall2, wall2, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 8, 6, 5, 8, 6, wall1, wall1, false);
			//stairs
			final boolean rand1 = randomIn.nextInt(4) == 0;
			final boolean rand2 = randomIn.nextBoolean();
			this.placeBlock(levelIn, block1, 5, 2, 5, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 5, 2, 4, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 4, 2, 5, 4, 2, 5, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 2, 5, 3, 3, 5, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 2, 5, 2, 4, 5, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 2, 5, 1, 5, 5, block2, block2, false);
			this.placeBlock(levelIn, stair6, 4, 3, 5, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 3, 4, 5, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 2, 5, 5, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 1, 6, 5, boundingBoxIn);
			if (rand1) {
				this.generateAirBox(levelIn, boundingBoxIn, 1, 3, 5, 2, 4, 5);
				this.placeBlock(levelIn, stair1, 2, 4, 5, boundingBoxIn);
				this.placeBlock(levelIn, brewingStand, 1, 3, 5, boundingBoxIn);
				this.placeBlock(levelIn, rand2 ? skull1 : skull2, 2, 3, 5, boundingBoxIn);
			}
			//bottom
			this.fillColumnDown(levelIn, block1, 0, -1, 0, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 0, -1, 6, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 6, -1, 0, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 6, -1, 6, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 5, -1, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 5, -1, 6, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 1, -1, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 1, -1, 6, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 0, -1, 5, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 6, -1, 5, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 0, -1, 1, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 6, -1, 1, boundingBoxIn);
			//spawners
			if (!this.hasPlacedSpawner && rand1) {
				final BlockPos spawnerPos = new BlockPos(this.getWorldX(2, 5), this.getWorldY(2), this.getWorldZ(2, 5));
				if (boundingBoxIn.isInside(spawnerPos)) {
					this.hasPlacedSpawner = true;
					levelIn.setBlock(spawnerPos, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = levelIn.getBlockEntity(spawnerPos);
					if (spawner instanceof SpawnerBlockEntity)
						((SpawnerBlockEntity)spawner).setEntityId(rand2 ? EntityType.SKELETON : EntityType.WITHER_SKELETON, randomIn);
				}
			}
		}
	}

	public static class MonsterThrone extends InfernalNetherBridgePiece {
		private boolean hasPlacedSpawner;

		public MonsterThrone(final int genDepthIn, final BoundingBox boundingBoxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_MONSTER_THRONE.get(), genDepthIn, boundingBoxIn);
			this.setOrientation(facingIn);
		}

		public MonsterThrone(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_MONSTER_THRONE.get(), compoundIn);
			this.hasPlacedSpawner = compoundIn.getBoolean("Mob");
		}

		@Override
		protected final void addAdditionalSaveData(final StructurePieceSerializationContext contextIn, final CompoundTag compoundIn) {
			super.addAdditionalSaveData(contextIn, compoundIn);
			compoundIn.putBoolean("Mob", this.hasPlacedSpawner);
		}

		public static final MonsterThrone createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int genDepthIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -2, 0, 0, 7, 8, 9, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new MonsterThrone(genDepthIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = BWBlocks.BLAZE_LANTERN.get().defaultBlockState();
			final BlockState block4 = BWBlocks.BLAZE_LANTERN.get().defaultBlockState().setValue(BlazeLanternBlock.HANGING, Boolean.valueOf(true));
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair2 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);	
			final BlockState stair5 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair6 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState fence1 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState();
			final BlockState fence2 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence3 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence4 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true));
			final BlockState fence5 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence6 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true));
			final BlockState fence7 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true));
			final BlockState fence8 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence9 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence10 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true));
			final BlockState fence11 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence12 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			//air
			this.generateAirBox(levelIn, boundingBoxIn, 0, 2, 0, 6, 7, 7);
			//stairs
			this.generateBox(levelIn, boundingBoxIn, 1, 0, 0, 5, 1, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 0, 0, 3, 1, 4, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 0, 5, 4, 1, 6, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 2, 1, 5, 2, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 2, 1, 3, 2, 4, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 2, 5, 4, 2, 6, block2, block2, false);
			this.placeBlock(levelIn, stair1, 2, 2, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 3, 2, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 4, 2, 0, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 1, 3, 2, 5, 3, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 3, 2, 3, 3, 4, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 3, 5, 4, 3, 6, block2, block2, false);
			this.placeBlock(levelIn, stair1, 2, 3, 1, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 3, 3, 1, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 4, 3, 1, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 1, 4, 3, 5, 4, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 4, 3, 3, 4, 4, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 4, 5, 4, 4, 6, block2, block2, false);
			this.placeBlock(levelIn, stair1, 2, 4, 2, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 3, 4, 2, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 4, 4, 2, boundingBoxIn);
			//rails
			this.generateBox(levelIn, boundingBoxIn, 1, 2, 0, 1, 4, 2, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 2, 0, 5, 4, 2, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 4, 0, 1, 4, 0, stair1, stair1, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 4, 0, 5, 4, 0, stair1, stair1, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 5, 3, 1, 5, 3, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 5, 3, 5, 5, 3, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 6, 3, 1, 6, 3, fence1, fence1, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 6, 3, 5, 6, 3, fence1, fence1, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 7, 3, 1, 7, 3, block3, block3, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 7, 3, 5, 7, 3, block3, block3, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 5, 2, 1, 5, 2, stair1, stair1, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 5, 2, 5, 5, 2, stair1, stair1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 5, 3, 0, 5, 8, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 5, 3, 6, 5, 8, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 5, 8, 5, 5, 8, block1, block1, false);
			//back
			this.placeBlock(levelIn, fence10, 1, 6, 3, boundingBoxIn);
			this.placeBlock(levelIn, fence11, 5, 6, 3, boundingBoxIn);
			this.placeBlock(levelIn, fence6, 0, 6, 3, boundingBoxIn);
			this.placeBlock(levelIn, fence7, 6, 6, 3, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 0, 6, 4, 0, 6, 7, fence3, fence3, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 6, 4, 6, 6, 7, fence3, fence3, false);
			this.placeBlock(levelIn, fence8, 0, 6, 8, boundingBoxIn);
			this.placeBlock(levelIn, fence9, 6, 6, 8, boundingBoxIn);
			this.placeBlock(levelIn, fence12, 1, 6, 8, boundingBoxIn);
			this.placeBlock(levelIn, fence2, 2, 6, 8, boundingBoxIn);
			this.placeBlock(levelIn, fence12, 3, 6, 8, boundingBoxIn);
			this.placeBlock(levelIn, fence2, 4, 6, 8, boundingBoxIn);
			this.placeBlock(levelIn, fence12, 5, 6, 8, boundingBoxIn);
			this.placeBlock(levelIn, fence11, 1, 7, 8, boundingBoxIn);
			this.placeBlock(levelIn, fence2, 2, 7, 8, boundingBoxIn);
			this.placeBlock(levelIn, fence12, 3, 7, 8, boundingBoxIn);
			this.placeBlock(levelIn, fence2, 4, 7, 8, boundingBoxIn);
			this.placeBlock(levelIn, fence4, 2, 7, 8, boundingBoxIn);
			this.placeBlock(levelIn, block4, 3, 7, 8, boundingBoxIn);
			this.placeBlock(levelIn, fence5, 4, 7, 8, boundingBoxIn);
			this.placeBlock(levelIn, fence10, 5, 7, 8, boundingBoxIn);
			this.placeBlock(levelIn, fence5, 2, 8, 8, boundingBoxIn);
			this.placeBlock(levelIn, fence12, 3, 8, 8, boundingBoxIn);
			this.placeBlock(levelIn, fence4, 4, 8, 8, boundingBoxIn);
			//spawner
			if (!this.hasPlacedSpawner) {
				final BlockPos blockPos = new BlockPos(this.getWorldX(3, 5), this.getWorldY(5), this.getWorldZ(3, 5));
				if (boundingBoxIn.isInside(blockPos)) {
					this.hasPlacedSpawner = true;
					levelIn.setBlock(blockPos, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = levelIn.getBlockEntity(blockPos);
					if (spawner instanceof SpawnerBlockEntity)
						((SpawnerBlockEntity)spawner).setEntityId(EntityType.BLAZE, randomIn);
				}
			}
			//bottom
			this.generateBox(levelIn, boundingBoxIn, 1, -1, 0, 5, -1, 0, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 1, -1, 1, 5, -1, 1, stair6, stair6, false);
			this.placeBlock(levelIn, stair5, 1, -1, 2, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 5, -1, 2, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 1, -1, 3, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 1, -1, 7, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 5, -1, 3, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 5, -1, 7, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 4, -1, 3, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 4, -1, 7, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 2, -1, 3, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 2, -1, 7, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 1, -1, 6, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 5, -1, 6, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 1, -1, 4, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 5, -1, 4, boundingBoxIn);
		}
	}

	public static class GrandMonsterThrone extends InfernalNetherBridgePiece implements TeleportDestinationPiece {
		private final boolean[] hasPlacedSpawner = new boolean[5];
		private final boolean[] isNeedingChest = new boolean[7];

		public GrandMonsterThrone(final int genDepthIn, final BoundingBox boundingBoxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_GRAND_MONSTER_THRONE.get(), genDepthIn, boundingBoxIn);
			this.setOrientation(facingIn);
			for (int i = 0; i < 5; i++)
				this.hasPlacedSpawner[i] = false;
			for (int i = 0; i < 7; i++)
				this.isNeedingChest[i] = true;
		}

		protected GrandMonsterThrone(final int xIn, final int zIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_GRAND_MONSTER_THRONE.get(), 0, StructurePiece.makeBoundingBox(xIn, 64, zIn, facingIn, 15, 13, 23));
			this.setOrientation(facingIn);
			for (int i = 0; i < 5; i++)
				this.hasPlacedSpawner[i] = false;
			for (int i = 0; i < 7; i++)
				this.isNeedingChest[i] = true;
		}

		protected GrandMonsterThrone(final StructurePieceType pieceTypeIn, final CompoundTag compoundIn) {
			super(pieceTypeIn, compoundIn);
			for (int i = 0; i < 5; i++)
				this.hasPlacedSpawner[i] = compoundIn.getBoolean("Mob" + String.valueOf(i));
			for (int i = 0; i < 7; i++)
				this.isNeedingChest[i] = compoundIn.getBoolean("Chest" + String.valueOf(i));
		}

		public GrandMonsterThrone(final CompoundTag compoundIn) {
			this(BWStructurePieceTypes.INF_GRAND_MONSTER_THRONE.get(), compoundIn);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 0);
			super.addChildren(pieceIn, piecesIn, randomIn);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource randomIn) {
			return List.of(new ChildInfo(Direction.NORTH, 6, 1, false, EndType.THICK));
		}

		@Override
		protected final List<Class<? extends InfernalNetherBridgePiece>> getUnacceptableChildren() {
			return List.of(RoomCrossing.class, CastleEntrance.class, StairsRoom.class, MonsterThrone.class);
		}

		@Override
		protected final void addAdditionalSaveData(final StructurePieceSerializationContext contextIn, final CompoundTag compoundIn) {
			super.addAdditionalSaveData(contextIn, compoundIn);
			for (int i = 0; i < 5; i++)
				compoundIn.putBoolean("Mob" + String.valueOf(i), this.hasPlacedSpawner[i]);
			for (int i = 0; i < 7; i++)
				compoundIn.putBoolean("Chest" + String.valueOf(i), this.isNeedingChest[i]);
		}

		public static final GrandMonsterThrone createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int genDepthIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -6, -1, 0, 15, 13, 23, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new GrandMonsterThrone(genDepthIn, box, facingIn) : null;
		}

		@Override
		public final BlockPos getDestination() {
			return this.getWorldPos(7, 6, 8);
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = BWBlocks.BLAZE_LANTERN.get().defaultBlockState();
			final BlockState block4 = BWBlocks.ANCIENT_NETHER_ALTAR.get().defaultBlockState().setValue(AncientNetherAltarBlock.FACING, Direction.NORTH);
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
			final BlockState fence1 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState();
			final BlockState fence2 = Blocks.NETHER_BRICK_FENCE.defaultBlockState();
			final BlockState fence3 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence4 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true));
			final BlockState fence5 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true));
			final BlockState fence6 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true));
			final BlockState fence7 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence8 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true));
			final BlockState wall1 = Blocks.RED_NETHER_BRICK_WALL.defaultBlockState();
			final BlockState wall2 = Blocks.NETHER_BRICK_WALL.defaultBlockState();
			final BlockState slab1 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState();
			//air
			this.generateAirBox(levelIn, boundingBoxIn, 6, 3, 20, 8, 7, 22);
			this.generateAirBox(levelIn, boundingBoxIn, 6, 4, 18, 8, 8, 20);
			this.generateAirBox(levelIn, boundingBoxIn, 6, 5, 15, 8, 9, 18);
			this.generateAirBox(levelIn, boundingBoxIn, 1, 5, 6, 13, 13, 10);
			this.generateAirBox(levelIn, boundingBoxIn, 2, 5, 4, 12, 13, 12);
			this.generateAirBox(levelIn, boundingBoxIn, 3, 5, 3, 11, 13, 13);
			this.generateAirBox(levelIn, boundingBoxIn, 4, 5, 2, 10, 13, 14);
			//stairs
			this.placeBlock(levelIn, stair12, 5, 0, 22, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 6, 0, 22, boundingBoxIn);
			this.placeBlock(levelIn, stair13, 7, 0, 22, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 8, 0, 22, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 9, 0, 22, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 5, 1, 22, 9, 1, 22, block1, block1, false);
			this.placeBlock(levelIn, stair12, 5, 1, 21, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 6, 1, 21, boundingBoxIn);
			this.placeBlock(levelIn, stair13, 7, 1, 21, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 8, 1, 21, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 9, 1, 21, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 5, 2, 20, 6, 2, 22, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 20, 7, 2, 22, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 8, 2, 20, 9, 2, 22, block1, block1, false);
			this.placeBlock(levelIn, stair3, 6, 3, 20, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 7, 3, 20, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 8, 3, 20, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 5, 2, 19, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 6, 2, 19, boundingBoxIn);
			this.placeBlock(levelIn, stair13, 7, 2, 19, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 8, 2, 19, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 9, 2, 19, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 5, 3, 18, 6, 3, 19, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 3, 18, 7, 3, 19, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 8, 3, 18, 9, 3, 19, block1, block1, false);
			this.placeBlock(levelIn, stair3, 6, 4, 18, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 7, 4, 18, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 8, 4, 18, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 5, 3, 17, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 6, 3, 17, boundingBoxIn);
			this.placeBlock(levelIn, stair13, 7, 3, 17, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 8, 3, 17, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 9, 3, 17, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 5, 4, 15, 6, 4, 17, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 4, 15, 7, 4, 17, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 8, 4, 15, 9, 4, 17, block1, block1, false);
			//rails
			this.generateBox(levelIn, boundingBoxIn, 5, 3, 20, 5, 3, 22, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 9, 3, 20, 9, 3, 22, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 4, 18, 5, 4, 20, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 9, 4, 18, 9, 4, 20, block1, block1, false);
			this.placeBlock(levelIn, stair3, 5, 4, 21, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 9, 4, 21, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 5, 5, 15, 5, 5, 18, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 9, 5, 15, 9, 5, 18, block1, block1, false);
			this.placeBlock(levelIn, stair3, 5, 5, 19, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 9, 5, 19, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 4, 4, 15, 4, 5, 15, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 4, 15, 10, 5, 15, block1, block1, false);
			//center rails
			this.generateBox(levelIn, boundingBoxIn, 3, 4, 14, 3, 5, 14, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 11, 4, 14, 11, 5, 14, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 4, 13, 2, 5, 13, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 4, 13, 12, 5, 13, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 4, 11, 1, 5, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 4, 11, 13, 5, 12, block1, block1, false);
			this.placeBlock(levelIn, wall2, 1, 6, 11, boundingBoxIn);
			this.placeBlock(levelIn, fence2, 1, 7, 11, boundingBoxIn);
			if (!this.hasPlacedSpawner[0]) {
				final BlockPos blockPos1 = new BlockPos(this.getWorldX(1, 11), this.getWorldY(8), this.getWorldZ(1, 11));
				if (boundingBoxIn.isInside(blockPos1)) {
					this.hasPlacedSpawner[0] = true;
					levelIn.setBlock(blockPos1, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = levelIn.getBlockEntity(blockPos1);
					if (spawner instanceof SpawnerBlockEntity)
						((SpawnerBlockEntity)spawner).setEntityId(EntityType.WITHER_SKELETON, randomIn);
				}
			}
			this.placeBlock(levelIn, wall2, 13, 6, 11, boundingBoxIn);
			this.placeBlock(levelIn, fence2, 13, 7, 11, boundingBoxIn);
			if (!this.hasPlacedSpawner[1]) {
				final BlockPos blockPos2 = new BlockPos(this.getWorldX(13, 11), this.getWorldY(8), this.getWorldZ(13, 11));
				if (boundingBoxIn.isInside(blockPos2)) {
					this.hasPlacedSpawner[1] = true;
					levelIn.setBlock(blockPos2, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = levelIn.getBlockEntity(blockPos2);
					if (spawner instanceof SpawnerBlockEntity)
						((SpawnerBlockEntity)spawner).setEntityId(EntityType.WITHER_SKELETON, randomIn);
				}
			}
			this.generateBox(levelIn, boundingBoxIn, 0, 4, 6, 0, 5, 10, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 14, 4, 6, 14, 5, 10, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 4, 4, 1, 5, 5, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 4, 4, 13, 5, 5, block1, block1, false);
			this.placeBlock(levelIn, wall2, 1, 6, 5, boundingBoxIn);
			this.placeBlock(levelIn, fence2, 1, 7, 5, boundingBoxIn);
			if (!this.hasPlacedSpawner[2]) {
				final BlockPos blockPos3 = new BlockPos(this.getWorldX(1, 5), this.getWorldY(8), this.getWorldZ(1, 5));
				if (boundingBoxIn.isInside(blockPos3)) {
					this.hasPlacedSpawner[2] = true;
					levelIn.setBlock(blockPos3, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = levelIn.getBlockEntity(blockPos3);
					if (spawner instanceof SpawnerBlockEntity)
						((SpawnerBlockEntity)spawner).setEntityId(EntityType.BLAZE, randomIn);
				}
			}
			this.placeBlock(levelIn, wall2, 13, 6, 5, boundingBoxIn);
			this.placeBlock(levelIn, fence2, 13, 7, 5, boundingBoxIn);
			if (!this.hasPlacedSpawner[3]) {
				final BlockPos blockPos4 = new BlockPos(this.getWorldX(13, 5), this.getWorldY(8), this.getWorldZ(13, 5));
				if (boundingBoxIn.isInside(blockPos4)) {
					this.hasPlacedSpawner[3] = true;
					levelIn.setBlock(blockPos4, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = levelIn.getBlockEntity(blockPos4);
					if (spawner instanceof SpawnerBlockEntity)
						((SpawnerBlockEntity)spawner).setEntityId(EntityType.BLAZE, randomIn);
				}
			}
			this.generateBox(levelIn, boundingBoxIn, 2, 4, 3, 2, 5, 3, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 4, 3, 12, 5, 3, block1, block1, false);
			this.placeBlock(levelIn, fence1, 2, 6, 3, boundingBoxIn);
			this.placeBlock(levelIn, fence1, 12, 6, 3, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 3, 4, 2, 3, 5, 2, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 11, 4, 2, 11, 5, 2, block1, block1, false);
			this.placeBlock(levelIn, wall1, 3, 6, 2, boundingBoxIn);
			this.placeBlock(levelIn, wall1, 11, 6, 2, boundingBoxIn);
			this.placeBlock(levelIn, fence1, 3, 7, 2, boundingBoxIn);
			this.placeBlock(levelIn, fence1, 11, 7, 2, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 4, 4, 1, 10, 5, 1, block1, block1, false);
			this.placeBlock(levelIn, wall2, 4, 6, 1, boundingBoxIn);
			this.placeBlock(levelIn, fence1, 5, 6, 1, boundingBoxIn);
			this.placeBlock(levelIn, wall2, 6, 6, 1, boundingBoxIn);
			this.placeBlock(levelIn, fence1, 7, 6, 1, boundingBoxIn);
			this.placeBlock(levelIn, wall2, 8, 6, 1, boundingBoxIn);
			this.placeBlock(levelIn, fence1, 9, 6, 1, boundingBoxIn);
			this.placeBlock(levelIn, wall2, 10, 6, 1, boundingBoxIn);
			this.placeBlock(levelIn, fence2, 4, 7, 1, boundingBoxIn);
			this.placeBlock(levelIn, wall1, 5, 7, 1, boundingBoxIn);
			this.placeBlock(levelIn, fence3, 6, 7, 1, boundingBoxIn);
			if (!this.hasPlacedSpawner[4]) {
				final BlockPos blockPos5 = new BlockPos(this.getWorldX(7, 1), this.getWorldY(7), this.getWorldZ(7, 1));
				if (boundingBoxIn.isInside(blockPos5)) {
					this.hasPlacedSpawner[4] = true;
					levelIn.setBlock(blockPos5, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = levelIn.getBlockEntity(blockPos5);
					if (spawner instanceof SpawnerBlockEntity)
						((SpawnerBlockEntity)spawner).setEntityId(EntityType.BLAZE, randomIn);
				}
			}
			this.placeBlock(levelIn, fence4, 8, 7, 1, boundingBoxIn);
			this.placeBlock(levelIn, wall1, 9, 7, 1, boundingBoxIn);
			this.placeBlock(levelIn, fence2, 10, 7, 1, boundingBoxIn);
			this.placeBlock(levelIn, fence4, 4, 8, 1, boundingBoxIn);
			this.placeBlock(levelIn, fence5, 5, 8, 1, boundingBoxIn);
			this.placeBlock(levelIn, fence3, 6, 8, 1, boundingBoxIn);
			this.placeBlock(levelIn, wall1, 7, 8, 1, boundingBoxIn);
			this.placeBlock(levelIn, fence4, 8, 8, 1, boundingBoxIn);
			this.placeBlock(levelIn, fence5, 9, 8, 1, boundingBoxIn);
			this.placeBlock(levelIn, fence3, 10, 8, 1, boundingBoxIn);
			this.placeBlock(levelIn, fence7, 5, 9, 1, boundingBoxIn);
			this.placeBlock(levelIn, fence6, 6, 9, 1, boundingBoxIn);
			this.placeBlock(levelIn, fence5, 7, 9, 1, boundingBoxIn);
			this.placeBlock(levelIn, fence6, 8, 9, 1, boundingBoxIn);
			this.placeBlock(levelIn, fence8, 9, 9, 1, boundingBoxIn);
			this.placeBlock(levelIn, fence1, 7, 10, 1, boundingBoxIn);
			//center
			this.generateBox(levelIn, boundingBoxIn, 4, 4, 14, 6, 4, 14, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 4, 14, 7, 4, 14, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 8, 4, 14, 10, 4, 14, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 4, 13, 4, 4, 13, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 4, 13, 9, 4, 13, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 4, 13, 11, 4, 13, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 4, 12, 3, 4, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 4, 12, 5, 4, 12, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 4, 12, 6, 4, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 4, 12, 7, 4, 12, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 8, 4, 12, 8, 4, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 9, 4, 12, 10, 4, 12, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 11, 4, 12, 12, 4, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 4, 11, 2, 4, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 4, 11, 11, 4, 11, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 4, 11, 12, 4, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 4, 10, 2, 4, 10, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 4, 10, 4, 4, 10, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 4, 10, 6, 4, 10, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 4, 10, 7, 4, 10, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 8, 4, 10, 9, 4, 10, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 4, 10, 11, 4, 10, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 4, 10, 13, 4, 10, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 4, 9, 1, 4, 9, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 4, 9, 4, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 4, 9, 5, 4, 9, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 4, 9, 8, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 9, 4, 9, 9, 4, 9, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 4, 9, 12, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 4, 9, 13, 4, 9, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 4, 8, 1, 4, 8, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 4, 8, 2, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 4, 8, 3, 4, 8, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 4, 8, 6, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 4, 8, 7, 4, 8, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 8, 4, 8, 10, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 11, 4, 8, 11, 4, 8, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 4, 8, 12, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 4, 8, 13, 4, 8, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 4, 7, 1, 4, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 4, 7, 4, 4, 7, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 4, 7, 5, 4, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 4, 7, 8, 4, 7, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 9, 4, 7, 9, 4, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 4, 7, 12, 4, 7, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 13, 4, 7, 13, 4, 7, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 4, 6, 2, 4, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 4, 6, 4, 4, 6, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 4, 6, 6, 4, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 4, 6, 7, 4, 6, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 8, 4, 6, 9, 4, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 4, 6, 11, 4, 6, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 4, 6, 13, 4, 6, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 4, 5, 2, 4, 5, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 4, 5, 11, 4, 5, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 12, 4, 5, 12, 4, 5, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 4, 4, 3, 4, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 4, 4, 5, 4, 4, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 4, 4, 6, 4, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 4, 4, 7, 4, 4, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 8, 4, 4, 8, 4, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 9, 4, 4, 10, 4, 4, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 11, 4, 4, 12, 4, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 4, 3, 4, 4, 3, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 4, 3, 9, 4, 3, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 10, 4, 3, 11, 4, 3, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 4, 2, 10, 4, 2, block1, block1, false);
			this.placeBlock(levelIn, wall1, 3, 5, 8, boundingBoxIn);
			this.placeBlock(levelIn, fence1, 3, 6, 8, boundingBoxIn);
			this.placeBlock(levelIn, block3, 3, 7, 8, boundingBoxIn);
			this.placeBlock(levelIn, wall1, 11, 5, 8, boundingBoxIn);
			this.placeBlock(levelIn, fence1, 11, 6, 8, boundingBoxIn);
			this.placeBlock(levelIn, block3, 11, 7, 8, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 5, 5, 10, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 6, 5, 10, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 7, 5, 10, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 8, 5, 10, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 9, 5, 10, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 5, 5, 9, boundingBoxIn);
			this.placeBlock(levelIn, block2, 6, 5, 9, boundingBoxIn);
			this.placeBlock(levelIn, block2, 7, 5, 9, boundingBoxIn);
			this.placeBlock(levelIn, block2, 8, 5, 9, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 9, 5, 9, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 5, 5, 8, boundingBoxIn);
			this.placeBlock(levelIn, block2, 6, 5, 8, boundingBoxIn);
			this.placeBlock(levelIn, block1, 7, 5, 8, boundingBoxIn);
			this.placeBlock(levelIn, block4, 7, 6, 8, boundingBoxIn);
			this.placeBlock(levelIn, block2, 8, 5, 8, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 9, 5, 8, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 5, 5, 7, boundingBoxIn);
			this.placeBlock(levelIn, block2, 6, 5, 7, boundingBoxIn);
			this.placeBlock(levelIn, block2, 7, 5, 7, boundingBoxIn);
			this.placeBlock(levelIn, block2, 8, 5, 7, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 9, 5, 7, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 5, 5, 6, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 6, 5, 6, boundingBoxIn);
			this.placeBlock(levelIn, stair8, 7, 5, 6, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 8, 5, 6, boundingBoxIn);
			this.placeBlock(levelIn, slab1, 9, 5, 6, boundingBoxIn);
			//chests
			if (this.isNeedingChest[0] && boundingBoxIn.isInside(new BlockPos(this.getWorldX(5, 2), this.getWorldY(5), this.getWorldZ(5, 2)))) {
				this.isNeedingChest[0] = false;
				this.createChest(levelIn, boundingBoxIn, randomIn, 5, 5, 2, BWChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE_GRAND_THRONE);
			}
			if (this.isNeedingChest[1] && boundingBoxIn.isInside(new BlockPos(this.getWorldX(7, 2), this.getWorldY(5), this.getWorldZ(7, 2)))) {
				this.isNeedingChest[1] = false;
				this.createChest(levelIn, boundingBoxIn, randomIn, 7, 5, 2, BWChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE_GRAND_THRONE);
			}
			if (this.isNeedingChest[2] && boundingBoxIn.isInside(new BlockPos(this.getWorldX(9, 2), this.getWorldY(5), this.getWorldZ(9, 2)))) {
				this.isNeedingChest[2] = false;
				this.createChest(levelIn, boundingBoxIn, randomIn, 9, 5, 2, BWChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE_GRAND_THRONE);
			}
			if (this.isNeedingChest[3] && boundingBoxIn.isInside(new BlockPos(this.getWorldX(1, 9), this.getWorldY(5), this.getWorldZ(1, 9)))) {
				this.isNeedingChest[3] = false;
				this.createChest(levelIn, boundingBoxIn, randomIn, 1, 5, 9, BWChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE_THRONE);
			}
			if (this.isNeedingChest[4] && boundingBoxIn.isInside(new BlockPos(this.getWorldX(1, 7), this.getWorldY(5), this.getWorldZ(1, 7)))) {
				this.isNeedingChest[4] = false;
				this.createChest(levelIn, boundingBoxIn, randomIn, 1, 5, 7, BWChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE_THRONE);
			}
			if (this.isNeedingChest[5] && boundingBoxIn.isInside(new BlockPos(this.getWorldX(13, 9), this.getWorldY(5), this.getWorldZ(13, 9)))) {
				this.isNeedingChest[5] = false;
				this.createChest(levelIn, boundingBoxIn, randomIn, 13, 5, 9, BWChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE_THRONE);
			}
			if (this.isNeedingChest[6] && boundingBoxIn.isInside(new BlockPos(this.getWorldX(13, 7), this.getWorldY(5), this.getWorldZ(13, 7)))) {
				this.isNeedingChest[6] = false;
				this.createChest(levelIn, boundingBoxIn, randomIn, 13, 5, 7, BWChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE_THRONE);
			}
			//bottom pillars
			this.fillColumnDown(levelIn, block1, 5, 3, 15, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 5, 3, 14, boundingBoxIn);
			this.placeBlock(levelIn, stair9, 4, 3, 15, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 6, 3, 15, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 9, 3, 15, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 9, 3, 14, boundingBoxIn);
			this.placeBlock(levelIn, stair9, 8, 3, 15, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 10, 3, 15, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 2, 3, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 2, 3, 11, boundingBoxIn);
			this.placeBlock(levelIn, stair11, 2, 3, 13, boundingBoxIn);
			this.placeBlock(levelIn, stair9, 1, 3, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 3, 3, 12, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 12, 3, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 12, 3, 11, boundingBoxIn);
			this.placeBlock(levelIn, stair11, 12, 3, 13, boundingBoxIn);
			this.placeBlock(levelIn, stair9, 11, 3, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 13, 3, 12, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 1, 3, 8, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 1, 3, 7, boundingBoxIn);
			this.placeBlock(levelIn, stair11, 1, 3, 9, boundingBoxIn);
			this.placeBlock(levelIn, stair9, 0, 3, 8, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 13, 3, 8, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 13, 3, 7, boundingBoxIn);
			this.placeBlock(levelIn, stair11, 13, 3, 9, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 14, 3, 8, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 2, 3, 4, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 2, 3, 3, boundingBoxIn);
			this.placeBlock(levelIn, stair11, 2, 3, 5, boundingBoxIn);
			this.placeBlock(levelIn, stair9, 1, 3, 4, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 3, 3, 4, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 12, 3, 4, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 12, 3, 3, boundingBoxIn);
			this.placeBlock(levelIn, stair11, 12, 3, 5, boundingBoxIn);
			this.placeBlock(levelIn, stair9, 11, 3, 4, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 13, 3, 4, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 7, 3, 2, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 7, 3, 1, boundingBoxIn);
			this.placeBlock(levelIn, stair9, 6, 3, 2, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 8, 3, 2, boundingBoxIn);
			//bottom
			this.placeBlock(levelIn, fence1, 6, 3, 12, boundingBoxIn);
			this.placeBlock(levelIn, block5, 6, 2, 12, boundingBoxIn);
			this.placeBlock(levelIn, fence1, 8, 3, 12, boundingBoxIn);
			this.placeBlock(levelIn, block5, 8, 2, 12, boundingBoxIn);
			this.placeBlock(levelIn, fence1, 3, 3, 8, boundingBoxIn);
			this.placeBlock(levelIn, block5, 3, 2, 8, boundingBoxIn);
			this.placeBlock(levelIn, wall1, 7, 3, 8, boundingBoxIn);
			this.placeBlock(levelIn, fence1, 7, 2, 8, boundingBoxIn);
			this.placeBlock(levelIn, block5, 7, 1, 8, boundingBoxIn);
			this.placeBlock(levelIn, fence1, 11, 3, 8, boundingBoxIn);
			this.placeBlock(levelIn, block5, 11, 2, 8, boundingBoxIn);
			this.placeBlock(levelIn, fence1, 6, 3, 4, boundingBoxIn);
			this.placeBlock(levelIn, block5, 6, 2, 4, boundingBoxIn);
			this.placeBlock(levelIn, fence1, 8, 3, 4, boundingBoxIn);
			this.placeBlock(levelIn, block5, 8, 2, 4, boundingBoxIn);
		}
	}

	public static class CastleSmallCorridorLeftTurnPiece extends InfernalNetherBridgePiece {
		private boolean isNeedingChest;

		public CastleSmallCorridorLeftTurnPiece(final int genDepthIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_CASTLE_SMALL_CORRIDOR_LEFT_TURN.get(), genDepthIn, boundingBoxIn);
			this.setOrientation(facingIn);
			this.isNeedingChest = randomIn.nextInt(3) == 0;
		}

		public CastleSmallCorridorLeftTurnPiece(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_CASTLE_SMALL_CORRIDOR_LEFT_TURN.get(), compoundIn);
			this.isNeedingChest = compoundIn.getBoolean("Chest");
		}

		@Override
		protected final void addAdditionalSaveData(final StructurePieceSerializationContext contextIn, final CompoundTag compoundIn) {
			super.addAdditionalSaveData(contextIn, compoundIn);
			compoundIn.putBoolean("Chest", this.isNeedingChest);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 0);
			super.addChildren(pieceIn, piecesIn, randomIn);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource randomIn) {
			return List.of(new ChildInfo(Direction.WEST, 0, 1, true, EndType.DEFAULT));
		}

		public static final CastleSmallCorridorLeftTurnPiece createPiece(final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int genDepthIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -1, 0, 0, 5, 7, 5, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new CastleSmallCorridorLeftTurnPiece(genDepthIn, randomIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
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
			this.generateAirBox(levelIn, boundingBoxIn, 0, 2, 0, 4, 5, 4);
			//floor
			this.generateBox(levelIn, boundingBoxIn, 0, 0, 0, 4, 1, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 0, 0, 2, 1, 2, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 0, 2, 2, 1, 2, block2, block2, false);
			//walls
			this.generateBox(levelIn, boundingBoxIn, 4, 2, 0, 4, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 2, 1, 4, 5, 1, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 3, 1, 4, 4, 1, fence2, fence2, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 2, 3, 4, 5, 3, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 3, 3, 4, 4, 3, fence2, fence2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 0, 0, 5, 0, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 4, 3, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 2, 4, 1, 5, 4, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 3, 4, 1, 4, 4, fence1, fence1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 2, 4, 3, 5, 4, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 3, 4, 3, 4, 4, fence1, fence1, false);
			//chest
			if (this.isNeedingChest && boundingBoxIn.isInside(new BlockPos(this.getWorldX(3, 3), this.getWorldY(2), this.getWorldZ(3, 3)))) {
				this.isNeedingChest = false;
				this.createChest(levelIn, boundingBoxIn, randomIn, 3, 2, 3, BWChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE);
			}
			//roof
			this.generateBox(levelIn, boundingBoxIn, 0, 6, 0, 4, 6, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 6, 0, 2, 6, 2, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 6, 2, 2, 6, 2, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 6, 0, 0, 6, 0, slab2, slab2, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 6, 0, 4, 6, 4, slab2, slab2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 6, 4, 4, 6, 4, slab2, slab2, false);
			//top
			this.generateBox(levelIn, boundingBoxIn, 1, 5, 1, 1, 5, 1, slab1, slab1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 5, 3, 3, 5, 3, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 5, 3, 2, 5, 3, stair1, stair1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 5, 1, 0, 5, 1, stair2, stair2, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 5, 0, 3, 5, 2, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 5, 0, 1, 5, 0, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 5, 3, 1, 5, 3, stair5, stair5, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 5, 1, 3, 5, 1, stair6, stair6, false);
			//bottom
			this.fillColumnDown(levelIn, block1, 4, -1, 4, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 4, -1, 3, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 3, -1, 4, boundingBoxIn);
		}
	}

	public static class CastleSmallCorridorRightTurnPiece extends InfernalNetherBridgePiece {
		private boolean isNeedingChest;

		public CastleSmallCorridorRightTurnPiece(final int genDepthIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_CASTLE_SMALL_CORRIDOR_RIGHT_TURN.get(), genDepthIn, boundingBoxIn);
			this.setOrientation(facingIn);
			this.isNeedingChest = randomIn.nextInt(3) == 0;
		}

		public CastleSmallCorridorRightTurnPiece(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_CASTLE_SMALL_CORRIDOR_RIGHT_TURN.get(), compoundIn);
			this.isNeedingChest = compoundIn.getBoolean("Chest");
		}

		@Override
		protected final void addAdditionalSaveData(final StructurePieceSerializationContext contextIn, final CompoundTag compoundIn) {
			super.addAdditionalSaveData(contextIn, compoundIn);
			compoundIn.putBoolean("Chest", this.isNeedingChest);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 0);
			super.addChildren(pieceIn, piecesIn, randomIn);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource randomIn) {
			return List.of(new ChildInfo(Direction.EAST, 0, 1, true, EndType.DEFAULT));
		}

		public static final CastleSmallCorridorRightTurnPiece createPiece(final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int genDepthIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -1, 0, 0, 5, 7, 5, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new CastleSmallCorridorRightTurnPiece(genDepthIn, randomIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
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
			this.generateAirBox(levelIn, boundingBoxIn, 0, 2, 0, 4, 5, 4);
			//floor
			this.generateBox(levelIn, boundingBoxIn, 0, 0, 0, 4, 1, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 0, 0, 2, 1, 2, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 0, 2, 4, 1, 2, block2, block2, false);
			//walls
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 0, 0, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 1, 0, 5, 1, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 1, 0, 4, 1, fence2, fence2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 3, 0, 5, 3, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 3, 0, 4, 3, fence2, fence2, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 2, 0, 4, 5, 0, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 2, 4, 4, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 2, 4, 3, 5, 4, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 3, 4, 3, 4, 4, fence1, fence1, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 2, 4, 1, 5, 4, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 3, 4, 1, 4, 4, fence1, fence1, false);
			//chest
			if (this.isNeedingChest && boundingBoxIn.isInside(new BlockPos(this.getWorldX(1, 3), this.getWorldY(2), this.getWorldZ(1, 3)))) {
				this.isNeedingChest = false;
				this.createChest(levelIn, boundingBoxIn, randomIn, 1, 2, 3, BWChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE);
			}
			//roof
			this.generateBox(levelIn, boundingBoxIn, 0, 6, 0, 4, 6, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 6, 0, 2, 6, 2, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 6, 2, 4, 6, 2, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 6, 0, 4, 6, 0, slab2, slab2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 6, 0, 0, 6, 4, slab2, slab2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 6, 4, 4, 6, 4, slab2, slab2, false);
			//top
			this.generateBox(levelIn, boundingBoxIn, 3, 5, 1, 3, 5, 1, slab1, slab1, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 5, 3, 1, 5, 3, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 5, 3, 4, 5, 3, stair1, stair1, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 5, 1, 4, 5, 1, stair2, stair2, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 5, 0, 1, 5, 2, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 5, 0, 3, 5, 0, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 5, 3, 3, 5, 3, stair5, stair5, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 5, 1, 1, 5, 1, stair6, stair6, false);
			//bottom
			this.fillColumnDown(levelIn, block1, 0, -1, 4, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 0, -1, 3, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 1, -1, 4, boundingBoxIn);
		}
	}

	public static class CastleCorridorStairsPiece extends InfernalNetherBridgePiece {
		public CastleCorridorStairsPiece(final int genDepthIn, final BoundingBox boundingBoxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_CASTLE_CORRIDOR_STAIRS.get(), genDepthIn, boundingBoxIn);
			this.setOrientation(facingIn);
		}

		public CastleCorridorStairsPiece(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_CASTLE_CORRIDOR_STAIRS.get(), compoundIn);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 0);
			super.addChildren(pieceIn, piecesIn, randomIn);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource randomIn) {
			return List.of(new ChildInfo(Direction.NORTH, 1, 0, true, EndType.DEFAULT));
		}

		public static final CastleCorridorStairsPiece createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int genDepthIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -1, -7, 0, 5, 14, 10, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new CastleCorridorStairsPiece(genDepthIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
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
				this.generateBox(levelIn, boundingBoxIn, 0, j - 1, i, 1, j, i, block1, block1, false);
				this.generateBox(levelIn, boundingBoxIn, 2, j - 1, i, 2, j, i, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, 3, j - 1, i, 4, j, i, block1, block1, false);
				this.generateAirBox(levelIn, boundingBoxIn, 1, j + 1, i, 3, k - 1, i);
				if (i <= 6) {
					this.placeBlock(levelIn, stair1, 1, j + 1, i, boundingBoxIn);
					this.placeBlock(levelIn, stair2, 2, j + 1, i, boundingBoxIn);
					this.placeBlock(levelIn, stair1, 3, j + 1, i, boundingBoxIn);
				}
				//top
				if (i < 9 && i > 1) {
					this.generateBox(levelIn, boundingBoxIn, 0, k, i, 0, k, i, stair1, stair1, false);
					this.generateBox(levelIn, boundingBoxIn, 1, k, i, 1, k, i, block1, block1, false);
					this.generateBox(levelIn, boundingBoxIn, 2, k, i, 2, k, i, block2, block2, false);
					this.generateBox(levelIn, boundingBoxIn, 3, k, i, 3, k, i, block1, block1, false);
					this.generateBox(levelIn, boundingBoxIn, 4, k, i, 4, k, i, stair1, stair1, false);
					if (i == 2) {
						this.generateBox(levelIn, boundingBoxIn, 1, k, i - 2, 1, k, i - 2, stair5, stair5, false);
						this.generateBox(levelIn, boundingBoxIn, 1, k, i - 1, 1, k, i - 1, block1, block1, false);
						this.generateBox(levelIn, boundingBoxIn, 2, k, i - 1, 2, k, i - 1, stair4, stair4, false);
						this.generateBox(levelIn, boundingBoxIn, 3, k, i - 1, 3, k, i - 1, block1, block1, false);
						this.generateBox(levelIn, boundingBoxIn, 3, k, i - 2, 3, k, i - 2, stair6, stair6, false);
					} else {
						this.generateBox(levelIn, boundingBoxIn, 1, k, i - 1, 1, k, i - 1, stair3, stair3, false);
						this.generateBox(levelIn, boundingBoxIn, 2, k, i - 1, 2, k, i - 1, stair4, stair4, false);
						this.generateBox(levelIn, boundingBoxIn, 3, k, i - 1, 3, k, i - 1, stair3, stair3, false);
					}
				} else {
					if (i == 9) {
						this.generateBox(levelIn, boundingBoxIn, 1, k - 1, i, 1, k - 1, i, stair5, stair5, false);
						this.generateBox(levelIn, boundingBoxIn, 1, k - 1, i - 1, 1, k - 1, i - 1, slab2, slab2, false);
						this.generateBox(levelIn, boundingBoxIn, 3, k - 1, i, 3, k - 1, i, stair6, stair6, false);
						this.generateBox(levelIn, boundingBoxIn, 3, k - 1, i - 1, 3, k - 1, i - 1, slab2, slab2, false);
					}
					this.generateBox(levelIn, boundingBoxIn, 0, k, i, 0, k, i, slab1, slab1, false);
					this.generateBox(levelIn, boundingBoxIn, 1, k, i, 1, k, i, block1, block1, false);
					this.generateBox(levelIn, boundingBoxIn, 2, k, i, 2, k, i, block2, block2, false);
					this.generateBox(levelIn, boundingBoxIn, 3, k, i, 3, k, i, block1, block1, false);
					this.generateBox(levelIn, boundingBoxIn, 4, k, i, 4, k, i, slab1, slab1, false);
				}
				this.generateBox(levelIn, boundingBoxIn, 1, k, i + 1, 1, k, i + 1, stair1, stair1, false);
				this.generateBox(levelIn, boundingBoxIn, 2, k, i + 1, 2, k, i + 1, stair2, stair2, false);
				this.generateBox(levelIn, boundingBoxIn, 3, k, i + 1, 3, k, i + 1, stair1, stair1, false);
				this.generateBox(levelIn, boundingBoxIn, 0, j + 1, i, 0, k - 1, i, block1, block1, false);
				this.generateBox(levelIn, boundingBoxIn, 4, j + 1, i, 4, k - 1, i, block1, block1, false);
				if ((i & 1) == 0) {
					//fences
					this.generateBox(levelIn, boundingBoxIn, 0, j + 1, i, 0, j + 4, i, block2, block2, false);
					this.generateBox(levelIn, boundingBoxIn, 4, j + 1, i, 4, j + 4, i, block2, block2, false);
					this.generateBox(levelIn, boundingBoxIn, 0, j + 2, i, 0, j + 3, i, fence1, fence1, false);
					this.generateBox(levelIn, boundingBoxIn, 4, j + 2, i, 4, j + 3, i, fence1, fence1, false);
				}
				if (i == 9) {
					this.fillColumnDown(levelIn, block1, 0, j - 2, i - 2, boundingBoxIn);
					this.placeBlock(levelIn, stair3, 0, j - 2, i - 3, boundingBoxIn);
					this.placeBlock(levelIn, stair7, 0, j - 2, i - 1, boundingBoxIn);
					this.placeBlock(levelIn, stair5, 1, j - 2, i - 2, boundingBoxIn);
					this.fillColumnDown(levelIn, block1, 4, j - 2, i - 2, boundingBoxIn);
					this.placeBlock(levelIn, stair3, 4, j - 2, i - 3, boundingBoxIn);
					this.placeBlock(levelIn, stair7, 4, j - 2, i - 1, boundingBoxIn);
					this.placeBlock(levelIn, stair6, 3, j - 2, i - 2, boundingBoxIn);
				}
			}
		}
	}

	public static class CastleCorridorTBalconyPiece extends InfernalNetherBridgePiece {
		public CastleCorridorTBalconyPiece(final int genDepthIn, final BoundingBox boundingBoxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_CASTLE_CORRIDOR_T_BALCONY.get(), genDepthIn, boundingBoxIn);
			this.setOrientation(facingIn);
		}

		public CastleCorridorTBalconyPiece(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_CASTLE_CORRIDOR_T_BALCONY.get(), compoundIn);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 0);
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 1);
			super.addChildren(pieceIn, piecesIn, randomIn);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource randomIn) {
			int i = 1;
			Direction direction = this.getOrientation();
			if (direction == Direction.WEST || direction == Direction.NORTH)
				i = 5;
			return List.of(new ChildInfo(Direction.WEST, 0, i, randomIn.nextInt(8) > 0, EndType.DEFAULT), new ChildInfo(Direction.EAST, 0, i, randomIn.nextInt(8) > 0, EndType.DEFAULT));
		}

		@Override
		protected final List<Class<? extends InfernalNetherBridgePiece>> getUnacceptableChildren() {
			return List.of(BridgeStraight1.class, BridgeStraight2.class, BridgeStraight3.class, BridgeCrossing1.class, BridgeCrossing2.class, BridgeCrossing3.class, LavaRoomCrossing.class);
		}

		public static final CastleCorridorTBalconyPiece createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int genDepthIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -3, 0, 0, 9, 7, 9, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new CastleCorridorTBalconyPiece(genDepthIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = BWBlocks.BLAZE_LANTERN.get().defaultBlockState();
			final BlockState fence1 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence2 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence3 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence4 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence5 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
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
			this.generateBox(levelIn, boundingBoxIn, 0, 0, 0, 8, 1, 5, block1, block1, false);
			this.generateAirBox(levelIn, boundingBoxIn, 0, 2, 0, 8, 5, 8);
			this.generateBox(levelIn, boundingBoxIn, 4, 0, 0, 4, 1, 5, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 0, 2, 8, 1, 2, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 4, 8, 2, 7, block1, block1, false);
			this.placeBlock(levelIn, stair1, 3, 2, 4, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 4, 2, 4, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 5, 2, 4, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 4, 2, 5, 4, 2, 7, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 2, 7, 7, 2, 7, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 8, 8, 2, 8, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 1, 7, 8, 1, 7, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 1, 6, 8, 1, 6, stair1, stair1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 0, 6, 8, 0, 6, stair3, stair3, false);
			//top
			this.generateBox(levelIn, boundingBoxIn, 0, 6, 0, 8, 6, 5, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 6, 0, 4, 6, 5, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 6, 2, 8, 6, 2, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 6, 0, 2, 6, 0, slab1, slab1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 6, 0, 8, 6, 0, slab1, slab1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 6, 5, 2, 6, 5, slab1, slab1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 6, 5, 8, 6, 5, slab1, slab1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 6, 4, 0, 6, 5, slab1, slab1, false);
			this.generateBox(levelIn, boundingBoxIn, 8, 6, 4, 8, 6, 5, slab1, slab1, false);
			this.placeBlock(levelIn, stair3, 0, 5, 1, boundingBoxIn);
			this.placeBlock(levelIn, slab3, 0, 5, 3, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 1, 5, 1, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 2, 5, 1, boundingBoxIn);
			this.placeBlock(levelIn, slab3, 3, 5, 1, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 3, 5, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 5, 5, 0, boundingBoxIn);
			this.placeBlock(levelIn, slab3, 5, 5, 1, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 6, 5, 1, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 7, 5, 1, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 8, 5, 1, boundingBoxIn);
			this.placeBlock(levelIn, slab3, 8, 5, 3, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 3, 5, 5, boundingBoxIn);
			this.placeBlock(levelIn, slab3, 3, 5, 4, boundingBoxIn);
			this.placeBlock(levelIn, slab3, 2, 5, 4, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 5, 5, 5, boundingBoxIn);
			this.placeBlock(levelIn, slab3, 5, 5, 4, boundingBoxIn);
			this.placeBlock(levelIn, slab3, 6, 5, 4, boundingBoxIn);
			//walls
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 0, 2, 5, 0, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 2, 0, 8, 5, 0, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 2, 0, 1, 5, 0, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 3, 0, 1, 4, 0, fence1, fence1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 2, 0, 7, 5, 0, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 3, 0, 7, 4, 0, fence1, fence1, false);
			this.generateAirBox(levelIn, boundingBoxIn, 1, 1, 4, 2, 2, 4);
			this.generateAirBox(levelIn, boundingBoxIn, 6, 1, 4, 7, 2, 4);
			//front
			this.generateBox(levelIn, boundingBoxIn, 1, 3, 8, 7, 3, 8, fence3, fence3, false);
			this.placeBlock(levelIn, fence4, 0, 3, 8, boundingBoxIn);
			this.placeBlock(levelIn, fence5, 8, 3, 8, boundingBoxIn);
			this.placeBlock(levelIn, block3, 0, 4, 8, boundingBoxIn);
			this.placeBlock(levelIn, block3, 8, 4, 8, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 6, 0, 3, 7, fence2, fence2, false);
			this.generateBox(levelIn, boundingBoxIn, 8, 3, 6, 8, 3, 7, fence2, fence2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 4, 0, 5, 5, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 8, 3, 4, 8, 5, 5, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 3, 5, 2, 5, 5, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 3, 5, 7, 5, 5, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 3, 5, 1, 3, 5, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 4, 5, 1, 5, 5, fence1, fence1, false);
			this.generateBox(levelIn, boundingBoxIn, 1, 6, 5, 1, 6, 5, slab2, slab2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 3, 5, 7, 3, 5, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 4, 5, 7, 5, 5, fence1, fence1, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 6, 5, 7, 6, 5, slab2, slab2, false);
			//bottom
			this.fillColumnDown(levelIn, block1, 0, -1, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 1, -1, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 0, -1, 1, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 8, -1, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 7, -1, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 8, -1, 1, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 0, -1, 5, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 1, -1, 5, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 0, -1, 4, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 8, -1, 5, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 7, -1, 5, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 8, -1, 4, boundingBoxIn);
		}
	}

	public static class CastleSmallCorridorPiece extends InfernalNetherBridgePiece {
		public CastleSmallCorridorPiece(final int genDepthIn, final BoundingBox boundingBoxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_CASTLE_SMALL_CORRIDOR.get(), genDepthIn, boundingBoxIn);
			this.setOrientation(facingIn);
		}

		public CastleSmallCorridorPiece(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_CASTLE_SMALL_CORRIDOR.get(), compoundIn);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 0);
			super.addChildren(pieceIn, piecesIn, randomIn);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource randomIn) {
			return List.of(new ChildInfo(Direction.NORTH, 1, 0, true, EndType.DEFAULT));
		}

		public static final CastleSmallCorridorPiece createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int genDepthIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -1, 0, 0, 5, 7, 5, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new CastleSmallCorridorPiece(genDepthIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
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
			this.generateBox(levelIn, boundingBoxIn, 0, 0, 0, 4, 1, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 0, 0, 2, 1, 4, block2, block2, false);
			this.generateAirBox(levelIn, boundingBoxIn, 0, 2, 0, 4, 5, 4);
			//walls
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 0, 0, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 2, 0, 4, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 1, 0, 5, 1, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 1, 0, 4, 1, fence1, fence1, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 2, 3, 0, 5, 3, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 3, 0, 4, 3, fence1, fence1, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 2, 1, 4, 5, 1, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 3, 1, 4, 4, 1, fence1, fence1, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 2, 3, 4, 5, 3, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 3, 3, 4, 4, 3, fence1, fence1, false);
			//top
			this.generateBox(levelIn, boundingBoxIn, 0, 6, 0, 4, 6, 4, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 6, 0, 2, 6, 4, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 0, 6, 0, 0, 6, 4, slab1, slab1, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 6, 0, 4, 6, 4, slab1, slab1, false);
			this.placeBlock(levelIn, stair3, 1, 5, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 1, 5, 1, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 1, 5, 2, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 1, 5, 3, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 1, 5, 4, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 3, 5, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 3, 5, 1, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 3, 5, 2, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 3, 5, 3, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 3, 5, 4, boundingBoxIn);
			//bottom
			this.fillColumnDown(levelIn, block1, 0, -1, 2, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 0, -1, 3, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 0, -1, 1, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 1, -1, 2, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 4, -1, 2, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 4, -1, 3, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 4, -1, 1, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 3, -1, 2, boundingBoxIn);
		}
	}

	public static class CastleStalkRoom extends InfernalNetherBridgePiece {
		private boolean hasPlacedSpawner;
		private boolean isNeedingChest;

		public CastleStalkRoom(final int genDepthIn, final BoundingBox boundingBoxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_CASTLE_STALK_ROOM.get(), genDepthIn, boundingBoxIn);
			this.setOrientation(facingIn);
			this.hasPlacedSpawner = false;
			this.isNeedingChest = true;
		}

		public CastleStalkRoom(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_CASTLE_STALK_ROOM.get(), compoundIn);
			this.hasPlacedSpawner = compoundIn.getBoolean("Mob");
			this.isNeedingChest = compoundIn.getBoolean("Chest");
		}

		@Override
		protected final void addAdditionalSaveData(final StructurePieceSerializationContext contextIn, final CompoundTag compoundIn) {
			super.addAdditionalSaveData(contextIn, compoundIn);
			compoundIn.putBoolean("Mob", this.hasPlacedSpawner);
			compoundIn.putBoolean("Chest", this.isNeedingChest);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 0);
			this.generateChild((StartPiece)pieceIn, piecesIn, randomIn, 1);
			super.addChildren(pieceIn, piecesIn, randomIn);
		}

		@Override
		protected List<ChildInfo> getChildInfo(final RandomSource randomIn) {
			return List.of(new ChildInfo(Direction.NORTH, 5, 3, true, EndType.DEFAULT), new ChildInfo(Direction.NORTH, 5, 11, true, EndType.DEFAULT));
		}

		@Override
		protected int maxPlacementChunkRange() {
			return 6;
		}

		public static final CastleStalkRoom createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int genDepthIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -5, -3, 0, 13, 14, 13, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new CastleStalkRoom(genDepthIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boundingBoxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = BWBlocks.BLAZE_LANTERN.get().defaultBlockState().setValue(BlazeLanternBlock.HANGING, Boolean.valueOf(true));
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
			final BlockState fence3 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence4 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence5 = fence2.setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true));
			final BlockState fence6 = fence2.setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence7 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence8 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.EAST, Boolean.valueOf(true));
			final BlockState fence9 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.SOUTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true));
			final BlockState fence10 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(CrossCollisionBlock.NORTH, Boolean.valueOf(true)).setValue(CrossCollisionBlock.WEST, Boolean.valueOf(true));
			//outLine
			this.generateBox(levelIn, boundingBoxIn, 0, 3, 0, 12, 4, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 3, 0, 6, 4, 12, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 3, 3, 9, 4, 3, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 9, 3, 3, 9, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 3, 3, 3, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 3, 9, 9, 4, 9, block2, block2, false);
			this.generateAirBox(levelIn, boundingBoxIn, 0, 5, 0, 12, 13, 12);
			this.generateBox(levelIn, boundingBoxIn, 0, 5, 0, 1, 12, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 11, 5, 0, 12, 12, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 5, 11, 4, 12, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 8, 5, 11, 10, 12, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 9, 11, 7, 12, 12, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 5, 0, 4, 12, 1, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 8, 5, 0, 10, 12, 1, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 9, 0, 7, 12, 1, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 2, 11, 2, 10, 12, 10, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 11, 3, 6, 11, 9, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 11, 3, 9, 11, 3, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 9, 11, 3, 9, 11, 9, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 11, 3, 3, 11, 9, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 11, 9, 9, 11, 9, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 12, 4, 6, 12, 5, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 12, 7, 6, 12, 8, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 12, 6, 5, 12, 6, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 12, 6, 8, 12, 6, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 12, 3, 8, 12, 3, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 4, 12, 9, 8, 12, 9, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 12, 4, 3, 12, 8, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 9, 12, 4, 9, 12, 8, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 12, 5, 5, 12, 5, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 12, 7, 5, 12, 7, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 12, 5, 7, 12, 5, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 12, 7, 7, 12, 7, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 8, 0, 5, 8, 1, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 8, 0, 7, 8, 1, stair4, stair4, false);
			this.placeBlock(levelIn, block3, 6, 9, 1, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 5, 8, 12, 5, 8, 12, stair3, stair3, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 8, 12, 7, 8, 12, stair4, stair4, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 8, 11, 5, 8, 11, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 6, 8, 11, 6, 8, 11, stair9, stair9, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 8, 11, 7, 8, 11, block1, block1, false);
			//walls
			for (int i = 1; i <= 11; i += 2) {
				this.generateBox(levelIn, boundingBoxIn, i, 9, 0, i, 12, 0, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, i, 10, 0, i, 11, 0, fence1, fence1, false);
				this.generateBox(levelIn, boundingBoxIn, i, 9, 12, i, 12, 12, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, i, 10, 12, i, 11, 12, fence1, fence1, false);
				this.generateBox(levelIn, boundingBoxIn, 0, 9, i, 0, 12, i, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, 0, 10, i, 0, 11, i, fence2, fence2, false);
				this.generateBox(levelIn, boundingBoxIn, 12, 9, i, 12, 12, i, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, 12, 10, i, 12, 11, i, fence2, fence2, false);
				this.placeBlock(levelIn, block2, i, 13, 0, boundingBoxIn);
				this.placeBlock(levelIn, block2, i, 13, 12, boundingBoxIn);
				this.placeBlock(levelIn, block2, 0, 13, i, boundingBoxIn);
				this.placeBlock(levelIn, block2, 12, 13, i, boundingBoxIn);
				if (i != 11) {
					this.placeBlock(levelIn, fence3, i + 1, 13, 0, boundingBoxIn);
					this.placeBlock(levelIn, fence3, i + 1, 13, 12, boundingBoxIn);
					this.placeBlock(levelIn, fence4, 0, 13, i + 1, boundingBoxIn);
					this.placeBlock(levelIn, fence4, 12, 13, i + 1, boundingBoxIn);
				}
			}
			this.placeBlock(levelIn, fence7, 0, 13, 0, boundingBoxIn);
			this.placeBlock(levelIn, fence8, 0, 13, 12, boundingBoxIn);
			this.placeBlock(levelIn, fence9, 12, 13, 12, boundingBoxIn);
			this.placeBlock(levelIn, fence10, 12, 13, 0, boundingBoxIn);
			for (int j1 = 3; j1 <= 9; j1 += 2) {
				this.generateBox(levelIn, boundingBoxIn, 1, 6, j1, 1, 9, j1, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, 1, 7, j1, 1, 8, j1, fence5, fence5, false);
				this.generateBox(levelIn, boundingBoxIn, 11, 6, j1, 11, 9, j1, block2, block2, false);
				this.generateBox(levelIn, boundingBoxIn, 11, 7, j1, 11, 8, j1, fence6, fence6, false);
			}
			//stairs
			for (int j = 0; j <= 6; ++j) {
				int k = j + 4;
				this.placeBlock(levelIn, stair7, 5, 5 + j, k, boundingBoxIn);
				this.placeBlock(levelIn, stair8, 6, 5 + j, k, boundingBoxIn);
				this.placeBlock(levelIn, stair7, 7, 5 + j, k, boundingBoxIn);
				if (k >= 5 && k <= 8) {
					this.generateBox(levelIn, boundingBoxIn, 5, 5, k, 5, j + 4, k, block1, block1, false);
					this.generateBox(levelIn, boundingBoxIn, 6, 5, k, 6, j + 4, k, block2, block2, false);
					this.generateBox(levelIn, boundingBoxIn, 7, 5, k, 7, j + 4, k, block1, block1, false);
				}
				else if (k >= 9 && k <= 10) {
					this.generateBox(levelIn, boundingBoxIn, 5, 8, k, 5, j + 4, k, block1, block1, false);
					this.generateBox(levelIn, boundingBoxIn, 6, 8, k, 6, j + 4, k, block2, block2, false);
					this.generateBox(levelIn, boundingBoxIn, 7, 8, k, 7, j + 4, k, block1, block1, false);
				}
				if (j >= 1)
					this.generateAirBox(levelIn, boundingBoxIn, 5, 6 + j, k, 7, 9 + j, k);
			}
			this.placeBlock(levelIn, stair7, 5, 12, 11, boundingBoxIn);
			this.placeBlock(levelIn, stair8, 6, 12, 11, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 7, 12, 11, boundingBoxIn);
			this.placeBlock(levelIn, block2, 6, 12, 12, boundingBoxIn);
			this.placeBlock(levelIn, fence10, 4, 13, 12, boundingBoxIn);
			this.placeBlock(levelIn, fence7, 8, 13, 12, boundingBoxIn);
			this.placeBlock(levelIn, block3, 6, 7, 8, boundingBoxIn);
			this.generateAirBox(levelIn, boundingBoxIn, 6, 5, 8, 6, 6, 8);
			if (this.isNeedingChest && boundingBoxIn.isInside(new BlockPos(this.getWorldX(1, 3), this.getWorldY(2), this.getWorldZ(1, 3)))) {
				this.isNeedingChest = false;
				this.createChest(levelIn, boundingBoxIn, randomIn, 6, 5, 8, BWChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE_STALK_ROOM);
			}
			if (!this.hasPlacedSpawner) {
				final BlockPos blockPos = new BlockPos(this.getWorldX(6, 7), this.getWorldY(5), this.getWorldZ(6, 7));
				if (boundingBoxIn.isInside(blockPos)) {
					this.hasPlacedSpawner = true;
					levelIn.setBlock(blockPos, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = levelIn.getBlockEntity(blockPos);
					if (spawner instanceof SpawnerBlockEntity)
						((SpawnerBlockEntity)spawner).setEntityId(EntityType.SKELETON, randomIn);
				}
			}
			this.generateBox(levelIn, boundingBoxIn, 5, 5, 7, 5, 5, 7, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 6, 7, 5, 7, 7, fence6, fence6, false);
			this.generateBox(levelIn, boundingBoxIn, 5, 8, 7, 5, 8, 7, stair8, stair8, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 5, 7, 7, 5, 7, block2, block2, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 6, 7, 7, 7, 7, fence5, fence5, false);
			this.generateBox(levelIn, boundingBoxIn, 7, 8, 7, 7, 8, 7, stair8, stair8, false);
			this.generateAirBox(levelIn, boundingBoxIn, 5, 13, 12, 7, 13, 12);
			this.placeBlock(levelIn, stair9, 5, 12, 7, boundingBoxIn);
			this.placeBlock(levelIn, stair9, 6, 12, 7, boundingBoxIn);
			this.placeBlock(levelIn, stair9, 7, 12, 7, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 5, 11, 6, boundingBoxIn);
			this.placeBlock(levelIn, stair9, 6, 11, 6, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 7, 11, 6, boundingBoxIn);
			this.placeBlock(levelIn, stair6, 5, 8, 9, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 5, 8, 10, boundingBoxIn);
			this.placeBlock(levelIn, stair5, 7, 8, 9, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 7, 8, 10, boundingBoxIn);
			//farm
			this.generateBox(levelIn, boundingBoxIn, 2, 5, 2, 3, 5, 3, block1, block1, false);
			this.placeBlock(levelIn, block2, 3, 5, 3, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 2, 5, 9, 3, 5, 10, block1, block1, false);
			this.placeBlock(levelIn, block2, 3, 5, 9, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 2, 5, 4, 2, 5, 8, block1, block1, false);
			this.generateBox(levelIn, boundingBoxIn, 9, 5, 2, 10, 5, 3, block1, block1, false);
			this.placeBlock(levelIn, block2, 9, 5, 3, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 9, 5, 9, 10, 5, 10, block1, block1, false);
			this.placeBlock(levelIn, block2, 9, 5, 9, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 10, 5, 4, 10, 5, 8, block1, block1, false);
			this.placeBlock(levelIn, stair11, 4, 5, 2, boundingBoxIn);
			this.placeBlock(levelIn, stair13, 4, 5, 3, boundingBoxIn);
			this.placeBlock(levelIn, stair13, 4, 5, 9, boundingBoxIn);
			this.placeBlock(levelIn, stair11, 4, 5, 10, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 8, 5, 2, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 8, 5, 3, boundingBoxIn);
			this.placeBlock(levelIn, stair12, 8, 5, 9, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 8, 5, 10, boundingBoxIn);
			this.generateBox(levelIn, boundingBoxIn, 3, 4, 4, 4, 4, 8, block4, block4, false);
			this.generateBox(levelIn, boundingBoxIn, 8, 4, 4, 9, 4, 8, block4, block4, false);
			this.generateBox(levelIn, boundingBoxIn, 3, 5, 4, 4, 5, 8, block5, block5, false);
			this.generateBox(levelIn, boundingBoxIn, 8, 5, 4, 9, 5, 8, block5, block5, false);
			//base pillars
			this.fillColumnDown(levelIn, block1, 0, 2, 0, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 0, 2, 12, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 12, 2, 0, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 12, 2, 12, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 0, 1, 6, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 6, 1, 0, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 6, 1, 12, boundingBoxIn);
			this.fillColumnDown(levelIn, block1, 12, 1, 6, boundingBoxIn);
			this.placeBlock(levelIn, block6, 0, 2, 6, boundingBoxIn);
			this.placeBlock(levelIn, block6, 6, 2, 0, boundingBoxIn);
			this.placeBlock(levelIn, block6, 6, 2, 12, boundingBoxIn);
			this.placeBlock(levelIn, block6, 12, 2, 6, boundingBoxIn);
			//pillars x
			this.placeBlock(levelIn, stair4, 11, 0, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair11, 11, 1, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 10, 1, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair11, 10, 2, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 9, 2, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 11, 0, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair11, 11, 1, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 10, 1, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair11, 10, 2, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 9, 2, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 5, 1, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair11, 5, 2, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 4, 2, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 5, 1, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair11, 5, 2, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair4, 4, 2, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 1, 0, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 1, 1, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 2, 1, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 2, 2, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 3, 2, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 1, 0, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 1, 1, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 2, 1, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 2, 2, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 3, 2, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 7, 1, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 7, 2, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 8, 2, 0, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 7, 1, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair10, 7, 2, 12, boundingBoxIn);
			this.placeBlock(levelIn, stair3, 8, 2, 12, boundingBoxIn);
			//pillars z
			this.placeBlock(levelIn, stair2, 0, 0, 11, boundingBoxIn);
			this.placeBlock(levelIn, stair14, 0, 1, 11, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 0, 1, 10, boundingBoxIn);
			this.placeBlock(levelIn, stair14, 0, 2, 10, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 0, 2, 9, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 12, 0, 11, boundingBoxIn);
			this.placeBlock(levelIn, stair14, 12, 1, 11, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 12, 1, 10, boundingBoxIn);
			this.placeBlock(levelIn, stair14, 12, 2, 10, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 12, 2, 9, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 0, 1, 5, boundingBoxIn);
			this.placeBlock(levelIn, stair14, 0, 2, 5, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 0, 2, 4, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 12, 1, 5, boundingBoxIn);
			this.placeBlock(levelIn, stair14, 12, 2, 5, boundingBoxIn);
			this.placeBlock(levelIn, stair2, 12, 2, 4, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 0, 0, 1, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 0, 1, 1, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 0, 1, 2, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 0, 2, 2, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 0, 2, 3, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 12, 0, 1, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 12, 1, 1, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 12, 1, 2, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 12, 2, 2, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 12, 2, 3, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 0, 1, 7, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 0, 2, 7, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 0, 2, 8, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 12, 1, 7, boundingBoxIn);
			this.placeBlock(levelIn, stair7, 12, 2, 7, boundingBoxIn);
			this.placeBlock(levelIn, stair1, 12, 2, 8, boundingBoxIn);
		}
	}

	private abstract static class InfernalNetherBridgePiece extends SmartStructurePiece<StartPiece, InfernalNetherBridgePiece> {
		private static final Logger LOGGER = BlightedWorldsMod.LOGGER;
		private boolean isTestPiece = false;

		protected InfernalNetherBridgePiece(final StructurePieceType typeIn, final int genDepthIn, final BoundingBox boundingBoxIn) {
			super(typeIn, genDepthIn, boundingBoxIn);
		}

		public InfernalNetherBridgePiece(final StructurePieceType typeIn, final CompoundTag compoundIn) {
			super(typeIn, compoundIn);
		}

		protected List<ChildInfo> getChildInfo(final RandomSource randomIn) {
			return List.of();
		}

		@Override
		protected void addAdditionalSaveData(final StructurePieceSerializationContext contextIn, final CompoundTag compoundIn) {};

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

		private final InfernalNetherBridgePiece generatePiece(final ChildInfo childInfoIn, final StartPiece startIn, final List<PieceWeight> weightsIn, final SmartStructurePiecesBuilder piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int genDepthIn) {
			final int i = this.updatePieceWeight(weightsIn);
			final boolean flag = i > 0 && genDepthIn <= 30;
			int j = 0;
			while (j < 5 && flag) {
				++j;
				int k = randomIn.nextInt(i);
				for (final PieceWeight pieceWeight : weightsIn) {
					k -= pieceWeight.weight;
					if (k < 0) {
						if (!pieceWeight.doPlace() || (pieceWeight.weightClass == this.getClass() && !pieceWeight.allowInRow))
							continue;
						final InfernalNetherBridgePiece piece = findAndCreateBridgePieceFactory(pieceWeight, piecesIn, randomIn, xIn, yIn, zIn, facingIn, genDepthIn, childInfoIn.endType);
						if (piece != null) {
							if (!this.isTestPiece) {
								piece.isTestPiece = true;
								boolean result = true;
								for (int index = 0; index < piece.getChildInfo(randomIn).size(); index++) {
									final ChildInfo childInfo = piece.getChildInfo(randomIn).get(index);
									if (childInfo.endType == EndType.NONE) {
										LOGGER.info("This: " + this.getClass().getSimpleName() + ", Child: " + piece.getClass().getSimpleName() + ", Pos: " + new BlockPos(xIn, yIn, zIn));
										final InfernalNetherBridgePiece childPiece = piece.generateChild(startIn, piecesIn, randomIn, index);
										LOGGER.info(childPiece == null ? "Piece: NULL!" : ("Piece: " + childPiece.getClass().getSimpleName() + ", Collision: " + piecesIn.findCollisionPiece(childPiece.boundingBox)));
										if (childPiece == null) {
											result = false;
											break;
										}
									}
								}
								if (!result) continue;
								piece.isTestPiece = false;
								++pieceWeight.placeCount;
								if (!pieceWeight.isValid())
									weightsIn.remove(pieceWeight);
							}
							return piece;
						}
					}
				}
			}
			return this.isTestPiece ? null : BridgeEndFiller1.createPiece(piecesIn, randomIn, xIn, yIn, zIn, facingIn, genDepthIn, childInfoIn.endType);
		}

		private final InfernalNetherBridgePiece generateAndAddPiece(final ChildInfo childInfoIn, final StartPiece startIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final int zIn, final @Nullable Direction facingIn, final int genDepthIn) {
			if (piecesIn instanceof SmartStructurePiecesBuilder smartPieces) {
				if (Math.abs(xIn - startIn.getBoundingBox().minX()) <= 16 * this.maxPlacementChunkRange() && Math.abs(zIn - startIn.getBoundingBox().minZ()) <= 16 * this.maxPlacementChunkRange()) {
					final ArrayList<PieceWeight> weights = new ArrayList<>(childInfoIn.secondary ? startIn.availableCastlePieces : startIn.availableBridgePieces);
					if (!this.getUnacceptableChildren().isEmpty())
						weights.removeIf(pieceWeight -> this.getUnacceptableChildren().contains(pieceWeight.weightClass));
					if (!startIn.hasPrerequisites && !startIn.getUnsatisfactoryPieces().isEmpty())
						weights.removeIf(pieceWeight -> startIn.getUnsatisfactoryPieces().contains(pieceWeight.weightClass));
					final InfernalNetherBridgePiece piece = this.generatePiece(childInfoIn, startIn, weights, smartPieces, randomIn, xIn, yIn, zIn, facingIn, genDepthIn + 1);
					if (!this.isTestPiece && piece != null) {
						if (!startIn.hasPrerequisites && !startIn.getSatisfactoryPieces().isEmpty() && startIn.getSatisfactoryPieces().contains(piece.getClass()))
							startIn.hasPrerequisites = true;
						piecesIn.addPiece(piece);
						startIn.pendingChildren.add(piece);
					}
					return piece;
				}
			}
			return this.isTestPiece ? null : BridgeEndFiller1.createPiece(piecesIn, randomIn, xIn, yIn, zIn, facingIn, genDepthIn, childInfoIn.endType);
		}

		@Override
		@Nullable
		protected InfernalNetherBridgePiece generateChild(final StartPiece startIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int indexIn) {
			final Direction facing = this.getOrientation();
			if (facing != null) {
				final ChildInfo childInfo = this.getChildInfo(randomIn).get(indexIn);
				switch (childInfo.genDir) {
				case NORTH:
					switch (facing) {
					case NORTH:
						return this.generateAndAddPiece(childInfo, startIn, piecesIn, randomIn, this.boundingBox.minX() + childInfo.x, this.boundingBox.minY() + childInfo.y, this.boundingBox.minZ() - 1, facing, this.getGenDepth());
					case SOUTH:
						return this.generateAndAddPiece(childInfo, startIn, piecesIn, randomIn, this.boundingBox.minX() + childInfo.x, this.boundingBox.minY() + childInfo.y, this.boundingBox.maxZ() + 1, facing, this.getGenDepth());
					case WEST:
						return this.generateAndAddPiece(childInfo, startIn, piecesIn, randomIn, this.boundingBox.minX() - 1, this.boundingBox.minY() + childInfo.y, this.boundingBox.minZ() + childInfo.x, facing, this.getGenDepth());
					case EAST:
						return this.generateAndAddPiece(childInfo, startIn, piecesIn, randomIn, this.boundingBox.maxX() + 1, this.boundingBox.minY() + childInfo.y, this.boundingBox.minZ() + childInfo.x, facing, this.getGenDepth());
					default:
						break;
					}
				case WEST:
					switch (facing) {
					case NORTH:
						return this.generateAndAddPiece(childInfo, startIn, piecesIn, randomIn, this.boundingBox.minX() - 1, this.boundingBox.minY() + childInfo.x, this.boundingBox.minZ() + childInfo.y, Direction.WEST, this.getGenDepth());
					case SOUTH:
						return this.generateAndAddPiece(childInfo, startIn, piecesIn, randomIn, this.boundingBox.minX() - 1, this.boundingBox.minY() + childInfo.x, this.boundingBox.minZ() + childInfo.y, Direction.WEST, this.getGenDepth());
					case WEST:
						return this.generateAndAddPiece(childInfo, startIn, piecesIn, randomIn, this.boundingBox.minX() + childInfo.y, this.boundingBox.minY() + childInfo.x, this.boundingBox.minZ() - 1, Direction.NORTH, this.getGenDepth());
					case EAST:
						return this.generateAndAddPiece(childInfo, startIn, piecesIn, randomIn, this.boundingBox.minX() + childInfo.y, this.boundingBox.minY() + childInfo.x, this.boundingBox.minZ() - 1, Direction.NORTH, this.getGenDepth());
					default:
						break;
					}
				case EAST:
					switch (facing) {
					case NORTH:
						return this.generateAndAddPiece(childInfo, startIn, piecesIn, randomIn, this.boundingBox.maxX() + 1, this.boundingBox.minY() + childInfo.x, this.boundingBox.minZ() + childInfo.y, Direction.EAST, this.getGenDepth());
					case SOUTH:
						return this.generateAndAddPiece(childInfo, startIn, piecesIn, randomIn, this.boundingBox.maxX() + 1, this.boundingBox.minY() + childInfo.x, this.boundingBox.minZ() + childInfo.y, Direction.EAST, this.getGenDepth());
					case WEST:
						return this.generateAndAddPiece(childInfo, startIn, piecesIn, randomIn, this.boundingBox.minX() + childInfo.y, this.boundingBox.minY() + childInfo.x, this.boundingBox.maxZ() + 1, Direction.SOUTH, this.getGenDepth());
					case EAST:
						return this.generateAndAddPiece(childInfo, startIn, piecesIn, randomIn, this.boundingBox.minX() + childInfo.y, this.boundingBox.minY() + childInfo.x, this.boundingBox.maxZ() + 1, Direction.SOUTH, this.getGenDepth());
					default:
						break;
					}
				default:
					break;
				}
			}
			return null;
		}

		protected static final boolean isOkBox(final BoundingBox boundingBoxIn) {
			return boundingBoxIn != null && boundingBoxIn.minY() > 10;
		}
	}

	private static final class PieceWeight {
		protected final Class<? extends InfernalNetherBridgePiece> weightClass;
		protected final int weight;
		protected int placeCount;
		protected final int maxPlaceCount;
		protected final boolean allowInRow;

		public PieceWeight(final Class<? extends InfernalNetherBridgePiece> weightClassIn, final int weightIn, final int maxPlaceCountIn, final boolean allowInRowIn) {
			this.weightClass = weightClassIn;
			this.weight = weightIn;
			this.maxPlaceCount = maxPlaceCountIn;
			this.allowInRow = allowInRowIn;
		}

		public PieceWeight(final Class<? extends InfernalNetherBridgePiece> weightClassIn, final int weightIn, final int maxPlaceCountIn) {
			this(weightClassIn, weightIn, maxPlaceCountIn, false);
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

		private ChildInfo(final Direction genDirIn, final int xIn, final int yIn, final boolean secondaryIn, final EndType endTypeIn) {
			this.genDir = genDirIn;
			this.x = xIn;
			this.y = yIn;
			this.secondary = secondaryIn;
			this.endType = endTypeIn;
		}
	}

	public static final class StartPiece extends GrandMonsterThrone {
		protected final ArrayList<SmartStructurePiece<?, ?>> pendingChildren = new ArrayList<>();
		protected Structure.GenerationContext context;
		private ArrayList<PieceWeight> availableBridgePieces;
		private ArrayList<PieceWeight> availableCastlePieces;
		private boolean hasPrerequisites;

		public StartPiece(final Structure.GenerationContext contextIn, final int xIn, final int zIn) {
			super(xIn, zIn, getRandomHorizontalDirection(contextIn.random()));
			this.context = contextIn;
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

		public StartPiece(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_START.get(), compoundIn);
		}

		public List<Class<? extends InfernalNetherBridgePiece>> getUnsatisfactoryPieces() {
			return List.of(CastleEntrance.class, MonsterThrone.class);
		}

		public List<Class<? extends InfernalNetherBridgePiece>> getSatisfactoryPieces() {
			return List.of(BridgeCrossing1.class, BridgeCrossing2.class, BridgeCrossing3.class, RoomCrossing.class, LavaRoomCrossing.class);
		}
	}
}
