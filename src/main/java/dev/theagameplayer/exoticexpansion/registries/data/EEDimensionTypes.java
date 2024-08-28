package dev.theagameplayer.exoticexpansion.registries.data;

import java.util.OptionalLong;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.dimension.DimensionType;

public final class EEDimensionTypes {
	public static final ResourceKey<DimensionType> ANCIENT_NETHER = ResourceKey.create(Registries.DIMENSION_TYPE, ExoticExpansionMod.namespace("the_ancient_nether"));
	
	public static final void bootstrap(final BootstrapContext<DimensionType> pContext) {
		pContext.register(ANCIENT_NETHER, new DimensionType(OptionalLong.empty(), true, false, false, false, 12.0D, false, true, -64, 384, 384, BlockTags.INFINIBURN_OVERWORLD, ExoticExpansionMod.namespace("the_ancient_nether"), 0.0F, new DimensionType.MonsterSettings(true, false, ConstantInt.of(11), 15)));
	}
}
