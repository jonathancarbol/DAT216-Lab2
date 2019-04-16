package recipesearch;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.lab2.Recipe;

import java.io.IOException;

public class RecipeListItem extends AnchorPane {
    private RecipeSearchController parentController;
    private Recipe recipe;

    @FXML public Label recipeItemName;      // Hämtas via recipe
    @FXML public ImageView recipeImage;
    @FXML public ImageView listitem_cuisine;

    public RecipeListItem(Recipe recipe, RecipeSearchController recipeSearchController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("recipe_listitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.recipe = recipe;
        this.parentController = recipeSearchController;
        recipeItemName.setText(recipe.getName());
        recipeImage.setImage(recipe.getFXImage());
        listitem_cuisine.setImage(parentController.getCuisineImage(recipe.getCuisine()));
    }

        @FXML
    protected void onClick(Event event){
        parentController.openRecipeView(recipe);
    }
}
