package model;

import java.util.ArrayList;
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
		List<Double> inputValues ;
		double maxError = 10000;
		for (int j=0; j<centuryLimit && maxError > possibleError; j++)
		{
			System.out.println("Epoka " + (j+1) + " : ");
			for (int i=0; i<trainingData.size();i++)
			{
				System.out.println("Dane wejsciowe: " + (i+1));
				inputValues = new ArrayList<Double>();
				for (Double current : trainingData.get(i).getInput())
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
					neuron.setError(neuron.getDerivativeActivationFunctionValue(neuron.getSum()) * (trainingData.get(i).getOutput()[k] - output[k]));
				}

				// hidden
				for (int k = network.getLayers().size() - 2; k > 1; k--)
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
				for (int k = network.getLayers().size() - 1; k > 1; k--)
				{
					Layer layer = network.getLayers().get(k);
					for (int l = 0; l < layer.getNeurons().size(); l++)
					{
						Neuron neuron = layer.getNeurons().get(l);
						for (int m=0; m<neuron.getWeights().size(); m++)
						{
							neuron.getWeights().set(m, neuron.getWeights().get(m) + step * neuron.getError() * neuron.getInputConnections().get(m).getInputNeuron().getOutput());
						}
					}
				}

				double[] out = network.getOutput();
				for (Double current : out)
				{
					System.out.println(current);
				}
				double[] errors = new double[trainingData.size()];
				errors[i] = Math.abs(out[0] - trainingData.get(i).getOutput()[0]);
				for (int k = 0; k < out.length; k++)
				{
					if (errors[i] < Math.abs(out[k] - trainingData.get(i).getOutput()[k]))
					{
						errors[i] = Math.abs(out[k] -trainingData.get(i).getOutput()[k]);
					}
				}
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
}
