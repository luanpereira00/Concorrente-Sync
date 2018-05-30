package unisexToiletProblem;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;


/**
 * @author luanpereira
 * Class that simulates a toilet
 */
public class Toilet extends Thread{
	private LinkedList<Person> queueToToilet;
	private int maxOfUsers;
	private int maxTimeOfUse;
	private Semaphore semaphore;
	private Gender inToiletNow;
	private int counterToStarvation;
	private final int maxUserSameGenderInSequence;
	
	/**
	 * Constructor for toilet
	 * @param maxOfUsers Param to define max of users that toilet could have at same time
	 * @param maxTimeOfUse Param to define max time each user could use
	 * @param maxUserSameGenderInSequence Param to define a counter to starvation
	 */
	Toilet(int maxOfUsers, int maxTimeOfUse, int maxUserSameGenderInSequence){
		if(maxOfUsers<=0 || maxTimeOfUse<=0 || maxUserSameGenderInSequence<=0) throw new IllegalArgumentException();
		this.maxTimeOfUse = maxTimeOfUse;
		this.maxOfUsers = maxOfUsers;
		this.maxUserSameGenderInSequence=maxUserSameGenderInSequence;
		counterToStarvation=maxUserSameGenderInSequence;
		
		inToiletNow = null;
		queueToToilet = new LinkedList<>();
		semaphore = new Semaphore(maxOfUsers,true);
	}
	
	/**
	 * Method to see if toilet is empty
	 * @return true if is empty and false if not
	 */
	public boolean isEmpty() {
		return semaphore.availablePermits()==maxOfUsers;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		Person person;
		while(true) {
			person = getNextInQueue();
			if(person != null) {
				inToiletNow = person.getGender();
				try {
					semaphore.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				person.setSemaphore(semaphore);
				person.start();
				queueToToilet.remove(person);
			} else {
				if(isEmpty()) inToiletNow = null;
			} 
		}
	}

	/**
	 * Method to reset counter to starvation
	 */
	private void resetCounter() {
		counterToStarvation = maxUserSameGenderInSequence;
	}
	
	/**
	 * Method to decrease var that is counter to starvation
	 */
	private void decreaseCounter() {
		counterToStarvation--;
	}
	
	/**
	 * Check counter to starvation
	 * @return true if starvation may be occuring, false if not
	 */
	private boolean checkCounter() {
		return counterToStarvation>1;
	}
	
	/**
	 * Get next in queue (not exactly next in queue, it is more like a scheduler to toilet)
	 * @return the next to toilet
	 */
	public Person getNextInQueue() {	
		if(inToiletNow != null && checkCounter()) {
			decreaseCounter();
			switch(inToiletNow) {
			case MALE:
				return getNextInQueueByGender(Gender.MALE);
			case FEMALE:
				return getNextInQueueByGender(Gender.FEMALE);
			}
		} else {
			if(!queueToToilet.isEmpty()) {
				if(queueToToilet.getFirst().getGender() == inToiletNow || isEmpty())
				{
					resetCounter();
					return queueToToilet.getFirst();
				}
			}
		}
		return null;
	}
	
	/**
	 * Method to get next in queue that has same gender in argument
	 * @param gender the gender to search
	 * @return the next
	 */
	private Person getNextInQueueByGender(Gender gender) {
		for(Person p : queueToToilet) {
			if(p.getGender() == gender) return p;
		}
		return null;
	}

	/**
	 * Method to add next person in queue
	 * @param person the person
	 */
	public void addToQueue(Person person) {
		person.setTimeToUse(ThreadLocalRandom.current().nextInt(1,maxTimeOfUse+1));
		queueToToilet.addLast(person);
	}

	/**
	 * Method to see queue to toilet, only for tests
	 */
	public void viewQueue() {
		String queue = "QUEUE =  [";
		for(Person person : queueToToilet) {
			if(person.getGender() == Gender.MALE) queue += "M";
			else queue += "F";
		}
		queue += "]";
		System.out.println(queue);
	}
	
	/**
	 * Method to see toilet, only for tests
	 * @param availablePermits 
	 */
	public void viewToilet(int availablePermits) {
		String inToilet = "TOILET = [";
		for(int i=maxOfUsers; i>availablePermits; i--) {
			if(inToiletNow == Gender.MALE) inToilet += "M";
			else inToilet += "F";
		}
		inToilet += "]";
		System.out.println(inToilet);
	}
	
//	public Queue<Person> getQueueToToilet() {
//		return queueToToilet;
//	}
	
//	public Semaphore getSemaphore() {
//		return semaphore;
//	}
	
//	public int getMaxTimeOfUse() {
//		return maxTimeOfUse;
//	}

//	public int getMaxOfUsers() {
//		return maxOfUsers;
//	}

//	public void setMaxOfUsers(int maxOfUsers) {
//		this.maxOfUsers = maxOfUsers;
//	}
}
