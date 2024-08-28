package dev.theagameplayer.exoticexpansion.world.level.pillar.saveddata;

import java.util.HashMap;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

public final class PillarLevelData extends SavedData {
	private static final HashMap<ServerLevel, PillarLevelData> PILLAR_DATA = new HashMap<>();
	
	private PillarLevelData(final ServerLevel pLevel) {
		PILLAR_DATA.put(pLevel, this);
	}

	public static final SavedData.Factory<PillarLevelData> factory(final ServerLevel pLevel) {
		return new SavedData.Factory<>(() -> {
			return new PillarLevelData(pLevel);
		}, (tag, provider) -> {
			return load(pLevel, tag);
		});
	}
	
	public static final PillarLevelData load(final ServerLevel pLevel, final CompoundTag pTag) {
		final PillarLevelData plData = new PillarLevelData(pLevel);
		return plData;
	}
	
	@Override
	public final CompoundTag save(final CompoundTag pTag, final Provider pRegistries) {
		return pTag;
	}
}
