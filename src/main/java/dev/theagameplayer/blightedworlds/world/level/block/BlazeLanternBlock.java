package dev.theagameplayer.blightedworlds.world.level.block;

import javax.annotation.Nullable;

import dev.theagameplayer.blightedworlds.registries.BWBlockEntityTypes;
import dev.theagameplayer.blightedworlds.world.level.block.entity.BlazeLanternBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class BlazeLanternBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
	public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final VoxelShape AABB = Shapes.or(Block.box(5.0D, 0.0D, 5.0D, 11.0D, 7.0D, 11.0D), Block.box(6.0D, 7.0D, 6.0D, 10.0D, 9.0D, 10.0D));
	private static final VoxelShape HANGING_AABB = Shapes.or(Block.box(5.0D, 1.0D, 5.0D, 11.0D, 8.0D, 11.0D), Block.box(6.0D, 8.0D, 6.0D, 10.0D, 10.0D, 10.0D));

	public BlazeLanternBlock(final BlockBehaviour.Properties propertiesIn) {
		super(propertiesIn);
		this.registerDefaultState(this.stateDefinition.any().setValue(HANGING, Boolean.valueOf(false)).setValue(WATERLOGGED, Boolean.valueOf(false)));
	}

	@Override
	public final BlockEntity newBlockEntity(final BlockPos posIn, final BlockState stateIn) {
		return new BlazeLanternBlockEntity(posIn, stateIn);
	}

	@Override
	public final <T extends BlockEntity> BlockEntityTicker<T> getTicker(final Level levelIn, final BlockState stateIn, final BlockEntityType<T> blockEntityIn) {
		return createTickerHelper(blockEntityIn, BWBlockEntityTypes.BLAZE_LANTERN.get(), BlazeLanternBlockEntity::tick);
	}

	@Override
	public final RenderShape getRenderShape(final BlockState stateIn) {
		return RenderShape.MODEL;
	}

	@Nullable
	@Override
	public final BlockState getStateForPlacement(final BlockPlaceContext contextIn) {
		for(final Direction direction : contextIn.getNearestLookingDirections()) {
			if (direction.getAxis() == Direction.Axis.Y) {
				final BlockState blockState = this.defaultBlockState().setValue(HANGING, Boolean.valueOf(direction == Direction.UP));
				if (blockState.canSurvive(contextIn.getLevel(), contextIn.getClickedPos())) {
					return blockState.setValue(WATERLOGGED, Boolean.valueOf(contextIn.getLevel().getFluidState(contextIn.getClickedPos()).getType() == Fluids.WATER));
				}
			}
		}
		return null;
	}

	@Override
	public final VoxelShape getShape(final BlockState stateIn, final BlockGetter getterIn, final BlockPos posIn, final CollisionContext contextIn) {
		return stateIn.getValue(HANGING) ? HANGING_AABB : AABB;
	}

	@Override
	protected final void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> stateDefinitionIn) {
		stateDefinitionIn.add(HANGING, WATERLOGGED);
	}

	@Override
	public final boolean canSurvive(final BlockState stateIn, final LevelReader levelIn, final BlockPos posIn) {
		final Direction direction = getConnectedDirection(stateIn).getOpposite();
		return Block.canSupportCenter(levelIn, posIn.relative(direction), direction.getOpposite());
	}

	@Override
	public final PushReaction getPistonPushReaction(final BlockState stateIn) {
		return PushReaction.DESTROY;
	}

	@SuppressWarnings("deprecation")
	@Override
	public final BlockState updateShape(final BlockState stateIn, final Direction facingIn, final BlockState facingStateIn, final LevelAccessor levelIn, final BlockPos posIn, final BlockPos facingPosIn) {
		if (stateIn.getValue(WATERLOGGED))
			levelIn.scheduleTick(posIn, Fluids.WATER, Fluids.WATER.getTickDelay(levelIn));
		return getConnectedDirection(stateIn).getOpposite() == facingIn && !stateIn.canSurvive(levelIn, posIn) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facingIn, facingStateIn, levelIn, posIn, facingPosIn);
	}

	@SuppressWarnings("deprecation")
	@Override
	public final FluidState getFluidState(final BlockState stateIn) {
		return stateIn.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(stateIn);
	}

	@Override
	public final boolean isPathfindable(final BlockState stateIn, final BlockGetter getterIn, final BlockPos posIn, final PathComputationType pathIn) {
		return false;
	}

	private final static Direction getConnectedDirection(final BlockState stateIn) {
		return stateIn.getValue(HANGING) ? Direction.DOWN : Direction.UP;
	}
}
