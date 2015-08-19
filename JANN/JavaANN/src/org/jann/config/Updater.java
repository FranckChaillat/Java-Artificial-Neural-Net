package org.jann.config;

import java.io.File;
import java.io.FileNotFoundException;


public class Updater {

	public void checkNews(){
		
		String patternDir = "C:/Users/f.chaillat/workspace/TestDiagnostic/Sensors/PatternRecognition/Patterns";
		
			File dir = new File(patternDir+"/textBL.json");
			
			for(File pat : dir.listFiles()){
				if(pat.isDirectory()){
					
				}
			}
		
		
	}
}
