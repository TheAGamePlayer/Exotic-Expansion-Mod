package dev.theagameplayer.exoticexpansion.registries.custom;

import java.util.List;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import dev.theagameplayer.exoticexpansion.world.level.levelgen.CavernSettings;
import dev.theagameplayer.exoticexpansion.world.level.levelgen.CavernSettings.CavernFloorSettings;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public final class EECavernSettings {
	public static final ResourceKey<Registry<CavernSettings>> CAVERN_SETTINGS = ResourceKey.createRegistryKey(ExoticExpansionMod.namespace("worldgen/cavern_settings"));
	
	public static final ResourceKey<CavernSettings> ANCIENT_NETHER = ResourceKey.create(CAVERN_SETTINGS, ExoticExpansionMod.namespace("ancient_nether"));
	
	public static final void bootstrap(final BootstrapContext<CavernSettings> pContext) {
		pContext.register(ANCIENT_NETHER, new CavernSettings(List.of(new CavernFloorSettings(64, 1, 3, 0, 0.01041667F/3), new CavernFloorSettings(256, 1, 7, 3, 0.0078125F/3))));
	}
}
