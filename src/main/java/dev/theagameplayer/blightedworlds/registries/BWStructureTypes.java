package dev.theagameplayer.blightedworlds.registries;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import dev.theagameplayer.blightedworlds.world.level.levelgen.structure.structures.InfernalNetherFortressStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class BWStructureTypes {
	public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, BlightedWorldsMod.MODID);
	
	public static final RegistryObject<StructureType<InfernalNetherFortressStructure>> INFERNAL_FORTRESS = STRUCTURE_TYPES.register("infernal_fortress", () -> () -> InfernalNetherFortressStructure.CODEC);
}
