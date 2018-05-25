package concurrent_linked_list;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello linked list");
		ConcLinkedList c = new ConcLinkedList();
		c.search("O");
		c.insert("A");
		c.search("A");
		c.insert("B");
		c.search("A");
		c.remove("B");
		c.search("C");
		c.search("A");
		c.remove("B");
		c.insert("C");
		c.search("A");
		c.insert("D");
		c.search("D");
		c.search("O");
		c.insert("A");
		c.search("A");
		c.insert("B");
		c.search("A");
		c.remove("B");
		c.search("C");
		c.search("A");
		c.remove("B");
		c.insert("C");
		c.search("A");
		c.insert("D");
		c.search("D");
		
		//System.out.println(c.top());
	}

}
