package dev.theagameplayer.exoticexpansion.world.level.pillar;

import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import dev.theagameplayer.exoticexpansion.world.level.pillar.PillarGenerator.Pillar;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

public final class PillarPos {
    public final int x;
    public final int z;
	
	public PillarPos(final int pX, final int pZ) {
		this.x = pX;
		this.z = pZ;
	}

	public PillarPos(final BlockPos pPos) {
		this.x = pPos.getX() >> Pillar.SHIFT;
		this.z = pPos.getZ() >> Pillar.SHIFT;
	}
	
	public PillarPos(final ChunkPos pPos) {
		this.x = pPos.getMinBlockX() >> Pillar.SHIFT;
		this.z = pPos.getMinBlockZ() >> Pillar.SHIFT;
	}
	
    public PillarPos(final long pPackedPos) {
        this.x = (int)pPackedPos;
        this.z = (int)(pPackedPos >> 32);
    }
    
    public static final PillarPos minFromRegion(int pPillarX, int pPillarZ) {
        return new PillarPos(pPillarX << 5, pPillarZ << 5);
    }
    
    public static final PillarPos maxFromRegion(int pPillarX, int pPillarZ) {
        return new PillarPos((pPillarX << 5) + 31, (pPillarZ << 5) + 31);
    }
	
    public static final long asLong(final int pX, final int pZ) {
        return (long)pX & 4294967295L | ((long)pZ & 4294967295L) << 32;
    }
    
    public final int getMinBlockX() {
    	return this.x << Pillar.SHIFT;
    }
    
    public final int getMinBlockZ() {
    	return this.z << Pillar.SHIFT;
    }
    
    public final int getRegionX() {
        return this.x >> 5;
    }

    public final int getRegionZ() {
        return this.z >> 5;
    }
    
    public final int getRegionLocalX() {
        return this.x & 31;
    }

    public final int getRegionLocalZ() {
        return this.z & 31;
    }
	
    @Override
    public final int hashCode() {
        return hash(this.x, this.z);
    }

    public static final int hash(final int pX, final int pZ) {
        final int i = 1664525 * pX + 1013904223;
        final int j = 1664525 * (pZ ^ -559038737) + 1013904223;
        return i ^ j;
    }
    
    @Override
    public final boolean equals(final Object pOther) {
        if (this == pOther) {
            return true;
        } else {
            return !(pOther instanceof PillarPos pillarPos) ? false : this.x == pillarPos.x && this.z == pillarPos.z;
        }
    }
    
    @Override
    public final String toString() {
        return "[" + this.x + ", " + this.z + "]";
    }
    
    public static final Stream<PillarPos> rangeClosed(final PillarPos pCenter, final int pRadius) {
        return rangeClosed(new PillarPos(pCenter.x - pRadius, pCenter.z - pRadius), new PillarPos(pCenter.x + pRadius, pCenter.z + pRadius));
    }

    public static final Stream<PillarPos> rangeClosed(final PillarPos pStart, final PillarPos pEnd) {
        final int i = Math.abs(pStart.x - pEnd.x) + 1;
        final int j = Math.abs(pStart.z - pEnd.z) + 1;
        final int k = pStart.x < pEnd.x ? 1 : -1;
        final int l = pStart.z < pEnd.z ? 1 : -1;
        return StreamSupport.stream(new AbstractSpliterator<PillarPos>((long)(i * j), 64) {
            @Nullable
            private PillarPos pos;

            @Override
            public final boolean tryAdvance(final Consumer<? super PillarPos> pConsumer) {
                if (this.pos == null) {
                    this.pos = pStart;
                } else {
                    final int i1 = this.pos.x;
                    final int j1 = this.pos.z;
                    if (i1 == pEnd.x) {
                        if (j1 == pEnd.z)
                            return false;
                        this.pos = new PillarPos(pStart.x, j1 + l);
                    } else {
                        this.pos = new PillarPos(i1 + k, j1);
                    }
                }
                pConsumer.accept(this.pos);
                return true;
            }
        }, false);
    }
}
