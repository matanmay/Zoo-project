package View;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

import Model.Zoo;
import Utils.Discount;
import Utils.TicketType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DateCell;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class snackChart {
	
	@FXML
	private MenuBar menuId;
	@FXML
	private PieChart myChart;
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
		
		HashMap<String,Integer> hm= new HashMap<String,Integer>();
		hm.putAll(Zoo.getInstance().getCounterSnacks());  //add all the snacks with counter 
		/* PieChart.Data data[] = new PieChart.Data[hm.size()];*/
		 ArrayList <String>name =new ArrayList<String>();
		 name.addAll(hm.keySet());  //we will show the names in labels
		 ArrayList <Integer>count =new ArrayList<Integer>();
		 count.addAll(hm.values());  //and we will use counter as percentage to show the amount of bought snacks
		 ObservableList<PieChart.Data> secdata = FXCollections.observableArrayList();
		for(int i=0; i<hm.size();i++)  //will add each time the data
		{		
			secdata.add(new PieChart.Data(name.get(i), count.get(i)));
		}
		myChart.setData(secdata);
		myChart.setVisible(true);
		logOutIcon.setVisible(true);
		Tooltip.install(logOutIcon, new Tooltip("LogOut"));	
		homeIcon.setVisible(true);
		Tooltip.install(homeIcon, new Tooltip("Home Page"));	
	}
	
	
	public void logOut(MouseEvent e) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
		Stage primaryStage = (Stage) menuId.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	public void goToHomePage(MouseEvent e) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/homePage.fxml"));
		Stage primaryStage = (Stage) menuId.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}

}
