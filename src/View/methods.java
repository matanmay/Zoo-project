package View;

import java.util.ArrayList;

import Model.Animal;
import Model.Section;
import Model.SnackBar;
import Model.Visitor;
import Model.Zoo;
import Model.ZooEmployee;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class methods {
	
	@FXML
	private MenuBar menuId;
	@FXML
	private MenuBar menuId2;
	@FXML
	private ComboBox myComboBox;
	@FXML
	private ListView myList;
	@FXML
	private Label myLbl;
	@FXML
	private ComboBox combox2;
	@FXML
	private ComboBox secCombo;
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
		myList.setVisible(false); 
		myLbl.setVisible(false);
		combox2.setVisible(false);
		ArrayList<String>arr = new ArrayList<String>();
		//what methods are possible to show
		arr.add("Zoo Revenue"); arr.add("All Animals In Section"); arr.add("Animals Not Treated By Worker"); arr.add("Snack By Requierments"); arr.add("Sleeping Reptiles"); arr.add("Discounts By Price");arr.add("Biggest Difference Between Workers & Visitors Amount In A Section");
		myComboBox.setItems(FXCollections.observableArrayList(arr));
		myComboBox.getSelectionModel().select(null);
		secCombo.setVisible(false);;
	}

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

	
	
	@FXML
    void onMethod(ActionEvent event) 
    {
		Boolean flagLbl = false;
		Boolean flagCom = false;
		Boolean flagList= false;
		if(myComboBox.getValue().equals("Zoo Revenue")) {
			myLbl.setText("Zoo revenue is: "+ Zoo.getInstance().checkTotalRevenue());
			flagLbl=true;
		}
		if(myComboBox.getValue().equals("All Animals In Section")) {
			combox2.setItems(FXCollections.observableArrayList(Zoo.getInstance().getSections().values()));
			flagCom=true;
		}
		if(myComboBox.getValue().equals("Animals Not Treated By Worker"))
		{
			flagCom=true;
			combox2.setItems(FXCollections.observableArrayList(Zoo.getInstance().getEmployees().values()));
			
		}
		if(myComboBox.getValue().equals("Snack By Requierments"))
		{
			combox2.setItems(FXCollections.observableArrayList(Zoo.getInstance().getBars().values()));
			flagCom=true;
		}
		if(myComboBox.getValue().equals("Sleeping Reptiles"))
		{
			myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().reptilesSleepAtSeasson()));
			flagList= true;
		}
		if(myComboBox.getValue().equals("Discounts By Price"))
		{
			myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().geAllDiscountAmount().values()));
			flagList= true;
		}
		if(myComboBox.getValue().equals("Biggest Difference Between Workers & Visitors Amount In A Section"))
		{
			myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().getMaxVisitorsVSMaxWorkers()));
			flagList= true;
		}

		
		myList.setVisible(flagList);
		myLbl.setVisible(flagLbl); 
		combox2.setVisible(flagCom);
		if(combox2.isVisible())
		{
			combox2.getSelectionModel().select(null);
		}
		Zoo.getInstance().serialize();
	}
	
	@FXML
    void changeSection(ActionEvent a) 
    {
		try 
		{
			if(combox2.isVisible())
			{
				
				if(myComboBox.getValue().equals("All Animals In Section")) 
				{
					ArrayList<Animal> arr = new ArrayList<Animal>();
					Section s= (Section) combox2.getSelectionModel().getSelectedItem();
					if(Zoo.getInstance().getAllAnimalsBySectionMaxVisits(s)!=null)
						arr.addAll(Zoo.getInstance().getAllAnimalsBySectionMaxVisits(s));
					myList.setItems(FXCollections.observableArrayList(arr));
					combox2.setVisible(false);
				}
				
				else if(myComboBox.getValue().equals("Snack By Requierments"))
				{
					SnackBar sb= (SnackBar) combox2.getSelectionModel().getSelectedItem();
					myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().findAllSnackByWorker(sb)));
					combox2.setVisible(false);
				}
				else if(myComboBox.getValue().equals("Animals Not Treated By Worker"))
				{
					ZooEmployee emp = (ZooEmployee)(combox2.getSelectionModel().getSelectedItem());
					myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().allAnimalsByWorker(emp)));
					combox2.setVisible(false);
				}
	
				myList.setVisible(true);
				Zoo.getInstance().serialize();
			}
			
		}
		
		catch(NullPointerException exc)
		{
			Alert alert = new Alert(AlertType.ERROR, "Something you chose is wrong.\nPlease try again");
			alert.setHeaderText("Error");
			alert.setTitle("Error");
			alert.showAndWait();
		}
		
		catch(Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR, "Sorry we can not do it, please try again later");
			alert.setHeaderText("Error");
			alert.setTitle("Error");
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
