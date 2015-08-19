package org.jann.core;


import java.util.ArrayList;
import org.jann.core.activationfunction.ActivationFunction_Type;
import org.jann.core.activationfunction.Sigmoid;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Neuron {

	protected ArrayList<Link> outputVector;
	protected ArrayList<Link> inputVector;
	protected double preActivation;
	protected double activation;
	protected ActivationFunction_Type activationFunc;
	protected double delta;
	protected int id;


	public Neuron(int id){
		this.activationFunc = ActivationFunction_Type.SIGMOID;
		outputVector = new ArrayList<Link>();
		inputVector = new ArrayList<Link>();
		this.id = id;
	}

	
	public int getId() {
		return id;
	}

	
	public double getDelta() {
		return delta;
	}
	
	public void setDelta(double delta) {
		this.delta = delta;
	}


	public double getActivation() {
		return activation;
	}

	public void setActivation(double activation) {
		this.activation = activation;
	}
	
	public double getPreActivation() {
		return preActivation;
	}


	public void addInputLink(Link l){
		inputVector = checkVectorInstanciation(inputVector);
		inputVector.add(l);
	}
	
	public void addOutputLink(Link l){
		outputVector = checkVectorInstanciation(outputVector);
		outputVector.add(l);
	}
	
	public ArrayList<Link> outputVector() {
		outputVector = checkVectorInstanciation(outputVector);
		return outputVector;
	}

	public void process() {
		preActivation();
		activation();
	}

	private ArrayList<Link> checkVectorInstanciation(ArrayList<Link> vector){
		
		if(vector == null){
			vector = new ArrayList<Link>();
		}
		return vector;
		
	}
	private void activation() {
		
		switch (activationFunc) {
		case SIGMOID:
			activation = new Sigmoid().compute(preActivation);
			break;
		default:
			new Sigmoid().compute(preActivation);
			break;
		}
		
	}


	protected void preActivation() {
		
		double weightedSum = 0;
		
		for(Link l : inputVector){
			weightedSum += l.getWeightedInput();
		}
		
		this.preActivation = weightedSum;
	}

	public void computeDelta(String target) {
	
		double selfDerivative = 0;
		
		switch (activationFunc) {
		case SIGMOID:
			selfDerivative = new Sigmoid().getDerivative(activation);
			break;
		default:
			selfDerivative = new Sigmoid().getDerivative(activation);
			break;
		}
		
		double weighted_deltaSum = 0;
		
		for(Link l : outputVector)
			weighted_deltaSum += l.getOutput().getDelta() * l.getWeight();
		
		delta = selfDerivative * weighted_deltaSum;
	}
	
	public JsonElement write(){
		
		JsonObject jsonNeuron = new JsonObject();
		jsonNeuron.addProperty("id", id);
		jsonNeuron.addProperty("activationFunc", activationFunc.toString());
		
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
