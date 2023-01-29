package dev.theagameplayer.blightedworlds.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import dev.theagameplayer.blightedworlds.world.level.levelgen.CavernSettings.CavernFloorSettings;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

public final class CavernChunkGenerator extends NoiseBasedChunkGenerator {
	public static final Codec<CavernChunkGenerator> CODEC = RecordCodecBuilder.create(builder -> {
		return builder.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter(generator -> {
			return generator.getBiomeSource();
		}), CavernSettings.CODEC.fieldOf("cavern_settings").forGetter(generator -> {
			return generator.cavernSettings;
		}), NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(generator -> {
			return generator.generatorSettings();
		})).apply(builder, builder.stable(CavernChunkGenerator::new));
	});
	private final Holder<CavernSettings> cavernSettings;
	private final CavernGenerator cavernGenerator;

	public CavernChunkGenerator(final BiomeSource biomeSourceIn, final Holder<CavernSettings> cavernSettingsIn, final Holder<NoiseGeneratorSettings> settingsIn) {
		super(biomeSourceIn, settingsIn);
		this.cavernSettings = cavernSettingsIn;
		this.cavernGenerator = new CavernGenerator(cavernSettingsIn);
	}

	@Override
	protected final Codec<? extends CavernChunkGenerator> codec() {
		return CODEC;
	}

	public final CavernGenerator getCavernGenerator() {
		return this.cavernGenerator;
	}

	@Override
	protected final ChunkAccess doFill(final Blender blenderIn, final StructureManager structureManagerIn, final RandomState randomStateIn, final ChunkAccess chunkAccessIn, final int minCellIn, final int maxCellIn) {
		final CavernSettings cavernSettings = this.cavernSettings.value();
		final NoiseGeneratorSettings noiseSettings = this.generatorSettings().value();
		final NoiseChunk noiseChunk = chunkAccessIn.getOrCreateNoiseChunk(chunkAccess -> {
			return this.createNoiseChunk(chunkAccess, structureManagerIn, blenderIn, randomStateIn);
		});
		final Heightmap heightMap = chunkAccessIn.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);
		final Heightmap heightMap1 = chunkAccessIn.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);
		final ChunkPos chunkPos = chunkAccessIn.getPos();
		final int minChunkX = chunkPos.getMinBlockX();
		final int minChunkZ = chunkPos.getMinBlockZ();
		final Aquifer aquifer = noiseChunk.aquifer();
		noiseChunk.initializeForFirstCellX();
		final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		final int cellWidth = noiseChunk.cellWidth();
		final int cellHeight = noiseChunk.cellHeight();
		final int cellXW = 16 / cellWidth;
		final int cellZW = 16 / cellWidth;
		for (int xw = 0; xw < cellXW; ++xw) {
			noiseChunk.advanceCellX(xw);
			for (int zw = 0; zw < cellZW; ++zw) {
				LevelChunkSection levelChunkSection = chunkAccessIn.getSection(chunkAccessIn.getSectionsCount() - 1);
				for (int cell = maxCellIn - 1; cell >= 0; --cell) {
					noiseChunk.selectCellYZ(cell, zw);
					int floorY = noiseSettings.seaLevel();
					for (final CavernFloorSettings cavernFloorSettings : cavernSettings.getCavernFloors()) {
						final int divisor = noiseSettings.noiseSettings().height()/(cavernFloorSettings.floorHeight() == 0 ? 1 : cavernFloorSettings.floorHeight());
						for (int floorSide = 0; floorSide < (cavernSettings.getCavernFloors().indexOf(cavernFloorSettings) == 0 ? 1 : 1); floorSide++) {
							final int sideDivisor = floorSide == 0 ? divisor : -divisor * Math.abs(floorY - noiseSettings.noiseSettings().minY());
							for (int y = cellHeight - 1; y >= 0; --y) {
								final int k2 = ((minCellIn + cell) * cellHeight + y)/(sideDivisor == 0 ? 1 : sideDivisor) + floorY;
								final int l2 = k2 & 15;
								final int i3 = chunkAccessIn.getSectionIndex(k2);
								if (chunkAccessIn.getSectionIndex(levelChunkSection.bottomBlockY()) != i3)
									levelChunkSection = chunkAccessIn.getSection(i3);
								final double d0 = (double)y / (double)cellHeight;
								noiseChunk.updateForY(k2, d0);
								for (int x = 0; x < cellWidth; ++x) {
									final int k3 = minChunkX + xw * cellWidth + x;
									final int l3 = k3 & 15;
									final double d1 = (double)x / (double)cellWidth;
									noiseChunk.updateForX(k3, d1);
									for (int z = 0; z < cellWidth; ++z) {
										final int j4 = minChunkZ + zw * cellWidth + z;
										final int k4 = j4 & 15;
										final double d2 = (double)z / (double)cellWidth;
										noiseChunk.updateForZ(j4, d2);
										BlockState blockState = noiseChunk.getInterpolatedState();
										if (blockState == null || (floorSide == 0 && blockState.isAir() && this.cavernGenerator.isNearPillar(cavernFloorSettings, floorY, k3, k2, j4)))
											blockState = this.generatorSettings().value().defaultBlock();
										if (blockState != Blocks.AIR.defaultBlockState() && !SharedConstants.debugVoidTerrain(chunkAccessIn.getPos())) {
											if (blockState.getLightEmission(chunkAccessIn, mutableBlockPos) != 0 && chunkAccessIn instanceof ProtoChunk) {
												mutableBlockPos.set(k3, k2, j4);
												((ProtoChunk)chunkAccessIn).addLight(mutableBlockPos);
											}
											levelChunkSection.setBlockState(l3, l2, k4, blockState, false);
											heightMap.update(l3, k2, k4, blockState);
											heightMap1.update(l3, k2, k4, blockState);
											if (aquifer.shouldScheduleFluidUpdate() && !blockState.getFluidState().isEmpty()) {
												mutableBlockPos.set(k3, k2, j4);
												chunkAccessIn.markPosForPostprocessing(mutableBlockPos);
											}
										}
									}
								}
							}
						}
						floorY += cavernFloorSettings.floorHeight();
					}
				}
			}
			noiseChunk.swapSlices();
		}
		noiseChunk.stopInterpolation();
		return chunkAccessIn;
	}
}
