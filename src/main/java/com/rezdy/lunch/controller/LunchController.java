package com.rezdy.lunch.controller;

import com.rezdy.lunch.service.LunchServiceImpl;
import com.rezdy.lunch.entity.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.rezdy.lunch.result.*;


@RestController
public class LunchController {

    private LunchServiceImpl lunchService;

    @Autowired
    public LunchController(LunchServiceImpl lunchService) {
        this.lunchService = lunchService;
    }

    @GetMapping("/lunch/")
    public Result getRecipesByDate(@RequestParam(value = "date") String date){
        ArrayList<Object> result = new ArrayList<>();
        List<Recipe> recipes = lunchService.getRecipes();
        List<Recipe> recipesOnDate = lunchService.getRecipesOnDate(LocalDate.parse(date));
        List<Recipe> recipesOnBestBeforeAndUseBy = lunchService.getRecipesOnBestBeforeAndUseBy(LocalDate.parse(date));
        result.add("list all recipes");
        result.add(recipes);
        result.add("list all recipes before useBy date");
        result.add(recipesOnDate);
        result.add("list all recipes before useBy and after bestBefore");
        result.add(recipesOnBestBeforeAndUseBy);

        return ResultFactory.buildResult(200,"success",result);
    }

    @GetMapping("/recipe/{title}")
    public Result getRecipesByTitle(@PathVariable(value = "title") String title){
        Optional<Recipe> recipeByTitle = lunchService.getRecipeByTitle(title);
        if (recipeByTitle.isEmpty()){
            return ResultFactory.buildNotFoundResult("not find by title");
        }
        return ResultFactory.buildSuccessResult(recipeByTitle);
    }

    @GetMapping("/recipe/excluded")
    public Result getRecipesByExcludedIngredients(@RequestParam(value = "excludedIngredients") List<String > excludedIngredients){
        List<Recipe> recipesExcludedIngredients = lunchService.getRecipesExcludedIngredients(excludedIngredients);
        return ResultFactory.buildSuccessResult(recipesExcludedIngredients);
    }
}
