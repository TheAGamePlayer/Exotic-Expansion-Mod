package dev.theagameplayer.exoticexpansion.world.level.block;

import java.util.Map;
import java.util.Map.Entry;

import com.mojang.serialization.MapCodec;

import dev.theagameplayer.exoticexpansion.registries.EEBlockEntityTypes;
import dev.theagameplayer.exoticexpansion.registries.EEBlocks;
import dev.theagameplayer.exoticexpansion.registries.other.EEBlockStateProperties;
import dev.theagameplayer.exoticexpansion.registries.other.EEDimensions;
import dev.theagameplayer.exoticexpansion.tags.EEStructureTags;
import dev.theagameplayer.exoticexpansion.world.level.PortalMode;
import dev.theagameplayer.exoticexpansion.world.level.block.entity.AncientNetherPortalBlockEntity;
import dev.theagameplayer.exoticexpansion.world.level.levelgen.structure.TeleportDestinationPiece;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class AncientNetherPortalBlock extends BaseEntityBlock implements Portal {
	public static final MapCodec<AncientNetherPortalBlock> CODEC = simpleCodec(AncientNetherPortalBlock::new);
	private static final VoxelShape SHAPE = Block.box(0.0D, 6.0D, 0.0D, 16.0D, 8.0D, 16.0D);
	public static final EnumProperty<PortalMode> PORTAL_MODE = EEBlockStateProperties.PORTAL_MODE;

	@Override
	public MapCodec<AncientNetherPortalBlock> codec() {
		return CODEC;
	}

	public AncientNetherPortalBlock(final BlockBehaviour.Properties pProperties) {
		super(pProperties);
		this.registerDefaultState(this.stateDefinition.any().setValue(PORTAL_MODE, PortalMode.ACTIVE));
	}

	@Override
	public final BlockEntity newBlockEntity(final BlockPos pPos, final BlockState pState) {
		return new AncientNetherPortalBlockEntity(pPos, pState);
	}

	@Override
	public final <T extends BlockEntity> BlockEntityTicker<T> getTicker(final Level pLevel, final BlockState pState, final BlockEntityType<T> pBlockEntity) {
		return createTickerHelper(pBlockEntity, EEBlockEntityTypes.ANCIENT_NETHER_PORTAL.get(), AncientNetherPortalBlockEntity::tick);
	}

	@Override
	public final RenderShape getRenderShape(final BlockState pState) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public final VoxelShape getShape(final BlockState pState, final BlockGetter pLevel, final BlockPos pPos, final CollisionContext pContext) {
		return SHAPE;
	}

	@Override
	public final BlockState getStateForPlacement(final BlockPlaceContext pContext) {
		return this.defaultBlockState().setValue(PORTAL_MODE, PortalMode.ACTIVE);
	}

	@Override
	public final void entityInside(final BlockState pState, final Level pLevel, final BlockPos pPos, final Entity pEntity) {
		if (pState.getValue(PORTAL_MODE).canTeleport() && pEntity.canUsePortal(false) && Shapes.joinIsNotEmpty(Shapes.create(pEntity.getBoundingBox().move(-pPos.getX(), -pPos.getY(), -pPos.getZ())), pState.getShape(pLevel, pPos), BooleanOp.AND))
			pEntity.setAsInsidePortal(this, pPos);
	}

	@Override
	public final void animateTick(final BlockState pState, final Level pLevel, final BlockPos pPos, final RandomSource pRandom) {
		if (pRandom.nextInt(100) == 0)
			pLevel.playLocalSound((double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.5F, pRandom.nextFloat() * 0.1F + 0.2F, false);
		for (int i = 0; i < 4; ++i) {
			final double x = pPos.getX() + pRandom.nextDouble();
			final double y = pPos.getY() + pRandom.nextDouble();
			final double z = pPos.getZ() + pRandom.nextDouble();
			final double xOffset = (pRandom.nextDouble() - 0.5D) * 0.5D;
			final double yOffset = (pRandom.nextDouble() - 0.5D) * 0.5D;
			final double zOffset = (pRandom.nextDouble() - 0.5D) * 0.5D;
			pLevel.addParticle(ParticleTypes.PORTAL, x, y, z, xOffset, yOffset, zOffset);
		}
	}

	@Override
	public final ItemStack getCloneItemStack(final LevelReader pLevel, final BlockPos pPos, final BlockState pState) {
		return ItemStack.EMPTY;
	}

	@Override
	protected final void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(PORTAL_MODE);
	}

	@Override
	public final DimensionTransition getPortalDestination(final ServerLevel pLevel, final Entity pEntity, final BlockPos pPos) {
		final ServerLevel serverLevel = pLevel.getServer().getLevel(pLevel.dimension() == EEDimensions.ANCIENT_NETHER ? Level.NETHER : EEDimensions.ANCIENT_NETHER);
		if (serverLevel != null) {
			final BlockPos spawnPos = serverLevel.findNearestMapStructure(EEStructureTags.ANCIENT_NETHER_DESTINATIONS, pEntity.blockPosition(), 100, false) == null ? pEntity.blockPosition() : serverLevel.findNearestMapStructure(EEStructureTags.ANCIENT_NETHER_DESTINATIONS, pEntity.blockPosition(), 100, false);
			if (serverLevel.structureManager().hasAnyStructureAt(spawnPos)) {
				final Map<Structure, LongSet> structures = serverLevel.structureManager().getAllStructuresAt(spawnPos);
				for (final Entry<Structure, LongSet> entry : structures.entrySet()) {
					for (final long l : entry.getValue()) {
						final SectionPos sectionPos = SectionPos.of(new ChunkPos(l), serverLevel.getMinSection());
						final StructureStart start = serverLevel.structureManager().getStartForStructure(sectionPos, entry.getKey(), serverLevel.getChunk(sectionPos.x(), sectionPos.z(), ChunkStatus.STRUCTURE_STARTS));
						for (final StructurePiece piece : start.getPieces()) {
							if (piece instanceof TeleportDestinationPiece destinationPiece) {
								final BlockPos altarPos = destinationPiece.getDestination();
								if (serverLevel.getBlockState(altarPos).getBlock() == EEBlocks.ANCIENT_NETHER_ALTAR.get()) {
									final BlockPos height = altarPos.above(6);
									if (!serverLevel.getBlockState(altarPos).getValue(AncientNetherAltarBlock.HAS_ORB))
										serverLevel.setBlockAndUpdate(altarPos, serverLevel.getBlockState(altarPos).setValue(AncientNetherAltarBlock.HAS_ORB, Boolean.valueOf(true)));
									if (!((AncientNetherAltarBlock)serverLevel.getBlockState(altarPos).getBlock()).placedPortal(serverLevel.getBlockState(altarPos), serverLevel, altarPos)) {
									} else {
										serverLevel.setBlockAndUpdate(height, serverLevel.getBlockState(height).setValue(AncientNetherPortalBlock.PORTAL_MODE, PortalMode.INACTIVE));
										((AncientNetherPortalBlockEntity)serverLevel.getBlockEntity(height)).setReactivationCount(600);
									}
								}
								return new DimensionTransition(serverLevel, altarPos.relative(serverLevel.getBlockState(altarPos).getValue(AncientNetherAltarBlock.FACING)).getCenter(), Vec3.ZERO, pEntity.getYRot(), pEntity.getXRot(), false, entity -> {
									entity.fallDistance = 0;
									if (entity instanceof ServerPlayer serverPlayer)
										serverPlayer.connection.send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
								});
							}
						}
					}
				}
			}
			return new DimensionTransition(serverLevel, spawnPos.getCenter(), Vec3.ZERO, pEntity.getYRot(), pEntity.getXRot(), false, DimensionTransition.DO_NOTHING);
		}
		return new DimensionTransition(pLevel, pEntity.position(), Vec3.ZERO, pEntity.getYRot(), pEntity.getXRot(), false, DimensionTransition.DO_NOTHING);
	}
}
