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

    public void setCuisine(String cuisine){
        if(cuisine.equals("Visa alla")){
            cuisine = null;
        }
        searchFilter.setCuisine(cuisine);
    }

    public void setMainIngredient(String mainIngredient){
        if(mainIngredient.equals("Visa alla")){
            mainIngredient = null;
        }
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
