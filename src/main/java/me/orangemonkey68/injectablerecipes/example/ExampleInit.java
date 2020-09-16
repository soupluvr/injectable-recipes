package me.orangemonkey68.injectablerecipes.example;

import me.orangemonkey68.injectablerecipes.InjectableRecipes;
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
    public Map<RecipeType<?>, Map<Identifier, Recipe<?>>> getRecipes() {
        return recipes;
    }

    @Override
    public void onInitialize() {
        //Creates the sub-map that holds crafting table recipes
        recipes.put(RecipeType.CRAFTING, new HashMap<>());

        //Sets up the Ingredients to pass into the recipe
        DefaultedList<Ingredient> recipeIngredients = DefaultedList.of();
        recipeIngredients.add(Ingredient.ofItems(Items.DIRT));

        ShapelessRecipe exampleRecipe = new ShapelessRecipe(
                new Identifier("injectable-recipes", "test-recipe-id"),
                "example-recipes",
                new ItemStack(Items.DIAMOND),
                recipeIngredients);
        recipes.get(RecipeType.CRAFTING).put(new Identifier("injectable-recipes", "test-recipe"), exampleRecipe);

        //Hands over recipes to Injectable Recipes
        InjectableRecipes.register(this);
    }
}
