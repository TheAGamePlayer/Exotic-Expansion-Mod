package dev.theagameplayer.exoticexpansion.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BreakingItemParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class TranslucentBreakingItemParticle extends BreakingItemParticle {
	public TranslucentBreakingItemParticle(final ClientLevel pLevel, final double pX, final double pY, final double pZ, final ItemStack pStack) {
		super(pLevel, pX, pY, pZ, pStack);
	}

	@Override
	public final ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}
	
	@Override
	public final void setAlpha(float pAlpha) {
		super.setAlpha(pAlpha);
	}
	
    public static class SlimeProvider implements ParticleProvider<SimpleParticleType> {
        public Particle createParticle(
            SimpleParticleType pType,
            ClientLevel pLevel,
            double pX,
            double pY,
            double pZ,
            double pXSpeed,
            double pYSpeed,
            double pZSpeed
        ) {
            return new TranslucentBreakingItemParticle(pLevel, pX, pY, pZ, new ItemStack(Items.SLIME_BALL));
        }
    }
}
