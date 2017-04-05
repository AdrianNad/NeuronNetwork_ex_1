package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Teacher
{
	private NeuronNetwork network;
	private List<TrainingData> trainingData = new ArrayList<TrainingData>();
	private double step;
	private double possibleError;
	private int centuryLimit;
	public NeuronNetwork getNetwork()
	{
		return network;
	}
	public void setNetwork(NeuronNetwork network)
	{
		this.network = network;
	}
	public List<TrainingData> getTrainingData()
	{
		return trainingData;
	}
	public void setTrainingData(List<TrainingData> trainingData)
	{
		this.trainingData = trainingData;
	}
	public void addTrainingData(TrainingData newTrainingData)
	{
		trainingData.add(newTrainingData);
	}
	public double getStep()
	{
		return step;
	}
	public void setStep(double step)
	{
		this.step = step;
	}
	public double getPossibleError()
	{
		return possibleError;
	}
	public void setPossibleError(double possibleError)
	{
		this.possibleError = possibleError;
	}
	public void clearTrainingData()
	{
		trainingData.clear();
	}

	public Teacher(int centuryLimit)
	{
		this.centuryLimit = centuryLimit;
		step = 0.6;
	}

	public Teacher(double possibleError, int centuryLimit, double step)
	{
		super();
		this.possibleError = possibleError;
		this.centuryLimit = centuryLimit;
		this.step = step;
	}
	public void teach()
	{
		double mseSum;
		List<Double> inputValues ;
		double maxError = 10000;
		for (int j=0; j<centuryLimit && maxError > possibleError; j++)
		{
//			Collections.shuffle(trainingData);
			double[] errors = new double[trainingData.size()];
			System.out.println("Epoka " + (j+1) + " : ");
			mseSum = 0.0;
			for (int i=0; i<trainingData.size();i++)
			{
				double[] trainingInput = trainingData.get(i).getInput();
				double[] trainingOutput = trainingData.get(i).getOutput();
				System.out.println("Dane wejsciowe: " + (i+1));
				inputValues = new ArrayList<Double>();
				for (Double current : trainingInput)
				{
					inputValues.add(current);
				}
				network.setInputs(inputValues);
				network.setNewInput();
				double[] output = network.getOutput();
//				System.out.println(output[0]);
//				System.out.println(output[1]);
//				System.out.println(output[2]);
//				System.out.println(output[3]);

				Layer lastLayer = network.getLayers().get((network.getLayers().size()-1));
				for (int k = 0; k < lastLayer.getNeurons().size(); k++)
				{
					Neuron neuron = lastLayer.getNeurons().get(k);
					neuron.setError(neuron.getDerivativeActivationFunctionValue(neuron.getSum()) * (trainingOutput[k] - output[k]));
				}

				// hidden
				for (int k = network.getLayers().size() - 2; k > 0; k--)
				{
					Layer layer = network.getLayers().get(k);
					for (int l = 0; l < layer.getNeurons().size(); l++)
					{
						Neuron neuron = layer.getNeurons().get(l);
						double sum = 0.0;
						for (int m = 0; m < neuron.getOutputConnections().size(); m++)
						{
							//System.out.println("NEURON W WARSTWIE " + k + " NUMER " + (l+1) + " MA OUTPUT CONNECTIONS = " + neuron.getOutputConnections().size() );
							sum += neuron.getWeights().get(m)* neuron.getOutputConnections().get(m).getOutputNeuron().getError();
						}
						neuron.setError(neuron.getDerivativeActivationFunctionValue(neuron.getSum())* sum);
					}
				}
				// weights
				for (int k = network.getLayers().size() - 1; k > 0; k--)
				{
					Layer layer = network.getLayers().get(k);
					for (int l = 0; l < layer.getNeurons().size(); l++)
					{
						Neuron neuron = layer.getNeurons().get(l);
						for (int m=0; m<neuron.getInputConnections().size(); m++)
						{
							neuron.getWeights().set(m, neuron.getWeights().get(m) + step * neuron.getError() * neuron.getInputConnections().get(m).getInputNeuron().getOutput());
							//neuron.getInputConnections().get(m).getInputNeuron().getWeights().set(l, neuron.getInputConnections().get(m).getInputNeuron().getWeights().get(l) + step * neuron.getError()* neuron.getInputConnections().get(m).getInputNeuron().getOutput());
						}
					}
				}

//				System.out.println("Inputy: ");
//				for (Neuron current : network.getLayers().get(0).getNeurons())
//				{
//					System.out.println(current.getOutput());
//				}
				System.out.println("Outputy: ");
				for (Double current : output)
				{
					System.out.println(current);
				}
				errors[i] = Math.abs(output[0] - trainingOutput[0]);
				for (int k = 0; k < output.length; k++)
				{
					if (errors[i] < Math.abs(output[k] - trainingOutput[k]))
					{
						errors[i] = Math.abs(output[k] - trainingOutput[k]);
					}
				}
				for (int k = 0; k< lastLayer.getNeurons().size(); k++)
				{
					mseSum += Math.pow(output[k] - trainingOutput[k], 2);
				}
			}
			double mseValue = mseSum / 16.0;
			System.out.println("Blad sredniokwadratowy po epoce: " + mseValue);
					maxError = errors[0];
					for (int k = 1; k < errors.length; k++)
					{
						if (maxError < errors[k])
						{
							maxError = errors[k];
						}
					}
			}
		}
}
