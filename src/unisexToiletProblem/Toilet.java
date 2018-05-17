package unisexToiletProblem;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Toilet {
	//private LinkedList<ArrayList<Person>> queueToToilet;
	private LinkedList<Person> queueToToilet;
	private int maxOfUsers;
	private int maxTimeOfUse;
	private Semaphore semaphore;
	private Gender inToiletNow;
	
	Toilet(int maxOfUsers, int maxTimeOfUse){
		//TO_DO-> try_catch InvalidArgument
		this.maxTimeOfUse = maxTimeOfUse;
		this.maxOfUsers = maxOfUsers;
		inToiletNow = null;
		
		semaphore = new Semaphore(maxOfUsers);
		queueToToilet = new LinkedList<>();
	}
	
	//ATENTION -> Try_Catch
	public void act() throws InterruptedException {
		Person person;
		while(true) {
			//lock
			person = getNextInQueue();
			if(person != null) {
				inToiletNow = person.getGender();
				//person.act();
			}
			//unlock
			Thread.sleep(5);
		}
	}
	
	public Person getNextInQueue() {
		if(inToiletNow != null) {
			switch(inToiletNow) {
			case MALE:
				return getNextInQueueByGender(Gender.MALE);
			case FEMALE:
				return getNextInQueueByGender(Gender.FEMALE);
			}
		} else {
			if(!queueToToilet.isEmpty()) return queueToToilet.getFirst();
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
		//person.setTimeToUse(maxTimeOfUse);
		queueToToilet.addLast(person);
	}

	public void viewQueue() {
		String queue = "[";
		for(Person person : queueToToilet) {
			if(person.getGender() == Gender.MALE) queue += "M";
			else queue += "F";
		}
		queue += "]";
		System.out.println(queue);
	}
	
	
	
//	public Queue<ArrayList<Person>> getQueueToToilet() {
//		return queueToToilet;
//	}

//	public void setQueueToToilet(Queue<ArrayList<Person>> queueToToilet) {
//		this.queueToToilet = queueToToilet;
//	}
	public boolean toiletIsEmpty() {
		if(inToiletNow == null) return true;
		else return false;
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
	
//	public void addToQueue(Person person) {
//		if(!queueToToilet.isEmpty()) {
//			ArrayList<Person> last = queueToToilet.getLast();		
//			if(last.get(0).getGender() == person.getGender()) {
//				if(last.size()<maxOfUsers) last.add(person);
//				else addNewOnQueue(person);	
//			} else addNewOnQueue(person);
//		}else addNewOnQueue(person);
//	}
//
//	private void addNewOnQueue(Person person) {
//		ArrayList<Person> p = new ArrayList<>();
//		p.add(person);
//		queueToToilet.addLast(p);
//	}
	
//	public void viewQueue() {
//		String queue = "[";
//		for(ArrayList<Person> array : queueToToilet) {
//			queue += "(";
//			for(Person person : array) {
//				if(person.getGender() == Gender.MALE) queue += "M";
//				else queue += "F";
//			}
//			queue += ")";
//		}
//		queue += "]";
//		System.out.println(queue);
//		
//	}
}
