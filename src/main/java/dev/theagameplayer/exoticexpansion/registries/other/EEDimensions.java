package dev.theagameplayer.exoticexpansion.registries.other;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public final class EEDimensions {
	public static final ResourceKey<Level> ANCIENT_NETHER = ResourceKey.create(Registries.DIMENSION, ExoticExpansionMod.namespace("the_ancient_nether"));
}
