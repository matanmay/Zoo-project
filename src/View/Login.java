package View;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

import com.sun.javafx.geom.Point2D;

import Model.Zoo;
import Model.ZooEmployee;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;


public class Login {
	
	@FXML
    private Button login;
	@FXML
	private TextField txtUserName;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private ImageView lionImg;
	@FXML
	private ImageView giraffImg;
	@FXML
	private Tooltip toolTip;
	@FXML
	private TextField showPassTxt;
	@FXML
	private Button myButton;
	@FXML
	private ImageView fbLogo;
	@FXML
	private ImageView inIcon;
	
	@FXML
	private AnchorPane myPane;
	
	@FXML
	private ImageView eyeIco;
	String password;
	int counter = 0; // the counter will save the number of times we clicked on eye

	
	@FXML
	public void initialize() 
	{		
		eyeIco.setVisible(true);
		lionImg.setVisible(true);
		giraffImg.setVisible(true);
		showPassTxt.setVisible(false);
		fbLogo.setVisible(true);
		inIcon.setVisible(true);
		myButton.setVisible(true);
		Tooltip.install(eyeIco, new Tooltip("show password"));
	}
	
	//This method do if we click on eye Image it will show the password (replace the password field to text field)
	public void showPassword(MouseEvent event)
	{
		counter ++;
		if(counter%2==1) // if click onece show the password
		{
			showPassTxt.setVisible(true);
			txtPassword.setVisible(false);
			
			showPassTxt.setText(txtPassword.getText());
			
		}
		else
		{ // secound clicked
			
			showPassTxt.setVisible(false);
			txtPassword.setVisible(true);
			txtPassword.setText(showPassTxt.getText());
			
		}
		
	}
	@FXML
	public void openFb(MouseEvent event) throws IOException, URISyntaxException
	{
		  Desktop.getDesktop().browse(new URI("https://www.facebook.com/zooInFunHaifa/"));
	}
	
	public void logIn(MouseEvent event) throws Exception
	{
		try 
		{
			if(counter%2 == 1 ) // he do show password
			{
				txtPassword.setText(showPassTxt.getText()); //send the text to password field
			}
			
			
			//administrator login
			if(txtUserName.getText().equals("admin") && txtPassword.getText().equals("admin")) 
			{
				Zoo.getInstance().setSaveMyUser(new ZooEmployee(0));  // if its admin the saveMyUser id will be -1
				Parent newRoot = FXMLLoader.load(getClass().getResource("/View/homePage.fxml"));
				Stage primaryStage = (Stage) login.getScene().getWindow();
				primaryStage.getScene().setRoot(newRoot);
				primaryStage.show();
				//Zoo.getInstance().setSaveMyUser(new ZooEmployee(0));
				
			}
			//user login
			else if(Zoo.getInstance().checkUser(txtUserName.getText(), txtPassword.getText())>0)
			{
				// if he is a user i will save all his details in my employee in zoo which is saveMyUser
				Zoo.getInstance().setSaveMyUser(Zoo.getInstance().getRealEmployee(Zoo.getInstance().checkUser(txtUserName.getText(), txtPassword.getText())));
				Parent newRoot = FXMLLoader.load(getClass().getResource("/View/homePage.fxml"));
				Stage primaryStage = (Stage) login.getScene().getWindow();
				primaryStage.getScene().setRoot(newRoot);
				primaryStage.show();
			}
			//not exist
			else
			{
				throw new Exceptions.WrongFieldException(); //call to exception because wrong fields
			}
		}
		catch(Exceptions.WrongFieldException e)
		{
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.setHeaderText("Failed Login");
			alert.setTitle("Cannot Login");
			alert.showAndWait();
		}
		
		
	}
}
