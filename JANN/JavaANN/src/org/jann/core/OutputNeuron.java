package org.jann.core;

import org.jann.core.activationfunction.ActivationFunction_Type;
import org.jann.core.activationfunction.Sigmoid;
import org.jann.core.activationfunction.Softmax;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class OutputNeuron extends Neuron {
	
	public String classification;
	
	public OutputNeuron(String classification, int id){
		super(id);
		this.classification = classification;
		this.activationFunc = ActivationFunction_Type.SOFTMAX;
	}
	
	
	public void process(Layer parentLayer) {
		preActivation();
		activation(parentLayer);
	}

	
	private void activation(Layer parentLayer) {
		
		switch (activationFunc) {
		case SIGMOID:
			activation = new Sigmoid().compute(preActivation);
			break;
		case SOFTMAX:
			activation= new Softmax().compute(parentLayer, preActivation);
			break;
		default:
			new Sigmoid().compute(preActivation);
			break;
		}
		
	}
	
	
	
	@Override
	public void computeDelta(String target) {
		
		double selfDerivative = 0;
		
		switch (activationFunc) {
		
		case SOFTMAX:
			selfDerivative = new Softmax().getDerivative(this, target);
		default:
			break;
		}

		delta = selfDerivative ;

	}
	
	@Override
	public JsonElement write(){
		
		JsonObject jsonNeuron = new JsonObject();
		jsonNeuron.addProperty("id", id);
		jsonNeuron.addProperty("activationFunc", activationFunc.toString());
		jsonNeuron.addProperty("classification", classification);
		
		JsonArray arrInputVect = new JsonArray();
		JsonArray arrOutputVect = new JsonArray();
		
		for(Link inLink : inputVector){
			arrInputVect.add(inLink.write());
		}
		
		for(Link outLink : outputVector){
			arrOutputVect.add(outLink.write());
		}
		
		jsonNeuron.add("inputLinks", arrInputVect);
		jsonNeuron.add("outputLinks", arrOutputVect);
		
		return jsonNeuron;
	}
	
}
