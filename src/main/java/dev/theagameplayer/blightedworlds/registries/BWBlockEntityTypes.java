package dev.theagameplayer.blightedworlds.registries;

import java.util.function.Supplier;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import dev.theagameplayer.blightedworlds.client.renderer.blockentity.AncientNetherPortalRenderer;
import dev.theagameplayer.blightedworlds.world.level.block.entity.AncientNetherPortalBlockEntity;
import dev.theagameplayer.blightedworlds.world.level.block.entity.BlazeLanternBlockEntity;
import net.minecraft.Util;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class BWBlockEntityTypes {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BlightedWorldsMod.MODID);

	public static final RegistryObject<BlockEntityType<BlazeLanternBlockEntity>> BLAZE_LANTERN = register("blaze_lantern", () -> BlockEntityType.Builder.of(BlazeLanternBlockEntity::new, BWBlocks.BLAZE_LANTERN.get()));
	public static final RegistryObject<BlockEntityType<AncientNetherPortalBlockEntity>> ANCIENT_NETHER_PORTAL = register("ancient_nether_portal", () -> BlockEntityType.Builder.of(AncientNetherPortalBlockEntity::new, BWBlocks.ANCIENT_NETHER_PORTAL.get()));
	
	private static final <BE extends BlockEntity> RegistryObject<BlockEntityType<BE>> register(final String nameIn, final Supplier<BlockEntityType.Builder<BE>> blockEntityTypeIn) {
		return BLOCK_ENTITY_TYPES.register(nameIn, () -> blockEntityTypeIn.get().build(Util.fetchChoiceType(References.BLOCK_ENTITY, nameIn)));
	}
	
	public static final void registerBlockEntityRenderers(final EntityRenderersEvent.RegisterRenderers eventIn) {
		eventIn.registerBlockEntityRenderer(ANCIENT_NETHER_PORTAL.get(), AncientNetherPortalRenderer::new);
	}
}
