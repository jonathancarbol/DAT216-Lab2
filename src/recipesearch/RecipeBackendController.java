package recipesearch;

import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.SearchFilter;

import java.util.ArrayList;
import java.util.List;

public class RecipeBackendController {

    SearchFilter searchFilter;




    public List<Recipe> getRecipe(){

        return new ArrayList<>();
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
