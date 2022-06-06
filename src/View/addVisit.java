package View;

import java.util.ArrayList;

import Exceptions.AnimalsVisitsException;
import Model.AnimalsVisits;
import Model.Person;
import Model.Section;
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
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class addVisit 
{
	//for menuBar //
	@FXML
	private MenuBar menuId;
	@FXML
	private MenuBar menuId2;
	
	@FXML
	private ComboBox comboPersons;
	
	@FXML
	private ComboBox comboAnimal ;
	
	@FXML
	private Button btnVisit;
	
	@FXML
	private Button clear;
	
	@FXML
	private ImageView img1;
	@FXML
	private HBox hbox;
	@FXML
	private ImageView logOutIcon;
	@FXML
	private ImageView homeIcon;
	
	//for menuBar // 	
	@FXML
	public void movePage(ActionEvent event) throws Exception
	{
		String add = "add";
		add+=(((MenuItem)event.getSource()).getText());
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/"+add+".fxml"));
		Stage primaryStage = (Stage) btnVisit.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	@FXML
	public void moveShow(ActionEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/showAll.fxml"));
		Stage primaryStage = (Stage) btnVisit.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	@FXML
	public void moveAction(ActionEvent event) throws Exception
	{
		String add=(((MenuItem)event.getSource()).getText());
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/"+add+".fxml"));
		Stage primaryStage = (Stage) btnVisit.getScene().getWindow();
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
		ArrayList<Person> persons = new ArrayList <Person>();
		if(Zoo.getInstance().getSaveMyUser().getId()==0)  //if the admin wants to add a visit  he will see the users from all the sections
		{
		persons.addAll(Zoo.getInstance().getEmployees().values());
		persons.addAll(Zoo.getInstance().getVisitors().values());
		} //else
		else  //he will get only people from his section 
		{
			ZooEmployee zm = Zoo.getInstance().getRealEmployee((Zoo.getInstance().getSaveMyUser().getId()));
			Section s= zm.getSection();
			persons.addAll(s.getEmployees());
			persons.addAll(s.getVisitors());
		}
		
		comboPersons.setItems(FXCollections.observableArrayList(persons));
		
		//add image
		try {
			Image img= new Image("images/happy.gif");
			img1 = new ImageView(img);
			img1.setFitHeight(150);
			img1.setFitWidth(150);
			hbox.getChildren().add(img1);
			
		}
		catch(Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR, "we have some error");
			alert.setHeaderText("erorr");
			alert.setTitle("error");
			alert.showAndWait();
		}
	}
	
	//Method //
	
	//add mammal and bird to comboBox
	public void addAnimals(ActionEvent event)
	{
		Person p = (Person) comboPersons.getSelectionModel().getSelectedItem();
		Section s = p.getSection();
		ArrayList<AnimalsVisits> a = new ArrayList<AnimalsVisits>();
		a.addAll(s.getMammals());// only in this section
		a.addAll(s.getBirds());// only in this section
		comboAnimal.setItems(FXCollections.observableArrayList(a));
		
	}
	
	//doing the method visit count on mammal and bird
	public void addVisitCounter(MouseEvent event)
	{
		try 
		{
			AnimalsVisits ani = (AnimalsVisits) comboAnimal.getSelectionModel().getSelectedItem();
			ani.visitcount((Person)comboPersons.getSelectionModel().getSelectedItem());
			sucseedMethod();
			
		}
		catch(AnimalsVisitsException e)
		{//we want diffrent message to employee and visitor
			Person p = (Person)comboPersons.getSelectionModel().getSelectedItem();
			if(p instanceof Visitor)
			{
				Alert alert = new Alert(AlertType.ERROR, e.getMessage());
				alert.setHeaderText("Failed Visit!");
				alert.setTitle("Visit");
				alert.showAndWait();
			}
			else if(p instanceof ZooEmployee)
			{
				Alert alert = new Alert(AlertType.INFORMATION,"Dear employee,\nAnimal needs to get treatment");
				alert.setHeaderText("Visit to Treatment");
				alert.setTitle("Visit to Treatment");
				alert.showAndWait();
			}
			
		}
		
		catch(Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR, "one or more field are have proble,");
			alert.setHeaderText("Failed Visit!");
			alert.setTitle("Visit");
			alert.showAndWait();
		}
		
	}
	
	
	public void clearFields(MouseEvent event)
	{
		comboPersons.getSelectionModel().select(0);
		comboAnimal.getSelectionModel().select(0);
	}
	
	private void sucseedMethod()
	{
		Alert alert = new Alert(AlertType.INFORMATION, "");
		alert.setHeaderText("Visit Succeed");
		alert.setTitle("Visit Succeed");
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

