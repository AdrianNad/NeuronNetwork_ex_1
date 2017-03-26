package model;

public class Connection
{
	private Neuron inputNeuron;
	protected Neuron outputNeuron;
	public Neuron getInputNeuron()
	{
		return inputNeuron;
	}
	public void setInputNeuron(Neuron inputNeuron)
	{
		this.inputNeuron = inputNeuron;
	}
	public Neuron getOutputNeuron()
	{
		return outputNeuron;
	}
	public void setOutputNeuron(Neuron outputNeuron)
	{
		this.outputNeuron = outputNeuron;
	}
	public Connection(Neuron inputNeuron, Neuron outputNeuron)
	{
		super();
		this.inputNeuron = inputNeuron;
		this.outputNeuron = outputNeuron;
		inputNeuron.addOutputConnection(this);
		outputNeuron.addInputConnection(this);
	}
	public Connection()
	{
		// TODO Auto-generated constructor stub
	}
	public double getOutput()
	{
		return inputNeuron.getOutput();
	}

}
