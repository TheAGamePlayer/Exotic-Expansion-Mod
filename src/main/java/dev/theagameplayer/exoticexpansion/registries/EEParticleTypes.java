package dev.theagameplayer.exoticexpansion.registries;

import java.util.function.Supplier;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;
import dev.theagameplayer.exoticexpansion.client.particle.provider.SpawnerSlimeProvider;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EEParticleTypes {
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPE = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, ExoticExpansionMod.MODID);

	public static final Supplier<SimpleParticleType> ACTIVE_SPAWNER_SLIME_BALL = PARTICLE_TYPE.register("active_spawner_slime_ball", () -> new SimpleParticleType(false));
	public static final Supplier<SimpleParticleType> INACTIVE_SPAWNER_SLIME_BALL = PARTICLE_TYPE.register("inactive_spawner_slime_ball", () -> new SimpleParticleType(false));
	
	public static final void registerProviders(final RegisterParticleProvidersEvent eventIn) {
		eventIn.registerSpriteSet(ACTIVE_SPAWNER_SLIME_BALL.get(), SpawnerSlimeProvider::new);
		eventIn.registerSpriteSet(INACTIVE_SPAWNER_SLIME_BALL.get(), SpawnerSlimeProvider::new);
	}
}
