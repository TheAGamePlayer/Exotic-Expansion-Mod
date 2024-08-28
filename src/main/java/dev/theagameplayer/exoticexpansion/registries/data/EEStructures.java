package dev.theagameplayer.exoticexpansion.registries.data;

import java.util.Map;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import dev.theagameplayer.exoticexpansion.tags.EEBiomeTags;
import dev.theagameplayer.exoticexpansion.world.level.levelgen.structure.structures.InfernalNetherFortressStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;

public final class EEStructures {
	public static final ResourceKey<Structure> INFERNAL_FORTRESS = ResourceKey.create(Registries.STRUCTURE, ExoticExpansionMod.namespace("infernal_fortress"));
	
	public static final void bootstrap(final BootstrapContext<Structure> pContext) {
		pContext.register(INFERNAL_FORTRESS, new InfernalNetherFortressStructure(settings(pContext, EEBiomeTags.HAS_INFERNAL_NETHER_FORTRESS, Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, InfernalNetherFortressStructure.FORTRESS_ENEMIES)), GenerationStep.Decoration.UNDERGROUND_DECORATION, TerrainAdjustment.NONE)));
	}
	
	private static final Structure.StructureSettings settings(final BootstrapContext<Structure> pContext, final TagKey<Biome> pBiomeTag, final Map<MobCategory, StructureSpawnOverride> pSpawnOverrides, final GenerationStep.Decoration pDecoration, final TerrainAdjustment pTerrainAdjustment) {
		return new Structure.StructureSettings(pContext.lookup(Registries.BIOME).getOrThrow(pBiomeTag), pSpawnOverrides, pDecoration, pTerrainAdjustment);
	}
}
