package model;

import java.util.ArrayList;
import java.util.List;


public class NeuronNetwork
{
	private List<Layer> layers = new ArrayList<Layer>();
	private List<Double> inputs = new ArrayList<Double>();

	public List<Layer> getLayers()
	{
		return layers;
	}

	public void setLayers(List<Layer> layers)
	{
		this.layers = layers;
	}

	public List<Double> getInputs()
	{
		return inputs;
	}

	public void setInputs(List<Double> inputs)
	{
		this.inputs = inputs;
	}

	public void addLayer(Layer layer)
	{
		layers.add(layer);
	}
	public void generateWeights()
	{
		for(int i=1; i<layers.size(); i++) // layer at index 0 is input of the whole network
		{
			layers.get(i).generateWeights();
		}
	}
	public void createAllConnections()
	{
		for(int i=1; i<layers.size(); i++)
		{
			for (Neuron current : layers.get(i).getNeurons())
			{
				current.clearInputs();
			}
			layers.get(i).connect(layers.get(i-1));
		}
	}
	public void recreateInputConnection()
	{
		if(layers.size() > 1)
		{
			for (Neuron current : layers.get(1).getNeurons())
			{
				current.clearInputs();
			}
			layers.get(1).connect(layers.get(0));
		}
	}
	public void setNewInput()
	{
		Layer inputLayer = new Layer();
		for (int i=0; i<inputs.size();i++)
		{
			Input input = new Input(inputs.get(i));
			Neuron neuron = new Neuron(input);
			inputLayer.addNeuron(neuron);
		}
		layers.set(0, inputLayer);
		recreateInputConnection();
	}
	public double[] getOutput()
	{
		double[] output = new double[layers.get(layers.size() - 1).getNeurons().size()];
		for(int i=0; i<output.length; i++)
		{
			Neuron neuron = layers.get(layers.size() - 1).getNeurons().get(i);
			output[i] = neuron.getOutput();
		}
		return output;
	}
//	public void setInputsAndCreateInputLayer(List<Double> inputs) // DODAC REMOVE CONNECTIONS W LAYER I ZROBIC TO NA TEJ Z INDEXEM 1
//	{
//		setInputs(inputs);
//		createInputLayer();
//
//
//	}
}
