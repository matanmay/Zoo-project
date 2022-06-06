package View;

import java.io.File;
import java.time.LocalDate;

import Exceptions.DiscountCheckException;
import Exceptions.DuplicatedValues;
import Exceptions.WrongFieldException;
import Model.Section;
import Model.Visitor;
import Model.Zoo;
import Model.ZooEmployee;
import Utils.Discount;
import Utils.Gender;
import Utils.Job;
import Utils.TicketType;
import javafx.collections.FXCollections;
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
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class addVisitor 
{
	@FXML
    private Button btnAddVis;
	@FXML
	private TextField txtFName;
	@FXML
	private TextField txtLName;
	@FXML
	private ComboBox ticket;
	@FXML
	private ComboBox discount;
	@FXML
	private DatePicker dp;
	@FXML
	ToggleGroup gender; // will save the gender chose from radio button
	@FXML
	private MenuBar menuId;
	@FXML
	private MenuBar menuId2;
	@FXML
	private RadioButton defGender;
	@FXML
	private ComboBox sectionCombo;
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
		Stage primaryStage = (Stage) btnAddVis.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	@FXML
	public void moveShow(ActionEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/showAll.fxml"));
		Stage primaryStage = (Stage) btnAddVis.getScene().getWindow();
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
		//adding to combox of ticket type and discount
		ticket.setItems(FXCollections.observableArrayList(TicketType.values()));
		ticket.getSelectionModel().select(0);
		discount.setItems(FXCollections.observableArrayList(Discount.values()));
		discount.getSelectionModel().select(6);
		sectionCombo.setItems(FXCollections.observableArrayList(Zoo.getInstance().getSections().values()));
		
		
		
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
	 void addVisitor(MouseEvent event) {
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

			Visitor newVisitor = new Visitor(txtFName.getText(), txtLName.getText(),  dp.getValue(), g,(TicketType) ticket.getValue(),(Discount) discount.getValue());
			//System.out.println(newVisitor);
			if(Zoo.getInstance().isDuplicate(newVisitor)) // if exist employee or visitor with this fields
			{
				throw new DuplicatedValues();
			}
			
			Section s = (Section) sectionCombo.getValue();
			Boolean flag =Zoo.getInstance().newVisitorInZoo(newVisitor, s); // will create new Visitor and do new Visitor in zoo method
			if(flag)
			{
				Alert alert = new Alert(AlertType.INFORMATION, "you can see him in: show, visitors.");
				alert.setHeaderText("Done: visitor: "+newVisitor.getFirstName()+" "+newVisitor.getLastName()+", was added succesfully!");
				alert.setTitle("Add Visitor");
				alert.showAndWait();
				Zoo.getInstance().serialize();
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR, "The visitor was not added to visitors list.");
				alert.setHeaderText("Failed adding!");
				alert.setTitle("Add Visitor");
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
				Alert alert = new Alert(AlertType.ERROR, "This person already exist");
				alert.setHeaderText("Failed adding!");
				alert.setTitle("Add Visitor");
				alert.showAndWait();
			}
		
		
			catch (NullPointerException e) 
			{
				Alert alert = new Alert(AlertType.ERROR, "One or more of fields are empty");
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
	
	 //clear method
	 public void clearFields(MouseEvent event) 
	 {
		 try 
		 {
			 txtFName.clear();
			 txtLName.clear();
			 ticket.getSelectionModel().select(0);
			 discount.getSelectionModel().select(0);
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
