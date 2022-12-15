package dev.theagameplayer.blightedworlds.item;

import dev.theagameplayer.blightedworlds.registries.BWBlocks;
import dev.theagameplayer.blightedworlds.world.level.block.AncientNetherAltarBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public final class AltarActivationOrbItem extends Item {
	public AltarActivationOrbItem(final Item.Properties propertiesIn) {
		super(propertiesIn);
	}

	@Override
	public final InteractionResult useOn(final UseOnContext contextIn) {
		final Level level = contextIn.getLevel();
		final BlockPos blockPos = contextIn.getClickedPos();
		final BlockState blockState = level.getBlockState(blockPos);
		if (blockState.is(BWBlocks.ANCIENT_NETHER_ALTAR.get()) && !blockState.getValue(AncientNetherAltarBlock.HAS_ORB)) {
			if (level.isClientSide) {
				return InteractionResult.SUCCESS;
			} else {
				final BlockState blockState1 = blockState.setValue(AncientNetherAltarBlock.HAS_ORB, Boolean.valueOf(true));
				Block.pushEntitiesUp(blockState, blockState1, level, blockPos);
				level.setBlock(blockPos, blockState1, 2);
				level.updateNeighbourForOutputSignal(blockPos, BWBlocks.ANCIENT_NETHER_ALTAR.get());
				contextIn.getItemInHand().shrink(1);
				level.levelEvent(1503, blockPos, 0);
				return InteractionResult.CONSUME;
			}
		} else {
			return InteractionResult.PASS;
		}
	}

	@Override
	public final InteractionResultHolder<ItemStack> use(final Level levelIn, final Player playerIn, final InteractionHand handIn) {
		final ItemStack itemStack = playerIn.getItemInHand(handIn);
		final HitResult hitResult = getPlayerPOVHitResult(levelIn, playerIn, ClipContext.Fluid.NONE);
		if (hitResult.getType() == HitResult.Type.BLOCK && levelIn.getBlockState(((BlockHitResult)hitResult).getBlockPos()).is(BWBlocks.ANCIENT_NETHER_ALTAR.get())) {
			return InteractionResultHolder.pass(itemStack);
		} else {
			playerIn.startUsingItem(handIn);
			if (!levelIn.isClientSide) {
				playerIn.awardStat(Stats.ITEM_USED.get(this));
				return InteractionResultHolder.success(itemStack);
			}
			return InteractionResultHolder.success(itemStack);
		}
	}
}
