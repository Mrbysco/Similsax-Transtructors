package com.vorquel.similsaxtranstructors.datagen.server;

import com.vorquel.similsaxtranstructors.registry.SimilsaxRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class SimilsaxRecipeProvider extends RecipeProvider {
  public SimilsaxRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
    super(provider, recipeOutput);
  }

  @Override
  protected void buildRecipes() {
    shaped(RecipeCategory.TOOLS, SimilsaxRegistry.ADVANCED)
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

    shaped(RecipeCategory.TOOLS, SimilsaxRegistry.BASIC)
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

  public static class Runner extends RecipeProvider.Runner {
    public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
      super(output, completableFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
      return new SimilsaxRecipeProvider(provider, recipeOutput);
    }

    @Override
    public String getName() {
      return "Similsax Recipes";
    }
  }
}
