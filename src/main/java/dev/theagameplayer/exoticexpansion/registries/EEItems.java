package dev.theagameplayer.exoticexpansion.registries;

import java.awt.Color;
import java.util.function.Supplier;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import dev.theagameplayer.exoticexpansion.world.item.AltarActivationOrbItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EEItems {
	public static final DeferredRegister<Item> ITEM = DeferredRegister.create(BuiltInRegistries.ITEM, ExoticExpansionMod.MODID);

	//ITEM
	public static final Supplier<AltarActivationOrbItem> ALTAR_ACTIVATION_ORB = ITEM.register("altar_activation_orb", () -> new AltarActivationOrbItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
	public static final Supplier<Item> ANIMATED_EYE = ITEM.register("animated_eye", () -> new Item(new Item.Properties()));
	//BLOCK ITEMS
	public static final Supplier<BlockItem> RED_NETHER_BRICK_FENCE = ITEM.register("red_nether_brick_fence", () -> new BlockItem(EEBlocks.RED_NETHER_BRICK_FENCE.get(), new Item.Properties().fireResistant()));
	public static final Supplier<BlockItem> BLAZE_LANTERN = ITEM.register("blaze_lantern", () -> new BlockItem(EEBlocks.BLAZE_LANTERN.get(), new Item.Properties()));
	public static final Supplier<BlockItem> ANCIENT_NETHER_ALTAR = ITEM.register("ancient_nether_altar", () -> new BlockItem(EEBlocks.ANCIENT_NETHER_ALTAR.get(), new Item.Properties().rarity(Rarity.RARE)));
	//SPAWN EGGS
	public static final Supplier<DeferredSpawnEggItem> SPAWNER_SLIME_SPAWN_EGG = ITEM.register("spawner_slime_spawn_egg", () -> new DeferredSpawnEggItem(() -> EEEntityTypes.SPAWNER_SLIME.get(), new Color(135, 90, 90).getRGB(), new Color(55, 70, 100).getRGB(), new Item.Properties()));
	
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, ExoticExpansionMod.MODID);
	
	public static final Supplier<CreativeModeTab> EXOTIC_EXPANSION = CREATIVE_MODE_TAB.register(ExoticExpansionMod.MODID, () -> CreativeModeTab.builder()
			.title(Component.translatable("itemGroup." + ExoticExpansionMod.MODID))
			.icon(() -> new ItemStack(ALTAR_ACTIVATION_ORB.get()))
			.displayItems((parameters, output) -> {
				for (final DeferredHolder<Item, ? extends Item> item : ITEM.getEntries())
					output.accept(item.get());
			}).build());
}
