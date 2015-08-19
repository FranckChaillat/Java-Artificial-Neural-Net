import org.jann.Train.DataExample;
import org.jann.Train.Train;
import org.jann.Train.TrainingDataSet;


public class inputTest {

	
	
	public static void main(String[] args) {
		
		TrainingDataSet ds = new TrainingDataSet();
		ds.dataset.add( new DataExample(new double[]{1,5}, "0"));
		ds.dataset.add( new DataExample(new double[]{4,2}, "0"));
		ds.dataset.add( new DataExample(new double[]{6,2}, "1"));
		ds.dataset.add( new DataExample(new double[]{10,10}, "2"));

		
		Train t = new Train();
	    t.doTrain(ds, ds);
	  
	    
	    
	}
	
}
