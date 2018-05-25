package unisexToiletProblem;

//USAR NESSE PROBLEMA: SEMAFOROS
//USAR NO OUTRO PROBLEMA: BLOQUEIOS EXPLICITOS

/**
 * @author luanpereira
 * Main class
 */
public class Main { 
	
	/**
	 * Param to define how much users could use toilet at same time
	 */
	public static final int MAX_USERS_IN_TOILET = 3; 
	
	/**
	 * Param to define how much time (max time) could each user use  
	 */
	public static final int MAX_TIME_PER_USERS = 10;
	
	/**
	 * Param to define how much users which same gender could join toilet before force a gender change 
	 */
	public static final int MAX_USER_SAME_GENDER_IN_SEQUENCE = 3;
	
	
	/**
	 * Param do define how much threads this simulation will create
	 */
	public static final int THREADS_TO_CREATE = 20;
	
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		
		Toilet t = new Toilet(MAX_USERS_IN_TOILET,MAX_TIME_PER_USERS, MAX_USER_SAME_GENDER_IN_SEQUENCE);
		
		for(int i=0; i<THREADS_TO_CREATE; i++) {
			t.addToQueue(new Person(Gender.randomGender()));	
		}
		t.viewQueue();
		t.start();
		
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}


