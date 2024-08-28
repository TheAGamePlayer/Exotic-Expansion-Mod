package dev.theagameplayer.exoticexpansion.data.loot.packs;

import java.util.stream.Stream;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import dev.theagameplayer.exoticexpansion.registries.EEEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.packs.VanillaEntityLoot;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public final class EEEntityLoot extends VanillaEntityLoot {
	public static final ResourceKey<LootTable> ENTITIES_SPAWNER_SLIME_ACTIVE = register("entities/spawner_slime/active");
	public static final ResourceKey<LootTable> ENTITIES_SPAWNER_SLIME_INACTIVE = register("entities/spawner_slime/inactive");

	public EEEntityLoot(final HolderLookup.Provider pRegistries) {
		super(pRegistries);
	}
	
	@Override
	protected final Stream<EntityType<?>> getKnownEntityTypes() {
		return BuiltInRegistries.ENTITY_TYPE.stream()
				.filter(entityType -> ExoticExpansionMod.MODID.equals(BuiltInRegistries.ENTITY_TYPE.getKey(entityType).getNamespace()));
	}

	@Override
	public final void generate() {
		//Spawner Slime
		this.add(EEEntityTypes.SPAWNER_SLIME.get(), LootTable.lootTable());
		this.addSpawnerSlimeLootTable(ENTITIES_SPAWNER_SLIME_ACTIVE, 2.0F, LootPool.lootPool()
				.setRolls(UniformGenerator.between(0.0F, 1.0F))
				.add(LootItem.lootTableItem(Items.BLAZE_POWDER)
						.when(LootItemKilledByPlayerCondition.killedByPlayer())));
		this.addSpawnerSlimeLootTable(ENTITIES_SPAWNER_SLIME_INACTIVE, 1.0F, LootPool.lootPool()
				.setRolls(UniformGenerator.between(0.0F, 1.0F))
				.add(LootItem.lootTableItem(Items.IRON_INGOT)
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries, 0.025F, 0.01F))));
	}

	private final void addSpawnerSlimeLootTable(final ResourceKey<LootTable> pId, final float pSlimeMax, final LootPool.Builder pBuilder) {
		this.add(EEEntityTypes.SPAWNER_SLIME.get(), pId, LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.SLIME_BALL)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, pSlimeMax)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
								.when(this.killedByFrog().invert()))
						.add(LootItem.lootTableItem(Items.IRON_NUGGET)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
								.when(this.killedByFrog().invert())
								.when(LootItemKilledByPlayerCondition.killedByPlayer())))
				.withPool(pBuilder));
	}
	
	private static final ResourceKey<LootTable> register(final String pName) {
		return ResourceKey.create(Registries.LOOT_TABLE, ExoticExpansionMod.namespace(pName));
	}
}
