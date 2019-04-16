
package recipesearch;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Callback;
import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;
import se.chalmers.ait.dat215.lab2.SearchFilter;

import static java.sql.DriverManager.println;


public class RecipeSearchController implements Initializable {

    RecipeDatabase db = RecipeDatabase.getSharedInstance();
    RecipeBackendController rbc = new RecipeBackendController();
    private List<Recipe> recipes;
    private Map<String, RecipeListItem> recipeListItemMap = new HashMap<String, RecipeListItem>();

    @FXML FlowPane resultFlowPane;
    @FXML ComboBox mainIngredientList;
    @FXML ComboBox kitchenList;
    @FXML RadioButton difficultyAll;
    @FXML RadioButton difficultyEasy;
    @FXML RadioButton difficultyMedium;
    @FXML RadioButton difficultyHard;
    @FXML Spinner maxPriceSpinner;
    @FXML Slider maxTimeSlider;
    @FXML Label minuteLabel;
    @FXML Button closeDetailed;
    @FXML Label detailedName;
    @FXML ImageView detailedImage;
    @FXML AnchorPane detailedView;
    @FXML SplitPane searchView;

    private void updateRecipeList(){
        resultFlowPane.getChildren().clear();
        recipes = rbc.getRecipe();
        for(Recipe r:recipes) {
            resultFlowPane.getChildren().add(recipeListItemMap.get(r.getName()));
        }

    }

    private void populateRecipeDetailView(Recipe recipe){
        detailedName.setText(recipe.getName());
        detailedImage.setImage(recipe.getFXImage());
    }

    @FXML
    public void closeRecipeView(){
        searchView.toFront();
    }

    public void openRecipeView(Recipe recipe){
        populateRecipeDetailView(recipe);
        detailedView.toFront();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for(Recipe r : rbc.getRecipe()){
            RecipeListItem rli = new RecipeListItem(r,this);
            recipeListItemMap.put(r.getName(),rli);
        }
        updateRecipeList();
        mainIngredientList.getItems().addAll("Visa alla","Kött","Fisk","Kyckling","Vegetarisk");
        mainIngredientList.getSelectionModel().select("Visa alla");
        mainIngredientList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                rbc.setMainIngredient(newValue);
                updateRecipeList();
            }
        });

        kitchenList.getItems().addAll("Visa alla","Sverige","Grekland","Indien","Asien","Afrika","Frankrike");
        kitchenList.getSelectionModel().select("Visa alla");
        kitchenList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                rbc.setCuisine(newValue);
                updateRecipeList();
            }
        });

        ToggleGroup difficultyToggleGroup = new ToggleGroup();
        difficultyAll.setToggleGroup(difficultyToggleGroup);
        difficultyEasy.setToggleGroup(difficultyToggleGroup);
        difficultyMedium.setToggleGroup(difficultyToggleGroup);
        difficultyHard.setToggleGroup(difficultyToggleGroup);
        difficultyAll.setSelected(true);
        difficultyToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(difficultyToggleGroup.getSelectedToggle() != null){
                    RadioButton selected = (RadioButton) difficultyToggleGroup.getSelectedToggle();
                    rbc.setDifficulty(selected.getText());
                    updateRecipeList();
                }
            }
        });

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,300,100,10);
        maxPriceSpinner.setEditable(true);
        maxPriceSpinner.setValueFactory(valueFactory);
        maxPriceSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                rbc.setMaxPrice(newValue);
                updateRecipeList();
            }
        });
        maxPriceSpinner.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if(newValue){

                }else{
                    Integer value = Integer.valueOf(maxPriceSpinner.getEditor().getText());
                    rbc.setMaxPrice(value);
                    updateRecipeList();
                }

            }
        });

        minuteLabel.setText("60");
        maxTimeSlider.setValue(60);
        maxTimeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue != null && !newValue.equals(oldValue) ) { //&& !maxTimeSlider.isValueChanging() om man inte vill att den ska uppdatera medans man rör sig men ger problem.
                    rbc.setMaxTime(newValue.intValue());
                    updateRecipeList();
                }
                minuteLabel.setText(Integer.toString((int)Math.floor(maxTimeSlider.getValue())));
            }
        });
        populateMainIngredientComboBox();
        Platform.runLater(()->mainIngredientList.requestFocus());
    }
    private void populateMainIngredientComboBox() {
        Callback<ListView<String>, ListCell<String>> cellFactory = new Callback<ListView<String>, ListCell<String>>() {

            @Override
            public ListCell<String> call(ListView<String> p) {

                return new ListCell<String>() {

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        setText(item);

                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            Image icon = null;
                            String iconPath;
                            try {
                                switch (item) {

                                    case "Kött":
                                        iconPath = "RecipeSearch/resources/icon_main_meat.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                    case "Fisk":
                                        iconPath = "RecipeSearch/resources/icon_main_fish.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                    case "Kyckling":
                                        iconPath = "RecipeSearch/resources/icon_main_chicken.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                    case "Vegetarisk":
                                        iconPath = "RecipeSearch/resources/icon_main_veg.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                }
                            } catch (NullPointerException ex) {
                                //This should never happen in this lab but could load a default image in case of a NullPointer
                            }
                            ImageView iconImageView = new ImageView(icon);
                            iconImageView.setFitHeight(12);
                            iconImageView.setPreserveRatio(true);
                            setGraphic(iconImageView);
                        }
                    }
                };
            }
        };
        mainIngredientList.setButtonCell(cellFactory.call(null));
        mainIngredientList.setCellFactory(cellFactory);
    }
    public Image getCuisineImage(String cuisine) {
        String iconPath = "RecipeSearch/resources/icon_flag_sweden.png";
        switch (cuisine) {
            case "Sverige":
                iconPath = "RecipeSearch/resources/icon_flag_sweden.png";
                break;
            case "Grekland":
                iconPath = "RecipeSearch/resources/icon_flag_greece.png";
                break;
            case "Indien":
                iconPath = "RecipeSearch/resources/icon_flag_india.png";
                break;
            case "Asien":
                iconPath = "RecipeSearch/resources/icon_flag_asia.png";
                break;
            case "Afrika":
                iconPath = "RecipeSearch/resources/icon_flag_africa.png";
                break;
            case "Frankrike":
                iconPath = "RecipeSearch/resources/icon_flag_france.png";
                break;
        }
        return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
    }

    public Image getDifficultyImage(String difficulty){
        String iconPath = "RecipeSearch/resources/icon_difficulty_easy";
        switch (difficulty) {
            case "Lätt":
                iconPath = "RecipeSearch/resources/icon_difficulty_easy";
                break;
            case "Mellan":
                iconPath = "RecipeSearch/resources/icon_difficulty_medium";
                break;
            case "Svår":
                iconPath = "RecipeSearch/resources/icon_difficulty_hard";
                break;
        }
        return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
    }

    public Image getMainIngredientImage(String ingredient) {
        String iconPath = "RecipeSearch/resources/icon_main_meat";
        switch (ingredient) {
            case "Kött":
                iconPath = "RecipeSearch/resources/icon_main_meat";
                break;
            case "Fisk":
                iconPath = "RecipeSearch/resources/icon_main_fish";
                break;
            case "Kyckling":
                iconPath = "RecipeSearch/resources/icon_main_chicken";
                break;
            case "Vegetarisk":
                iconPath = "RecipeSearch/resources/icon_main_veg";
                break;
        }
        return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
    }

    
    public Image getSquareImage(Image image){

        int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;

        if(image.getWidth() > image.getHeight()){
            width = (int) image.getHeight();
            height = (int) image.getHeight();
            x = (int)(image.getWidth() - width)/2;
            y = 0;
        }

        else if(image.getHeight() > image.getWidth()){
            width = (int) image.getWidth();
            height = (int) image.getWidth();
            x = 0;
            y = (int) (image.getHeight() - height)/2;
        }

        else{
            //Width equals Height, return original image
            return image;
        }
        return new WritableImage(image.getPixelReader(), x, y, width, height);
    }
}