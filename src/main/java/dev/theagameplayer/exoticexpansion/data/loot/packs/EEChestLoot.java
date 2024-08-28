package dev.theagameplayer.exoticexpansion.data.loot.packs;

import java.util.function.BiConsumer;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public final class EEChestLoot implements LootTableSubProvider {
	@SuppressWarnings("unused")
	private final HolderLookup.Provider registeries;
	public static final ResourceKey<LootTable> CHESTS_INFERNAL_NETHER_BRIDGE = register("chests/infernal_nether_bridge");
	public static final ResourceKey<LootTable> CHESTS_INFERNAL_NETHER_BRIDGE_STALK_ROOM = register("chests/infernal_nether_bridge_stalk_room");
	public static final ResourceKey<LootTable> CHESTS_INFERNAL_NETHER_BRIDGE_THRONE = register("chests/infernal_nether_bridge_throne");
	public static final ResourceKey<LootTable> CHESTS_INFERNAL_NETHER_BRIDGE_GRAND_THRONE = register("chests/infernal_nether_bridge_grand_throne");

	public EEChestLoot(final HolderLookup.Provider pRegistries) {
		this.registeries = pRegistries;
	}
	
	@Override
	public final void generate(final BiConsumer<ResourceKey<LootTable>, LootTable.Builder> pOutput) {
		pOutput.accept(CHESTS_INFERNAL_NETHER_BRIDGE, infernalFortressLootTableBuilder(0, 0));
		pOutput.accept(CHESTS_INFERNAL_NETHER_BRIDGE_STALK_ROOM, infernalFortressLootTableBuilder(3, 0));
		pOutput.accept(CHESTS_INFERNAL_NETHER_BRIDGE_THRONE, infernalFortressLootTableBuilder(0, 2));
		pOutput.accept(CHESTS_INFERNAL_NETHER_BRIDGE_GRAND_THRONE, infernalFortressLootTableBuilder(0, 3));
	}

	private final LootTable.Builder infernalFortressLootTableBuilder(final int pJunk, final int pRich) {
		return LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(UniformGenerator.between(2.0F, 4.0F))
						.add(LootItem.lootTableItem(Items.DIAMOND)
								.setWeight(5 + pRich - pJunk)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F + pRich))))
						.add(LootItem.lootTableItem(Items.EMERALD)
								.setWeight(0 + pJunk)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
						.add(LootItem.lootTableItem(Items.IRON_INGOT)
								.setWeight(6 + pRich - pJunk)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F + pRich))))
						.add(LootItem.lootTableItem(Items.IRON_NUGGET)
								.setWeight(9 - pJunk)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 12.0F + pRich))))
						.add(LootItem.lootTableItem(Items.GOLD_INGOT)
								.setWeight(15 + pRich - pJunk)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F + pRich))))
						.add(LootItem.lootTableItem(Items.GOLD_NUGGET)
								.setWeight(18 - pJunk)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 9.0F))))
						.add(LootItem.lootTableItem(Items.GOLDEN_SWORD)
								.setWeight(6))
						.add(LootItem.lootTableItem(Items.GOLDEN_CHESTPLATE)
								.setWeight(6))
						.add(LootItem.lootTableItem(Items.FLINT_AND_STEEL)
								.setWeight(7))
						.add(LootItem.lootTableItem(Items.NETHER_WART)
								.setWeight(6 + pJunk - pRich)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 7.0F + pJunk))))
						.add(LootItem.lootTableItem(Items.BONE)
								.setWeight(8 + pJunk - pRich)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F + pJunk))))
						.add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
								.setWeight(11 + pJunk - pRich)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))
						.add(LootItem.lootTableItem(Items.SADDLE)
								.setWeight(12))
						.add(LootItem.lootTableItem(Items.GOLDEN_HORSE_ARMOR)
								.setWeight(9 - pJunk))
						.add(LootItem.lootTableItem(Items.IRON_HORSE_ARMOR)
								.setWeight(5 - pJunk))
						.add(LootItem.lootTableItem(Items.DIAMOND_HORSE_ARMOR)
								.setWeight(3 - pJunk))
						.add(LootItem.lootTableItem(Blocks.OBSIDIAN)
								.setWeight(2)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 7.0F))))
						.add(LootItem.lootTableItem(Items.NETHERITE_SCRAP)
								.setWeight(1 + pRich)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))));
	}
	
	private static final ResourceKey<LootTable> register(final String pName) {
		return ResourceKey.create(Registries.LOOT_TABLE, ExoticExpansionMod.namespace(pName));
	}
}
