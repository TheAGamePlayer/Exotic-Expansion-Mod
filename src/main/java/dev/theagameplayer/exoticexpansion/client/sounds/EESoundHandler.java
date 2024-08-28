package dev.theagameplayer.exoticexpansion.client.sounds;

import org.apache.logging.log4j.Logger;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.openal.EXTEfx;

import dev.theagameplayer.exoticexpansion.ExoticExpansionMod;

public final class EESoundHandler {
	private static final Logger LOGGER = ExoticExpansionMod.LOGGER;
	private static boolean hasEfx, setUp;
	private static int aux, effect;

	public static final void playSound(final int pId, final float pDecay) {
		if (!setUp) setUpEffects();
		if (!hasEfx) return;
		final int a = aux;
		final int e = effect;
		EXTEfx.alEffecti(e, EXTEfx.AL_EFFECT_TYPE, EXTEfx.AL_EFFECT_EAXREVERB);
		EXTEfx.alEffectf(e, EXTEfx.AL_EAXREVERB_DECAY_TIME, pDecay);
		EXTEfx.alAuxiliaryEffectSloti(a, EXTEfx.AL_EFFECTSLOT_EFFECT, e);
		AL11.alSource3i(pId, EXTEfx.AL_AUXILIARY_SEND_FILTER, a, 0, EXTEfx.AL_FILTER_NULL);
	}

	public static final void setUpEffects() { //Must be called when sound is being played
		setUp = true;
		hasEfx = AL.getCapabilities().ALC_EXT_EFX;
		if (!hasEfx) {
			LOGGER.warn("OPEN_AL EFX is unsupported!");
			return;
		}
		aux = EXTEfx.alGenAuxiliaryEffectSlots();
		EXTEfx.alAuxiliaryEffectSloti(aux, EXTEfx.AL_EFFECTSLOT_AUXILIARY_SEND_AUTO, AL10.AL_TRUE);
		effect = EXTEfx.alGenEffects();
	}
}
