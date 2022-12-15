package dev.theagameplayer.blightedworlds.data.loot.packs;

import java.util.stream.Stream;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import dev.theagameplayer.blightedworlds.registries.BWEntityTypes;
import net.minecraft.data.loot.packs.VanillaEntityLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;

public final class BWEntityLoot extends VanillaEntityLoot {
	public static final ResourceLocation ENTITIES_SPAWNER_SLIME_ACTIVE = new ResourceLocation(BlightedWorldsMod.MODID, "entities/spawner_slime/active");
	public static final ResourceLocation ENTITIES_SPAWNER_SLIME_INACTIVE = new ResourceLocation(BlightedWorldsMod.MODID, "entities/spawner_slime/inactive");

	@Override
	protected final Stream<EntityType<?>> getKnownEntityTypes() {
		return ForgeRegistries.ENTITY_TYPES.getValues().stream()
				.filter(entityType -> BlightedWorldsMod.MODID.equals(ForgeRegistries.ENTITY_TYPES.getKey(entityType).getNamespace()));
	}

	@Override
	public final void generate() {
		//Spawner Slime
		this.add(BWEntityTypes.SPAWNER_SLIME.get(), LootTable.lootTable());
		this.addSpawnerSlimeLootTable(ENTITIES_SPAWNER_SLIME_ACTIVE, 2.0F, LootPool.lootPool()
				.setRolls(UniformGenerator.between(0.0F, 1.0F))
				.add(LootItem.lootTableItem(Items.BLAZE_POWDER)
						.when(LootItemKilledByPlayerCondition.killedByPlayer())));
		this.addSpawnerSlimeLootTable(ENTITIES_SPAWNER_SLIME_INACTIVE, 1.0F, LootPool.lootPool()
				.setRolls(UniformGenerator.between(0.0F, 1.0F))
				.add(LootItem.lootTableItem(Items.IRON_INGOT)
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));
	}

	private final void addSpawnerSlimeLootTable(final ResourceLocation idIn, final float slimeMaxIn, final LootPool.Builder builderIn) {
		this.add(BWEntityTypes.SPAWNER_SLIME.get(), idIn, LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.SLIME_BALL)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, slimeMaxIn)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
								.when(this.killedByFrog().invert()))
						.add(LootItem.lootTableItem(Items.IRON_NUGGET)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
								.when(this.killedByFrog().invert())
								.when(LootItemKilledByPlayerCondition.killedByPlayer())))
				.withPool(builderIn));
	}
}
