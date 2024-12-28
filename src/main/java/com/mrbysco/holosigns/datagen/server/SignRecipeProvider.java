package com.mrbysco.holosigns.datagen.server;

import com.mrbysco.holosigns.registry.SignReg;
import com.mrbysco.holosigns.registry.SignRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class SignRecipeProvider extends RecipeProvider {
	public SignRecipeProvider(PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
		super(packOutput, lookupProvider);
	}

	@Override
	protected void buildRecipes(RecipeOutput output, HolderLookup.Provider provider) {
		generateSigns(output, Blocks.GLASS, SignRegistry.GLASS);
		generateSigns(output, Blocks.WHITE_STAINED_GLASS, SignRegistry.WHITE_STAINED_GLASS);
		generateSigns(output, Blocks.ORANGE_STAINED_GLASS, SignRegistry.ORANGE_STAINED_GLASS);
		generateSigns(output, Blocks.MAGENTA_STAINED_GLASS, SignRegistry.MAGENTA_STAINED_GLASS);
		generateSigns(output, Blocks.LIGHT_BLUE_STAINED_GLASS, SignRegistry.LIGHT_BLUE_STAINED_GLASS);
		generateSigns(output, Blocks.YELLOW_STAINED_GLASS, SignRegistry.YELLOW_STAINED_GLASS);
		generateSigns(output, Blocks.LIME_STAINED_GLASS, SignRegistry.LIME_STAINED_GLASS);
		generateSigns(output, Blocks.PINK_STAINED_GLASS, SignRegistry.PINK_STAINED_GLASS);
		generateSigns(output, Blocks.GRAY_STAINED_GLASS, SignRegistry.GRAY_STAINED_GLASS);
		generateSigns(output, Blocks.LIGHT_GRAY_STAINED_GLASS, SignRegistry.LIGHT_GRAY_STAINED_GLASS);
		generateSigns(output, Blocks.CYAN_STAINED_GLASS, SignRegistry.CYAN_STAINED_GLASS);
		generateSigns(output, Blocks.PURPLE_STAINED_GLASS, SignRegistry.PURPLE_STAINED_GLASS);
		generateSigns(output, Blocks.BLUE_STAINED_GLASS, SignRegistry.BLUE_STAINED_GLASS);
		generateSigns(output, Blocks.BROWN_STAINED_GLASS, SignRegistry.BROWN_STAINED_GLASS);
		generateSigns(output, Blocks.GREEN_STAINED_GLASS, SignRegistry.GREEN_STAINED_GLASS);
		generateSigns(output, Blocks.RED_STAINED_GLASS, SignRegistry.RED_STAINED_GLASS);
		generateSigns(output, Blocks.BLACK_STAINED_GLASS, SignRegistry.BLACK_STAINED_GLASS);
	}

	private void generateSigns(RecipeOutput output, ItemLike material, SignReg sign) {
		generateSign(output, material, sign.getSignItem());
		generateHangingSign(output, material, sign.getHangingSignItem());
	}

	private void generateSign(RecipeOutput output, ItemLike material, ItemLike sign) {
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, sign, 6)
				.pattern("###")
				.pattern("###")
				.pattern(" S ")
				.group("sign")
				.define('#', material)
				.define('S', Tags.Items.RODS_WOODEN)
				.unlockedBy("has_material", has(material))
				.save(output);
	}


	private void generateHangingSign(RecipeOutput output, ItemLike material, ItemLike sign) {
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, sign, 6)
				.pattern("X X")
				.pattern("###")
				.pattern("###")
				.group("hanging_sign")
				.define('#', material)
				.define('X', Items.CHAIN)
				.unlockedBy("has_material", has(material))
				.save(output);
	}
}
