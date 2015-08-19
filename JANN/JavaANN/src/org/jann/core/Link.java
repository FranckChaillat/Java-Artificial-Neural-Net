package org.jann.core;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class Link {
	
	private Neuron input;
	private Neuron output;
	private double weight;
	
	public Link(Neuron in, Neuron out, double w){
		input=in;
		output=out;
		weight = w;
	}
	
	public Link(){
		
	}
	
	public Neuron getInput() {
		return input;
	}

	
	public Neuron getOutput() {
		return output;
	}
	
	public void setWeight(double w) {
		weight = w;
	}
	
	public double getWeight(){
		return weight;
	}

	public double getWeightedInput() {
		
		return input.getActivation() * weight;
		
	}

	public JsonElement write() {
		
		JsonObject jsonLink = new JsonObject();
		jsonLink.addProperty("input", input.getId());
		jsonLink.addProperty("output", output.getId());
		jsonLink.addProperty("weight", weight);
		
		return jsonLink;
	}

}
