package dev.theagameplayer.exoticexpansion.registries;

import java.util.function.Supplier;

import com.mojang.serialization.MapCodec;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EEDensityFunctions {
	public static final DeferredRegister<MapCodec<? extends DensityFunction>> DENSITY_FUNCTION_TYPE = DeferredRegister.create(BuiltInRegistries.DENSITY_FUNCTION_TYPE, ExoticExpansionMod.MODID);

	public static final Supplier<MapCodec<? extends DensityFunction>> CAVERN = null;
}
