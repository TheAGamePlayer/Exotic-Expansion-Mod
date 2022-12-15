package dev.theagameplayer.blightedworlds.registries;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import dev.theagameplayer.blightedworlds.client.renderer.blockentity.AncientNetherPortalRenderer;
import dev.theagameplayer.blightedworlds.world.level.block.entity.AncientNetherPortalBlockEntity;
import dev.theagameplayer.blightedworlds.world.level.block.entity.BlazeLanternBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class BWBlockEntityTypes {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BlightedWorldsMod.MODID);

	public static final RegistryObject<BlockEntityType<BlazeLanternBlockEntity>> BLAZE_LANTERN = BLOCK_ENTITY_TYPES.register("blaze_lantern", () -> BlockEntityType.Builder.of(BlazeLanternBlockEntity::new, BWBlocks.BLAZE_LANTERN.get()).build(null));
	public static final RegistryObject<BlockEntityType<AncientNetherPortalBlockEntity>> ANCIENT_NETHER_PORTAL = BLOCK_ENTITY_TYPES.register("ancient_nether_portal", () -> BlockEntityType.Builder.of(AncientNetherPortalBlockEntity::new, BWBlocks.ANCIENT_NETHER_ALTAR.get()).build(null));
	
	public static final void registerBlockEntityRenderers(final EntityRenderersEvent.RegisterRenderers eventIn) {
		eventIn.registerBlockEntityRenderer(ANCIENT_NETHER_PORTAL.get(), AncientNetherPortalRenderer::new);
	}
}
