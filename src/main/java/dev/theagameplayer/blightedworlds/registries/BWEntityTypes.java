package dev.theagameplayer.blightedworlds.registries;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import dev.theagameplayer.blightedworlds.client.renderer.entity.SpawnerSlimeRenderer;
import dev.theagameplayer.blightedworlds.world.entity.monster.SpawnerSlime;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class BWEntityTypes {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BlightedWorldsMod.MODID);

	public static final RegistryObject<EntityType<SpawnerSlime>> SPAWNER_SLIME = register("spawner_slime", EntityType.Builder.of(SpawnerSlime::new, MobCategory.MONSTER).sized(2.04F, 2.04F).setTrackingRange(10));
			
	private static final <E extends Entity> RegistryObject<EntityType<E>> register(final String nameIn, final EntityType.Builder<E> entityTypeIn) {
		return ENTITY_TYPES.register(nameIn, () -> entityTypeIn.build(nameIn));
	}
	
	public static final void registerEntityRenderers(final EntityRenderersEvent.RegisterRenderers eventIn) {
		eventIn.registerEntityRenderer(SPAWNER_SLIME.get(), SpawnerSlimeRenderer::new);
	}
	
	public static final void registerEntityAttributes(final EntityAttributeCreationEvent eventIn) {
		eventIn.put(SPAWNER_SLIME.get(), Monster.createMonsterAttributes().build());
	}
	
	public static final void registerEntitySpawnPlacements(final SpawnPlacementRegisterEvent eventIn) {
		eventIn.register(SPAWNER_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
	}
}
