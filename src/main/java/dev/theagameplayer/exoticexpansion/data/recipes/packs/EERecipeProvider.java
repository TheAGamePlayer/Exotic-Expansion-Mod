package dev.theagameplayer.exoticexpansion.data.recipes.packs;

import java.util.concurrent.CompletableFuture;

import dev.theagameplayer.exoticexpansion.registries.EEBlocks;
import dev.theagameplayer.exoticexpansion.registries.EEItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public final class EERecipeProvider extends VanillaRecipeProvider {
	public EERecipeProvider(final PackOutput pOutput, final CompletableFuture<HolderLookup.Provider> pRegistries) {
		super(pOutput, pRegistries);
	}

	@Override
	protected final void buildRecipes(final RecipeOutput pRecipeOutput) {
		this.registerShapedRecipes(pRecipeOutput);
		this.registerShapelessRecipes(pRecipeOutput);
	}
	
	private final void registerShapedRecipes(final RecipeOutput pRecipeOutput) {
		//Items
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, EEItems.ALTAR_ACTIVATION_ORB.get())
		.pattern("323").pattern("212").pattern("323")
		.define('1', Items.NETHER_STAR).define('2', Items.ENDER_PEARL).define('3',Items.MAGMA_CREAM)
		.unlockedBy("has_nether_star", has(Items.NETHER_STAR))
		.save(pRecipeOutput);
		//BlockItems
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, EEBlocks.RED_NETHER_BRICK_FENCE.get(), 6)
		.pattern("131").pattern("121")
		.define('1', Blocks.RED_NETHER_BRICKS).define('2', Items.NETHER_BRICK).define('3',Items.NETHER_WART)
		.unlockedBy("has_red_nether_bricks", has(Blocks.RED_NETHER_BRICKS))
		.save(pRecipeOutput);
	}
	
	private final void registerShapelessRecipes(final RecipeOutput pRecipeOutput) {}

}
