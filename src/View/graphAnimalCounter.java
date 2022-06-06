package View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Model.Animal;
import Model.Zoo;
import Utils.Job;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class graphAnimalCounter 
{
	@FXML
	private MenuBar menuId;
	@FXML
	private MenuBar menuId2;
	
	@FXML
	private BarChart animalsCart;
	@FXML
	private ImageView logOutIcon;
	@FXML
	private ImageView homeIcon;
	
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
		//get the birds and mammals from zoo
		HashMap<Animal,Integer> birdsCount = Zoo.getInstance().getCounterVisitsBird();
		HashMap<Animal,Integer> mammalsCount = Zoo.getInstance().getCounterVisitsMammal();
		showAnimal(birdsCount,"Birds"); //shows charts of Bird
		showAnimal(mammalsCount,"Mammals"); //shows charts of Mammal

	}
	
	//This method gets HashMap of animals and counter and String of name and shows on screen chartBar of collection and name 
	private void showAnimal(HashMap<Animal,Integer> hm, String name)
	{
	    XYChart.Series<String, Number> series1 = new XYChart.Series<>();
	    for (Map.Entry<Animal, Integer> entry : hm.entrySet()) {
	        String tmpString = entry.getKey().getName();
	        Number tmpValue = entry.getValue();
	        XYChart.Data<String, Number> d = new XYChart.Data<>(tmpString, tmpValue);
	        //System.out.println(d);
	        series1.getData().add(d);
	        series1.setName(name); //show the name of what we see
	    }
	    animalsCart.getData().addAll(series1);
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

