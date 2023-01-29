package dev.theagameplayer.blightedworlds.registries;

import com.mojang.serialization.Codec;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import dev.theagameplayer.blightedworlds.world.level.levelgen.CavernChunkGenerator;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class BWChunkGenerators {
	public static final DeferredRegister<Codec<? extends ChunkGenerator>> CHUNK_GENERATORS = DeferredRegister.create(Registries.CHUNK_GENERATOR, BlightedWorldsMod.MODID);

	public static final RegistryObject<Codec<? extends ChunkGenerator>> CAVERN = CHUNK_GENERATORS.register("cavern", () -> CavernChunkGenerator.CODEC);
}
