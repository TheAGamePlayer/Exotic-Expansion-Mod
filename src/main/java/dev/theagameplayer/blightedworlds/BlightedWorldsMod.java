package dev.theagameplayer.blightedworlds;

import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.theagameplayer.blightedworlds.data.BWRegistriesDatapackProvider;
import dev.theagameplayer.blightedworlds.data.BWSpriteSourceProvider;
import dev.theagameplayer.blightedworlds.data.loot.BWLootTableProvider;
import dev.theagameplayer.blightedworlds.data.recipes.packs.BWRecipeProvider;
import dev.theagameplayer.blightedworlds.data.tags.BWBiomeTagsProvider;
import dev.theagameplayer.blightedworlds.data.tags.BWBlockTagsProvider;
import dev.theagameplayer.blightedworlds.data.tags.BWItemTagsProvider;
import dev.theagameplayer.blightedworlds.data.tags.BWStructureTagsProvider;
import dev.theagameplayer.blightedworlds.registries.BWBlockEntityTypes;
import dev.theagameplayer.blightedworlds.registries.BWBlocks;
import dev.theagameplayer.blightedworlds.registries.BWEntityTypes;
import dev.theagameplayer.blightedworlds.registries.BWItems;
import dev.theagameplayer.blightedworlds.registries.BWParticleTypes;
import dev.theagameplayer.blightedworlds.registries.BWStructurePieceTypes;
import dev.theagameplayer.blightedworlds.registries.BWStructureTypes;
import dev.theagameplayer.blightedworlds.registries.other.BWLayerDefinitions;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

//TheAGamePlayer was here :>
@Mod(value = BlightedWorldsMod.MODID)
public final class BlightedWorldsMod {
	public static final String MODID = "blightedworlds";
	private static final Logger LOGGER = LogManager.getLogger(MODID);
	
	public BlightedWorldsMod() {
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		this.createConfig();
		this.registerAll(modEventBus);
		this.createRegistries(modEventBus);
		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(this::clientSetup);
		modEventBus.addListener(this::gatherData);
		if (FMLEnvironment.dist.isClient())
			this.attachClientEventListeners(modEventBus, MinecraftForge.EVENT_BUS);
		this.attachCommonEventListeners(modEventBus, MinecraftForge.EVENT_BUS);
	}
	
	private final void createConfig() {
		LOGGER.info("Created mod config.");
	}
	
	private final void registerAll(final IEventBus busIn) {
		BWBlocks.BLOCKS.register(busIn);
		BWItems.ITEMS.register(busIn);
		BWBlockEntityTypes.BLOCK_ENTITY_TYPES.register(busIn);
		BWEntityTypes.ENTITY_TYPES.register(busIn);
		BWParticleTypes.PARTICLE_TYPES.register(busIn);
		BWStructureTypes.STRUCTURE_TYPES.register(busIn);
		BWStructurePieceTypes.STRUCTURE_PIECE_TYPES.register(busIn);
		LOGGER.info("Registered all event buses.");
	}
	
	private final void createRegistries(final IEventBus busIn) {
		LOGGER.info("Created custom registries.");
	}
	
	private final void attachClientEventListeners(final IEventBus modBusIn, final IEventBus forgeBusIn) {
		//Registers
		modBusIn.addListener(BWBlockEntityTypes::registerBlockEntityRenderers);
		modBusIn.addListener(BWEntityTypes::registerEntityRenderers);
		modBusIn.addListener(BWParticleTypes::registerProviders);
		modBusIn.addListener(BWLayerDefinitions::registerLayerDefinitions);
	}
	
	private final void attachCommonEventListeners(final IEventBus modBusIn, final IEventBus forgeBusIn) {
		//Registers
		modBusIn.addListener(BWEntityTypes::registerEntityAttributes);
		modBusIn.addListener(BWEntityTypes::registerEntitySpawnPlacements);
		modBusIn.addListener(BWItems::registerCreativeModeTabs);
	}
	
	private final void commonSetup(final FMLCommonSetupEvent eventIn) {
		BWOverrides.doOverrides();
		LOGGER.info("Finished common setup.");
	}
	
	private final void clientSetup(final FMLClientSetupEvent eventIn) {
		LOGGER.info("Finished client setup.");
	}
	
	private final void gatherData(final GatherDataEvent eventIn) {
		final DataGenerator generator = eventIn.getGenerator();
		final ExistingFileHelper existingFileHelper = eventIn.getExistingFileHelper();
		final CompletableFuture<HolderLookup.Provider> lookupProvider = eventIn.getLookupProvider();
		if (eventIn.includeServer()) {
			generator.addProvider(true, new BWRegistriesDatapackProvider(generator.getPackOutput(), lookupProvider));
			generator.addProvider(true, new BWSpriteSourceProvider(generator.getPackOutput(), existingFileHelper));
			generator.addProvider(true, new BWLootTableProvider(generator.getPackOutput()));
			generator.addProvider(true, new BWRecipeProvider(generator.getPackOutput()));
			generator.addProvider(true, new BWBiomeTagsProvider(generator.getPackOutput(), lookupProvider, existingFileHelper));
			generator.addProvider(true, new BWBlockTagsProvider(generator.getPackOutput(), lookupProvider, existingFileHelper));
			generator.addProvider(true, new BWItemTagsProvider(generator.getPackOutput(), lookupProvider, existingFileHelper));
			generator.addProvider(true, new BWStructureTagsProvider(generator.getPackOutput(), lookupProvider, existingFileHelper));
		}
		LOGGER.info("Generated new data.");
	}
}
