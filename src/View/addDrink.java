package View;

import java.time.LocalDate;

import Exceptions.NegativeNumberException;
import Exceptions.WrongFieldException;
import Model.Drink;
import Model.Reptile;
import Model.Section;
import Model.SnackBar;
import Model.Zoo;
import Utils.AnimalFood;
import Utils.Gender;
import Utils.Job;
import Utils.SnackType;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class addDrink 
{

	@FXML
	private TextField txtName; //name of the Drink
	@FXML
	private ComboBox comboBoxSnackType; 
	@FXML
	private ComboBox comboBoxSnackBar; 
	@FXML
	private TextField price;
	@FXML
	ToggleGroup fattening; 
	@FXML
	ToggleGroup sprinkle;
	@FXML
	ToggleGroup straw;
	@FXML
	ToggleGroup iceCube; 	
	@FXML
	private MenuBar menuId2;
	@FXML
    private Button btnAddDrink; // button send
	@FXML
	private MenuBar menuId;
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
		Stage primaryStage = (Stage) btnAddDrink.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	@FXML
	public void moveShow(ActionEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/showAll.fxml"));
		Stage primaryStage = (Stage) btnAddDrink.getScene().getWindow();
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
		comboBoxSnackType.getSelectionModel().select(1);
		comboBoxSnackBar.setItems(FXCollections.observableArrayList(Zoo.getInstance().getBars().values()));
		comboBoxSnackBar.getSelectionModel().select(0);
		shekelIcon.setVisible(true);
	}
	
	@FXML
    void addDrink(MouseEvent event) 
	{
		try {
			//getting the boolean seleted fields
			Boolean isFattening;
			Boolean isSprinkle;
			Boolean isStraw;
			Boolean isIceCube;
			
			if(txtName.getText().isEmpty()) // we dont have name
			{
				throw new WrongFieldException();
			}
			
			RadioButton selectedFat = (RadioButton) fattening.getSelectedToggle();
			if(selectedFat.getText().equals("Yes"))
				isFattening = true;
			else
				isFattening = false;
			
			RadioButton SelectedSprinkle = (RadioButton) sprinkle.getSelectedToggle();
			if(SelectedSprinkle.getText().equals("Yes"))	
				isSprinkle = true;	
			else
				isSprinkle = false;
			
			RadioButton SelectedStraw = (RadioButton) straw.getSelectedToggle();
			if(SelectedStraw.getText().equals("Yes"))	
				isStraw = true;	
			else
				isStraw = false;
			
			RadioButton SelectedIceCube = (RadioButton) iceCube.getSelectedToggle();
			if(SelectedIceCube.getText().equals("Yes"))	
				isIceCube = true;	
			else
				isIceCube = false;
			//price
			double myPrice = Double.parseDouble(price.getText());
			
			//is valid price?
			if(myPrice<=0.0)
			{
				throw new NegativeNumberException();
			}
			
			SnackBar s = (SnackBar) comboBoxSnackBar.getValue();
		

			Drink newDrink = new Drink((SnackType) comboBoxSnackType.getValue(), txtName.getText(),s, isFattening, myPrice , isSprinkle,isStraw,isIceCube);
			//adding to Zoo
			Boolean flag=Zoo.getInstance().addSnack(newDrink);
			if(flag)
			{
				Alert alert = new Alert(AlertType.INFORMATION, "you can see him: show, Drink.");
				alert.setHeaderText("Done: your drink: "+newDrink.getSnackName()+", was added succesfully!");
				alert.setTitle("Add Drink");
				alert.showAndWait();
				Zoo.getInstance().serialize();
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR, "The drink was not added to employee list.");
				alert.setHeaderText("Failed adding!");
				alert.setTitle("Add Drink");
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
				alert.setHeaderText("Price Should Be more than 0.0");
				alert.setTitle("Error Price Alert");
				alert.showAndWait();
	
			}
			catch(NumberFormatException e)
			{
				Alert alert = new Alert(AlertType.ERROR, "Price Should be a number!");
				alert.setHeaderText("Value not excepted");
				alert.setTitle("Failed adding");
				alert.showAndWait();
			}
			catch (NullPointerException e) 
			{
				Alert alert = new Alert(AlertType.ERROR, "One or more of fields are empty");
				alert.setHeaderText("Failed adding!");
				alert.setTitle("Add Drink");
				alert.showAndWait();
			}

			
			catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR, "we have some error adding drink");
				alert.setHeaderText("error");
				alert.setTitle("error");
				alert.showAndWait();
			}
	    }
	
	//clear method
	 public void clearFields(MouseEvent event) 
	 {
		 try {
			 txtName.clear();
			 comboBoxSnackType.getSelectionModel().clearSelection();
			 comboBoxSnackBar.getSelectionModel().clearSelection();
			 price.clear();
			 fattening.selectToggle(null);
			 sprinkle.selectToggle(null);
			 straw.selectToggle(null);
			 iceCube.selectToggle(null);
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
