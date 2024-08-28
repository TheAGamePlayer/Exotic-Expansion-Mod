package dev.theagameplayer.exoticexpansion.world.level.pillar;

import org.joml.SimplexNoise;

import dev.theagameplayer.exoticexpansion.world.level.levelgen.CavernSettings;
import dev.theagameplayer.exoticexpansion.world.level.levelgen.CavernSettings.CavernFloorSettings;
import dev.theagameplayer.exoticexpansion.world.level.pillar.storage.PillarStorage;
import io.netty.util.concurrent.FastThreadLocal;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;

public final class PillarGenerator {
	private final CavernFloorSettings[] cavernFloorSettings;
	private final int[][][][] widths;
	private final FastThreadLocal<Pillar[][]> pillars = new FastThreadLocal<>();
	private final FastThreadLocal<boolean[][][]> fillY = new FastThreadLocal<>();
	private volatile PillarStorage storage;

	public PillarGenerator(final Holder<CavernSettings> pCavernSettings) {
		this.cavernFloorSettings = pCavernSettings.value().getCavernFloors().toArray(CavernFloorSettings[]::new);
		this.widths = new int[this.cavernFloorSettings.length][][][];
		for (int f = 0; f < this.cavernFloorSettings.length; f++) {
			final CavernFloorSettings cavernFloorSettings = this.cavernFloorSettings[f];
			this.widths[f] = new int[cavernFloorSettings.pillarLayers() + 1][cavernFloorSettings.maxPillarSize() - cavernFloorSettings.minPillarSize() + 1][];
			final float halfHeight = cavernFloorSettings.floorHeight() * 0.5F;
			for (int l = 0; l < cavernFloorSettings.pillarLayers() + 1; l++) {
				for (int s = 1; s <= this.widths[f][l].length; s++) {
					if (l == 0) {
						this.widths[f][l][s - 1] = new int[cavernFloorSettings.floorHeight()];
						final float absSize = 833.0F/(s * cavernFloorSettings.floorHeight());
						for (int y = 0; y < cavernFloorSettings.floorHeight(); y++) {
							final float base = s * y;
							final float maxDist = absSize * Math.abs(y - halfHeight) + 449.0F - absSize * halfHeight; //Avoids random harsh drop offs
							this.widths[f][l][s - 1][y] = (int)Math.min(maxDist, Mth.square(y - halfHeight)/base + base/(-2 * (y - cavernFloorSettings.floorHeight())) + s * 32);
						}
					} else {
						this.widths[f][l][s - 1] = new int[33];
						final int widthSqr = Mth.square(s * (32 - (16/(cavernFloorSettings.pillarLayers() + 1)) * (l - 1)) + 64);
						for (int y = 0; y < 33; y++) {
							final float top = widthSqr * (0.125F * (-y + 34));
							final float bottom = widthSqr * (0.041666667F * y);
							this.widths[f][l][s - 1][y] = (int)Math.min(widthSqr, Math.min(top, bottom));
						}
					}
				}
			}
		}
	}

	public final PillarStorage getStorage() {
		return this.storage;
	}

	public final boolean isInStorage(final ChunkAccess pChunk) {
		return this.storage != null && this.storage.read(new PillarPos(pChunk.getPos())) != null;
	}

	public final void generateIn(final ChunkAccess pChunk, final ResourceKey<Level> pLevel, final MinecraftServer pServer, final long pSeed, final int pMinY) {
		if (this.storage == null)
			this.storage = new PillarStorage(pLevel, pServer);
		final ChunkPos chunkPos = pChunk.getPos();
		final int zoneX = chunkPos.getMinBlockX() >> Pillar.SHIFT;
						final int zoneZ = chunkPos.getMinBlockZ() >> Pillar.SHIFT;
				for (int x = zoneX - 1, x1 = zoneX + 2; x < x1; x++) {
					for (int z = zoneZ - 1, z1 = zoneZ + 2; z < z1; z++) {
						final PillarPos pos = new PillarPos(x, z);
						if (this.storage.read(pos) != null) continue;
						final CompoundTag tag = new CompoundTag();
						for (int f = 0; f < this.cavernFloorSettings.length; f++)
							tag.put("Floor " + f, new Pillar(pSeed, f, pos).write());
						this.storage.write(pos, tag);
					}
				}
	}

	public final void readPillars(final ChunkAccess pChunk) {
		if (this.storage == null) return;
		if (this.fillY.get() == null) {
			this.pillars.set(new Pillar[this.cavernFloorSettings.length][9]);
			this.fillY.set(new boolean[16][16][this.cavernFloorSettings.length]);
		} else {
			final boolean[][][] fillY = this.fillY.get();
			for (int cx = 0; cx < 16; cx++) {
				for (int cz = 0; cz < 16; cz++) {
					for (int f = 0; f < this.cavernFloorSettings.length; f++)
						fillY[cx][cz][f] = false;
				}
			}
		}
		final ChunkPos chunkPos = pChunk.getPos();
		final int zoneX = chunkPos.getMinBlockX() >> Pillar.SHIFT;
					final int zoneZ = chunkPos.getMinBlockZ() >> Pillar.SHIFT;
			for (int f = 0; f < this.cavernFloorSettings.length; f++) {
				final CavernFloorSettings cavernFloorSettings = this.cavernFloorSettings[f];
				if (cavernFloorSettings.pillarRarity() <= 0.0D) continue;
				int p = 0;
				for (int x = zoneX - 1, x1 = zoneX + 2; x < x1; x++) {
					for (int z = zoneZ - 1, z1 = zoneZ + 2; z < z1; z++) {
						this.pillars.get()[f][p] = new Pillar(cavernFloorSettings, this.storage.read(new PillarPos(x, z)).getCompound("Floor " + f));
						p++;
					}
				}
			}
	}

	public final boolean isNearPillar(final ChunkAccess pChunk, final int pFloor, final int pFloorY, final int pX, final int pY, final int pZ, final int pChunkX, final int pChunkZ) {
		final boolean[] fillY = this.fillY.get()[pChunkX][pChunkZ];
		if (fillY[pFloor]) return true;
		final Pillar[] pillars = this.pillars.get()[pFloor];
		final int yIndex = Math.abs(pFloorY - pY);
		final int[][] widths = this.widths[pFloor][0];
		final float noise = SimplexNoise.noise(pX * 0.0078125F, pY * 0.0078125F, pZ * 0.0078125F) * 2.0F;
		for (int p = 0; p < pillars.length; p++) {
			final Pillar pillar = pillars[p];
			final float distSqr = Mth.square(pillar.x + 0.5F - pX) + Mth.square(pillar.z + 0.5F - pZ);
			final int sizeIndex = pillar.size - this.cavernFloorSettings[pFloor].minPillarSize();
			if (!fillY[pFloor] && distSqr < pillar.fillSize) {
				fillY[pFloor] = true;
				return true;
			} else if (distSqr < Mth.square(widths[sizeIndex][yIndex] - noise * pillar.size) || this.isInLayer(pillar.layers, pFloor, sizeIndex, pX, pY, pZ, noise)) return true;
		}
		return false;
	}

	private final boolean isInLayer(final Pillar.Layer[] pLayers, final int pFloor, final int pSizeIndex, final int pX, final int pY, final int pZ, final float pNoise) {
		final int[][][] widths = this.widths[pFloor];
		final float noiseXZ = pNoise * 4.0F;
		final int noiseYT = (int)(pNoise * 2.0F);
		for (int l = 0; l < pLayers.length; l++) { //Bottom to top layer
			if (pY < pLayers[l].y - 23 || pY > pLayers[l].y + 9) continue;
			final int yIndex = Math.abs(pLayers[l].y - 23 - pY - noiseYT);
			if (yIndex < 0 || yIndex > 32) return false;
			final float distSqr = Mth.square(pLayers[l].x + 0.5F - pX - noiseXZ) + Mth.square(pLayers[l].z + 0.5F - pZ - noiseXZ);
			if (distSqr < widths[l + 1][pSizeIndex][yIndex]) return true;
		}
		return false;
	}

	public final class Pillar {
		public static final int SHIFT = 9, MAX_DISTANCE = 1 << SHIFT;
		private final CavernFloorSettings cavernFloorSettings;
		private final int size, fillSize;
		private final int floor, x, z;
		private final Layer[] layers;

		private Pillar(final CavernFloorSettings pCavernFloorSettings, final CompoundTag pTag) { //Loading
			this.cavernFloorSettings = pCavernFloorSettings;
			this.size = pTag.getInt("Size");
			this.fillSize = this.size * 32 + 1;
			this.floor = pTag.getInt("Floor");
			this.x = pTag.getInt("X");
			this.z = pTag.getInt("Z");
			this.layers = new Layer[pTag.getInt("Layers")];
			for (int l = 0; l < layers.length; l++)
				this.layers[l] = new Layer(pTag.getCompound("Layer " + l));
		}

		private Pillar(final long pSeed, final int pFloor, final PillarPos pPos) { //Generating
			final RandomSource random = RandomSource.create((long)pPos.x + (long)pFloor + (long)pPos.z - pSeed);
			this.cavernFloorSettings = PillarGenerator.this.cavernFloorSettings[pFloor];
			this.size = random.nextInt(this.cavernFloorSettings.maxPillarSize() - this.cavernFloorSettings.minPillarSize()) + this.cavernFloorSettings.minPillarSize();
			this.fillSize = this.size * 32 + 1;
			final int seperationDistance = 16 * this.size;
			this.floor = pFloor;
			this.x = (pPos.x << SHIFT) + random.nextInt(MAX_DISTANCE - seperationDistance) + seperationDistance;
			this.z = (pPos.z << SHIFT) + random.nextInt(MAX_DISTANCE - seperationDistance) + seperationDistance;
			this.layers = new Layer[this.cavernFloorSettings.pillarLayers()];
			for (int l = 0; l < this.layers.length; l++)
				this.layers[l] = new Layer(random, l, l == 0 ? -1 : this.layers[l - 1].orientation);
		}

		private final CompoundTag write() {
			final CompoundTag tag = new CompoundTag();
			tag.putInt("Size", this.size);
			tag.putInt("Floor", this.floor);
			tag.putInt("X", this.x);
			tag.putInt("Z", this.z);
			tag.putInt("Layers", this.layers.length);
			for (int l = 0; l < this.layers.length; l++)
				tag.put("Layer " + l, this.layers[l].write());
			return tag;
		}

		private final class Layer {
			private final int orientation;
			private final int y, x, z;

			private Layer(final CompoundTag pTag) { //Loading
				this.orientation = pTag.getInt("Orientation");
				this.y = pTag.getInt("Y");
				this.x = pTag.getInt("X");
				this.z = pTag.getInt("Z");
			}

			private Layer(final RandomSource pRandom, final int pIndex, final int pLastOrientation) { //Generating
				this.y = (Pillar.this.cavernFloorSettings.floorHeight()/(Pillar.this.cavernFloorSettings.pillarLayers() + 1)) * (pIndex + 1) - 8 + pRandom.nextInt(16);
				if (pLastOrientation > -1) {
					final int o = pRandom.nextInt(3);
					this.orientation = o < pLastOrientation ? o : o + 1;
				} else {
					this.orientation = pRandom.nextInt(4);
				}
				final int minDegrees = this.orientation * 90;
				final int maxDegrees = (this.orientation + 1) * 90;
				final int pillarWidth = PillarGenerator.this.widths[Pillar.this.floor][0][Pillar.this.size - 1][this.y];
				final int layerDist = pRandom.nextInt(pillarWidth/4) + pillarWidth/2;
				final int degrees = pRandom.nextInt(maxDegrees - minDegrees) + minDegrees;
				final double theta = degrees * Math.PI/180;
				this.x = (int)(Pillar.this.x + layerDist * Math.cos(theta));
				this.z = (int)(Pillar.this.z + layerDist * Math.sin(theta));
			}

			private final CompoundTag write() {
				final CompoundTag tag = new CompoundTag();
				tag.putInt("Orientation", this.orientation);
				tag.putInt("Y", this.y);
				tag.putInt("X", this.x);
				tag.putInt("Z", this.z);
				return tag;
			}
		}
	}
}
