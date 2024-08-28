package dev.theagameplayer.exoticexpansion.registries;

import java.util.function.Supplier;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import dev.theagameplayer.exoticexpansion.client.renderer.blockentity.AncientNetherPortalRenderer;
import dev.theagameplayer.exoticexpansion.world.level.block.entity.AncientNetherPortalBlockEntity;
import dev.theagameplayer.exoticexpansion.world.level.block.entity.BlazeLanternBlockEntity;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EEBlockEntityTypes {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ExoticExpansionMod.MODID);

	public static final Supplier<BlockEntityType<BlazeLanternBlockEntity>> BLAZE_LANTERN = register("blaze_lantern", () -> BlockEntityType.Builder.of(BlazeLanternBlockEntity::new, EEBlocks.BLAZE_LANTERN.get()));
	public static final Supplier<BlockEntityType<AncientNetherPortalBlockEntity>> ANCIENT_NETHER_PORTAL = register("ancient_nether_portal", () -> BlockEntityType.Builder.of(AncientNetherPortalBlockEntity::new, EEBlocks.ANCIENT_NETHER_PORTAL.get()));
	
	private static final <BE extends BlockEntity> Supplier<BlockEntityType<BE>> register(final String pName, final Supplier<BlockEntityType.Builder<BE>> pBlockEntityType) {
		return BLOCK_ENTITY_TYPE.register(pName, () -> pBlockEntityType.get().build(Util.fetchChoiceType(References.BLOCK_ENTITY, pName)));
	}
	
	public static final void registerBlockEntityRenderers(final EntityRenderersEvent.RegisterRenderers pEvent) {
		pEvent.registerBlockEntityRenderer(ANCIENT_NETHER_PORTAL.get(), AncientNetherPortalRenderer::new);
	}
}
