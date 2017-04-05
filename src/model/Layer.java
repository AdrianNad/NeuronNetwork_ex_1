package model;

import java.util.ArrayList;
import java.util.List;


public class Layer
{
	private List<Neuron> neurons = new ArrayList<Neuron>();

	public void initializeMomentum()
	{
		for(Neuron current : neurons)
		{
			current.fillPreviousChanges();
		}
	}
	public List<Neuron> getNeurons()
	{
		return neurons;
	}
	public void setNeurons(List<Neuron> neurons)
	{
		this.neurons = neurons;
	}
	public void addNeuron(Neuron neuron)
	{
		neurons.add(neuron);
	}
	public void generateWeights()
	{
		for (Neuron current : neurons)
		{
			current.generateWeights();
		}
	}

	public void connect(Layer lowerLayer)
	{
		for(Neuron output: neurons)
		{
			for(Neuron input: lowerLayer.getNeurons())
			{
				new Connection(input, output);
				//System.out.println("OUTPUTY : " + output.getInputConnections().size());
			}
		}
	}

}
