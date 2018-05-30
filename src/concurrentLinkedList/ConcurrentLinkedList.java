package concurrentLinkedList;

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
public class ConcurrentLinkedList<E> {
	private Lock lockRemove; 
	private Lock lockInsert;
	private Condition condToRemove;
	private LinkedList<E> linkedList;
	private boolean couldSearch = true;
		
	/**
	 * Constructor for concurrent linked list
	 */
	public ConcurrentLinkedList() {
		linkedList = new LinkedList<E>();
		lockRemove = new ReentrantLock();
		lockInsert = new ReentrantLock();
		condToRemove = lockRemove.newCondition();
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
					//try_sleep(200);
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
	 * Method to condToRemove an element on list
	 * @param element element to be removed on list
	 */
	public void remove(E element) {
		/**
		 * @author luanpereira
		 * Anonymous class for a thread
		 */
		Thread t = new Thread() {
			public void run() {
				synchronized (condToRemove) {
					couldSearch=false;
					lockRemove.lock();
					lockInsert.lock();
					System.out.println("----- starting to remove");
					try {
						//try_sleep(500);
						linkedList.remove(element);
						System.out.println(element + " removed succesfully");
					} finally {
						System.out.println("----- finishing to remove");
						lockInsert.unlock();
						lockRemove.unlock();
						couldSearch=true;
						condToRemove.notifyAll();
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
				synchronized (condToRemove) {
					if(!couldSearch) {
						try {
							condToRemove.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					System.out.println("- starting to search");
					result.set(linkedList.contains(element));
					//try_sleep(700);
					if(result.get()) {
						System.out.println(element + " founded");
					}else {
						System.out.println(element + " not founded");
					}
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
