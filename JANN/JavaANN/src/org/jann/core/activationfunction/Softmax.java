package org.jann.core.activationfunction;

import org.jann.core.Layer;
import org.jann.core.Neuron;
import org.jann.core.OutputNeuron;

public class Softmax {
	
	public double compute(Layer parentLayer, double preActivation ) {
		
		
		double total = 0.0;
		for(Neuron n : parentLayer.getNeurons()){
			total += Math.exp(n.getPreActivation());
		}
		
		return Math.exp(preActivation)/total;
	}
	
	public double getDerivative(OutputNeuron n, String target){
		
		
		
		/*if(n.classification != target){
			return -(1.0 - n.getActivation());
		}else
			return -(0.0 -  n.getActivation());*/
		
		if(n.classification.equals(target)){
			return 1.0 - n.getActivation();
		}else
			return 0.0 -  n.getActivation();
	}
}
