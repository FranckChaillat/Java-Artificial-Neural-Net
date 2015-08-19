package org.jann.guess;


import java.io.IOException;
import java.util.Hashtable;

import org.jann.core.InvalidInputVectorException;
import org.jann.core.Layer;
import org.jann.core.NetBuilder;
import org.jann.core.Network;
import org.jann.core.OutputNeuron;


public class GuessSession {
	
	private Network net;

	public GuessSession(){
		net = new Network();
		net.appendLayer(new Layer(96, 0, false));
		net.appendLayer(new Layer(90,1, false));
		net.appendLayer(new Layer(90,2, false));
		/*net.appendLayer(new Layer(2, 0, false));
		net.appendLayer(new Layer(2,1, false));*/
		Layer outputLayer = new Layer(4, true);
		for(int i=0; i<=3;i++){
			outputLayer.appendNeuron(new OutputNeuron(String.valueOf(i), i));
		}
		net.appendLayer(outputLayer);
		net.initWeights(-0.1, 0.1);
	}
	

	public GuessSession(String pathFile){
		NetBuilder builder = new NetBuilder();
		try {
			this.net = builder.build(pathFile);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	
	
	public Network getNet() {
		return net;
	}

	
	public Hashtable<String, String> submit(double[] inputs){
		Layer inputLayer = net.getInputLayer();
		try {
			inputLayer = loadEntries(inputs, inputLayer);
		} catch (InvalidInputVectorException e) {
			e.printStackTrace();
		}
	
		return net.compute();
		
	}
	
	public void train(String target){
		net.backPropagation(target);
		net.weightUpdate();
		
	}
	
	
	private Layer loadEntries(double[] inputs, Layer inputLayer) throws InvalidInputVectorException {
		
		for(int i=0; i<inputLayer.getNeurons().size();i++){
			inputLayer.getNeurons().get(i).setActivation(inputs[i]);
		}
		return inputLayer;
	}
	
	
}
