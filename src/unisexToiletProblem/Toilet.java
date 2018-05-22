package unisexToiletProblem;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;


public class Toilet extends Thread{
	private LinkedList<Person> queueToToilet;
	private int maxOfUsers;
	private int maxTimeOfUse;
	private Semaphore semaphore;
	private Gender inToiletNow;
	private int counterToStarvation;
	private final int maxUserSameGenderInSequence;
	
	Toilet(int maxOfUsers, int maxTimeOfUse, int maxUserSameGenderInSequence){
		//TODO-> try_catch InvalidArgument
		this.maxTimeOfUse = maxTimeOfUse;
		this.maxOfUsers = maxOfUsers;
		inToiletNow = null;
		this.maxUserSameGenderInSequence=maxUserSameGenderInSequence;
		counterToStarvation=maxUserSameGenderInSequence;
		semaphore = new Semaphore(maxOfUsers,true);
		queueToToilet = new LinkedList<>();
	}
	
	public boolean isEmpty() {
		return semaphore.availablePermits()==maxOfUsers;
	}
	
	//TODO ATENTION -> Try_Catch
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
//				try {
//					person.join();//FIXME isso vai travar a execução para esperar a thread?
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				queueToToilet.remove(person);
			} else {
				if(isEmpty()) inToiletNow = null;
			} 
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void resetCounter() {
		counterToStarvation = maxUserSameGenderInSequence;
	}
	
	private void decreaseCounter() {
		counterToStarvation--;
	}
	
	private boolean checkCounter() {
		return counterToStarvation>1;
	}
	
	public Person getNextInQueue() {
		//FIXME starvation 
		if(inToiletNow != null && checkCounter()) {
			decreaseCounter();
			//System.out.println(counterToStarvation);
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
	
	private Person getNextInQueueByGender(Gender gender) {
		for(Person p : queueToToilet) {
			if(p.getGender() == gender) return p;
		}
		return null;
	}

	public void addToQueue(Person person) {
		person.setTimeToUse(ThreadLocalRandom.current().nextInt(1,maxTimeOfUse+1));
		queueToToilet.addLast(person);
	}

	public void viewQueue() {
		String queue = "QUEUE =  [";
		for(Person person : queueToToilet) {
			if(person.getGender() == Gender.MALE) queue += "M";
			else queue += "F";
		}
		queue += "]";
		System.out.println(queue);
	}
	
	public void viewToilet(int availablePermits) {
		String inToilet = "TOILET = [";
		for(int i=maxOfUsers; i>availablePermits; i--) {
			if(inToiletNow == Gender.MALE) inToilet += "M";
			else inToilet += "F";
		}
		inToilet += "]";
		System.out.println(inToilet);
	}
	
	public Queue<Person> getQueueToToilet() {
		return queueToToilet;
	}
	
	public Semaphore getSemaphore() {
		return semaphore;
	}
	
	public int getMaxTimeOfUse() {
		return maxTimeOfUse;
	}

	public int getMaxOfUsers() {
		return maxOfUsers;
	}

	public void setMaxOfUsers(int maxOfUsers) {
		this.maxOfUsers = maxOfUsers;
	}
}
