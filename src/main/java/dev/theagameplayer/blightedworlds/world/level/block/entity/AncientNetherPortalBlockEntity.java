package dev.theagameplayer.blightedworlds.world.level.block.entity;

import dev.theagameplayer.blightedworlds.registries.BWBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public final class AncientNetherPortalBlockEntity extends BlockEntity {
	public AncientNetherPortalBlockEntity(final BlockEntityType<?> blockEntityTypeIn, final BlockPos posIn, final BlockState stateIn) {
		super(blockEntityTypeIn, posIn, stateIn);
	}

	public AncientNetherPortalBlockEntity(final BlockPos posIn, final BlockState stateIn) {
		super(BWBlockEntityTypes.ANCIENT_NETHER_PORTAL.get(), posIn, stateIn);
	}
}
