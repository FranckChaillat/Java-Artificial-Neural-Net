package org.jann.Train;

import java.util.ArrayList;

public class TrainingDataSet {
	public ArrayList<DataExample> dataset;
	public int errorCount;
	
	public TrainingDataSet(){
		errorCount = 1;
		dataset = new ArrayList<DataExample>();
	}
	
	

}
