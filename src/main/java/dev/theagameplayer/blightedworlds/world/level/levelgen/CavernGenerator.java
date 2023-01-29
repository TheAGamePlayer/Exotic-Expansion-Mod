package dev.theagameplayer.blightedworlds.world.level.levelgen;

import dev.theagameplayer.blightedworlds.world.level.levelgen.CavernSettings.CavernFloorSettings;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public final class CavernGenerator {
	private final Holder<CavernSettings> cavernSettings;

	public CavernGenerator(final Holder<CavernSettings> cavernSettingsIn) {
		this.cavernSettings = cavernSettingsIn;
	}

	public final boolean isNearPillar(final CavernFloorSettings cavernFloorSettingsIn, final int floorYIn, final int xIn, final int yIn, final int zIn) {
		if (cavernFloorSettingsIn.pillarRarity() > 0.0D && yIn >= floorYIn && yIn < floorYIn + cavernFloorSettingsIn.floorHeight()) {
			final int zoneX = xIn >> Pillar.SHIFT;
			final int zoneZ = zIn >> Pillar.SHIFT;
			for (int x = zoneX - 1; x < zoneX + 2; x++) {
				for (int z = zoneZ - 1; z < zoneZ + 2; z++) {
					final Pillar pillar = new Pillar(cavernFloorSettingsIn, x, z);
					final double width = Mth.square(yIn - cavernFloorSettingsIn.floorHeight()/2 + floorYIn)/(yIn == 0 ? pillar.size * 1 : pillar.size * yIn) + pillar.size * 32;
					final double sqrt = Math.sqrt(Mth.square(pillar.x + 0.5D - xIn) + Mth.square(pillar.z + 0.5D - zIn));
					if (sqrt <= width) return true;
				}
			}
		}
		return false;
	}
	
	private final class Pillar {
		private static final int SHIFT = 9, DISTANCE = 512, CHUNK_DISTANCE = 16;
		private final int size;
		private final double x, z;
		
		private Pillar(final CavernFloorSettings cavernFloorSettingsIn, final int xIn, final int zIn) {
			final RandomSource random = RandomSource.create((long)CavernGenerator.this.cavernSettings.value().getCavernFloors().indexOf(cavernFloorSettingsIn) + (long)xIn + (long)zIn);
			this.size = random.nextInt(cavernFloorSettingsIn.maxPillarSize() - cavernFloorSettingsIn.minPillarSize()) + cavernFloorSettingsIn.minPillarSize();
			this.x = (xIn << SHIFT) + random.nextInt(DISTANCE - CHUNK_DISTANCE) + CHUNK_DISTANCE;
			this.z = (zIn << SHIFT) + random.nextInt(DISTANCE - CHUNK_DISTANCE) + CHUNK_DISTANCE;
		}
	}
}
