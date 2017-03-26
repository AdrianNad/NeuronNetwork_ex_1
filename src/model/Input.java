package model;

public class Input extends Connection
{

	private double input;
	@Override
	public double getOutput()
	{
		return input;
	}
	Input(double input)
	{
		super();
		this.input = input;
	}

}
