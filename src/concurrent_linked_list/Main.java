package concurrent_linked_list;

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
		ConcLinkedList<Integer> c = new ConcLinkedList<>();
		
		(new Thread() {
			public void run() {
				int var = 10;
				for(int i=0;i<var; i++) {
					c.search(i);
				}
			}
		}).start();
		
		(new Thread() {
			public void run() {
				int var = 10;
				for(int i=0;i<var; i+=3) {
					c.remove(i);
				}
			}
		}).start();
		
		(new Thread() {
			public void run() {
				int var = 10;
				for(int i=0;i<var; i+=2) {
					c.insert(i);
				}
			}
		}).start();
	}

}
