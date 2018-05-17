package unisexToiletProblem;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Toilet {
	private LinkedList<ArrayList<Person>> queueToToilet;
	private int maxOfUsers;
	private int maxTimeOfUse;
	
	Toilet(int maxOfUsers, int maxTimeOfUse){
		//TO_DO-> try_catch InvalidArgument
		this.maxTimeOfUse = maxTimeOfUse;
		this.maxOfUsers = maxOfUsers;
		queueToToilet = new LinkedList<>();
	}

	public Queue<ArrayList<Person>> getQueueToToilet() {
		return queueToToilet;
	}

//	public void setQueueToToilet(Queue<ArrayList<Person>> queueToToilet) {
//		this.queueToToilet = queueToToilet;
//	}

	public int getMaxTimeOfUse() {
		return maxTimeOfUse;
	}

	public void setMaxTimeOfUse(int maxTimeOfUse) {
		this.maxTimeOfUse = maxTimeOfUse;
	}

	public int getMaxOfUsers() {
		return maxOfUsers;
	}

	public void setMaxOfUsers(int maxOfUsers) {
		this.maxOfUsers = maxOfUsers;
	}
	
	public void addToQueue(Person person) {
		if(!queueToToilet.isEmpty()) {
			ArrayList<Person> last = queueToToilet.getLast();		
			if(last.get(0).getGender() == person.getGender()) {
				if(last.size()<maxOfUsers) last.add(person);
				else addNewOnQueue(person);	
			} else addNewOnQueue(person);
		}else addNewOnQueue(person);
	}

	private void addNewOnQueue(Person person) {
		ArrayList<Person> p = new ArrayList<>();
		p.add(person);
		queueToToilet.addLast(p);
	}

	public void viewQueue() {
		String queue = "[";
		for(ArrayList<Person> array : queueToToilet) {
			queue += "(";
			for(Person person : array) {
				if(person.getGender() == Gender.MALE) queue += "M";
				else queue += "F";
			}
			queue += ")";
		}
		queue += "]";
		System.out.println(queue);
		
	}
}
