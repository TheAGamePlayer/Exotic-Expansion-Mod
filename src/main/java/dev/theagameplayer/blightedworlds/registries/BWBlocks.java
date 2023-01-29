package dev.theagameplayer.blightedworlds.registries;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import dev.theagameplayer.blightedworlds.world.level.block.AncientNetherAltarBlock;
import dev.theagameplayer.blightedworlds.world.level.block.AncientNetherPortalBlock;
import dev.theagameplayer.blightedworlds.world.level.block.BlazeLanternBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class BWBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BlightedWorldsMod.MODID);

	public static final RegistryObject<FenceBlock> RED_NETHER_BRICK_FENCE = BLOCKS.register("red_nether_brick_fence", () -> new FenceBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.NETHER).requiresCorrectToolForDrops().strength(2.0F, 6.0F).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<BlazeLanternBlock> BLAZE_LANTERN = BLOCKS.register("blaze_lantern", () -> new BlazeLanternBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.LANTERN).lightLevel((block) -> {
		return 10;
	}).noOcclusion()));
	public static final RegistryObject<AncientNetherAltarBlock> ANCIENT_NETHER_ALTAR  = BLOCKS.register("ancient_nether_altar", () -> new AncientNetherAltarBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).sound(SoundType.NETHERITE_BLOCK).lightLevel((block) -> {
		return block.getValue(AncientNetherAltarBlock.HAS_ORB) ? 15 : 0;
	}).strength(-1.0F, 3600000.0F).noLootTable()));
	public static final RegistryObject<AncientNetherPortalBlock> ANCIENT_NETHER_PORTAL = BLOCKS.register("ancient_nether_portal", () -> new AncientNetherPortalBlock(BlockBehaviour.Properties.of(Material.PORTAL).sound(SoundType.GLASS).noCollission().lightLevel((block) -> {
		return 11;
	}).strength(-1.0F).noLootTable()));
}
