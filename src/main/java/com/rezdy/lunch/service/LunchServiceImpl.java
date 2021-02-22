package com.rezdy.lunch.service;

import com.rezdy.lunch.dao.RecipeDAO;
import com.rezdy.lunch.entity.Ingredient;
import com.rezdy.lunch.entity.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class LunchServiceImpl implements LunchService {

    @Autowired
    private RecipeDAO recipeDAO;

    @Override
    public List<Recipe> getRecipesOnDate(LocalDate date) {
        List<Recipe> recipes = recipeDAO.listAllByDate(date);
        return sortRecipes(recipes);
    }

    @Override
    public List<Recipe> getRecipes() {
        List<Recipe> recipes = recipeDAO.listAll();
        return sortRecipes(recipes);
    }

    @Override
    public List<Recipe> getRecipesOnBestBeforeAndUseBy(LocalDate date) {
        List<Recipe> recipes = recipeDAO.listALLByDateOnBestBeforeAndUseBy(date);
        return sortRecipes(recipes);
    }

    public Optional<Recipe> getRecipeByTitle(String title) {
        return recipeDAO.findById(title);
    }

    @Override
    public List<Recipe> getRecipesExcludedIngredients(List<String> excludedIngredients) {

        return recipeDAO.listRecipesByExcludedIngredients(excludedIngredients);
    }


private List<Recipe> sortRecipes(List<Recipe> recipes){
    List<Recipe> sortedList = new ArrayList<>();
    sortedList.addAll(recipes);
    sortedList.sort((o1, o2) -> {
            LocalDate o1BestBefore = getMinBestBefore(o1.getIngredients()).get();
            LocalDate o2BestBefore = getMinBestBefore(o2.getIngredients()).get();
            return o1BestBefore.compareTo(o2BestBefore);
        });
    return sortedList;
}

private Optional<LocalDate> getMinBestBefore(Set<Ingredient> ingredients) {
    return ingredients
            .stream()
            .map(Ingredient::getBestBefore)
            .filter(Objects::nonNull)
            .min(LocalDate::compareTo);

}

}
