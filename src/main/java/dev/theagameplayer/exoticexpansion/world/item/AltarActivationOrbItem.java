package dev.theagameplayer.exoticexpansion.world.item;

import dev.theagameplayer.exoticexpansion.registries.EEBlocks;
import dev.theagameplayer.exoticexpansion.world.level.block.AncientNetherAltarBlock;
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

public class AltarActivationOrbItem extends Item {
	public AltarActivationOrbItem(final Item.Properties pProperties) {
		super(pProperties);
	}

	@Override
	public final InteractionResult useOn(final UseOnContext pContext) {
		final Level level = pContext.getLevel();
		final BlockPos blockPos = pContext.getClickedPos();
		final BlockState blockState = level.getBlockState(blockPos);
		if (blockState.is(EEBlocks.ANCIENT_NETHER_ALTAR.get()) && !blockState.getValue(AncientNetherAltarBlock.HAS_ORB)) {
			if (level.isClientSide) {
				return InteractionResult.SUCCESS;
			} else {
				final BlockState blockState1 = blockState.setValue(AncientNetherAltarBlock.HAS_ORB, Boolean.valueOf(true));
				Block.pushEntitiesUp(blockState, blockState1, level, blockPos);
				level.setBlock(blockPos, blockState1, 2);
				level.updateNeighbourForOutputSignal(blockPos, EEBlocks.ANCIENT_NETHER_ALTAR.get());
				pContext.getItemInHand().shrink(1);
				level.levelEvent(1503, blockPos, 0);
				return InteractionResult.CONSUME;
			}
		} else {
			return InteractionResult.PASS;
		}
	}

	@Override
	public final InteractionResultHolder<ItemStack> use(final Level pLevel, final Player pPlayer, final InteractionHand pHand) {
		final ItemStack itemStack = pPlayer.getItemInHand(pHand);
		final BlockHitResult blockHitResult = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.NONE);
		if (blockHitResult.getType() == HitResult.Type.BLOCK && pLevel.getBlockState(blockHitResult.getBlockPos()).is(EEBlocks.ANCIENT_NETHER_ALTAR.get())) {
			return InteractionResultHolder.pass(itemStack);
		} else {
			pPlayer.startUsingItem(pHand);
			if (!pLevel.isClientSide) {
				pPlayer.awardStat(Stats.ITEM_USED.get(this));
				return InteractionResultHolder.success(itemStack);
			}
			return InteractionResultHolder.success(itemStack);
		}
	}
}
