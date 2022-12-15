package dev.theagameplayer.blightedworlds.registries.data;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;

public final class BWStructureSets {
	public static final ResourceKey<StructureSet> INFERNAL_FORTRESS = ResourceKey.create(Registries.STRUCTURE_SET, new ResourceLocation(BlightedWorldsMod.MODID, "infernal_fortress"));
	
	public static final void bootstrap(final BootstapContext<StructureSet> contextIn) {
		contextIn.register(INFERNAL_FORTRESS, register(contextIn, BWStructures.INFERNAL_FORTRESS, new RandomSpreadStructurePlacement(81, 12, RandomSpreadType.LINEAR, 30084232)));
	}
	
	private static final <S extends Structure> StructureSet register(final BootstapContext<StructureSet> contextIn, final ResourceKey<Structure> structureIn, final StructurePlacement placementIn) {
		return new StructureSet(contextIn.lookup(Registries.STRUCTURE).getOrThrow(structureIn), placementIn);
	}
}
