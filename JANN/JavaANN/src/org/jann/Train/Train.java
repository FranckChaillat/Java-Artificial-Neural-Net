package org.jann.Train;


import java.util.Hashtable;

import org.jann.guess.GuessSession;

public class Train {
	
	private GuessSession session;
	
	public void doTrain(TrainingDataSet dataSet, TrainingDataSet testSet){
		
		try {
			checkDataSet(dataSet);
		} catch (DataExampleFormatExcpetion | DataSetException e) {
			e.printStackTrace();
		}
		this.session = new GuessSession();
		
	
		for(int i =0; dataSet.errorCount !=0; i++){
			dataSet.errorCount=0;
			for(DataExample ex : dataSet.dataset){
				Hashtable<String, String> results = session.submit(ex.getValues());
				String classification = results.get("class");
				double rate = Double.valueOf(results.get("rate"));
				
				System.out.println("the output is "+ classification+ " and the expected is "+ ex.getTarget());
				if( !classification.equals(ex.getTarget())||(classification == ex.getTarget())&&(rate <0.7)){
					dataSet.errorCount++;
					session.train(ex.getTarget());
				}
				
			}
			
			System.out.println("SESSION NB: "+i+", ERROR COUNTER : "+dataSet.errorCount);
			System.out.println();
		}
		doTest(testSet);
		session.getNet().WriteNet("C:/Users/f.chaillat/Documents/JANN/JavaANN/network.json");
		
		
	}
	
	private void doTest(TrainingDataSet testSet){
		System.out.println("TEST BEGUIN");
		
		testSet.errorCount = 0;
		for(DataExample ex : testSet.dataset){
		
			Hashtable<String, String> results = session.submit(ex.getValues());
			String classification = results.get("class");
			double rate = Double.valueOf(results.get("rate"));
				
				System.out.println("the output is "+ classification+ " and the expected is "+ ex.getTarget());
				if(!classification.equals(ex.getTarget())){
					testSet.errorCount++;
				}
				
			}
		
		System.out.println(testSet.errorCount+" erreur(s) trouvée(s) sur l'ensemble de test");
		
	}
	
	private void checkDataSet(TrainingDataSet dataSet) throws DataExampleFormatExcpetion, DataSetException{
		if(dataSet.dataset.isEmpty()){
			throw new DataSetException();
		}
		int refLenght = dataSet.dataset.get(0).getValues().length;
		
		for(DataExample ex: dataSet.dataset){
			if(refLenght != ex.getValues().length){
				throw new DataExampleFormatExcpetion();
			}
		}
		
	}
	

}
