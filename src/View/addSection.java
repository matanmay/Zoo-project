package View;

import java.io.IOException;

import Exceptions.NegativeNumberException;
import Exceptions.WrongFieldException;
import Model.Reptile;
import Model.Section;
import Model.Zoo;
import Utils.AnimalFood;
import Utils.Gender;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class addSection 
{
	//Fields over order
	
	@FXML
	private TextField txtName; //name of the Section
	
	@FXML
	private TextField maxCap; //max capitaciy (Integer)
	@FXML
	private MenuBar menuId;
	@FXML
	private MenuBar menuId2;
	@FXML
	private Button btnAddSection;
	@FXML
	private Button clear;
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
		Stage primaryStage = (Stage) btnAddSection.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	@FXML
	public void moveShow(ActionEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/showAll.fxml"));
		Stage primaryStage = (Stage) btnAddSection.getScene().getWindow();
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
	}
	
	
	@FXML
    public void addSection(MouseEvent event)
	{
		try 
		{
			int max = Integer.parseInt(maxCap.getText());
			if(max<=0)
			{
				throw new NegativeNumberException();
			}
			if(txtName.getText().isEmpty() || maxCap.getText().isEmpty())
			{
				throw new WrongFieldException();
			}
			Section s = new Section(txtName.getText(), max);
			Boolean flag = Zoo.getInstance().addSection(s);
			
			if(flag) 
			{
				Alert alert = new Alert(AlertType.INFORMATION, "you can watch it in: show, section.");
				alert.setHeaderText("Done: your section: "+s.getSectionName()+", was added succesfully!");
				alert.setTitle("Add Section");
				alert.showAndWait();
				Zoo.getInstance().serialize();
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR, "The section was not added to section list.");
				alert.setHeaderText("Failed adding!");
				alert.setTitle("Add Section");
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
			Alert alert = new Alert(AlertType.ERROR,e.getMessage());
			alert.setHeaderText("Failed adding!");
			alert.setTitle("Failed adding");
			alert.showAndWait();
			
		}
		
		catch (NumberFormatException e)
		{
			Alert alert = new Alert(AlertType.ERROR, "Max capacity should be a number!");
			alert.setHeaderText("Failed adding!");
			alert.setTitle("Failed adding");
			alert.showAndWait();
		}
		
		
		catch(Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR, "The section was not added to section list.");
			alert.setHeaderText("Failed adding!");
			alert.setTitle("Add Section");
			alert.showAndWait();
		}
			
	}
	
	//this method clear the text field
	 public void clearFields(MouseEvent event) 
	 {
		 txtName.clear();
		 maxCap.clear(); 
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
