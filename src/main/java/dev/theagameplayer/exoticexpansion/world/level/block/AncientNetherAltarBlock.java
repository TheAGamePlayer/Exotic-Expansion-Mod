package dev.theagameplayer.exoticexpansion.world.level.block;

import dev.theagameplayer.exoticexpansion.registries.EEBlocks;
import dev.theagameplayer.exoticexpansion.registries.other.EEBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class AncientNetherAltarBlock extends Block {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty HAS_ORB = EEBlockStateProperties.ORB;
	private static final VoxelShape BASE_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);
	private static final VoxelShape EYE_SHAPE = Block.box(4.0D, 9.0D, 4.0D, 12.0D, 12.0D, 12.0D);
	private static final VoxelShape FULL_SHAPE = Shapes.or(BASE_SHAPE, EYE_SHAPE);

	public AncientNetherAltarBlock(final BlockBehaviour.Properties pProperties) {
		super(pProperties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HAS_ORB, Boolean.valueOf(false)));
	}

	@Override
	public final InteractionResult useWithoutItem(final BlockState pState, final Level pLevel, final BlockPos pPos, final Player pPlayer, final BlockHitResult pHit) {
		if (pState.getValue(HAS_ORB) == true) {
			if (!this.placedPortal(pState, pLevel, pPos))
				return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}

	public final boolean placedPortal(final BlockState pState, final LevelAccessor pLevel, final BlockPos pPos) { //LevelAccessor used for Auto Activation in Ancient Nether
		final BlockPos height = pPos.above(6);
		if (pLevel.getBlockState(height) == Blocks.AIR.defaultBlockState()) {
			if (pLevel.getBlockState(pPos).getBlock() == EEBlocks.ANCIENT_NETHER_ALTAR.get() && pState.getValue(HAS_ORB))
				pLevel.setBlock(height, EEBlocks.ANCIENT_NETHER_PORTAL.get().defaultBlockState(), 2);
			return false;
		}
		return true;
	}

	@Override
	public final void destroy(final LevelAccessor pLevel, final BlockPos pPos, final BlockState pState) {
		if (this.placedPortal(pState, pLevel, pPos))
			pLevel.destroyBlock(pPos.above(6), dynamicShape);
	}

	@Override
	public final void animateTick(final BlockState pState, final Level pLevel, final BlockPos pPos, final RandomSource pRandom) {
		if (pState.getValue(HAS_ORB) == true) {
			final Direction direction = pState.getValue(FACING);
			final Direction.Axis axis = direction.getAxis();
			final double x = pPos.getX() + pRandom.nextDouble();
			final double y = pPos.getY() + 0.5D;
			final double z = pPos.getZ() + pRandom.nextDouble();
			final double offset = pRandom.nextDouble() * 0.1D - 0.0D;
			final double xOffset = axis == Direction.Axis.X ? (double)direction.getStepX() * 0.0D : offset;
			final double yOffset = axis == Direction.Axis.Y ? (double)direction.getStepY() * 0.62D : offset;
			final double zOffset = axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.0D : offset;
			if (pRandom.nextInt() == 0)
				pLevel.playLocalSound(x, y, z, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, pLevel.getBlockState(pPos.above()).isAir() ? 1.0F : 0.2F, 0.2F, false);
			if (pRandom.nextInt(10) == 0)
				pLevel.playLocalSound(x, y, z, SoundEvents.AMBIENT_CAVE.value(), SoundSource.BLOCKS, 0.5F, 0.2F, false);
			pLevel.addParticle(ParticleTypes.END_ROD, x + xOffset, y + yOffset, z + zOffset, 0.0D, pRandom.nextDouble()/5, 0.0D);
		}
	}

	@Override
	public final boolean useShapeForLightOcclusion(final BlockState pState) {
		return true;
	}

	@Override
	public final VoxelShape getShape(final BlockState pState, final BlockGetter pLevel, final BlockPos pPos, final CollisionContext pContext) {
		return pState.getValue(HAS_ORB) ? FULL_SHAPE : BASE_SHAPE;
	}

	@Override
	public final BlockState getStateForPlacement(final BlockPlaceContext pContext) {
		return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(HAS_ORB, Boolean.valueOf(false));
	}

	@Override
	public final boolean hasAnalogOutputSignal(final BlockState pState) {
		return true;
	}

	@Override
	public final int getAnalogOutputSignal(final BlockState pState, final Level pLevel, final BlockPos pPos) {
		return pState.getValue(HAS_ORB) ? 15 : 0;
	}

	@Override
	public final BlockState rotate(final BlockState pState, final Rotation pRotation) {
		return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
	}

	@SuppressWarnings("deprecation")
	@Override
	public final BlockState mirror(final BlockState pState, final Mirror pMirror) {
		return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
	}

	@Override
	protected final void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(FACING, HAS_ORB);
	}

	@Override
	public final boolean isPathfindable(final BlockState pState, final PathComputationType pType) {
		return false;
	}
}
