package dev.theagameplayer.blightedworlds.data.recipes.packs;

import java.util.function.Consumer;

import dev.theagameplayer.blightedworlds.registries.BWBlocks;
import dev.theagameplayer.blightedworlds.registries.BWItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public final class BWRecipeProvider extends VanillaRecipeProvider {
	public BWRecipeProvider(final PackOutput packOutputIn) {
		super(packOutputIn);
	}

	@Override
	protected final void buildRecipes(final Consumer<FinishedRecipe> consumerIn) {
		this.registerShapedRecipes(consumerIn);
		this.registerShapelessRecipes(consumerIn);
	}
	
	private final void registerShapedRecipes(final Consumer<FinishedRecipe> consumerIn) {
		//Items
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BWItems.ALTAR_ACTIVATION_ORB.get())
		.pattern("323").pattern("212").pattern("323")
		.define('1', Items.NETHER_STAR).define('2', Items.ENDER_PEARL).define('3',Items.MAGMA_CREAM)
		.unlockedBy("has_nether_star", has(Items.NETHER_STAR))
		.save(consumerIn);
		//BlockItems
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BWBlocks.RED_NETHER_BRICK_FENCE.get())
		.pattern("131").pattern("121")
		.define('1', Blocks.RED_NETHER_BRICKS).define('2', Items.NETHER_BRICK).define('3',Items.NETHER_WART)
		.unlockedBy("has_red_nether_bricks", has(Blocks.RED_NETHER_BRICKS))
		.save(consumerIn);
	}
	
	private final void registerShapelessRecipes(final Consumer<FinishedRecipe> consumerIn) {}
}
