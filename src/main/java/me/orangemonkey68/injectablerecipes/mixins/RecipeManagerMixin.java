package me.orangemonkey68.injectablerecipes.mixins;

import com.google.common.collect.ImmutableMap;
import me.orangemonkey68.injectablerecipes.InjectableRecipes;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

    @Shadow private Map<RecipeType<?>, Map<Identifier, Recipe<?>>> recipes;

    @Inject(method = "apply", at = @At(value = "TAIL"))
    public void injectRecipes(CallbackInfo ci){
        Map<RecipeType<?>, Map<Identifier, Recipe<?>>> newRecipes = InjectableRecipes.getNewRecipes();
        Map<RecipeType<?>, Map<Identifier, Recipe<?>>> mergedRecipes = Stream.concat(newRecipes.entrySet().stream(), recipes.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1));

        //This probably doesn't NEED to be Immutable, but it's what Mojang uses, so better safe than sorry.
        recipes = ImmutableMap.copyOf(mergedRecipes);
    }
}
