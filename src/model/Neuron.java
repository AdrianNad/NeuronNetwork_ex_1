package model;

import static java.lang.Math.exp;
import static java.lang.Math.pow;

import java.util.ArrayList;
import java.util.List;

public class Neuron
{
	private List<Connection> inputConnections;
	private List<Connection> outputConnections;
	private List<Double> weights;
	private List<Double> previousChanges;

	public void fillPreviousChanges()
	{
		previousChanges.clear();
		for(int i=0; i<weights.size();i++)
		{
			previousChanges.add(0.0);
		}
	}
	public List<Double> getPreviousChanges()
	{
		return previousChanges;
	}
	public void setPreviousChanges(List<Double> previousChanges)
	{
		this.previousChanges = previousChanges;
	}
	private double error;

	public List<Double> getWeights()
	{
		return weights;
	}
	public void setWeights(List<Double> weights)
	{
		this.weights = weights;
	}
	public double getError()
	{
		return error;
	}
	public void setError(double error)
	{
		this.error = error;
	}
	public List<Connection> getInputConnections()
	{
		return inputConnections;
	}
	public void setInputConnections(List<Connection> inputConnections)
	{
		this.inputConnections = inputConnections;
	}
	public List<Connection> getOutputConnections()
	{
		return outputConnections;
	}
	public void setOutputConnections(List<Connection> outputConnections)
	{
		this.outputConnections = outputConnections;
	}
	public void addInputConnection(Connection conn)
	{
		inputConnections.add(conn);
	}
	public void addOutputConnection(Connection conn)
	{
		outputConnections.add(conn);
	}
	public double getSum()
	{
		Double sum = 0.0;
		for (int i=0; i<inputConnections.size(); i++)
		{
			//System.out.println("ROZMIAR " +inputConnections.size() + " SUMA " + sum + " ITERACJA " + i);
			sum += inputConnections.get(i).getOutput()*weights.get(i);
		}
		return sum;
	}
	public double getOutput()
	{
		if (inputConnections.get(0) instanceof Input)
		{
			return getSum();
		}
		else
		{
			return getActivationFunctionValue(getSum()); // TU MA BYC FUNKCJA AKTYWACJI Z SUMY POWYZEJ
		}
	}//INPUTY OVERRIDE
	public double getActivationFunctionValue(double x)
	{
			return 1/(1 + exp(-x));
	}
	public double getDerivativeActivationFunctionValue(double x)
	{
		return exp(x)/pow(exp(x) + 1, 2);
	}
	public Neuron()
	{
		super();
		initializeLists();
	}
	public void initializeLists()
	{
		weights = new ArrayList<Double>();
		previousChanges = new ArrayList<Double>();
		inputConnections = new ArrayList<Connection>();
		outputConnections = new ArrayList<Connection>();
	}
	public void generateWeights()
	{
		weights.clear();
		for (int i=0; i<inputConnections.size(); i++)
		{
			weights.add(Math.random()-0.5);
		}
	}
	public Neuron (Input input) // it's for a layer which will be the input of the whole network
	{
		initializeLists();
		addInputConnection(input);
		weights.add(1.0);
		input.setOutputNeuron(this);
	}
	public void clearInputs()
	{
		inputConnections.clear();
	}
	public void clearOutputs()
	{
		outputConnections.clear();
	}
//	public Neuron(Double[] valuesToCopy)
//	{
//		super();
//		initializeLists(valuesToCopy.length);
//		copyValues(valuesToCopy);
//		step = 0.1;
//	}
//	public void copyValues(Double[] valuesToCopy)
//	{
//		if ( weights.size() == valuesToCopy.length)
//		{
//			values.clear();
//			for (int i=0; i<valuesToCopy.length; i++)
//			{
//				values.add(valuesToCopy[i]);
//			}
//		}
//		else
//		{
//			// TODO later
//		}
//	}
//	public void changeWeights(double error)
//	{
//		for (int i=0; i<weights.size(); i++)
//		{
//			System.out.println(" Przed " +weights.get(i));
//			weights.set(i, weights.get(i) + step * error * values.get(i));
//			System.out.println(" Po " +weights.get(i));
//		}
//	}
//	public Double sum()
//	{
//		Double sum = 0.0;
//		for (int i=0; i<values.size(); i++)
//		{
//			sum += values.get(i)*weights.get(i);
//		}
//		return sum;
//	}
//	public Double countResult()
//	{
//		if (sum() > 0.0) return 1.0;
//		else return 0.0;
//		//if (sum() > function(values.get(0))) return 1.0;
//		//else return 0.0;
//	}

}
