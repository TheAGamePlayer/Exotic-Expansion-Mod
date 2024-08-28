package dev.theagameplayer.exoticexpansion.world.level.levelgen.structure.structures;

import java.util.ArrayList;
import java.util.Optional;

import com.mojang.serialization.MapCodec;

import dev.theagameplayer.exoticexpansion.registries.EEEntityTypes;
import dev.theagameplayer.exoticexpansion.registries.EEStructureTypes;
import dev.theagameplayer.exoticexpansion.world.level.levelgen.structure.SmartStructurePiece;
import net.minecraft.core.BlockPos;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public final class InfernalNetherFortressStructure extends Structure {
	public static final WeightedRandomList<MobSpawnSettings.SpawnerData> FORTRESS_ENEMIES = WeightedRandomList.create(
			new MobSpawnSettings.SpawnerData(EntityType.BLAZE, 15, 2, 5), 
			new MobSpawnSettings.SpawnerData(EntityType.ZOMBIFIED_PIGLIN, 8, 4, 6), 
			new MobSpawnSettings.SpawnerData(EntityType.WITHER_SKELETON, 12, 5, 8), 
			new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 3, 5, 8), 
			new MobSpawnSettings.SpawnerData(EntityType.MAGMA_CUBE, 5, 4, 6), 
			new MobSpawnSettings.SpawnerData(EEEntityTypes.SPAWNER_SLIME.get(), 2, 1, 3));
	public static final MapCodec<InfernalNetherFortressStructure> CODEC = simpleCodec(InfernalNetherFortressStructure::new);

	public InfernalNetherFortressStructure(final Structure.StructureSettings pSettings) {
		super(pSettings);
	}

	@Override
	public final Optional<Structure.GenerationStub> findGenerationPoint(final Structure.GenerationContext pContext) {
		final ChunkPos chunkPos = pContext.chunkPos();
		final BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), 64, chunkPos.getMinBlockZ());
		return Optional.of(new Structure.GenerationStub(blockPos, builder -> {
			generatePieces(builder, pContext);
		}));
	}

	@SuppressWarnings("deprecation")
	private static final void generatePieces(final StructurePiecesBuilder pBuilder, final Structure.GenerationContext pContext) {
		final InfernalNetherFortressPieces.StartPiece startPiece = new InfernalNetherFortressPieces.StartPiece(pContext, pContext.chunkPos().getBlockX(2), pContext.chunkPos().getBlockZ(2));
		pBuilder.addPiece(startPiece);
		startPiece.addChildren(startPiece, pBuilder, pContext.random());
		final ArrayList<SmartStructurePiece<?, ?>> list = startPiece.pendingChildren;
		while (!list.isEmpty()) {
			final int index = pContext.random().nextInt(list.size());
			final SmartStructurePiece<?, ?> structurePiece = list.remove(index);
			structurePiece.addChildren(startPiece, pBuilder, pContext.random());
		}
		pBuilder.moveInsideHeights(pContext.random(), 59, 89);
	}

	@Override
	public final StructureType<?> type() {
		return EEStructureTypes.INFERNAL_FORTRESS.get();
	}
}
