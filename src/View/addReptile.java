package View;

import java.time.LocalDate;

import Exceptions.WrongFieldException;
import Model.Bird;
import Model.Reptile;
import Model.Section;
import Model.Zoo;
import Utils.AnimalFood;
import Utils.Gender;
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
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class addReptile 
{
	//Fields over order
	
	@FXML
	private TextField txtName; //name of the animal
	@FXML
	private DatePicker dp; // birthday of the animal
	@FXML
	ToggleGroup gender; // will save the gender chose from radio button	
	@FXML
	private ComboBox animalFood; 
	@FXML
	private ComboBox section; 
	@FXML
	ToggleGroup danger; // will save the fly chose from radio button	
	@FXML
	ToggleGroup sleepAtSeason; // will save the if photo chose from radio button	
	@FXML
    private Button btnAddReptile; // button send
	
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
		Stage primaryStage = (Stage) btnAddReptile.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	@FXML
	public void moveShow(ActionEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/showAll.fxml"));
		Stage primaryStage = (Stage) btnAddReptile.getScene().getWindow();
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
		//adding to combox of animalFood type and section
		animalFood.setItems(FXCollections.observableArrayList(AnimalFood.values()));
		animalFood.getSelectionModel().select(0);
		section.setItems(FXCollections.observableArrayList(Zoo.getInstance().getSections().values()));
		section.getSelectionModel().select(0);
		//animal could have born today
		dp.setDayCellFactory(picker -> new DateCell() {
	        public void updateItem(LocalDate date, boolean empty) {
	            super.updateItem(date, empty);
	            LocalDate minDate = LocalDate.of(1920, 1, 1);
	            LocalDate maxDate = LocalDate.now();
	            setDisable(date.compareTo(maxDate)>0 || date.compareTo(minDate) < 0 );
	        }
	    });
		dp.setEditable(false);
	}
	
	
	@FXML
    void addReptile(MouseEvent event) 
	{

		try 
		{
				if(txtName.getText().isEmpty() || dp.getValue() == null) //some of the fields is empty
				{
					throw new WrongFieldException();
				}
				RadioButton selectedGender = (RadioButton) gender.getSelectedToggle();
				String toogleGroupValue = selectedGender.getText();
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
				
				Boolean isdanger;
				Boolean hasSleepSeason;
				RadioButton selectedMeater = (RadioButton) danger.getSelectedToggle();
				if(selectedMeater.getText().equals("Yes"))
				{
					isdanger = true;
				}
				else
				{
					isdanger = false;
				}
				RadioButton selectedPhoto = (RadioButton) sleepAtSeason.getSelectedToggle();
				if(selectedPhoto.getText().equals("Yes"))
				{
					hasSleepSeason = true;
				}
				else
				{
					hasSleepSeason = false;
				}
				
				
				Reptile newReptile = new Reptile(txtName.getText(), dp.getValue(),(AnimalFood) animalFood.getValue(), g,(Section) section.getValue(), isdanger, hasSleepSeason);
				
				//adding to Zoo
				Boolean flag = Zoo.getInstance().addReptileById(newReptile);
				
				if(flag)
				{
					//if added successfully then play audio
					Media medFromSrc =new Media(getClass().getResource("/images/frog_sound_effect.mp4").toExternalForm());
				    MediaPlayer mediaPlayer = new MediaPlayer(medFromSrc);
				    mediaPlayer.play();
				    mediaPlayer.setAutoPlay(true); 
					Alert alert = new Alert(AlertType.INFORMATION, "you can see him: show, Reptiles.");
					alert.setHeaderText("Done: reptile: "+newReptile.getName()+", was added succesfully!");
					alert.setTitle("Add Reptile");
					alert.showAndWait();
					Zoo.getInstance().serialize();
				}
				else
				{
					Alert alert = new Alert(AlertType.ERROR, "The reptile was not added to reptiles list.");
					alert.setHeaderText("Failed adding!");
					alert.setTitle("Add Reptile");
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
			catch (NullPointerException e) 
			{
				Alert alert = new Alert(AlertType.ERROR, "One or more of fields are empty");
				alert.setHeaderText("Failed adding!");
				alert.setTitle("Add Reptile");
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
		 try {
			 txtName.clear();
			 dp.setValue(null);
			 gender.selectToggle(null);
			 animalFood.getSelectionModel().select(0);
			 section.getSelectionModel().select(0);
			 danger.selectToggle(null);
			 sleepAtSeason.selectToggle(null);
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
