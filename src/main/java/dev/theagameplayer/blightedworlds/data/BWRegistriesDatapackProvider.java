package dev.theagameplayer.blightedworlds.data;

import com.mojang.serialization.JsonOps;

import dev.theagameplayer.blightedworlds.BlightedWorldsMod;
import dev.theagameplayer.blightedworlds.registries.data.BWDimensionTypes;
import dev.theagameplayer.blightedworlds.registries.data.BWLevelStems;
import dev.theagameplayer.blightedworlds.registries.data.BWNoiseGeneratorSettings;
import dev.theagameplayer.blightedworlds.registries.data.BWStructureSets;
import dev.theagameplayer.blightedworlds.registries.data.BWStructures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.data.worldgen.biome.Biomes;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;

public final class BWRegistriesDatapackProvider extends DatapackBuiltinEntriesProvider {
	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.STRUCTURE, BWStructures::bootstrap)
			.add(Registries.STRUCTURE_SET, BWStructureSets::bootstrap)
			.add(Registries.DIMENSION_TYPE, BWDimensionTypes::bootstrap)
			.add(Registries.NOISE_SETTINGS, BWNoiseGeneratorSettings::bootstrap);
	
	public BWRegistriesDatapackProvider(final PackOutput packOutputIn) {
		super(packOutputIn, BWRegistriesDatapackProvider::createLookup);
	}
	
	private static final HolderLookup.Provider createLookup() {
		return BUILDER.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), VanillaRegistries.createLookup());
	}
	
	public static final JsonCodecProvider<LevelStem> levelStemProvider(final PackOutput packOutputIn, final ExistingFileHelper existingFileHelperIn) {
		final HolderLookup.Provider lookup = BUILDER.add(Registries.BIOME, Biomes::bootstrap).buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), VanillaRegistries.createLookup()); //Add Vanilla Biomes due to bugginess?
		return new JsonCodecProvider<>(packOutputIn, existingFileHelperIn, BlightedWorldsMod.MODID, RegistryOps.create(JsonOps.INSTANCE, lookup), PackType.SERVER_DATA, Registries.LEVEL_STEM.location().getPath(), LevelStem.CODEC, BWLevelStems.generate(lookup));
	}
}
