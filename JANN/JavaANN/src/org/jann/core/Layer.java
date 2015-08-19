package org.jann.core;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Layer {

	
	private ArrayList<Neuron> neurons;
	private int id;
	private boolean isOutputLayer;
	private  Neuron biasNeuron;
	private double biasWeight;

	public Layer(ArrayList<Neuron> neurons, int id, boolean isOutput){
		biasNeuron = new Neuron(0);
		biasNeuron.setActivation(1);
		isOutputLayer= isOutput;
		this.id = id;
		this.neurons = new ArrayList<Neuron>();
		for(Neuron n : neurons){
			appendNeuron(n);
		}
		
	}
	
	public Layer(int id, boolean isOutput){
		biasNeuron = new Neuron(0);
		biasNeuron.setActivation(1);
		isOutputLayer= isOutput;
		this.id = id;
		neurons = new ArrayList<Neuron>();
	}
	
	public Layer(int nb, int id, boolean isOutput){
		biasNeuron = new Neuron(0);
		biasNeuron.setActivation(1);
		isOutputLayer= isOutput;
		this.id = id;
		neurons = new ArrayList<Neuron>();
		for(int i=1; i<=nb;i++){
			appendNeuron(new Neuron(i));
		}
	}
	
	public Neuron getBiasNeuron() {
		return biasNeuron;
	}
	
	public void setBiasNeuron(Neuron biasN){
		this.biasNeuron = biasN;
	}


	public void setBias(double biasWeight) {
		this.biasWeight = biasWeight;
	}

	
	public double getBias() {
		return biasWeight;
	}
	
	public int getId() {
		return id;
	}


	public boolean isOutputLayer(){
		return isOutputLayer;
	}
	
	public ArrayList<Neuron> getNeurons() {
		return neurons;
	}
	
	public void appendNeuron(Neuron n){
		neurons.add(n);
	}
	
	public void appendNeuron(ArrayList<Neuron> neurons){
		neurons.forEach(x->appendNeuron(x));
	}
	
	public void resolveInputCo(Layer previousLayer){
		
		for(Neuron prev_N : previousLayer.getNeurons()){
			
			for(Neuron curr_N : this.getNeurons()){
				Link link = new Link(prev_N, curr_N, 0.1);
				prev_N.addOutputLink(link);
				curr_N.addInputLink(link);
				
			}
		}
		
		resolveBias(previousLayer);
	}
	
	
	private void resolveBias(Layer previousLayer) {
		
		
		for(Neuron n : this.neurons){
			Link bias = new Link(previousLayer.getBiasNeuron(), n, previousLayer.biasWeight);
			previousLayer.getBiasNeuron().addOutputLink(bias);
			n.addInputLink(bias);
		}
		
	}

	public JsonElement write(){
		
		JsonObject jsonLayer = new JsonObject();
		jsonLayer.addProperty("id", id);
		jsonLayer.addProperty("isOutputLayer", isOutputLayer);
		jsonLayer.addProperty("biasWeight", biasWeight);
		jsonLayer.add("biasNeuron", biasNeuron.write());
		JsonArray neuronArr = new JsonArray();
		
		for(Neuron n : neurons){
			neuronArr.add(n.write());
		}
		
		jsonLayer.addProperty("id", id);
		jsonLayer.add("neurons", neuronArr);
		
		
		return jsonLayer;
	}
	
}
