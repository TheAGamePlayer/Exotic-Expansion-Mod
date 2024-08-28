package dev.theagameplayer.exoticexpansion.world.entity.monster;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.mojang.datafixers.util.Either;

import dev.theagameplayer.exoticexpansion.data.loot.packs.EEEntityLoot;
import dev.theagameplayer.exoticexpansion.registries.EEEntityTypes;
import dev.theagameplayer.exoticexpansion.registries.EEParticleTypes;
import dev.theagameplayer.exoticexpansion.world.level.SpawnerSlimeSpawner;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.event.EventHooks;

public final class SpawnerSlime extends Slime {
	private static final EntityDataAccessor<Boolean> ACTIVE = SynchedEntityData.defineId(SpawnerSlime.class, EntityDataSerializers.BOOLEAN);
	private final SpawnerSlimeSpawner spawner;
	private final Runnable ticker;
	private boolean hasChild;

	public SpawnerSlime(final EntityType<? extends SpawnerSlime> pEntityType, final Level pLevel) {
		super(pEntityType, pLevel);
		this.spawner = new SpawnerSlimeSpawner() {
			@Override
			public final void broadcastEvent(final Level pLevel, final BlockPos pPos, final int pId) {
				SpawnerSlime.this.level().broadcastEntityEvent(SpawnerSlime.this, (byte)pId);
			}

			@Override
			@Nullable
			public final Either<BlockEntity, Entity> getOwner() {
				return Either.right(SpawnerSlime.this);
			}
		};
		this.spawner.setEntityId(pEntityType, pLevel, this.random, this.blockPosition());
		this.ticker = this.level() instanceof ServerLevel level ? () -> {
			this.spawner.serverTick(level, this.blockPosition(), this.getTarget() != null || this.isInLava());
		} : () -> {
			this.spawner.clientTick(this.level(), this.blockPosition(), this.position(), this.getSize());
		};
		this.hasChild = true;
	}

	@Override
	protected final void defineSynchedData(SynchedEntityData.Builder pBuilder) {
		super.defineSynchedData(pBuilder);
		pBuilder.define(ACTIVE, false);
	}

	@Override
	public final void addAdditionalSaveData(final CompoundTag pDataTag) {
		super.addAdditionalSaveData(pDataTag);
		this.spawner.save(pDataTag);
		pDataTag.putBoolean("SpawnerActive", this.isSpawnerActive());
	}

	@Override
	public final void readAdditionalSaveData(final CompoundTag pDataTag) {
		super.readAdditionalSaveData(pDataTag);
		this.setSpawnerActivity(pDataTag.getBoolean("SpawnerActive"));
		this.spawner.load(this.level(), this.blockPosition(), pDataTag);
	}

	@Override
	public final EntityType<SpawnerSlime> getType() {
		return EEEntityTypes.SPAWNER_SLIME.get();
	}

	@Override
	public final void tick() {
		super.tick();
		if (this.entityData.get(ACTIVE))
			this.ticker.run();
	}

	@Override
	protected ResourceKey<LootTable> getDefaultLootTable() {
		return this.isTiny() ? (this.isSpawnerActive() ? EEEntityLoot.ENTITIES_SPAWNER_SLIME_ACTIVE : EEEntityLoot.ENTITIES_SPAWNER_SLIME_INACTIVE) : BuiltInLootTables.EMPTY;
	}

	@Override
	public final void setSize(final int pSize, final boolean pResetHealth) {
		super.setSize(pSize, pResetHealth);
		this.getAttribute(Attributes.ARMOR).setBaseValue((pSize * 3) * 1.25D);
	}

	public final SpawnerSlimeSpawner getSpawnerBaseLogic() {
		return this.spawner;
	}

	public final boolean isSpawnerActive() {
		return this.entityData.get(ACTIVE);
	}

	protected final void setSpawnerActivity(final boolean pActive) {
		this.entityData.set(ACTIVE, pActive);
	}

	public final boolean hasChild() {
		return this.hasChild;
	}

	public final void setHasChild(final boolean pHasChild) {
		this.hasChild = pHasChild;
	}

	@Override
	protected ParticleOptions getParticleType() {
		return this.isSpawnerActive() ? EEParticleTypes.ACTIVE_SPAWNER_SLIME_BALL.get() : EEParticleTypes.INACTIVE_SPAWNER_SLIME_BALL.get();
	}

	@Override
	protected void decreaseSquish() {
		this.targetSquish *= 0.4F;
	}

	@Override
	protected int getJumpDelay() {
		return this.random.nextInt(25) + 15;
	}

	@Override
	public void remove(final Entity.RemovalReason pReason) {
		final int size = this.getSize();
		final Level level = this.level();
		if (!level.isClientSide && size > 1 && this.isDeadOrDying()) {
			final float d = size / 4.0F;
			final int j = size / 2;
			final int k = 2 + this.random.nextInt(3);
			final List<Mob> children = new ArrayList<>();
			for (int l = 0; l < k; ++l) {
				final double d1 = ((l % 2) - 0.5F) * d;
				final double d2 = ((l / 2) - 0.5F) * d;
				final SpawnerSlime spawnerSlime = this.getType().create(this.level());
				if (this.isPersistenceRequired())
					spawnerSlime.setPersistenceRequired();
				spawnerSlime.setCustomName(this.getCustomName());
				spawnerSlime.setNoAi(this.isNoAi());
				spawnerSlime.setInvulnerable(this.isInvulnerable());
				spawnerSlime.setSize(j, true);
				spawnerSlime.setSpawnerActivity(this.isSpawnerActive());
				spawnerSlime.moveTo(this.getX() + d1, this.getY() + 0.5D, this.getZ() + d2, this.random.nextFloat() * 360.0F, 0.0F);
				children.add(spawnerSlime);
			}
			if (!EventHooks.onMobSplit(this, children).isCanceled())
				children.forEach(level::addFreshEntity);
		}
		super.remove(pReason);
	}

	@Override
	protected boolean isDealsDamage() {
		return this.isEffectiveAi();
	}

	@Override
	protected float getAttackDamage() { //TODO: Check if method is needed
		return super.getAttackDamage() + 3.0F;
	}

	@Nullable
	@Override
	public final SpawnGroupData finalizeSpawn(final ServerLevelAccessor pLevel, final DifficultyInstance pDifficulty, final MobSpawnType pMobSpawnType, @Nullable final SpawnGroupData pSpawnGroupData) {
		this.entityData.set(ACTIVE, pMobSpawnType != MobSpawnType.SPAWNER);
		return super.finalizeSpawn(pLevel, pDifficulty, pMobSpawnType, pSpawnGroupData);
	}

	@Override
	public final void handleEntityEvent(final byte pId) {
		this.spawner.onEventTriggered(this.level(), pId);
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	public static final boolean checkSpawnerSlimeSpawnRules(final EntityType<SpawnerSlime> pEntityType, final LevelAccessor pLevel, final MobSpawnType pMobSpawnType, final BlockPos pPos, final RandomSource pRandom) {
		return pLevel.getDifficulty() != Difficulty.PEACEFUL;
	}
}
