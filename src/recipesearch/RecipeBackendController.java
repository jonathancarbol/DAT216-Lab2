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
    }

    public void setMainIngredient(String mainIngredient){
        searchFilter.setMainIngredient(mainIngredient);
    }

    public void setDifficulty(String difficulty){
        searchFilter.setDifficulty(difficulty);
    }

    public void setMaxPrice(int maxPrice){
        searchFilter.setMaxPrice(maxPrice);
    }

    public void setMaxTime(int maxTime){
        searchFilter.setMaxTime(maxTime);
    }
}
