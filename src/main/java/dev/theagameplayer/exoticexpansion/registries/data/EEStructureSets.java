package dev.theagameplayer.exoticexpansion.registries.data;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;

public final class EEStructureSets {
	public static final ResourceKey<StructureSet> INFERNAL_FORTRESS = ResourceKey.create(Registries.STRUCTURE_SET, ExoticExpansionMod.namespace("infernal_fortress"));
	
	public static final void bootstrap(final BootstrapContext<StructureSet> pContext) {
		pContext.register(INFERNAL_FORTRESS, register(pContext, EEStructures.INFERNAL_FORTRESS, new RandomSpreadStructurePlacement(81, 12, RandomSpreadType.LINEAR, 30084232)));
	}
	
	private static final <S extends Structure> StructureSet register(final BootstrapContext<StructureSet> pContext, final ResourceKey<Structure> pStructure, final StructurePlacement pPlacement) {
		return new StructureSet(pContext.lookup(Registries.STRUCTURE).getOrThrow(pStructure), pPlacement);
	}
}
