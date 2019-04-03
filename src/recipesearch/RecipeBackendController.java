package recipesearch;

import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;
import se.chalmers.ait.dat215.lab2.SearchFilter;

import java.util.ArrayList;
import java.util.List;

public class RecipeBackendController {

    SearchFilter searchFilter = new SearchFilter(null,0,null,0,null);
    RecipeDatabase db = RecipeDatabase.getSharedInstance();




    public List<Recipe> getRecipe(){

        return db.search(searchFilter);
    }

    public void setCuisine(String Cuisine){
        searchFilter.setCuisine(Cuisine);
        getRecipe();
    }

    public void setMainIngredient(String mainIngredient){
        searchFilter.setMainIngredient(mainIngredient);
        getRecipe();
    }

    public void setDifficulty(String difficulty){
        searchFilter.setDifficulty(difficulty);
        getRecipe();
    }

    public void setMaxPrice(int maxPrice){
        searchFilter.setMaxPrice(maxPrice);
        getRecipe();
    }

    public void setMaxTime(int maxTime){
        searchFilter.setMaxTime(maxTime);
        getRecipe();
    }
}
