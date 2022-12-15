package dev.theagameplayer.blightedworlds.registries.data;

import java.util.Map;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import dev.theagameplayer.blightedworlds.data.tags.BWBiomeTags;
import dev.theagameplayer.blightedworlds.world.level.levelgen.structure.structures.InfernalNetherFortressStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;

public final class BWStructures {
	public static final ResourceKey<Structure> INFERNAL_FORTRESS = ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(BlightedWorldsMod.MODID, "infernal_fortress"));
	
	public static final void bootstrap(final BootstapContext<Structure> contextIn) {
		contextIn.register(INFERNAL_FORTRESS, new InfernalNetherFortressStructure(settings(contextIn, BWBiomeTags.HAS_INFERNAL_NETHER_FORTRESS, Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, InfernalNetherFortressStructure.FORTRESS_ENEMIES)), GenerationStep.Decoration.UNDERGROUND_DECORATION, TerrainAdjustment.NONE)));
	}
	
	private static final Structure.StructureSettings settings(final BootstapContext<Structure> contextIn, final TagKey<Biome> biomeTagIn, final Map<MobCategory, StructureSpawnOverride> spawnOverridesIn, final GenerationStep.Decoration stepIn, final TerrainAdjustment terrainAdaptationIn) {
		return new Structure.StructureSettings(contextIn.lookup(Registries.BIOME).getOrThrow(biomeTagIn), spawnOverridesIn, stepIn, terrainAdaptationIn);
	}
}
