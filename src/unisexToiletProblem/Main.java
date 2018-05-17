package unisexToiletProblem;

//USAR NESSE PROBLEMA: SEMAFOROS
//USAR NO OUTRO PROBLEMA: BLOQUEIOS EXPLICITOS

public class Main { 
	
	public static final int MAX_USERS_IN_TOILET = 5; 
	public static final int MAX_TIME_PER_USERS = 10;
	public static final int THREADS_TO_CREATE = 10;
	
	public static void main(String[] args) {
		Toilet t = new Toilet(MAX_USERS_IN_TOILET,MAX_TIME_PER_USERS);
		for(int i=0; i<THREADS_TO_CREATE; i++) 
			t.addToQueue(new Person(Gender.randomGender()));	
		t.viewQueue();
	}

}


