package dev.theagameplayer.exoticexpansion.world.level.block;

import javax.annotation.Nullable;

import com.mojang.serialization.MapCodec;

import dev.theagameplayer.exoticexpansion.registries.EEBlockEntityTypes;
import dev.theagameplayer.exoticexpansion.world.level.block.entity.BlazeLanternBlockEntity;
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
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class BlazeLanternBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
	public static final MapCodec<BlazeLanternBlock> CODEC = simpleCodec(BlazeLanternBlock::new);
	public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final VoxelShape AABB = Shapes.or(Block.box(5.0D, 0.0D, 5.0D, 11.0D, 7.0D, 11.0D), Block.box(6.0D, 7.0D, 6.0D, 10.0D, 9.0D, 10.0D));
	private static final VoxelShape HANGING_AABB = Shapes.or(Block.box(5.0D, 1.0D, 5.0D, 11.0D, 8.0D, 11.0D), Block.box(6.0D, 8.0D, 6.0D, 10.0D, 10.0D, 10.0D));

    @Override
    public MapCodec<BlazeLanternBlock> codec() {
        return CODEC;
    }
	
	public BlazeLanternBlock(final BlockBehaviour.Properties pProperties) {
		super(pProperties);
		this.registerDefaultState(this.stateDefinition.any().setValue(HANGING, Boolean.valueOf(false)).setValue(WATERLOGGED, Boolean.valueOf(false)));
	}

	@Override
	public final BlockEntity newBlockEntity(final BlockPos pPos, final BlockState pState) {
		return new BlazeLanternBlockEntity(pPos, pState);
	}

	@Override
	public final <T extends BlockEntity> BlockEntityTicker<T> getTicker(final Level pLevel, final BlockState pState, final BlockEntityType<T> pBlockEntity) {
		return createTickerHelper(pBlockEntity, EEBlockEntityTypes.BLAZE_LANTERN.get(), BlazeLanternBlockEntity::tick);
	}

	@Override
	public final RenderShape getRenderShape(final BlockState pState) {
		return RenderShape.MODEL;
	}

	@Nullable
	@Override
	public final BlockState getStateForPlacement(final BlockPlaceContext pContext) {
		final FluidState fluidState = pContext.getLevel().getFluidState(pContext.getClickedPos());
		for(final Direction direction : pContext.getNearestLookingDirections()) {
			if (direction.getAxis() == Direction.Axis.Y) {
				final BlockState blockState = this.defaultBlockState().setValue(HANGING, Boolean.valueOf(direction == Direction.UP));
				if (blockState.canSurvive(pContext.getLevel(), pContext.getClickedPos())) {
					return blockState.setValue(WATERLOGGED, Boolean.valueOf(fluidState.getType() == Fluids.WATER));
				}
			}
		}
		return null;
	}

	@Override
	public final VoxelShape getShape(final BlockState pState, final BlockGetter pLevel, final BlockPos pPos, final CollisionContext pContext) {
		return pState.getValue(HANGING) ? HANGING_AABB : AABB;
	}

	@Override
	protected final void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(HANGING, WATERLOGGED);
	}

	@Override
	public final boolean canSurvive(final BlockState pState, final LevelReader pLevel, final BlockPos pPos) {
		final Direction direction = getConnectedDirection(pState).getOpposite();
		return Block.canSupportCenter(pLevel, pPos.relative(direction), direction.getOpposite());
	}
	
	private final static Direction getConnectedDirection(final BlockState pState) {
		return pState.getValue(HANGING) ? Direction.DOWN : Direction.UP;
	}

	@Override
	public final BlockState updateShape(final BlockState pState, final Direction pDirection, final BlockState pNeighborState, final LevelAccessor pLevel, final BlockPos pPos, final BlockPos pNeighborPos) {
		if (pState.getValue(WATERLOGGED))
			pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
		return getConnectedDirection(pState).getOpposite() == pDirection && !pState.canSurvive(pLevel, pPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
	}

	@Override
	public final FluidState getFluidState(final BlockState pState) {
		return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
	}

	@Override
	public final boolean isPathfindable(final BlockState pState, final PathComputationType pType) {
		return false;
	}
}
