 package concurrentLinkedList;

//Questionamente: durante iniciar uma remocao, eh necessario esperar as buscas concluirem?
/**
 * @author luanpereira
 * Main class
 */
public class Main {

	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Hello linked list");
		ConcurrentLinkedList<Integer> c = new ConcurrentLinkedList<>();
		
		Thread tS = new Thread() {
			public void run() {
				int var = 20;
				for(int i=0;i<var; i++) {
					c.search(i);
				}
			}
		};
		
		Thread tR = new Thread() {
			public void run() {
				int var = 10;
				for(int i=0;i<var; i+=3) {
					c.remove(i);
				}
			}
		};
		
		Thread tI = new Thread() {
			public void run() {
				int var = 30;
				for(int i=0;i<var; i+=2) {
					c.insert(i);
				}
			}
		};
		
		tI.start();
		tR.start();
		tS.start();
		
		try {
			tI.join();
			tR.join();
			tS.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
