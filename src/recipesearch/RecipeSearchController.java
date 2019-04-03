
package recipesearch;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;
import se.chalmers.ait.dat215.lab2.SearchFilter;


public class RecipeSearchController implements Initializable {

    RecipeDatabase db = RecipeDatabase.getSharedInstance();
    RecipeBackendController rbc = new RecipeBackendController();
    private List<Recipe> recipes;

    @FXML FlowPane resultFlowPane;



    private void updateRecipeList(){
        resultFlowPane.getChildren().clear();
        recipes = rbc.getRecipe();
        for(Recipe r:recipes) {
            resultFlowPane.getChildren().add(new RecipeListItem(r, this));
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateRecipeList();
    }

}