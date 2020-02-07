package pass1;

import pass2.Pass2;

public class Driver {
	
	public static void main(String[] args) throws Exception{
		Pass1 p1 = new Pass1();
		Pass2 p2 = new Pass2();
		System.out.println("_____________________________ PASS 1 _____________________________");
		p1.doPass1();
		System.out.println("_____________________________ PASS 2 _____________________________");
		p2.doPass2();
	}

}
