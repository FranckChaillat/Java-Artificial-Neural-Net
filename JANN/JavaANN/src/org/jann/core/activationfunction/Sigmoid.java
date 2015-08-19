package org.jann.core.activationfunction;

public class Sigmoid{


	public double compute(double preActivation) {
		
		return  1/(1+Math.exp(-preActivation));
	}


	public double getDerivative(double activation) {
		return  activation * (1-activation);	
	}
	
	
}
