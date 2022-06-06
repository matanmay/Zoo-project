package View;

import java.util.ArrayList;

import Exceptions.WrongFieldException;
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

public class moveVisitorSection {
	
	@FXML
	private MenuBar menuId;
	@FXML
	private MenuBar menuId2;
	@FXML
	private ComboBox visitorCombo; // show all visitors
	@FXML
    private Button btnChooseMethod;
	/*@FXML
	private Label myLbl;*/
	@FXML
	private ComboBox sectionCombo; //show all Sections he can to move	
	@FXML
	private Label lblSection;
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
		Stage primaryStage = (Stage) btnChooseMethod.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	@FXML
	public void moveShow(ActionEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/showAll.fxml"));
		Stage primaryStage = (Stage) btnChooseMethod.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	@FXML
	public void moveAction(ActionEvent event) throws Exception
	{
		String add=(((MenuItem)event.getSource()).getText());
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/"+add+".fxml"));
		Stage primaryStage = (Stage) btnChooseMethod.getScene().getWindow();
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
		sectionCombo.setVisible(false);
		lblSection.setVisible(false);
		ArrayList<String>arr = new ArrayList<String>();
		int id = Zoo.getInstance().getSaveMyUser().getId(); // save id of user enter to system
		if(id==0) // if admin
			visitorCombo.setItems(FXCollections.observableArrayList(Zoo.getInstance().getVisitors().values()));
		else 
		{
			Section s = Zoo.getInstance().getRealVisitor(id).getSection();
			visitorCombo.setItems(FXCollections.observableArrayList(s.getVisitors()));
		}
		visitorCombo.setVisible(true);
	}

	//adding the section he can
	@FXML
    public void onMethod(ActionEvent event) 
    {
			Visitor v = (Visitor) visitorCombo.getValue();
			ArrayList<Section> allSectionCan = new ArrayList<>(); // this will be collection that have all sections he can to visit
			allSectionCan.addAll(Zoo.getInstance().getSections().values());
			allSectionCan.remove(v.getSection());
			sectionCombo.setItems(FXCollections.observableArrayList(allSectionCan));
			sectionCombo.setVisible(true);	
			lblSection.setVisible(true);	
	}
	
	
	//doing the change section
	@FXML
	public void changeSection(MouseEvent event) 
    {
		try 
		{
			Visitor v = (Visitor) visitorCombo.getValue();
			if(sectionCombo.getValue() == null) //the user not chose anything
			{
				throw new WrongFieldException();
			}
			v.moveVisitorToSection((Section)sectionCombo.getValue());
			Zoo.getInstance().serialize();
		}
		catch(WrongFieldException e)
		{
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.setHeaderText("You cannot leave a field empty");
			alert.setTitle("No Field");
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
