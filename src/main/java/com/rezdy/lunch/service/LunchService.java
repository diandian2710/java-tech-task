package com.rezdy.lunch.service;

import com.rezdy.lunch.entity.Recipe;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LunchService {
    public List<Recipe> getRecipesOnDate(LocalDate date);
    public List<Recipe> getRecipes();
    public List<Recipe> getRecipesOnBestBeforeAndUseBy(LocalDate date);
    public Optional<Recipe> getRecipeByTitle(String title);
    public List<Recipe> getRecipesExcludedIngredients(List<String> excludedIngredients);
}
