package dev.theagameplayer.exoticexpansion.world.level.block.entity;

import java.util.List;

import dev.theagameplayer.exoticexpansion.registries.EEBlockEntityTypes;
import dev.theagameplayer.exoticexpansion.world.level.block.BlazeLanternBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public final class BlazeLanternBlockEntity extends BlockEntity {
	private static final double RANGE = 7.5D;
	private int degrees, speed, rangeOffset;

	public BlazeLanternBlockEntity(final BlockEntityType<?> pBlockEntityType, final BlockPos pPos, final BlockState pState) {
		super(pBlockEntityType, pPos, pState);
	}

	public BlazeLanternBlockEntity(final BlockPos pPos, final BlockState pState) {
		this(EEBlockEntityTypes.BLAZE_LANTERN.get(), pPos, pState);
	}

	public static final void tick(final Level pLevel, final BlockPos pPos, final BlockState pState, final BlazeLanternBlockEntity pBlockEntity) {
		final AABB aabb = new AABB(pPos).inflate(RANGE, RANGE, RANGE);
		final List<Mob> list = pLevel.getEntitiesOfClass(Mob.class, aabb);
		list.removeIf(mob -> mob.getClassification(false) != MobCategory.MONSTER);
		addBuffs(list, pLevel, RANGE);
		if (pLevel.isClientSide && !list.isEmpty()) {
			pBlockEntity.rangeOffset = pBlockEntity.rangeOffset == 0 ? pLevel.random.nextInt(26)/100 : 0;
			final double range = 0.75D + pBlockEntity.rangeOffset;
			final double theta = (pBlockEntity.degrees * Math.PI/180) * 10;
			final double x = pPos.getX() + 0.5D;
			final double y = pPos.getY() + (pState.getValue(BlazeLanternBlock.HANGING) ? 0.3125D : 0.25D);
			final double z = pPos.getZ() + 0.5D;
			if (pBlockEntity.speed == 0)
				pLevel.addParticle(ParticleTypes.FLAME, x + range * Math.cos(theta), y, z + range * Math.sin(theta), 0.0D, 0.0D, 0.0D);
			pBlockEntity.degrees++;
			pBlockEntity.speed++;
			if (pBlockEntity.speed > 5/list.size())
				pBlockEntity.speed = 0;
		}
	}

	private static final void addBuffs(final List<Mob> pMobs, final Level pLevel, final double pDistance) {
		for (final Mob mob : pMobs) {
			final int amplifier = mob.getMaxHealth() / 2 >= mob.getHealth() ? 1 : 0;
			mob.setLastHurtByPlayer(pLevel.getNearestPlayer(mob, pDistance));
			mob.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, amplifier));
			mob.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, amplifier));
		}
	}
}
