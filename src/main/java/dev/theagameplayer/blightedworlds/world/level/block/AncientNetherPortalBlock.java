package dev.theagameplayer.blightedworlds.world.level.block;

import dev.theagameplayer.blightedworlds.registries.BWBlockEntityTypes;
import dev.theagameplayer.blightedworlds.registries.other.BWBlockStateProperties;
import dev.theagameplayer.blightedworlds.registries.other.BWDimensions;
import dev.theagameplayer.blightedworlds.world.BWTeleporter;
import dev.theagameplayer.blightedworlds.world.level.PortalMode;
import dev.theagameplayer.blightedworlds.world.level.block.entity.AncientNetherPortalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class AncientNetherPortalBlock extends BaseEntityBlock {
	private static final VoxelShape SHAPE = Block.box(0.0D, 6.0D, 0.0D, 16.0D, 8.0D, 16.0D);
	public static final EnumProperty<PortalMode> PORTAL_MODE = BWBlockStateProperties.PORTAL_MODE;

	public AncientNetherPortalBlock(final BlockBehaviour.Properties propertiesIn) {
		super(propertiesIn);
		this.registerDefaultState(this.stateDefinition.any().setValue(PORTAL_MODE, PortalMode.ACTIVE));
	}

	@Override
	public final BlockEntity newBlockEntity(final BlockPos posIn, final BlockState stateIn) {
		return new AncientNetherPortalBlockEntity(posIn, stateIn);
	}
	
	@Override
	public final <T extends BlockEntity> BlockEntityTicker<T> getTicker(final Level levelIn, final BlockState stateIn, final BlockEntityType<T> blockEntityIn) {
		return createTickerHelper(blockEntityIn, BWBlockEntityTypes.ANCIENT_NETHER_PORTAL.get(), AncientNetherPortalBlockEntity::tick);
	}
	
	@Override
	public final RenderShape getRenderShape(final BlockState stateIn) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public final VoxelShape getShape(final BlockState stateIn, final BlockGetter getterIn, final BlockPos posIn, final CollisionContext contextIn) {
		return SHAPE;
	}
	
	@Override
	public final BlockState getStateForPlacement(final BlockPlaceContext contextIn) {
		return this.defaultBlockState().setValue(PORTAL_MODE, PortalMode.ACTIVE);
	}

	@Override
	public final void entityInside(final BlockState stateIn, final Level levelIn, final BlockPos posIn, final Entity entityIn) {
		if (levelIn instanceof ServerLevel && stateIn.getValue(PORTAL_MODE).canTeleport() && !entityIn.isPassenger() && !entityIn.isVehicle() && entityIn.canChangeDimensions() && Shapes.joinIsNotEmpty(Shapes.create(entityIn.getBoundingBox().move(-posIn.getX(), -posIn.getY(), -posIn.getZ())), stateIn.getShape(levelIn, posIn), BooleanOp.AND)) {
			final ServerLevel serverLevel = ((ServerLevel)levelIn).getServer().getLevel(levelIn.dimension() == BWDimensions.ANCIENT_NETHER ? Level.NETHER : BWDimensions.ANCIENT_NETHER);
			if (serverLevel == null) return;
			entityIn.changeDimension(serverLevel, new BWTeleporter());
		}
	}

	@Override
	public final void animateTick(final BlockState stateIn, final Level levelIn, final BlockPos posIn, final RandomSource randomIn) {
		if (randomIn.nextInt(100) == 0)
			levelIn.playLocalSound((double)posIn.getX() + 0.5D, (double)posIn.getY() + 0.5D, (double)posIn.getZ() + 0.5D, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.5F, randomIn.nextFloat() * 0.1F + 0.2F, false);
		for(int i = 0; i < 4; ++i) {
			final double x = posIn.getX() + randomIn.nextDouble();
			final double y = posIn.getY() + randomIn.nextDouble();
			final double z = posIn.getZ() + randomIn.nextDouble();
			final double xOffset = (randomIn.nextDouble() - 0.5D) * 0.5D;
			final double yOffset = (randomIn.nextDouble() - 0.5D) * 0.5D;
			final double zOffset = (randomIn.nextDouble() - 0.5D) * 0.5D;
			levelIn.addParticle(ParticleTypes.PORTAL, x, y, z, xOffset, yOffset, zOffset);
		}
	}

	@Override
	public final ItemStack getCloneItemStack(final BlockGetter getterIn, final BlockPos posIn, final BlockState stateIn) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canBeReplaced(final BlockState stateIn, final Fluid fluidIn) {
		return false;
	}
	
	@Override
	protected final void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> stateDefinitionIn) {
		stateDefinitionIn.add(PORTAL_MODE);
	}
}
