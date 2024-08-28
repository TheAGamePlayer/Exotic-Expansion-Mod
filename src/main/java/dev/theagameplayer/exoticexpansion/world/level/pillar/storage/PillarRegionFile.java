package dev.theagameplayer.exoticexpansion.world.level.pillar.storage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Logger;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import dev.theagameplayer.exoticexpansion.world.level.pillar.PillarPos;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.storage.RegionBitmap;
import net.minecraft.world.level.chunk.storage.RegionFileVersion;

public final class PillarRegionFile implements AutoCloseable {
	private static final Logger LOGGER = ExoticExpansionMod.LOGGER;
	private static final ByteBuffer PADDING_BUFFER = ByteBuffer.allocateDirect(1);
	private final Path path;
	private final Path externalFileDir;
	private final FileChannel file;
	private volatile RegionFileVersion version;
	private final ByteBuffer header = ByteBuffer.allocateDirect(8192);
	private final IntBuffer offsets;
	private final IntBuffer timeStamps;
	protected final RegionBitmap usedSectors = new RegionBitmap();

	public PillarRegionFile(final Path pPath, final Path pExternalFileDir, final boolean pSync) throws IOException {
		this(pPath, pExternalFileDir, RegionFileVersion.getSelected(), pSync);
	}

	public PillarRegionFile(final Path pPath, final Path pExternalFileDir, final RegionFileVersion pVersion, final boolean pSync) throws IOException {
		this.path = pPath;
		this.version = pVersion;
		if (!Files.isDirectory(pExternalFileDir)) {
			throw new IllegalArgumentException("Expected directory, got " + pExternalFileDir.toAbsolutePath());
		} else {
			this.externalFileDir = pExternalFileDir;
			this.offsets = this.header.asIntBuffer();
			this.offsets.limit(1024);
			this.header.position(4096);
			this.timeStamps = this.header.asIntBuffer();
			if (pSync) {
				this.file = FileChannel.open(pPath, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.DSYNC);
			} else {
				this.file = FileChannel.open(pPath, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
			}
			this.usedSectors.force(0, 2);
			this.header.position(0);
			int i = this.file.read(this.header, 0L);
			if (i != -1) {
				if (i != 8192)
					LOGGER.warn("Pillar Region file {} has truncated header: {}", pPath, i);
				long j = Files.size(pPath);
				for (int k = 0; k < 1024; k++) {
					int l = this.offsets.get(k);
					if (l != 0) {
						int i1 = getSectorNumber(l);
						int j1 = getNumSectors(l);
						if (i1 < 2) {
							LOGGER.warn("Pillar Region file {} has invalid sector at index: {}; sector {} overlaps with header", pPath, k, i1);
							this.offsets.put(k, 0);
						} else if (j1 == 0) {
							LOGGER.warn("Pillar Region file {} has an invalid sector at index: {}; size has to be > 0", pPath, k);
							this.offsets.put(k, 0);
						} else if ((long)i1 * 4096L > j) {
							LOGGER.warn("Pillar Region file {} has an invalid sector at index: {}; sector {} is out of bounds", pPath, k, i1);
							this.offsets.put(k, 0);
						} else {
							this.usedSectors.force(i1, j1);
						}
					}
				}
			}
		}
	}

	public final Path getPath() {
		return this.path;
	}

	private final Path getExternalPillarPath(final PillarPos pPillarPos) {
		String s = "c." + pPillarPos.x + "." + pPillarPos.z + ".mcc";
		return this.externalFileDir.resolve(s);
	}

	@Nullable
	public final synchronized DataInputStream getPillarDataInputStream(final PillarPos pPillarPos) throws IOException {
		int i = this.getOffset(pPillarPos);
		if (i == 0) {
			return null;
		} else {
			int j = getSectorNumber(i);
			int k = getNumSectors(i);
			int l = k * 4096;
			ByteBuffer byteBuffer = ByteBuffer.allocate(l);
			this.file.read(byteBuffer, (long)(j * 4096));
			byteBuffer.flip();
			if (byteBuffer.remaining() < 5) {
				LOGGER.error("Pillar {} header is truncated: expected {} but read {}", pPillarPos, l, byteBuffer.remaining());
				return null;
			} else {
				int i1 = byteBuffer.getInt();
				byte b0 = byteBuffer.get();
				if (i1 == 0) {
					LOGGER.warn("Pillar {} is allocated, but stream is missing", pPillarPos);
					return null;
				} else {
					int j1 = i1 - 1;
					if (isExternalStreamPillar(b0)) {
						if (j1 != 0)
							LOGGER.warn("Pillar has both internal and external streams");
						return this.createExternalPillarInputStream(pPillarPos, getExternalPillarVersion(b0));
					} else if (j1 > byteBuffer.remaining()) {
						LOGGER.error("Pillar {} stream is truncated: expected {} but read {}", pPillarPos, j1, byteBuffer.remaining());
						return null;
					} else if (j1 < 0) {
						LOGGER.error("Declared size {} of pillar {} is negative", i1, pPillarPos);
						return null;
					} else {
						return this.createPillarInputStream(pPillarPos, b0, createStream(byteBuffer, j1));
					}
				}
			}
		}
	}

	private static final int getTimestamp() {
		return (int)(Util.getEpochMillis() / 1000L);
	}

	private static final boolean isExternalStreamPillar(final byte pVersionByte) {
		return (pVersionByte & 128) != 0;
	}

	private static final byte getExternalPillarVersion(final byte pVersionByte) {
		return (byte)(pVersionByte & -129);
	}

	@Nullable
	private final DataInputStream createPillarInputStream(final PillarPos pPillarPos, final byte pVersionByte, final InputStream pInputStream) throws IOException {
		final RegionFileVersion regionFileVersion = RegionFileVersion.fromId(pVersionByte);
		if (regionFileVersion == RegionFileVersion.VERSION_CUSTOM) {
			final String s = new DataInputStream(pInputStream).readUTF();
			final ResourceLocation resourceLocation = ResourceLocation.tryParse(s);
			if (resourceLocation != null) {
				LOGGER.error("Unrecognized custom compression {}", resourceLocation);
				return null;
			} else {
				LOGGER.error("Invalid custom compression id {}", s);
				return null;
			}
		} else if (regionFileVersion == null) {
			LOGGER.error("Pillar {} has invalid chunk stream version {}", pPillarPos, pVersionByte);
			return null;
		} else {
			return new DataInputStream(regionFileVersion.wrap(pInputStream));
		}
	}

	@Nullable
	private final DataInputStream createExternalPillarInputStream(final PillarPos pPillarPos, final byte pVersionByte) throws IOException {
		final Path path = this.getExternalPillarPath(pPillarPos);
		if (!Files.isRegularFile(path)) {
			LOGGER.error("External chunk path {} is not file", path);
			return null;
		} else {
			return this.createPillarInputStream(pPillarPos, pVersionByte, Files.newInputStream(path));
		}
	}

	private static final ByteArrayInputStream createStream(final ByteBuffer pSourceBuffer, final int pLength) {
		return new ByteArrayInputStream(pSourceBuffer.array(), pSourceBuffer.position(), pLength);
	}

	private final int packSectorOffset(final int pSectorOffset, final int pSectorCount) {
		return pSectorOffset << 8 | pSectorCount;
	}

	private static final int getNumSectors(final int pPackedSectorOffset) {
		return pPackedSectorOffset & 0xFF;
	}

	private static final int getSectorNumber(final int pPackedSectorOffset) {
		return pPackedSectorOffset >> 8 & 16777215;
	}

	private static final int sizeToSectors(final int pSize) {
		return (pSize + 4096 - 1) / 4096;
	}

	public final boolean doesPillarExist(final PillarPos pPillarPos) {
		final int i = this.getOffset(pPillarPos);
		if (i == 0) {
			return false;
		} else {
			int j = getSectorNumber(i);
			int k = getNumSectors(i);
			final ByteBuffer byteBuffer = ByteBuffer.allocate(5);
			try {
				this.file.read(byteBuffer, (long)(j * 4096));
				byteBuffer.flip();
				if (byteBuffer.remaining() != 5) {
					return false;
				} else {
					final int l = byteBuffer.getInt();
					final byte b0 = byteBuffer.get();
					if (isExternalStreamPillar(b0)) {
						if (!RegionFileVersion.isValidVersion(getExternalPillarVersion(b0)))
							return false;
						if (!Files.isRegularFile(this.getExternalPillarPath(pPillarPos)))
							return false;
					} else {
						if (!RegionFileVersion.isValidVersion(b0))
							return false;
						if (l == 0)
							return false;
						final int i1 = l - 1;
						if (i1 < 0 || i1 > 4096 * k)
							return false;
					}

					return true;
				}
			} catch (final IOException ioException) {
				return false;
			}
		}
	}


	public final DataOutputStream getPillarDataOutputStream(PillarPos pPillarPos) throws IOException {
		return new DataOutputStream(this.version.wrap(new PillarRegionFile.PillarBuffer(pPillarPos)));
	}

	public final void flush() throws IOException {
		this.file.force(true);
	}

	public final synchronized void clear(final PillarPos pPillarPos) throws IOException {
		final int i = getOffsetIndex(pPillarPos);
		final int j = this.offsets.get(i);
		if (j != 0) {
			this.offsets.put(i, 0);
			this.timeStamps.put(i, getTimestamp());
			this.writeHeader();
			Files.deleteIfExists(this.getExternalPillarPath(pPillarPos));
			this.usedSectors.free(getSectorNumber(j), getNumSectors(j));
		}
	}
	
    protected final synchronized void write(final PillarPos pPillarPos, final ByteBuffer pPillarData) throws IOException {
        final int i = getOffsetIndex(pPillarPos);
        final int j = this.offsets.get(i);
        final int k = getSectorNumber(j);
        final int l = getNumSectors(j);
        final int i1 = pPillarData.remaining();
        int j1 = sizeToSectors(i1);
        final int k1;
        final PillarRegionFile.CommitOp pillarFile$commitOp;
        if (j1 >= 256) {
            Path path = this.getExternalPillarPath(pPillarPos);
            LOGGER.warn("Saving oversized chunk {} ({} bytes} to external file {}", pPillarPos, i1, path);
            j1 = 1;
            k1 = this.usedSectors.allocate(j1);
            pillarFile$commitOp = this.writeToExternalFile(path, pPillarData);
            final ByteBuffer byteBuffer = this.createExternalStub();
            this.file.write(byteBuffer, (long)(k1 * 4096));
        } else {
            k1 = this.usedSectors.allocate(j1);
            pillarFile$commitOp = () -> Files.deleteIfExists(this.getExternalPillarPath(pPillarPos));
            this.file.write(pPillarData, (long)(k1 * 4096));
        }

        this.offsets.put(i, this.packSectorOffset(k1, j1));
        this.timeStamps.put(i, getTimestamp());
        this.writeHeader();
        pillarFile$commitOp.run();
        if (k != 0) {
            this.usedSectors.free(k, l);
        }
    }

    private final ByteBuffer createExternalStub() {
        final ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        byteBuffer.putInt(1);
        byteBuffer.put((byte)(this.version.getId() | 128));
        byteBuffer.flip();
        return byteBuffer;
    }

    private final PillarRegionFile.CommitOp writeToExternalFile(final Path pExternalPillarFile, final ByteBuffer pPillarData) throws IOException {
        final Path path = Files.createTempFile(this.externalFileDir, "tmp", null);
        try (final FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            pPillarData.position(5);
            fileChannel.write(pPillarData);
        }
        return () -> Files.move(path, pExternalPillarFile, StandardCopyOption.REPLACE_EXISTING);
    }

    private final void writeHeader() throws IOException {
        this.header.position(0);
        this.file.write(this.header, 0L);
    }

    private final int getOffset(final PillarPos pPillarPos) {
        return this.offsets.get(getOffsetIndex(pPillarPos));
    }

    public final boolean hasPillar(final PillarPos pPillarPos) {
        return this.getOffset(pPillarPos) != 0;
    }

    private static final int getOffsetIndex(final PillarPos pPillarPos) {
        return pPillarPos.getRegionLocalX() + pPillarPos.getRegionLocalZ() * 32;
    }

    @Override
    public final void close() throws IOException {
        try {
            this.padToFullSector();
        } finally {
            try {
                this.file.force(true);
            } finally {
                this.file.close();
            }
        }
    }

    private final void padToFullSector() throws IOException {
        final int i = (int)this.file.size();
        final int j = sizeToSectors(i) * 4096;
        if (i != j) {
            final ByteBuffer byteBuffer = PADDING_BUFFER.duplicate();
            byteBuffer.position(0);
            this.file.write(byteBuffer, (long)(j - 1));
        }
    }

    private class PillarBuffer extends ByteArrayOutputStream {
        private final PillarPos pos;

        public PillarBuffer(final PillarPos pPos) {
            super(8096);
            super.write(0);
            super.write(0);
            super.write(0);
            super.write(0);
            super.write(PillarRegionFile.this.version.getId());
            this.pos = pPos;
        }

        @Override
        public final void close() throws IOException {
            final ByteBuffer byteBuffer = ByteBuffer.wrap(this.buf, 0, this.count);
            int i = this.count - 5 + 1;
            byteBuffer.putInt(0, i);
            PillarRegionFile.this.write(this.pos, byteBuffer);
        }
    }

    private interface CommitOp {
        abstract void run() throws IOException;
    }
}
