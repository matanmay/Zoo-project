package View;

import java.time.LocalDate;

import Exceptions.NegativeNumberException;
import Exceptions.WrongFieldException;
import Model.Drink;
import Model.Food;
import Model.Reptile;
import Model.Section;
import Model.SnackBar;
import Model.Zoo;
import Utils.AnimalFood;
import Utils.Gender;
import Utils.SnackType;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class addFood {

	@FXML
	private TextField txtName; 
	@FXML
	private ComboBox comboBoxSnackType; 
	@FXML
	private ComboBox comboBoxSnackBar; 
	@FXML
	private TextField price;
	@FXML
	ToggleGroup fattening; 
	@FXML
	ToggleGroup plate; 
	@FXML
	ToggleGroup glutenFree; 
	@FXML
	ToggleGroup spaciy; 	
	@FXML
    private Button btnAddFood; 
	@FXML
	private MenuBar menuId;
	@FXML
	private MenuBar menuId2;
	@FXML
	private ImageView shekelIcon; // will show picture of shekel to the price
	@FXML
	private ImageView logOutIcon;
	@FXML
	private ImageView homeIcon;
	
	@FXML
	public void movePage(ActionEvent event) throws Exception
	{
		String add = "add";
		add+=(((MenuItem)event.getSource()).getText());
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/"+add+".fxml"));
		Stage primaryStage = (Stage) btnAddFood.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	@FXML
	public void moveShow(ActionEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/showAll.fxml"));
		Stage primaryStage = (Stage) btnAddFood.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	@FXML
	public void moveAction(ActionEvent event) throws Exception
	{
		String add=(((MenuItem)event.getSource()).getText());
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/"+add+".fxml"));
		Stage primaryStage = (Stage) menuId.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	@FXML
	public void initialize() 
	{
		//for menu show icons of home and logout and tooltip
		logOutIcon.setVisible(true);
		Tooltip.install(logOutIcon, new Tooltip("LogOut"));	
		homeIcon.setVisible(true);
		Tooltip.install(homeIcon, new Tooltip("Home Page"));
		if(Zoo.getInstance().getSaveMyUser().getId()==0)
		{
			//this is admin
			menuId.setVisible(true);
			menuId2.setVisible(false);
		}
		else
		{
			menuId.setVisible(false);
			menuId2.setVisible(true);
		}
		comboBoxSnackType.setItems(FXCollections.observableArrayList(SnackType.values()));
		comboBoxSnackType.getSelectionModel().select(0);
		comboBoxSnackBar.setItems(FXCollections.observableArrayList(Zoo.getInstance().getBars().values()));
		comboBoxSnackBar.getSelectionModel().select(0);
		shekelIcon.setVisible(true);
	}
	
	@FXML
    void addFood(MouseEvent event) 
	{
		try {
			//getting the boolean selected values
			Boolean isFattening;
			Boolean isPlate;
			Boolean isGlutenFree;
			Boolean isSpicy;
			
			if(txtName.getText().isEmpty()) // if name is empty
			{
				throw new WrongFieldException();
			}
			
			RadioButton selectedFat = (RadioButton) fattening.getSelectedToggle();
			if(selectedFat.getText().equals("Yes"))
				isFattening = true;
			else
				isFattening = false;
			
			RadioButton SelectedPlate = (RadioButton) plate.getSelectedToggle();
			if(SelectedPlate.getText().equals("Yes"))	
				isPlate = true;	
			else
				isPlate = false;
			
			RadioButton SelectedGluten = (RadioButton) glutenFree.getSelectedToggle();
			if(SelectedGluten.getText().equals("Yes"))	
				isGlutenFree = true;	
			else
				isGlutenFree = false;
			
			RadioButton SelectedSpicy = (RadioButton) spaciy.getSelectedToggle();
			if(SelectedSpicy.getText().equals("Yes"))	
				isSpicy = true;	
			else
				isSpicy = false;
			
			//price
			double myPrice = Double.parseDouble(price.getText());
			
			SnackBar s =(SnackBar) comboBoxSnackBar.getValue();

			Food newFood = new Food((SnackType) comboBoxSnackType.getValue(), txtName.getText(),s, isFattening, myPrice , isPlate, isSpicy,isGlutenFree);

			//adding to Zoo
			Boolean flag=Zoo.getInstance().addSnack(newFood);

			if(flag)
			{ 
				Alert alert = new Alert(AlertType.INFORMATION, "you can see him in: show, Food.");
				alert.setHeaderText("Done: food: "+newFood.getSnackName()+", was added succesfully!");
				alert.setTitle("Add Food");
				alert.showAndWait();
				Zoo.getInstance().serialize();
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR, "The food was not added to food list.");
				alert.setHeaderText("Failed adding!");
				alert.setTitle("Add Food");
				alert.showAndWait();
			}
		}
			catch(WrongFieldException e)
			{
				Alert alert = new Alert(AlertType.ERROR,e.getMessage());
				alert.setHeaderText("Failed adding!");
				alert.setTitle("Failed adding");
				alert.showAndWait();
			}
			
			catch(NegativeNumberException e)
			{
				Alert alert = new Alert(AlertType.ERROR, e.getMessage());
				alert.setHeaderText("Price Should Be more then 0.0");
				alert.setTitle("Error Price Alert");
				alert.showAndWait();	
			}
		
			catch(NumberFormatException e)
			{
				Alert alert = new Alert(AlertType.ERROR, "Price Should be number!");
				alert.setHeaderText("Value not excepted");
				alert.setTitle("Failed adding");
				alert.showAndWait();
			}
		
			catch (NullPointerException e) 
			{
				Alert alert = new Alert(AlertType.ERROR, "One or more fields are empty");
				alert.setHeaderText("Failed adding!");
				alert.setTitle("Add Food");
				alert.showAndWait();
			}

			
			catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR, "we have some error");
				alert.setHeaderText("erorr");
				alert.setTitle("error");
				alert.showAndWait();
			}
	    }
	
	
	// clear methods
	 public void clearFields(MouseEvent event) 
	 {
		 try {
			 txtName.clear();
			 comboBoxSnackType.getSelectionModel().clearSelection();
			 comboBoxSnackBar.getSelectionModel().clearSelection();
			 fattening.selectToggle(null);
			 plate.selectToggle(null);
			 glutenFree.selectToggle(null);
			 spaciy.selectToggle(null);
			 price.clear();

		 }
		 catch(Exception e)
		 {
			 Alert alert = new Alert(AlertType.ERROR, "we have some error cleaning");
				alert.setHeaderText("erorr");
				alert.setTitle("error");
				alert.showAndWait();
		 }
		 
	 }
	 	//on click on pic logOut you will do this method to go back to login 
		public void logOut(MouseEvent e) throws Exception
		{
			Parent newRoot = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
			Stage primaryStage = (Stage) menuId.getScene().getWindow();
			primaryStage.getScene().setRoot(newRoot);
			primaryStage.show();
		}
		
		//on click on pic home you will do this method to go back to home page
		public void goToHomePage(MouseEvent e) throws Exception
		{
			Parent newRoot = FXMLLoader.load(getClass().getResource("/View/homePage.fxml"));
			Stage primaryStage = (Stage) menuId.getScene().getWindow();
			primaryStage.getScene().setRoot(newRoot);
			primaryStage.show();
		}
}
