package dev.theagameplayer.blightedworlds.world.level.levelgen.structure.structures;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

import dev.theagameplayer.blightedworlds.data.loot.packs.BWChestLoot;
import dev.theagameplayer.blightedworlds.registries.BWBlocks;
import dev.theagameplayer.blightedworlds.registries.BWStructurePieceTypes;
import dev.theagameplayer.blightedworlds.world.level.block.AncientNetherAltarBlock;
import dev.theagameplayer.blightedworlds.world.level.block.BlazeLanternBlock;
import dev.theagameplayer.blightedworlds.world.level.levelgen.structure.TeleportDestinationPiece;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
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
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

public final class InfernalNetherFortressPieces {
	private static final InfernalNetherFortressPieces.PieceWeight[] PRIMARY_COMPONENTS = new InfernalNetherFortressPieces.PieceWeight[] {
			new InfernalNetherFortressPieces.PieceWeight(InfernalNetherFortressPieces.BridgeStraight1.class, 15, 0, true), 
			new InfernalNetherFortressPieces.PieceWeight(InfernalNetherFortressPieces.BridgeStraight2.class, 5, 0, false), 
			new InfernalNetherFortressPieces.PieceWeight(InfernalNetherFortressPieces.BridgeStraight3.class, 10, 0, true), 
			new InfernalNetherFortressPieces.PieceWeight(InfernalNetherFortressPieces.BridgeCrossing1.class, 10, 8), 
			new InfernalNetherFortressPieces.PieceWeight(InfernalNetherFortressPieces.BridgeCrossing2.class, 10, 8), 
			new InfernalNetherFortressPieces.PieceWeight(InfernalNetherFortressPieces.BridgeCrossing3.class, 10, 6), 
			new InfernalNetherFortressPieces.PieceWeight(InfernalNetherFortressPieces.RoomCrossing.class, 10, 8), 
			new InfernalNetherFortressPieces.PieceWeight(InfernalNetherFortressPieces.BigRoomCrossing.class, 5, 4), 
			new InfernalNetherFortressPieces.PieceWeight(InfernalNetherFortressPieces.CastleEntrance.class, 5, 2), 
			new InfernalNetherFortressPieces.PieceWeight(InfernalNetherFortressPieces.StairsRoom.class, 10, 6), 
			new InfernalNetherFortressPieces.PieceWeight(InfernalNetherFortressPieces.MonsterThrone.class, 5, 4),
			new InfernalNetherFortressPieces.PieceWeight(InfernalNetherFortressPieces.GrandMonsterThrone.class, 10, 1)
	};
	private static final InfernalNetherFortressPieces.PieceWeight[] SECONDARY_COMPONENTS = new InfernalNetherFortressPieces.PieceWeight[] {
			new InfernalNetherFortressPieces.PieceWeight(InfernalNetherFortressPieces.CastleSmallCorridorLeftTurnPiece.class, 5, 20), 
			new InfernalNetherFortressPieces.PieceWeight(InfernalNetherFortressPieces.CastleSmallCorridorRightTurnPiece.class, 5, 20), 
			new InfernalNetherFortressPieces.PieceWeight(InfernalNetherFortressPieces.CastleCorridorStairsPiece.class, 10, 6, true), 
			new InfernalNetherFortressPieces.PieceWeight(InfernalNetherFortressPieces.CastleCorridorTBalconyPiece.class, 7, 4),
			new InfernalNetherFortressPieces.PieceWeight(InfernalNetherFortressPieces.CastleSmallCorridorPiece.class, 25, 0, true),
			new InfernalNetherFortressPieces.PieceWeight(InfernalNetherFortressPieces.CastleStalkRoom.class, 5, 3)
	};

	private static InfernalNetherFortressPieces.InfernalNetherBridgePiece findAndCreateBridgePieceFactory(final InfernalNetherFortressPieces.PieceWeight weightIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int componentTypeIn) {
		final Class<? extends InfernalNetherFortressPieces.InfernalNetherBridgePiece> oClass = weightIn.weightClass;
		InfernalNetherFortressPieces.InfernalNetherBridgePiece piece = null;
		if (oClass == InfernalNetherFortressPieces.BridgeEndFiller.class) {
			piece = InfernalNetherFortressPieces.BridgeEndFiller.createPiece(piecesIn, randomIn, xIn, yIn, zIn, facingIn, componentTypeIn);
		} else if (oClass == InfernalNetherFortressPieces.BridgeStraight1.class) {
			piece = InfernalNetherFortressPieces.BridgeStraight1.createPiece(piecesIn, randomIn, xIn, yIn, zIn, facingIn, componentTypeIn);
		} else if (oClass == InfernalNetherFortressPieces.BridgeStraight2.class) {
			piece = InfernalNetherFortressPieces.BridgeStraight2.createPiece(piecesIn, randomIn, xIn, yIn, zIn, facingIn, componentTypeIn);
		} else if (oClass == InfernalNetherFortressPieces.BridgeStraight3.class) {
			piece = InfernalNetherFortressPieces.BridgeStraight3.createPiece(piecesIn, randomIn, xIn, yIn, zIn, facingIn, componentTypeIn);
		} else if (oClass == InfernalNetherFortressPieces.BridgeCrossing1.class) {
			piece = InfernalNetherFortressPieces.BridgeCrossing1.createPiece(piecesIn, xIn, yIn, zIn, facingIn, componentTypeIn);
		} else if (oClass == InfernalNetherFortressPieces.BridgeCrossing2.class) {
			piece = InfernalNetherFortressPieces.BridgeCrossing2.createPiece(piecesIn, xIn, yIn, zIn, facingIn, componentTypeIn);
		} else if (oClass == InfernalNetherFortressPieces.BridgeCrossing3.class) {
			piece = InfernalNetherFortressPieces.BridgeCrossing3.createPiece(piecesIn, xIn, yIn, zIn, facingIn, componentTypeIn);
		} else if (oClass == InfernalNetherFortressPieces.RoomCrossing.class) {
			piece = InfernalNetherFortressPieces.RoomCrossing.createPiece(piecesIn, xIn, yIn, zIn, facingIn, componentTypeIn);
		} else if (oClass == InfernalNetherFortressPieces.BigRoomCrossing.class) {
			piece = InfernalNetherFortressPieces.BigRoomCrossing.createPiece(piecesIn, xIn, yIn, zIn, facingIn, componentTypeIn);
		} else if (oClass == InfernalNetherFortressPieces.CastleEntrance.class) {
			piece = InfernalNetherFortressPieces.CastleEntrance.createPiece(piecesIn, randomIn, xIn, yIn, zIn, facingIn, componentTypeIn);
		} else if (oClass == InfernalNetherFortressPieces.StairsRoom.class) {
			piece = InfernalNetherFortressPieces.StairsRoom.createPiece(piecesIn, xIn, yIn, zIn, facingIn, componentTypeIn);
		} else if (oClass == InfernalNetherFortressPieces.MonsterThrone.class) {
			piece = InfernalNetherFortressPieces.MonsterThrone.createPiece(piecesIn, xIn, yIn, zIn, facingIn, componentTypeIn);
		} else if (oClass == InfernalNetherFortressPieces.GrandMonsterThrone.class) {
			piece = InfernalNetherFortressPieces.GrandMonsterThrone.createPiece(piecesIn, xIn, yIn, zIn, facingIn, componentTypeIn);
		} else if (oClass == InfernalNetherFortressPieces.CastleSmallCorridorLeftTurnPiece.class) {
			piece = InfernalNetherFortressPieces.CastleSmallCorridorLeftTurnPiece.createPiece(piecesIn, randomIn, xIn, yIn, zIn, facingIn, componentTypeIn);
		} else if (oClass == InfernalNetherFortressPieces.CastleSmallCorridorRightTurnPiece.class) {
			piece = InfernalNetherFortressPieces.CastleSmallCorridorRightTurnPiece.createPiece(piecesIn, randomIn, xIn, yIn, zIn, facingIn, componentTypeIn);
		} else if (oClass == InfernalNetherFortressPieces.CastleCorridorStairsPiece.class) {
			piece = InfernalNetherFortressPieces.CastleCorridorStairsPiece.createPiece(piecesIn, xIn, yIn, zIn, facingIn, componentTypeIn);
		} else if (oClass == InfernalNetherFortressPieces.CastleCorridorTBalconyPiece.class) {
			piece = InfernalNetherFortressPieces.CastleCorridorTBalconyPiece.createPiece(piecesIn, xIn, yIn, zIn, facingIn, componentTypeIn);
		} else if (oClass == InfernalNetherFortressPieces.CastleSmallCorridorPiece.class) {
			piece = InfernalNetherFortressPieces.CastleSmallCorridorPiece.createPiece(piecesIn, xIn, yIn, zIn, facingIn, componentTypeIn);
		} else if (oClass == InfernalNetherFortressPieces.CastleStalkRoom.class) {
			piece = InfernalNetherFortressPieces.CastleStalkRoom.createPiece(piecesIn, xIn, yIn, zIn, facingIn, componentTypeIn);
		}
		return piece;
	}

	public static class BridgeEndFiller extends InfernalNetherFortressPieces.InfernalNetherBridgePiece {
		private final int selfSeed;

		public BridgeEndFiller(final int componentTypeIn, final RandomSource randomIn, final BoundingBox boxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_END_FILLER.get(), componentTypeIn, boxIn);
			this.setOrientation(facingIn);
			this.selfSeed = randomIn.nextInt();
		}

		public BridgeEndFiller(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_END_FILLER.get(), compoundIn);
			this.selfSeed = compoundIn.getInt("Seed");
		}

		public static final InfernalNetherFortressPieces.BridgeEndFiller createPiece(final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int componentTypeIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -1, -3, 0, 5, 10, 8, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new InfernalNetherFortressPieces.BridgeEndFiller(componentTypeIn, randomIn, box, facingIn) : null;
		}

		@Override
		protected void addAdditionalSaveData(final StructurePieceSerializationContext contextIn, final CompoundTag compoundIn) {
			super.addAdditionalSaveData(contextIn, compoundIn);
			compoundIn.putInt("Seed", this.selfSeed);
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final RandomSource random = RandomSource.create(this.selfSeed);
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState();
			final BlockState block4 = Blocks.GLOWSTONE.defaultBlockState();
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair3 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			//air
			this.generateAirBox(levelIn, boxIn, 1, 5, 0, 3, 9, 8);
			//left rail
			for (int w5 = 0; w5 <= 0; ++w5) {
				final int l3 = random.nextInt(5) + 1;
				this.generateBox(levelIn, boxIn, w5, 5, 0, w5, 5, l3, block1, block1, false);
				this.generateBox(levelIn, boxIn, w5, 5, l3 + 1, w5, 5, l3 + 1, stair1, stair1, false);
				this.placeBlock(levelIn, block3, w5, 6, 0, boxIn);
				this.placeBlock(levelIn, block4, w5, 7, 0, boxIn);
			}
			//right rail
			for (int w6 = 4; w6 <= 4; ++w6) {
				final int l3 = random.nextInt(5) + 1;
				this.generateBox(levelIn, boxIn, w6, 5, 0, w6, 5, l3, block1, block1, false);
				this.generateBox(levelIn, boxIn, w6, 5, l3 + 1, w6, 5, l3 + 1, stair1, stair1, false);
				this.placeBlock(levelIn, block3, w6, 6, 0, boxIn);
				this.placeBlock(levelIn, block4, w6, 7, 0, boxIn);
			}
			//top-left
			for (int w1 = 0; w1 <= 1; ++w1) {
				final int l = random.nextInt(8);
				this.generateBox(levelIn, boxIn, w1, 4, 0, w1, 4, l, block1, block1, false);
				this.generateBox(levelIn, boxIn, w1, 4, l + 1, w1, 4, l + 1, stair2, stair2, false);
			}
			//top-middle
			for (int w2 = 2; w2 <= 2; ++w2) {
				final int l = random.nextInt(8);
				this.generateBox(levelIn, boxIn, w2, 4, 0, w2, 4, l, block2, block2, false);
				this.generateBox(levelIn, boxIn, w2, 4, l + 1, w2, 4, l + 1, stair3, stair3, false);
			}
			//top-right
			for (int w3 = 3; w3 <= 4; ++w3) {
				final int l = random.nextInt(8);
				this.generateBox(levelIn, boxIn, w3, 4, 0, w3, 4, l, block1, block1, false);
				this.generateBox(levelIn, boxIn, w3, 4, l + 1, w3, 4, l + 1, stair2, stair2, false);
			}
			//bottom
			for (int w4 = 0; w4 <= 4; ++w4) {
				for (int h1 = 2; h1 <= 3; ++h1) {
					final int l2 = random.nextInt(2 + ((h1 - 1) * 2));
					this.generateBox(levelIn, boxIn, w4, h1, 0, w4, h1, l2, block1, block1, false);
					this.generateBox(levelIn, boxIn, w4, h1, l2 + 1, w4, h1, l2 + 1, stair2, stair2, false);
				}
			}
		}
	}

	public static class BridgeStraight1 extends InfernalNetherFortressPieces.InfernalNetherBridgePiece {
		public BridgeStraight1(final int componentTypeIn, final RandomSource randomIn, final BoundingBox boxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_STRAIGHT1.get(), componentTypeIn, boxIn);
			this.setOrientation(facingIn);
		}

		public BridgeStraight1(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_STRAIGHT1.get(), compoundIn);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChildForward((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 1, 3, false);
		}

		public static final InfernalNetherFortressPieces.BridgeStraight1 createPiece(final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int componentTypeIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -1, -3, 0, 5, 10, 19, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new InfernalNetherFortressPieces.BridgeStraight1(componentTypeIn, randomIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
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
			this.generateAirBox(levelIn, boxIn, 1, 5, 0, 3, 9, 18);
			//top
			this.generateBox(levelIn, boxIn, 0, 4, 0, 1, 4, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 2, 4, 0, 2, 4, 18, block2, block2, false);
			this.generateBox(levelIn, boxIn, 3, 4, 0, 4, 4, 18, block1, block1, false);
			//rails
			this.generateBox(levelIn, boxIn, 0, 5, 0, 0, 5, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 6, 3, 0, 6, 3, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 0, 6, 4, 0, 6, 4, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 0, 6, 8, 0, 6, 8, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 0, 6, 9, 0, 6, 9, slab1, slab1, false);
			this.generateBox(levelIn, boxIn, 0, 6, 10, 0, 6, 10, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 0, 6, 14, 0, 6, 14, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 0, 6, 15, 0, 6, 15, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 4, 5, 0, 4, 5, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 4, 6, 3, 4, 6, 3, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 4, 6, 4, 4, 6, 4, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 4, 6, 8, 4, 6, 8, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 4, 6, 9, 4, 6, 9, slab1, slab1, false);
			this.generateBox(levelIn, boxIn, 4, 6, 10, 4, 6, 10, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 4, 6, 14, 4, 6, 14, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 4, 6, 15, 4, 6, 15, stair4, stair4, false);
			//bottom
			this.generateBox(levelIn, boxIn, 0, 3, 0, 4, 3, 18, block1, block1, false);
			//pillars
			this.generateBox(levelIn, boxIn, 0, 2, 0, 4, 2, 0, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 0, 2, 4, 4, 2, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 2, 18, 4, 2, 18, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 0, 2, 14, 4, 2, 14, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 2, 3, 4, 2, 3, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 0, 2, 5, 4, 2, 5, stair2, stair2, false);
			this.generateBox(levelIn, boxIn, 0, 2, 15, 4, 2, 15, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 0, 2, 13, 4, 2, 13, stair1, stair1, false);
			this.generateBox(levelIn, boxIn, 0, 1, 1, 4, 1, 1, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 0, 1, 2, 4, 1, 2, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 0, 0, 1, 4, 0, 2, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 1, 0, 4, 1, 0, stair1, stair1, false);
			this.generateBox(levelIn, boxIn, 0, 1, 18, 4, 1, 18, stair2, stair2, false);
			this.generateBox(levelIn, boxIn, 0, 1, 16, 4, 1, 16, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 0, 1, 17, 4, 1, 17, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 0, 0, 16, 4, 0, 17, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 1, 3, 4, 1, 3, stair2, stair2, false);
			this.generateBox(levelIn, boxIn, 0, 1, 15, 4, 1, 15, stair1, stair1, false);
			for (int w1 = 0; w1 <= 4; ++w1) {
				for (int l1 = 1; l1 <= 2; ++l1) {
					this.fillColumnDown(levelIn, block1, w1, -1, l1, boxIn);
					this.fillColumnDown(levelIn, block1, w1, -1, 18 - l1, boxIn);
				}
			}
			//decoration
			final int chainLength = randomIn.nextInt(2);
			this.placeBlock(levelIn, block4, 0, 2, 9, boxIn);
			this.placeBlock(levelIn, block3, 0, 1, 9, boxIn);
			for (int length = 0; length < chainLength; length++)
				this.placeBlock(levelIn, block6, 0, 0 - length, 9, boxIn);
			this.placeBlock(levelIn, block5, 0, 0 - chainLength, 9, boxIn);
			this.placeBlock(levelIn, block4, 4, 2, 9, boxIn);
			this.placeBlock(levelIn, block3, 4, 1, 9, boxIn);
			for (int length = 0; length < chainLength; length++)
				this.placeBlock(levelIn, block6, 4, 0 - length, 9, boxIn);
			this.placeBlock(levelIn, block5, 4, 0 - chainLength, 9, boxIn);
			this.placeBlock(levelIn, block3, 0, 2, 7, boxIn);
			for (int length = 0; length < chainLength; length++)
				this.placeBlock(levelIn, block6, 0, 1 - length, 7, boxIn);
			this.placeBlock(levelIn, block5, 0, 1 - chainLength, 7, boxIn);
			this.placeBlock(levelIn, block3, 4, 2, 7, boxIn);
			for (int length = 0; length < chainLength; length++)
				this.placeBlock(levelIn, block6, 4, 1 - length, 7, boxIn);
			this.placeBlock(levelIn, block5, 4, 1 - chainLength, 7, boxIn);
			this.placeBlock(levelIn, block3, 0, 2, 11, boxIn);
			for (int length = 0; length < chainLength; length++)
				this.placeBlock(levelIn, block6, 0, 1 - length, 11, boxIn);
			this.placeBlock(levelIn, block5, 0, 1 - chainLength, 11, boxIn);
			this.placeBlock(levelIn, block3, 4, 2, 11, boxIn);
			for (int length = 0; length < chainLength; length++)
				this.placeBlock(levelIn, block6, 4, 1 - length, 11, boxIn);
			this.placeBlock(levelIn, block5, 4, 1 - chainLength, 11, boxIn);
		}
	}

	public static class BridgeStraight2 extends InfernalNetherFortressPieces.InfernalNetherBridgePiece {
		private final int selfSeed;

		public BridgeStraight2(final int componentTypeIn, final RandomSource randomIn, final BoundingBox boxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_STRAIGHT2.get(), componentTypeIn, boxIn);
			this.setOrientation(facingIn);
			this.selfSeed = randomIn.nextInt();
		}

		public BridgeStraight2(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_STRAIGHT2.get(), compoundIn);
			this.selfSeed = compoundIn.getInt("Seed");
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChildForward((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 1, 3, false);
		}

		public static final InfernalNetherFortressPieces.BridgeStraight2 createPiece(final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int componentTypeIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -1, -3, 0, 5, 10, 19, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new InfernalNetherFortressPieces.BridgeStraight2(componentTypeIn, randomIn, box, facingIn) : null;
		}

		@Override
		protected void addAdditionalSaveData(final StructurePieceSerializationContext contextIn, final CompoundTag compoundIn) {
			super.addAdditionalSaveData(contextIn, compoundIn);
			compoundIn.putInt("Seed", this.selfSeed);
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final RandomSource random = RandomSource.create(this.selfSeed);
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState();
			final BlockState block4 = Blocks.GLOWSTONE.defaultBlockState();
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair3 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair5 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair6 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			//air
			this.generateAirBox(levelIn, boxIn, 1, 5, 0, 3, 9, 18);
			//left rail
			for (int w5 = 0; w5 <= 0; ++w5) {
				final int l3 = random.nextInt(5) + 1;
				this.generateBox(levelIn, boxIn, w5, 5, 0, w5, 5, l3, block1, block1, false);
				this.generateBox(levelIn, boxIn, w5, 5, l3 + 1, w5, 5, l3 + 1, stair1, stair1, false);
				this.placeBlock(levelIn, block3, w5, 6, 0, boxIn);
				this.placeBlock(levelIn, block4, w5, 7, 0, boxIn);
				final int l6 = random.nextInt(5) + 1;
				this.generateBox(levelIn, boxIn, w5, 5, 18 - l6, w5, 5, 18, block1, block1, false);
				this.generateBox(levelIn, boxIn, w5, 5, (18 - l6) - 1, w5, 5, (18 - l6) - 1, stair4, stair4, false);
				this.placeBlock(levelIn, block3, w5, 6, 18, boxIn);
				this.placeBlock(levelIn, block4, w5, 7, 18, boxIn);
			}
			//right rail
			for (int w6 = 4; w6 <= 4; ++w6) {
				final int l3 = random.nextInt(5) + 1;
				this.generateBox(levelIn, boxIn, w6, 5, 0, w6, 5, l3, block1, block1, false);
				this.generateBox(levelIn, boxIn, w6, 5, l3 + 1, w6, 5, l3 + 1, stair1, stair1, false);
				this.placeBlock(levelIn, block3, w6, 6, 0, boxIn);
				this.placeBlock(levelIn, block4, w6, 7, 0, boxIn);
				final int l6 = random.nextInt(5) + 1;
				this.generateBox(levelIn, boxIn, w6, 5, 18 - l6, w6, 5, 18, block1, block1, false);
				this.generateBox(levelIn, boxIn, w6, 5, (18 - l6) - 1, w6, 5, (18 - l6) - 1, stair4, stair4, false);
				this.placeBlock(levelIn, block3, w6, 6, 18, boxIn);
				this.placeBlock(levelIn, block4, w6, 7, 18, boxIn);
			}
			//top-left
			for (int w1 = 0; w1 <= 1; ++w1) {
				final int l = random.nextInt(8);
				this.generateBox(levelIn, boxIn, w1, 4, 0, w1, 4, l, block1, block1, false);
				this.generateBox(levelIn, boxIn, w1, 4, l + 1, w1, 4, l + 1, stair2, stair2, false);
				final int l4 = random.nextInt(8);
				this.generateBox(levelIn, boxIn, w1, 4, 18 - l4, w1, 4, 18, block1, block1, false);
				this.generateBox(levelIn, boxIn, w1, 4, (18 - l4) - 1, w1, 4, (18 - l4) - 1, stair5, stair5, false);
			}
			//top-middle
			for (int w2 = 2; w2 <= 2; ++w2) {
				final int l = random.nextInt(8);
				this.generateBox(levelIn, boxIn, w2, 4, 0, w2, 4, l, block2, block2, false);
				this.generateBox(levelIn, boxIn, w2, 4, l + 1, w2, 4, l + 1, stair3, stair3, false);
				final int l4 = random.nextInt(8);
				this.generateBox(levelIn, boxIn, w2, 4, 18 - l4, w2, 4, 18, block2, block2, false);
				this.generateBox(levelIn, boxIn, w2, 4, (18 - l4) - 1, w2, 4, (18 - l4) - 1, stair6, stair6, false);
			}
			//top-right
			for (int w3 = 3; w3 <= 4; ++w3) {
				final int l = random.nextInt(8);
				this.generateBox(levelIn, boxIn, w3, 4, 0, w3, 4, l, block1, block1, false);
				this.generateBox(levelIn, boxIn, w3, 4, l + 1, w3, 4, l + 1, stair2, stair2, false);
				final int l4 = random.nextInt(8);
				this.generateBox(levelIn, boxIn, w3, 4, 18 - l4, w3, 4, 18, block1, block1, false);
				this.generateBox(levelIn, boxIn, w3, 4, (18 - l4) - 1, w3, 4, (18 - l4) - 1, stair5, stair5, false);
			}
			//bottom
			for (int w4 = 0; w4 <= 4; ++w4) {
				for (int h1 = 2; h1 <= 3; ++h1) {
					final int l2 = random.nextInt(2 + ((h1 - 1) * 2));
					this.generateBox(levelIn, boxIn, w4, h1, 0, w4, h1, l2, block1, block1, false);
					this.generateBox(levelIn, boxIn, w4, h1, l2 + 1, w4, h1, l2 + 1, stair2, stair2, false);
					final int l5 = random.nextInt(2 + ((h1 - 1) * 2));
					this.generateBox(levelIn, boxIn, w4, h1, 18 - l5, w4, h1, 18, block1, block1, false);
					this.generateBox(levelIn, boxIn, w4, h1, (18 - l5) - 1, w4, h1, (18 - l5) - 1, stair5, stair5, false);
				}
			}
		}
	}

	public static class BridgeStraight3 extends InfernalNetherFortressPieces.InfernalNetherBridgePiece {
		public BridgeStraight3(final int componentTypeIn, final RandomSource randomIn, final BoundingBox boxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_STRAIGHT3.get(), componentTypeIn, boxIn);
			this.setOrientation(facingIn);
		}

		public BridgeStraight3(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_STRAIGHT3.get(), compoundIn);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChildForward((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 1, 3, false);
		}

		public static final InfernalNetherFortressPieces.BridgeStraight3 createPiece(final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int componentTypeIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -1, -3, 0, 5, 10, 19, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new InfernalNetherFortressPieces.BridgeStraight3(componentTypeIn, randomIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
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
			final BlockState slab2 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM);
			//air
			this.generateAirBox(levelIn, boxIn, 1, 5, 0, 3, 9, 18);
			//top
			this.generateBox(levelIn, boxIn, 0, 4, 0, 1, 4, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 2, 4, 0, 2, 4, 18, block2, block2, false);
			this.generateBox(levelIn, boxIn, 3, 4, 0, 4, 4, 18, block1, block1, false);
			//rails
			this.generateBox(levelIn, boxIn, 0, 5, 0, 0, 5, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 6, 3, 0, 6, 3, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 0, 6, 4, 0, 6, 4, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 0, 6, 8, 0, 6, 8, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 0, 6, 9, 0, 6, 9, slab1, slab1, false);
			this.generateBox(levelIn, boxIn, 0, 6, 10, 0, 6, 10, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 0, 6, 14, 0, 6, 14, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 0, 6, 15, 0, 6, 15, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 4, 5, 0, 4, 5, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 4, 6, 3, 4, 6, 3, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 4, 6, 4, 4, 6, 4, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 4, 6, 8, 4, 6, 8, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 4, 6, 9, 4, 6, 9, slab1, slab1, false);
			this.generateBox(levelIn, boxIn, 4, 6, 10, 4, 6, 10, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 4, 6, 14, 4, 6, 14, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 4, 6, 15, 4, 6, 15, stair4, stair4, false);
			//top structure
			this.placeBlock(levelIn, block4, 0, 7, 9, boxIn);
			this.placeBlock(levelIn, block4, 4, 7, 9, boxIn);
			this.placeBlock(levelIn, block3, 0, 8, 9, boxIn);
			this.placeBlock(levelIn, block3, 4, 8, 9, boxIn);
			this.placeBlock(levelIn, block4, 0, 9, 9, boxIn);
			this.placeBlock(levelIn, block4, 4, 9, 9, boxIn);
			this.placeBlock(levelIn, slab2, 0, 10, 9, boxIn);
			this.placeBlock(levelIn, slab2, 4, 10, 9, boxIn);
			this.placeBlock(levelIn, block1, 1, 10, 9, boxIn);
			this.placeBlock(levelIn, block2, 2, 10, 9, boxIn);
			this.placeBlock(levelIn, block1, 3, 10, 9, boxIn);
			//bottom
			this.generateBox(levelIn, boxIn, 0, 3, 0, 4, 3, 18, block1, block1, false);
			//pillars
			this.generateBox(levelIn, boxIn, 0, 2, 0, 4, 2, 0, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 0, 2, 4, 4, 2, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 2, 18, 4, 2, 18, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 0, 2, 14, 4, 2, 14, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 2, 3, 4, 2, 3, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 0, 2, 5, 4, 2, 5, stair2, stair2, false);
			this.generateBox(levelIn, boxIn, 0, 2, 15, 4, 2, 15, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 0, 2, 13, 4, 2, 13, stair1, stair1, false);
			this.generateBox(levelIn, boxIn, 0, 1, 1, 4, 1, 1, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 0, 1, 2, 4, 1, 2, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 0, 0, 1, 4, 0, 2, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 1, 0, 4, 1, 0, stair1, stair1, false);
			this.generateBox(levelIn, boxIn, 0, 1, 18, 4, 1, 18, stair2, stair2, false);
			this.generateBox(levelIn, boxIn, 0, 1, 16, 4, 1, 16, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 0, 1, 17, 4, 1, 17, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 0, 0, 16, 4, 0, 17, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 1, 3, 4, 1, 3, stair2, stair2, false);
			this.generateBox(levelIn, boxIn, 0, 1, 15, 4, 1, 15, stair1, stair1, false);
			for (int w1 = 0; w1 <= 4; ++w1) {
				for (int l1 = 1; l1 <= 2; ++l1) {
					this.fillColumnDown(levelIn, block1, w1, -1, l1, boxIn);
					this.fillColumnDown(levelIn, block1, w1, -1, 18 - l1, boxIn);
				}
			}
			//decoration
			final int chainLength = randomIn.nextInt(2);
			this.placeBlock(levelIn, block4, 0, 2, 9, boxIn);
			this.placeBlock(levelIn, block3, 0, 1, 9, boxIn);
			for (int length = 0; length < chainLength; length++)
				this.placeBlock(levelIn, block6, 0, 0 - length, 9, boxIn);
			this.placeBlock(levelIn, block5, 0, 0 - chainLength, 9, boxIn);
			this.placeBlock(levelIn, block4, 4, 2, 9, boxIn);
			this.placeBlock(levelIn, block3, 4, 1, 9, boxIn);
			for (int length = 0; length < chainLength; length++)
				this.placeBlock(levelIn, block6, 4, 0 - length, 9, boxIn);
			this.placeBlock(levelIn, block5, 4, 0 - chainLength, 9, boxIn);
			this.placeBlock(levelIn, block3, 0, 2, 7, boxIn);
			for (int length = 0; length < chainLength; length++)
				this.placeBlock(levelIn, block6, 0, 1 - length, 7, boxIn);
			this.placeBlock(levelIn, block5, 0, 1 - chainLength, 7, boxIn);
			this.placeBlock(levelIn, block3, 4, 2, 7, boxIn);
			for (int length = 0; length < chainLength; length++)
				this.placeBlock(levelIn, block6, 4, 1 - length, 7, boxIn);
			this.placeBlock(levelIn, block5, 4, 1 - chainLength, 7, boxIn);
			this.placeBlock(levelIn, block3, 0, 2, 11, boxIn);
			for (int length = 0; length < chainLength; length++)
				this.placeBlock(levelIn, block6, 0, 1 - length, 11, boxIn);
			this.placeBlock(levelIn, block5, 0, 1 - chainLength, 11, boxIn);
			this.placeBlock(levelIn, block3, 4, 2, 11, boxIn);
			for (int length = 0; length < chainLength; length++)
				this.placeBlock(levelIn, block6, 4, 1 - length, 11, boxIn);
			this.placeBlock(levelIn, block5, 4, 1 - chainLength, 11, boxIn);
		}
	}

	public static class BridgeCrossing1 extends InfernalNetherFortressPieces.InfernalNetherBridgePiece {
		public BridgeCrossing1(final int componentTypeIn, final BoundingBox boxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_CROSSING1.get(), componentTypeIn, boxIn);
			this.setOrientation(facingIn);
		}

		protected BridgeCrossing1(final int xIn, final int zIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_CROSSING1.get(), 0, StructurePiece.makeBoundingBox(xIn, 64, zIn, facingIn, 19, 10, 19));
			this.setOrientation(facingIn);
		}

		protected BridgeCrossing1(final StructurePieceType pieceTypeIn, final CompoundTag compoundIn) {
			super(pieceTypeIn, compoundIn);
		}

		public BridgeCrossing1(final CompoundTag compoundIn) {
			this(BWStructurePieceTypes.INF_BRIDGE_CROSSING1.get(), compoundIn);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChildForward((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 8, 3, false);
			this.generateChildLeft((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 3, 8, false);
			this.generateChildRight((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 3, 8, false);
		}

		public static final InfernalNetherFortressPieces.BridgeCrossing1 createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int componentTypeIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -8, -3, 0, 19, 10, 19, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new InfernalNetherFortressPieces.BridgeCrossing1(componentTypeIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
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
			this.generateAirBox(levelIn, boxIn, 8, 5, 0, 10, 9, 18);
			this.generateAirBox(levelIn, boxIn, 0, 5, 8, 18, 9, 10);
			//top
			this.generateBox(levelIn, boxIn, 7, 4, 0, 8, 4, 7, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 4, 11, 8, 4, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 9, 4, 0, 9, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boxIn, 9, 4, 10, 9, 4, 18, block2, block2, false);
			this.generateBox(levelIn, boxIn, 10, 4, 0, 11, 4, 7, block1, block1, false);
			this.generateBox(levelIn, boxIn, 10, 4, 11, 11, 4, 18, block1, block1, false);
			//center
			this.placeBlock(levelIn, block1, 9, 4, 9, boxIn);
			this.placeBlock(levelIn, block2, 8, 4, 8, boxIn);
			this.placeBlock(levelIn, block2, 8, 4, 10, boxIn);
			this.placeBlock(levelIn, block2, 10, 4, 8, boxIn);
			this.placeBlock(levelIn, block2, 10, 4, 10, boxIn);
			//top2
			this.generateBox(levelIn, boxIn, 0, 4, 7, 7, 4, 8, block1, block1, false);
			this.generateBox(levelIn, boxIn, 11, 4, 7, 18, 4, 8, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 4, 9, 8, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boxIn, 10, 4, 9, 18, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boxIn, 0, 4, 10, 7, 4, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 11, 4, 10, 18, 4, 11, block1, block1, false);
			//bottom
			this.generateBox(levelIn, boxIn, 7, 3, 0, 11, 3, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 3, 7, 18, 3, 11, block1, block1, false);
			//rails
			this.generateBox(levelIn, boxIn, 7, 5, 0, 7, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 5, 11, 7, 5, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 11, 5, 0, 11, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boxIn, 11, 5, 11, 11, 5, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 5, 7, 7, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boxIn, 11, 5, 7, 18, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 5, 11, 7, 5, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 11, 5, 11, 18, 5, 11, block1, block1, false);
			//pillars z-Axis
			this.generateBox(levelIn, boxIn, 7, 2, 0, 11, 2, 0, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 7, 2, 4, 11, 2, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 2, 18, 11, 2, 18, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 7, 2, 14, 11, 2, 14, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 2, 3, 11, 2, 3, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 7, 2, 5, 11, 2, 5, stair2, stair2, false);
			this.generateBox(levelIn, boxIn, 7, 2, 15, 11, 2, 15, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 7, 2, 13, 11, 2, 13, stair1, stair1, false);
			this.generateBox(levelIn, boxIn, 7, 1, 1, 11, 1, 1, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 7, 1, 2, 11, 1, 2, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 7, 0, 1, 11, 0, 2, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 1, 0, 11, 1, 0, stair1, stair1, false);
			this.generateBox(levelIn, boxIn, 7, 1, 18, 11, 1, 18, stair2, stair2, false);
			this.generateBox(levelIn, boxIn, 7, 1, 16, 11, 1, 16, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 7, 1, 17, 11, 1, 17, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 7, 0, 16, 11, 0, 17, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 1, 3, 11, 1, 3, stair2, stair2, false);
			this.generateBox(levelIn, boxIn, 7, 1, 15, 11, 1, 15, stair1, stair1, false);
			for (int w1 = 7; w1 <= 11; ++w1) {
				for (int l1 = 1; l1 <= 2; ++l1) {
					this.fillColumnDown(levelIn, block1, w1, -1, l1, boxIn);
					this.fillColumnDown(levelIn, block1, w1, -1, 18 - l1, boxIn);
				}
			}
			//pillars x-axis
			this.generateBox(levelIn, boxIn, 0, 2, 7, 0, 2, 11, stair8, stair8, false);
			this.generateBox(levelIn, boxIn, 4, 2, 7, 4, 2, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 18, 2, 7, 18, 2, 11, stair7, stair7, false);
			this.generateBox(levelIn, boxIn, 14, 2, 7, 14, 2, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 3, 2, 7, 3, 2, 11, stair7, stair7, false);
			this.generateBox(levelIn, boxIn, 5, 2, 7, 5, 2, 11, stair6, stair6, false);
			this.generateBox(levelIn, boxIn, 15, 2, 7, 15, 2, 11, stair8, stair8, false);
			this.generateBox(levelIn, boxIn, 13, 2, 7, 13, 2, 11, stair5, stair5, false);
			this.generateBox(levelIn, boxIn, 1, 1, 7, 1, 1, 11, stair8, stair8, false);
			this.generateBox(levelIn, boxIn, 2, 1, 7, 2, 1, 11, stair7, stair7, false);
			this.generateBox(levelIn, boxIn, 1, 0, 7, 2, 0, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 1, 7, 0, 1, 11, stair5, stair5, false);
			this.generateBox(levelIn, boxIn, 18, 1, 7, 18, 1, 11, stair6, stair6, false);
			this.generateBox(levelIn, boxIn, 16, 1, 7, 16, 1, 11, stair8, stair8, false);
			this.generateBox(levelIn, boxIn, 17, 1, 7, 17, 1, 11, stair7, stair7, false);
			this.generateBox(levelIn, boxIn, 16, 0, 7, 17, 0, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 3, 1, 7, 3, 1, 11, stair6, stair6, false);
			this.generateBox(levelIn, boxIn, 15, 1, 7, 15, 1, 11, stair5, stair5, false);
			for (int w2 = 1; w2 <= 2; ++w2) {
				for (int l2 = 7; l2 <= 11; ++l2) {
					this.fillColumnDown(levelIn, block1, w2, -1, l2, boxIn);
					this.fillColumnDown(levelIn, block1, 18 - w2, -1, l2, boxIn);
				}
			}
		}
	}

	public static class BridgeCrossing2 extends InfernalNetherFortressPieces.InfernalNetherBridgePiece {
		public BridgeCrossing2(final int componentTypeIn, final BoundingBox boxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_CROSSING2.get(), componentTypeIn, boxIn);
			this.setOrientation(facingIn);
		}

		protected BridgeCrossing2(final int xIn, final int zIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_CROSSING2.get(), 0, StructurePiece.makeBoundingBox(xIn, 64, zIn, facingIn, 19, 10, 19));
			this.setOrientation(facingIn);
		}

		protected BridgeCrossing2(final StructurePieceType pieceTypeIn, final CompoundTag compoundIn) {
			super(pieceTypeIn, compoundIn);
		}

		public BridgeCrossing2(final CompoundTag compoundIn) {
			this(BWStructurePieceTypes.INF_BRIDGE_CROSSING2.get(), compoundIn);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChildForward((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 8, 3, false);
			this.generateChildLeft((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 3, 8, false);
			this.generateChildRight((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 3, 8, false);
		}

		public static final InfernalNetherFortressPieces.BridgeCrossing2 createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int componentTypeIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -8, -3, 0, 19, 10, 19, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new InfernalNetherFortressPieces.BridgeCrossing2(componentTypeIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = Blocks.RED_NETHER_BRICK_WALL.defaultBlockState();
			final BlockState block4 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState();
			final BlockState block5 = BWBlocks.BLAZE_LANTERN.get().defaultBlockState().setValue(BlazeLanternBlock.HANGING, Boolean.valueOf(true));
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
			this.generateAirBox(levelIn, boxIn, 8, 5, 0, 10, 9, 18);
			this.generateAirBox(levelIn, boxIn, 0, 5, 8, 18, 9, 10);
			//top
			this.generateBox(levelIn, boxIn, 7, 4, 0, 8, 4, 7, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 4, 11, 8, 4, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 9, 4, 0, 9, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boxIn, 9, 4, 10, 9, 4, 18, block2, block2, false);
			this.generateBox(levelIn, boxIn, 10, 4, 0, 11, 4, 7, block1, block1, false);
			this.generateBox(levelIn, boxIn, 10, 4, 11, 11, 4, 18, block1, block1, false);
			//center
			this.placeBlock(levelIn, block1, 9, 4, 9, boxIn);
			this.placeBlock(levelIn, block2, 8, 4, 8, boxIn);
			this.placeBlock(levelIn, block2, 8, 4, 10, boxIn);
			this.placeBlock(levelIn, block2, 10, 4, 8, boxIn);
			this.placeBlock(levelIn, block2, 10, 4, 10, boxIn);
			this.placeBlock(levelIn, block4, 9, 8, 9, boxIn);
			this.placeBlock(levelIn, block5, 9, 7, 9, boxIn);
			this.placeBlock(levelIn, block3, 9, 9, 9, boxIn);
			//top center
			this.placeBlock(levelIn, block1, 9, 10, 9, boxIn);
			this.placeBlock(levelIn, block2, 8, 10, 8, boxIn);
			this.placeBlock(levelIn, block2, 8, 10, 10, boxIn);
			this.placeBlock(levelIn, block2, 10, 10, 8, boxIn);
			this.placeBlock(levelIn, block2, 10, 10, 10, boxIn);
			this.placeBlock(levelIn, block2, 9, 10, 8, boxIn);
			this.placeBlock(levelIn, block2, 8, 10, 9, boxIn);
			this.placeBlock(levelIn, block2, 10, 10, 9, boxIn);
			this.placeBlock(levelIn, block2, 9, 10, 10, boxIn);
			this.placeBlock(levelIn, block1, 7, 9, 7, boxIn);
			this.placeBlock(levelIn, block3, 7, 8, 7, boxIn);
			this.placeBlock(levelIn, block4, 7, 7, 7, boxIn);
			this.placeBlock(levelIn, block3, 7, 6, 7, boxIn);
			this.placeBlock(levelIn, block1, 7, 9, 11, boxIn);
			this.placeBlock(levelIn, block3, 7, 8, 11, boxIn);
			this.placeBlock(levelIn, block4, 7, 7, 11, boxIn);
			this.placeBlock(levelIn, block3, 7, 6, 11, boxIn);
			this.placeBlock(levelIn, block1, 11, 9, 11, boxIn);
			this.placeBlock(levelIn, block3, 11, 8, 11, boxIn);
			this.placeBlock(levelIn, block4, 11, 7, 11, boxIn);
			this.placeBlock(levelIn, block3, 11, 6, 11, boxIn);
			this.placeBlock(levelIn, block1, 11, 9, 7, boxIn);
			this.placeBlock(levelIn, block3, 11, 8, 7, boxIn);
			this.placeBlock(levelIn, block4, 11, 7, 7, boxIn);
			this.placeBlock(levelIn, block3, 11, 6, 7, boxIn);
			//top center stairs & slabs
			this.placeBlock(levelIn, stair9, 9, 10, 7, boxIn);
			this.placeBlock(levelIn, stair3, 8, 10, 7, boxIn);
			this.placeBlock(levelIn, stair3, 10, 10, 7, boxIn);
			this.placeBlock(levelIn, stair10, 9, 10, 11, boxIn);
			this.placeBlock(levelIn, stair4, 8, 10, 11, boxIn);
			this.placeBlock(levelIn, stair4, 10, 10, 11, boxIn);
			this.placeBlock(levelIn, stair11, 7, 10, 9, boxIn);
			this.placeBlock(levelIn, stair7, 7, 10, 8, boxIn);
			this.placeBlock(levelIn, stair7, 7, 10, 10, boxIn);
			this.placeBlock(levelIn, stair12, 11, 10, 9, boxIn);
			this.placeBlock(levelIn, stair8, 11, 10, 8, boxIn);
			this.placeBlock(levelIn, stair8, 11, 10, 10, boxIn);
			this.placeBlock(levelIn, slab1, 7, 10, 7, boxIn);
			this.placeBlock(levelIn, slab1, 7, 10, 11, boxIn);
			this.placeBlock(levelIn, slab1, 11, 10, 11, boxIn);
			this.placeBlock(levelIn, slab1, 11, 10, 7, boxIn);
			this.placeBlock(levelIn, slab2, 8, 9, 7, boxIn);
			this.placeBlock(levelIn, slab3, 9, 9, 7, boxIn);
			this.placeBlock(levelIn, slab2, 10, 9, 7, boxIn);
			this.placeBlock(levelIn, slab2, 8, 9, 11, boxIn);
			this.placeBlock(levelIn, slab3, 9, 9, 11, boxIn);
			this.placeBlock(levelIn, slab2, 10, 9, 11, boxIn);
			this.placeBlock(levelIn, slab2, 7, 9, 8, boxIn);
			this.placeBlock(levelIn, slab3, 7, 9, 9, boxIn);
			this.placeBlock(levelIn, slab2, 7, 9, 10, boxIn);
			this.placeBlock(levelIn, slab2, 11, 9, 8, boxIn);
			this.placeBlock(levelIn, slab3, 11, 9, 9, boxIn);
			this.placeBlock(levelIn, slab2, 11, 9, 10, boxIn);
			//top2
			this.generateBox(levelIn, boxIn, 0, 4, 7, 7, 4, 8, block1, block1, false);
			this.generateBox(levelIn, boxIn, 11, 4, 7, 18, 4, 8, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 4, 9, 8, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boxIn, 10, 4, 9, 18, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boxIn, 0, 4, 10, 7, 4, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 11, 4, 10, 18, 4, 11, block1, block1, false);
			//bottom
			this.generateBox(levelIn, boxIn, 7, 3, 0, 11, 3, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 3, 7, 18, 3, 11, block1, block1, false);
			//rails
			this.generateBox(levelIn, boxIn, 7, 5, 0, 7, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 5, 11, 7, 5, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 11, 5, 0, 11, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boxIn, 11, 5, 11, 11, 5, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 5, 7, 7, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boxIn, 11, 5, 7, 18, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 5, 11, 7, 5, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 11, 5, 11, 18, 5, 11, block1, block1, false);
			//pillars z-Axis
			this.generateBox(levelIn, boxIn, 7, 2, 0, 11, 2, 0, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 7, 2, 4, 11, 2, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 2, 18, 11, 2, 18, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 7, 2, 14, 11, 2, 14, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 2, 3, 11, 2, 3, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 7, 2, 5, 11, 2, 5, stair2, stair2, false);
			this.generateBox(levelIn, boxIn, 7, 2, 15, 11, 2, 15, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 7, 2, 13, 11, 2, 13, stair1, stair1, false);
			this.generateBox(levelIn, boxIn, 7, 1, 1, 11, 1, 1, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 7, 1, 2, 11, 1, 2, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 7, 0, 1, 11, 0, 2, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 1, 0, 11, 1, 0, stair1, stair1, false);
			this.generateBox(levelIn, boxIn, 7, 1, 18, 11, 1, 18, stair2, stair2, false);
			this.generateBox(levelIn, boxIn, 7, 1, 16, 11, 1, 16, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 7, 1, 17, 11, 1, 17, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 7, 0, 16, 11, 0, 17, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 1, 3, 11, 1, 3, stair2, stair2, false);
			this.generateBox(levelIn, boxIn, 7, 1, 15, 11, 1, 15, stair1, stair1, false);
			for (int w1 = 7; w1 <= 11; ++w1) {
				for (int l1 = 1; l1 <= 2; ++l1) {
					this.fillColumnDown(levelIn, block1, w1, -1, l1, boxIn);
					this.fillColumnDown(levelIn, block1, w1, -1, 18 - l1, boxIn);
				}
			}
			//pillars x-axis
			this.generateBox(levelIn, boxIn, 0, 2, 7, 0, 2, 11, stair8, stair8, false);
			this.generateBox(levelIn, boxIn, 4, 2, 7, 4, 2, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 18, 2, 7, 18, 2, 11, stair7, stair7, false);
			this.generateBox(levelIn, boxIn, 14, 2, 7, 14, 2, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 3, 2, 7, 3, 2, 11, stair7, stair7, false);
			this.generateBox(levelIn, boxIn, 5, 2, 7, 5, 2, 11, stair6, stair6, false);
			this.generateBox(levelIn, boxIn, 15, 2, 7, 15, 2, 11, stair8, stair8, false);
			this.generateBox(levelIn, boxIn, 13, 2, 7, 13, 2, 11, stair5, stair5, false);
			this.generateBox(levelIn, boxIn, 1, 1, 7, 1, 1, 11, stair8, stair8, false);
			this.generateBox(levelIn, boxIn, 2, 1, 7, 2, 1, 11, stair7, stair7, false);
			this.generateBox(levelIn, boxIn, 1, 0, 7, 2, 0, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 1, 7, 0, 1, 11, stair5, stair5, false);
			this.generateBox(levelIn, boxIn, 18, 1, 7, 18, 1, 11, stair6, stair6, false);
			this.generateBox(levelIn, boxIn, 16, 1, 7, 16, 1, 11, stair8, stair8, false);
			this.generateBox(levelIn, boxIn, 17, 1, 7, 17, 1, 11, stair7, stair7, false);
			this.generateBox(levelIn, boxIn, 16, 0, 7, 17, 0, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 3, 1, 7, 3, 1, 11, stair6, stair6, false);
			this.generateBox(levelIn, boxIn, 15, 1, 7, 15, 1, 11, stair5, stair5, false);
			for (int w2 = 1; w2 <= 2; ++w2) {
				for (int l2 = 7; l2 <= 11; ++l2) {
					this.fillColumnDown(levelIn, block1, w2, -1, l2, boxIn);
					this.fillColumnDown(levelIn, block1, 18 - w2, -1, l2, boxIn);
				}
			}
		}
	}

	public static class BridgeCrossing3 extends InfernalNetherFortressPieces.InfernalNetherBridgePiece {
		public BridgeCrossing3(final int componentTypeIn, final BoundingBox boxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_CROSSING3.get(), componentTypeIn, boxIn);
			this.setOrientation(facingIn);
		}

		protected BridgeCrossing3(final int xIn, final int zIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_BRIDGE_CROSSING3.get(), 0, StructurePiece.makeBoundingBox(xIn, 64, zIn, facingIn, 19, 10, 19));
			this.setOrientation(facingIn);
		}

		protected BridgeCrossing3(final StructurePieceType pieceTypeIn, final CompoundTag compoundIn) {
			super(pieceTypeIn, compoundIn);
		}

		public BridgeCrossing3(final CompoundTag compoundIn) {
			this(BWStructurePieceTypes.INF_BRIDGE_CROSSING3.get(), compoundIn);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChildForward((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 8, 3, false);
			this.generateChildLeft((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 3, 8, false);
			this.generateChildRight((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 3, 8, false);
		}

		public static final InfernalNetherFortressPieces.BridgeCrossing3 createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int componentTypeIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -8, -3, 0, 19, 10, 19, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new InfernalNetherFortressPieces.BridgeCrossing3(componentTypeIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
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
			this.generateAirBox(levelIn, boxIn, 8, 5, 0, 10, 9, 18);
			this.generateAirBox(levelIn, boxIn, 0, 5, 8, 18, 9, 10);
			this.generateAirBox(levelIn, boxIn, 6, 5, 5, 12, 9, 13);
			this.generateAirBox(levelIn, boxIn, 5, 5, 6, 13, 9, 12);
			//top
			this.generateBox(levelIn, boxIn, 7, 4, 0, 8, 4, 5, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 4, 6, 8, 4, 6, block2, block2, false);
			this.generateBox(levelIn, boxIn, 7, 4, 13, 8, 4, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 4, 12, 8, 4, 12, block2, block2, false);
			this.generateBox(levelIn, boxIn, 9, 4, 0, 9, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boxIn, 9, 4, 10, 9, 4, 18, block2, block2, false);
			this.generateBox(levelIn, boxIn, 10, 4, 0, 11, 4, 5, block1, block1, false);
			this.generateBox(levelIn, boxIn, 10, 4, 6, 11, 4, 6, block2, block2, false);
			this.generateBox(levelIn, boxIn, 10, 4, 13, 11, 4, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 10, 4, 12, 11, 4, 12, block2, block2, false);
			//center
			this.placeBlock(levelIn, block1, 9, 4, 9, boxIn);
			this.placeBlock(levelIn, block3, 9, 5, 9, boxIn);
			this.placeBlock(levelIn, block4, 9, 6, 9, boxIn);
			this.placeBlock(levelIn, block5, 9, 7, 9, boxIn);
			this.placeBlock(levelIn, block2, 8, 4, 8, boxIn);
			this.placeBlock(levelIn, block2, 8, 4, 10, boxIn);
			this.placeBlock(levelIn, block2, 10, 4, 8, boxIn);
			this.placeBlock(levelIn, block2, 10, 4, 10, boxIn);
			this.placeBlock(levelIn, block1, 7, 4, 7, boxIn);
			this.placeBlock(levelIn, block1, 7, 4, 8, boxIn);
			this.placeBlock(levelIn, block1, 8, 4, 7, boxIn);
			this.placeBlock(levelIn, block1, 7, 4, 11, boxIn);
			this.placeBlock(levelIn, block1, 7, 4, 10, boxIn);
			this.placeBlock(levelIn, block1, 8, 4, 11, boxIn);
			this.placeBlock(levelIn, block1, 11, 4, 7, boxIn);
			this.placeBlock(levelIn, block1, 10, 4, 7, boxIn);
			this.placeBlock(levelIn, block1, 11, 4, 8, boxIn);
			this.placeBlock(levelIn, block1, 11, 4, 11, boxIn);
			this.placeBlock(levelIn, block1, 10, 4, 11, boxIn);
			this.placeBlock(levelIn, block1, 11, 4, 10, boxIn);
			//center corners
			this.generateBox(levelIn, boxIn, 6, 3, 6, 6, 4, 6, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 3, 5, 6, 4, 5, block1, block1, false);
			this.generateBox(levelIn, boxIn, 5, 3, 6, 5, 4, 6, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 3, 12, 6, 4, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 3, 13, 6, 4, 13, block1, block1, false);
			this.generateBox(levelIn, boxIn, 5, 3, 12, 5, 4, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 12, 3, 6, 12, 4, 6, block1, block1, false);
			this.generateBox(levelIn, boxIn, 13, 3, 6, 13, 4, 6, block1, block1, false);
			this.generateBox(levelIn, boxIn, 12, 3, 5, 12, 4, 5, block1, block1, false);
			this.generateBox(levelIn, boxIn, 12, 3, 12, 12, 4, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 12, 3, 13, 12, 4, 13, block1, block1, false);
			this.generateBox(levelIn, boxIn, 13, 3, 12, 13, 4, 12, block1, block1, false);
			//center rails
			this.generateBox(levelIn, boxIn, 5, 4, 5, 5, 5, 5, block1, block1, false);
			this.generateBox(levelIn, boxIn, 4, 4, 6, 4, 5, 6, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 4, 4, 6, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 5, 4, 13, 5, 5, 13, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 4, 14, 6, 5, 14, block1, block1, false);
			this.generateBox(levelIn, boxIn, 4, 4, 12, 4, 5, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 13, 4, 5, 13, 5, 5, block1, block1, false);
			this.generateBox(levelIn, boxIn, 14, 4, 6, 14, 5, 6, block1, block1, false);
			this.generateBox(levelIn, boxIn, 12, 4, 4, 12, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 13, 4, 13, 13, 5, 13, block1, block1, false);
			this.generateBox(levelIn, boxIn, 14, 4, 12, 14, 5, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 12, 4, 14, 12, 5, 14, block1, block1, false);
			//top2
			this.generateBox(levelIn, boxIn, 0, 4, 7, 5, 4, 8, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 4, 7, 6, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boxIn, 13, 4, 7, 18, 4, 8, block1, block1, false);
			this.generateBox(levelIn, boxIn, 12, 4, 7, 12, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boxIn, 0, 4, 9, 8, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boxIn, 10, 4, 9, 18, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boxIn, 0, 4, 10, 5, 4, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 4, 10, 6, 4, 11, block2, block2, false);
			this.generateBox(levelIn, boxIn, 13, 4, 10, 18, 4, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 12, 4, 10, 12, 4, 11, block2, block2, false);
			//bottom
			this.generateBox(levelIn, boxIn, 7, 3, 0, 11, 3, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 3, 7, 18, 3, 11, block1, block1, false);
			//rails
			this.generateBox(levelIn, boxIn, 7, 5, 0, 7, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 5, 14, 7, 5, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 11, 5, 0, 11, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 11, 5, 14, 11, 5, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 5, 7, 4, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boxIn, 14, 5, 7, 18, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 5, 11, 4, 5, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 14, 5, 11, 18, 5, 11, block1, block1, false);
			//pillars z-Axis
			this.generateBox(levelIn, boxIn, 7, 2, 0, 11, 2, 0, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 7, 2, 4, 11, 2, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 2, 18, 11, 2, 18, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 7, 2, 14, 11, 2, 14, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 2, 3, 11, 2, 3, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 7, 2, 5, 11, 2, 5, stair2, stair2, false);
			this.generateBox(levelIn, boxIn, 7, 2, 15, 11, 2, 15, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 7, 2, 13, 11, 2, 13, stair1, stair1, false);
			this.generateBox(levelIn, boxIn, 7, 1, 1, 11, 1, 1, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 7, 1, 2, 11, 1, 2, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 7, 0, 1, 11, 0, 2, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 1, 0, 11, 1, 0, stair1, stair1, false);
			this.generateBox(levelIn, boxIn, 7, 1, 18, 11, 1, 18, stair2, stair2, false);
			this.generateBox(levelIn, boxIn, 7, 1, 16, 11, 1, 16, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 7, 1, 17, 11, 1, 17, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 7, 0, 16, 11, 0, 17, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 1, 3, 11, 1, 3, stair2, stair2, false);
			this.generateBox(levelIn, boxIn, 7, 1, 15, 11, 1, 15, stair1, stair1, false);
			for (int w1 = 7; w1 <= 11; ++w1) {
				for (int l1 = 1; l1 <= 2; ++l1) {
					this.fillColumnDown(levelIn, block1, w1, -1, l1, boxIn);
					this.fillColumnDown(levelIn, block1, w1, -1, 18 - l1, boxIn);
				}
			}
			//pillars x-axis
			this.generateBox(levelIn, boxIn, 0, 2, 7, 0, 2, 11, stair8, stair8, false);
			this.generateBox(levelIn, boxIn, 4, 2, 7, 4, 2, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 18, 2, 7, 18, 2, 11, stair7, stair7, false);
			this.generateBox(levelIn, boxIn, 14, 2, 7, 14, 2, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 3, 2, 7, 3, 2, 11, stair7, stair7, false);
			this.generateBox(levelIn, boxIn, 5, 2, 7, 5, 2, 11, stair6, stair6, false);
			this.generateBox(levelIn, boxIn, 15, 2, 7, 15, 2, 11, stair8, stair8, false);
			this.generateBox(levelIn, boxIn, 13, 2, 7, 13, 2, 11, stair5, stair5, false);
			this.generateBox(levelIn, boxIn, 1, 1, 7, 1, 1, 11, stair8, stair8, false);
			this.generateBox(levelIn, boxIn, 2, 1, 7, 2, 1, 11, stair7, stair7, false);
			this.generateBox(levelIn, boxIn, 1, 0, 7, 2, 0, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 1, 7, 0, 1, 11, stair5, stair5, false);
			this.generateBox(levelIn, boxIn, 18, 1, 7, 18, 1, 11, stair6, stair6, false);
			this.generateBox(levelIn, boxIn, 16, 1, 7, 16, 1, 11, stair8, stair8, false);
			this.generateBox(levelIn, boxIn, 17, 1, 7, 17, 1, 11, stair7, stair7, false);
			this.generateBox(levelIn, boxIn, 16, 0, 7, 17, 0, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 3, 1, 7, 3, 1, 11, stair6, stair6, false);
			this.generateBox(levelIn, boxIn, 15, 1, 7, 15, 1, 11, stair5, stair5, false);
			for (int w2 = 1; w2 <= 2; ++w2) {
				for (int l2 = 7; l2 <= 11; ++l2) {
					this.fillColumnDown(levelIn, block1, w2, -1, l2, boxIn);
					this.fillColumnDown(levelIn, block1, 18 - w2, -1, l2, boxIn);
				}
			}
		}
	}

	public static class RoomCrossing extends InfernalNetherFortressPieces.InfernalNetherBridgePiece {
		public RoomCrossing(final int componentTypeIn, final BoundingBox boxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_ROOM_CROSSING.get(), componentTypeIn, boxIn);
			this.setOrientation(facingIn);
		}

		protected RoomCrossing(final int xIn, final int zIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_ROOM_CROSSING.get(), 0, StructurePiece.makeBoundingBox(xIn, 64, zIn, facingIn, 7, 9, 7));
			this.setOrientation(facingIn);
		}

		protected RoomCrossing(final StructurePieceType pieceTypeIn, final CompoundTag compoundIn) {
			super(pieceTypeIn, compoundIn);
		}

		public RoomCrossing(final CompoundTag compoundIn) {
			this(BWStructurePieceTypes.INF_ROOM_CROSSING.get(), compoundIn);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChildForward((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 2, 0, false);
			this.generateChildLeft((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 0, 2, false);
			this.generateChildRight((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 0, 2, false);
		}

		public static final InfernalNetherFortressPieces.RoomCrossing createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int componentTypeIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -2, 0, 0, 7, 9, 7, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new InfernalNetherFortressPieces.RoomCrossing(componentTypeIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState fence1 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true));
			final BlockState fence2 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence3 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true));
			final BlockState fence4 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);	
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			//air
			this.generateAirBox(levelIn, boxIn, 0, 2, 0, 6, 7, 6);
			//floor
			this.generateBox(levelIn, boxIn, 0, 0, 0, 6, 1, 6, block1, block1, false);
			this.generateBox(levelIn, boxIn, 3, 0, 0, 3, 1, 2, block2, block2, false);
			this.generateBox(levelIn, boxIn, 3, 0, 4, 3, 1, 6, block2, block2, false);
			this.generateBox(levelIn, boxIn, 0, 0, 3, 2, 1, 3, block2, block2, false);
			this.generateBox(levelIn, boxIn, 4, 0, 3, 6, 1, 3, block2, block2, false);
			this.generateBox(levelIn, boxIn, 2, 0, 2, 2, 1, 2, block2, block2, false);
			this.generateBox(levelIn, boxIn, 2, 0, 4, 2, 1, 4, block2, block2, false);
			this.generateBox(levelIn, boxIn, 4, 0, 2, 4, 1, 2, block2, block2, false);
			this.generateBox(levelIn, boxIn, 4, 0, 4, 4, 1, 4, block2, block2, false);
			//walls
			this.generateBox(levelIn, boxIn, 0, 2, 0, 1, 6, 0, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 2, 6, 1, 6, 6, block1, block1, false);
			this.generateBox(levelIn, boxIn, 5, 2, 0, 6, 6, 0, block1, block1, false);
			this.generateBox(levelIn, boxIn, 5, 2, 6, 6, 6, 6, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 2, 0, 0, 6, 1, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 2, 5, 0, 6, 6, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 2, 0, 6, 6, 1, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 2, 5, 6, 6, 6, block1, block1, false);
			//gates
			this.generateBox(levelIn, boxIn, 2, 6, 0, 4, 6, 0, block1, block1, false);
			this.placeBlock(levelIn, block2, 3, 6, 0, boxIn);
			this.generateBox(levelIn, boxIn, 2, 6, 6, 4, 6, 6, block1, block1, false);
			this.placeBlock(levelIn, block2, 3, 6, 6, boxIn);
			this.generateBox(levelIn, boxIn, 0, 6, 2, 0, 6, 4, block1, block1, false);
			this.placeBlock(levelIn, block2, 0, 6, 3, boxIn);
			this.generateBox(levelIn, boxIn, 6, 6, 2, 6, 6, 4, block1, block1, false);
			this.placeBlock(levelIn, block2, 6, 6, 3, boxIn);
			final int[] rand = new int[4];
			if (randomIn.nextInt(2) == 0) {
				rand[0] = randomIn.nextInt(2);
				rand[1] = randomIn.nextInt(2);
				rand[2] = randomIn.nextInt(2);
				rand[3] = randomIn.nextInt(2);
			}
			this.generateBox(levelIn, boxIn, 2, 5 - rand[0], 0, 2, 5, 0, fence3, fence3, false);
			this.generateBox(levelIn, boxIn, 3, 5 - rand[0], 0, 3, 5, 0, fence1, fence1, false);
			this.generateBox(levelIn, boxIn, 4, 5 - rand[0], 0, 4, 5, 0, fence3, fence3, false);
			this.generateBox(levelIn, boxIn, 2, 5 - rand[1], 6, 2, 5, 6, fence3, fence3, false);
			this.generateBox(levelIn, boxIn, 3, 5 - rand[1], 6, 3, 5, 6, fence1, fence1, false);
			this.generateBox(levelIn, boxIn, 4, 5 - rand[1], 6, 4, 5, 6, fence3, fence3, false);
			this.generateBox(levelIn, boxIn, 0, 5 - rand[2], 2, 0, 5, 2, fence4, fence4, false);
			this.generateBox(levelIn, boxIn, 0, 5 - rand[2], 3, 0, 5, 3, fence2, fence2, false);
			this.generateBox(levelIn, boxIn, 0, 5 - rand[2], 4, 0, 5, 4, fence4, fence4, false);
			this.generateBox(levelIn, boxIn, 6, 5 - rand[3], 2, 6, 5, 2, fence4, fence4, false);
			this.generateBox(levelIn, boxIn, 6, 5 - rand[3], 3, 6, 5, 3, fence2, fence2, false);
			this.generateBox(levelIn, boxIn, 6, 5 - rand[3], 4, 6, 5, 4, fence4, fence4, false);
			//bottom
			this.fillColumnDown(levelIn, block1, 0, -1, 0, boxIn);
			this.fillColumnDown(levelIn, block1, 0, -1, 6, boxIn);
			this.fillColumnDown(levelIn, block1, 6, -1, 0, boxIn);
			this.fillColumnDown(levelIn, block1, 6, -1, 6, boxIn);
			this.placeBlock(levelIn, stair1, 5, -1, 0, boxIn);
			this.placeBlock(levelIn, stair1, 5, -1, 6, boxIn);
			this.placeBlock(levelIn, stair2, 1, -1, 0, boxIn);
			this.placeBlock(levelIn, stair2, 1, -1, 6, boxIn);
			this.placeBlock(levelIn, stair3, 0, -1, 5, boxIn);
			this.placeBlock(levelIn, stair3, 6, -1, 5, boxIn);
			this.placeBlock(levelIn, stair4, 0, -1, 1, boxIn);
			this.placeBlock(levelIn, stair4, 6, -1, 1, boxIn);
		}
	}

	public static class BigRoomCrossing extends InfernalNetherFortressPieces.InfernalNetherBridgePiece {
		private boolean hasPlacedSpawner;

		public BigRoomCrossing(final int componentTypeIn, final BoundingBox boxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_BIG_ROOM_CROSSING.get(), componentTypeIn, boxIn);
			this.setOrientation(facingIn);
		}

		protected BigRoomCrossing(final int xIn, final int zIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_BIG_ROOM_CROSSING.get(), 0, StructurePiece.makeBoundingBox(xIn, 64, zIn, facingIn, 19, 10, 19));
			this.setOrientation(facingIn);
		}

		protected BigRoomCrossing(final StructurePieceType pieceTypeIn, final CompoundTag compoundIn) {
			super(pieceTypeIn, compoundIn);
			this.hasPlacedSpawner = compoundIn.getBoolean("Mob");
		}

		public BigRoomCrossing(final CompoundTag compoundIn) {
			this(StructurePieceType.NETHER_FORTRESS_BRIDGE_CROSSING, compoundIn);
			this.hasPlacedSpawner = compoundIn.getBoolean("Mob");
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChildForward((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 8, 3, false);
			this.generateChildLeft((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 3, 8, false);
			this.generateChildRight((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 3, 8, false);
		}

		public static final InfernalNetherFortressPieces.BigRoomCrossing createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int componentTypeIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -8, -3, 0, 19, 10, 19, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new InfernalNetherFortressPieces.BigRoomCrossing(componentTypeIn, box, facingIn) : null;
		}

		@Override
		protected void addAdditionalSaveData(final StructurePieceSerializationContext contextIn, final CompoundTag compoundIn) {
			super.addAdditionalSaveData(contextIn, compoundIn);
			compoundIn.putBoolean("Mob", this.hasPlacedSpawner);
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
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
			this.generateAirBox(levelIn, boxIn, 8, 5, 0, 10, 9, 18);
			this.generateAirBox(levelIn, boxIn, 0, 5, 8, 18, 9, 10);
			this.generateAirBox(levelIn, boxIn, 6, 5, 5, 12, 9, 13);
			this.generateAirBox(levelIn, boxIn, 5, 5, 6, 13, 9, 12);
			//structure
			this.generateBox(levelIn, boxIn, 7, 6, 4, 7, 7, 4, block3, block3, false);
			this.generateBox(levelIn, boxIn, 7, 6, 14, 7, 7, 14, block3, block3, false);
			this.generateBox(levelIn, boxIn, 11, 6, 4, 11, 7, 4, block3, block3, false);
			this.generateBox(levelIn, boxIn, 11, 6, 14, 11, 7, 14, block3, block3, false);
			this.generateBox(levelIn, boxIn, 4, 6, 7, 4, 7, 7, block3, block3, false);
			this.generateBox(levelIn, boxIn, 14, 6, 7, 14, 7, 7, block3, block3, false);
			this.generateBox(levelIn, boxIn, 4, 6, 11, 4, 7, 11, block3, block3, false);
			this.generateBox(levelIn, boxIn, 14, 6, 11, 14, 7, 11, block3, block3, false);
			this.placeBlock(levelIn, block4, 5, 6, 5, boxIn);
			this.placeBlock(levelIn, block4, 5, 6, 13, boxIn);
			this.placeBlock(levelIn, block4, 13, 6, 5, boxIn);
			this.placeBlock(levelIn, block4, 13, 6, 13, boxIn);
			this.placeBlock(levelIn, block5, 5, 7, 5, boxIn);
			this.placeBlock(levelIn, block5, 5, 7, 13, boxIn);
			this.placeBlock(levelIn, block5, 13, 7, 5, boxIn);
			this.placeBlock(levelIn, block5, 13, 7, 13, boxIn);
			//structure top
			this.placeBlock(levelIn, block1, 5, 8, 5, boxIn);
			this.placeBlock(levelIn, block1, 5, 8, 13, boxIn);
			this.placeBlock(levelIn, block1, 13, 8, 5, boxIn);
			this.placeBlock(levelIn, block1, 13, 8, 13, boxIn);
			this.generateBox(levelIn, boxIn, 6, 8, 4, 8, 8, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 8, 14, 8, 8, 14, block1, block1, false);
			this.placeBlock(levelIn, block2, 9, 8, 4, boxIn);
			this.placeBlock(levelIn, block2, 9, 8, 14, boxIn);
			this.generateBox(levelIn, boxIn, 10, 8, 4, 12, 8, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 10, 8, 14, 12, 8, 14, block1, block1, false);
			this.generateBox(levelIn, boxIn, 4, 8, 6, 4, 8, 8, block1, block1, false);
			this.generateBox(levelIn, boxIn, 14, 8, 6, 14, 8, 8, block1, block1, false);
			this.placeBlock(levelIn, block2, 4, 8, 9, boxIn);
			this.placeBlock(levelIn, block2, 14, 8, 9, boxIn);
			this.generateBox(levelIn, boxIn, 4, 8, 10, 4, 8, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 14, 8, 10, 14, 8, 12, block1, block1, false);
			//structure top2
			this.placeBlock(levelIn, slab4, 6, 8, 6, boxIn);
			this.placeBlock(levelIn, slab4, 6, 8, 12, boxIn);
			this.placeBlock(levelIn, slab4, 12, 8, 6, boxIn);
			this.placeBlock(levelIn, slab4, 12, 8, 12, boxIn);
			this.generateBox(levelIn, boxIn, 6, 8, 5, 8, 8, 5, slab4, slab4, false);
			this.generateBox(levelIn, boxIn, 6, 8, 13, 8, 8, 13, slab4, slab4, false);
			this.placeBlock(levelIn, slab2, 9, 8, 5, boxIn);
			this.placeBlock(levelIn, slab2, 9, 8, 13, boxIn);
			this.generateBox(levelIn, boxIn, 10, 8, 5, 12, 8, 5, slab4, slab4, false);
			this.generateBox(levelIn, boxIn, 10, 8, 13, 12, 8, 13, slab4, slab4, false);
			this.generateBox(levelIn, boxIn, 5, 8, 6, 5, 8, 8, slab4, slab4, false);
			this.generateBox(levelIn, boxIn, 13, 8, 6, 13, 8, 8, slab4, slab4, false);
			this.placeBlock(levelIn, slab2, 5, 8, 9, boxIn);
			this.placeBlock(levelIn, slab2, 13, 8, 9, boxIn);
			this.generateBox(levelIn, boxIn, 5, 8, 10, 5, 8, 12, slab4, slab4, false);
			this.generateBox(levelIn, boxIn, 13, 8, 10, 13, 8, 12, slab4, slab4, false);
			this.placeBlock(levelIn, slab3, 6, 9, 6, boxIn);
			this.placeBlock(levelIn, slab3, 6, 9, 12, boxIn);
			this.placeBlock(levelIn, slab3, 12, 9, 6, boxIn);
			this.placeBlock(levelIn, slab3, 12, 9, 12, boxIn);
			this.generateBox(levelIn, boxIn, 6, 9, 5, 8, 9, 5, slab3, slab3, false);
			this.generateBox(levelIn, boxIn, 6, 9, 13, 8, 9, 13, slab3, slab3, false);
			this.placeBlock(levelIn, slab1, 9, 9, 5, boxIn);
			this.placeBlock(levelIn, slab1, 9, 9, 13, boxIn);
			this.generateBox(levelIn, boxIn, 10, 9, 5, 12, 9, 5, slab3, slab3, false);
			this.generateBox(levelIn, boxIn, 10, 9, 13, 12, 9, 13, slab3, slab3, false);
			this.generateBox(levelIn, boxIn, 5, 9, 6, 5, 9, 8, slab3, slab3, false);
			this.generateBox(levelIn, boxIn, 13, 9, 6, 13, 9, 8, slab3, slab3, false);
			this.placeBlock(levelIn, slab1, 5, 9, 9, boxIn);
			this.placeBlock(levelIn, slab1, 13, 9, 9, boxIn);
			this.generateBox(levelIn, boxIn, 5, 9, 10, 5, 9, 12, slab3, slab3, false);
			this.generateBox(levelIn, boxIn, 13, 9, 10, 13, 9, 12, slab3, slab3, false);
			//structure top3
			this.placeBlock(levelIn, block1, 7, 9, 7, boxIn);
			this.placeBlock(levelIn, block1, 7, 9, 11, boxIn);
			this.placeBlock(levelIn, block1, 11, 9, 7, boxIn);
			this.placeBlock(levelIn, block1, 11, 9, 11, boxIn);
			this.placeBlock(levelIn, block2, 9, 9, 6, boxIn);
			this.generateBox(levelIn, boxIn, 7, 9, 6, 8, 9, 6, block2, block2, false);
			this.generateBox(levelIn, boxIn, 10, 9, 6, 11, 9, 6, block2, block2, false);
			this.placeBlock(levelIn, block2, 9, 9, 12, boxIn);
			this.generateBox(levelIn, boxIn, 7, 9, 12, 8, 9, 12, block2, block2, false);
			this.generateBox(levelIn, boxIn, 10, 9, 12, 11, 9, 12, block2, block2, false);
			this.placeBlock(levelIn, block2, 6, 9, 9, boxIn);
			this.generateBox(levelIn, boxIn, 6, 9, 7, 6, 9, 8, block2, block2, false);
			this.generateBox(levelIn, boxIn, 6, 9, 10, 6, 9, 11, block2, block2, false);
			this.placeBlock(levelIn, block2, 12, 9, 9, boxIn);
			this.generateBox(levelIn, boxIn, 12, 9, 7, 12, 9, 8, block2, block2, false);
			this.generateBox(levelIn, boxIn, 12, 9, 10, 12, 9, 11, block2, block2, false);
			//structure top4
			this.placeBlock(levelIn, slab2, 8, 9, 8, boxIn);
			this.placeBlock(levelIn, slab2, 8, 9, 10, boxIn);
			this.placeBlock(levelIn, slab2, 10, 9, 8, boxIn);
			this.placeBlock(levelIn, slab2, 10, 9, 10, boxIn);
			this.placeBlock(levelIn, slab2, 9, 9, 7, boxIn);
			this.placeBlock(levelIn, slab4, 8, 9, 7, boxIn);
			this.placeBlock(levelIn, slab4, 10, 9, 7, boxIn);
			this.placeBlock(levelIn, slab2, 9, 9, 11, boxIn);
			this.placeBlock(levelIn, slab4, 8, 9, 11, boxIn);
			this.placeBlock(levelIn, slab4, 10, 9, 11, boxIn);
			this.placeBlock(levelIn, slab2, 7, 9, 9, boxIn);
			this.placeBlock(levelIn, slab4, 7, 9, 8, boxIn);
			this.placeBlock(levelIn, slab4, 7, 9, 10, boxIn);
			this.placeBlock(levelIn, slab2, 11, 9, 9, boxIn);
			this.placeBlock(levelIn, slab4, 11, 9, 8, boxIn);
			this.placeBlock(levelIn, slab4, 11, 9, 10, boxIn);
			this.placeBlock(levelIn, slab1, 8, 10, 8, boxIn);
			this.placeBlock(levelIn, slab1, 8, 10, 10, boxIn);
			this.placeBlock(levelIn, slab1, 10, 10, 8, boxIn);
			this.placeBlock(levelIn, slab1, 10, 10, 10, boxIn);
			this.placeBlock(levelIn, slab1, 9, 10, 7, boxIn);
			this.placeBlock(levelIn, slab3, 8, 10, 7, boxIn);
			this.placeBlock(levelIn, slab3, 10, 10, 7, boxIn);
			this.placeBlock(levelIn, slab1, 9, 10, 11, boxIn);
			this.placeBlock(levelIn, slab3, 8, 10, 11, boxIn);
			this.placeBlock(levelIn, slab3, 10, 10, 11, boxIn);
			this.placeBlock(levelIn, slab1, 7, 10, 9, boxIn);
			this.placeBlock(levelIn, slab3, 7, 10, 8, boxIn);
			this.placeBlock(levelIn, slab3, 7, 10, 10, boxIn);
			this.placeBlock(levelIn, slab1, 11, 10, 9, boxIn);
			this.placeBlock(levelIn, slab3, 11, 10, 8, boxIn);
			this.placeBlock(levelIn, slab3, 11, 10, 10, boxIn);
			//structure top5
			this.placeBlock(levelIn, block1, 9, 10, 9, boxIn);
			this.placeBlock(levelIn, block2, 8, 10, 9, boxIn);
			this.placeBlock(levelIn, block2, 9, 10, 8, boxIn);
			this.placeBlock(levelIn, block2, 10, 10, 9, boxIn);
			this.placeBlock(levelIn, block2, 9, 10, 10, boxIn);
			//top
			this.generateBox(levelIn, boxIn, 7, 4, 0, 8, 4, 5, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 4, 6, 8, 4, 6, block2, block2, false);
			this.generateBox(levelIn, boxIn, 7, 4, 13, 8, 4, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 4, 12, 8, 4, 12, block2, block2, false);
			this.generateBox(levelIn, boxIn, 9, 4, 0, 9, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boxIn, 9, 4, 10, 9, 4, 18, block2, block2, false);
			this.generateBox(levelIn, boxIn, 10, 4, 0, 11, 4, 5, block1, block1, false);
			this.generateBox(levelIn, boxIn, 10, 4, 6, 11, 4, 6, block2, block2, false);
			this.generateBox(levelIn, boxIn, 10, 4, 13, 11, 4, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 10, 4, 12, 11, 4, 12, block2, block2, false);
			//center
			this.placeBlock(levelIn, stair9, 9, 5, 8, boxIn);
			this.placeBlock(levelIn, stair10, 9, 5, 10, boxIn);
			this.placeBlock(levelIn, stair11, 8, 5, 9, boxIn);
			this.placeBlock(levelIn, stair12, 10, 5, 9, boxIn);
			this.placeBlock(levelIn, slab1, 8, 5, 8, boxIn);
			this.placeBlock(levelIn, slab1, 8, 5, 10, boxIn);
			this.placeBlock(levelIn, slab1, 10, 5, 8, boxIn);
			this.placeBlock(levelIn, slab1, 10, 5, 10, boxIn);
			this.placeBlock(levelIn, block2, 8, 4, 8, boxIn);
			this.placeBlock(levelIn, block2, 8, 4, 10, boxIn);
			this.placeBlock(levelIn, block2, 10, 4, 8, boxIn);
			this.placeBlock(levelIn, block2, 10, 4, 10, boxIn);
			this.placeBlock(levelIn, block1, 7, 4, 7, boxIn);
			this.placeBlock(levelIn, block1, 7, 4, 8, boxIn);
			this.placeBlock(levelIn, block1, 8, 4, 7, boxIn);
			this.placeBlock(levelIn, block1, 7, 4, 11, boxIn);
			this.placeBlock(levelIn, block1, 7, 4, 10, boxIn);
			this.placeBlock(levelIn, block1, 8, 4, 11, boxIn);
			this.placeBlock(levelIn, block1, 11, 4, 7, boxIn);
			this.placeBlock(levelIn, block1, 10, 4, 7, boxIn);
			this.placeBlock(levelIn, block1, 11, 4, 8, boxIn);
			this.placeBlock(levelIn, block1, 11, 4, 11, boxIn);
			this.placeBlock(levelIn, block1, 10, 4, 11, boxIn);
			this.placeBlock(levelIn, block1, 11, 4, 10, boxIn);
			//center corners
			this.generateBox(levelIn, boxIn, 6, 3, 6, 6, 4, 6, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 3, 5, 6, 4, 5, block1, block1, false);
			this.generateBox(levelIn, boxIn, 5, 3, 6, 5, 4, 6, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 3, 12, 6, 4, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 3, 13, 6, 4, 13, block1, block1, false);
			this.generateBox(levelIn, boxIn, 5, 3, 12, 5, 4, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 12, 3, 6, 12, 4, 6, block1, block1, false);
			this.generateBox(levelIn, boxIn, 13, 3, 6, 13, 4, 6, block1, block1, false);
			this.generateBox(levelIn, boxIn, 12, 3, 5, 12, 4, 5, block1, block1, false);
			this.generateBox(levelIn, boxIn, 12, 3, 12, 12, 4, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 12, 3, 13, 12, 4, 13, block1, block1, false);
			this.generateBox(levelIn, boxIn, 13, 3, 12, 13, 4, 12, block1, block1, false);
			//center rails
			this.generateBox(levelIn, boxIn, 5, 4, 5, 5, 5, 5, block1, block1, false);
			this.generateBox(levelIn, boxIn, 4, 4, 6, 4, 5, 6, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 4, 4, 6, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 5, 4, 13, 5, 5, 13, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 4, 14, 6, 5, 14, block1, block1, false);
			this.generateBox(levelIn, boxIn, 4, 4, 12, 4, 5, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 13, 4, 5, 13, 5, 5, block1, block1, false);
			this.generateBox(levelIn, boxIn, 14, 4, 6, 14, 5, 6, block1, block1, false);
			this.generateBox(levelIn, boxIn, 12, 4, 4, 12, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 13, 4, 13, 13, 5, 13, block1, block1, false);
			this.generateBox(levelIn, boxIn, 14, 4, 12, 14, 5, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 12, 4, 14, 12, 5, 14, block1, block1, false);
			//top2
			this.generateBox(levelIn, boxIn, 0, 4, 7, 5, 4, 8, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 4, 7, 6, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boxIn, 13, 4, 7, 18, 4, 8, block1, block1, false);
			this.generateBox(levelIn, boxIn, 12, 4, 7, 12, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boxIn, 0, 4, 9, 8, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boxIn, 10, 4, 9, 18, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boxIn, 0, 4, 10, 5, 4, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 4, 10, 6, 4, 11, block2, block2, false);
			this.generateBox(levelIn, boxIn, 13, 4, 10, 18, 4, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 12, 4, 10, 12, 4, 11, block2, block2, false);
			//bottom
			this.generateBox(levelIn, boxIn, 7, 3, 0, 11, 3, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 3, 7, 18, 3, 11, block1, block1, false);
			//rails
			this.generateBox(levelIn, boxIn, 7, 5, 0, 7, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 5, 14, 7, 5, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 11, 5, 0, 11, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 11, 5, 14, 11, 5, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 5, 7, 4, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boxIn, 14, 5, 7, 18, 5, 7, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 5, 11, 4, 5, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 14, 5, 11, 18, 5, 11, block1, block1, false);
			//pillars z-Axis
			this.generateBox(levelIn, boxIn, 7, 2, 0, 11, 2, 0, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 7, 2, 4, 11, 2, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 2, 18, 11, 2, 18, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 7, 2, 14, 11, 2, 14, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 2, 3, 11, 2, 3, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 7, 2, 5, 11, 2, 5, stair2, stair2, false);
			this.generateBox(levelIn, boxIn, 7, 2, 15, 11, 2, 15, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 7, 2, 13, 11, 2, 13, stair1, stair1, false);
			this.generateBox(levelIn, boxIn, 7, 1, 1, 11, 1, 1, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 7, 1, 2, 11, 1, 2, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 7, 0, 1, 11, 0, 2, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 1, 0, 11, 1, 0, stair1, stair1, false);
			this.generateBox(levelIn, boxIn, 7, 1, 18, 11, 1, 18, stair2, stair2, false);
			this.generateBox(levelIn, boxIn, 7, 1, 16, 11, 1, 16, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 7, 1, 17, 11, 1, 17, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 7, 0, 16, 11, 0, 17, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 1, 3, 11, 1, 3, stair2, stair2, false);
			this.generateBox(levelIn, boxIn, 7, 1, 15, 11, 1, 15, stair1, stair1, false);
			for (int w1 = 7; w1 <= 11; ++w1) {
				for (int l1 = 1; l1 <= 2; ++l1) {
					this.fillColumnDown(levelIn, block1, w1, -1, l1, boxIn);
					this.fillColumnDown(levelIn, block1, w1, -1, 18 - l1, boxIn);
				}
			}
			//pillars x-axis
			this.generateBox(levelIn, boxIn, 0, 2, 7, 0, 2, 11, stair8, stair8, false);
			this.generateBox(levelIn, boxIn, 4, 2, 7, 4, 2, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 18, 2, 7, 18, 2, 11, stair7, stair7, false);
			this.generateBox(levelIn, boxIn, 14, 2, 7, 14, 2, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 3, 2, 7, 3, 2, 11, stair7, stair7, false);
			this.generateBox(levelIn, boxIn, 5, 2, 7, 5, 2, 11, stair6, stair6, false);
			this.generateBox(levelIn, boxIn, 15, 2, 7, 15, 2, 11, stair8, stair8, false);
			this.generateBox(levelIn, boxIn, 13, 2, 7, 13, 2, 11, stair5, stair5, false);
			this.generateBox(levelIn, boxIn, 1, 1, 7, 1, 1, 11, stair8, stair8, false);
			this.generateBox(levelIn, boxIn, 2, 1, 7, 2, 1, 11, stair7, stair7, false);
			this.generateBox(levelIn, boxIn, 1, 0, 7, 2, 0, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 1, 7, 0, 1, 11, stair5, stair5, false);
			this.generateBox(levelIn, boxIn, 18, 1, 7, 18, 1, 11, stair6, stair6, false);
			this.generateBox(levelIn, boxIn, 16, 1, 7, 16, 1, 11, stair8, stair8, false);
			this.generateBox(levelIn, boxIn, 17, 1, 7, 17, 1, 11, stair7, stair7, false);
			this.generateBox(levelIn, boxIn, 16, 0, 7, 17, 0, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 3, 1, 7, 3, 1, 11, stair6, stair6, false);
			this.generateBox(levelIn, boxIn, 15, 1, 7, 15, 1, 11, stair5, stair5, false);
			for (int w2 = 1; w2 <= 2; ++w2) {
				for (int l2 = 7; l2 <= 11; ++l2) {
					this.fillColumnDown(levelIn, block1, w2, -1, l2, boxIn);
					this.fillColumnDown(levelIn, block1, 18 - w2, -1, l2, boxIn);
				}
			}
			//fluids
			this.placeBlock(levelIn, fluid1, 9, 5, 9, boxIn);
			//spawners
			if (!this.hasPlacedSpawner) {
				final BlockPos spawnerPos = new BlockPos(this.getWorldX(9, 9), this.getWorldY(4), this.getWorldZ(9, 9));
				if (boxIn.isInside(spawnerPos)) {
					this.hasPlacedSpawner = true;
					levelIn.setBlock(spawnerPos, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = levelIn.getBlockEntity(spawnerPos);
					if (spawner instanceof SpawnerBlockEntity)
						((SpawnerBlockEntity)spawner).setEntityId(EntityType.MAGMA_CUBE, randomIn);
				}
			}
		}
	}

	public static class CastleEntrance extends InfernalNetherFortressPieces.InfernalNetherBridgePiece {
		public CastleEntrance(final int componentTypeIn, final RandomSource randomIn, final BoundingBox boxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_CASTLE_ENTRANCE.get(), componentTypeIn, boxIn);
			this.setOrientation(facingIn);
		}

		public CastleEntrance(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_CASTLE_ENTRANCE.get(), compoundIn);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChildForward((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 5, 3, true);
		}

		public static final InfernalNetherFortressPieces.CastleEntrance createPiece(final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int componentTypeIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -5, -3, 0, 13, 14, 13, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new InfernalNetherFortressPieces.CastleEntrance(componentTypeIn, randomIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
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
			final BlockState fence1 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true));
			final BlockState fence2 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence3 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true));
			final BlockState fence4 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence5 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true));
			final BlockState fence6 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.SOUTH, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true));
			final BlockState fence7 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.SOUTH, Boolean.valueOf(true)).setValue(FenceBlock.WEST, Boolean.valueOf(true));
			final BlockState fence8 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.WEST, Boolean.valueOf(true));
			final BlockState fence9 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState();
			final BlockState slab1 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState();
			final BlockState slab2 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP);
			final BlockState slab3 = Blocks.NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP);
			final BlockState wall1 = Blocks.NETHER_BRICK_WALL.defaultBlockState();
			final BlockState wall2 = Blocks.RED_NETHER_BRICK_WALL.defaultBlockState();
			final BlockState fluid1 = Blocks.LAVA.defaultBlockState();
			//air
			this.generateAirBox(levelIn, boxIn, 0, 5, 0, 12, 13, 12);
			//floor
			this.generateBox(levelIn, boxIn, 0, 3, 0, 12, 4, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 3, 0, 6, 4, 5, block2, block2, false);
			this.generateBox(levelIn, boxIn, 6, 3, 7, 6, 4, 12, block2, block2, false);
			this.generateBox(levelIn, boxIn, 4, 3, 6, 5, 4, 6, block2, block2, false);
			this.generateBox(levelIn, boxIn, 7, 3, 6, 8, 4, 6, block2, block2, false);
			this.generateBox(levelIn, boxIn, 4, 3, 3, 8, 4, 3, block2, block2, false);
			this.generateBox(levelIn, boxIn, 4, 3, 9, 8, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boxIn, 3, 3, 4, 3, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boxIn, 9, 3, 4, 9, 4, 8, block2, block2, false);
			this.generateBox(levelIn, boxIn, 5, 3, 5, 5, 4, 5, block2, block2, false);
			this.generateBox(levelIn, boxIn, 5, 3, 7, 5, 4, 7, block2, block2, false);
			this.generateBox(levelIn, boxIn, 7, 3, 5, 7, 4, 5, block2, block2, false);
			this.generateBox(levelIn, boxIn, 7, 3, 7, 7, 4, 7, block2, block2, false);	
			this.generateBox(levelIn, boxIn, 6, 4, 7, 6, 4, 7, stair1, stair1, false);
			this.generateBox(levelIn, boxIn, 6, 4, 5, 6, 4, 5, stair2, stair2, false);
			this.generateBox(levelIn, boxIn, 7, 4, 6, 7, 4, 6, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 5, 4, 6, 5, 4, 6, stair4, stair4, false);
			this.generateAirBox(levelIn, boxIn, 6, 1, 6, 6, 4, 6);
			//walls
			this.generateBox(levelIn, boxIn, 0, 5, 0, 1, 12, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 11, 5, 0, 12, 12, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 2, 5, 11, 4, 12, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 8, 5, 11, 10, 12, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 5, 9, 11, 7, 12, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 2, 5, 0, 4, 12, 1, block1, block1, false);
			this.generateBox(levelIn, boxIn, 8, 5, 0, 10, 12, 1, block1, block1, false);
			this.generateBox(levelIn, boxIn, 5, 9, 0, 7, 12, 1, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 8, 0, 7, 8, 1, stair5, stair5, false);
			this.generateBox(levelIn, boxIn, 5, 8, 0, 5, 8, 1, stair6, stair6, false);
			this.generateBox(levelIn, boxIn, 7, 8, 11, 7, 8, 12, stair5, stair5, false);
			this.generateBox(levelIn, boxIn, 5, 8, 11, 5, 8, 12, stair6, stair6, false);
			this.generateBox(levelIn, boxIn, 6, 9, 1, 6, 9, 1, block4, block4, false);
			this.generateBox(levelIn, boxIn, 6, 9, 11, 6, 9, 11, block4, block4, false);
			//roof
			this.generateBox(levelIn, boxIn, 0, 11, 0, 12, 12, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 11, 4, 6, 12, 5, block2, block2, false);
			this.generateBox(levelIn, boxIn, 6, 11, 7, 6, 12, 8, block2, block2, false);
			this.generateBox(levelIn, boxIn, 4, 11, 6, 5, 12, 6, block2, block2, false);
			this.generateBox(levelIn, boxIn, 7, 11, 6, 8, 12, 6, block2, block2, false);
			this.generateBox(levelIn, boxIn, 4, 11, 3, 8, 12, 3, block2, block2, false);
			this.generateBox(levelIn, boxIn, 4, 11, 9, 8, 12, 9, block2, block2, false);
			this.generateBox(levelIn, boxIn, 3, 11, 4, 3, 12, 8, block2, block2, false);
			this.generateBox(levelIn, boxIn, 9, 11, 4, 9, 12, 8, block2, block2, false);
			this.generateBox(levelIn, boxIn, 5, 11, 5, 5, 12, 5, block2, block2, false);
			this.generateBox(levelIn, boxIn, 5, 11, 7, 5, 12, 7, block2, block2, false);
			this.generateBox(levelIn, boxIn, 7, 11, 5, 7, 12, 5, block2, block2, false);
			this.generateBox(levelIn, boxIn, 7, 11, 7, 7, 12, 7, block2, block2, false);
			//fences
			for (int i = 1; i <= 11; i += 2) {
				this.generateBox(levelIn, boxIn, i, 10, 0, i, 11, 0, fence1, fence1, false);
				this.generateBox(levelIn, boxIn, i, 9, 0, i, 9, 0, block2, block2, false);
				this.generateBox(levelIn, boxIn, i, 12, 0, i, 12, 0, block2, block2, false);
				this.generateBox(levelIn, boxIn, i, 10, 12, i, 11, 12, fence1, fence1, false);
				this.generateBox(levelIn, boxIn, i, 9, 12, i, 9, 12, block2, block2, false);
				this.generateBox(levelIn, boxIn, i, 12, 12, i, 12, 12, block2, block2, false);
				this.generateBox(levelIn, boxIn, 0, 10, i, 0, 11, i, fence2, fence2, false);
				this.generateBox(levelIn, boxIn, 0, 9, i, 0, 9, i, block2, block2, false);
				this.generateBox(levelIn, boxIn, 0, 12, i, 0, 12, i, block2, block2, false);
				this.generateBox(levelIn, boxIn, 12, 10, i, 12, 11, i, fence2, fence2, false);
				this.generateBox(levelIn, boxIn, 12, 9, i, 12, 9, i, block2, block2, false);
				this.generateBox(levelIn, boxIn, 12, 12, i, 12, 12, i, block2, block2, false);
				this.generateBox(levelIn, boxIn, i, 13, 0, i, 13, 0, block2, block2, false);
				this.generateBox(levelIn, boxIn, i, 13, 12, i, 13, 12, block2, block2, false);
				this.generateBox(levelIn, boxIn, 0, 13, i, 0, 13, i, block2, block2, false);
				this.generateBox(levelIn, boxIn, 12, 13, i, 12, 13, i, block2, block2, false);
				if (i != 11) {
					this.placeBlock(levelIn, fence3, i + 1, 13, 0, boxIn);
					this.placeBlock(levelIn, fence3, i + 1, 13, 12, boxIn);
					this.placeBlock(levelIn, fence4, 0, 13, i + 1, boxIn);
					this.placeBlock(levelIn, fence4, 12, 13, i + 1, boxIn);
				}
			}
			this.placeBlock(levelIn, fence5, 0, 13, 0, boxIn);
			this.placeBlock(levelIn, fence6, 0, 13, 12, boxIn);
			this.placeBlock(levelIn, fence7, 12, 13, 12, boxIn);
			this.placeBlock(levelIn, fence8, 12, 13, 0, boxIn);
			for (int k = 3; k <= 9; k += 2) {
				this.generateBox(levelIn, boxIn, 1, 7, k, 1, 8, k, fence2.setValue(FenceBlock.WEST, Boolean.valueOf(true)), fence2.setValue(FenceBlock.WEST, Boolean.valueOf(true)), false);
				this.generateBox(levelIn, boxIn, 1, 6, k, 1, 6, k, block2, block2, false);
				this.generateBox(levelIn, boxIn, 1, 9, k, 1, 9, k, block2, block2, false);
				this.generateBox(levelIn, boxIn, 11, 7, k, 11, 8, k, fence2.setValue(FenceBlock.EAST, Boolean.valueOf(true)), fence2.setValue(FenceBlock.EAST, Boolean.valueOf(true)), false);
				this.generateBox(levelIn, boxIn, 11, 6, k, 11, 6, k, block2, block2, false);
				this.generateBox(levelIn, boxIn, 11, 9, k, 11, 9, k, block2, block2, false);
			}
			//corners
			this.placeBlock(levelIn, slab1, 2, 5, 3, boxIn);
			this.placeBlock(levelIn, slab1, 3, 5, 2, boxIn);
			this.placeBlock(levelIn, slab1, 9, 5, 2, boxIn);
			this.placeBlock(levelIn, slab1, 10, 5, 3, boxIn);
			this.placeBlock(levelIn, slab1, 2, 5, 9, boxIn);
			this.placeBlock(levelIn, slab1, 3, 5, 10, boxIn);
			this.placeBlock(levelIn, slab1, 9, 5, 10, boxIn);
			this.placeBlock(levelIn, slab1, 10, 5, 9, boxIn);
			this.placeBlock(levelIn, slab2, 2, 10, 3, boxIn);
			this.placeBlock(levelIn, slab2, 3, 10, 2, boxIn);
			this.placeBlock(levelIn, slab2, 9, 10, 2, boxIn);
			this.placeBlock(levelIn, slab2, 10, 10, 3, boxIn);
			this.placeBlock(levelIn, slab2, 2, 10, 9, boxIn);
			this.placeBlock(levelIn, slab2, 3, 10, 10, boxIn);
			this.placeBlock(levelIn, slab2, 9, 10, 10, boxIn);
			this.placeBlock(levelIn, slab2, 10, 10, 9, boxIn);
			this.placeBlock(levelIn, fluid1, 2, 10, 2, boxIn);
			this.placeBlock(levelIn, fluid1, 10, 10, 2, boxIn);
			this.placeBlock(levelIn, fluid1, 2, 10, 10, boxIn);
			this.placeBlock(levelIn, fluid1, 10, 10, 10, boxIn);
			//center
			this.generateBox(levelIn, boxIn, 4, 10, 5, 8, 10, 5, slab2, slab2, false);
			this.generateBox(levelIn, boxIn, 5, 10, 4, 5, 10, 8, slab2, slab2, false);
			this.generateBox(levelIn, boxIn, 4, 10, 7, 8, 10, 7, slab2, slab2, false);
			this.generateBox(levelIn, boxIn, 7, 10, 4, 7, 10, 8, slab2, slab2, false);
			this.generateBox(levelIn, boxIn, 6, 10, 5, 6, 10, 5, slab3, slab3, false);
			this.generateBox(levelIn, boxIn, 5, 10, 6, 5, 10, 6, slab3, slab3, false);
			this.generateBox(levelIn, boxIn, 6, 10, 7, 6, 10, 7, slab3, slab3, false);
			this.generateBox(levelIn, boxIn, 7, 10, 6, 7, 10, 6, slab3, slab3, false);
			this.placeBlock(levelIn, wall1, 5, 5, 5, boxIn);
			this.placeBlock(levelIn, fence9, 5, 6, 5, boxIn);
			this.placeBlock(levelIn, wall2, 5, 7, 5, boxIn);
			this.placeBlock(levelIn, fence9, 5, 8, 5, boxIn);
			this.placeBlock(levelIn, wall1, 5, 9, 5, boxIn);
			this.placeBlock(levelIn, block2, 5, 10, 5, boxIn);
			this.placeBlock(levelIn, wall1, 5, 5, 7, boxIn);
			this.placeBlock(levelIn, fence9, 5, 6, 7, boxIn);
			this.placeBlock(levelIn, wall2, 5, 7, 7, boxIn);
			this.placeBlock(levelIn, fence9, 5, 8, 7, boxIn);
			this.placeBlock(levelIn, wall1, 5, 9, 7, boxIn);
			this.placeBlock(levelIn, block2, 5, 10, 7, boxIn);
			this.placeBlock(levelIn, wall1, 7, 5, 5, boxIn);
			this.placeBlock(levelIn, fence9, 7, 6, 5, boxIn);
			this.placeBlock(levelIn, wall2, 7, 7, 5, boxIn);
			this.placeBlock(levelIn, fence9, 7, 8, 5, boxIn);
			this.placeBlock(levelIn, wall1, 7, 9, 5, boxIn);
			this.placeBlock(levelIn, block2, 7, 10, 5, boxIn);
			this.placeBlock(levelIn, wall1, 7, 5, 7, boxIn);
			this.placeBlock(levelIn, fence9, 7, 6, 7, boxIn);
			this.placeBlock(levelIn, wall2, 7, 7, 7, boxIn);
			this.placeBlock(levelIn, fence9, 7, 8, 7, boxIn);
			this.placeBlock(levelIn, wall1, 7, 9, 7, boxIn);
			this.placeBlock(levelIn, block2, 7, 10, 7, boxIn);
			this.placeBlock(levelIn, fluid1, 6, 10, 6, boxIn);
			this.fillColumnDown(levelIn, block1, 6, 1, 6, boxIn);
			//base pillars
			this.fillColumnDown(levelIn, block1, 0, 2, 0, boxIn);
			this.fillColumnDown(levelIn, block1, 0, 2, 12, boxIn);
			this.fillColumnDown(levelIn, block1, 12, 2, 0, boxIn);
			this.fillColumnDown(levelIn, block1, 12, 2, 12, boxIn);
			this.fillColumnDown(levelIn, block1, 0, 1, 6, boxIn);
			this.fillColumnDown(levelIn, block1, 6, 1, 0, boxIn);
			this.fillColumnDown(levelIn, block1, 6, 1, 12, boxIn);
			this.fillColumnDown(levelIn, block1, 12, 1, 6, boxIn);
			this.placeBlock(levelIn, block5, 0, 2, 6, boxIn);
			this.placeBlock(levelIn, block5, 6, 2, 0, boxIn);
			this.placeBlock(levelIn, block5, 6, 2, 12, boxIn);
			this.placeBlock(levelIn, block5, 12, 2, 6, boxIn);
			//pillars x
			this.placeBlock(levelIn, stair5, 11, 0, 0, boxIn);
			this.placeBlock(levelIn, stair10, 11, 1, 0, boxIn);
			this.placeBlock(levelIn, stair5, 10, 1, 0, boxIn);
			this.placeBlock(levelIn, stair10, 10, 2, 0, boxIn);
			this.placeBlock(levelIn, stair5, 9, 2, 0, boxIn);
			this.placeBlock(levelIn, stair5, 11, 0, 12, boxIn);
			this.placeBlock(levelIn, stair10, 11, 1, 12, boxIn);
			this.placeBlock(levelIn, stair5, 10, 1, 12, boxIn);
			this.placeBlock(levelIn, stair10, 10, 2, 12, boxIn);
			this.placeBlock(levelIn, stair5, 9, 2, 12, boxIn);
			this.placeBlock(levelIn, stair5, 5, 1, 0, boxIn);
			this.placeBlock(levelIn, stair10, 5, 2, 0, boxIn);
			this.placeBlock(levelIn, stair5, 4, 2, 0, boxIn);
			this.placeBlock(levelIn, stair5, 5, 1, 12, boxIn);
			this.placeBlock(levelIn, stair10, 5, 2, 12, boxIn);
			this.placeBlock(levelIn, stair5, 4, 2, 12, boxIn);
			this.placeBlock(levelIn, stair6, 1, 0, 0, boxIn);
			this.placeBlock(levelIn, stair9, 1, 1, 0, boxIn);
			this.placeBlock(levelIn, stair6, 2, 1, 0, boxIn);
			this.placeBlock(levelIn, stair9, 2, 2, 0, boxIn);
			this.placeBlock(levelIn, stair6, 3, 2, 0, boxIn);
			this.placeBlock(levelIn, stair6, 1, 0, 12, boxIn);
			this.placeBlock(levelIn, stair9, 1, 1, 12, boxIn);
			this.placeBlock(levelIn, stair6, 2, 1, 12, boxIn);
			this.placeBlock(levelIn, stair9, 2, 2, 12, boxIn);
			this.placeBlock(levelIn, stair6, 3, 2, 12, boxIn);
			this.placeBlock(levelIn, stair6, 7, 1, 0, boxIn);
			this.placeBlock(levelIn, stair9, 7, 2, 0, boxIn);
			this.placeBlock(levelIn, stair6, 8, 2, 0, boxIn);
			this.placeBlock(levelIn, stair6, 7, 1, 12, boxIn);
			this.placeBlock(levelIn, stair9, 7, 2, 12, boxIn);
			this.placeBlock(levelIn, stair6, 8, 2, 12, boxIn);
			//pillars z
			this.placeBlock(levelIn, stair7, 0, 0, 11, boxIn);
			this.placeBlock(levelIn, stair12, 0, 1, 11, boxIn);
			this.placeBlock(levelIn, stair7, 0, 1, 10, boxIn);
			this.placeBlock(levelIn, stair12, 0, 2, 10, boxIn);
			this.placeBlock(levelIn, stair7, 0, 2, 9, boxIn);
			this.placeBlock(levelIn, stair7, 12, 0, 11, boxIn);
			this.placeBlock(levelIn, stair12, 12, 1, 11, boxIn);
			this.placeBlock(levelIn, stair7, 12, 1, 10, boxIn);
			this.placeBlock(levelIn, stair12, 12, 2, 10, boxIn);
			this.placeBlock(levelIn, stair7, 12, 2, 9, boxIn);
			this.placeBlock(levelIn, stair7, 0, 1, 5, boxIn);
			this.placeBlock(levelIn, stair12, 0, 2, 5, boxIn);
			this.placeBlock(levelIn, stair7, 0, 2, 4, boxIn);
			this.placeBlock(levelIn, stair7, 12, 1, 5, boxIn);
			this.placeBlock(levelIn, stair12, 12, 2, 5, boxIn);
			this.placeBlock(levelIn, stair7, 12, 2, 4, boxIn);
			this.placeBlock(levelIn, stair8, 0, 0, 1, boxIn);
			this.placeBlock(levelIn, stair11, 0, 1, 1, boxIn);
			this.placeBlock(levelIn, stair8, 0, 1, 2, boxIn);
			this.placeBlock(levelIn, stair11, 0, 2, 2, boxIn);
			this.placeBlock(levelIn, stair8, 0, 2, 3, boxIn);
			this.placeBlock(levelIn, stair8, 12, 0, 1, boxIn);
			this.placeBlock(levelIn, stair11, 12, 1, 1, boxIn);
			this.placeBlock(levelIn, stair8, 12, 1, 2, boxIn);
			this.placeBlock(levelIn, stair11, 12, 2, 2, boxIn);
			this.placeBlock(levelIn, stair8, 12, 2, 3, boxIn);
			this.placeBlock(levelIn, stair8, 0, 1, 7, boxIn);
			this.placeBlock(levelIn, stair11, 0, 2, 7, boxIn);
			this.placeBlock(levelIn, stair8, 0, 2, 8, boxIn);
			this.placeBlock(levelIn, stair8, 12, 1, 7, boxIn);
			this.placeBlock(levelIn, stair11, 12, 2, 7, boxIn);
			this.placeBlock(levelIn, stair8, 12, 2, 8, boxIn);
		}
	}

	public static class StairsRoom extends InfernalNetherFortressPieces.InfernalNetherBridgePiece {
		public StairsRoom(final int componentTypeIn, final BoundingBox boxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_STAIRS_ROOM.get(), componentTypeIn, boxIn);
			this.setOrientation(facingIn);
		}

		public StairsRoom(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_STAIRS_ROOM.get(), compoundIn);
		}

		@Override
		public void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChildRight((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 6, 2, false);
		}

		public static final InfernalNetherFortressPieces.StairsRoom createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int componentTypeIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -2, 0, 0, 7, 11, 7, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new InfernalNetherFortressPieces.StairsRoom(componentTypeIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState fence1 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true));
			final BlockState fence2 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence3 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true));
			final BlockState fence4 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
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
			this.generateAirBox(levelIn, boxIn, 0, 2, 0, 6, 10, 6);
			//floor
			this.generateBox(levelIn, boxIn, 0, 0, 0, 6, 1, 6, block1, block1, false);
			this.generateBox(levelIn, boxIn, 3, 0, 0, 3, 1, 2, block2, block2, false);
			this.generateBox(levelIn, boxIn, 2, 0, 2, 2, 1, 4, block2, block2, false);
			this.generateBox(levelIn, boxIn, 4, 0, 2, 4, 1, 4, block2, block2, false);
			this.generateBox(levelIn, boxIn, 3, 0, 4, 3, 1, 4, block2, block2, false);
			//walls
			this.generateBox(levelIn, boxIn, 0, 2, 0, 1, 8, 0, block1, block1, false);
			this.generateBox(levelIn, boxIn, 5, 2, 0, 6, 8, 0, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 2, 1, 0, 8, 6, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 2, 1, 6, 8, 6, block1, block1, false);
			this.generateBox(levelIn, boxIn, 1, 2, 6, 5, 8, 6, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 3, 2, 0, 3, 4, fence2, fence2, false);
			this.generateBox(levelIn, boxIn, 6, 3, 2, 6, 3, 2, fence2, fence2, false);
			this.generateBox(levelIn, boxIn, 6, 3, 4, 6, 3, 4, fence2, fence2, false);
			this.generateBox(levelIn, boxIn, 0, 4, 2, 0, 4, 4, fence4, fence4, false);
			this.generateBox(levelIn, boxIn, 6, 4, 2, 6, 4, 2, fence4, fence4, false);
			this.generateBox(levelIn, boxIn, 6, 4, 4, 6, 4, 4, fence4, fence4, false);
			this.generateBox(levelIn, boxIn, 0, 5, 2, 0, 5, 4, fence2, fence2, false);
			this.generateBox(levelIn, boxIn, 6, 5, 2, 6, 5, 2, fence2, fence2, false);
			this.generateBox(levelIn, boxIn, 6, 5, 4, 6, 5, 4, fence2, fence2, false);
			this.generateBox(levelIn, boxIn, 0, 6, 2, 0, 6, 4, block2, block2, false);
			this.generateBox(levelIn, boxIn, 6, 6, 2, 6, 6, 2, block2, block2, false);
			this.generateBox(levelIn, boxIn, 6, 6, 4, 6, 6, 4, block2, block2, false);
			this.generateBox(levelIn, boxIn, 0, 2, 2, 0, 2, 4, block2, block2, false);
			this.generateBox(levelIn, boxIn, 6, 2, 2, 6, 2, 2, block2, block2, false);
			this.generateBox(levelIn, boxIn, 6, 2, 4, 6, 2, 4, block2, block2, false);
			//gate
			final int rand1 = randomIn.nextInt(2) == 0 ? randomIn.nextInt(2) : 0;
			this.generateAirBox(levelIn, boxIn, 6, 8, 2, 6, 8, 4);
			this.generateBox(levelIn, boxIn, 2, 6, 0, 4, 8, 0, block1, block1, false);
			this.generateBox(levelIn, boxIn, 2, 5 - rand1, 0, 2, 5, 0, fence1, fence1, false);
			this.generateBox(levelIn, boxIn, 3, 5 - rand1, 0, 3, 5, 0, fence3, fence3, false);
			this.generateBox(levelIn, boxIn, 4, 5 - rand1, 0, 4, 5, 0, fence1, fence1, false);
			//roof
			this.generateBox(levelIn, boxIn, 1, 7, 1, 5, 7, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 4, 7, 3, 6, 7, 3, block2, block2, false);
			this.generateBox(levelIn, boxIn, 2, 7, 2, 4, 7, 2, block2, block2, false);
			this.generateBox(levelIn, boxIn, 2, 7, 4, 4, 7, 4, block2, block2, false);
			this.generateBox(levelIn, boxIn, 2, 7, 3, 2, 7, 3, block2, block2, false);
			this.generateBox(levelIn, boxIn, 1, 8, 0, 5, 8, 0, wall1, wall1, false);
			this.generateBox(levelIn, boxIn, 0, 8, 1, 0, 8, 5, wall2, wall2, false);
			this.generateBox(levelIn, boxIn, 1, 8, 6, 5, 8, 6, wall1, wall1, false);
			//stairs
			final int rand2 = randomIn.nextInt(4);
			final int rand3 = randomIn.nextInt(2);
			this.placeBlock(levelIn, block1, 5, 2, 5, boxIn);
			this.placeBlock(levelIn, stair7, 5, 2, 4, boxIn);
			this.generateBox(levelIn, boxIn, 4, 2, 5, 4, 2, 5, block2, block2, false);
			this.generateBox(levelIn, boxIn, 3, 2, 5, 3, 3, 5, block2, block2, false);
			this.generateBox(levelIn, boxIn, 2, 2, 5, 2, 4, 5, block2, block2, false);
			this.generateBox(levelIn, boxIn, 1, 2, 5, 1, 5, 5, block2, block2, false);
			this.placeBlock(levelIn, stair6, 4, 3, 5, boxIn);
			this.placeBlock(levelIn, stair6, 3, 4, 5, boxIn);
			this.placeBlock(levelIn, stair6, 2, 5, 5, boxIn);
			this.placeBlock(levelIn, stair6, 1, 6, 5, boxIn);
			if (rand2 == 0) {
				if (rand3 == 0) {
					this.generateAirBox(levelIn, boxIn, 1, 3, 5, 2, 4, 5);
					this.placeBlock(levelIn, stair1, 2, 4, 5, boxIn);
					this.placeBlock(levelIn, brewingStand, 1, 3, 5, boxIn);
					this.placeBlock(levelIn, skull2, 2, 3, 5, boxIn);
				} else {
					this.generateAirBox(levelIn, boxIn, 1, 3, 5, 2, 4, 5);
					this.placeBlock(levelIn, stair1, 2, 4, 5, boxIn);
					this.placeBlock(levelIn, brewingStand, 1, 3, 5, boxIn);
					this.placeBlock(levelIn, skull1, 2, 3, 5, boxIn);
				}
			}
			//bottom
			this.fillColumnDown(levelIn, block1, 0, -1, 0, boxIn);
			this.fillColumnDown(levelIn, block1, 0, -1, 6, boxIn);
			this.fillColumnDown(levelIn, block1, 6, -1, 0, boxIn);
			this.fillColumnDown(levelIn, block1, 6, -1, 6, boxIn);
			this.placeBlock(levelIn, stair2, 5, -1, 0, boxIn);
			this.placeBlock(levelIn, stair2, 5, -1, 6, boxIn);
			this.placeBlock(levelIn, stair3, 1, -1, 0, boxIn);
			this.placeBlock(levelIn, stair3, 1, -1, 6, boxIn);
			this.placeBlock(levelIn, stair4, 0, -1, 5, boxIn);
			this.placeBlock(levelIn, stair4, 6, -1, 5, boxIn);
			this.placeBlock(levelIn, stair5, 0, -1, 1, boxIn);
			this.placeBlock(levelIn, stair5, 6, -1, 1, boxIn);
		}
	}

	public static class MonsterThrone extends InfernalNetherFortressPieces.InfernalNetherBridgePiece {
		private boolean hasPlacedSpawner;

		public MonsterThrone(final int componentTypeIn, final BoundingBox boxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_MONSTER_THRONE.get(), componentTypeIn, boxIn);
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

		public static final InfernalNetherFortressPieces.MonsterThrone createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int componentTypeIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -2, 0, 0, 7, 8, 9, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new InfernalNetherFortressPieces.MonsterThrone(componentTypeIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
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
			final BlockState fence2 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true));
			final BlockState fence3 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence4 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true));
			final BlockState fence5 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.EAST, Boolean.valueOf(true));
			final BlockState fence6 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.EAST, Boolean.valueOf(true)).setValue(FenceBlock.NORTH, Boolean.valueOf(true));
			final BlockState fence7 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true)).setValue(FenceBlock.NORTH, Boolean.valueOf(true));
			final BlockState fence8 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.EAST, Boolean.valueOf(true)).setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence9 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true)).setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
			//air
			this.generateAirBox(levelIn, boxIn, 0, 2, 0, 6, 7, 7);
			//stairs
			this.generateBox(levelIn, boxIn, 1, 0, 0, 5, 1, 7, block1, block1, false);
			this.generateBox(levelIn, boxIn, 3, 0, 0, 3, 1, 4, block2, block2, false);
			this.generateBox(levelIn, boxIn, 2, 0, 5, 4, 1, 6, block2, block2, false);
			this.generateBox(levelIn, boxIn, 1, 2, 1, 5, 2, 7, block1, block1, false);
			this.generateBox(levelIn, boxIn, 3, 2, 1, 3, 2, 4, block2, block2, false);
			this.generateBox(levelIn, boxIn, 2, 2, 5, 4, 2, 6, block2, block2, false);
			this.placeBlock(levelIn, stair1, 2, 2, 0, boxIn);
			this.placeBlock(levelIn, stair2, 3, 2, 0, boxIn);
			this.placeBlock(levelIn, stair1, 4, 2, 0, boxIn);
			this.generateBox(levelIn, boxIn, 1, 3, 2, 5, 3, 7, block1, block1, false);
			this.generateBox(levelIn, boxIn, 3, 3, 2, 3, 3, 4, block2, block2, false);
			this.generateBox(levelIn, boxIn, 2, 3, 5, 4, 3, 6, block2, block2, false);
			this.placeBlock(levelIn, stair1, 2, 3, 1, boxIn);
			this.placeBlock(levelIn, stair2, 3, 3, 1, boxIn);
			this.placeBlock(levelIn, stair1, 4, 3, 1, boxIn);
			this.generateBox(levelIn, boxIn, 1, 4, 3, 5, 4, 7, block1, block1, false);
			this.generateBox(levelIn, boxIn, 3, 4, 3, 3, 4, 4, block2, block2, false);
			this.generateBox(levelIn, boxIn, 2, 4, 5, 4, 4, 6, block2, block2, false);
			this.placeBlock(levelIn, stair1, 2, 4, 2, boxIn);
			this.placeBlock(levelIn, stair2, 3, 4, 2, boxIn);
			this.placeBlock(levelIn, stair1, 4, 4, 2, boxIn);
			//rails
			this.generateBox(levelIn, boxIn, 1, 2, 0, 1, 4, 2, block1, block1, false);
			this.generateBox(levelIn, boxIn, 5, 2, 0, 5, 4, 2, block1, block1, false);
			this.generateBox(levelIn, boxIn, 1, 4, 0, 1, 4, 0, stair1, stair1, false);
			this.generateBox(levelIn, boxIn, 5, 4, 0, 5, 4, 0, stair1, stair1, false);
			this.generateBox(levelIn, boxIn, 1, 5, 3, 1, 5, 3, block1, block1, false);
			this.generateBox(levelIn, boxIn, 5, 5, 3, 5, 5, 3, block1, block1, false);
			this.generateBox(levelIn, boxIn, 1, 6, 3, 1, 6, 3, fence1, fence1, false);
			this.generateBox(levelIn, boxIn, 5, 6, 3, 5, 6, 3, fence1, fence1, false);
			this.generateBox(levelIn, boxIn, 1, 7, 3, 1, 7, 3, block3, block3, false);
			this.generateBox(levelIn, boxIn, 5, 7, 3, 5, 7, 3, block3, block3, false);
			this.generateBox(levelIn, boxIn, 1, 5, 2, 1, 5, 2, stair1, stair1, false);
			this.generateBox(levelIn, boxIn, 5, 5, 2, 5, 5, 2, stair1, stair1, false);
			this.generateBox(levelIn, boxIn, 0, 5, 3, 0, 5, 8, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 5, 3, 6, 5, 8, block1, block1, false);
			this.generateBox(levelIn, boxIn, 1, 5, 8, 5, 5, 8, block1, block1, false);
			//back
			this.placeBlock(levelIn, fence4, 1, 6, 3, boxIn);
			this.placeBlock(levelIn, fence5, 5, 6, 3, boxIn);
			this.placeBlock(levelIn, fence6, 0, 6, 3, boxIn);
			this.placeBlock(levelIn, fence7, 6, 6, 3, boxIn);
			this.generateBox(levelIn, boxIn, 0, 6, 4, 0, 6, 7, fence3, fence3, false);
			this.generateBox(levelIn, boxIn, 6, 6, 4, 6, 6, 7, fence3, fence3, false);
			this.placeBlock(levelIn, fence8, 0, 6, 8, boxIn);
			this.placeBlock(levelIn, fence9, 6, 6, 8, boxIn);
			this.generateBox(levelIn, boxIn, 1, 6, 8, 5, 6, 8, fence2, fence2, false);
			this.placeBlock(levelIn, fence5, 1, 7, 8, boxIn);
			this.generateBox(levelIn, boxIn, 2, 7, 8, 4, 7, 8, fence2, fence2, false);
			this.placeBlock(levelIn, fence4, 2, 7, 8, boxIn);
			this.placeBlock(levelIn, block4, 3, 7, 8, boxIn);
			this.placeBlock(levelIn, fence5, 4, 7, 8, boxIn);
			this.placeBlock(levelIn, fence4, 5, 7, 8, boxIn);
			this.placeBlock(levelIn, fence5, 2, 8, 8, boxIn);
			this.placeBlock(levelIn, fence2, 3, 8, 8, boxIn);
			this.placeBlock(levelIn, fence4, 4, 8, 8, boxIn);
			//spawner
			if (!this.hasPlacedSpawner) {
				final BlockPos blockPos = new BlockPos(this.getWorldX(3, 5), this.getWorldY(5), this.getWorldZ(3, 5));
				if (boxIn.isInside(blockPos)) {
					this.hasPlacedSpawner = true;
					levelIn.setBlock(blockPos, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = levelIn.getBlockEntity(blockPos);
					if (spawner instanceof SpawnerBlockEntity)
						((SpawnerBlockEntity)spawner).setEntityId(EntityType.BLAZE, randomIn);
				}
			}
			//bottom
			this.generateBox(levelIn, boxIn, 1, -1, 0, 5, -1, 0, block1, block1, false);
			this.generateBox(levelIn, boxIn, 1, -1, 1, 5, -1, 1, stair6, stair6, false);
			this.placeBlock(levelIn, stair5, 1, -1, 2, boxIn);
			this.placeBlock(levelIn, stair5, 5, -1, 2, boxIn);
			this.fillColumnDown(levelIn, block1, 1, -1, 3, boxIn);
			this.fillColumnDown(levelIn, block1, 1, -1, 7, boxIn);
			this.fillColumnDown(levelIn, block1, 5, -1, 3, boxIn);
			this.fillColumnDown(levelIn, block1, 5, -1, 7, boxIn);
			this.placeBlock(levelIn, stair3, 4, -1, 3, boxIn);
			this.placeBlock(levelIn, stair3, 4, -1, 7, boxIn);
			this.placeBlock(levelIn, stair4, 2, -1, 3, boxIn);
			this.placeBlock(levelIn, stair4, 2, -1, 7, boxIn);
			this.placeBlock(levelIn, stair5, 1, -1, 6, boxIn);
			this.placeBlock(levelIn, stair5, 5, -1, 6, boxIn);
			this.placeBlock(levelIn, stair6, 1, -1, 4, boxIn);
			this.placeBlock(levelIn, stair6, 5, -1, 4, boxIn);
		}
	}

	public static class GrandMonsterThrone extends InfernalNetherFortressPieces.InfernalNetherBridgePiece implements TeleportDestinationPiece {
		private final boolean[] hasPlacedSpawner = new boolean[5];
		private final boolean[] isNeedingChest = new boolean[3];

		public GrandMonsterThrone(final int componentTypeIn, final BoundingBox boxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_GRAND_MONSTER_THRONE.get(), componentTypeIn, boxIn);
			this.setOrientation(facingIn);
			for (int i = 0; i < 3; i++)
				this.isNeedingChest[i] = true;
		}

		public GrandMonsterThrone(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_GRAND_MONSTER_THRONE.get(), compoundIn);
			for (int i = 0; i < 5; i++)
				this.hasPlacedSpawner[i] = compoundIn.getBoolean("Mob" + String.valueOf(i));
			for (int i = 0; i < 3; i++)
				this.isNeedingChest[i] = compoundIn.getBoolean("Chest" + String.valueOf(i));
		}

		@Override
		protected final void addAdditionalSaveData(final StructurePieceSerializationContext contextIn, final CompoundTag compoundIn) {
			super.addAdditionalSaveData(contextIn, compoundIn);
			for (int i = 0; i < 5; i++)
				compoundIn.putBoolean("Mob" + String.valueOf(i), this.hasPlacedSpawner[i]);
			for (int i = 0; i < 3; i++)
				compoundIn.putBoolean("Chest" + String.valueOf(i), this.isNeedingChest[i]);
		}

		public static final InfernalNetherFortressPieces.GrandMonsterThrone createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int componentTypeIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -6, 0, 0, 14, 12, 22, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new InfernalNetherFortressPieces.GrandMonsterThrone(componentTypeIn, box, facingIn) : null;
		}

		@Override
		public final BlockPos getDestination() {
			return this.getWorldPos(7, 5, 14);
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = BWBlocks.BLAZE_LANTERN.get().defaultBlockState();
			final BlockState block4 = BWBlocks.ANCIENT_NETHER_ALTAR.get().defaultBlockState().setValue(AncientNetherAltarBlock.FACING, Direction.SOUTH);
			final BlockState block5 = Blocks.LANTERN.defaultBlockState().setValue(LanternBlock.HANGING, Boolean.valueOf(true));
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST);	
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair5 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST);
			final BlockState stair6 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST);
			final BlockState stair7 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
			final BlockState stair8 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair9 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair10 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);	
			final BlockState stair11 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair12 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair16 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState fence1 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState();
			final BlockState fence2 = Blocks.NETHER_BRICK_FENCE.defaultBlockState();
			final BlockState fence3 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.EAST, Boolean.valueOf(true));
			final BlockState fence4 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true));
			final BlockState fence5 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.EAST, Boolean.valueOf(true)).setValue(FenceBlock.WEST, Boolean.valueOf(true));
			final BlockState fence6 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.EAST, Boolean.valueOf(true)).setValue(FenceBlock.WEST, Boolean.valueOf(true));
			final BlockState fence7 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.EAST, Boolean.valueOf(true));
			final BlockState fence8 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true));
			final BlockState wall1 = Blocks.RED_NETHER_BRICK_WALL.defaultBlockState();
			final BlockState wall2 = Blocks.NETHER_BRICK_WALL.defaultBlockState();
			final BlockState slab1 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState();
			//air
			this.generateAirBox(levelIn, boxIn, 6, 2, 0, 8, 6, 2);
			this.generateAirBox(levelIn, boxIn, 6, 3, 2, 8, 7, 4);
			this.generateAirBox(levelIn, boxIn, 6, 4, 4, 8, 8, 7);
			this.generateAirBox(levelIn, boxIn, 1, 4, 12, 13, 12, 16);
			this.generateAirBox(levelIn, boxIn, 2, 4, 10, 12, 12, 18);
			this.generateAirBox(levelIn, boxIn, 3, 4, 9, 11, 12, 19);
			this.generateAirBox(levelIn, boxIn, 4, 4, 8, 10, 12, 20);
			//stairs
			this.placeBlock(levelIn, stair12, 5, 0, 0, boxIn);
			this.placeBlock(levelIn, stair12, 6, 0, 0, boxIn);
			this.placeBlock(levelIn, stair16, 7, 0, 0, boxIn);
			this.placeBlock(levelIn, stair12, 8, 0, 0, boxIn);
			this.placeBlock(levelIn, stair12, 9, 0, 0, boxIn);
			this.generateBox(levelIn, boxIn, 5, 1, 0, 6, 1, 2, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 1, 0, 7, 1, 2, block2, block2, false);
			this.generateBox(levelIn, boxIn, 8, 1, 0, 9, 1, 2, block1, block1, false);
			this.placeBlock(levelIn, stair3, 6, 2, 2, boxIn);
			this.placeBlock(levelIn, stair7, 7, 2, 2, boxIn);
			this.placeBlock(levelIn, stair3, 8, 2, 2, boxIn);
			this.placeBlock(levelIn, stair12, 5, 1, 3, boxIn);
			this.placeBlock(levelIn, stair12, 6, 1, 3, boxIn);
			this.placeBlock(levelIn, stair16, 7, 1, 3, boxIn);
			this.placeBlock(levelIn, stair12, 8, 1, 3, boxIn);
			this.placeBlock(levelIn, stair12, 9, 1, 3, boxIn);
			this.generateBox(levelIn, boxIn, 5, 2, 3, 6, 2, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 2, 3, 7, 2, 4, block2, block2, false);
			this.generateBox(levelIn, boxIn, 8, 2, 3, 9, 2, 4, block1, block1, false);
			this.placeBlock(levelIn, stair3, 6, 3, 4, boxIn);
			this.placeBlock(levelIn, stair7, 7, 3, 4, boxIn);
			this.placeBlock(levelIn, stair3, 8, 3, 4, boxIn);
			this.placeBlock(levelIn, stair12, 5, 2, 5, boxIn);
			this.placeBlock(levelIn, stair12, 6, 2, 5, boxIn);
			this.placeBlock(levelIn, stair16, 7, 2, 5, boxIn);
			this.placeBlock(levelIn, stair12, 8, 2, 5, boxIn);
			this.placeBlock(levelIn, stair12, 9, 2, 5, boxIn);
			this.generateBox(levelIn, boxIn, 5, 3, 5, 6, 3, 7, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 3, 5, 7, 3, 7, block2, block2, false);
			this.generateBox(levelIn, boxIn, 8, 3, 5, 9, 3, 7, block1, block1, false);
			//rails
			this.generateBox(levelIn, boxIn, 5, 2, 0, 5, 2, 2, block1, block1, false);
			this.generateBox(levelIn, boxIn, 9, 2, 0, 9, 2, 2, block1, block1, false);
			this.generateBox(levelIn, boxIn, 5, 3, 2, 5, 3, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 9, 3, 2, 9, 3, 4, block1, block1, false);
			this.placeBlock(levelIn, stair3, 5, 3, 1, boxIn);
			this.placeBlock(levelIn, stair3, 9, 3, 1, boxIn);
			this.generateBox(levelIn, boxIn, 5, 4, 4, 5, 4, 7, block1, block1, false);
			this.generateBox(levelIn, boxIn, 9, 4, 4, 9, 4, 7, block1, block1, false);
			this.placeBlock(levelIn, stair3, 5, 4, 3, boxIn);
			this.placeBlock(levelIn, stair3, 9, 4, 3, boxIn);
			this.generateBox(levelIn, boxIn, 4, 3, 7, 4, 4, 7, block1, block1, false);
			this.generateBox(levelIn, boxIn, 10, 3, 7, 10, 4, 7, block1, block1, false);
			//center rails
			this.generateBox(levelIn, boxIn, 3, 3, 8, 3, 4, 8, block1, block1, false);
			this.generateBox(levelIn, boxIn, 11, 3, 8, 11, 4, 8, block1, block1, false);
			this.generateBox(levelIn, boxIn, 2, 3, 9, 2, 4, 9, block1, block1, false);
			this.generateBox(levelIn, boxIn, 12, 3, 9, 12, 4, 9, block1, block1, false);
			this.generateBox(levelIn, boxIn, 1, 3, 10, 1, 4, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 13, 3, 10, 13, 4, 11, block1, block1, false);
			this.placeBlock(levelIn, wall2, 1, 5, 11, boxIn);
			this.placeBlock(levelIn, fence2, 1, 6, 11, boxIn);
			if (!this.hasPlacedSpawner[0]) {
				final BlockPos blockPos1 = new BlockPos(this.getWorldX(1, 11), this.getWorldY(7), this.getWorldZ(1, 11));
				if (boxIn.isInside(blockPos1)) {
					this.hasPlacedSpawner[0] = true;
					levelIn.setBlock(blockPos1, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = levelIn.getBlockEntity(blockPos1);
					if (spawner instanceof SpawnerBlockEntity)
						((SpawnerBlockEntity)spawner).setEntityId(EntityType.WITHER_SKELETON, randomIn);
				}
			}
			this.placeBlock(levelIn, wall2, 13, 5, 11, boxIn);
			this.placeBlock(levelIn, fence2, 13, 6, 11, boxIn);
			if (!this.hasPlacedSpawner[1]) {
				final BlockPos blockPos2 = new BlockPos(this.getWorldX(13, 11), this.getWorldY(7), this.getWorldZ(13, 11));
				if (boxIn.isInside(blockPos2)) {
					this.hasPlacedSpawner[1] = true;
					levelIn.setBlock(blockPos2, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = levelIn.getBlockEntity(blockPos2);
					if (spawner instanceof SpawnerBlockEntity)
						((SpawnerBlockEntity)spawner).setEntityId(EntityType.WITHER_SKELETON, randomIn);
				}
			}
			this.generateBox(levelIn, boxIn, 0, 3, 12, 0, 4, 16, block1, block1, false);
			this.generateBox(levelIn, boxIn, 14, 3, 12, 14, 4, 16, block1, block1, false);
			this.generateBox(levelIn, boxIn, 1, 3, 17, 1, 4, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 13, 3, 17, 13, 4, 18, block1, block1, false);
			this.placeBlock(levelIn, wall2, 1, 5, 17, boxIn);
			this.placeBlock(levelIn, fence2, 1, 6, 17, boxIn);
			if (!this.hasPlacedSpawner[2]) {
				final BlockPos blockPos3 = new BlockPos(this.getWorldX(1, 17), this.getWorldY(7), this.getWorldZ(1, 17));
				if (boxIn.isInside(blockPos3)) {
					this.hasPlacedSpawner[2] = true;
					levelIn.setBlock(blockPos3, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = levelIn.getBlockEntity(blockPos3);
					if (spawner instanceof SpawnerBlockEntity)
						((SpawnerBlockEntity)spawner).setEntityId(EntityType.BLAZE, randomIn);
				}
			}
			this.placeBlock(levelIn, wall2, 13, 5, 17, boxIn);
			this.placeBlock(levelIn, fence2, 13, 6, 17, boxIn);
			if (!this.hasPlacedSpawner[3]) {
				final BlockPos blockPos4 = new BlockPos(this.getWorldX(13, 17), this.getWorldY(7), this.getWorldZ(13, 17));
				if (boxIn.isInside(blockPos4)) {
					this.hasPlacedSpawner[3] = true;
					levelIn.setBlock(blockPos4, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = levelIn.getBlockEntity(blockPos4);
					if (spawner instanceof SpawnerBlockEntity)
						((SpawnerBlockEntity)spawner).setEntityId(EntityType.BLAZE, randomIn);
				}
			}
			this.generateBox(levelIn, boxIn, 2, 3, 19, 2, 4, 19, block1, block1, false);
			this.generateBox(levelIn, boxIn, 12, 3, 19, 12, 4, 19, block1, block1, false);
			this.placeBlock(levelIn, fence1, 2, 5, 19, boxIn);
			this.placeBlock(levelIn, fence1, 12, 5, 19, boxIn);
			this.generateBox(levelIn, boxIn, 3, 3, 20, 3, 4, 20, block1, block1, false);
			this.generateBox(levelIn, boxIn, 11, 3, 20, 11, 4, 20, block1, block1, false);
			this.placeBlock(levelIn, wall1, 3, 5, 20, boxIn);
			this.placeBlock(levelIn, wall1, 11, 5, 20, boxIn);
			this.placeBlock(levelIn, fence1, 3, 6, 20, boxIn);
			this.placeBlock(levelIn, fence1, 11, 6, 20, boxIn);
			this.generateBox(levelIn, boxIn, 4, 3, 21, 10, 4, 21, block1, block1, false);
			this.placeBlock(levelIn, wall2, 4, 5, 21, boxIn);
			this.placeBlock(levelIn, fence1, 5, 5, 21, boxIn);
			this.placeBlock(levelIn, wall2, 6, 5, 21, boxIn);
			this.placeBlock(levelIn, fence1, 7, 5, 21, boxIn);
			this.placeBlock(levelIn, wall2, 8, 5, 21, boxIn);
			this.placeBlock(levelIn, fence1, 9, 5, 21, boxIn);
			this.placeBlock(levelIn, wall2, 10, 5, 21, boxIn);
			this.placeBlock(levelIn, fence2, 4, 6, 21, boxIn);
			this.placeBlock(levelIn, wall1, 5, 6, 21, boxIn);
			this.placeBlock(levelIn, fence3, 6, 6, 21, boxIn);
			if (!this.hasPlacedSpawner[4]) {
				final BlockPos blockPos5 = new BlockPos(this.getWorldX(7, 21), this.getWorldY(6), this.getWorldZ(7, 21));
				if (boxIn.isInside(blockPos5)) {
					this.hasPlacedSpawner[4] = true;
					levelIn.setBlock(blockPos5, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = levelIn.getBlockEntity(blockPos5);
					if (spawner instanceof SpawnerBlockEntity)
						((SpawnerBlockEntity)spawner).setEntityId(EntityType.BLAZE, randomIn);
				}
			}
			this.placeBlock(levelIn, fence4, 8, 6, 21, boxIn);
			this.placeBlock(levelIn, wall1, 9, 6, 21, boxIn);
			this.placeBlock(levelIn, fence2, 10, 6, 21, boxIn);
			this.placeBlock(levelIn, fence4, 4, 7, 21, boxIn);
			this.placeBlock(levelIn, fence5, 5, 7, 21, boxIn);
			this.placeBlock(levelIn, fence3, 6, 7, 21, boxIn);
			this.placeBlock(levelIn, wall1, 7, 7, 21, boxIn);
			this.placeBlock(levelIn, fence4, 8, 7, 21, boxIn);
			this.placeBlock(levelIn, fence5, 9, 7, 21, boxIn);
			this.placeBlock(levelIn, fence3, 10, 7, 21, boxIn);
			this.placeBlock(levelIn, fence7, 5, 8, 21, boxIn);
			this.placeBlock(levelIn, fence6, 6, 8, 21, boxIn);
			this.placeBlock(levelIn, fence5, 7, 8, 21, boxIn);
			this.placeBlock(levelIn, fence6, 8, 8, 21, boxIn);
			this.placeBlock(levelIn, fence8, 9, 8, 21, boxIn);
			this.placeBlock(levelIn, fence1, 7, 9, 21, boxIn);
			//center
			this.generateBox(levelIn, boxIn, 4, 3, 8, 6, 3, 8, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 3, 8, 7, 3, 8, block2, block2, false);
			this.generateBox(levelIn, boxIn, 8, 3, 8, 10, 3, 8, block1, block1, false);
			this.generateBox(levelIn, boxIn, 3, 3, 9, 4, 3, 9, block1, block1, false);
			this.generateBox(levelIn, boxIn, 5, 3, 9, 9, 3, 9, block2, block2, false);
			this.generateBox(levelIn, boxIn, 10, 3, 9, 11, 3, 9, block1, block1, false);
			this.generateBox(levelIn, boxIn, 2, 3, 10, 3, 3, 10, block1, block1, false);
			this.generateBox(levelIn, boxIn, 4, 3, 10, 5, 3, 10, block2, block2, false);
			this.generateBox(levelIn, boxIn, 6, 3, 10, 6, 3, 10, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 3, 10, 7, 3, 10, block2, block2, false);
			this.generateBox(levelIn, boxIn, 8, 3, 10, 8, 3, 10, block1, block1, false);
			this.generateBox(levelIn, boxIn, 9, 3, 10, 10, 3, 10, block2, block2, false);
			this.generateBox(levelIn, boxIn, 11, 3, 10, 12, 3, 10, block1, block1, false);
			this.generateBox(levelIn, boxIn, 2, 3, 11, 2, 3, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 3, 3, 11, 11, 3, 11, block2, block2, false);
			this.generateBox(levelIn, boxIn, 12, 3, 11, 12, 3, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 1, 3, 12, 2, 3, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 3, 3, 12, 4, 3, 12, block2, block2, false);
			this.generateBox(levelIn, boxIn, 5, 3, 12, 6, 3, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 3, 12, 7, 3, 12, block2, block2, false);
			this.generateBox(levelIn, boxIn, 8, 3, 12, 9, 3, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 10, 3, 12, 11, 3, 12, block2, block2, false);
			this.generateBox(levelIn, boxIn, 12, 3, 12, 13, 3, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 1, 3, 13, 1, 3, 13, block1, block1, false);
			this.generateBox(levelIn, boxIn, 2, 3, 13, 4, 3, 13, block2, block2, false);
			this.generateBox(levelIn, boxIn, 5, 3, 13, 5, 3, 13, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 3, 13, 8, 3, 13, block2, block2, false);
			this.generateBox(levelIn, boxIn, 9, 3, 13, 9, 3, 13, block1, block1, false);
			this.generateBox(levelIn, boxIn, 10, 3, 13, 12, 3, 13, block2, block2, false);
			this.generateBox(levelIn, boxIn, 13, 3, 13, 13, 3, 13, block1, block1, false);
			this.generateBox(levelIn, boxIn, 1, 3, 14, 1, 3, 14, block1, block1, false);
			this.generateBox(levelIn, boxIn, 2, 3, 14, 2, 3, 14, block2, block2, false);
			this.generateBox(levelIn, boxIn, 3, 3, 14, 3, 3, 14, block1, block1, false);
			this.generateBox(levelIn, boxIn, 4, 3, 14, 6, 3, 14, block2, block2, false);
			this.generateBox(levelIn, boxIn, 7, 3, 14, 7, 3, 14, block1, block1, false);
			this.generateBox(levelIn, boxIn, 8, 3, 14, 10, 3, 14, block2, block2, false);
			this.generateBox(levelIn, boxIn, 11, 3, 14, 11, 3, 14, block1, block1, false);
			this.generateBox(levelIn, boxIn, 12, 3, 14, 12, 3, 14, block2, block2, false);
			this.generateBox(levelIn, boxIn, 13, 3, 14, 13, 3, 14, block1, block1, false);
			this.generateBox(levelIn, boxIn, 1, 3, 15, 1, 3, 15, block1, block1, false);
			this.generateBox(levelIn, boxIn, 2, 3, 15, 4, 3, 15, block2, block2, false);
			this.generateBox(levelIn, boxIn, 5, 3, 15, 5, 3, 15, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 3, 15, 8, 3, 15, block2, block2, false);
			this.generateBox(levelIn, boxIn, 9, 3, 15, 9, 3, 15, block1, block1, false);
			this.generateBox(levelIn, boxIn, 10, 3, 15, 12, 3, 15, block2, block2, false);
			this.generateBox(levelIn, boxIn, 13, 3, 15, 13, 3, 15, block1, block1, false);
			this.generateBox(levelIn, boxIn, 1, 3, 16, 2, 3, 16, block1, block1, false);
			this.generateBox(levelIn, boxIn, 3, 3, 16, 4, 3, 16, block2, block2, false);
			this.generateBox(levelIn, boxIn, 5, 3, 16, 6, 3, 16, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 3, 16, 7, 3, 16, block2, block2, false);
			this.generateBox(levelIn, boxIn, 8, 3, 16, 9, 3, 16, block1, block1, false);
			this.generateBox(levelIn, boxIn, 10, 3, 16, 11, 3, 16, block2, block2, false);
			this.generateBox(levelIn, boxIn, 12, 3, 16, 13, 3, 16, block1, block1, false);
			this.generateBox(levelIn, boxIn, 2, 3, 17, 2, 3, 17, block1, block1, false);
			this.generateBox(levelIn, boxIn, 3, 3, 17, 11, 3, 17, block2, block2, false);
			this.generateBox(levelIn, boxIn, 12, 3, 17, 12, 3, 17, block1, block1, false);
			this.generateBox(levelIn, boxIn, 2, 3, 18, 3, 3, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 4, 3, 18, 5, 3, 18, block2, block2, false);
			this.generateBox(levelIn, boxIn, 6, 3, 18, 6, 3, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 7, 3, 18, 7, 3, 18, block2, block2, false);
			this.generateBox(levelIn, boxIn, 8, 3, 18, 8, 3, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 9, 3, 18, 10, 3, 18, block2, block2, false);
			this.generateBox(levelIn, boxIn, 11, 3, 18, 12, 3, 18, block1, block1, false);
			this.generateBox(levelIn, boxIn, 3, 3, 19, 4, 3, 19, block1, block1, false);
			this.generateBox(levelIn, boxIn, 5, 3, 19, 9, 3, 19, block2, block2, false);
			this.generateBox(levelIn, boxIn, 10, 3, 19, 11, 3, 19, block1, block1, false);
			this.generateBox(levelIn, boxIn, 4, 3, 20, 10, 3, 20, block1, block1, false);
			this.placeBlock(levelIn, wall1, 3, 4, 14, boxIn);
			this.placeBlock(levelIn, fence1, 3, 5, 14, boxIn);
			this.placeBlock(levelIn, block3, 3, 6, 14, boxIn);
			this.placeBlock(levelIn, wall1, 11, 4, 14, boxIn);
			this.placeBlock(levelIn, fence1, 11, 5, 14, boxIn);
			this.placeBlock(levelIn, block3, 11, 6, 14, boxIn);
			this.placeBlock(levelIn, slab1, 5, 4, 12, boxIn);
			this.placeBlock(levelIn, stair3, 6, 4, 12, boxIn);
			this.placeBlock(levelIn, stair7, 7, 4, 12, boxIn);
			this.placeBlock(levelIn, stair3, 8, 4, 12, boxIn);
			this.placeBlock(levelIn, slab1, 9, 4, 12, boxIn);
			this.placeBlock(levelIn, stair1, 5, 4, 13, boxIn);
			this.placeBlock(levelIn, block2, 6, 4, 13, boxIn);
			this.placeBlock(levelIn, block2, 7, 4, 13, boxIn);
			this.placeBlock(levelIn, block2, 8, 4, 13, boxIn);
			this.placeBlock(levelIn, stair2, 9, 4, 13, boxIn);
			this.placeBlock(levelIn, stair5, 5, 4, 14, boxIn);
			this.placeBlock(levelIn, block2, 6, 4, 14, boxIn);
			this.placeBlock(levelIn, block1, 7, 4, 14, boxIn);
			this.placeBlock(levelIn, block4, 7, 5, 14, boxIn);
			this.placeBlock(levelIn, block2, 8, 4, 14, boxIn);
			this.placeBlock(levelIn, stair6, 9, 4, 14, boxIn);
			this.placeBlock(levelIn, stair1, 5, 4, 15, boxIn);
			this.placeBlock(levelIn, block2, 6, 4, 15, boxIn);
			this.placeBlock(levelIn, block2, 7, 4, 15, boxIn);
			this.placeBlock(levelIn, block2, 8, 4, 15, boxIn);
			this.placeBlock(levelIn, stair2, 9, 4, 15, boxIn);
			this.placeBlock(levelIn, slab1, 5, 4, 16, boxIn);
			this.placeBlock(levelIn, stair4, 6, 4, 16, boxIn);
			this.placeBlock(levelIn, stair8, 7, 4, 16, boxIn);
			this.placeBlock(levelIn, stair4, 8, 4, 16, boxIn);
			this.placeBlock(levelIn, slab1, 9, 4, 16, boxIn);
			if (this.isNeedingChest[0] && boxIn.isInside(new BlockPos(this.getWorldX(7, 20), this.getWorldY(4), this.getWorldZ(7, 20)))) {
				this.isNeedingChest[0] = false;
				this.createChest(levelIn, boxIn, randomIn, 7, 4, 20, BWChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE_GRAND_THRONE);
			}
			if (this.isNeedingChest[1] && boxIn.isInside(new BlockPos(this.getWorldX(1, 14), this.getWorldY(4), this.getWorldZ(1, 14)))) {
				this.isNeedingChest[1] = false;
				this.createChest(levelIn, boxIn, randomIn, 1, 4, 14, BWChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE_THRONE);
			}
			if (this.isNeedingChest[2] && boxIn.isInside(new BlockPos(this.getWorldX(13, 14), this.getWorldY(4), this.getWorldZ(13, 14)))) {
				this.isNeedingChest[2] = false;
				this.createChest(levelIn, boxIn, randomIn, 13, 4, 14, BWChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE_THRONE);
			}
			//bottom pillars
			this.fillColumnDown(levelIn, block1, 5, 2, 7, boxIn);
			this.placeBlock(levelIn, stair12, 5, 2, 8, boxIn);
			this.placeBlock(levelIn, stair9, 4, 2, 7, boxIn);
			this.placeBlock(levelIn, stair10, 6, 2, 7, boxIn);
			this.fillColumnDown(levelIn, block1, 9, 2, 7, boxIn);
			this.placeBlock(levelIn, stair12, 9, 2, 8, boxIn);
			this.placeBlock(levelIn, stair9, 8, 2, 7, boxIn);
			this.placeBlock(levelIn, stair10, 10, 2, 7, boxIn);
			this.fillColumnDown(levelIn, block1, 2, 2, 10, boxIn);
			this.placeBlock(levelIn, stair12, 2, 2, 11, boxIn);
			this.placeBlock(levelIn, stair11, 2, 2, 9, boxIn);
			this.placeBlock(levelIn, stair9, 1, 2, 10, boxIn);
			this.placeBlock(levelIn, stair10, 3, 2, 10, boxIn);
			this.fillColumnDown(levelIn, block1, 12, 2, 10, boxIn);
			this.placeBlock(levelIn, stair12, 12, 2, 11, boxIn);
			this.placeBlock(levelIn, stair11, 12, 2, 9, boxIn);
			this.placeBlock(levelIn, stair9, 11, 2, 10, boxIn);
			this.placeBlock(levelIn, stair10, 13, 2, 10, boxIn);
			this.fillColumnDown(levelIn, block1, 1, 2, 14, boxIn);
			this.placeBlock(levelIn, stair12, 1, 2, 15, boxIn);
			this.placeBlock(levelIn, stair11, 1, 2, 13, boxIn);
			this.placeBlock(levelIn, stair9, 0, 2, 14, boxIn);
			this.fillColumnDown(levelIn, block1, 13, 2, 14, boxIn);
			this.placeBlock(levelIn, stair12, 13, 2, 15, boxIn);
			this.placeBlock(levelIn, stair11, 13, 2, 13, boxIn);
			this.placeBlock(levelIn, stair10, 14, 2, 14, boxIn);
			this.fillColumnDown(levelIn, block1, 2, 2, 18, boxIn);
			this.placeBlock(levelIn, stair12, 2, 2, 19, boxIn);
			this.placeBlock(levelIn, stair11, 2, 2, 17, boxIn);
			this.placeBlock(levelIn, stair9, 1, 2, 18, boxIn);
			this.placeBlock(levelIn, stair10, 3, 2, 18, boxIn);
			this.fillColumnDown(levelIn, block1, 12, 2, 18, boxIn);
			this.placeBlock(levelIn, stair12, 12, 2, 19, boxIn);
			this.placeBlock(levelIn, stair11, 12, 2, 17, boxIn);
			this.placeBlock(levelIn, stair9, 11, 2, 18, boxIn);
			this.placeBlock(levelIn, stair10, 13, 2, 18, boxIn);
			this.fillColumnDown(levelIn, block1, 7, 2, 20, boxIn);
			this.placeBlock(levelIn, stair12, 7, 2, 21, boxIn);
			this.placeBlock(levelIn, stair9, 6, 2, 20, boxIn);
			this.placeBlock(levelIn, stair10, 8, 2, 20, boxIn);
			//bottom
			this.placeBlock(levelIn, fence1, 6, 2, 10, boxIn);
			this.placeBlock(levelIn, block5, 6, 1, 10, boxIn);
			this.placeBlock(levelIn, fence1, 8, 2, 10, boxIn);
			this.placeBlock(levelIn, block5, 8, 1, 10, boxIn);
			this.placeBlock(levelIn, fence1, 3, 2, 14, boxIn);
			this.placeBlock(levelIn, block5, 3, 1, 14, boxIn);
			this.placeBlock(levelIn, wall1, 7, 2, 14, boxIn);
			this.placeBlock(levelIn, fence1, 7, 1, 14, boxIn);
			this.placeBlock(levelIn, block5, 7, 0, 14, boxIn);
			this.placeBlock(levelIn, fence1, 11, 2, 14, boxIn);
			this.placeBlock(levelIn, block5, 11, 1, 14, boxIn);
			this.placeBlock(levelIn, fence1, 6, 2, 18, boxIn);
			this.placeBlock(levelIn, block5, 6, 1, 18, boxIn);
			this.placeBlock(levelIn, fence1, 8, 2, 18, boxIn);
			this.placeBlock(levelIn, block5, 8, 1, 18, boxIn);
		}
	}

	public static class CastleSmallCorridorLeftTurnPiece extends InfernalNetherFortressPieces.InfernalNetherBridgePiece {
		private boolean isNeedingChest;

		public CastleSmallCorridorLeftTurnPiece(final int componentTypeIn, final RandomSource randomIn, final BoundingBox boxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_CASTLE_SMALL_CORRIDOR_LEFT_TURN.get(), componentTypeIn, boxIn);
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
			this.generateChildLeft((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 0, 1, true);
		}

		public static final InfernalNetherFortressPieces.CastleSmallCorridorLeftTurnPiece createPiece(final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int componentTypeIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -1, 0, 0, 5, 7, 5, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new InfernalNetherFortressPieces.CastleSmallCorridorLeftTurnPiece(componentTypeIn, randomIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState fence1 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true));
			final BlockState fence2 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
			final BlockState slab1 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP);
			final BlockState slab2 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM);
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair5 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair6 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			//air
			this.generateAirBox(levelIn, boxIn, 0, 2, 0, 4, 5, 4);
			//floor
			this.generateBox(levelIn, boxIn, 0, 0, 0, 4, 1, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 2, 0, 0, 2, 1, 2, block2, block2, false);
			this.generateBox(levelIn, boxIn, 0, 0, 2, 2, 1, 2, block2, block2, false);
			//walls
			this.generateBox(levelIn, boxIn, 4, 2, 0, 4, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 4, 2, 1, 4, 5, 1, block2, block2, false);
			this.generateBox(levelIn, boxIn, 4, 3, 1, 4, 4, 1, fence2, fence2, false);
			this.generateBox(levelIn, boxIn, 4, 2, 3, 4, 5, 3, block2, block2, false);
			this.generateBox(levelIn, boxIn, 4, 3, 3, 4, 4, 3, fence2, fence2, false);
			this.generateBox(levelIn, boxIn, 0, 2, 0, 0, 5, 0, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 2, 4, 3, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 1, 2, 4, 1, 5, 4, block2, block2, false);
			this.generateBox(levelIn, boxIn, 1, 3, 4, 1, 4, 4, fence1, fence1, false);
			this.generateBox(levelIn, boxIn, 3, 2, 4, 3, 5, 4, block2, block2, false);
			this.generateBox(levelIn, boxIn, 3, 3, 4, 3, 4, 4, fence1, fence1, false);
			//chest
			if (this.isNeedingChest && boxIn.isInside(new BlockPos(this.getWorldX(3, 3), this.getWorldY(2), this.getWorldZ(3, 3)))) {
				this.isNeedingChest = false;
				this.createChest(levelIn, boxIn, randomIn, 3, 2, 3, BWChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE);
			}
			//roof
			this.generateBox(levelIn, boxIn, 0, 6, 0, 4, 6, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 2, 6, 0, 2, 6, 2, block2, block2, false);
			this.generateBox(levelIn, boxIn, 0, 6, 2, 2, 6, 2, block2, block2, false);
			this.generateBox(levelIn, boxIn, 0, 6, 0, 0, 6, 0, slab2, slab2, false);
			this.generateBox(levelIn, boxIn, 4, 6, 0, 4, 6, 4, slab2, slab2, false);
			this.generateBox(levelIn, boxIn, 0, 6, 4, 4, 6, 4, slab2, slab2, false);
			//top
			this.generateBox(levelIn, boxIn, 1, 5, 1, 1, 5, 1, slab1, slab1, false);
			this.generateBox(levelIn, boxIn, 3, 5, 3, 3, 5, 3, block2, block2, false);
			this.generateBox(levelIn, boxIn, 0, 5, 3, 2, 5, 3, stair1, stair1, false);
			this.generateBox(levelIn, boxIn, 0, 5, 1, 0, 5, 1, stair2, stair2, false);
			this.generateBox(levelIn, boxIn, 3, 5, 0, 3, 5, 2, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 1, 5, 0, 1, 5, 0, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 1, 5, 3, 1, 5, 3, stair5, stair5, false);
			this.generateBox(levelIn, boxIn, 3, 5, 1, 3, 5, 1, stair6, stair6, false);
			//bottom
			this.fillColumnDown(levelIn, block1, 4, -1, 4, boxIn);
			this.placeBlock(levelIn, stair1, 4, -1, 3, boxIn);
			this.placeBlock(levelIn, stair3, 3, -1, 4, boxIn);
		}
	}

	public static class CastleSmallCorridorRightTurnPiece extends InfernalNetherFortressPieces.InfernalNetherBridgePiece {
		private boolean isNeedingChest;

		public CastleSmallCorridorRightTurnPiece(final int componentTypeIn, final RandomSource randomIn, final BoundingBox boxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_CASTLE_SMALL_CORRIDOR_RIGHT_TURN.get(), componentTypeIn, boxIn);
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
			this.generateChildRight((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 0, 1, true);
		}

		public static final InfernalNetherFortressPieces.CastleSmallCorridorRightTurnPiece createPiece(final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int componentTypeIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -1, 0, 0, 5, 7, 5, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new InfernalNetherFortressPieces.CastleSmallCorridorRightTurnPiece(componentTypeIn, randomIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState fence1 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true));
			final BlockState fence2 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
			final BlockState slab1 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP);
			final BlockState slab2 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM);
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair5 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair6 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);
			//air
			this.generateAirBox(levelIn, boxIn, 0, 2, 0, 4, 5, 4);
			//floor
			this.generateBox(levelIn, boxIn, 0, 0, 0, 4, 1, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 2, 0, 0, 2, 1, 2, block2, block2, false);
			this.generateBox(levelIn, boxIn, 2, 0, 2, 4, 1, 2, block2, block2, false);
			//walls
			this.generateBox(levelIn, boxIn, 0, 2, 0, 0, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 2, 1, 0, 5, 1, block2, block2, false);
			this.generateBox(levelIn, boxIn, 0, 3, 1, 0, 4, 1, fence2, fence2, false);
			this.generateBox(levelIn, boxIn, 0, 2, 3, 0, 5, 3, block2, block2, false);
			this.generateBox(levelIn, boxIn, 0, 3, 3, 0, 4, 3, fence2, fence2, false);
			this.generateBox(levelIn, boxIn, 4, 2, 0, 4, 5, 0, block1, block1, false);
			this.generateBox(levelIn, boxIn, 1, 2, 4, 4, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 3, 2, 4, 3, 5, 4, block2, block2, false);
			this.generateBox(levelIn, boxIn, 3, 3, 4, 3, 4, 4, fence1, fence1, false);
			this.generateBox(levelIn, boxIn, 1, 2, 4, 1, 5, 4, block2, block2, false);
			this.generateBox(levelIn, boxIn, 1, 3, 4, 1, 4, 4, fence1, fence1, false);
			//chest
			if (this.isNeedingChest && boxIn.isInside(new BlockPos(this.getWorldX(1, 3), this.getWorldY(2), this.getWorldZ(1, 3)))) {
				this.isNeedingChest = false;
				this.createChest(levelIn, boxIn, randomIn, 1, 2, 3, BWChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE);
			}
			//roof
			this.generateBox(levelIn, boxIn, 0, 6, 0, 4, 6, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 2, 6, 0, 2, 6, 2, block2, block2, false);
			this.generateBox(levelIn, boxIn, 2, 6, 2, 4, 6, 2, block2, block2, false);
			this.generateBox(levelIn, boxIn, 4, 6, 0, 4, 6, 0, slab2, slab2, false);
			this.generateBox(levelIn, boxIn, 0, 6, 0, 0, 6, 4, slab2, slab2, false);
			this.generateBox(levelIn, boxIn, 0, 6, 4, 4, 6, 4, slab2, slab2, false);
			//top
			this.generateBox(levelIn, boxIn, 3, 5, 1, 3, 5, 1, slab1, slab1, false);
			this.generateBox(levelIn, boxIn, 1, 5, 3, 1, 5, 3, block2, block2, false);
			this.generateBox(levelIn, boxIn, 2, 5, 3, 4, 5, 3, stair1, stair1, false);
			this.generateBox(levelIn, boxIn, 4, 5, 1, 4, 5, 1, stair2, stair2, false);
			this.generateBox(levelIn, boxIn, 1, 5, 0, 1, 5, 2, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 3, 5, 0, 3, 5, 0, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 3, 5, 3, 3, 5, 3, stair5, stair5, false);
			this.generateBox(levelIn, boxIn, 1, 5, 1, 1, 5, 1, stair6, stair6, false);
			//bottom
			this.fillColumnDown(levelIn, block1, 0, -1, 4, boxIn);
			this.placeBlock(levelIn, stair1, 0, -1, 3, boxIn);
			this.placeBlock(levelIn, stair3, 1, -1, 4, boxIn);
		}
	}

	public static class CastleCorridorStairsPiece extends InfernalNetherFortressPieces.InfernalNetherBridgePiece {
		public CastleCorridorStairsPiece(final int componentTypeIn, final BoundingBox boxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_CASTLE_CORRIDOR_STAIRS.get(), componentTypeIn, boxIn);
			this.setOrientation(facingIn);
		}

		public CastleCorridorStairsPiece(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_CASTLE_CORRIDOR_STAIRS.get(), compoundIn);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChildForward((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 1, 0, true);
		}

		public static final InfernalNetherFortressPieces.CastleCorridorStairsPiece createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int componentTypeIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -1, -7, 0, 5, 14, 10, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new InfernalNetherFortressPieces.CastleCorridorStairsPiece(componentTypeIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair2 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair4 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair5 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair6 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair7 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState fence1 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
			final BlockState slab1 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState();
			final BlockState slab2 = Blocks.NETHER_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP);
			//stairs
			for (int i = 0; i <= 9; ++i) {
				int j = Math.max(1, 7 - i);
				int k = Math.min(Math.max(j + 5, 14 - i), 13);
				//bottom
				this.generateBox(levelIn, boxIn, 0, j - 1, i, 1, j, i, block1, block1, false);
				this.generateBox(levelIn, boxIn, 2, j - 1, i, 2, j, i, block2, block2, false);
				this.generateBox(levelIn, boxIn, 3, j - 1, i, 4, j, i, block1, block1, false);
				this.generateAirBox(levelIn, boxIn, 1, j + 1, i, 3, k - 1, i);
				if (i <= 6) {
					this.placeBlock(levelIn, stair1, 1, j + 1, i, boxIn);
					this.placeBlock(levelIn, stair2, 2, j + 1, i, boxIn);
					this.placeBlock(levelIn, stair1, 3, j + 1, i, boxIn);
				}
				//top
				if (i < 9 && i > 1) {
					this.generateBox(levelIn, boxIn, 0, k, i, 0, k, i, stair1, stair1, false);
					this.generateBox(levelIn, boxIn, 1, k, i, 1, k, i, block1, block1, false);
					this.generateBox(levelIn, boxIn, 2, k, i, 2, k, i, block2, block2, false);
					this.generateBox(levelIn, boxIn, 3, k, i, 3, k, i, block1, block1, false);
					this.generateBox(levelIn, boxIn, 4, k, i, 4, k, i, stair1, stair1, false);
					if (i == 2) {
						this.generateBox(levelIn, boxIn, 1, k, i - 2, 1, k, i - 2, stair5, stair5, false);
						this.generateBox(levelIn, boxIn, 1, k, i - 1, 1, k, i - 1, block1, block1, false);
						this.generateBox(levelIn, boxIn, 2, k, i - 1, 2, k, i - 1, stair4, stair4, false);
						this.generateBox(levelIn, boxIn, 3, k, i - 1, 3, k, i - 1, block1, block1, false);
						this.generateBox(levelIn, boxIn, 3, k, i - 2, 3, k, i - 2, stair6, stair6, false);
					} else {
						this.generateBox(levelIn, boxIn, 1, k, i - 1, 1, k, i - 1, stair3, stair3, false);
						this.generateBox(levelIn, boxIn, 2, k, i - 1, 2, k, i - 1, stair4, stair4, false);
						this.generateBox(levelIn, boxIn, 3, k, i - 1, 3, k, i - 1, stair3, stair3, false);
					}
				} else {
					if (i == 9) {
						this.generateBox(levelIn, boxIn, 1, k - 1, i, 1, k - 1, i, stair5, stair5, false);
						this.generateBox(levelIn, boxIn, 1, k - 1, i - 1, 1, k - 1, i - 1, slab2, slab2, false);
						this.generateBox(levelIn, boxIn, 3, k - 1, i, 3, k - 1, i, stair6, stair6, false);
						this.generateBox(levelIn, boxIn, 3, k - 1, i - 1, 3, k - 1, i - 1, slab2, slab2, false);
					}
					this.generateBox(levelIn, boxIn, 0, k, i, 0, k, i, slab1, slab1, false);
					this.generateBox(levelIn, boxIn, 1, k, i, 1, k, i, block1, block1, false);
					this.generateBox(levelIn, boxIn, 2, k, i, 2, k, i, block2, block2, false);
					this.generateBox(levelIn, boxIn, 3, k, i, 3, k, i, block1, block1, false);
					this.generateBox(levelIn, boxIn, 4, k, i, 4, k, i, slab1, slab1, false);
				}
				this.generateBox(levelIn, boxIn, 1, k, i + 1, 1, k, i + 1, stair1, stair1, false);
				this.generateBox(levelIn, boxIn, 2, k, i + 1, 2, k, i + 1, stair2, stair2, false);
				this.generateBox(levelIn, boxIn, 3, k, i + 1, 3, k, i + 1, stair1, stair1, false);
				this.generateBox(levelIn, boxIn, 0, j + 1, i, 0, k - 1, i, block1, block1, false);
				this.generateBox(levelIn, boxIn, 4, j + 1, i, 4, k - 1, i, block1, block1, false);
				if ((i & 1) == 0) {
					//fences
					this.generateBox(levelIn, boxIn, 0, j + 1, i, 0, j + 4, i, block2, block2, false);
					this.generateBox(levelIn, boxIn, 4, j + 1, i, 4, j + 4, i, block2, block2, false);
					this.generateBox(levelIn, boxIn, 0, j + 2, i, 0, j + 3, i, fence1, fence1, false);
					this.generateBox(levelIn, boxIn, 4, j + 2, i, 4, j + 3, i, fence1, fence1, false);
				}
				if (i == 9) {
					this.fillColumnDown(levelIn, block1, 0, j - 2, i - 2, boxIn);
					this.placeBlock(levelIn, stair3, 0, j - 2, i - 3, boxIn);
					this.placeBlock(levelIn, stair7, 0, j - 2, i - 1, boxIn);
					this.placeBlock(levelIn, stair5, 1, j - 2, i - 2, boxIn);
					this.fillColumnDown(levelIn, block1, 4, j - 2, i - 2, boxIn);
					this.placeBlock(levelIn, stair3, 4, j - 2, i - 3, boxIn);
					this.placeBlock(levelIn, stair7, 4, j - 2, i - 1, boxIn);
					this.placeBlock(levelIn, stair6, 3, j - 2, i - 2, boxIn);
				}
			}
		}
	}

	public static class CastleCorridorTBalconyPiece extends InfernalNetherFortressPieces.InfernalNetherBridgePiece {
		public CastleCorridorTBalconyPiece(final int componentTypeIn, final BoundingBox boxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_CASTLE_CORRIDOR_T_BALCONY.get(), componentTypeIn, boxIn);
			this.setOrientation(facingIn);
		}

		public CastleCorridorTBalconyPiece(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_CASTLE_CORRIDOR_T_BALCONY.get(), compoundIn);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			int i = 1;
			Direction direction = this.getOrientation();
			if (direction == Direction.WEST || direction == Direction.NORTH)
				i = 5;
			this.generateChildLeft((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 0, i, randomIn.nextInt(8) > 0);
			this.generateChildRight((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 0, i, randomIn.nextInt(8) > 0);
		}

		public static final InfernalNetherFortressPieces.CastleCorridorTBalconyPiece createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int componentTypeIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -3, 0, 0, 9, 7, 9, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new InfernalNetherFortressPieces.CastleCorridorTBalconyPiece(componentTypeIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState block3 = BWBlocks.BLAZE_LANTERN.get().defaultBlockState();
			final BlockState fence1 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true));
			final BlockState fence2 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence3 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true));
			final BlockState fence4 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.EAST, Boolean.valueOf(true)).setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence5 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true)).setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
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
			this.generateBox(levelIn, boxIn, 0, 0, 0, 8, 1, 5, block1, block1, false);
			this.generateAirBox(levelIn, boxIn, 0, 2, 0, 8, 5, 8);
			this.generateBox(levelIn, boxIn, 4, 0, 0, 4, 1, 5, block2, block2, false);
			this.generateBox(levelIn, boxIn, 0, 0, 2, 8, 1, 2, block2, block2, false);
			this.generateBox(levelIn, boxIn, 0, 2, 4, 8, 2, 7, block1, block1, false);
			this.placeBlock(levelIn, stair1, 3, 2, 4, boxIn);
			this.placeBlock(levelIn, stair2, 4, 2, 4, boxIn);
			this.placeBlock(levelIn, stair1, 5, 2, 4, boxIn);
			this.generateBox(levelIn, boxIn, 4, 2, 5, 4, 2, 7, block2, block2, false);
			this.generateBox(levelIn, boxIn, 1, 2, 7, 7, 2, 7, block2, block2, false);
			this.generateBox(levelIn, boxIn, 0, 2, 8, 8, 2, 8, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 0, 1, 7, 8, 1, 7, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 0, 1, 6, 8, 1, 6, stair1, stair1, false);
			this.generateBox(levelIn, boxIn, 0, 0, 6, 8, 0, 6, stair3, stair3, false);
			//top
			this.generateBox(levelIn, boxIn, 0, 6, 0, 8, 6, 5, block1, block1, false);
			this.generateBox(levelIn, boxIn, 4, 6, 0, 4, 6, 5, block2, block2, false);
			this.generateBox(levelIn, boxIn, 0, 6, 2, 8, 6, 2, block2, block2, false);
			this.generateBox(levelIn, boxIn, 0, 6, 0, 2, 6, 0, slab1, slab1, false);
			this.generateBox(levelIn, boxIn, 6, 6, 0, 8, 6, 0, slab1, slab1, false);
			this.generateBox(levelIn, boxIn, 0, 6, 5, 2, 6, 5, slab1, slab1, false);
			this.generateBox(levelIn, boxIn, 6, 6, 5, 8, 6, 5, slab1, slab1, false);
			this.generateBox(levelIn, boxIn, 0, 6, 4, 0, 6, 5, slab1, slab1, false);
			this.generateBox(levelIn, boxIn, 8, 6, 4, 8, 6, 5, slab1, slab1, false);
			this.placeBlock(levelIn, stair3, 0, 5, 1, boxIn);
			this.placeBlock(levelIn, slab3, 0, 5, 3, boxIn);
			this.placeBlock(levelIn, stair4, 1, 5, 1, boxIn);
			this.placeBlock(levelIn, stair3, 2, 5, 1, boxIn);
			this.placeBlock(levelIn, slab3, 3, 5, 1, boxIn);
			this.placeBlock(levelIn, stair5, 3, 5, 0, boxIn);
			this.placeBlock(levelIn, stair6, 5, 5, 0, boxIn);
			this.placeBlock(levelIn, slab3, 5, 5, 1, boxIn);
			this.placeBlock(levelIn, stair3, 6, 5, 1, boxIn);
			this.placeBlock(levelIn, stair4, 7, 5, 1, boxIn);
			this.placeBlock(levelIn, stair3, 8, 5, 1, boxIn);
			this.placeBlock(levelIn, slab3, 8, 5, 3, boxIn);
			this.placeBlock(levelIn, stair5, 3, 5, 5, boxIn);
			this.placeBlock(levelIn, slab3, 3, 5, 4, boxIn);
			this.placeBlock(levelIn, slab3, 2, 5, 4, boxIn);
			this.placeBlock(levelIn, stair6, 5, 5, 5, boxIn);
			this.placeBlock(levelIn, slab3, 5, 5, 4, boxIn);
			this.placeBlock(levelIn, slab3, 6, 5, 4, boxIn);
			//walls
			this.generateBox(levelIn, boxIn, 0, 2, 0, 2, 5, 0, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 2, 0, 8, 5, 0, block1, block1, false);
			this.generateBox(levelIn, boxIn, 1, 2, 0, 1, 5, 0, block2, block2, false);
			this.generateBox(levelIn, boxIn, 1, 3, 0, 1, 4, 0, fence1, fence1, false);
			this.generateBox(levelIn, boxIn, 7, 2, 0, 7, 5, 0, block2, block2, false);
			this.generateBox(levelIn, boxIn, 7, 3, 0, 7, 4, 0, fence1, fence1, false);
			this.generateAirBox(levelIn, boxIn, 1, 1, 4, 2, 2, 4);
			this.generateAirBox(levelIn, boxIn, 6, 1, 4, 7, 2, 4);
			//front
			this.generateBox(levelIn, boxIn, 1, 3, 8, 7, 3, 8, fence3, fence3, false);
			this.placeBlock(levelIn, fence4, 0, 3, 8, boxIn);
			this.placeBlock(levelIn, fence5, 8, 3, 8, boxIn);
			this.placeBlock(levelIn, block3, 0, 4, 8, boxIn);
			this.placeBlock(levelIn, block3, 8, 4, 8, boxIn);
			this.generateBox(levelIn, boxIn, 0, 3, 6, 0, 3, 7, fence2, fence2, false);
			this.generateBox(levelIn, boxIn, 8, 3, 6, 8, 3, 7, fence2, fence2, false);
			this.generateBox(levelIn, boxIn, 0, 3, 4, 0, 5, 5, block1, block1, false);
			this.generateBox(levelIn, boxIn, 8, 3, 4, 8, 5, 5, block1, block1, false);
			this.generateBox(levelIn, boxIn, 1, 3, 5, 2, 5, 5, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 3, 5, 7, 5, 5, block1, block1, false);
			this.generateBox(levelIn, boxIn, 1, 3, 5, 1, 3, 5, block2, block2, false);
			this.generateBox(levelIn, boxIn, 1, 4, 5, 1, 5, 5, fence1, fence1, false);
			this.generateBox(levelIn, boxIn, 1, 6, 5, 1, 6, 5, slab2, slab2, false);
			this.generateBox(levelIn, boxIn, 7, 3, 5, 7, 3, 5, block2, block2, false);
			this.generateBox(levelIn, boxIn, 7, 4, 5, 7, 5, 5, fence1, fence1, false);
			this.generateBox(levelIn, boxIn, 7, 6, 5, 7, 6, 5, slab2, slab2, false);
			//bottom
			this.fillColumnDown(levelIn, block1, 0, -1, 0, boxIn);
			this.placeBlock(levelIn, stair5, 1, -1, 0, boxIn);
			this.placeBlock(levelIn, stair3, 0, -1, 1, boxIn);
			this.fillColumnDown(levelIn, block1, 8, -1, 0, boxIn);
			this.placeBlock(levelIn, stair6, 7, -1, 0, boxIn);
			this.placeBlock(levelIn, stair3, 8, -1, 1, boxIn);
			this.fillColumnDown(levelIn, block1, 0, -1, 5, boxIn);
			this.placeBlock(levelIn, stair5, 1, -1, 5, boxIn);
			this.placeBlock(levelIn, stair7, 0, -1, 4, boxIn);
			this.fillColumnDown(levelIn, block1, 8, -1, 5, boxIn);
			this.placeBlock(levelIn, stair6, 7, -1, 5, boxIn);
			this.placeBlock(levelIn, stair7, 8, -1, 4, boxIn);
		}
	}

	public static class CastleSmallCorridorPiece extends InfernalNetherFortressPieces.InfernalNetherBridgePiece {
		public CastleSmallCorridorPiece(final int componentTypeIn, final BoundingBox boxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_CASTLE_SMALL_CORRIDOR.get(), componentTypeIn, boxIn);
			this.setOrientation(facingIn);
		}

		public CastleSmallCorridorPiece(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_CASTLE_SMALL_CORRIDOR.get(), compoundIn);
		}

		@Override
		public final void addChildren(final StructurePiece pieceIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn) {
			this.generateChildForward((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 1, 0, true);
		}

		public static final InfernalNetherFortressPieces.CastleSmallCorridorPiece createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int componentTypeIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -1, 0, 0, 5, 7, 5, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new InfernalNetherFortressPieces.CastleSmallCorridorPiece(componentTypeIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
			final BlockState block1 = Blocks.RED_NETHER_BRICKS.defaultBlockState();
			final BlockState block2 = Blocks.NETHER_BRICKS.defaultBlockState();
			final BlockState fence1 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
			final BlockState slab1 = Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState();
			final BlockState stair1 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair2 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair3 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair4 = Blocks.RED_NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair5 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP);
			final BlockState stair6 = Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP);
			//floor
			this.generateBox(levelIn, boxIn, 0, 0, 0, 4, 1, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 2, 0, 0, 2, 1, 4, block2, block2, false);
			this.generateAirBox(levelIn, boxIn, 0, 2, 0, 4, 5, 4);
			//walls
			this.generateBox(levelIn, boxIn, 0, 2, 0, 0, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 4, 2, 0, 4, 5, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 0, 2, 1, 0, 5, 1, block2, block2, false);
			this.generateBox(levelIn, boxIn, 0, 3, 1, 0, 4, 1, fence1, fence1, false);
			this.generateBox(levelIn, boxIn, 0, 2, 3, 0, 5, 3, block2, block2, false);
			this.generateBox(levelIn, boxIn, 0, 3, 3, 0, 4, 3, fence1, fence1, false);
			this.generateBox(levelIn, boxIn, 4, 2, 1, 4, 5, 1, block2, block2, false);
			this.generateBox(levelIn, boxIn, 4, 3, 1, 4, 4, 1, fence1, fence1, false);
			this.generateBox(levelIn, boxIn, 4, 2, 3, 4, 5, 3, block2, block2, false);
			this.generateBox(levelIn, boxIn, 4, 3, 3, 4, 4, 3, fence1, fence1, false);
			//top
			this.generateBox(levelIn, boxIn, 0, 6, 0, 4, 6, 4, block1, block1, false);
			this.generateBox(levelIn, boxIn, 2, 6, 0, 2, 6, 4, block2, block2, false);
			this.generateBox(levelIn, boxIn, 0, 6, 0, 0, 6, 4, slab1, slab1, false);
			this.generateBox(levelIn, boxIn, 4, 6, 0, 4, 6, 4, slab1, slab1, false);
			this.placeBlock(levelIn, stair3, 1, 5, 0, boxIn);
			this.placeBlock(levelIn, stair5, 1, 5, 1, boxIn);
			this.placeBlock(levelIn, stair3, 1, 5, 2, boxIn);
			this.placeBlock(levelIn, stair5, 1, 5, 3, boxIn);
			this.placeBlock(levelIn, stair3, 1, 5, 4, boxIn);
			this.placeBlock(levelIn, stair4, 3, 5, 0, boxIn);
			this.placeBlock(levelIn, stair6, 3, 5, 1, boxIn);
			this.placeBlock(levelIn, stair4, 3, 5, 2, boxIn);
			this.placeBlock(levelIn, stair6, 3, 5, 3, boxIn);
			this.placeBlock(levelIn, stair4, 3, 5, 4, boxIn);
			//bottom
			this.fillColumnDown(levelIn, block1, 0, -1, 2, boxIn);
			this.placeBlock(levelIn, stair1, 0, -1, 3, boxIn);
			this.placeBlock(levelIn, stair2, 0, -1, 1, boxIn);
			this.placeBlock(levelIn, stair3, 1, -1, 2, boxIn);
			this.fillColumnDown(levelIn, block1, 4, -1, 2, boxIn);
			this.placeBlock(levelIn, stair1, 4, -1, 3, boxIn);
			this.placeBlock(levelIn, stair2, 4, -1, 1, boxIn);
			this.placeBlock(levelIn, stair4, 3, -1, 2, boxIn);
		}
	}

	public static class CastleStalkRoom extends InfernalNetherFortressPieces.InfernalNetherBridgePiece {
		private boolean hasPlacedSpawner;
		private boolean isNeedingChest;

		public CastleStalkRoom(final int componentTypeIn, final BoundingBox boxIn, final Direction facingIn) {
			super(BWStructurePieceTypes.INF_CASTLE_STALK_ROOM.get(), componentTypeIn, boxIn);
			this.setOrientation(facingIn);
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
			this.generateChildForward((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 5, 3, true);
			this.generateChildForward((InfernalNetherFortressPieces.StartPiece)pieceIn, piecesIn, randomIn, 5, 11, true);
		}

		public static final InfernalNetherFortressPieces.CastleStalkRoom createPiece(final StructurePieceAccessor piecesIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int componentTypeIn) {
			final BoundingBox box = BoundingBox.orientBox(xIn, yIn, zIn, -5, -3, 0, 13, 14, 13, facingIn);
			return isOkBox(box) && piecesIn.findCollisionPiece(box) == null ? new InfernalNetherFortressPieces.CastleStalkRoom(componentTypeIn, box, facingIn) : null;
		}

		@Override
		public final void postProcess(final WorldGenLevel levelIn, final StructureManager managerIn, final ChunkGenerator generatorIn, final RandomSource randomIn, final BoundingBox boxIn, final ChunkPos chunkPosIn, final BlockPos posIn) {
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
			final BlockState fence1 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true));
			final BlockState fence2 = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence3 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true));
			final BlockState fence4 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.SOUTH, Boolean.valueOf(true));
			final BlockState fence5 = fence2.setValue(FenceBlock.WEST, Boolean.valueOf(true));
			final BlockState fence6 = fence2.setValue(FenceBlock.EAST, Boolean.valueOf(true));
			final BlockState fence7 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true));
			final BlockState fence8 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.SOUTH, Boolean.valueOf(true)).setValue(FenceBlock.EAST, Boolean.valueOf(true));
			final BlockState fence9 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.SOUTH, Boolean.valueOf(true)).setValue(FenceBlock.WEST, Boolean.valueOf(true));
			final BlockState fence10 = BWBlocks.RED_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, Boolean.valueOf(true)).setValue(FenceBlock.WEST, Boolean.valueOf(true));
			//outLine
			this.generateBox(levelIn, boxIn, 0, 3, 0, 12, 4, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 3, 0, 6, 4, 12, block2, block2, false);
			this.generateBox(levelIn, boxIn, 3, 3, 3, 9, 4, 3, block2, block2, false);
			this.generateBox(levelIn, boxIn, 9, 3, 3, 9, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boxIn, 3, 3, 3, 3, 4, 9, block2, block2, false);
			this.generateBox(levelIn, boxIn, 3, 3, 9, 9, 4, 9, block2, block2, false);
			this.generateAirBox(levelIn, boxIn, 0, 5, 0, 12, 13, 12);
			this.generateBox(levelIn, boxIn, 0, 5, 0, 1, 12, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 11, 5, 0, 12, 12, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 2, 5, 11, 4, 12, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 8, 5, 11, 10, 12, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 5, 9, 11, 7, 12, 12, block1, block1, false);
			this.generateBox(levelIn, boxIn, 2, 5, 0, 4, 12, 1, block1, block1, false);
			this.generateBox(levelIn, boxIn, 8, 5, 0, 10, 12, 1, block1, block1, false);
			this.generateBox(levelIn, boxIn, 5, 9, 0, 7, 12, 1, block1, block1, false);
			this.generateBox(levelIn, boxIn, 2, 11, 2, 10, 12, 10, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 11, 3, 6, 11, 9, block2, block2, false);
			this.generateBox(levelIn, boxIn, 3, 11, 3, 9, 11, 3, block2, block2, false);
			this.generateBox(levelIn, boxIn, 9, 11, 3, 9, 11, 9, block2, block2, false);
			this.generateBox(levelIn, boxIn, 3, 11, 3, 3, 11, 9, block2, block2, false);
			this.generateBox(levelIn, boxIn, 3, 11, 9, 9, 11, 9, block2, block2, false);
			this.generateBox(levelIn, boxIn, 6, 12, 4, 6, 12, 5, block2, block2, false);
			this.generateBox(levelIn, boxIn, 6, 12, 7, 6, 12, 8, block2, block2, false);
			this.generateBox(levelIn, boxIn, 4, 12, 6, 5, 12, 6, block2, block2, false);
			this.generateBox(levelIn, boxIn, 7, 12, 6, 8, 12, 6, block2, block2, false);
			this.generateBox(levelIn, boxIn, 4, 12, 3, 8, 12, 3, block2, block2, false);
			this.generateBox(levelIn, boxIn, 4, 12, 9, 8, 12, 9, block2, block2, false);
			this.generateBox(levelIn, boxIn, 3, 12, 4, 3, 12, 8, block2, block2, false);
			this.generateBox(levelIn, boxIn, 9, 12, 4, 9, 12, 8, block2, block2, false);
			this.generateBox(levelIn, boxIn, 5, 12, 5, 5, 12, 5, block2, block2, false);
			this.generateBox(levelIn, boxIn, 5, 12, 7, 5, 12, 7, block2, block2, false);
			this.generateBox(levelIn, boxIn, 7, 12, 5, 7, 12, 5, block2, block2, false);
			this.generateBox(levelIn, boxIn, 7, 12, 7, 7, 12, 7, block2, block2, false);
			this.generateBox(levelIn, boxIn, 5, 8, 0, 5, 8, 1, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 7, 8, 0, 7, 8, 1, stair4, stair4, false);
			this.placeBlock(levelIn, block3, 6, 9, 1, boxIn);
			this.generateBox(levelIn, boxIn, 5, 8, 12, 5, 8, 12, stair3, stair3, false);
			this.generateBox(levelIn, boxIn, 7, 8, 12, 7, 8, 12, stair4, stair4, false);
			this.generateBox(levelIn, boxIn, 5, 8, 11, 5, 8, 11, block1, block1, false);
			this.generateBox(levelIn, boxIn, 6, 8, 11, 6, 8, 11, stair9, stair9, false);
			this.generateBox(levelIn, boxIn, 7, 8, 11, 7, 8, 11, block1, block1, false);
			//walls
			for (int i = 1; i <= 11; i += 2) {
				this.generateBox(levelIn, boxIn, i, 9, 0, i, 12, 0, block2, block2, false);
				this.generateBox(levelIn, boxIn, i, 10, 0, i, 11, 0, fence1, fence1, false);
				this.generateBox(levelIn, boxIn, i, 9, 12, i, 12, 12, block2, block2, false);
				this.generateBox(levelIn, boxIn, i, 10, 12, i, 11, 12, fence1, fence1, false);
				this.generateBox(levelIn, boxIn, 0, 9, i, 0, 12, i, block2, block2, false);
				this.generateBox(levelIn, boxIn, 0, 10, i, 0, 11, i, fence2, fence2, false);
				this.generateBox(levelIn, boxIn, 12, 9, i, 12, 12, i, block2, block2, false);
				this.generateBox(levelIn, boxIn, 12, 10, i, 12, 11, i, fence2, fence2, false);
				this.placeBlock(levelIn, block2, i, 13, 0, boxIn);
				this.placeBlock(levelIn, block2, i, 13, 12, boxIn);
				this.placeBlock(levelIn, block2, 0, 13, i, boxIn);
				this.placeBlock(levelIn, block2, 12, 13, i, boxIn);
				if (i != 11) {
					this.placeBlock(levelIn, fence3, i + 1, 13, 0, boxIn);
					this.placeBlock(levelIn, fence3, i + 1, 13, 12, boxIn);
					this.placeBlock(levelIn, fence4, 0, 13, i + 1, boxIn);
					this.placeBlock(levelIn, fence4, 12, 13, i + 1, boxIn);
				}
			}
			this.placeBlock(levelIn, fence7, 0, 13, 0, boxIn);
			this.placeBlock(levelIn, fence8, 0, 13, 12, boxIn);
			this.placeBlock(levelIn, fence9, 12, 13, 12, boxIn);
			this.placeBlock(levelIn, fence10, 12, 13, 0, boxIn);
			for (int j1 = 3; j1 <= 9; j1 += 2) {
				this.generateBox(levelIn, boxIn, 1, 6, j1, 1, 9, j1, block2, block2, false);
				this.generateBox(levelIn, boxIn, 1, 7, j1, 1, 8, j1, fence5, fence5, false);
				this.generateBox(levelIn, boxIn, 11, 6, j1, 11, 9, j1, block2, block2, false);
				this.generateBox(levelIn, boxIn, 11, 7, j1, 11, 8, j1, fence6, fence6, false);
			}
			//stairs
			for (int j = 0; j <= 6; ++j) {
				int k = j + 4;
				this.placeBlock(levelIn, stair7, 5, 5 + j, k, boxIn);
				this.placeBlock(levelIn, stair8, 6, 5 + j, k, boxIn);
				this.placeBlock(levelIn, stair7, 7, 5 + j, k, boxIn);
				if (k >= 5 && k <= 8) {
					this.generateBox(levelIn, boxIn, 5, 5, k, 5, j + 4, k, block1, block1, false);
					this.generateBox(levelIn, boxIn, 6, 5, k, 6, j + 4, k, block2, block2, false);
					this.generateBox(levelIn, boxIn, 7, 5, k, 7, j + 4, k, block1, block1, false);
				}
				else if (k >= 9 && k <= 10) {
					this.generateBox(levelIn, boxIn, 5, 8, k, 5, j + 4, k, block1, block1, false);
					this.generateBox(levelIn, boxIn, 6, 8, k, 6, j + 4, k, block2, block2, false);
					this.generateBox(levelIn, boxIn, 7, 8, k, 7, j + 4, k, block1, block1, false);
				}
				if (j >= 1)
					this.generateAirBox(levelIn, boxIn, 5, 6 + j, k, 7, 9 + j, k);
			}
			this.placeBlock(levelIn, stair7, 5, 12, 11, boxIn);
			this.placeBlock(levelIn, stair8, 6, 12, 11, boxIn);
			this.placeBlock(levelIn, stair7, 7, 12, 11, boxIn);
			this.placeBlock(levelIn, block2, 6, 12, 12, boxIn);
			this.placeBlock(levelIn, fence10, 4, 13, 12, boxIn);
			this.placeBlock(levelIn, fence7, 8, 13, 12, boxIn);
			this.placeBlock(levelIn, block3, 6, 7, 8, boxIn);
			this.generateAirBox(levelIn, boxIn, 6, 5, 8, 6, 6, 8);
			if (this.isNeedingChest && boxIn.isInside(new BlockPos(this.getWorldX(1, 3), this.getWorldY(2), this.getWorldZ(1, 3)))) {
				this.isNeedingChest = false;
				this.createChest(levelIn, boxIn, randomIn, 6, 5, 8, BWChestLoot.CHESTS_INFERNAL_NETHER_BRIDGE_STALK_ROOM);
			}
			if (!this.hasPlacedSpawner) {
				final BlockPos blockPos = new BlockPos(this.getWorldX(6, 7), this.getWorldY(5), this.getWorldZ(6, 7));
				if (boxIn.isInside(blockPos)) {
					this.hasPlacedSpawner = true;
					levelIn.setBlock(blockPos, Blocks.SPAWNER.defaultBlockState(), 2);
					final BlockEntity spawner = levelIn.getBlockEntity(blockPos);
					if (spawner instanceof SpawnerBlockEntity)
						((SpawnerBlockEntity)spawner).setEntityId(EntityType.SKELETON, randomIn);
				}
			}
			this.generateBox(levelIn, boxIn, 5, 5, 7, 5, 5, 7, block2, block2, false);
			this.generateBox(levelIn, boxIn, 5, 6, 7, 5, 7, 7, fence6, fence6, false);
			this.generateBox(levelIn, boxIn, 5, 8, 7, 5, 8, 7, stair8, stair8, false);
			this.generateBox(levelIn, boxIn, 7, 5, 7, 7, 5, 7, block2, block2, false);
			this.generateBox(levelIn, boxIn, 7, 6, 7, 7, 7, 7, fence5, fence5, false);
			this.generateBox(levelIn, boxIn, 7, 8, 7, 7, 8, 7, stair8, stair8, false);
			this.generateAirBox(levelIn, boxIn, 5, 13, 12, 7, 13, 12);
			this.placeBlock(levelIn, stair9, 5, 12, 7, boxIn);
			this.placeBlock(levelIn, stair9, 6, 12, 7, boxIn);
			this.placeBlock(levelIn, stair9, 7, 12, 7, boxIn);
			this.placeBlock(levelIn, stair1, 5, 11, 6, boxIn);
			this.placeBlock(levelIn, stair9, 6, 11, 6, boxIn);
			this.placeBlock(levelIn, stair1, 7, 11, 6, boxIn);
			this.placeBlock(levelIn, stair6, 5, 8, 9, boxIn);
			this.placeBlock(levelIn, stair4, 5, 8, 10, boxIn);
			this.placeBlock(levelIn, stair5, 7, 8, 9, boxIn);
			this.placeBlock(levelIn, stair3, 7, 8, 10, boxIn);
			//farm
			this.generateBox(levelIn, boxIn, 2, 5, 2, 3, 5, 3, block1, block1, false);
			this.placeBlock(levelIn, block2, 3, 5, 3, boxIn);
			this.generateBox(levelIn, boxIn, 2, 5, 9, 3, 5, 10, block1, block1, false);
			this.placeBlock(levelIn, block2, 3, 5, 9, boxIn);
			this.generateBox(levelIn, boxIn, 2, 5, 4, 2, 5, 8, block1, block1, false);
			this.generateBox(levelIn, boxIn, 9, 5, 2, 10, 5, 3, block1, block1, false);
			this.placeBlock(levelIn, block2, 9, 5, 3, boxIn);
			this.generateBox(levelIn, boxIn, 9, 5, 9, 10, 5, 10, block1, block1, false);
			this.placeBlock(levelIn, block2, 9, 5, 9, boxIn);
			this.generateBox(levelIn, boxIn, 10, 5, 4, 10, 5, 8, block1, block1, false);
			this.placeBlock(levelIn, stair11, 4, 5, 2, boxIn);
			this.placeBlock(levelIn, stair13, 4, 5, 3, boxIn);
			this.placeBlock(levelIn, stair13, 4, 5, 9, boxIn);
			this.placeBlock(levelIn, stair11, 4, 5, 10, boxIn);
			this.placeBlock(levelIn, stair10, 8, 5, 2, boxIn);
			this.placeBlock(levelIn, stair12, 8, 5, 3, boxIn);
			this.placeBlock(levelIn, stair12, 8, 5, 9, boxIn);
			this.placeBlock(levelIn, stair10, 8, 5, 10, boxIn);
			this.generateBox(levelIn, boxIn, 3, 4, 4, 4, 4, 8, block4, block4, false);
			this.generateBox(levelIn, boxIn, 8, 4, 4, 9, 4, 8, block4, block4, false);
			this.generateBox(levelIn, boxIn, 3, 5, 4, 4, 5, 8, block5, block5, false);
			this.generateBox(levelIn, boxIn, 8, 5, 4, 9, 5, 8, block5, block5, false);
			//base pillars
			this.fillColumnDown(levelIn, block1, 0, 2, 0, boxIn);
			this.fillColumnDown(levelIn, block1, 0, 2, 12, boxIn);
			this.fillColumnDown(levelIn, block1, 12, 2, 0, boxIn);
			this.fillColumnDown(levelIn, block1, 12, 2, 12, boxIn);
			this.fillColumnDown(levelIn, block1, 0, 1, 6, boxIn);
			this.fillColumnDown(levelIn, block1, 6, 1, 0, boxIn);
			this.fillColumnDown(levelIn, block1, 6, 1, 12, boxIn);
			this.fillColumnDown(levelIn, block1, 12, 1, 6, boxIn);
			this.placeBlock(levelIn, block6, 0, 2, 6, boxIn);
			this.placeBlock(levelIn, block6, 6, 2, 0, boxIn);
			this.placeBlock(levelIn, block6, 6, 2, 12, boxIn);
			this.placeBlock(levelIn, block6, 12, 2, 6, boxIn);
			//pillars x
			this.placeBlock(levelIn, stair4, 11, 0, 0, boxIn);
			this.placeBlock(levelIn, stair11, 11, 1, 0, boxIn);
			this.placeBlock(levelIn, stair4, 10, 1, 0, boxIn);
			this.placeBlock(levelIn, stair11, 10, 2, 0, boxIn);
			this.placeBlock(levelIn, stair4, 9, 2, 0, boxIn);
			this.placeBlock(levelIn, stair4, 11, 0, 12, boxIn);
			this.placeBlock(levelIn, stair11, 11, 1, 12, boxIn);
			this.placeBlock(levelIn, stair4, 10, 1, 12, boxIn);
			this.placeBlock(levelIn, stair11, 10, 2, 12, boxIn);
			this.placeBlock(levelIn, stair4, 9, 2, 12, boxIn);
			this.placeBlock(levelIn, stair4, 5, 1, 0, boxIn);
			this.placeBlock(levelIn, stair11, 5, 2, 0, boxIn);
			this.placeBlock(levelIn, stair4, 4, 2, 0, boxIn);
			this.placeBlock(levelIn, stair4, 5, 1, 12, boxIn);
			this.placeBlock(levelIn, stair11, 5, 2, 12, boxIn);
			this.placeBlock(levelIn, stair4, 4, 2, 12, boxIn);
			this.placeBlock(levelIn, stair3, 1, 0, 0, boxIn);
			this.placeBlock(levelIn, stair10, 1, 1, 0, boxIn);
			this.placeBlock(levelIn, stair3, 2, 1, 0, boxIn);
			this.placeBlock(levelIn, stair10, 2, 2, 0, boxIn);
			this.placeBlock(levelIn, stair3, 3, 2, 0, boxIn);
			this.placeBlock(levelIn, stair3, 1, 0, 12, boxIn);
			this.placeBlock(levelIn, stair10, 1, 1, 12, boxIn);
			this.placeBlock(levelIn, stair3, 2, 1, 12, boxIn);
			this.placeBlock(levelIn, stair10, 2, 2, 12, boxIn);
			this.placeBlock(levelIn, stair3, 3, 2, 12, boxIn);
			this.placeBlock(levelIn, stair3, 7, 1, 0, boxIn);
			this.placeBlock(levelIn, stair10, 7, 2, 0, boxIn);
			this.placeBlock(levelIn, stair3, 8, 2, 0, boxIn);
			this.placeBlock(levelIn, stair3, 7, 1, 12, boxIn);
			this.placeBlock(levelIn, stair10, 7, 2, 12, boxIn);
			this.placeBlock(levelIn, stair3, 8, 2, 12, boxIn);
			//pillars z
			this.placeBlock(levelIn, stair2, 0, 0, 11, boxIn);
			this.placeBlock(levelIn, stair14, 0, 1, 11, boxIn);
			this.placeBlock(levelIn, stair2, 0, 1, 10, boxIn);
			this.placeBlock(levelIn, stair14, 0, 2, 10, boxIn);
			this.placeBlock(levelIn, stair2, 0, 2, 9, boxIn);
			this.placeBlock(levelIn, stair2, 12, 0, 11, boxIn);
			this.placeBlock(levelIn, stair14, 12, 1, 11, boxIn);
			this.placeBlock(levelIn, stair2, 12, 1, 10, boxIn);
			this.placeBlock(levelIn, stair14, 12, 2, 10, boxIn);
			this.placeBlock(levelIn, stair2, 12, 2, 9, boxIn);
			this.placeBlock(levelIn, stair2, 0, 1, 5, boxIn);
			this.placeBlock(levelIn, stair14, 0, 2, 5, boxIn);
			this.placeBlock(levelIn, stair2, 0, 2, 4, boxIn);
			this.placeBlock(levelIn, stair2, 12, 1, 5, boxIn);
			this.placeBlock(levelIn, stair14, 12, 2, 5, boxIn);
			this.placeBlock(levelIn, stair2, 12, 2, 4, boxIn);
			this.placeBlock(levelIn, stair1, 0, 0, 1, boxIn);
			this.placeBlock(levelIn, stair7, 0, 1, 1, boxIn);
			this.placeBlock(levelIn, stair1, 0, 1, 2, boxIn);
			this.placeBlock(levelIn, stair7, 0, 2, 2, boxIn);
			this.placeBlock(levelIn, stair1, 0, 2, 3, boxIn);
			this.placeBlock(levelIn, stair1, 12, 0, 1, boxIn);
			this.placeBlock(levelIn, stair7, 12, 1, 1, boxIn);
			this.placeBlock(levelIn, stair1, 12, 1, 2, boxIn);
			this.placeBlock(levelIn, stair7, 12, 2, 2, boxIn);
			this.placeBlock(levelIn, stair1, 12, 2, 3, boxIn);
			this.placeBlock(levelIn, stair1, 0, 1, 7, boxIn);
			this.placeBlock(levelIn, stair7, 0, 2, 7, boxIn);
			this.placeBlock(levelIn, stair1, 0, 2, 8, boxIn);
			this.placeBlock(levelIn, stair1, 12, 1, 7, boxIn);
			this.placeBlock(levelIn, stair7, 12, 2, 7, boxIn);
			this.placeBlock(levelIn, stair1, 12, 2, 8, boxIn);
		}
	}

	private abstract static class InfernalNetherBridgePiece extends StructurePiece {
		protected InfernalNetherBridgePiece(final StructurePieceType structurePieceTypeIn, final int componentTypeIn, final BoundingBox boundingBoxIn) {
			super(structurePieceTypeIn, componentTypeIn, boundingBoxIn);
		}

		public InfernalNetherBridgePiece(final StructurePieceType structurePieceTypeIn, final CompoundTag compoundIn) {
			super(structurePieceTypeIn, compoundIn);
		}

		@Override
		protected void placeBlock(final WorldGenLevel levelIn, BlockState stateIn, final int xIn, final int yIn, final int zIn, final BoundingBox boxIn) {
			if ((stateIn == Blocks.NETHER_BRICKS.defaultBlockState() || stateIn == Blocks.RED_NETHER_BRICKS.defaultBlockState()) && levelIn.getRandom().nextInt(112) == 0)
				stateIn = Blocks.AIR.defaultBlockState();
			super.placeBlock(levelIn, stateIn, xIn, yIn, zIn, boxIn);
		}

		@Override
		protected void addAdditionalSaveData(final StructurePieceSerializationContext contextIn, final CompoundTag compoundIn) {}

		private final int updatePieceWeight(final List<InfernalNetherFortressPieces.PieceWeight> weightsIn) {
			boolean flag = false;
			int i = 0;
			for(final InfernalNetherFortressPieces.PieceWeight pieceWeight : weightsIn) {
				if (pieceWeight.maxPlaceCount > 0 && pieceWeight.placeCount < pieceWeight.maxPlaceCount)
					flag = true;
				i += pieceWeight.weight;
			}
			return flag ? i : -1;
		}

		private final InfernalNetherFortressPieces.InfernalNetherBridgePiece generatePiece(final InfernalNetherFortressPieces.StartPiece startIn, final List<InfernalNetherFortressPieces.PieceWeight> weightsIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final int zIn, final Direction facingIn, final int componentTypeIn) {
			final int i = this.updatePieceWeight(weightsIn);
			final boolean flag = i > 0 && componentTypeIn <= 30;
			int j = 0;
			while(j < 5 && flag) {
				++j;
				int k = randomIn.nextInt(i);
				for(final InfernalNetherFortressPieces.PieceWeight pieceWeight : weightsIn) {
					k -= pieceWeight.weight;
					if (k < 0) {
						if (!pieceWeight.doPlace() || pieceWeight == startIn.previousPiece && !pieceWeight.allowInRow)
							break;
						final InfernalNetherFortressPieces.InfernalNetherBridgePiece piece = InfernalNetherFortressPieces.findAndCreateBridgePieceFactory(pieceWeight, piecesIn, randomIn, xIn, yIn, zIn, facingIn, componentTypeIn);
						if (piece != null) {
							++pieceWeight.placeCount;
							startIn.previousPiece = pieceWeight;
							if (!pieceWeight.isValid())
								weightsIn.remove(pieceWeight);
							return piece;
						}
					}
				}
			}
			return InfernalNetherFortressPieces.BridgeEndFiller.createPiece(piecesIn, randomIn, xIn, yIn, zIn, facingIn, componentTypeIn);
		}

		private final StructurePiece generateAndAddPiece(final InfernalNetherFortressPieces.StartPiece startIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final int zIn, final @Nullable Direction facingIn, final int componentTypeIn, final boolean secondaryIn) {
			if (Math.abs(xIn - startIn.getBoundingBox().minX()) <= 112 && Math.abs(zIn - startIn.getBoundingBox().minZ()) <= 112) {
				List<InfernalNetherFortressPieces.PieceWeight> list = startIn.availableBridgePieces;
				if (secondaryIn)
					list = startIn.availableCastlePieces;
				final StructurePiece structurePiece = this.generatePiece(startIn, list, piecesIn, randomIn, xIn, yIn, zIn, facingIn, componentTypeIn + 1);
				if (structurePiece != null) {
					piecesIn.addPiece(structurePiece);
					startIn.pendingChildren.add(structurePiece);
				}
				return structurePiece;
			} else {
				return InfernalNetherFortressPieces.BridgeEndFiller.createPiece(piecesIn, randomIn, xIn, yIn, zIn, facingIn, componentTypeIn);
			}
		}

		@Nullable
		protected StructurePiece generateChildForward(final InfernalNetherFortressPieces.StartPiece startIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final boolean secondaryIn) {
			final Direction direction = this.getOrientation();
			if (direction != null) {
				switch (direction) {
				case NORTH:
					return this.generateAndAddPiece(startIn, piecesIn, randomIn, this.boundingBox.minX() + xIn, this.boundingBox.minY() + yIn, this.boundingBox.minZ() - 1, direction, this.getGenDepth(), secondaryIn);
				case SOUTH:
					return this.generateAndAddPiece(startIn, piecesIn, randomIn, this.boundingBox.minX() + xIn, this.boundingBox.minY() + yIn, this.boundingBox.maxZ() + 1, direction, this.getGenDepth(), secondaryIn);
				case WEST:
					return this.generateAndAddPiece(startIn, piecesIn, randomIn, this.boundingBox.minX() - 1, this.boundingBox.minY() + yIn, this.boundingBox.minZ() + xIn, direction, this.getGenDepth(), secondaryIn);
				case EAST:
					return this.generateAndAddPiece(startIn, piecesIn, randomIn, this.boundingBox.maxX() + 1, this.boundingBox.minY() + yIn, this.boundingBox.minZ() + xIn, direction, this.getGenDepth(), secondaryIn);
				default:
					break;
				}
			}
			return null;
		}

		@Nullable
		protected StructurePiece generateChildLeft(final InfernalNetherFortressPieces.StartPiece startIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final boolean secondaryIn) {
			final Direction direction = this.getOrientation();
			if (direction != null) {
				switch (direction) {
				case NORTH:
					return this.generateAndAddPiece(startIn, piecesIn, randomIn, this.boundingBox.minX() - 1, this.boundingBox.minY() + xIn, this.boundingBox.minZ() + yIn, Direction.WEST, this.getGenDepth(), secondaryIn);
				case SOUTH:
					return this.generateAndAddPiece(startIn, piecesIn, randomIn, this.boundingBox.minX() - 1, this.boundingBox.minY() + xIn, this.boundingBox.minZ() + yIn, Direction.WEST, this.getGenDepth(), secondaryIn);
				case WEST:
					return this.generateAndAddPiece(startIn, piecesIn, randomIn, this.boundingBox.minX() + yIn, this.boundingBox.minY() + xIn, this.boundingBox.minZ() - 1, Direction.NORTH, this.getGenDepth(), secondaryIn);
				case EAST:
					return this.generateAndAddPiece(startIn, piecesIn, randomIn, this.boundingBox.minX() + yIn, this.boundingBox.minY() + xIn, this.boundingBox.minZ() - 1, Direction.NORTH, this.getGenDepth(), secondaryIn);
				default:
					break;
				}
			}
			return null;
		}

		@Nullable
		protected StructurePiece generateChildRight(final InfernalNetherFortressPieces.StartPiece startIn, final StructurePieceAccessor piecesIn, final RandomSource randomIn, final int xIn, final int yIn, final boolean secondaryIn) {
			final Direction direction = this.getOrientation();
			if (direction != null) {
				switch (direction) {
				case NORTH:
					return this.generateAndAddPiece(startIn, piecesIn, randomIn, this.boundingBox.maxX() + 1, this.boundingBox.minY() + xIn, this.boundingBox.minZ() + yIn, Direction.EAST, this.getGenDepth(), secondaryIn);
				case SOUTH:
					return this.generateAndAddPiece(startIn, piecesIn, randomIn, this.boundingBox.maxX() + 1, this.boundingBox.minY() + xIn, this.boundingBox.minZ() + yIn, Direction.EAST, this.getGenDepth(), secondaryIn);
				case WEST:
					return this.generateAndAddPiece(startIn, piecesIn, randomIn, this.boundingBox.minX() + yIn, this.boundingBox.minY() + xIn, this.boundingBox.maxZ() + 1, Direction.SOUTH, this.getGenDepth(), secondaryIn);
				case EAST:
					return this.generateAndAddPiece(startIn, piecesIn, randomIn, this.boundingBox.minX() + yIn, this.boundingBox.minY() + xIn, this.boundingBox.maxZ() + 1, Direction.SOUTH, this.getGenDepth(), secondaryIn);
				default:
					break;
				}
			}
			return null;
		}

		protected static final boolean isOkBox(final BoundingBox boxIn) {
			return boxIn != null && boxIn.minY() > 10;
		}
	}

	private static final class PieceWeight {
		public final Class<? extends InfernalNetherFortressPieces.InfernalNetherBridgePiece> weightClass;
		public final int weight;
		public int placeCount;
		public final int maxPlaceCount;
		public final boolean allowInRow;

		public PieceWeight(final Class<? extends InfernalNetherFortressPieces.InfernalNetherBridgePiece> weightClassIn, final int weightIn, final int maxPlaceCountIn, final boolean allowInRowIn) {
			this.weightClass = weightClassIn;
			this.weight = weightIn;
			this.maxPlaceCount = maxPlaceCountIn;
			this.allowInRow = allowInRowIn;
		}

		public PieceWeight(final Class<? extends InfernalNetherFortressPieces.InfernalNetherBridgePiece> weightClassIn, final int weightIn, final int maxPlaceCountIn) {
			this(weightClassIn, weightIn, maxPlaceCountIn, false);
		}

		public final boolean doPlace() {
			return this.maxPlaceCount == 0 || this.placeCount < this.maxPlaceCount;
		}

		public final boolean isValid() {
			return this.maxPlaceCount == 0 || this.placeCount < this.maxPlaceCount;
		}
	}

	public static final class StartPiece extends InfernalNetherFortressPieces.BridgeCrossing1 {
		public InfernalNetherFortressPieces.PieceWeight previousPiece;
		public ArrayList<InfernalNetherFortressPieces.PieceWeight> availableBridgePieces;
		public ArrayList<InfernalNetherFortressPieces.PieceWeight> availableCastlePieces;
		public final ArrayList<StructurePiece> pendingChildren = new ArrayList<>();

		public StartPiece(final RandomSource randomIn, final int xIn, final int zIn) {
			super(xIn, zIn, getRandomHorizontalDirection(randomIn));
			this.availableBridgePieces = new ArrayList<>();
			for (final InfernalNetherFortressPieces.PieceWeight pieceWeight : InfernalNetherFortressPieces.PRIMARY_COMPONENTS) {
				pieceWeight.placeCount = 0;
				this.availableBridgePieces.add(pieceWeight);
			}
			this.availableCastlePieces = new ArrayList<>();
			for (final InfernalNetherFortressPieces.PieceWeight pieceWeight1 : InfernalNetherFortressPieces.SECONDARY_COMPONENTS) {
				pieceWeight1.placeCount = 0;
				this.availableCastlePieces.add(pieceWeight1);
			}
		}

		public StartPiece(final CompoundTag compoundIn) {
			super(BWStructurePieceTypes.INF_START.get(), compoundIn);
		}
	}
}
