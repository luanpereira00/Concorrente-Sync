package concurrent_linked_list;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author luanpereira
 * A class that defines concurrent linked list
 * @param <E> Type of list's element
 */
public class ConcLinkedList<E> {
	private Lock lockRemove; 
	private Lock lockInsert;
	private Condition remove;
	private LinkedList<E> linkedList;
		
	/**
	 * Constructor for concurrent linked list
	 */
	public ConcLinkedList() {
		linkedList = new LinkedList<E>();
		lockRemove = new ReentrantLock();
		lockInsert = new ReentrantLock();
		remove = lockRemove.newCondition();
	}
	
	/**
	 * Method to insert an element on list
	 * @param element the element to be inserted
	 */
	public void insert(E element) {
		/**
		 * @author luanpereira
		 * Anonymous class for a thread
		 */
		Thread t = new Thread() {
			public void run() {
				lockInsert.lock();
				System.out.println("--- starting to insert");
				try {
					try_sleep(200);
					linkedList.add(element);
					System.out.println(element + " inserted succesfully");
				} finally {
					System.out.println("--- finishing to insert");
					lockInsert.unlock();
				}
				
			}
		};
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to remove an element on list
	 * @param element element to be removed on list
	 */
	public void remove(E element) {
		/**
		 * @author luanpereira
		 * Anonymous class for a thread
		 */
		Thread t = new Thread() {
			public void run() {
				synchronized (remove) {
					lockRemove.lock();
					lockInsert.lock();
					System.out.println("----- starting to remove");
					try {
						try_sleep(500);
						linkedList.remove(element);
						System.out.println(element + " removed succesfully");
					} finally {
						System.out.println("----- finishing to remove");
						lockInsert.unlock();
						lockRemove.unlock();
					}
				}
			}
		};
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to search an element on list
	 * @param element element to be searched
	 * @return true if element is on list, false if not
	 */
	public boolean search(E element) {
		final AtomicBoolean result = new AtomicBoolean();
		/**
		 * @author luanpereira
		 * Anonymous class for a thread
		 */
		Thread t = new Thread() {
			public void run() {
				synchronized (remove) {
					System.out.println("- starting to search");
					result.set(linkedList.contains(element));
					try_sleep(700);
					System.out.println("- finishing to search");
				}
			}
		};
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(result.get()) {
			System.out.println(element + " founded");
		}else {
			System.out.println(element + " not founded");
		}
		return result.get();
	}
	
	/**
	 * Method only to try apply sleep on others methods
	 * @param time time to sleep
	 */
	void try_sleep(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
