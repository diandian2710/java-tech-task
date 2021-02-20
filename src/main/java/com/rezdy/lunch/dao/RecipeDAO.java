package com.rezdy.lunch.dao;

import com.rezdy.lunch.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecipeDAO extends JpaRepository<Recipe,String> {
    @Query("select distinct r1 from Recipe r1 join fetch r1.ingredients where not r1.title = all(select distinct r2.title from Recipe r2 join r2.ingredients i where :date > i.useBy)")
    List<Recipe> listAllByDate(@Param("date")LocalDate date);

    @Query("select distinct r1 from Recipe r1 join fetch r1.ingredients")
    List<Recipe> listAll();

    @Query("select distinct r1 from Recipe r1 join fetch r1.ingredients where r1.title = all(select distinct r2.title from Recipe r2 join r2.ingredients i where :date < i.useBy and :date > i.bestBefore)")
   List<Recipe> listALLByDateOnBestBeforeAndUseBy(@Param("date")LocalDate date);

    @Query("select distinct r1 from Recipe r1 join fetch r1.ingredients where not r1.title = all (select distinct r2.title from Recipe r2 join r2.ingredients i where i.title in (:excludedIngredients))")
    List<Recipe> listRecipesByExcludedIngredients(@Param("excludedIngredients") List<String> excludedIngredients);
}



