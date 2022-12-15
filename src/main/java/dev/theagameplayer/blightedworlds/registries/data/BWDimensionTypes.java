package dev.theagameplayer.blightedworlds.registries.data;

import java.util.OptionalLong;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.dimension.DimensionType;

public final class BWDimensionTypes {
	public static final ResourceKey<DimensionType> ANCIENT_NETHER = ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation(BlightedWorldsMod.MODID, "the_ancient_nether"));
	
	public static final void bootstrap(final BootstapContext<DimensionType> contextIn) {
		contextIn.register(ANCIENT_NETHER, new DimensionType(OptionalLong.empty(), true, false, true, false, 12.0D, false, true, -64, 384, 384, BlockTags.INFINIBURN_OVERWORLD, new ResourceLocation(BlightedWorldsMod.MODID, "the_ancient_nether"), 0.0F, new DimensionType.MonsterSettings(true, false, ConstantInt.of(11), 15)));
	}
}
