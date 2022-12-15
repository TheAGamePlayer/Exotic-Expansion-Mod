package dev.theagameplayer.blightedworlds.registries;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import dev.theagameplayer.blightedworlds.client.particle.provider.SpawnerSlimeProvider;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class BWParticleTypes {
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, BlightedWorldsMod.MODID);

	public static final RegistryObject<SimpleParticleType> ACTIVE_SPAWNER_SLIME_BALL = PARTICLE_TYPES.register("active_spawner_slime_ball", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> INACTIVE_SPAWNER_SLIME_BALL = PARTICLE_TYPES.register("inactive_spawner_slime_ball", () -> new SimpleParticleType(false));
	
	public static final void registerProviders(final RegisterParticleProvidersEvent eventIn) {
		eventIn.register(ACTIVE_SPAWNER_SLIME_BALL.get(), SpawnerSlimeProvider::new);
		eventIn.register(INACTIVE_SPAWNER_SLIME_BALL.get(), SpawnerSlimeProvider::new);
	}
}
