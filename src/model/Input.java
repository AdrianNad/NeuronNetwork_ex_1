package model;

public class Input extends Connection
{

	private double input;
	@Override
	public double getOutput()
	{
		return input;
	}
	public Input(double input)
	{
		super();
		this.input = input;
	}
	public Input()
	{

	}
}
