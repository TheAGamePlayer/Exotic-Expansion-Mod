package dev.theagameplayer.blightedworlds.client.particle.provider;

import dev.theagameplayer.blightedworlds.client.particle.TranslucentTextureParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public final class SpawnerSlimeProvider implements ParticleProvider<SimpleParticleType> {
	private final SpriteSet spriteSet;
	
	public SpawnerSlimeProvider(final SpriteSet spriteSetIn) {
		this.spriteSet = spriteSetIn;
	}
	
	@Override
	public final Particle createParticle(final SimpleParticleType particleTypeIn, final ClientLevel levelIn, final double xdIn, final double ydIn, final double zdIn, final double xOffsetIn, final double yOffsetIn, final double zOffsetIn) {
		final TranslucentTextureParticle particle = new TranslucentTextureParticle(levelIn, xdIn, ydIn, zdIn);
		particle.pickSprite(this.spriteSet);
		particle.setAlpha(0.675F);
		return particle;
	}
}
