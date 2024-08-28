package dev.theagameplayer.exoticexpansion.world.level.pillar.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import javax.annotation.Nullable;

import dev.theagameplayer.exoticexpansion.world.level.pillar.PillarPos;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import net.minecraft.FileUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.util.ExceptionCollector;
import net.minecraft.world.level.chunk.storage.RegionFileStorage;

public final class PillarRegionFileStorage implements AutoCloseable {
	public static final String ANVIL_EXTENSION = RegionFileStorage.ANVIL_EXTENSION;
	private final Long2ObjectLinkedOpenHashMap<PillarRegionFile> pillarCache = new Long2ObjectLinkedOpenHashMap<>();
	private final Path folder;
	private final boolean sync;

	protected PillarRegionFileStorage(final Path pFolder, final boolean pSync) {
		this.folder = pFolder;
		this.sync = pSync;
	}

	private final synchronized PillarRegionFile getPillarFile(final PillarPos pPillarPos) throws IOException {
		final long i = PillarPos.asLong(pPillarPos.getRegionX(), pPillarPos.getRegionZ());
		final PillarRegionFile pillarFile = this.pillarCache.getAndMoveToFirst(i);
		if (pillarFile != null) {
			return pillarFile;
		} else {
			if (this.pillarCache.size() >= 256)
				this.pillarCache.removeLast().close();
			FileUtil.createDirectoriesSafe(this.folder);
			final Path path = this.folder.resolve("r." + pPillarPos.getRegionX() + "." + pPillarPos.getRegionZ() + ".mca");
			final PillarRegionFile pillarFile1 = new PillarRegionFile(path, this.folder, this.sync);
			this.pillarCache.putAndMoveToFirst(i, pillarFile1);
			return pillarFile1;
		}
	}

	@Nullable
	public final CompoundTag read(final PillarPos pPillarPos) throws IOException {
		final PillarRegionFile pillarFile = this.getPillarFile(pPillarPos);
		CompoundTag compoundTag;
		try (final DataInputStream dataInputStream = pillarFile.getPillarDataInputStream(pPillarPos)) {
			if (dataInputStream == null)
				return null;
			compoundTag = NbtIo.read(dataInputStream);
		}
		return compoundTag;
	}

	public final void write(final PillarPos pPillarPos, final @Nullable CompoundTag pPillarData) throws IOException {
		final PillarRegionFile pillarFile = this.getPillarFile(pPillarPos);
		if (pPillarData == null) {
			pillarFile.clear(pPillarPos);
		} else {
			try (final DataOutputStream dataOutputStream = pillarFile.getPillarDataOutputStream(pPillarPos)) {
				NbtIo.write(pPillarData, dataOutputStream);
			}
		}
	}

	@Override
	public final void close() throws IOException {
		final ExceptionCollector<IOException> exceptionCollector = new ExceptionCollector<>();
	    for (final PillarRegionFile pillarFile : this.pillarCache.values()) {
	        try {
	            pillarFile.close();
	        } catch (final IOException ioException) {
	            exceptionCollector.add(ioException);
	        }
	    }
	    exceptionCollector.throwIfPresent();
	}

	public final void flush() throws IOException {
		for (final PillarRegionFile pillarFile : this.pillarCache.values())
			pillarFile.flush();
	}
}
