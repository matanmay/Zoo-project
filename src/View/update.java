package View;

import java.io.FileInputStream;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.javafx.scene.control.skin.Utils;
import com.sun.media.sound.ModelAbstractChannelMixer;

import Exceptions.DiscountCheckException;
import Exceptions.DuplicatedValues;
import Exceptions.NegativeNumberException;
import Exceptions.WrongFieldException;
import Model.Animal;
import Model.Bird;
import Model.Drink;
import Model.Food;
import Model.Mammal;
import Model.Reptile;
import Model.Section;
import Model.SnackBar;
import Model.Visitor;
import Model.Zoo;
import Model.ZooEmployee;
import Utils.AnimalFood;
import Utils.Discount;
import Utils.Gender;
import Utils.Job;
import Utils.SnackType;
import Utils.TicketType;
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
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class update {

	@FXML
	private MenuBar menuId;
	@FXML
	private MenuBar menuId2;
	@FXML
	private ComboBox ComboBoxClass;
	@FXML
	private ComboBox comboBoxField;
	@FXML
    private Button buttonUpdateField;
	@FXML
    private Button buttonChooseField;
	@FXML
    private Button buttonChooseClass;
	@FXML
	private ListView myList;
	@FXML
	private TextField textNew;
	@FXML
	private TextField textBoxId;
	@FXML
	private Label myLbl;
	@FXML
	private Label lblId;
	@FXML
	private Label updateLabel;
	@FXML
	private ComboBox updateCombo;
	@FXML
	private DatePicker dp;
	@FXML
	private Tooltip myFirstTip;
	@FXML
	private ImageView img1;
	@FXML
	private HBox hbox;
	@FXML
	private Label tipLabel;
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
		
		//shows if user take the option 
		myList.setVisible(false); myLbl.setVisible(false); textNew.setVisible(false); buttonChooseField.setVisible(false);  buttonUpdateField.setVisible(false); 
		comboBoxField.setVisible(false); lblId.setVisible(false); textBoxId.setVisible(false); updateCombo.setVisible(false);
		dp.setVisible(false); 
		ArrayList<String>arr = new ArrayList<String>();
		arr.add("Sections"); arr.add("Birds"); arr.add("Mammals"); arr.add("Reptiles"); arr.add("SnackBars"); arr.add("Visitors");arr.add("Employees");
		arr.add("Drink");arr.add("Food");
		ComboBoxClass.setItems(FXCollections.observableArrayList(arr));
		ComboBoxClass.getSelectionModel().select(0);

		try {
			Image img= new Image("images/help.png");
			img1 = new ImageView(img);
			img1.setFitHeight(35);
			img1.setFitWidth(35);
			hbox.getChildren().add(img1);
			Tooltip.install(hbox, new Tooltip("choose here which field you want to change, then press choose button"));	
		}
		catch(Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR, "Something went wrong");
			alert.setHeaderText("Remove Failed!");
			alert.setTitle("Remove");
			alert.showAndWait();
		}
		
	}
	  
	@FXML
    void showClassList(MouseEvent event) 
	{
		myLbl.setVisible(true); myLbl.setText(ComboBoxClass.getValue().toString()); buttonChooseField.setVisible(true);
		myList.setVisible(true); lblId.setVisible(true); textBoxId.setVisible(true); comboBoxField.setVisible(true);
		try 
		{
			if(ComboBoxClass.getValue().equals("Sections")) 
			{
				myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().getSections().values()));
				ArrayList<String> arr = new ArrayList<>();
				arr.add("Name");
				comboBoxField.setItems(FXCollections.observableArrayList(arr));
			}
			
			if(ComboBoxClass.getValue().equals("Birds")) 
			{
				myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().getBirds().values()));
				ArrayList<String> arr = Zoo.getInstance().subTwoArrayLists(Zoo.getInstance().getMyClassFieldsNames(Animal.class.getDeclaredFields()), Zoo.getInstance().getMyClassFieldsNames(Model.Bird.class.getDeclaredFields()));
				comboBoxField.setItems(FXCollections.observableArrayList(arr));
				
			}
			
			if(ComboBoxClass.getValue().equals("Mammals"))
			{
				myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().getMammals().values()));
				ArrayList<String> arr = Zoo.getInstance().subTwoArrayLists(Zoo.getInstance().getMyClassFieldsNames(Animal.class.getDeclaredFields()), Zoo.getInstance().getMyClassFieldsNames(Model.Mammal.class.getDeclaredFields()));
				comboBoxField.setItems(FXCollections.observableArrayList(arr));
			}
				
			
			if(ComboBoxClass.getValue().equals("Reptiles"))
			{
				myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().getReptiles().values()));
				ArrayList<String> arr = Zoo.getInstance().subTwoArrayLists(Zoo.getInstance().getMyClassFieldsNames(Animal.class.getDeclaredFields()), Zoo.getInstance().getMyClassFieldsNames(Model.Reptile.class.getDeclaredFields()));
				comboBoxField.setItems(FXCollections.observableArrayList(arr));
			}
				
			
			if(ComboBoxClass.getValue().equals("SnackBars"))
			{
				myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().getBars().values()));
				ArrayList<String> arr = new ArrayList<>();
				arr.addAll(Zoo.getInstance().getMyClassFieldsNames(Model.SnackBar.class.getDeclaredFields()));
				arr.remove("section");
				arr.remove("zooPercentage");
				comboBoxField.setItems(FXCollections.observableArrayList(arr));
			}
				
			
			if(ComboBoxClass.getValue().equals("Visitors"))
			{
				myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().getVisitors().values()));
				ArrayList<String> arr = new ArrayList<>();
				arr.addAll(Zoo.getInstance().subTwoArrayLists(Zoo.getInstance().getMyClassFieldsNames(Model.Person.class.getDeclaredFields()), Zoo.getInstance().getMyClassFieldsNames(Model.Visitor.class.getDeclaredFields())));
				arr.remove("section");
				comboBoxField.setItems(FXCollections.observableArrayList(arr));
				
			}
				
			
			if(ComboBoxClass.getValue().equals("Employees"))
			{
				myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().getEmployees().values()));
				ArrayList<String> arr = Zoo.getInstance().subTwoArrayLists(Zoo.getInstance().getMyClassFieldsNames(Model.Person.class.getDeclaredFields()), Zoo.getInstance().getMyClassFieldsNames(Model.ZooEmployee.class.getDeclaredFields()));
				arr.remove("userName");
				arr.remove("password");
				arr.remove("myStatus");
				comboBoxField.setItems(FXCollections.observableArrayList(arr));
			}
							
			if(ComboBoxClass.getValue().equals("Drink"))
			{
				myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().getDrink()));
				ArrayList<String> arr = Zoo.getInstance().subTwoArrayLists(Zoo.getInstance().getMyClassFieldsNames(Model.Snack.class.getDeclaredFields()), Zoo.getInstance().getMyClassFieldsNames(Model.Drink.class.getDeclaredFields()));
				comboBoxField.setItems(FXCollections.observableArrayList(arr));
			}
							
			if(ComboBoxClass.getValue().equals("Food"))
			{
				myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().getFood()));
				ArrayList<String> arr = Zoo.getInstance().subTwoArrayLists(Zoo.getInstance().getMyClassFieldsNames(Model.Snack.class.getDeclaredFields()), Zoo.getInstance().getMyClassFieldsNames(Model.Food.class.getDeclaredFields()));
				comboBoxField.setItems(FXCollections.observableArrayList(arr));
			}			
		}
			
		catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, "Something went wrong");
			alert.setHeaderText("Remove Failed!");
			alert.setTitle("Remove");
			alert.showAndWait();
		}
	}
	
	@FXML
    void showToUpdate(MouseEvent event) 
	{
		try {
			//Show Text Box //
			
			//Model fields will be in text box
			if(comboBoxField.getValue().equals("name") || comboBoxField.getValue().equals("visitCounter") ||  comboBoxField.getValue().equals("firstName" )||  comboBoxField.getValue().equals("lastName") || 
					 comboBoxField.getValue().equals("Name") ||  comboBoxField.getValue().equals("snackName") ||  comboBoxField.getValue().equals("price")
					 ||  comboBoxField.getValue().equals("barName") ||  comboBoxField.getValue().equals("zooPercentage"))
			{
				textNew.setVisible(true);
				dp.setVisible(false);
				updateCombo.setVisible(false);
			}
		
			//Show CoboBox //
			
			//Model fields will in combox
			if(comboBoxField.getValue().equals("food") )
			{
				updateCombo.setItems(FXCollections.observableArrayList(AnimalFood.values()));
				updateCombo.getSelectionModel().select(0);
				updateCombo.setVisible(true);
				textNew.setVisible(false);
				dp.setVisible(false);
			}
			//Model fields will in combox
			if(comboBoxField.getValue().equals("gender") )
			{
				updateCombo.setItems(FXCollections.observableArrayList(Gender.values()));;
				updateCombo.getSelectionModel().select(0);
				updateCombo.setVisible(true);
				textNew.setVisible(false);
				dp.setVisible(false);
			}
			
			//Model fields will in combox
			if(comboBoxField.getValue().equals("section" ))
			{
				updateCombo.setItems(FXCollections.observableArrayList(Zoo.getInstance().getSections().values()));
				updateCombo.getSelectionModel().select(0);
				updateCombo.setVisible(true);
				textNew.setVisible(false);
				dp.setVisible(false);
			}
			
			//Model fields will in combox
			if(comboBoxField.getValue().equals("bar"))
			{
				updateCombo.setItems(FXCollections.observableArrayList(Zoo.getInstance().getBars().values()));
				updateCombo.getSelectionModel().select(0);
				updateCombo.setVisible(true);
				textNew.setVisible(false);
				dp.setVisible(false);
			}
				
			//Model fields will in combox
			if(comboBoxField.getValue().equals("type"))
			{
				updateCombo.setItems(FXCollections.observableArrayList(SnackType.values()));
				updateCombo.getSelectionModel().select(0);
				updateCombo.setVisible(true);
				textNew.setVisible(false);
				dp.setVisible(false);
			}
			
			//Model fields will in combox
			if(comboBoxField.getValue().equals("ticket"))
			{
				updateCombo.setItems(FXCollections.observableArrayList(TicketType.values()));
				updateCombo.getSelectionModel().select(0);
				updateCombo.setVisible(true);
				textNew.setVisible(false);
				dp.setVisible(false);
			}
			
			//Model fields will in combox
			if(comboBoxField.getValue().equals("discount"))
			{
				updateCombo.setItems(FXCollections.observableArrayList(Discount.values()));
				updateCombo.getSelectionModel().select(0);
				updateCombo.setVisible(true);
				textNew.setVisible(false);
				dp.setVisible(false);
			}
			
			//Model fields will in combox
			if(comboBoxField.getValue().equals("job"))
			{
				updateCombo.setItems(FXCollections.observableArrayList(Job.values()));
				updateCombo.getSelectionModel().select(0);
				updateCombo.setVisible(true);
				textNew.setVisible(false);
				dp.setVisible(false);
			}
			
			
			//Model fields will in Boolean combox
			if(comboBoxField.getValue().equals("canFly" )|| comboBoxField.getValue().equals("takePic" )|| comboBoxField.getValue().equals("sprinkle" )|| comboBoxField.getValue().equals("straw")
					|| comboBoxField.getValue().equals("iceCube" )|| comboBoxField.getValue().equals("plate") || comboBoxField.getValue().equals("spaciy" )|| comboBoxField.getValue().equals("glutenFree")
					|| comboBoxField.getValue().equals("meatEater") || comboBoxField.getValue().equals("canTakePic") || comboBoxField.getValue().equals("isDangerous") || comboBoxField.getValue().equals("seasonSleep")
					|| comboBoxField.getValue().equals("isFattening"))
			{
				ArrayList<Boolean> arr = new ArrayList<>();
				arr.add(true);
				arr.add(false);
				updateCombo.setItems(FXCollections.observableArrayList(arr));
				updateCombo.getSelectionModel().select(0);
				updateCombo.setVisible(true);
				textNew.setVisible(false);
				dp.setVisible(false);
			}
			
			if(comboBoxField.getValue().equals("birthDay"))
			{
				dp.setDayCellFactory(picker -> new DateCell() {
			        public void updateItem(LocalDate date, boolean empty) {
			            super.updateItem(date, empty);
			            LocalDate minDate = LocalDate.of(1920, 1, 1);
			            LocalDate maxDate;
			            //because employee or visitor needs to be atleast 3
			            if(ComboBoxClass.getValue().equals("Employees") || ComboBoxClass.getValue().equals("Visitors"))
			            {
			            	maxDate = LocalDate.of(2018, 1, 1);
			            }
			            else 
			            {
			            	maxDate = LocalDate.now();
			            }
			            
			            setDisable(date.compareTo(maxDate)>0 || date.compareTo(minDate) < 0 );
			        }
			    });
				updateCombo.setVisible(false);
				textNew.setVisible(false);
				dp.setEditable(false);
				dp.setVisible(true);
				
			}			
			buttonUpdateField.setVisible(true);
		}
		catch(NullPointerException e)
		{
			Alert alert = new Alert(AlertType.ERROR, "Please choose or enter value");
			//Alert alert = new Alert(AlertType.ERROR, "we have some error");
			alert.setHeaderText("No Value Error");
			alert.setTitle("No Value Error");
			alert.showAndWait();
		}
		catch(Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR, "we have a problem/nPlease try again later");
			alert.setHeaderText("Some Problem Here");
			alert.setTitle("Some Problem Here");
			alert.showAndWait();
		}

	}
	
	//for menuBar
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
	
	//this method go after choose update and do the update
	 public void updateField(MouseEvent event) 
	 {
		 try 
		 {
			 updateDP(); //This method will update if chose Birthday update
			 updateTextNew(); //if it text updating
			 updateComboBox();  //if comboBox update

		 }
		catch(DuplicatedValues e)
		{
			Alert alert = new Alert(AlertType.ERROR,e.getMessage());
			alert.setHeaderText("Failed update!");
			alert.setTitle("Failed update");
			alert.showAndWait();	
		}
		catch(WrongFieldException e)
		{
				Alert alert = new Alert(AlertType.ERROR,e.getMessage());
				alert.setHeaderText("Update Failed!");
				alert.setTitle("Update");
				alert.showAndWait();
		}
		 catch(NumberFormatException e)
		 {
				Alert alert = new Alert(AlertType.ERROR, "Id needs be number!");
				alert.setHeaderText("Update Failed!");
				alert.setTitle("Update");
				alert.showAndWait();
		 }
		 
		 catch(Exception e)
		 {
				Alert alert = new Alert(AlertType.ERROR, "Something Wrong");
				alert.setHeaderText("Update Failed!");
				alert.setTitle("Update");
				alert.showAndWait();
		 }	 
	 }
	 
	 /*
	  * This method will update if chose Birthday update
	  *////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 private void updateDP() throws WrongFieldException, DuplicatedValues
	 {
		// update birthday
		 if(dp.isVisible()) 
		 {
			 String myClass = myLbl.getText(); //which class we are
			 Integer myId = Integer.parseInt(textBoxId.getText()); // which id
			 if(dp.getValue()==null)
			 {
				 throw new WrongFieldException();
			 }
			 // checking class //
			 if(myClass.equals("Birds"))
			 {
				 Bird b = Zoo.getInstance().getRealBird(myId);
				 if(b!=null)
				 {
					 b.setBirthDay(dp.getValue());
					 sucseedAlert();					 
				 }
			 }
			 if(myClass.equals("Mammals"))
			 {
				 Model.Mammal b = Zoo.getInstance().getRealMammal(myId);
				 if(b!=null)
				 {
					 b.setBirthDay(dp.getValue());
					 sucseedAlert();					 
				 }
			 }
			 if(myClass.equals("Reptiles"))
			 {
				 Model.Reptile b = Zoo.getInstance().getRealReptile(myId);
				 if(b!=null)
				 {
					 b.setBirthDay(dp.getValue());
					 sucseedAlert();				 
				 }
			 }
			 
			 if(myClass.equals("Visitors"))
			 {
				 Model.Visitor v=Zoo.getInstance().getRealVisitor(myId);
				 if(v!=null)
				 {
					 v.setBirthDay(dp.getValue());
					 sucseedAlert();				 
				 }
			 }
			 
			 if(myClass.equals("Employees"))
			 {
				 Model.ZooEmployee v=Zoo.getInstance().getRealEmployee(myId);
				 if(v!=null)
				 {
					 v.setBirthDay(dp.getValue());
					 sucseedAlert();				 
				 }
			 }
			 
		 }
	 }
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
	 //This method will update if somthing text update
	 private void updateTextNew()
	 {
	try {
		 if(textNew.isVisible())
		 {
			 if(textNew.getText().isEmpty())//if we not have a field
			 {
				 throw new WrongFieldException();
			 }
			 String myClass = myLbl.getText(); //which class we are
			 Integer myId = Integer.parseInt(textBoxId.getText().toString());  //id 
			 String myField =comboBoxField.getSelectionModel().getSelectedItem().toString();  //
			/////////////////////////////////////////////////////////////////////////////////
			 if(myClass.equals("Sections"))
			 {
				 Model.Section s = Zoo.getInstance().getRealSection(myId);
				 if(s!=null)
				 {
					 s.setSectionName(textNew.getText().toString()); // because we only have name
					 sucseedAlert();					 
				 }
			 }
			 ///////////////////////////////////////////////////////////////////
			 if(myClass.equals("Birds"))
			 {
				 Bird b = Zoo.getInstance().getRealBird(myId);
				 if(b!=null)
				 {
					 b.setName(textNew.getText().toString()); // because we only have name
					 sucseedAlert();
				 }
			 }
			/////////////////////////////////////////////////////////////////////////////////////
			 if(myClass.equals("Mammals"))
			 {
				 Model.Mammal b = Zoo.getInstance().getRealMammal(myId);
				 if(b!=null)
				 {
					 b.setName(textNew.getText().toString()); // because we only have name
					 sucseedAlert();
				 }
			 }
			 ///////////////////////////////////////////////////////////////////////////////////
			 if(myClass.equals("Reptiles"))
			 {
				 Model.Reptile b = Zoo.getInstance().getRealReptile(myId);
				 if(b!=null)
				 {
					 b.setName(textNew.getText().toString()); // because we only have name
					 sucseedAlert();
				 }
			 }
			 ////////////////////////////////////////////////////////////////////////////
			 if(myClass.equals("Visitors"))  //works
			 {
				 Model.Visitor v = Zoo.getInstance().getRealVisitor(myId);
				 if(v!=null)
				 {
					 if(myField.equals("firstName"))
					 {
						 v.setFirstName(textNew.getText().toString()); 
						 sucseedAlert();
					 }
					 else if(myField.equals("lastName"))
					 {
						 v.setLastName(textNew.getText().toString()); 
						 sucseedAlert();
					 }
				 }
			 }
			 ///////////////////////////////////////////////////////////////////////////////////////////////////////
			 if(myClass.equals("Employees")) //works
			 {
				 Model.ZooEmployee v = Zoo.getInstance().getRealEmployee(myId);
				 if(v!=null)
				 {
					 if(myField.equals("firstName"))
					 {
						 v.setFirstName(textNew.getText().toString()); 
						 sucseedAlert();
					 }
					 else if(myField.equals("lastName"))
					 {
						 v.setLastName(textNew.getText().toString()); 
						 sucseedAlert();
					 }
				 }
			 }
			 ///////////////////////////////////////////////////////////////////////////////////
			 if(myClass.equals("Drink")||myClass.equals("Food"))  //works
			 {
				 try {					 				
					 Model.Snack b = Zoo.getInstance().getRealSnack(myId);
					 if(b!=null)
					 {
						 if(myField.equals("snackName")) {
							 b.setSnackName(textNew.getText().toString()); // because we only have name
							 sucseedAlert();
						 }
						 else if(myField.equals("price"))
						 {
							 b.setPrice(Double.parseDouble(textNew.getText().toString())); // because we only have name
							 sucseedAlert();
						 }
					 }
				 }
				 catch(NegativeNumberException e)
				 {
						Alert alert = new Alert(AlertType.ERROR, e.getMessage());
						alert.setHeaderText("Price Error");
						alert.setTitle("Negative Price");
						alert.showAndWait();
				 }
			 }
			 ////////////////////////////////////////////////////////////////////
			 if(myClass.equals("SnackBars")) //works
			 {
				 Model.SnackBar v = Zoo.getInstance().getRealSnackBar(myId);
				 if(v!=null)
				 {
					 if(myField.equals("barName"))
					 {
						 v.setBarName(textNew.getText().toString());
						 sucseedAlert();
					 }
					 if(myField.equals("zooPercentage"))
					 {
					 	 SnackBar.setZooPercentage(Double.parseDouble(textNew.getText().toString()));
						 sucseedAlert();
					 }
				 }
			 }
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	 		 	 
			 
		 }
	}
	
		catch(DuplicatedValues e)
		{
			Alert alert = new Alert(AlertType.ERROR,e.getMessage());
			alert.setHeaderText("Failed update!");
			alert.setTitle("Failed update");
			alert.showAndWait();	
		}
		catch(WrongFieldException e)
		{
			Alert alert = new Alert(AlertType.ERROR,e.getMessage());
			alert.setHeaderText("Failed update!");
			alert.setTitle("Failed update");
			alert.showAndWait();
		}
	 }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 // if we need update which includes combo box
	 private void updateComboBox() throws DiscountCheckException
	 {
		 if(updateCombo.isVisible()) 
		 {
			 String myClass = myLbl.getText(); //which class we are
			 Integer myId = Integer.parseInt(textBoxId.getText().toString()); //id of object
			 String myField =comboBoxField.getSelectionModel().getSelectedItem().toString();  //what to update
			 ////////////////////////////////////////////////////////////////////////////////////////////////////
			 if(myClass.equals("Mammals"))
			 {
				 Mammal b = Zoo.getInstance().getRealMammal(myId);
				 if(b!=null)
				 {
					 if(myField.equals("food")) {
						b.setFood((AnimalFood)updateCombo.getValue());
						 sucseedAlert();
					 }
					 if(myField.equals("gender")) {
							b.setGender((Gender)updateCombo.getValue());
							 sucseedAlert();
						 }
					 if(myField.equals("section")) 
					 {
						 	Section s = (Section) updateCombo.getValue();
							b.setSection(s);
							sucseedAlert();
						 }
					 if(myField.equals("meatEater")) {
							b.setMeatEater((Boolean)updateCombo.getValue());
							 sucseedAlert();
						 }
					 if(myField.equals("canTakePic")) {
							b.setCanTakePic((Boolean)updateCombo.getValue());
							 sucseedAlert();
						 }
					 }
				 }
			 ///////////////////////////////////////////////////////////////////////////////////////
			 
			 if(myClass.equals("Birds"))
			 {
				 Bird b = Zoo.getInstance().getRealBird(myId);
				 if(b!=null)
				 {
					 if(myField.equals("food")) {
						b.setFood((AnimalFood)updateCombo.getValue());
						 sucseedAlert();
					 }
					 if(myField.equals("gender")) {
							b.setGender((Gender)updateCombo.getValue());
							 sucseedAlert();
						 }
					 if(myField.equals("section")) 
					 {
						 	Section s =(Section) updateCombo.getValue();
							b.setSection(s);
							sucseedAlert();
						 }
					 if(myField.equals("canFly")) {
							b.setCanFly((Boolean)updateCombo.getValue());
							 sucseedAlert();
						 }
					 if(myField.equals("canTakePic")) {
							b.setCanTakePic((Boolean)updateCombo.getValue());
							 sucseedAlert();
						 }
					 }
				 }
			 ///////////////////////////////////////////////////////////////////////////////////
			 if(myClass.equals("Reptiles"))
			 {
				 Reptile b = Zoo.getInstance().getRealReptile(myId);
				 if(b!=null)
				 {
					 if(myField.equals("food")) {
						b.setFood((AnimalFood)updateCombo.getValue());
						 sucseedAlert();
					 }
					 if(myField.equals("gender")) {
							b.setGender((Gender)updateCombo.getValue());
							 sucseedAlert();
						 }
					 if(myField.equals("section")) 
					 {
						 	Section s = (Section) updateCombo.getValue();
							b.setSection(s);
							sucseedAlert();
						 }
					 if(myField.equals("isDangerous")) {
							b.setDangerous((Boolean)updateCombo.getValue());
							 sucseedAlert();
						 }
					 if(myField.equals("seasonSleep")) {
							b.setSeasonSleep((Boolean)updateCombo.getValue());
							 sucseedAlert();
						 }
					 }
				 }
		 //////////////////////////////////////////////////////////////////////////////////////////
			 if(myClass.equals("Drink"))
			 {
				 Drink b = (Drink)Zoo.getInstance().getRealSnack(myId);
				 if(b!=null)
				 {
					 if(myField.equals("isFattening")) {
						b.setFattening((Boolean)updateCombo.getValue());
						 sucseedAlert();
					 }
					 if(myField.equals("type")) {
							b.setType((SnackType)updateCombo.getValue());
							 sucseedAlert();
						 }
					 if(myField.equals("bar")) 
					 {
						 	SnackBar s =(SnackBar)updateCombo.getValue();
						 	b.setBar(s);
							sucseedAlert();
					 }
					 if(myField.equals("sprinkel")) {
							b.setSprinkel((Boolean)updateCombo.getValue());
							 sucseedAlert();
						 }
					 if(myField.equals("straw")) {
							b.setStraw((Boolean)updateCombo.getValue());
							 sucseedAlert();
						 }
					 if(myField.equals("iceCube")) {
							b.setIceCube((Boolean)updateCombo.getValue());
							 sucseedAlert();
						 }
					 }
				 }
			 /////////////////////////////////////////////////////////////////////////////////////////
			 if(myClass.equals("Food"))
			 {
				 Food b = (Food)Zoo.getInstance().getRealSnack(myId);
				 if(b!=null)
				 {
					 if(myField.equals("isFattening")) {
						b.setFattening((Boolean)updateCombo.getValue());
						 sucseedAlert();
					 }
					 if(myField.equals("type")) {
							b.setType((SnackType)updateCombo.getValue());
							 sucseedAlert();
						 }
					 if(myField.equals("bar")) 
					 {
						 	SnackBar s =(SnackBar)updateCombo.getValue();
						 	b.setBar(s);
							sucseedAlert();
					 }
					 if(myField.equals("glutenFree")) {
							b.setGlutenFree((Boolean)updateCombo.getValue());
							 sucseedAlert();
						 }
					 if(myField.equals("plate")) {
							b.setPlate((Boolean)updateCombo.getValue());
							 sucseedAlert();
						 }
					 if(myField.equals("spaciy")) {
							b.setSpaciy((Boolean)updateCombo.getValue());
							 sucseedAlert();
						 }
					 }
				 }
			 /////////////////////////////////////////////////////////////////////////////////
			 if(myClass.equals("SnackBars"))
			 {
				 SnackBar b = Zoo.getInstance().getRealSnackBar(myId);
				 if(b!=null)
				 {
					 if(myField.equals("section")) 
					 {
						 	Section s = (Section) updateCombo.getValue();
							b.setSection(s);
							sucseedAlert();
					 }
				 }
			 }
			 //////////////////////////////////////////////////////////////////////////////////////////
		 
			 if(myClass.equals("Visitors"))
			 {
				 Visitor b = (Visitor)Zoo.getInstance().getRealVisitor(myId);
				 if(b!=null)
				 {
					 if(myField.equals("discount")) {
						b.setDiscount((Discount)updateCombo.getValue());
						 sucseedAlert();
					 }
					 if(myField.equals("ticket")) {
							b.setTicket((TicketType)updateCombo.getValue());
							 sucseedAlert();
						 }
					 if(myField.equals("section")) {
						 	Section s = (Section) updateCombo.getValue();
							b.setSection(s);
							sucseedAlert();
						 }
					 }
				 }
		 //////////////////////////////////////////////////////////////////////////////////////////////
			 if(myClass.equals("Employees"))
			 {
				 ZooEmployee b = (ZooEmployee)Zoo.getInstance().getRealEmployee(myId);
				 if(b!=null)
				 {
					 if(myField.equals("discount")) {
						b.setJob((Job)updateCombo.getValue());
						 sucseedAlert();
					 }
					 if(myField.equals("section")) {
						 	Section s = (Section) updateCombo.getValue();
							b.setSection(s);
							sucseedAlert();
						 }
					 }
				 }
		 }

	 }
	 
	 //this method will show alert that the update is sucseed
	 private void sucseedAlert()
	 {
			Alert alert = new Alert(AlertType.INFORMATION, "you can see the update in show");
			alert.setHeaderText("Succesfully updated!");
			alert.setTitle("Update");
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
