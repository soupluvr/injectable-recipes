package me.orangemonkey68.injectablerecipes.mixins;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import me.orangemonkey68.injectablerecipes.InjectableRecipes;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

    @Shadow private Map<RecipeType<?>, Map<Identifier, Recipe<?>>> recipes;
    Logger logger = LogManager.getLogger("injectable-recipes");

    @Inject(method = "apply", at = @At(value = "TAIL"))
    public void injectRecipes(CallbackInfo ci){
        Map<RecipeType<?>, Map<Identifier, Recipe<?>>> mergedRecipes = mergeRecipes(InjectableRecipes.getAllRecipes(), new Object2ObjectOpenHashMap<>(Map.copyOf(recipes)));
        //This probably doesn't NEED to be Immutable, but it's what Mojang uses, so better safe than sorry.
        recipes = ImmutableMap.copyOf(mergedRecipes);
    }

    Map<RecipeType<?>, Map<Identifier, Recipe<?>>> mergeRecipes(Map<RecipeType<?>, Map<Identifier, Recipe<?>>> newRecipes, Map<RecipeType<?>, Map<Identifier, Recipe<?>>> existingRecipes){
        Map<RecipeType<?>, Map<Identifier, Recipe<?>>> mergedRecipes = new Object2ObjectOpenHashMap<>(existingRecipes);
        AtomicInteger successfulMerges = new AtomicInteger();
        AtomicInteger failedMerges = new AtomicInteger();

        newRecipes.forEach((type, map) -> map.forEach((id, recipe) -> {
            mergedRecipes.computeIfAbsent(type, f -> new HashMap<>());

            if(mergedRecipes.get(type).get(id) == null){
                mergedRecipes.get(type).put(id, recipe);
                successfulMerges.getAndIncrement();
            }else {
                logger.warn("Recipe with ID {} failed to merge. Are you registering the same ID twice?", id.toString());
                failedMerges.getAndIncrement();
            }
        }));

        logger.info("Successfully merged {} recipes into the game", successfulMerges.get());
        logger.info("Failed to merge {} recipes", failedMerges.get());

        return mergedRecipes;
    }
}
