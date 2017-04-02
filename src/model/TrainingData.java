package model;

public class TrainingData
{
	public Double[] input;
	public Double[] output;
	public TrainingData(Double[] input, Double[] output)
	{
		super();
		this.input = input;
		this.output = output;
	}
	public Double[] getInput()
	{
		return input;
	}
	public void setInput(Double[] input)
	{
		this.input = input;
	}
	public Double[] getOutput()
	{
		return output;
	}
	public void setOutput(Double[] output)
	{
		this.output = output;
	}

}
