package View;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import Exceptions.DuplicatedValues;
import Exceptions.WrongFieldException;
import Model.Section;
import Model.Zoo;
import Model.ZooEmployee;
import Utils.Gender;
import Utils.Job;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;


public class addEmployee {
	
	@FXML
    private Button btnAddEmp;
	@FXML
	private TextField txtFName;
	@FXML
	private TextField txtLName;
	@FXML
	private ComboBox comboBoxJob;
	@FXML
	private ComboBox comboBoxSec;
	@FXML
	private DatePicker dp;
	@FXML
    ToggleGroup gender; // will save the gender chosen from radio button 
	@FXML
	private MenuBar menuId;
	@FXML
	private MenuBar menuId2;
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
		Stage primaryStage = (Stage) btnAddEmp.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	@FXML
	public void moveShow(ActionEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/showAll.fxml"));
		Stage primaryStage = (Stage) btnAddEmp.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	@FXML
	public void moveAction(ActionEvent event) throws Exception
	{
		String add2="";
		add2 = (((MenuItem)event.getSource()).getText());
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/"+add2+".fxml"));
		Stage primaryStage = (Stage) btnAddEmp.getScene().getWindow();
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
		comboBoxJob.setItems(FXCollections.observableArrayList(Job.values()));
		comboBoxJob.getSelectionModel().select(0);
		if(Zoo.getInstance().getSaveMyUser().getId() == 0) //is admin
		{
			comboBoxSec.setItems(FXCollections.observableArrayList(Zoo.getInstance().getSections().values()));
		}
		else
		{//is user 
			Section newEmp = Zoo.getInstance().getSaveMyUser().getSection();
			ArrayList<Section> sec =  new ArrayList<Section>();
			sec.add(newEmp);
			comboBoxSec.setItems(FXCollections.observableArrayList(sec));	
		}
		
		comboBoxSec.getSelectionModel().select(0);
		//setting which values are possible for date selecting
		dp.setDayCellFactory(picker -> new DateCell() {
	        public void updateItem(LocalDate date, boolean empty) {
	            super.updateItem(date, empty);
	            LocalDate minDate = LocalDate.of(1920, 1, 1);
	            LocalDate maxDate = LocalDate.of(2018, 1, 1);
	            setDisable(date.compareTo(maxDate)>0 || date.compareTo(minDate) < 0 );
	        }
	    });
		dp.setEditable(false);
	}
	
	
	 @FXML
	 public void addEmployee(MouseEvent event) {
		try 
		{
				if(txtFName.getText().isEmpty() || txtLName.getText().isEmpty() || dp.getValue() == null) //some of the fields is empty
				{
					throw new WrongFieldException();
				}
			
				RadioButton selectedRadioButton = (RadioButton) gender.getSelectedToggle();
				String toogleGroupValue = selectedRadioButton.getText();
				Gender g;
				//getChoose gender
				if(toogleGroupValue.equals("Male"))
				{
					g = Gender.Male;
				}
				else if(toogleGroupValue.equals("Female"))
				{
					g = Gender.Female;
				}
				else 
				{
					g = Gender.Unknown;
				}
				
				Section s=  (Section) comboBoxSec.getValue();

				ZooEmployee newEmp = new ZooEmployee(txtFName.getText(), txtLName.getText(), dp.getValue(), g, s,(Job) comboBoxJob.getValue());
				if(Zoo.getInstance().isDuplicate(newEmp)) // is already exist (Visitor or Employee)
				{
					throw new DuplicatedValues();
				}
				Boolean added = Zoo.getInstance().addEmployee(newEmp);
				if(added)
				{
					Alert alert = new Alert(AlertType.INFORMATION, "you can see him in: show, Employees.");
					alert.setHeaderText("Done: your employee: "+newEmp.getFirstName()+" "+newEmp.getLastName()+", was added succesfully!");
					alert.setTitle("Add Employee");
					alert.showAndWait();
					Zoo.getInstance().serialize();
				}
				else
				{ // not added
					
					Alert alert = new Alert(AlertType.ERROR, "The employee was not added to employee list.");
					alert.setHeaderText("Failed adding!");
					alert.setTitle("Add Employee");
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
		
			catch(DuplicatedValues e)
			{
				Alert alert = new Alert(AlertType.ERROR, "This person is already exist");
				alert.setHeaderText("Failed adding!");
				alert.setTitle("Add Visitor");
				alert.showAndWait();
			}
			catch (NullPointerException e) 
			{
				Alert alert = new Alert(AlertType.ERROR, "One or more of the fields are empty");
				alert.setHeaderText("Failed adding!");
				alert.setTitle("Add Visitor");
				alert.showAndWait();
			}
			
			catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR, "we have some error");
				alert.setHeaderText("erorr");
				alert.setTitle("error");
				alert.showAndWait();
			}
	    }
	 
	 //this method on click the button clear clear the fields
	 public void clearFields(MouseEvent event) 
	 {
		 try 
		 {
			 txtFName.clear();
			 txtLName.clear();
			 comboBoxJob.getSelectionModel().select(0);
			 comboBoxSec.getSelectionModel().select(0);
			 dp.setValue(null);
			 gender.selectToggle(null);
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
