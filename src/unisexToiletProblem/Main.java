package unisexToiletProblem;

//USAR NESSE PROBLEMA: SEMAFOROS
//USAR NO OUTRO PROBLEMA: BLOQUEIOS EXPLICITOS

public class Main { 
	
	public static final int MAX_USERS_IN_TOILET = 3; 
	public static final int MAX_TIME_PER_USERS = 10;
	public static final int THREADS_TO_CREATE = 20;
	
	public static void main(String[] args) {
		
		Toilet t = new Toilet(MAX_USERS_IN_TOILET,MAX_TIME_PER_USERS);
		t.start();
		for(int i=0; i<THREADS_TO_CREATE; i++) {
			t.addToQueue(new Person(Gender.randomGender()));	
			if(i%5==0) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}


