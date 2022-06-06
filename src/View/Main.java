package View;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;

import Model.Zoo;
import Model.ZooEmployee;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Main extends Application {

	private static Zoo myZoo = Zoo.getInstance();
	
	public static void main(String[] args) 
	{
		//myZoo.serialize();
		myZoo.deserialize();
		//myZoo.serialize();
		launch(args);
	}

		
		public void start(Stage primaryStage) throws Exception 
		{
			//which page we want to load next
			Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
			Scene scene = new Scene(root,500,500);  //size of window
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);  //not resizeable
			primaryStage.initStyle(StageStyle.DECORATED);
			primaryStage.setTitle("Fun In Zoo");
			primaryStage.getIcons().add(new Image("./images/zooImg.png"));  //icon
			primaryStage.show();
			//Application.setUserAgentStylesheet(getClass().getResource("mycss.css").toExternalForm());
			//primaryStage.add(getClass().getResource("mycss.css").toExternalForm());
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent event) 
			    {
			        Platform.exit();
			        System.exit(0);
			    }
			});
			
		}
		
		

}
