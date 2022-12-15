package dev.theagameplayer.blightedworlds.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public final class AncientNetherChunkGenerator extends NoiseBasedChunkGenerator {
	public static final Codec<AncientNetherChunkGenerator> CODEC = RecordCodecBuilder.create(builder -> {
		return builder.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter(generator -> {
			return generator.getBiomeSource();
		}), NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(generator -> {
			return generator.generatorSettings();
		})).apply(builder, builder.stable(AncientNetherChunkGenerator::new));
	});

	public AncientNetherChunkGenerator(final BiomeSource biomeSourceIn, final Holder<NoiseGeneratorSettings> settingsIn) {
		super(biomeSourceIn, settingsIn);
	}

	@Override
	protected final Codec<? extends AncientNetherChunkGenerator> codec() {
		return CODEC;
	}
}
