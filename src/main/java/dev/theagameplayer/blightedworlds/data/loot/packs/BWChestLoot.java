package dev.theagameplayer.blightedworlds.data.loot.packs;

import java.util.function.BiConsumer;
import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public final class BWChestLoot implements LootTableSubProvider {
	public static final ResourceLocation CHESTS_INFERNAL_NETHER_BRIDGE = new ResourceLocation(BlightedWorldsMod.MODID, "chests/infernal_nether_bridge");
	public static final ResourceLocation CHESTS_INFERNAL_NETHER_BRIDGE_STALK_ROOM = new ResourceLocation(BlightedWorldsMod.MODID, "chests/infernal_nether_bridge_stalk_room");
	public static final ResourceLocation CHESTS_INFERNAL_NETHER_BRIDGE_THRONE = new ResourceLocation(BlightedWorldsMod.MODID, "chests/infernal_nether_bridge_throne");
	public static final ResourceLocation CHESTS_INFERNAL_NETHER_BRIDGE_GRAND_THRONE = new ResourceLocation(BlightedWorldsMod.MODID, "chests/infernal_nether_bridge_grand_throne");

	@Override
	public final void generate(final BiConsumer<ResourceLocation, LootTable.Builder> consumerIn) {
		consumerIn.accept(CHESTS_INFERNAL_NETHER_BRIDGE, infernalFortressLootTableBuilder(0, 0));
		consumerIn.accept(CHESTS_INFERNAL_NETHER_BRIDGE_STALK_ROOM, infernalFortressLootTableBuilder(3, 0));
		consumerIn.accept(CHESTS_INFERNAL_NETHER_BRIDGE_THRONE, infernalFortressLootTableBuilder(0, 2));
		consumerIn.accept(CHESTS_INFERNAL_NETHER_BRIDGE_GRAND_THRONE, infernalFortressLootTableBuilder(0, 3));
	}

	private final LootTable.Builder infernalFortressLootTableBuilder(final int junkIn, final int richIn) {
		return LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(UniformGenerator.between(2.0F, 4.0F))
						.add(LootItem.lootTableItem(Items.DIAMOND)
								.setWeight(5 + richIn - junkIn)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F + richIn))))
						.add(LootItem.lootTableItem(Items.EMERALD)
								.setWeight(0 + junkIn)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
						.add(LootItem.lootTableItem(Items.IRON_INGOT)
								.setWeight(6 + richIn - junkIn)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F + richIn))))
						.add(LootItem.lootTableItem(Items.IRON_NUGGET)
								.setWeight(9 - junkIn)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 12.0F + richIn))))
						.add(LootItem.lootTableItem(Items.GOLD_INGOT)
								.setWeight(15 + richIn - junkIn)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F + richIn))))
						.add(LootItem.lootTableItem(Items.GOLD_NUGGET)
								.setWeight(18 - junkIn)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 9.0F))))
						.add(LootItem.lootTableItem(Items.GOLDEN_SWORD)
								.setWeight(6))
						.add(LootItem.lootTableItem(Items.GOLDEN_CHESTPLATE)
								.setWeight(6))
						.add(LootItem.lootTableItem(Items.FLINT_AND_STEEL)
								.setWeight(7))
						.add(LootItem.lootTableItem(Items.NETHER_WART)
								.setWeight(6 + junkIn - richIn)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 7.0F + junkIn))))
						.add(LootItem.lootTableItem(Items.BONE)
								.setWeight(8 + junkIn - richIn)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F + junkIn))))
						.add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
								.setWeight(11 + junkIn - richIn)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))
						.add(LootItem.lootTableItem(Items.SADDLE)
								.setWeight(12))
						.add(LootItem.lootTableItem(Items.GOLDEN_HORSE_ARMOR)
								.setWeight(9 - junkIn))
						.add(LootItem.lootTableItem(Items.IRON_HORSE_ARMOR)
								.setWeight(5 - junkIn))
						.add(LootItem.lootTableItem(Items.DIAMOND_HORSE_ARMOR)
								.setWeight(3 - junkIn))
						.add(LootItem.lootTableItem(Blocks.OBSIDIAN)
								.setWeight(2)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 7.0F))))
						.add(LootItem.lootTableItem(Items.NETHERITE_SCRAP)
								.setWeight(1 + richIn)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))));
	}
}
