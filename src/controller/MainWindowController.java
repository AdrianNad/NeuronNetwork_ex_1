package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Input;
import model.Layer;
import model.Neuron;
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
		int count = Integer.parseInt(textFieldNeuronsCount.getText());
		List<Neuron> neurons = new ArrayList<Neuron>();
		for (int i=0; i<count; i++)
		{
			neurons.add(new Neuron());
		}
		Layer newLayer = new Layer();
		newLayer.setNeurons(neurons);
		network.addLayer(newLayer);
		network.createAllConnections();
		network.generateWeights();
	}
	@FXML
	public void initialize()
	{
		network = new NeuronNetwork();
		Input inputOne = new Input();
		Input inputTwo = new Input();
		Input inputThree = new Input();
		Input inputFour = new Input();
		List<Neuron> neurons = new ArrayList<Neuron>();
		Neuron neuronOne = new Neuron(inputOne);
		Neuron neuronTwo = new Neuron(inputTwo);
		Neuron neuronThree = new Neuron(inputThree);
		Neuron neuronFour = new Neuron(inputFour);
		neurons.add(neuronOne);
		neurons.add(neuronTwo);
		neurons.add(neuronThree);
		neurons.add(neuronFour);
		Layer inputLayer = new Layer();
		inputLayer.setNeurons(neurons);
		network.addLayer(inputLayer);
	}
	@FXML
	public void buttonLoadDataPressed()
	{
		Vector<Double> inputValues = new Vector<Double>();
		try
		{
            BufferedReader inputReader = new BufferedReader(new FileReader("data.txt"));
            String dataLine;
            dataLine = inputReader.readLine();
            while ((dataLine = inputReader.readLine()) != null)
            {
            	String[] splited=dataLine.split(" ");
            	inputValues.add(Double.parseDouble(splited[0]));
            	inputValues.add(Double.parseDouble(splited[1]));
            	inputValues.add(Double.parseDouble(splited[2]));
            	inputValues.add(Double.parseDouble(splited[3]));
            }
            inputReader.close();
        	}
			catch (IOException e)
			{
				System.out.println("File Read Error");
				e.printStackTrace();
			}
		List<Double> haha ;
		for (int i=0; i<inputValues.size();i=i+4)
		{
			haha = new ArrayList<Double>();
			haha.add(inputValues.get(i));
			haha.add(inputValues.get(i+1));
			haha.add(inputValues.get(i+2));
			haha.add(inputValues.get(i+3));
			network.setInputs(haha);
			network.setNewInput();
			double[] hahax = network.getOutput();
			System.out.println(hahax[0]);
			System.out.println(hahax[1]);
			System.out.println(hahax[2]);
			System.out.println(hahax[3]);
			System.out.println("PRZERWA");
		}

	}
}
