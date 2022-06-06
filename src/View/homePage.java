package View;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.Person;
import Model.Zoo;
import Model.ZooEmployee;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
//import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;


public class homePage implements Initializable 
{
	//for menuBar //
	@FXML
	private MenuBar menuId;
	@FXML
	private MenuBar menuId2;
	@FXML
	private ImageView penguimImage;
	@FXML
	private HBox hbox111;
	@FXML
	private Label lblHello;
	@FXML
	private Label lblStatus;
	@FXML
	private Button buttonUpdate;
	@FXML
	private Button buttonDone;
	@FXML
	private TextField textStatus;
	@FXML
	private MediaView media;
	@FXML
	private MediaPlayer mediaPlayer;
	@FXML
	private ImageView logOutIcon;
	@FXML
	private ImageView homeIcon;
	@FXML
	private AnchorPane myPane;
	
	//private final String MEDIA_URL = "C:/movie/v.mp4";
	
	private final String MEDIA_URL = "/images/videoplayback.mp4";
	
	//for menuBar // 	
	@FXML
	public void movePage(ActionEvent event) throws Exception
	{
		if(mediaPlayer!=null)
			mediaPlayer.stop(); // if we not doing that this will be continue in other page
		String add = "add";
		add+=(((MenuItem)event.getSource()).getText());
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/"+add+".fxml"));
		Stage primaryStage = (Stage) buttonUpdate.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	@FXML
	public void moveShow(ActionEvent event) throws Exception
	{
		if(mediaPlayer!=null)
			mediaPlayer.stop();// if we not doing that this will be continue in other page
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/showAll.fxml"));
		Stage primaryStage = (Stage) buttonUpdate.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	@FXML
	public void moveAction(ActionEvent event) throws Exception
	{
		if(mediaPlayer!=null)
			mediaPlayer.stop();// if we not doing that this will be continue in other page
		String add=(((MenuItem)event.getSource()).getText());
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/"+add+".fxml"));
		Stage primaryStage = (Stage) buttonUpdate.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		textStatus.setVisible(false);
		buttonDone.setVisible(false);
		buttonUpdate.setVisible(false);
		penguimImage.setVisible(true);
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
			lblHello.setText("Hello Admin,");
			lblStatus.setText(" Mirror mirror on the wall,\n  who is the prettiest admin of them all?");
		}
		else
		{
			menuId.setVisible(false);
			menuId2.setVisible(true);
			lblHello.setText("Hello "+ Zoo.getInstance().getSaveMyUser().getFirstName()+" "+Zoo.getInstance().getSaveMyUser().getLastName()+"!");
			lblStatus.setText(Zoo.getInstance().getSaveMyUser().getMyStatus());
			//showing the user status
			buttonUpdate.setVisible(true);
		}
		
		try 
		{ 
			//playing the video we added	
			mediaPlayer = new MediaPlayer(new Media(this.getClass().getResource(MEDIA_URL).toExternalForm()));
			mediaPlayer.setAutoPlay(true);
			media.setMediaPlayer(mediaPlayer);
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); //nonStop
		}
		catch(Exception e)
		{
			//Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			Alert alert = new Alert(AlertType.ERROR, "We have some troubles, Please try again later");
			alert.setHeaderText("erorr");
			alert.setTitle("error");
			alert.showAndWait();
		}
		
	}	
	
	@FXML
	public void showField(MouseEvent event) 
	{
		textStatus.setVisible(true);
		buttonDone.setVisible(true);
	}
	@FXML
	public void updateField(MouseEvent event) 
	{
		Zoo.getInstance().getSaveMyUser().setMyStatus(textStatus.getText());
		lblStatus.setText(Zoo.getInstance().getSaveMyUser().getMyStatus());
		textStatus.setVisible(false);
		buttonDone.setVisible(false);
		Zoo.getInstance().serialize();
	}
	
	public void logOut(MouseEvent e) throws Exception
	{
		if(mediaPlayer!=null)
			mediaPlayer.stop(); // if we not doing that this will be continue in other page
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
		Stage primaryStage = (Stage) buttonUpdate.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	public void goToHomePage(MouseEvent e) throws Exception
	{
		if(mediaPlayer!=null)
			mediaPlayer.stop(); // if we not doing that this will be continue in other page
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/homePage.fxml"));
		Stage primaryStage = (Stage) buttonUpdate.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
}
