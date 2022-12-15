package dev.theagameplayer.blightedworlds.registries;

import java.awt.Color;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import dev.theagameplayer.blightedworlds.item.AltarActivationOrbItem;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class BWItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BlightedWorldsMod.MODID);

	//Items
	public static final RegistryObject<AltarActivationOrbItem> ALTAR_ACTIVATION_ORB = ITEMS.register("altar_activation_orb", () -> new AltarActivationOrbItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
	//BlockItems
	public static final RegistryObject<BlockItem> RED_NETHER_BRICK_FENCE = ITEMS.register("red_nether_brick_fence", () -> new BlockItem(BWBlocks.RED_NETHER_BRICK_FENCE.get(), new Item.Properties().fireResistant()));
	public static final RegistryObject<BlockItem> BLAZE_LANTERN = ITEMS.register("blaze_lantern", () -> new BlockItem(BWBlocks.BLAZE_LANTERN.get(), new Item.Properties()));
	public static final RegistryObject<BlockItem> ANCIENT_NETHER_ALTAR = ITEMS.register("ancient_nether_altar", () -> new BlockItem(BWBlocks.ANCIENT_NETHER_ALTAR.get(), new Item.Properties().rarity(Rarity.RARE)));
	//SpawnEggs
    public static final RegistryObject<ForgeSpawnEggItem> SPAWNER_SLIME_SPAWN_EGG = ITEMS.register("spawner_slime_spawn_egg", () -> new ForgeSpawnEggItem(() -> BWEntityTypes.SPAWNER_SLIME.get(), new Color(135, 90, 90).getRGB(), new Color(55, 70, 100).getRGB(), new Item.Properties()));
    
    public static final void registerCreativeModeTabs(final CreativeModeTabEvent.Register eventIn) {
    	eventIn.registerCreativeModeTab(new ResourceLocation(BlightedWorldsMod.MODID, BlightedWorldsMod.MODID), builder -> builder
    			.title(Component.translatable("itemGroup." + BlightedWorldsMod.MODID))
    			.icon(() -> new ItemStack(ALTAR_ACTIVATION_ORB.get()))
    			.displayItems((featureFlagSet, output, hasPermissions) -> {
    				for (final RegistryObject<Item> item : ITEMS.getEntries())
    					output.accept(item.get());
    			}));
    }
}
