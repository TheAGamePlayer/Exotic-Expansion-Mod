package dev.theagameplayer.blightedworlds.world.level.block.entity;

import java.util.List;

import dev.theagameplayer.blightedworlds.registries.BWBlockEntityTypes;
import dev.theagameplayer.blightedworlds.world.level.block.BlazeLanternBlock;
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

	public BlazeLanternBlockEntity(final BlockEntityType<?> blockEntityTypeIn, final BlockPos posIn, final BlockState stateIn) {
		super(blockEntityTypeIn, posIn, stateIn);
	}

	public BlazeLanternBlockEntity(final BlockPos posIn, final BlockState stateIn) {
		this(BWBlockEntityTypes.BLAZE_LANTERN.get(), posIn, stateIn);
	}

	public static final void tick(final Level levelIn, final BlockPos posIn, final BlockState stateIn, final BlazeLanternBlockEntity blockEntityIn) {
		final AABB aabb = new AABB(posIn).inflate(RANGE, RANGE, RANGE);
		final List<Mob> list = levelIn.getEntitiesOfClass(Mob.class, aabb);
		list.removeIf(mob -> mob.getClassification(false) != MobCategory.MONSTER);
		addBuffs(list, levelIn, RANGE);
		if (levelIn.isClientSide && !list.isEmpty()) {
			blockEntityIn.rangeOffset = blockEntityIn.rangeOffset == 0 ? levelIn.random.nextInt(26)/100 : 0;
			final double range = 0.75D + blockEntityIn.rangeOffset;
			final double theta = (blockEntityIn.degrees * Math.PI/180) * 10;
			final double x = posIn.getX() + 0.5D;
			final double y = posIn.getY() + (stateIn.getValue(BlazeLanternBlock.HANGING) ? 0.3125D : 0.25D);
			final double z = posIn.getZ() + 0.5D;
			if (blockEntityIn.speed == 0)
				levelIn.addParticle(ParticleTypes.FLAME, x + range * Math.cos(theta), y, z + range * Math.sin(theta), 0.0D, 0.0D, 0.0D);
			blockEntityIn.degrees++;
			blockEntityIn.speed++;
			if (blockEntityIn.speed > 5/list.size())
				blockEntityIn.speed = 0;
		}
	}

	private static final void addBuffs(final List<Mob> listIn, final Level levelIn, final double distanceIn) {
		for (final Mob mob : listIn) {
			final int amplifier = mob.getMaxHealth() / 2 >= mob.getHealth() ? 1 : 0;
			mob.setLastHurtByPlayer(levelIn.getNearestPlayer(mob, distanceIn));
			mob.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, amplifier));
			mob.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, amplifier));
		}
	}
}
