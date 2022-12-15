package dev.theagameplayer.blightedworlds.world.entity.monster;

import javax.annotation.Nullable;

import dev.theagameplayer.blightedworlds.data.loot.packs.BWEntityLoot;
import dev.theagameplayer.blightedworlds.registries.BWEntityTypes;
import dev.theagameplayer.blightedworlds.registries.BWParticleTypes;
import dev.theagameplayer.blightedworlds.world.level.spawner.SpawnerSlimeSpawner;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public final class SpawnerSlime extends Slime {
	private static final EntityDataAccessor<Boolean> ACTIVE = SynchedEntityData.defineId(SpawnerSlime.class, EntityDataSerializers.BOOLEAN);
	private final SpawnerSlimeSpawner spawner;
	private final Runnable ticker;
	private boolean hasChild;

	public SpawnerSlime(final EntityType<? extends SpawnerSlime> entityTypeIn, final Level levelIn) {
		super(entityTypeIn, levelIn);
		this.spawner = new SpawnerSlimeSpawner() {
			@Override
			public void broadcastEvent(final Level levelIn, final BlockPos posIn, final int idIn) {
				SpawnerSlime.this.getLevel().broadcastEntityEvent(SpawnerSlime.this, (byte)idIn);
			}

			@Override
			@Nullable
			public Entity getSpawnerEntity() {
				return SpawnerSlime.this;
			}
		};
		this.spawner.setEntityId(entityTypeIn);
		this.ticker = this.getLevel() instanceof ServerLevel ? () -> {
			this.spawner.serverTick((ServerLevel)this.getLevel(), this.blockPosition(), this.getTarget() != null);
		} : () -> {
			this.spawner.clientTick(this.getLevel(), this.blockPosition());
		};
		this.hasChild = true;
	}

	@Override
	protected final void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ACTIVE, false);
	}

	@Override
	public final void addAdditionalSaveData(final CompoundTag compoundIn) {
		super.addAdditionalSaveData(compoundIn);
		this.spawner.save(compoundIn);
		compoundIn.putBoolean("SpawnerActive", this.isSpawnerActive());
	}

	@Override
	public final void readAdditionalSaveData(final CompoundTag compoundIn) {
		super.readAdditionalSaveData(compoundIn);
		this.setSpawnerActivity(compoundIn.getBoolean("SpawnerActive"));
		this.spawner.load(this.getLevel(), this.blockPosition(), compoundIn);
		//TODO: Check if Size is needed
	}

	@Override
	public final EntityType<SpawnerSlime> getType() {
		return BWEntityTypes.SPAWNER_SLIME.get();
	}

	@Override
	public final void tick() {
		super.tick();
		if (this.entityData.get(ACTIVE))
			this.ticker.run();
	}

	@Override
	protected ResourceLocation getDefaultLootTable() {
		return this.isTiny() ? (this.isSpawnerActive() ? BWEntityLoot.ENTITIES_SPAWNER_SLIME_ACTIVE : BWEntityLoot.ENTITIES_SPAWNER_SLIME_INACTIVE) : BuiltInLootTables.EMPTY;
	}

	@Override
	public final void setSize(final int sizeIn, final boolean resetHealthIn) {
		super.setSize(sizeIn, resetHealthIn);
		this.getAttribute(Attributes.ARMOR).setBaseValue((sizeIn * 3) * 1.25D);
	}

	public final SpawnerSlimeSpawner getSpawnerBaseLogic() {
		return this.spawner;
	}

	public final boolean isSpawnerActive() {
		return this.entityData.get(ACTIVE);
	}

	protected final void setSpawnerActivity(final boolean activeIn) {
		this.entityData.set(ACTIVE, activeIn);
	}
	
	public final boolean hasChild() {
		return this.hasChild;
	}

	public final void setHasChild(final boolean hasChildIn) {
		this.hasChild = hasChildIn;
	}
	
	@Override
	protected ParticleOptions getParticleType() {
		return this.isSpawnerActive() ? BWParticleTypes.ACTIVE_SPAWNER_SLIME_BALL.get() : BWParticleTypes.INACTIVE_SPAWNER_SLIME_BALL.get();
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
	public void remove(final Entity.RemovalReason reasonIn) {
		final int size = this.getSize();
		if (!this.level.isClientSide && size > 1 && this.isDeadOrDying()) {
			final float d = size / 4.0F;
			final int j = size / 2;
			final int k = 2 + this.random.nextInt(3);
			for(int l = 0; l < k; ++l) {
				final double d1 = ((l % 2) - 0.5F) * d;
				final double d2 = ((l / 2) - 0.5F) * d;
				final SpawnerSlime spawnerSlime = this.getType().create(this.level);
				if (this.isPersistenceRequired())
					spawnerSlime.setPersistenceRequired();
				spawnerSlime.setCustomName(this.getCustomName());
				spawnerSlime.setNoAi(this.isNoAi());
				spawnerSlime.setInvulnerable(this.isInvulnerable());
				spawnerSlime.setSize(j, true);
				spawnerSlime.setSpawnerActivity(this.isSpawnerActive());
				spawnerSlime.moveTo(this.getX() + d1, this.getY() + 0.5D, this.getZ() + d2, this.random.nextFloat() * 360.0F, 0.0F);
				this.level.addFreshEntity(spawnerSlime);
			}
		}
		this.setRemoved(reasonIn);
		this.invalidateCaps();
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
	public final SpawnGroupData finalizeSpawn(final ServerLevelAccessor levelIn, final DifficultyInstance difficultyIn, final MobSpawnType mobSpawnTypeIn, @Nullable final SpawnGroupData spawnDataIn, @Nullable final CompoundTag compoundIn) {
		this.entityData.set(ACTIVE, mobSpawnTypeIn != MobSpawnType.SPAWNER);
		return super.finalizeSpawn(levelIn, difficultyIn, mobSpawnTypeIn, spawnDataIn, compoundIn);
	}

	@Override
	public final void handleEntityEvent(final byte idIn) {
		this.spawner.onEventTriggered(this.getLevel(), idIn);
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	public static final boolean checkSpawnerSlimeSpawnRules(final EntityType<SpawnerSlime> entityTypeIn, final LevelAccessor levelIn, final MobSpawnType mobSpawnTypeIn, final BlockPos posIn, final RandomSource randomIn) {
		return levelIn.getDifficulty() != Difficulty.PEACEFUL;
	}
}
