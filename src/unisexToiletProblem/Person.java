package unisexToiletProblem;

import java.util.concurrent.Semaphore;

public class Person extends Thread {
	private Gender gender;
	private Integer timeToUse;
	private Semaphore semaphore;
	
	public Person(Gender gender){
		this.gender = gender;
		timeToUse = null;
		semaphore = null;
	}

	public void setSemaphore(Semaphore semaphore) {
		this.semaphore = semaphore;
	}
	
	public Gender getGender() {
		return gender;
	}

	public Integer getTimeToUse() {
		return timeToUse;
	}

	public void setTimeToUse(Integer time_to_use) {
		this.timeToUse = time_to_use;
	}
	
	public void run(){
		System.out.println(this.getName()+" ("+gender+") entering WC");
		try {
			Thread.sleep(timeToUse*100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println(this.getName()+" leaving WC");
		semaphore.release();
    }
}
