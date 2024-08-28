package dev.theagameplayer.exoticexpansion.world.level.levelgen;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.lang3.mutable.MutableObject;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import dev.theagameplayer.exoticexpansion.world.level.levelgen.CavernSettings.CavernFloorSettings;
import dev.theagameplayer.exoticexpansion.world.level.pillar.PillarGenerator;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

public final class CavernChunkGenerator extends NoiseBasedChunkGenerator {
	public static final MapCodec<CavernChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(builder -> {
		return builder.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter(generator -> {
			return generator.getBiomeSource();
		}), CavernSettings.CODEC.fieldOf("cavern_settings").forGetter(generator -> {
			return generator.cavernSettings;
		}), NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(generator -> {
			return generator.generatorSettings();
		})).apply(builder, builder.stable((biomeSource, cavernSettings, settings) -> new CavernChunkGenerator(biomeSource, cavernSettings, settings, false)));
	});
	private final Holder<CavernSettings> cavernSettings;
	private final PillarGenerator pillarGenerator;

	public CavernChunkGenerator(final BiomeSource pBiomeSource, final Holder<CavernSettings> pCavernSettings, final Holder<NoiseGeneratorSettings> pSettings, final boolean pIsDataGen) {
		super(pBiomeSource, pSettings);
		this.cavernSettings = pCavernSettings;
		this.pillarGenerator = pIsDataGen ? null : new PillarGenerator(pCavernSettings);
	}

	@Override
	protected final MapCodec<? extends CavernChunkGenerator> codec() {
		return CODEC;
	}

	public final PillarGenerator getPillarGenerator() {
		return this.pillarGenerator;
	}

	@Override
	protected final OptionalInt iterateNoiseColumn(final LevelHeightAccessor pLevel, final RandomState pRandom, final int pX, final int pZ, final @Nullable MutableObject<NoiseColumn> pColumn, final @Nullable Predicate<BlockState> pStoppingState) {
		final NoiseGeneratorSettings noiseGenSettings = this.settings.value();
		final NoiseSettings noiseSettings = noiseGenSettings.noiseSettings().clampToHeightAccessor(pLevel);
		final List<CavernFloorSettings> cavernFloors = this.cavernSettings.value().getCavernFloors();
		final int i = noiseSettings.getCellHeight();
		final int j = noiseSettings.minY();
		final int k = Math.floorDiv(j, i);
		final int l = Math.floorDiv(noiseSettings.height(), i);
		if (l <= 0) {
			return OptionalInt.empty();
		} else {
			final BlockState[] aBlockState;
			if (pColumn == null) {
				aBlockState = null;
			} else {
				aBlockState = new BlockState[noiseSettings.height()];
				pColumn.setValue(new NoiseColumn(j, aBlockState));
			}
			final int i1 = noiseSettings.getCellWidth();
			final int j1 = Math.floorDiv(pX, i1);
			final int k1 = Math.floorDiv(pZ, i1);
			final int l1 = Math.floorMod(pX, i1);
			final int i2 = Math.floorMod(pZ, i1);
			final int j2 = j1 * i1;
			final int k2 = k1 * i1;
			final double d0 = (double)l1 / (double)i1;
			final double d1 = (double)i2 / (double)i1;
			final NoiseChunk noiseChunk = new NoiseChunk(1, pRandom, j2, k2, noiseSettings, DensityFunctions.BeardifierMarker.INSTANCE, this.settings.value(), this.globalFluidPicker.get(), Blender.empty());
			final int chunkX = pX & 15;
			final int chunkZ = pZ & 15;
			ChunkAccess chunk = null;
			boolean flag = false;
			if (pLevel instanceof LevelReader level) {
				chunk = level.getChunk(SectionPos.blockToSectionCoord(pX), SectionPos.blockToSectionCoord(pZ));
				flag = this.pillarGenerator.isInStorage(chunk);
			} else if (pLevel instanceof ProtoChunk protoChunk && protoChunk.levelHeightAccessor instanceof ServerLevel level) {
				if (!flag) {
					this.pillarGenerator.generateIn(protoChunk, level.dimension(), level.getServer(), level.getSeed()/2, this.getMinY());
					flag = true;
				}
				chunk = protoChunk;
			}
			this.pillarGenerator.readPillars(chunk);
			noiseChunk.initializeForFirstCellX();
			noiseChunk.advanceCellX(0);
			for (int l2 = l - 1; l2 >= 0; --l2) {
				final int l3 = l2 * i;
				noiseChunk.selectCellYZ(l2, 0);
				for (int i3 = i - 1; i3 >= 0; --i3) {
					final int j3 = (k + l2) * i + i3;
					final double d2 = (double)i3 / (double)i;
					noiseChunk.updateForY(j3, d2);
					noiseChunk.updateForX(pX, d0);
					noiseChunk.updateForZ(pZ, d1);
					BlockState blockState = noiseChunk.getInterpolatedState();
					if (chunk != null && flag) {
						int floorY = j;
						for (int f = 0; f < cavernFloors.size(); f++) {
							final CavernFloorSettings cavernFloorSettings = cavernFloors.get(f);
							if (cavernFloorSettings.pillarRarity() > 0.0F && j3 >= floorY && j3 < floorY + cavernFloorSettings.floorHeight() && !this.pillarGenerator.isNearPillar(chunk, f, floorY, pX, j3, pZ, chunkX, chunkZ) && (blockState == null || blockState.getFluidState().isEmpty()))
								blockState = j3 > this.getSeaLevel() ? Blocks.AIR.defaultBlockState() : noiseGenSettings.defaultFluid();
								floorY += cavernFloorSettings.floorHeight();
						}
					}
					final BlockState blockState1 = blockState == null ? this.settings.value().defaultBlock() : blockState;
					if (aBlockState != null) {
						final int k3 = l3 + i3;
						aBlockState[k3] = blockState1;
					}
					if (pStoppingState != null && pStoppingState.test(blockState1)) {
						noiseChunk.stopInterpolation();
						return OptionalInt.of(j3 + 1);
					}
				}
			}
			noiseChunk.stopInterpolation();
			return OptionalInt.empty();
		}
	}

	@Override
	protected final ChunkAccess doFill(final Blender pBlender, final StructureManager pStructureManager, final RandomState pRandom, final ChunkAccess pChunk, final int pMinCellY, final int pCellCountY) {
		final NoiseGeneratorSettings noiseSettings = this.settings.value();
		final List<CavernFloorSettings> cavernFloors = this.cavernSettings.value().getCavernFloors();
		final NoiseChunk noiseChunk = pChunk.getOrCreateNoiseChunk(chunkAccess -> this.createNoiseChunk(chunkAccess, pStructureManager, pBlender, pRandom));
		final Heightmap heightMap = pChunk.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);
		final Heightmap heightMap1 = pChunk.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);
		final ChunkPos chunkPos = pChunk.getPos();
		final int minChunkX = chunkPos.getMinBlockX();
		final int minChunkZ = chunkPos.getMinBlockZ();
		final Aquifer aquifer = noiseChunk.aquifer();
		noiseChunk.initializeForFirstCellX();
		final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		final int cellWidth = noiseChunk.cellWidth();
		final int cellHeight = noiseChunk.cellHeight();
		final int cellXW = 16 / cellWidth;
		final int cellZW = 16 / cellWidth;
		final int sectionsCount = pChunk.getSectionsCount();
		boolean flag = this.pillarGenerator.isInStorage(pChunk);
		if (!flag && pChunk.levelHeightAccessor instanceof ServerLevel level) {
			this.pillarGenerator.generateIn(pChunk, level.dimension(), level.getServer(), level.getSeed()/2, this.getMinY());
			flag = true;
		}
		this.pillarGenerator.readPillars(pChunk);
		for (int xw = 0; xw < cellXW; ++xw) {
			final int xv = minChunkX + xw * cellWidth;
			noiseChunk.advanceCellX(xw);
			for (int zw = 0; zw < cellZW; ++zw) {
				final int zv = minChunkZ + zw * cellWidth;
				int i2 = sectionsCount - 1;
				LevelChunkSection levelChunkSection = pChunk.getSection(i2);
				for (int cell = pCellCountY - 1; cell >= 0; --cell) {
					final int yv = (pMinCellY + cell) * cellHeight;
					noiseChunk.selectCellYZ(cell, zw);
					for (int cy = cellHeight - 1; cy >= 0; --cy) {
						final int y = yv + cy;
						final int chunkY = y & 15;
						final int j3 = pChunk.getSectionIndex(y);
						if (i2 != j3) {
							i2 = j3;
							levelChunkSection = pChunk.getSection(j3);
						}
						final double d0 = (double)cy / (double)cellHeight;
						noiseChunk.updateForY(y, d0);
						for (int cx = 0; cx < cellWidth; ++cx) {
							final int x = xv + cx;
							final int chunkX = x & 15;
							final double d1 = (double)cx / (double)cellWidth;
							noiseChunk.updateForX(x, d1);
							for (int cz = 0; cz < cellWidth; ++cz) {
								final int z = zv + cz;
								final int chunkZ = z & 15;
								final double d2 = (double)cz / (double)cellWidth;
								noiseChunk.updateForZ(z, d2);
								BlockState blockState = noiseChunk.getInterpolatedState();
								if (flag) {
									int floorY = this.getMinY();
									for (int f = 0; f < cavernFloors.size(); f++) {
										final CavernFloorSettings cavernFloorSettings = cavernFloors.get(f);
										if (cavernFloorSettings.pillarRarity() > 0.0F && y >= floorY && y < floorY + cavernFloorSettings.floorHeight() && !this.pillarGenerator.isNearPillar(pChunk, f, floorY, x, y, z, chunkX, chunkZ) && (blockState == null || blockState.getFluidState().isEmpty()))
											blockState = y > this.getSeaLevel() ? Blocks.AIR.defaultBlockState() : noiseSettings.defaultFluid();
											floorY += cavernFloorSettings.floorHeight();
									}
								}
								if (blockState == null)
									blockState = noiseSettings.defaultBlock();
								blockState = this.debugPreliminarySurfaceLevel(noiseChunk, x, y, z, blockState);
								if (!SharedConstants.debugVoidTerrain(pChunk.getPos())) {
									levelChunkSection.setBlockState(chunkX, chunkY, chunkZ, blockState, false);
									heightMap.update(chunkX, y, chunkZ, blockState);
									heightMap1.update(chunkX, y, chunkZ, blockState);
									if (aquifer.shouldScheduleFluidUpdate() && !blockState.getFluidState().isEmpty()) {
										mutableBlockPos.set(x, y, z);
										pChunk.markPosForPostprocessing(mutableBlockPos);
									}
								}
							}
						}
					}
				}
			}
			noiseChunk.swapSlices();
		}
		noiseChunk.stopInterpolation();
		return pChunk;
	}
}
