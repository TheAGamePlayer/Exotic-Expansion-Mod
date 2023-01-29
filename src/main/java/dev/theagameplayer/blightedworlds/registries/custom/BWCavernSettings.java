package dev.theagameplayer.blightedworlds.registries.custom;

import java.util.List;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import dev.theagameplayer.blightedworlds.world.level.levelgen.CavernSettings;
import dev.theagameplayer.blightedworlds.world.level.levelgen.CavernSettings.CavernFloorSettings;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;

public final class BWCavernSettings {
	public static final ResourceKey<Registry<CavernSettings>> CAVERN_SETTINGS = ResourceKey.createRegistryKey(BlightedWorldsMod.namespace("worldgen/cavern_settings"));
	
	public static final ResourceKey<CavernSettings> ANCIENT_NETHER = ResourceKey.create(CAVERN_SETTINGS, BlightedWorldsMod.namespace("ancient_nether"));
	
	public static final void bootstrap(final BootstapContext<CavernSettings> contextIn) {
		contextIn.register(ANCIENT_NETHER, new CavernSettings(List.of(new CavernFloorSettings(48, 1, 3, 0, 0.01041667D/3), new CavernFloorSettings(256, 1, 7, 3, 0.0078125D/3), new CavernFloorSettings(0, 0, 0, 0, -1.0D))));
	}
}
