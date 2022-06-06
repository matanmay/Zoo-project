package View;

import Exceptions.WrongFieldException;
import Model.Mammal;
import Model.Section;
import Model.SnackBar;
import Model.Zoo;
import Utils.AnimalFood;
import Utils.Gender;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class addSnackBar 
{
	//Fields over order
	@FXML
	private TextField txtName; //name of the animal

	@FXML
	private ComboBox section; 

	@FXML
	private MenuBar menuId;
	@FXML
	private MenuBar menuId2;
	
	@FXML
	private Button btnAddBar;
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
		Stage primaryStage = (Stage) btnAddBar.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	@FXML
	public void moveShow(ActionEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/showAll.fxml"));
		Stage primaryStage = (Stage) btnAddBar.getScene().getWindow();
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
		//for menuBar if admin or user
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
		section.setItems(FXCollections.observableArrayList(Zoo.getInstance().getSections().values()));
		section.getSelectionModel().select(0);
	}
	
	
	@FXML
    void addSnackBar(MouseEvent event) 
	{
		try 
		{
			Section s = (Section) section.getValue();
			if(txtName.getText().isEmpty() || s == null ) //some of the fields is empty
			{
				throw new WrongFieldException();
			}
		
			SnackBar newBar = new SnackBar(txtName.getText(), s);
			Boolean flag = Zoo.getInstance().addSnackBar(newBar, s);
			if(flag)
			{
				Alert alert = new Alert(AlertType.INFORMATION, "you can see it in: show, SnackBars.");
				alert.setHeaderText("Done: SnackBar: "+newBar.getBarName()+", was added succesfully!");
				alert.setTitle("Add Bar");
				alert.showAndWait();
				Zoo.getInstance().serialize();
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR, "The SnackBar was not added to bars list.");
				alert.setHeaderText("Failed adding!");
				alert.setTitle("Add Bar");
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
		catch (Exception e) 
		{
			Alert alert = new Alert(AlertType.ERROR, "we have some error");
			alert.setHeaderText("erorr");
			alert.setTitle("error");
			alert.showAndWait();
		}
	}
	
	//clear method
	 public void clearFields(MouseEvent event) 
	 {
		 try 
		 {
			 txtName.clear();
			 section.getSelectionModel().select(0);
		 }
		 catch(Exception e)
		 {
			 Alert alert = new Alert(AlertType.ERROR, "we have some error");
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
