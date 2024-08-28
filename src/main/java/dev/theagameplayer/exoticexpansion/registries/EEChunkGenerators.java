package dev.theagameplayer.exoticexpansion.registries;

import java.util.function.Supplier;

import com.mojang.serialization.MapCodec;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import dev.theagameplayer.exoticexpansion.world.level.levelgen.CavernChunkGenerator;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EEChunkGenerators {
	public static final DeferredRegister<MapCodec<? extends ChunkGenerator>> CHUNK_GENERATORS = DeferredRegister.create(Registries.CHUNK_GENERATOR, ExoticExpansionMod.MODID);

	public static final Supplier<MapCodec<? extends ChunkGenerator>> CAVERN = CHUNK_GENERATORS.register("cavern", () -> CavernChunkGenerator.CODEC);
}