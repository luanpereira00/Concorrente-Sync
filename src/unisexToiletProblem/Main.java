package unisexToiletProblem;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello toilet");
		Toilet t = new Toilet(5,10);
		for(int i=0; i<10; i++) 
			t.addToQueue(new Person(Gender.randomGender()));	
		t.viewQueue();
	}

}


