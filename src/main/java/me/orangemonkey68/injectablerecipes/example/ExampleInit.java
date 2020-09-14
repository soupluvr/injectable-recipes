package me.orangemonkey68.injectablerecipes.example;

import me.orangemonkey68.injectablerecipes.RecipeHolder;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused") //Example entrypoint
public class ExampleInit implements ModInitializer, RecipeHolder {
    private static final Map<RecipeType<?>, Map<Identifier, Recipe<?>>> recipes = new HashMap<>();

    @Override
    public void onInitialize() {
        //It's recommended to construct the recipe map onInitialize, then return that map in getRecipes()

        Identifier recipeId = new Identifier("injectable-recipes", "dirtToDiamondsRecipe");

        DefaultedList<Ingredient> recipeIngredients = DefaultedList.of();
        recipeIngredients.add(Ingredient.ofItems(Items.DIRT));

        //A shapeless recipe that turns dirt into tiamonds
        ShapelessRecipe exampleRecipe = new ShapelessRecipe(
                recipeId,
                "example-recipes",
                new ItemStack(Items.DIAMOND),
                recipeIngredients);

        recipes.get(RecipeType.CRAFTING).put(recipeId, exampleRecipe);
    }

    @Override
    public Map<RecipeType<?>, Map<Identifier, Recipe<?>>> getRecipes() {
        return recipes;
    }
}
