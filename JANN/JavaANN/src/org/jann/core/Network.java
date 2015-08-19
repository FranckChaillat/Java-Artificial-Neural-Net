package org.jann.core;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class Network {
	
	private double learningRate = 0.065;
	private ArrayList<Layer> layers;
	private int nbOfLayer;

	public Network(){
		this.layers = new ArrayList<Layer>();
		nbOfLayer =0;
	}
	
	
	public int getNbOfLayer() {
		return nbOfLayer;
	}


	public Network(ArrayList<Layer> layers){
		this.layers = new ArrayList<Layer>();
		nbOfLayer =0;
	}
	
	public ArrayList<Layer> getLayers() {
		return layers;
	}

	
	public double getLearningRate() {
		return learningRate;
	}

	
	public Layer getOutputLayer(){
		return layers.get(nbOfLayer-1);
	}
	
	public Layer getInputLayer(){
		return layers.get(0);
	}
	
	public void appendLayer(Layer l){
		layers.add(l);
		if(nbOfLayer>0){
			Layer prevLayer = layers.get(nbOfLayer-1);
			l.resolveInputCo(prevLayer);
		}
		nbOfLayer++;
	}
	
	public void appendAllLayer(ArrayList<Layer> layers){
		layers.forEach((l)->appendLayer(l));
	}
	
	
	public void initWeights(double start, double end){
		Random randGen = new Random();
		
		for(Layer l : layers){
			for(Neuron n : l.getNeurons()){
				
				for(Link lnk : n.outputVector()){
					double result = start +(randGen.nextDouble() *(end-start));
					lnk.setWeight(result);
				}
			}
			double result = 0 +(randGen.nextDouble() *(0.1-0));
			l.setBias(result);
		}
		
	}
	
	public Hashtable<String, String> compute(){
		
		for(int i=1; i<nbOfLayer;i++){
			for(Neuron n : layers.get(i).getNeurons()){
				
				if(n instanceof OutputNeuron){
					OutputNeuron outN = (OutputNeuron)n;
					outN.process(layers.get(i));
				}else
					n.process();
			}
		}
		return decision();
		
	}
	
	public void backPropagation(String target){
		
		for(int i=nbOfLayer-1; i>0; i--){
			computeGradient(layers.get(i), target);
		}
		
	}
	
	public void weightUpdate(){
		
		for(Layer l : layers){
			for(Neuron n : l.getNeurons()){
				
				for(Link lnk : n.outputVector()){
					double outPutN_delta = lnk.getOutput().getDelta();
					double inputN_activation = lnk.getInput().getActivation();
					double newWeight = lnk.getWeight() - (learningRate * (-outPutN_delta)*inputN_activation);
					lnk.setWeight(newWeight);
				}
			}
		}
		
		biasUpdate();
		
	}
	
	private void biasUpdate() {
		
		for(Layer l : layers){
			
			for(Link bias : l.getBiasNeuron().outputVector()){
				double outPutN_delta = bias.getOutput().getDelta();
				double inputN_activation = bias.getInput().getActivation();
				double newWeight = bias.getWeight() - (learningRate * (-outPutN_delta)*inputN_activation);
				bias.setWeight(newWeight);
			}
		}
		
	}


	private void computeGradient(Layer currentLayer, String target){
		
		for(Neuron currNeuron : currentLayer.getNeurons()){
				currNeuron.computeDelta(target);
		}
		
	}
	
	private Hashtable<String, String> decision(){
		
		Layer outputLayer = getOutputLayer();
		OutputNeuron winner = (OutputNeuron) outputLayer.getNeurons().get(0);
		
		for(Neuron outNeuron : outputLayer.getNeurons()){
			if(outNeuron.getActivation() > winner.activation){
				winner = (OutputNeuron)outNeuron;
			}	
		}
		
		System.out.println("the winner's activation worth "+winner.getActivation()+" for class "+winner.classification);
		Hashtable<String, String> result = new Hashtable<String,String>();
		result.put("class", winner.classification);
		result.put("rate", String.valueOf(winner.activation));
		return result;
		
	}
	
	public void WriteNet(String path){
		
		JsonObject jsonNetwork = new JsonObject();
		jsonNetwork.addProperty("learningRate", learningRate);
		
		JsonArray layerArr = new JsonArray();
		for(Layer l : layers){
			layerArr.add(l.write());
		}
		
		jsonNetwork.add("layers", layerArr);
		jsonNetwork.addProperty("nbOfLayer", nbOfLayer);
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		
		System.out.println(gson.toJson(jsonNetwork));
		
		try {
			
			FileWriter file = new FileWriter(path);
			file.write(gson.toJson(jsonNetwork));
			file.flush();
			file.close();
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}


	
	
	
}
