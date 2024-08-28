package dev.theagameplayer.exoticexpansion.registries;

import java.util.function.Supplier;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import dev.theagameplayer.exoticexpansion.world.level.levelgen.structure.structures.InfernalNetherFortressStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EEStructureTypes {
	public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPE = DeferredRegister.create(Registries.STRUCTURE_TYPE, ExoticExpansionMod.MODID);

	public static final Supplier<StructureType<InfernalNetherFortressStructure>> INFERNAL_FORTRESS = STRUCTURE_TYPE.register("infernal_fortress", () -> () -> InfernalNetherFortressStructure.CODEC);
}
