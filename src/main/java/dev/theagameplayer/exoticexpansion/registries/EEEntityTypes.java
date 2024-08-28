package dev.theagameplayer.exoticexpansion.registries;

import java.util.function.Supplier;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import dev.theagameplayer.exoticexpansion.client.renderer.entity.SpawnerSlimeRenderer;
import dev.theagameplayer.exoticexpansion.world.entity.monster.SpawnerSlime;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EEEntityTypes {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ExoticExpansionMod.MODID);

	public static final Supplier<EntityType<SpawnerSlime>> SPAWNER_SLIME = register("spawner_slime", EntityType.Builder.of(SpawnerSlime::new, MobCategory.MONSTER)
			.fireImmune().sized(0.52F, 0.52F).eyeHeight(0.325F).spawnDimensionsScale(4.0F).setTrackingRange(8));
	
	private static final <E extends Entity> Supplier<EntityType<E>> register(final String pName, final EntityType.Builder<E> pEntityType) {
		return ENTITY_TYPE.register(pName, () -> pEntityType.build(pName));
	}
	
	public static final void registerEntityRenderers(final EntityRenderersEvent.RegisterRenderers pEvent) {
		pEvent.registerEntityRenderer(SPAWNER_SLIME.get(), SpawnerSlimeRenderer::new);
	}
	
	public static final void registerEntityAttributes(final EntityAttributeCreationEvent pEvent) {
		pEvent.put(SPAWNER_SLIME.get(), Monster.createMonsterAttributes().build());
	}
	
	public static final void registerEntitySpawnPlacements(final RegisterSpawnPlacementsEvent pEvent) {
		pEvent.register(SPAWNER_SLIME.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
	}
}
