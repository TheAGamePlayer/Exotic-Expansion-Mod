package dev.theagameplayer.blightedworlds.world.level.levelgen.structure.structures;

import java.util.ArrayList;
import java.util.Optional;

import com.mojang.serialization.Codec;

import dev.theagameplayer.blightedworlds.registries.BWEntityTypes;
import dev.theagameplayer.blightedworlds.registries.BWStructureTypes;
import dev.theagameplayer.blightedworlds.world.level.levelgen.structure.SmartStructurePiece;
import dev.theagameplayer.blightedworlds.world.level.levelgen.structure.pieces.SmartStructurePiecesBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public final class InfernalNetherFortressStructure extends Structure {
	public static final WeightedRandomList<MobSpawnSettings.SpawnerData> FORTRESS_ENEMIES = WeightedRandomList.create(
			new MobSpawnSettings.SpawnerData(EntityType.BLAZE, 15, 2, 5), 
			new MobSpawnSettings.SpawnerData(EntityType.ZOMBIFIED_PIGLIN, 8, 4, 6), 
			new MobSpawnSettings.SpawnerData(EntityType.WITHER_SKELETON, 12, 5, 8), 
			new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 3, 5, 8), 
			new MobSpawnSettings.SpawnerData(EntityType.MAGMA_CUBE, 5, 4, 6), 
			new MobSpawnSettings.SpawnerData(BWEntityTypes.SPAWNER_SLIME.get(), 2, 1, 3));
	public static final Codec<InfernalNetherFortressStructure> CODEC = simpleCodec(InfernalNetherFortressStructure::new);

	public InfernalNetherFortressStructure(final Structure.StructureSettings settingsIn) {
		super(settingsIn);
	}

	@Override
	public final Optional<Structure.GenerationStub> findGenerationPoint(final Structure.GenerationContext contextIn) {
		final ChunkPos chunkPos = contextIn.chunkPos();
		final BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), 64, chunkPos.getMinBlockZ());
		return Optional.of(new Structure.GenerationStub(blockPos, builder -> {
			generatePieces(new SmartStructurePiecesBuilder(builder), contextIn);
		}));
	}

	@SuppressWarnings("deprecation")
	private static final void generatePieces(final SmartStructurePiecesBuilder builderIn, final Structure.GenerationContext contextIn) {
		final InfernalNetherFortressPieces.StartPiece startPiece = new InfernalNetherFortressPieces.StartPiece(contextIn, contextIn.chunkPos().getBlockX(2), contextIn.chunkPos().getBlockZ(2));
		builderIn.addPiece(startPiece);
		startPiece.addChildren(startPiece, builderIn, contextIn.random());
		final ArrayList<SmartStructurePiece<?, ?>> list = startPiece.pendingChildren;
		while (!list.isEmpty()) {
			final int index = contextIn.random().nextInt(list.size());
			final SmartStructurePiece<?, ?> smartPiece = list.remove(index);
			smartPiece.addChildren(startPiece, builderIn, contextIn.random());
		}
		builderIn.moveInsideHeights(contextIn.random(), 46, 89);
	}

	@Override
	public final StructureType<?> type() {
		return BWStructureTypes.INFERNAL_FORTRESS.get();
	}
}
