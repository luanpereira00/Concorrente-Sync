package unisexToiletProblem;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;


public class Toilet extends Thread{
	//private LinkedList<ArrayList<Person>> queueToToilet;
	private LinkedList<Person> queueToToilet;
	private int maxOfUsers;
	private int maxTimeOfUse;
	private Semaphore semaphore;
	private Gender inToiletNow;
	
	Toilet(int maxOfUsers, int maxTimeOfUse){
		//TODO-> try_catch InvalidArgument
		this.maxTimeOfUse = maxTimeOfUse;
		this.maxOfUsers = maxOfUsers;
		inToiletNow = null;
		
		semaphore = new Semaphore(maxOfUsers,true);
		queueToToilet = new LinkedList<>();
	}
	
	//TODO ATENTION -> Try_Catch
	public void run() {
		Person person;
		viewQueue();
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
				
				System.out.println("=============================================");
				
				viewToilet(semaphore.availablePermits());
				System.out.println("=============================================");
			} else inToiletNow = null;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
