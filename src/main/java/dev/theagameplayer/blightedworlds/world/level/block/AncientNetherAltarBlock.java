package dev.theagameplayer.blightedworlds.world.level.block;

import dev.theagameplayer.blightedworlds.registries.BWBlocks;
import dev.theagameplayer.blightedworlds.registries.other.BWBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
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
	public static final BooleanProperty HAS_ORB = BWBlockStateProperties.ORB;
	private static final VoxelShape BASE_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);
	private static final VoxelShape EYE_SHAPE = Block.box(4.0D, 9.0D, 4.0D, 12.0D, 12.0D, 12.0D);
	private static final VoxelShape FULL_SHAPE = Shapes.or(BASE_SHAPE, EYE_SHAPE);

	public AncientNetherAltarBlock(final BlockBehaviour.Properties propertiesIn) {
		super(propertiesIn);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HAS_ORB, Boolean.valueOf(false)));
	}

	@Override
	public final InteractionResult use(final BlockState stateIn, final Level levelIn, final BlockPos posIn, final Player playerIn, final InteractionHand handIn, final BlockHitResult hitIn) {
		if (stateIn.getValue(HAS_ORB) == true) {
			if (!this.placedPortal(stateIn, levelIn, posIn))
				return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}

	public final boolean placedPortal(final BlockState stateIn, final LevelAccessor levelIn, final BlockPos posIn) { //LevelAccessor used for Auto Activation in Ancient Nether
		final BlockPos height = posIn.above(6);
		if (levelIn.getBlockState(height) == Blocks.AIR.defaultBlockState()) {
			if (levelIn.getBlockState(posIn).getBlock() == BWBlocks.ANCIENT_NETHER_ALTAR.get() && stateIn.getValue(HAS_ORB))
				levelIn.setBlock(height, BWBlocks.ANCIENT_NETHER_PORTAL.get().defaultBlockState(), 2);
			return false;
		}
		return true;
	}

	@Override
	public final void destroy(final LevelAccessor levelIn, final BlockPos posIn, final BlockState stateIn) {
		if (this.placedPortal(stateIn, levelIn, posIn))
			levelIn.destroyBlock(posIn.above(6), dynamicShape);
	}

	@Override
	public final void animateTick(final BlockState stateIn, final Level levelIn, final BlockPos posIn, final RandomSource randomIn) {
		if (stateIn.getValue(HAS_ORB) == true) {
			final Direction direction = stateIn.getValue(FACING);
			final Direction.Axis axis = direction.getAxis();
			final double x = posIn.getX() + randomIn.nextDouble();
			final double y = posIn.getY() + 0.5D;
			final double z = posIn.getZ() + randomIn.nextDouble();
			final double offset = randomIn.nextDouble() * 0.1D - 0.0D;
			final double xOffset = axis == Direction.Axis.X ? (double)direction.getStepX() * 0.0D : offset;
			final double yOffset = axis == Direction.Axis.Y ? (double)direction.getStepY() * 0.62D : offset;
			final double zOffset = axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.0D : offset;
			if (randomIn.nextInt() == 0)
				levelIn.playLocalSound(x, y, z, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, levelIn.getBlockState(posIn.above()) == Blocks.AIR.defaultBlockState() ? 1.0F : 0.2F, 0.2F, false);
			if (randomIn.nextInt(10) == 0)
				levelIn.playLocalSound(x, y, z, SoundEvents.AMBIENT_CAVE.get(), SoundSource.BLOCKS, 0.5F, 0.2F, false);
			levelIn.addParticle(ParticleTypes.END_ROD, x + xOffset, y + yOffset, z + zOffset, 0.0D, randomIn.nextDouble()/5, 0.0D);
		}
	}

	@Override
	public final boolean useShapeForLightOcclusion(final BlockState stateIn) {
		return true;
	}

	@Override
	public final VoxelShape getShape(final BlockState stateIn, final BlockGetter getterIn, final BlockPos posIn, final CollisionContext contextIn) {
		return stateIn.getValue(HAS_ORB) ? FULL_SHAPE : BASE_SHAPE;
	}

	@Override
	public final BlockState getStateForPlacement(final BlockPlaceContext contextIn) {
		return this.defaultBlockState().setValue(FACING, contextIn.getHorizontalDirection().getOpposite()).setValue(HAS_ORB, Boolean.valueOf(false));
	}

	@Override
	public final boolean hasAnalogOutputSignal(final BlockState stateIn) {
		return true;
	}

	@Override
	public final int getAnalogOutputSignal(final BlockState stateIn, final Level levelIn, final BlockPos posIn) {
		return stateIn.getValue(HAS_ORB) ? 15 : 0;
	}

	@Override
	public final BlockState rotate(final BlockState stateIn, final Rotation rotIn) {
		return stateIn.setValue(FACING, rotIn.rotate(stateIn.getValue(FACING)));
	}

	@SuppressWarnings("deprecation")
	@Override
	public final BlockState mirror(final BlockState stateIn, final Mirror mirrorIn) {
		return stateIn.rotate(mirrorIn.getRotation(stateIn.getValue(FACING)));
	}

	@Override
	protected final void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> stateDefinitionIn) {
		stateDefinitionIn.add(FACING, HAS_ORB);
	}

	@Override
	public final boolean isPathfindable(final BlockState stateIn, final BlockGetter getterIn, final BlockPos posIn, final PathComputationType pathIn) {
		return false;
	}
}
