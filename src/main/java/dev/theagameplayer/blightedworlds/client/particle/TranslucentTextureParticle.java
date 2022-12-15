package dev.theagameplayer.blightedworlds.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;

public final class TranslucentTextureParticle extends TextureSheetParticle { //Code from BreakingItemParticle.class
	private final float uo, vo;

	public TranslucentTextureParticle(final ClientLevel levelIn, final double xdIn, final double ydIn, final double zdIn) {
		super(levelIn, xdIn, ydIn, zdIn);
		this.gravity = 1.0F;
		this.quadSize /= 2.0F;
		this.uo = this.random.nextFloat() * 3.0F;
		this.vo = this.random.nextFloat() * 3.0F;
	}

	@Override
	public final ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	protected final float getU0() {
		return this.sprite.getU((double)((this.uo + 1.0F) / 4.0F * 16.0F));
	}

	@Override
	protected final float getU1() {
		return this.sprite.getU((double)(this.uo / 4.0F * 16.0F));
	}

	@Override
	protected final float getV0() {
		return this.sprite.getV((double)(this.vo / 4.0F * 16.0F));
	}

	@Override
	protected final float getV1() {
		return this.sprite.getV((double)((this.vo + 1.0F) / 4.0F * 16.0F));
	}
	
	@Override
	public final void setAlpha(float alphaIn) { //Overrides protected method
		super.setAlpha(alphaIn);
	}
}
