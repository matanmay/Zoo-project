package View;

import java.time.LocalDate;
import java.util.ArrayList;

import Model.Snack;
import Model.Visitor;
import Model.Zoo;
import Utils.Discount;
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
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class buyingSnack 
{
	
	@FXML
	private ComboBox visitorCombo;
	@FXML
	private ComboBox snackCombo;
	@FXML
	private Button btnBuy;
	
	//menu bar
	@FXML
	private MenuBar menuId;
	@FXML
	private MenuBar menuId2;
	@FXML
	private ImageView img11;
	@FXML
	private HBox hbox11;
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
		//show menuBar
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
		//adding to combox of visitor and snacks
		visitorCombo.setItems(FXCollections.observableArrayList(Zoo.getInstance().getVisitors().values()));
		//visitorCombo.getSelectionModel().select(0);
		Image img2= new Image("images/buy1.gif");
		img11 = new ImageView(img2);
		img11.setFitHeight(150);
		img11.setFitWidth(180);
		hbox11.getChildren().add(img11);
		snackCombo.setVisible(false);
	}
	
	//for menuBar
	@FXML
	public void movePage(ActionEvent event) throws Exception
	{
		String add = "add";
		add+=(((MenuItem)event.getSource()).getText());
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/"+add+".fxml"));
		Stage primaryStage = (Stage) btnBuy.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	@FXML
	public void moveShow(ActionEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/showAll.fxml"));
		Stage primaryStage = (Stage) btnBuy.getScene().getWindow();
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
	public void addSnacks(ActionEvent event) throws Exception
	{//this method and snacks to combolist
		Visitor v = (Visitor) visitorCombo.getValue();
		ArrayList<Snack> snacks = new ArrayList<>();
		if(v.getSection().getBar()!=null)
			snacks.addAll(v.getSection().getBar().getSnacks());
		snackCombo.setItems(FXCollections.observableArrayList(snacks));
		snackCombo.getSelectionModel().select(0);
		snackCombo.setVisible(true);
	}
	
	
	@FXML
	public void buySnack(MouseEvent event)
	{
		try 
		{
			Visitor v = (Visitor) visitorCombo.getValue();
			Snack s = (Snack) snackCombo.getValue();
			v.purchaseSnack(s);
			Zoo.getInstance().serialize();
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
