package dev.theagameplayer.blightedworlds.registries.other;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public final class BWDimensions {
	public static final ResourceKey<Level> ANCIENT_NETHER = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(BlightedWorldsMod.MODID, "the_ancient_nether"));
}
