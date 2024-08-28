package dev.theagameplayer.exoticexpansion.world.level.pillar.storage;

import java.io.IOException;
import org.codehaus.plexus.util.FastMap;

import dev.theagameplayer.exoticexpansion.world.level.pillar.PillarPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;

public final class PillarStorage implements AutoCloseable {
	private final FastMap<PillarPos, CompoundTag> pillars = new FastMap<>();
	private final PillarRegionFileStorage storage;

	public PillarStorage(final ResourceKey<Level> pLevelKey, final MinecraftServer pServer) {
		this.storage = new PillarRegionFileStorage(pServer.storageSource.getDimensionPath(pLevelKey).resolve("pillar_region"), pServer.forceSynchronousWrites());
	}

	public final CompoundTag read(final PillarPos pPillarPos) {
		try {
			if (this.pillars.containsKey(pPillarPos))
				return this.pillars.get(pPillarPos);
			final CompoundTag tag = this.storage.read(pPillarPos);
			this.pillars.put(pPillarPos, tag);
			return tag;
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public final void write(final PillarPos pPillarPos, final CompoundTag pData) {
		this.pillars.put(pPillarPos, pData);
		try {
			this.storage.write(pPillarPos, pData);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public final void flush() {
		try {
			this.storage.flush();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public final void close() throws IOException {
		this.storage.close();
	}
}
