package dev.theagameplayer.exoticexpansion.registries;

import java.util.function.Supplier;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import dev.theagameplayer.exoticexpansion.world.level.block.AncientNetherAltarBlock;
import dev.theagameplayer.exoticexpansion.world.level.block.AncientNetherPortalBlock;
import dev.theagameplayer.exoticexpansion.world.level.block.BlazeLanternBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EEBlocks {
	public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(BuiltInRegistries.BLOCK, ExoticExpansionMod.MODID);

	public static final Supplier<FenceBlock> RED_NETHER_BRICK_FENCE = BLOCK.register("red_nether_brick_fence", () -> new FenceBlock(BlockBehaviour.Properties.of()
			.mapColor(MapColor.NETHER)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(2.0F, 6.0F)
            .sound(SoundType.NETHER_BRICKS)));
	public static final Supplier<BlazeLanternBlock> BLAZE_LANTERN = BLOCK.register("blaze_lantern", () -> new BlazeLanternBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .forceSolidOn()
            .requiresCorrectToolForDrops()
            .strength(3.5F)
            .sound(SoundType.LANTERN)
            .lightLevel(state -> 10)
            .noOcclusion()
            .pushReaction(PushReaction.DESTROY)));
	public static final Supplier<AncientNetherAltarBlock> ANCIENT_NETHER_ALTAR = BLOCK.register("ancient_nether_altar", () -> new AncientNetherAltarBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BLACK)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .lightLevel(state -> state.getValue(AncientNetherAltarBlock.HAS_ORB) ? 15 : 0)
            .strength(-1.0F, 3600000.0F)
            .noLootTable()));
	public static final Supplier<AncientNetherPortalBlock> ANCIENT_NETHER_PORTAL = BLOCK.register("ancient_nether_portal", () -> new AncientNetherPortalBlock(BlockBehaviour.Properties.of()
			.mapColor(MapColor.COLOR_BLACK)
            .noCollission()
            .strength(-1.0F, 3600000.0F)
            .sound(SoundType.GLASS)
            .lightLevel(state -> 11)
            .pushReaction(PushReaction.BLOCK)
            .noLootTable()));
}
