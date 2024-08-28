package dev.theagameplayer.exoticexpansion.event;

import java.util.Optional;

import dev.theagameplayer.exoticexpansion.client.sounds.EESoundHandler;
import dev.theagameplayer.exoticexpansion.registries.other.EEDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.neoforged.neoforge.client.event.sound.PlayStreamingSourceEvent;

public final class EEClientSoundEvents {
	public static final void playStreamingSource(final PlayStreamingSourceEvent pEvent) {
		final Minecraft mc = Minecraft.getInstance();
		if (mc.level == null || !mc.level.dimension().equals(EEDimensions.ANCIENT_NETHER)) return;
		final Optional<Music> music = mc.level.getBiome(mc.player.blockPosition()).value().getBackgroundMusic();
		if (music.isEmpty()) return;
		if (Musics.CREATIVE.getEvent().value().getLocation().getPath().equals(pEvent.getName())) {
			pEvent.getEngine().stop(pEvent.getSound());
			mc.getMusicManager().startPlaying(music.orElse(Musics.GAME));
		} else if (music.get().getEvent().value().getLocation().getPath().equals(pEvent.getName())) {
			pEvent.getChannel().setPitch(pEvent.getSound().getPitch() * 0.5F);
			EESoundHandler.playSound(pEvent.getChannel().source, 15.0F);
		}
	}
}
