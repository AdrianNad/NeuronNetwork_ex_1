package model;

public class TrainingData
{
	public double[] input;
	public double[] output;
	public TrainingData(double[] input, double[] output)
	{
		super();
		this.input = input;
		this.output = output;
	}
	public double[] getInput()
	{
		return input;
	}
	public void setInput(double[] input)
	{
		this.input = input;
	}
	public double[] getOutput()
	{
		return output;
	}
	public void setOutput(double[] output)
	{
		this.output = output;
	}

}
