package dev.theagameplayer.exoticexpansion;

import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.theagameplayer.exoticexpansion.data.EERegistriesDatapackProvider;
import dev.theagameplayer.exoticexpansion.data.EESpriteSourceProvider;
import dev.theagameplayer.exoticexpansion.data.loot.EELootTableProvider;
import dev.theagameplayer.exoticexpansion.data.recipes.packs.EERecipeProvider;
import dev.theagameplayer.exoticexpansion.data.tags.EEBiomeTagsProvider;
import dev.theagameplayer.exoticexpansion.data.tags.EEBlockTagsProvider;
import dev.theagameplayer.exoticexpansion.data.tags.EEItemTagsProvider;
import dev.theagameplayer.exoticexpansion.data.tags.EEStructureTagsProvider;
import dev.theagameplayer.exoticexpansion.event.EEClientSoundEvents;
import dev.theagameplayer.exoticexpansion.registries.EEBlockEntityTypes;
import dev.theagameplayer.exoticexpansion.registries.EEBlocks;
import dev.theagameplayer.exoticexpansion.registries.EEChunkGenerators;
import dev.theagameplayer.exoticexpansion.registries.EEEntityTypes;
import dev.theagameplayer.exoticexpansion.registries.EEItems;
import dev.theagameplayer.exoticexpansion.registries.EEParticleTypes;
import dev.theagameplayer.exoticexpansion.registries.EEStructurePieceTypes;
import dev.theagameplayer.exoticexpansion.registries.EEStructureTypes;
import dev.theagameplayer.exoticexpansion.registries.other.EELayerDefinitions;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

//TheAGamePlayer was here :>
@Mod(ExoticExpansionMod.MODID)
public final class ExoticExpansionMod {
	public static final String MODID = "exoticexpansion";
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public ExoticExpansionMod(final IEventBus pModEventBus) {
		this.registerAll(pModEventBus);
		this.createRegistries(pModEventBus);
		this.createConfig();
		pModEventBus.addListener(this::commonSetup);
		pModEventBus.addListener(this::clientSetup);
		pModEventBus.addListener(this::gatherData);
		if (FMLEnvironment.dist.isClient())
			attachClientEventListeners(pModEventBus, NeoForge.EVENT_BUS);
		attachCommonEventListeners(pModEventBus, NeoForge.EVENT_BUS);
	}

	public static final ResourceLocation namespace(final String pName) {
		return ResourceLocation.fromNamespaceAndPath(MODID, pName);
	}

	private final void createConfig() {
		LOGGER.info("Created mod config.");
	}

	private final void createRegistries(final IEventBus pBus) {
		LOGGER.info("Created custom registries.");
	}

	private final void registerAll(final IEventBus pBus) {
		EEBlocks.BLOCK.register(pBus);
		EEItems.ITEM.register(pBus);
		EEItems.CREATIVE_MODE_TAB.register(pBus);
		EEBlockEntityTypes.BLOCK_ENTITY_TYPE.register(pBus);
		EEEntityTypes.ENTITY_TYPE.register(pBus);
		EEParticleTypes.PARTICLE_TYPE.register(pBus);
		EEStructureTypes.STRUCTURE_TYPE.register(pBus);
		EEStructurePieceTypes.STRUCTURE_PIECE.register(pBus);
		EEChunkGenerators.CHUNK_GENERATORS.register(pBus);
		LOGGER.info("Registered all event buses.");
	}

	public static final void attachClientEventListeners(final IEventBus pModBus, final IEventBus pForgeBus) {
		//Registers
		pModBus.addListener(EEBlockEntityTypes::registerBlockEntityRenderers);
		pModBus.addListener(EEEntityTypes::registerEntityRenderers);
		pModBus.addListener(EEParticleTypes::registerProviders);
		pModBus.addListener(EELayerDefinitions::registerLayerDefinitions);
		//Sounds
		pForgeBus.addListener(EEClientSoundEvents::playStreamingSource);
	}

	public static final void attachCommonEventListeners(final IEventBus pModBus, final IEventBus pForgeBus) {
		//Registers
		pModBus.addListener(EERegistriesDatapackProvider::registerDatapackRegistries);
		pModBus.addListener(EEEntityTypes::registerEntityAttributes);
		pModBus.addListener(EEEntityTypes::registerEntitySpawnPlacements);
	}

	private final void commonSetup(final FMLCommonSetupEvent pEvent) {
		EEOverrides.doOverrides();
		LOGGER.info("Finished common setup.");
	}

	private final void clientSetup(final FMLClientSetupEvent pEvent) {
		LOGGER.info("Finished client setup.");
	}

	private final void gatherData(final GatherDataEvent pEvent) {
		final DataGenerator generator = pEvent.getGenerator();
		final ExistingFileHelper existingFileHelper = pEvent.getExistingFileHelper();
		final CompletableFuture<HolderLookup.Provider> provider = pEvent.getLookupProvider();
		if (pEvent.includeServer()) {
			generator.addProvider(true, new EERegistriesDatapackProvider(generator.getPackOutput(), provider));
			generator.addProvider(true, new EESpriteSourceProvider(generator.getPackOutput(), provider, existingFileHelper));
			generator.addProvider(true, new EELootTableProvider(generator.getPackOutput(), provider));
			generator.addProvider(true, new EERecipeProvider(generator.getPackOutput(), provider));
			generator.addProvider(true, new EEBiomeTagsProvider(generator.getPackOutput(), provider, existingFileHelper));
			final EEBlockTagsProvider blockTags = new EEBlockTagsProvider(generator.getPackOutput(), provider, existingFileHelper);
			generator.addProvider(true, blockTags);
			generator.addProvider(true, new EEItemTagsProvider(generator.getPackOutput(), provider, blockTags.contentsGetter(), existingFileHelper));
			generator.addProvider(true, new EEStructureTagsProvider(generator.getPackOutput(), provider, existingFileHelper));
		}
		LOGGER.info("Generated new data.");
	}
}
