package dev.theagameplayer.exoticexpansion.world.level.block.entity;

import java.util.List;

import dev.theagameplayer.exoticexpansion.registries.EEBlockEntityTypes;
import dev.theagameplayer.exoticexpansion.world.level.PortalMode;
import dev.theagameplayer.exoticexpansion.world.level.block.AncientNetherPortalBlock;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class AncientNetherPortalBlockEntity extends BlockEntity {
	private static final double RANGE = 7.5D;
	private static final double BLIND_RANGE = 0.5D;
	private float prevRotY, prevRotX, prevRotZ, prevScale;
	private float rotY, rotX, rotZ, scale;
	private int yRotations, xRotations, zRotations;
	private boolean flag, flag2, flag3, flag4;
	private int reactivationCount;

	public AncientNetherPortalBlockEntity(final BlockEntityType<?> pBlockEntityType, final BlockPos pPos, final BlockState pState) {
		super(pBlockEntityType, pPos, pState);
		this.yRotations = 1;
		this.xRotations = 1;
		this.zRotations = 1;
	}

	public AncientNetherPortalBlockEntity(final BlockPos pPos, final BlockState pState) {
		this(EEBlockEntityTypes.ANCIENT_NETHER_PORTAL.get(), pPos, pState);
	}

	public static final void tick(final Level pLevel, final BlockPos pPos, final BlockState pState, final AncientNetherPortalBlockEntity pBlockEntity) {
		final PortalMode portalMode = pState.getValue(AncientNetherPortalBlock.PORTAL_MODE);
		if (pLevel.isClientSide) clientTick(pLevel, pPos, pState, pBlockEntity);
		if (portalMode == PortalMode.ACTIVE) {
			final AABB aabb = new AABB(pPos).setMinY(pPos.getY() - 6.0D);
			final List<LivingEntity> list = pLevel.getEntitiesOfClass(LivingEntity.class, aabb);
			final Vec3 center = pPos.getCenter();
			list.removeIf(livingEntity -> pLevel.isClientSide ? !(livingEntity instanceof AbstractClientPlayer) : false);
			for (final LivingEntity livingEntity : list) {
				final double distX = Math.abs(center.x() - livingEntity.getX());
				final double distY = Math.abs(center.y() - livingEntity.getY());
				final double distZ = Math.abs(center.z() - livingEntity.getZ());
				final double forceY = 0.05D * (RANGE + 1.0D - distY);
				livingEntity.addDeltaMovement(new Vec3(0.0F, distX <= BLIND_RANGE && distZ <= BLIND_RANGE ? (livingEntity.getY() < center.y() ? forceY : -forceY) : 0.0F, 0.0F));
			}
		} else if (portalMode == PortalMode.USED) {
			final AABB aabb = new AABB(pPos).inflate(RANGE, RANGE, RANGE);
			final List<LivingEntity> list = pLevel.getEntitiesOfClass(LivingEntity.class, aabb);
			final Vec3 center = pPos.getCenter();
			list.removeIf(livingEntity -> pLevel.isClientSide ? !(livingEntity instanceof AbstractClientPlayer) : false);
			for (final LivingEntity livingEntity : list) {
				final double distX = Math.abs(center.x() - livingEntity.getX());
				final double distY = Math.abs(center.y() - livingEntity.getY());
				final double distZ = Math.abs(center.z() - livingEntity.getZ());
				final double forceX = 0.01D * (RANGE + 1.0D - distX);
				final double forceY = 0.05D * (RANGE + 1.0D - distY);
				final double forceZ = 0.01D * (RANGE + 1.0D - distZ);
				livingEntity.addDeltaMovement(new Vec3(distX > BLIND_RANGE ? (livingEntity.getX() < center.x() ? forceX : -forceX) : 0.0F, distX <= BLIND_RANGE && distZ <= BLIND_RANGE ? (livingEntity.getY() < center.y() ? forceY : -forceY) : 0.0F, distZ > BLIND_RANGE ? (livingEntity.getZ() < center.z() ? forceZ : -forceZ) : 0.0F));
			}
		} else if (portalMode == PortalMode.INACTIVE && !pLevel.isClientSide) {
			pBlockEntity.reactivationCount--;
			if (pBlockEntity.reactivationCount < 0)
				pLevel.setBlockAndUpdate(pPos, pState.setValue(AncientNetherPortalBlock.PORTAL_MODE, PortalMode.ACTIVE));
		}
	}

	private static final void clientTick(final Level pLevel, final BlockPos pPos, final BlockState pState, final AncientNetherPortalBlockEntity pBlockEntity) {
		final float rotYMult = 12.5F + pBlockEntity.scale * 5.0F;
		final float rotXMult = pBlockEntity.scale * 0.005F;
		final float rotZMult = pBlockEntity.scale * 0.005F;
		final float scaleMult = 0.005F;
		pBlockEntity.prevRotY = pBlockEntity.rotY;
		pBlockEntity.prevRotX = pBlockEntity.rotX;
		pBlockEntity.prevRotZ = pBlockEntity.rotZ;
		pBlockEntity.prevScale = pBlockEntity.scale;
		pBlockEntity.rotY += pBlockEntity.flag ? -rotYMult : rotYMult;
		pBlockEntity.rotX += pBlockEntity.flag2 ? -rotXMult : rotXMult;
		pBlockEntity.rotZ += pBlockEntity.flag3 ? -rotZMult : rotZMult;
		pBlockEntity.scale += pBlockEntity.flag4 ? -scaleMult : scaleMult;
		if (pBlockEntity.rotY >= 360.0F * pBlockEntity.yRotations) {
			pBlockEntity.flag = true;
		} else if (pBlockEntity.rotY < 0.0F) {
			pBlockEntity.flag = false;
			pBlockEntity.yRotations = 5 + (4 - pLevel.random.nextInt(pLevel.random.nextInt(5) + 1));
		}
		if (pBlockEntity.rotX >= pBlockEntity.xRotations) {
			pBlockEntity.flag2 = true;
		} else if (pBlockEntity.rotX < 0.0F) {
			pBlockEntity.flag2 = false;
			pBlockEntity.xRotations = 1 + pLevel.random.nextInt(pLevel.random.nextInt(10) + 1);
		}
		if (pBlockEntity.rotZ >= pBlockEntity.zRotations) {
			pBlockEntity.flag3 = true;
		} else if (pBlockEntity.rotZ < 0.0F) {
			pBlockEntity.flag3 = false;
			pBlockEntity.zRotations = 1 + pLevel.random.nextInt(pLevel.random.nextInt(10) + 1);
		}
		if (pBlockEntity.scale >= 1.0F) {
			pBlockEntity.flag4 = true;
		} else if (pBlockEntity.scale < 0.0F) {
			pBlockEntity.flag4 = false;
		}
	}

	public final void setReactivationCount(final int pCount) {
		this.reactivationCount = pCount;
	}

	public final float getPrevRotY() {
		return this.prevRotY;
	}

	public final float getPrevRotX() {
		return this.prevRotX;
	}

	public final float getPrevRotZ() {
		return this.prevRotZ;
	}

	public final float getPrevScale() {
		return this.prevScale;
	}

	public final float getRotY() {
		return this.rotY;
	}

	public final float getRotX() {
		return this.rotX;
	}

	public final float getRotZ() {
		return this.rotZ;
	}

	public final float getScale() {
		return this.scale;
	}

	public final int getYRotations() {
		return this.yRotations;
	}

	public final int getXRotations() {
		return this.xRotations;
	}

	public final int getZRotations() {
		return this.zRotations;
	}

	public final boolean getYFlag() {
		return this.flag;
	}
}
