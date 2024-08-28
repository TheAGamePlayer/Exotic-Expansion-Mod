package dev.theagameplayer.exoticexpansion.world.level;

import java.util.Optional;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Logger;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.extensions.IOwnedSpawner;
import net.neoforged.neoforge.event.EventHooks;

public abstract class SpawnerSlimeSpawner implements IOwnedSpawner {
	private static final Logger LOGGER = ExoticExpansionMod.LOGGER;
	private SimpleWeightedRandomList<SpawnData> spawnPotentials = SimpleWeightedRandomList.empty();
	private SpawnData nextSpawnData;
	private double spin;
	private double oSpin;
	private int spawnDelay = 20;
	private int minSpawnDelay = 200;
	private int maxSpawnDelay = 800;
	private int spawnCount = 4;
	@Nullable
	private Entity displayEntity;
	private int maxNearbyEntities = 6;
	private int requiredPlayerRange = 16;
	private int spawnRange = 4;

	public void setEntityId(final EntityType<?> pEntityType, final @Nullable Level pLevel, final RandomSource pRandom, final BlockPos pPos) {
		this.getOrCreateNextSpawnData(pLevel, pRandom, pPos)
		.getEntityToSpawn()
		.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(pEntityType).toString());
	}

	private final boolean isNearPlayer(final Level pLevel, final BlockPos pPos) {
		return pLevel.hasNearbyAlivePlayer(pPos.getX() + 0.5D, pPos.getY() + 0.5D, pPos.getZ() + 0.5D, this.requiredPlayerRange);
	}

	public void clientTick(final Level pLevel, final BlockPos pBlockPos, final Vec3 pPos, final int pSize) {
		if (!this.isNearPlayer(pLevel, pBlockPos)) {
			this.oSpin = this.spin;
		} else {
			final RandomSource randomSource = pLevel.getRandom();
			final float scale = 0.255F * pSize;
			final float scale2 = scale * 2;
			final double x = pPos.x + randomSource.nextDouble() * scale2 - scale;
			final double y = pPos.y + randomSource.nextDouble() * scale2 - scale;
			final double z = pPos.z + randomSource.nextDouble() * scale2 - scale;
			pLevel.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, 0.0D, 0.0D);
			pLevel.addParticle(ParticleTypes.FLAME, x, y, z, 0.0D, 0.0D, 0.0D);
			if (this.spawnDelay > 0)
				--this.spawnDelay;
			this.oSpin = this.spin;
			this.spin = (this.spin + (double)(1000.0F / ((float)this.spawnDelay + 200.0F))) % 360.0D;
		}
	}

	public void serverTick(final ServerLevel pServerLevel, final BlockPos pPos, final boolean isMadIn) {
		this.minSpawnDelay = isMadIn ? 150 : 200;
		this.maxSpawnDelay = isMadIn ? 600 : 800;
		this.maxNearbyEntities = isMadIn ? 8 : 6;
		if (this.isNearPlayer(pServerLevel, pPos)) {
			if (this.spawnDelay < 0)
				this.delay(pServerLevel, pPos);
			if (this.spawnDelay > 0) {
				this.spawnDelay -= isMadIn ? 2 : 1;
			} else {
				boolean flag = false;
				final RandomSource randomSource = pServerLevel.getRandom();
				final SpawnData spawnData = this.getOrCreateNextSpawnData(pServerLevel, randomSource, pPos);
				for (int i = 0; i < this.spawnCount; ++i) {
					final CompoundTag compoundTag = spawnData.getEntityToSpawn();
					final Optional<EntityType<?>> optional = EntityType.by(compoundTag);
					if (optional.isEmpty()) {
						this.delay(pServerLevel, pPos);
						return;
					}
					final ListTag listTag = compoundTag.getList("Pos", 6);
					final int s = listTag.size();
					final double x = s >= 1 ? listTag.getDouble(0) : pPos.getX() + (randomSource.nextDouble() - randomSource.nextDouble()) * this.spawnRange + 0.5D;
					final double y = s >= 2 ? listTag.getDouble(1) : pPos.getY() + randomSource.nextInt(3) - 1;
					final double z = s >= 3 ? listTag.getDouble(2) : pPos.getZ() + (randomSource.nextDouble() - randomSource.nextDouble()) * this.spawnRange + 0.5D;
					if (pServerLevel.noCollision(optional.get().getSpawnAABB(x, y, z))) {
						final BlockPos blockPos = BlockPos.containing(x, y, z);
						if (spawnData.getCustomSpawnRules().isPresent()) {
							if (!optional.get().getCategory().isFriendly() && pServerLevel.getDifficulty() == Difficulty.PEACEFUL)
								continue;
							final SpawnData.CustomSpawnRules spawnRules = spawnData.getCustomSpawnRules().get();
							if (!spawnRules.isValidPosition(blockPos, pServerLevel))
								continue;
						} else if (!SpawnPlacements.checkSpawnRules(optional.get(), pServerLevel, MobSpawnType.SPAWNER, blockPos, pServerLevel.getRandom())) {
							continue;
						}
						final Entity entity = EntityType.loadEntityRecursive(compoundTag, pServerLevel, e -> {
							e.moveTo(x, y, z, e.getYRot(), e.getXRot());
							return e;
						});
						if (entity == null) {
							this.delay(pServerLevel, pPos);
							return;
						}
						final int a = pServerLevel.getEntities(EntityTypeTest.forExactClass(entity.getClass()), new AABB(pPos.getX(), pPos.getY(), pPos.getZ(), pPos.getX() + 1, pPos.getY() + 1, pPos.getZ() + 1).inflate(this.spawnRange), EntitySelector.NO_SPECTATORS).size();
						if (a >= this.maxNearbyEntities) {
							this.delay(pServerLevel, pPos);
							return;
						}
						entity.moveTo(entity.getX(), entity.getY(), entity.getZ(), randomSource.nextFloat() * 360.0F, 0.0F);
						if (entity instanceof Mob mob) {
							if (!EventHooks.checkSpawnPositionSpawner(mob, pServerLevel, MobSpawnType.SPAWNER, spawnData, null)) continue;
							final boolean flag1 = this.nextSpawnData.getEntityToSpawn().size() == 1 && this.nextSpawnData.getEntityToSpawn().contains("id", 8);
							EventHooks.finalizeMobSpawnSpawner(mob, pServerLevel, pServerLevel.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.SPAWNER, null, this, flag1);
							spawnData.getEquipment().ifPresent(mob::equip);
						}
						if (!pServerLevel.tryAddFreshEntityWithPassengers(entity)) {
							this.delay(pServerLevel, pPos);
							return;
						}
						pServerLevel.levelEvent(2004, pPos, 0);
						pServerLevel.gameEvent(entity, GameEvent.ENTITY_PLACE, blockPos);
						if (entity instanceof Mob)
							((Mob)entity).spawnAnim();
						flag = true;
					}
				}
				if (flag)
					this.delay(pServerLevel, pPos);
			}
		}
	}

	public void load(@Nullable final Level pLevel, final BlockPos pPos, final CompoundTag pTag) {
		this.spawnDelay = pTag.getShort("Delay");
		final boolean flag = pTag.contains("SpawnData", 10);
		final boolean flag1 = pTag.contains("SpawnPotentials", 9);
        if (flag) {
            SpawnData spawndata = SpawnData.CODEC
                .parse(NbtOps.INSTANCE, pTag.getCompound("SpawnData"))
                .resultOrPartial(id -> LOGGER.warn("Invalid SpawnData: {}", id))
                .orElseGet(SpawnData::new);
            this.setNextSpawnData(pLevel, pPos, spawndata);
        }
        if (flag1) {
            final ListTag listTag = pTag.getList("SpawnPotentials", 10);
            this.spawnPotentials = SpawnData.LIST_CODEC
                .parse(NbtOps.INSTANCE, listTag)
                .resultOrPartial(id -> LOGGER.warn("Invalid SpawnPotentials list: {}", id))
                .orElseGet(SimpleWeightedRandomList::empty);
        } else {
            this.spawnPotentials = SimpleWeightedRandomList.single(this.nextSpawnData != null ? this.nextSpawnData : new SpawnData());
        }
		if (pTag.contains("MinSpawnDelay", 99)) {
			this.minSpawnDelay = pTag.getShort("MinSpawnDelay");
			this.maxSpawnDelay = pTag.getShort("MaxSpawnDelay");
			this.spawnCount = pTag.getShort("SpawnCount");
		}
		if (pTag.contains("MaxNearbyEntities", 99)) {
			this.maxNearbyEntities = pTag.getShort("MaxNearbyEntities");
			this.requiredPlayerRange = pTag.getShort("RequiredPlayerRange");
		}
		if (pTag.contains("SpawnRange", 99))
			this.spawnRange = pTag.getShort("SpawnRange");
		this.displayEntity = null;
	}

	public CompoundTag save(final CompoundTag pTag) { //No Idea why Mojang is converting to shorts...
		pTag.putShort("Delay", (short)this.spawnDelay);
		pTag.putShort("MinSpawnDelay", (short)this.minSpawnDelay);
		pTag.putShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
		pTag.putShort("SpawnCount", (short)this.spawnCount);
		pTag.putShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
		pTag.putShort("RequiredPlayerRange", (short)this.requiredPlayerRange);
		pTag.putShort("SpawnRange", (short)this.spawnRange);
		pTag.put("SpawnData", SpawnData.CODEC.encodeStart(NbtOps.INSTANCE, this.nextSpawnData).getOrThrow(id -> {
			return new IllegalStateException("Invalid SpawnData: " + id);
		}));
		pTag.put("SpawnPotentials", SpawnData.LIST_CODEC.encodeStart(NbtOps.INSTANCE, this.spawnPotentials).result().orElseThrow());
		return pTag;
	}

	@Nullable
	public Entity getOrCreateDisplayEntity(final Level pLevel, final BlockPos pPos) {
		if (this.displayEntity == null) {
            final CompoundTag compoundTag = this.getOrCreateNextSpawnData(pLevel, pLevel.getRandom(), pPos).getEntityToSpawn();
            if (!compoundTag.contains("id", 8)) return null;
			this.displayEntity = EntityType.loadEntityRecursive(this.nextSpawnData.getEntityToSpawn(), pLevel, Function.identity());
		}
		return this.displayEntity;
	}

	public boolean onEventTriggered(final Level pLevel, final int pEventId) {
		if (pEventId == 1) {
			if (pLevel.isClientSide)
				this.spawnDelay = this.minSpawnDelay;
			return true;
		} else {
			return false;
		}
	}
	
	public void setNextSpawnData(@Nullable final Level pLevel, final BlockPos pPos, final SpawnData pSpawnData) {
		this.nextSpawnData = pSpawnData;
	}

	private SpawnData getOrCreateNextSpawnData(final @Nullable Level pLevel, final RandomSource pRandom, final BlockPos pPos) {
		if (this.nextSpawnData != null) {
			return this.nextSpawnData;
		} else {
			this.setNextSpawnData(pLevel, pPos, this.spawnPotentials.getRandom(pRandom).map(WeightedEntry.Wrapper::data).orElseGet(SpawnData::new));
			return this.nextSpawnData;
		}
	}

	public double getSpin() {
		return this.spin;
	}

	public double getoSpin() {
		return this.oSpin;
	}

	public abstract void broadcastEvent(final Level pLevel, final BlockPos pPos, final int pEventId);

	private final void delay(final Level pLevel, final BlockPos pPos) {
		final RandomSource randomSource = pLevel.random;
		this.spawnDelay = this.maxSpawnDelay <= this.minSpawnDelay ? this.spawnDelay = this.minSpawnDelay : this.minSpawnDelay + randomSource.nextInt(this.maxSpawnDelay - this.minSpawnDelay);
		this.spawnPotentials.getRandom(randomSource).ifPresent(we -> {
			this.setNextSpawnData(pLevel, pPos, we.data());
		});
		this.broadcastEvent(pLevel, pPos, 1);
	}
}
