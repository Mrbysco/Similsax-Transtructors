package com.vorquel.similsaxtranstructors.datagen.server;

import com.vorquel.similsaxtranstructors.registry.SimilsaxRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class SimilsaxRecipeProvider extends RecipeProvider {
  public SimilsaxRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
    super(packOutput, lookupProvider);
  }

  @Override
  protected void buildRecipes(RecipeOutput output, HolderLookup.Provider provider) {
    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SimilsaxRegistry.ADVANCED)
        .pattern("x x")
        .pattern("xox")
        .pattern(" / ")
        .define('x', Tags.Items.INGOTS_IRON)
        .define('o', Tags.Items.ENDER_PEARLS)
        .define('/', Tags.Items.GEMS_DIAMOND)
        .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
        .unlockedBy("has_ender_pearl", has(Tags.Items.ENDER_PEARLS))
        .unlockedBy("has_diamond", has(Tags.Items.GEMS_DIAMOND))
        .save(output);

    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SimilsaxRegistry.BASIC)
        .pattern("x x")
        .pattern("xox")
        .pattern(" / ")
        .define('x', Tags.Items.INGOTS_IRON)
        .define('o', Tags.Items.DYES_CYAN)
        .define('/', Tags.Items.RODS_WOODEN)
        .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
        .unlockedBy("has_cyan_dye", has(Tags.Items.DYES_CYAN))
        .unlockedBy("has_wooden_rod", has(Tags.Items.RODS_WOODEN))
        .save(output);
  }
}
