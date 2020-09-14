package me.orangemonkey68.injectablerecipes;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

import java.util.Map;

public class InjectableRecipes {
    private static final Map<Class<? extends RecipeHolder>, Map<RecipeType<?>, Map<Identifier, Recipe<?>>>> holders = new Object2ObjectOpenHashMap<>();

    private InjectableRecipes(){

    }

    public static void register(RecipeHolder holder){
        if(holders.containsKey(holder.getClass())){
            throw new IllegalStateException(String.format("RecipeHolder %s already registered", holder.getClass()));
        }

        holders.put(holder.getClass(), holder.getRecipes());
    }

    public static Map<Class<? extends RecipeHolder>, Map<RecipeType<?>, Map<Identifier, Recipe<?>>>> getHolders(){
        return holders;
    }

    public static Map<RecipeType<?>, Map<Identifier, Recipe<?>>> getNewRecipes(){
        Map<RecipeType<?>, Map<Identifier, Recipe<?>>> retval = new Object2ObjectOpenHashMap<>();
        holders.values().forEach((value) -> retval.forEach((key, val) -> retval.merge(key, val, (v1, v2) -> v1)));
        return retval;
    }

    public static Map<RecipeType<?>, Map<Identifier, Recipe<?>>> getRecipesFromHolder(Class <? extends RecipeHolder> holderClass){
        return holders.get(holderClass);
    }
}
