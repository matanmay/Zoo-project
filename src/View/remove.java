package View;

import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.HashSet;

import Model.*;
import Model.Animal;
import Model.Bird;
import Model.Section;
import Model.Zoo;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class remove 
{
	//for menuBar // 
	@FXML
	private MenuBar menuId; // for admin
	@FXML
	private MenuBar menuId2; // for others
	//for remove//
	@FXML
	private ComboBox ComboBoxClass;
	@FXML
	private ComboBox comboBoxObject;
	@FXML
	private Button btnRemove;
	@FXML
	private Label lblObj;
	@FXML
	private Button buttonChooseClass;
	
	//for section only // 
	@FXML
	private ComboBox comboBoxNewSection;
	@FXML
	private Label lblNewSection;
	@FXML
	private ImageView logOutIcon;
	@FXML
	private ImageView homeIcon;
	
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
		
		//shows if user take the option
		comboBoxObject.setVisible(false);
		btnRemove.setVisible(false);
		lblObj.setVisible(false);
		buttonChooseClass.setVisible(true);
		
		//because they need only for remove section
		comboBoxNewSection.setVisible(false);
		lblNewSection.setVisible(false);
		
		//show for choose class
		ArrayList<String>arr = new ArrayList<String>();
		if(Zoo.getInstance().getSaveMyUser().getId()==0)
		{
		arr.add("Sections"); arr.add("Birds"); arr.add("Mammals"); arr.add("Reptiles"); arr.add("SnackBars"); arr.add("Visitors");arr.add("Employees");
		arr.add("Snacks");
		}
		else  // if the user is not admin, he can delete only objects from his section, and those are the objects:
		{
			arr.add("Visitors");arr.add("Employees"); arr.add("Snacks");
		}
		ComboBoxClass.setItems(FXCollections.observableArrayList(arr));
		ComboBoxClass.getSelectionModel().select(0);
	}
	
	//on press choose class
	public void showClassList(MouseEvent event)
	{
		//shows if user take the option
		Boolean flag = false;
		
		try 
		{
			//checking whcih field was chosen and deciding what to show in the next comboBox
			if(ComboBoxClass.getValue().equals("Sections")) 
			{
				comboBoxObject.setItems(FXCollections.observableArrayList(Zoo.getInstance().getSections().values()));
				//comboBoxNewSection.setItems(FXCollections.observableArrayList(Zoo.getInstance().getSections().values()));
				//flag = true;
			}
			
			if(ComboBoxClass.getValue().equals("Birds")) 
			{
				comboBoxObject.setItems(FXCollections.observableArrayList(Zoo.getInstance().getBirds().values()));
			}
			
			if(ComboBoxClass.getValue().equals("Mammals"))
			{
				comboBoxObject.setItems(FXCollections.observableArrayList(Zoo.getInstance().getMammals().values()));
			}
				
			
			if(ComboBoxClass.getValue().equals("Reptiles"))
			{
				comboBoxObject.setItems(FXCollections.observableArrayList(Zoo.getInstance().getReptiles().values()));
			}
				
			
			if(ComboBoxClass.getValue().equals("SnackBars"))
			{
				comboBoxObject.setItems(FXCollections.observableArrayList(Zoo.getInstance().getBars().values()));
			}
				
			
			if(ComboBoxClass.getValue().equals("Visitors"))
			{
				comboBoxObject.setItems(FXCollections.observableArrayList(Zoo.getInstance().getVisitors().values()));
				
			}
				
			
			if(ComboBoxClass.getValue().equals("Employees"))
			{
				if(Zoo.getInstance().getSaveMyUser().getId()==0) //admin
					comboBoxObject.setItems(FXCollections.observableArrayList(Zoo.getInstance().getEmployees().values()));
				else
				{//user
					HashSet<ZooEmployee> arr = Zoo.getInstance().getSaveMyUser().getSection().getEmployees();
					//because we want array with no can remove this user
					ArrayList<ZooEmployee> brr = new ArrayList<>();
					brr.addAll(arr);
					brr.remove(Zoo.getInstance().getSaveMyUser());
					comboBoxObject.setItems(FXCollections.observableArrayList(brr));
								
				}
				
			}
				
			
			if(  ComboBoxClass.getValue().equals("Snacks") || ComboBoxClass.getValue().equals("Drink") || ComboBoxClass.getValue().equals("Food") )
			{
				comboBoxObject.setItems(FXCollections.observableArrayList(Zoo.getInstance().getSnacks().values()));
			}
				
			
			//for section only
			comboBoxNewSection.setVisible(flag);
			lblNewSection.setVisible(flag);
			
			//for all
			comboBoxObject.setVisible(true);
			btnRemove.setVisible(true);
			lblObj.setVisible(true);
		}
		
		catch(Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR, "Something went wrong");
			alert.setHeaderText("Remove Failed!");
			alert.setTitle("Remove");
			alert.showAndWait();
		}
		
	}
	
	//for menu bar//
	@FXML
	public void movePage(ActionEvent event) throws Exception
	{
		String add = "add";
		add+=(((MenuItem)event.getSource()).getText());
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/"+add+".fxml"));
		Stage primaryStage = (Stage) menuId.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	@FXML
	public void moveShow(ActionEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/showAll.fxml"));
		Stage primaryStage = (Stage) menuId.getScene().getWindow();
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
	
	//this method we need to validate that user cannot chooseToRemove and move stuff to the same Section
	public void chooseSection(ActionEvent event)
	{
		if(ComboBoxClass.getValue().equals("Sections")) 
		{
			ArrayList<Section> canMove = new ArrayList<Section>();
			canMove.addAll(Zoo.getInstance().getSections().values());
			Section wantToDelete = (Section)comboBoxObject.getValue();
			if(wantToDelete !=null)
				canMove.remove(wantToDelete);
			comboBoxNewSection.setItems(FXCollections.observableArrayList(canMove));
			comboBoxNewSection.setVisible(true);
			lblNewSection.setVisible(true);
		}
	}
	
	//on press remove the chosen item
	public void removeFromClass(MouseEvent event)
	{
		try {
			Boolean flag = false; 
			if(ComboBoxClass.getValue().equals("Sections")) 
			{// we need new Section
				Section oldSection = (Section)(comboBoxObject.getValue());
				Section newSection = (Section)(comboBoxNewSection.getValue());
				if(!oldSection.equals(newSection))
					flag = Zoo.getInstance().removeSection(oldSection, newSection);
				else
					flag = false;
			}
			
			if(ComboBoxClass.getValue().equals("Birds")) 
			{
				Bird b = (Bird) comboBoxObject.getValue();
				flag = Zoo.getInstance().removeBird(b);
			}
			
			if(ComboBoxClass.getValue().equals("Mammals"))
			{
				Mammal b = (Mammal) comboBoxObject.getValue();
				flag = Zoo.getInstance().removeMammal(b);
			}
				
			
			if(ComboBoxClass.getValue().equals("Reptiles"))
			{
				Reptile r = (Reptile) comboBoxObject.getValue();
				flag = Zoo.getInstance().removeReptile(r);
			}
				
			
			if(ComboBoxClass.getValue().equals("SnackBars"))
			{
				SnackBar sb = (SnackBar) comboBoxObject.getValue();
				flag = Zoo.getInstance().removeSnackBar(sb);
			}
				
			
			if(ComboBoxClass.getValue().equals("Visitors"))
			{
				Visitor v = (Visitor) comboBoxObject.getValue();
				flag = Zoo.getInstance().removeVisitor(v);
				
			}
				
			
			if(ComboBoxClass.getValue().equals("Employees"))
			{
				ZooEmployee e = (ZooEmployee) comboBoxObject.getValue();
				flag = Zoo.getInstance().removeEmployee(e);
			}
				
			
			if(ComboBoxClass.getValue().equals("Snacks") || ComboBoxClass.getValue().equals("Food") || ComboBoxClass.getValue().equals("Drink"))
			{
				Snack d = (Snack) comboBoxObject.getValue();
				flag = Zoo.getInstance().removeSnack(d);
			}
				
			
			if(flag)
			{
				sucseedAlert();
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR, "We cannot do this remove\nPlease choose something else");
				alert.setHeaderText("Delete Failed!");
				alert.setTitle("Delete Alert");
				alert.showAndWait();
			}
		}
		catch(Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR, "Something Wrong");
			alert.setHeaderText("Delete Failed!");
			alert.setTitle("Delete Alert");
			alert.showAndWait();
		}
			
		
	}
	
	 private void sucseedAlert()
	 {
			Alert alert = new Alert(AlertType.INFORMATION, "you can see the remove in show");
			alert.setHeaderText("Succesfully Removed!");
			alert.setTitle("Remove");
			alert.showAndWait();
			Zoo.getInstance().serialize();
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