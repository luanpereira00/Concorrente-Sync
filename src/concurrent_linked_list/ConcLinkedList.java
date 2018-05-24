package concurrent_linked_list;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcLinkedList<E> {
	private Lock lockRemove;
	private Lock lockInsert;
	private Condition remove;
	private Condition insert;
	private boolean isRemoving = false;

	
	private LinkedList<E> linkedList;
	//TODO concorrencia usando alguma ferramenta
	
	public ConcLinkedList() {
		linkedList = new LinkedList<E>();
		lockRemove = new ReentrantLock();
		lockInsert = new ReentrantLock();
		remove = lockRemove.newCondition();
		insert = lockInsert.newCondition();
	}
	
	public void insert(E element) {
		(new Thread() {
			public void run() {
				lockInsert.lock();
				try {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					linkedList.add(element);
					System.out.println(element + " inserted succesfully");
				} finally {
					lockInsert.unlock();
				}
			}
		}).start();
	}
	
	public void remove(E element) {
		//TODO try catch to remove
		(new Thread() {
			public void run() {
				lockRemove.lock();
				isRemoving=true;
				lockInsert.lock();
				try {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					linkedList.remove(element);
					System.out.println(element + " removed succesfully");
				} finally {
					isRemoving=false;
					remove.signalAll();
					//remove.notifyAll();
					lockInsert.unlock();
					lockRemove.unlock();
				}
			}
		}).start();
	}
	
	public boolean search(E element) {
		final AtomicBoolean result = new AtomicBoolean();
		(new Thread() {
			public void run() {
				if(isRemoving) {
					//remove.wait();
					remove.awaitUninterruptibly();
				}
				result.set(linkedList.contains(element));
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		if(result.get()) {
			System.out.println(element + " founded");
		}else {
			System.out.println(element + " not founded");
		}
		return result.get();
	}
}
