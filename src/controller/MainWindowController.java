package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Bias;
import model.Input;
import model.Layer;
import model.Neuron;
import model.NeuronNetwork;
import model.Teacher;
import model.TrainingData;

public class MainWindowController
{
	private Teacher teacher = new Teacher(0.01, 100000, 0.6);
	@FXML
	private TextField textFieldNeuronsCount;
	@FXML
	private Button buttonAdd;
	@FXML
	private Button buttonLoadData;
	public NeuronNetwork network;
	@FXML
	public TableView<NeuronNetwork> tableViewNetwork;
	@FXML
	private Button buttonAddBias;
	@FXML
	public void buttonAddBiasPressed()
	{
		buttonAddBias.setDisable(true);
		for (int i=1; i<network.getLayers().size(); i++)
		{
			Layer layer = network.getLayers().get(i);
			for (int j=0; j<layer.getNeurons().size();j++)
			{
				Neuron neuron = layer.getNeurons().get(j);
				Input inputBias = new Input(1);
				Bias bias = new Bias();
				inputBias.setInputNeuron(new Bias());
				bias.addOutputConnection(inputBias);
				neuron.addInputConnection(inputBias);
				inputBias.setOutputNeuron(neuron);
				neuron.getWeights().add(Math.random()-0.5);
			}
		}
	}
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
	public void fillTable()
	{
//		for (int i=0; i<network.getLayers().size();i++)
//		{
//			System.out.println("Warstwa " + i);
//			System.out.println("MA " + network.getLayers().get(i).getNeurons().size() + " NEURONOW");
//			System.out.println("MAJA ONE " + network.getLayers().get(i).getNeurons().get(0).getInputConnections().size() + "polaczen");
//		}
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
		teacher.setNetwork(network);

		// for debug
//		textFieldNeuronsCount.setText("3");
//		buttonAddPressed();
//		textFieldNeuronsCount.setText("4");
//		buttonAddPressed();
//		if ( true)
//		{
//			System.out.println("HAHA");
//		}

	}
	@FXML
	public void buttonLoadDataPressed()
	{
		teacher.clearTrainingData();
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
//		List<Double> haha ;
		for (int i=0; i<inputValues.size();i=i+4)
		{
			double[] trainingValues = new double[]{inputValues.get(i), inputValues.get(i+1), inputValues.get(i+2), inputValues.get(i+3)};
			teacher.addTrainingData(new TrainingData(trainingValues, trainingValues));
			//System.out.println("TRAINING DATA " + teacher.getTrainingData().size());
//			haha = new ArrayList<Double>();
//			haha.add(inputValues.get(i));
//			haha.add(inputValues.get(i+1));
//			haha.add(inputValues.get(i+2));
//			haha.add(inputValues.get(i+3));
//			network.setInputs(haha);
//			network.setNewInput();
//			double[] hahax = network.getOutput();
//			System.out.println(hahax[0]);
//			System.out.println(hahax[1]);
//			System.out.println(hahax[2]);
//			System.out.println(hahax[3]);
//			System.out.println("PRZERWA");
		}
		//System.out.println(inputValues.size());
		teacher.teach();
		fillTable();
	}
}
