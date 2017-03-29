package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Layer;
import model.NeuronNetwork;

public class MainWindowController
{
	List<Layer> listOfLayers = new ArrayList<Layer>();
	@FXML
	private TextField textFieldNeuronsCount;
	@FXML
	private Button buttonAdd;
	@FXML
	private Button buttonLoadData;
	public NeuronNetwork network;
	@FXML
	public void buttonAddPressed()
	{

	}
	public void initialize()
	{
		network = new NeuronNetwork();
	}
	@FXML
	public void buttonLoadDataPressed()
	{
		try
		{
            BufferedReader inputReader = new BufferedReader(new FileReader("data.txt"));
            String dataLine;
            dataLine = inputReader.readLine();
            while ((dataLine = inputReader.readLine()) != null)
            {
            	String[] splited=dataLine.split(" ");

//            	x.add(Double.parseDouble(splited[0]));
//            	y.add(Double.parseDouble(splited[1]));
//            	z.add(Integer.parseInt(splited[2]));
            }
            inputReader.close();
        	}
			catch (IOException e)
			{
				System.out.println("File Read Error");
			}
	}
}
