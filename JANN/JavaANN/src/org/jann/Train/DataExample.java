package org.jann.Train;

public class DataExample {
	private double[] values;
	private String target; 
	
	public double[] getValues() {
		return values;
	}


	public String getTarget() {
		return target;
	}

	
	public DataExample(double[] values, String target){
		this.target = target;
		this.values = values;
	}
}
