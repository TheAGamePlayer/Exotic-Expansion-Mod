package dev.theagameplayer.exoticexpansion.mixin;

import java.io.IOException;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.theagameplayer.exoticexpansion.world.level.levelgen.CavernChunkGenerator;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.world.level.chunk.storage.ChunkStorage;

@Mixin(ChunkStorage.class)
public final class ChunkStorageMixin {
	@Inject(at = @At("RETURN"), method = "Lnet/minecraft/world/level/chunk/storage/ChunkStorage;flushWorker()V")
	private final void flushWorker(final CallbackInfo pCallbackInfo) {
		if (((Object)this) instanceof ChunkMap map && map.generator() instanceof CavernChunkGenerator cavernChunkGenerator)
			cavernChunkGenerator.getPillarGenerator().getStorage().flush();
	}
	
	@Inject(at = @At("RETURN"), method = "Lnet/minecraft/world/level/chunk/storage/ChunkStorage;close()V")
	private final void close(final CallbackInfo pCallbackInfo) throws IOException {
		if (((Object)this) instanceof ChunkMap map && map.generator() instanceof CavernChunkGenerator cavernChunkGenerator)
			cavernChunkGenerator.getPillarGenerator().getStorage().close();
	}
}
