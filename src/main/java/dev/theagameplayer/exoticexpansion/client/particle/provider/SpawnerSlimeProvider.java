package dev.theagameplayer.exoticexpansion.client.particle.provider;

import dev.theagameplayer.exoticexpansion.client.particle.TranslucentBreakingItemParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SpawnerSlimeProvider implements ParticleProvider<SimpleParticleType> {
	private final SpriteSet spriteSet;
	
	public SpawnerSlimeProvider(final SpriteSet pSpriteSet) {
		this.spriteSet = pSpriteSet;
	}
	
	@Override
	public final Particle createParticle(final SimpleParticleType pType, final ClientLevel pLevel, final double pX, final double pY, final double pZ, final double pXSpeed, final double pYSpeed, final double pZSpeed) {
		final TranslucentBreakingItemParticle particle = new TranslucentBreakingItemParticle(pLevel, pX, pY, pZ, new ItemStack(Items.SLIME_BALL));
		particle.pickSprite(this.spriteSet);
		particle.setAlpha(0.675F);
		return particle;
	}
}
