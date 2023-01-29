package dev.theagameplayer.blightedworlds.world;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import dev.theagameplayer.blightedworlds.data.tags.BWStructureTags;
import dev.theagameplayer.blightedworlds.registries.BWBlocks;
import dev.theagameplayer.blightedworlds.world.level.PortalMode;
import dev.theagameplayer.blightedworlds.world.level.block.AncientNetherAltarBlock;
import dev.theagameplayer.blightedworlds.world.level.block.AncientNetherPortalBlock;
import dev.theagameplayer.blightedworlds.world.level.block.entity.AncientNetherPortalBlockEntity;
import dev.theagameplayer.blightedworlds.world.level.levelgen.structure.TeleportDestinationPiece;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;

public final class BWTeleporter implements ITeleporter {
	@Override
	public final Entity placeEntity(final Entity entityIn, final ServerLevel currentLevelIn, final ServerLevel destLevelIn, final float yawIn, final Function<Boolean, Entity> repositionEntityIn) {
		entityIn.fallDistance = 0;
		return repositionEntityIn.apply(false);
	}

	@Override
	public final PortalInfo getPortalInfo(final Entity entityIn, final ServerLevel destLevelIn, final Function<ServerLevel, PortalInfo> defaultPortalInfoIn) {
		final BlockPos spawnPos = destLevelIn.findNearestMapStructure(BWStructureTags.ANCIENT_NETHER_DESTINATIONS, entityIn.blockPosition(), 100, false) == null ? entityIn.blockPosition() : destLevelIn.findNearestMapStructure(BWStructureTags.ANCIENT_NETHER_DESTINATIONS, entityIn.blockPosition(), 100, false);
		if (destLevelIn.structureManager().hasAnyStructureAt(spawnPos)) {
			final Map<Structure, LongSet> structures = destLevelIn.structureManager().getAllStructuresAt(spawnPos);
			for (final Entry<Structure, LongSet> entry : structures.entrySet()) {
				for (final long l : entry.getValue()) {
					final SectionPos sectionPos = SectionPos.of(new ChunkPos(l), destLevelIn.getMinSection());
					final StructureStart start = destLevelIn.structureManager().getStartForStructure(sectionPos, entry.getKey(), destLevelIn.getChunk(sectionPos.x(), sectionPos.z(), ChunkStatus.STRUCTURE_STARTS));
					for (final StructurePiece piece : start.getPieces()) {
						if (piece instanceof TeleportDestinationPiece destinationPiece) {
							final BlockPos altarPos = destinationPiece.getDestination();
							if (destLevelIn.getBlockState(altarPos).getBlock() == BWBlocks.ANCIENT_NETHER_ALTAR.get()) {
								final BlockPos height = altarPos.above(6);
								if (!destLevelIn.getBlockState(altarPos).getValue(AncientNetherAltarBlock.HAS_ORB))
									destLevelIn.setBlockAndUpdate(altarPos, destLevelIn.getBlockState(altarPos).setValue(AncientNetherAltarBlock.HAS_ORB, Boolean.valueOf(true)));
								if (!((AncientNetherAltarBlock)destLevelIn.getBlockState(altarPos).getBlock()).placedPortal(destLevelIn.getBlockState(altarPos), destLevelIn, altarPos)) {
								} else {
									destLevelIn.setBlockAndUpdate(height, destLevelIn.getBlockState(height).setValue(AncientNetherPortalBlock.PORTAL_MODE, PortalMode.INACTIVE));
									((AncientNetherPortalBlockEntity)destLevelIn.getBlockEntity(height)).setReactivationCount(600);
								}
							}
							return new PortalInfo(altarPos.relative(destLevelIn.getBlockState(altarPos).getValue(AncientNetherAltarBlock.FACING)).getCenter(), Vec3.ZERO, entityIn.getYRot(), entityIn.getXRot());
						}
					}
				}
			}
		}
		return new PortalInfo(spawnPos.getCenter(), Vec3.ZERO, entityIn.getYRot(), entityIn.getXRot());
	}
}
