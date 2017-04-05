package controller;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Bias;
import model.Input;
import model.Layer;
import model.Neuron;
import model.NeuronNetwork;
import model.Teacher;
import model.TrainingData;

public class MainWindowController
{
	private Teacher teacher = new Teacher(0.01, 25000, 0.4, 0.9);
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
	private ImageView imageViewChart;
	private static XYSeries series = new XYSeries("Wykres bledow");

	public XYSeries getSeries()
	{
		return series;
	}
	public void setSeries(XYSeries series)
	{
		this.series = series;
	}
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
				neuron.getPreviousChanges().add(0.0);
			}
		}
	}
	@FXML
	public void buttonCreateChartPressed()
	{
		XYSeriesCollection dataSetCollection = new XYSeriesCollection();
		dataSetCollection.addSeries(series);
	    String chartTitle = "Wykres bledow";
	    String xAxisLabel = "Liczba epok";
	    String yAxisLabel = "Liczba bledow";
	    XYDataset dataset = dataSetCollection;
	    JFreeChart chart;
	    	chart = ChartFactory.createXYLineChart(chartTitle,
	    			xAxisLabel, yAxisLabel, dataset);
	    XYPlot plot = chart.getXYPlot();
	    plot.setRangeGridlinesVisible(true);
	    plot.setRangeGridlinePaint(Color.BLACK);

	    plot.setDomainGridlinesVisible(true);
	    plot.setDomainGridlinePaint(Color.BLACK);
	    File imageFile = new File("Chart.png");
	    int width = 640;
	    int height = 480;

	    try
	    {
	        ChartUtilities.saveChartAsPNG(imageFile, chart, width, height);
	    }
	    catch (IOException ex)
	    {
	        System.err.println(ex);
	    }
	    File file = new File("Chart.png");
		String pathToImage = ("File:" + file.getPath()).replace("\\", "/");
		Image chartImage = new Image(pathToImage);
		imageViewChart.setImage(chartImage);
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
		network.initializeMomentum();
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
		teacher.setController(this);
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
		series.clear();
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
