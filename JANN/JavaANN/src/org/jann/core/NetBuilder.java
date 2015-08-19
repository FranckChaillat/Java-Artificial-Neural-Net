package org.jann.core;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class NetBuilder {
	
	public Network build(String pathFile) throws IOException {
		
		FileReader reader = null;
		reader = new FileReader(pathFile);
		
		reader.close();
		reader = new FileReader(pathFile);
		
		JsonObject parsedNet = (JsonObject) new JsonParser().parse(reader);
		ArrayList<Layer> layers = (ArrayList<Layer>) getLayers(parsedNet);
		
		Network net = new Network();
		net.appendAllLayer(layers);
		
		for(Layer l : net.getLayers()){
			
			for(Neuron n : l.getNeurons()){
				
				for(int i=0; (n.outputVector() != null)&&(i<n.outputVector().size());i++){
					
					for(JsonElement e : resolveLink(parsedNet, l.getId(), n.getId())){
						
						if(n.outputVector().get(i).getOutput().getId() == e.getAsJsonObject().getAsJsonPrimitive("output").getAsInt())
							n.outputVector().get(i).setWeight(e.getAsJsonObject().getAsJsonPrimitive("weight").getAsDouble());
							
					}
				}
			}
		}
		return net;
		 
	}
	
	private List<Layer> getLayers(JsonObject parsedNet){
		
		List<Layer> layers = new ArrayList<Layer>();
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		
		JsonArray parsedLayers = parsedNet.getAsJsonArray("layers");
		for(int i=0; i< parsedLayers.size(); i++){
			
			boolean isout = parsedLayers.get(i).getAsJsonObject().getAsJsonPrimitive("isOutputLayer").getAsBoolean();
			Layer l = new Layer(i, isout);
			for(JsonElement neuron : parsedLayers.get(i).getAsJsonObject().getAsJsonArray("neurons")){
				
				if(l.isOutputLayer())
					l.appendNeuron(gson.fromJson(neuron, OutputNeuron.class));
				else
					l.appendNeuron(gson.fromJson(neuron, Neuron.class));
			}
			
			JsonObject parsedBiasN = parsedLayers.get(i).getAsJsonObject();
			l.setBiasNeuron(gson.fromJson(parsedBiasN, Neuron.class));
			layers.add(l);
		}
		
		return layers;
	}
	

	
	private JsonArray resolveLink(JsonObject parsedNet, int layerId,int neuronId){
		
		
		JsonArray layers = parsedNet.getAsJsonArray("layers");
		JsonArray outputVector = null;
		
		for(final JsonElement layer : layers){
			
			if(layer.getAsJsonObject().getAsJsonPrimitive("id").getAsInt() !=  layerId)
				continue;
			
			JsonArray neurons = layer.getAsJsonObject().getAsJsonArray("neurons");
			
			for(JsonElement neuron : neurons){
				
				if(neuron.getAsJsonObject().getAsJsonPrimitive("id").getAsInt() !=  neuronId)
					continue;
				
				outputVector = neuron.getAsJsonObject().getAsJsonArray("outputLinks");
				return outputVector;
			}
			
		}
		return outputVector;
	}
	
}
