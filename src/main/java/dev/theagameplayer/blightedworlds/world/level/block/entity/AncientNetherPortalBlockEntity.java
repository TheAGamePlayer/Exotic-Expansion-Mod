package dev.theagameplayer.blightedworlds.world.level.block.entity;

import java.util.List;

import dev.theagameplayer.blightedworlds.registries.BWBlockEntityTypes;
import dev.theagameplayer.blightedworlds.world.level.PortalMode;
import dev.theagameplayer.blightedworlds.world.level.block.AncientNetherPortalBlock;
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

	public AncientNetherPortalBlockEntity(final BlockEntityType<?> blockEntityTypeIn, final BlockPos posIn, final BlockState stateIn) {
		super(blockEntityTypeIn, posIn, stateIn);
		this.yRotations = 1;
		this.xRotations = 1;
		this.zRotations = 1;
	}

	public AncientNetherPortalBlockEntity(final BlockPos posIn, final BlockState stateIn) {
		this(BWBlockEntityTypes.ANCIENT_NETHER_PORTAL.get(), posIn, stateIn);
	}

	public static final void tick(final Level levelIn, final BlockPos posIn, final BlockState stateIn, final AncientNetherPortalBlockEntity blockEntityIn) {
		final PortalMode portalMode = stateIn.getValue(AncientNetherPortalBlock.PORTAL_MODE);
		if (levelIn.isClientSide) clientTick(levelIn, posIn, stateIn, blockEntityIn);
		if (portalMode == PortalMode.ACTIVE) {
			final AABB aabb = new AABB(posIn).setMinY(posIn.getY() - 6.0D);
			final List<LivingEntity> list = levelIn.getEntitiesOfClass(LivingEntity.class, aabb);
			final Vec3 center = posIn.getCenter();
			list.removeIf(livingEntity -> levelIn.isClientSide ? !(livingEntity instanceof AbstractClientPlayer) : false);
			for (final LivingEntity livingEntity : list) {
				final double distX = Math.abs(center.x() - livingEntity.getX());
				final double distY = Math.abs(center.y() - livingEntity.getY());
				final double distZ = Math.abs(center.z() - livingEntity.getZ());
				final double forceY = 0.05D * (RANGE + 1.0D - distY);
				livingEntity.addDeltaMovement(new Vec3(0.0F, distX <= BLIND_RANGE && distZ <= BLIND_RANGE ? (livingEntity.getY() < center.y() ? forceY : -forceY) : 0.0F, 0.0F));
			}
		} else if (portalMode == PortalMode.USED) {
			final AABB aabb = new AABB(posIn).inflate(RANGE, RANGE, RANGE);
			final List<LivingEntity> list = levelIn.getEntitiesOfClass(LivingEntity.class, aabb);
			final Vec3 center = posIn.getCenter();
			list.removeIf(livingEntity -> levelIn.isClientSide ? !(livingEntity instanceof AbstractClientPlayer) : false);
			for (final LivingEntity livingEntity : list) {
				final double distX = Math.abs(center.x() - livingEntity.getX());
				final double distY = Math.abs(center.y() - livingEntity.getY());
				final double distZ = Math.abs(center.z() - livingEntity.getZ());
				final double forceX = 0.01D * (RANGE + 1.0D - distX);
				final double forceY = 0.05D * (RANGE + 1.0D - distY);
				final double forceZ = 0.01D * (RANGE + 1.0D - distZ);
				livingEntity.addDeltaMovement(new Vec3(distX > BLIND_RANGE ? (livingEntity.getX() < center.x() ? forceX : -forceX) : 0.0F, distX <= BLIND_RANGE && distZ <= BLIND_RANGE ? (livingEntity.getY() < center.y() ? forceY : -forceY) : 0.0F, distZ > BLIND_RANGE ? (livingEntity.getZ() < center.z() ? forceZ : -forceZ) : 0.0F));
			}
		} else if (portalMode == PortalMode.INACTIVE && !levelIn.isClientSide) {
			blockEntityIn.reactivationCount--;
			if (blockEntityIn.reactivationCount < 0)
				levelIn.setBlockAndUpdate(posIn, stateIn.setValue(AncientNetherPortalBlock.PORTAL_MODE, PortalMode.ACTIVE));
		}
	}

	private static final void clientTick(final Level levelIn, final BlockPos posIn, final BlockState stateIn, final AncientNetherPortalBlockEntity blockEntityIn) {
		final float rotYMult = 12.5F + blockEntityIn.scale * 5.0F;
		final float rotXMult = blockEntityIn.scale * 0.005F;
		final float rotZMult = blockEntityIn.scale * 0.005F;
		final float scaleMult = 0.005F;
		blockEntityIn.prevRotY = blockEntityIn.rotY;
		blockEntityIn.prevRotX = blockEntityIn.rotX;
		blockEntityIn.prevRotZ = blockEntityIn.rotZ;
		blockEntityIn.prevScale = blockEntityIn.scale;
		blockEntityIn.rotY += blockEntityIn.flag ? -rotYMult : rotYMult;
		blockEntityIn.rotX += blockEntityIn.flag2 ? -rotXMult : rotXMult;
		blockEntityIn.rotZ += blockEntityIn.flag3 ? -rotZMult : rotZMult;
		blockEntityIn.scale += blockEntityIn.flag4 ? -scaleMult : scaleMult;
		if (blockEntityIn.rotY >= 360.0F * blockEntityIn.yRotations) {
			blockEntityIn.flag = true;
		} else if (blockEntityIn.rotY < 0.0F) {
			blockEntityIn.flag = false;
			blockEntityIn.yRotations = 5 + (4 - levelIn.random.nextInt(levelIn.random.nextInt(5) + 1));
		}
		if (blockEntityIn.rotX >= blockEntityIn.xRotations) {
			blockEntityIn.flag2 = true;
		} else if (blockEntityIn.rotX < 0.0F) {
			blockEntityIn.flag2 = false;
			blockEntityIn.xRotations = 1 + levelIn.random.nextInt(levelIn.random.nextInt(10) + 1);
		}
		if (blockEntityIn.rotZ >= blockEntityIn.zRotations) {
			blockEntityIn.flag3 = true;
		} else if (blockEntityIn.rotZ < 0.0F) {
			blockEntityIn.flag3 = false;
			blockEntityIn.zRotations = 1 + levelIn.random.nextInt(levelIn.random.nextInt(10) + 1);
		}
		if (blockEntityIn.scale >= 1.0F) {
			blockEntityIn.flag4 = true;
		} else if (blockEntityIn.scale < 0.0F) {
			blockEntityIn.flag4 = false;
		}
	}

	public final void setReactivationCount(final int countIn) {
		this.reactivationCount = countIn;
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
