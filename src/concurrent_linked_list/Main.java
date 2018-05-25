package concurrent_linked_list;

//Questionamente: durante iniciar uma remoção, é necessário esperar as buscas concluirem?
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
		// TODO Auto-generated method stub
		System.out.println("Hello linked list");
		ConcLinkedList<Integer> c = new ConcLinkedList<>();
		
		for(int i=0;i<20; i++) {
			c.insert(i);
		}
		
		for(int i=0;i<20; i+=2) {
			c.search(i);
		}
		
		for(int i=0;i<20; i+=3) {
			c.remove(i);
		}
	}

}
