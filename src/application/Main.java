package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application
{
	@Override
	public void start(Stage primaryStage)
	{
	    Parent root = null;
		try
		{
			root = FXMLLoader.load(getClass().getResource("/view/MainWindow.fxml"));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	        Scene scene = new Scene(root);

	        primaryStage.setScene(scene);
	       // scene.getStylesheets().add("application/application.css");
	        primaryStage.show();
	        /*stage.setOnCloseRequest(new EventHandler<WindowEvent>()
	        {
				@Override
				public void handle(WindowEvent event)
				{
					Platform.exit();
					System.exit(0);
				}
	        });*/
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
