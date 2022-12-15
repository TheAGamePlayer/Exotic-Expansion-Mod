package dev.theagameplayer.blightedworlds.world.level.spawner;

import java.util.Optional;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class SpawnerSlimeSpawner { //Code from BaseSpawner.class
	private static final Logger LOGGER = LogManager.getLogger(BlightedWorldsMod.MODID);
	private SimpleWeightedRandomList<SpawnData> spawnPotentials = SimpleWeightedRandomList.empty();
	private SpawnData nextSpawnData = new SpawnData();
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

	public void setEntityId(final EntityType<?> entityTypeIn) {
		this.nextSpawnData.getEntityToSpawn().putString("id", ForgeRegistries.ENTITY_TYPES.getKey(entityTypeIn).toString());
	}

	public void clientTick(final Level levelIn, final BlockPos posIn) {
		if (!this.isNearPlayer(levelIn, posIn)) {
			this.oSpin = this.spin;
		} else {
			final RandomSource randomSource = levelIn.getRandom();
			final double x = posIn.getX() + randomSource.nextDouble();
			final double y = posIn.getY() + randomSource.nextDouble();
			final double z = posIn.getZ() + randomSource.nextDouble();
			levelIn.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, 0.0D, 0.0D);
			levelIn.addParticle(ParticleTypes.FLAME, x, y, z, 0.0D, 0.0D, 0.0D);
			if (this.spawnDelay > 0)
				--this.spawnDelay;
			this.oSpin = this.spin;
			this.spin = (this.spin + (double)(1000.0F / ((float)this.spawnDelay + 200.0F))) % 360.0D;
		}
	}

	public void serverTick(final ServerLevel levelIn, final BlockPos posIn, final boolean isMadIn) {
		this.minSpawnDelay = isMadIn ? 150 : 200;
		this.maxSpawnDelay = isMadIn ? 600 : 800;
		if (this.isNearPlayer(levelIn, posIn)) {
			if (this.spawnDelay < 0)
				this.delay(levelIn, posIn);
			if (this.spawnDelay > 0) {
				this.spawnDelay -= isMadIn ? 2 : 1;
			} else {
				boolean flag = false;
				for(int i = 0; i < this.spawnCount; ++i) {
					final CompoundTag compoundTag = this.nextSpawnData.getEntityToSpawn();
					final Optional<EntityType<?>> optional = EntityType.by(compoundTag);
					if (optional.isEmpty()) {
						this.delay(levelIn, posIn);
						return;
					}
					final ListTag listTag = compoundTag.getList("Pos", 6);
					final int s = listTag.size();
					final RandomSource randomSource = levelIn.getRandom();
					final double x = s >= 1 ? listTag.getDouble(0) : posIn.getX() + (randomSource.nextDouble() - randomSource.nextDouble()) * this.spawnRange + 0.5D;
					final double y = s >= 2 ? listTag.getDouble(1) : posIn.getY() + randomSource.nextInt(3) - 1;
					final double z = s >= 3 ? listTag.getDouble(2) : posIn.getZ() + (randomSource.nextDouble() - randomSource.nextDouble()) * this.spawnRange + 0.5D;
					if (levelIn.noCollision(optional.get().getAABB(x, y, z))) {
						final BlockPos blockPos = new BlockPos(x, y, z);
						if (this.nextSpawnData.getCustomSpawnRules().isPresent()) {
							if (!optional.get().getCategory().isFriendly() && levelIn.getDifficulty() == Difficulty.PEACEFUL)
								continue;
							final SpawnData.CustomSpawnRules spawnRules = this.nextSpawnData.getCustomSpawnRules().get();
							if (!spawnRules.blockLightLimit().isValueInRange(levelIn.getBrightness(LightLayer.BLOCK, blockPos)) || !spawnRules.skyLightLimit().isValueInRange(levelIn.getBrightness(LightLayer.SKY, blockPos)))
								continue;
						} else if (!SpawnPlacements.checkSpawnRules(optional.get(), levelIn, MobSpawnType.SPAWNER, blockPos, levelIn.getRandom())) {
							continue;
						}
						final Entity entity = EntityType.loadEntityRecursive(compoundTag, levelIn, e -> {
							e.moveTo(x, y, z, e.getYRot(), e.getXRot());
							return e;
						});
						if (entity == null) {
							this.delay(levelIn, posIn);
							return;
						}
						final int a = levelIn.getEntitiesOfClass(entity.getClass(), new AABB(posIn.getX(), posIn.getY(), posIn.getZ(), posIn.getX() + 1, posIn.getY() + 1, posIn.getZ() + 1).inflate(this.spawnRange)).size();
						if (a >= this.maxNearbyEntities) {
							this.delay(levelIn, posIn);
							return;
						}
						entity.moveTo(entity.getX(), entity.getY(), entity.getZ(), randomSource.nextFloat() * 360.0F, 0.0F);
						if (entity instanceof Mob) {
							final Mob mob = (Mob)entity;
							if (this.nextSpawnData.getCustomSpawnRules().isEmpty() && !mob.checkSpawnRules(levelIn, MobSpawnType.SPAWNER) || !mob.checkSpawnObstruction(levelIn))
								continue;
							if (this.nextSpawnData.getEntityToSpawn().size() == 1 && this.nextSpawnData.getEntityToSpawn().contains("id", 8))
								mob.finalizeSpawn(levelIn, levelIn.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.SPAWNER, null, null);
						}
						if (!levelIn.tryAddFreshEntityWithPassengers(entity)) {
							this.delay(levelIn, posIn);
							return;
						}
						levelIn.levelEvent(2004, posIn, 0);
						levelIn.gameEvent(entity, GameEvent.ENTITY_PLACE, blockPos);
						if (entity instanceof Mob)
							((Mob)entity).spawnAnim();
						flag = true;
					}
				}
				if (flag)
					this.delay(levelIn, posIn);
			}
		}
	}

	public void load(@Nullable final Level levelIn, final BlockPos posIn, final CompoundTag compoundIn) {
		this.spawnDelay = compoundIn.getShort("Delay");
		final boolean flag = compoundIn.contains("SpawnPotentials", 9);
		final boolean flag1 = compoundIn.contains("SpawnData", 10);
		if (!flag) {
			SpawnData spawnData;
			if (flag1) {
				spawnData = SpawnData.CODEC.parse(NbtOps.INSTANCE, compoundIn.getCompound("SpawnData")).resultOrPartial(id -> {
					LOGGER.warn("Invalid SpawnData: {}", id);
				}).orElseGet(SpawnData::new);
			} else {
				spawnData = new SpawnData();
			}
			this.spawnPotentials = SimpleWeightedRandomList.single(spawnData);
			this.setNextSpawnData(levelIn, posIn, spawnData);
		} else {
			final ListTag listTag = compoundIn.getList("SpawnPotentials", 10);
			this.spawnPotentials = SpawnData.LIST_CODEC.parse(NbtOps.INSTANCE, listTag).resultOrPartial(id -> {
				LOGGER.warn("Invalid SpawnPotentials list: {}", id);
			}).orElseGet(SimpleWeightedRandomList::empty);
			if (flag1) {
				final SpawnData spawnData1 = SpawnData.CODEC.parse(NbtOps.INSTANCE, compoundIn.getCompound("SpawnData")).resultOrPartial(id -> {
					LOGGER.warn("Invalid SpawnData: {}", id);
				}).orElseGet(SpawnData::new);
				this.setNextSpawnData(levelIn, posIn, spawnData1);
			} else {
				this.spawnPotentials.getRandom(levelIn.getRandom()).ifPresent(we -> {
					this.setNextSpawnData(levelIn , posIn, we.getData());
				});
			}
		}
		if (compoundIn.contains("MinSpawnDelay", 99)) {
			this.minSpawnDelay = compoundIn.getShort("MinSpawnDelay");
			this.maxSpawnDelay = compoundIn.getShort("MaxSpawnDelay");
			this.spawnCount = compoundIn.getShort("SpawnCount");
		}
		if (compoundIn.contains("MaxNearbyEntities", 99)) {
			this.maxNearbyEntities = compoundIn.getShort("MaxNearbyEntities");
			this.requiredPlayerRange = compoundIn.getShort("RequiredPlayerRange");
		}
		if (compoundIn.contains("SpawnRange", 99))
			this.spawnRange = compoundIn.getShort("SpawnRange");
		this.displayEntity = null;
	}

	public CompoundTag save(final CompoundTag compoundIn) { //No Idea why Mojang is converting to shorts...
		compoundIn.putShort("Delay", (short)this.spawnDelay);
		compoundIn.putShort("MinSpawnDelay", (short)this.minSpawnDelay);
		compoundIn.putShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
		compoundIn.putShort("SpawnCount", (short)this.spawnCount);
		compoundIn.putShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
		compoundIn.putShort("RequiredPlayerRange", (short)this.requiredPlayerRange);
		compoundIn.putShort("SpawnRange", (short)this.spawnRange);
		compoundIn.put("SpawnData", SpawnData.CODEC.encodeStart(NbtOps.INSTANCE, this.nextSpawnData).result().orElseThrow(() -> {
			return new IllegalStateException("Invalid SpawnData");
		}));
		compoundIn.put("SpawnPotentials", SpawnData.LIST_CODEC.encodeStart(NbtOps.INSTANCE, this.spawnPotentials).result().orElseThrow());
		return compoundIn;
	}

	@Nullable
	public Entity getOrCreateDisplayEntity(final Level levelIn) {
		if (this.displayEntity == null)
			this.displayEntity = EntityType.loadEntityRecursive(this.nextSpawnData.getEntityToSpawn(), levelIn, Function.identity());
		return this.displayEntity;
	}

	public boolean onEventTriggered(final Level levelIn, final int eventIn) {
		if (eventIn == 1) {
			if (levelIn.isClientSide)
				this.spawnDelay = this.minSpawnDelay;
			return true;
		} else {
			return false;
		}
	}

	public void setNextSpawnData(@Nullable final Level levelIn, final BlockPos posIn, final SpawnData spawnerDataIn) {
		this.nextSpawnData = spawnerDataIn;
	}

	public double getSpin() {
		return this.spin;
	}

	public double getoSpin() {
		return this.oSpin;
	}
	
	public abstract void broadcastEvent(final Level levelIn, final BlockPos posIn, final int eventIn);

	public abstract Entity getSpawnerEntity();
	
	private final boolean isNearPlayer(final Level levelIn, final BlockPos posIn) {
		return levelIn.hasNearbyAlivePlayer(posIn.getX() + 0.5D, posIn.getY() + 0.5D, posIn.getZ() + 0.5D, this.requiredPlayerRange);
	}
	
	private final void delay(final Level levelIn, final BlockPos posIn) {
		final RandomSource randomSource = levelIn.random;
		this.spawnDelay = this.maxSpawnDelay <= this.minSpawnDelay ? this.spawnDelay = this.minSpawnDelay : this.minSpawnDelay + randomSource.nextInt(this.maxSpawnDelay - this.minSpawnDelay);
		this.spawnPotentials.getRandom(randomSource).ifPresent(we -> {
			this.setNextSpawnData(levelIn, posIn, we.getData());
		});
		this.broadcastEvent(levelIn, posIn, 1);
	}
}
