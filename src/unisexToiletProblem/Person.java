package unisexToiletProblem;

import java.util.concurrent.Semaphore;

/**
 * @author luanpereira
 * A class that simulate a person going to toilet
 */
public class Person extends Thread {
	private Gender gender;
	private Integer timeToUse;
	private Semaphore semaphore;
	
	/**
	 * Default constructor
	 * @param gender the person gender
	 */
	public Person(Gender gender){
		this.gender = gender;
		timeToUse = null;
		semaphore = null;
	}
	
	/**
	 * Accesor to gender
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * Accesor to timeToUse
	 * @return the time
	 */
	public Integer getTimeToUse() {
		return timeToUse;
	}

	/**
	 * Modifier to time to use
	 * @param time_to_use the new time
	 */
	public void setTimeToUse(Integer time_to_use) {
		this.timeToUse = time_to_use;
	}
	
	/**
	 * Modifier to semaphore
	 * @param semaphore the new semaphore
	 */
	public void setSemaphore(Semaphore semaphore) {
		this.semaphore = semaphore;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run(){
		System.out.println(this.getName()+" ("+gender+") entering WC");
		try {
			Thread.sleep(getTimeToUse()*100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println(this.getName()+" leaving WC");
		semaphore.release();
    }
}
