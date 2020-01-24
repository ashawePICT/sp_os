package pass1;
import java.io.IOException;
import java.util.Scanner;
import pass2.Pass2;

public class MyDriver {
	private static Scanner sc; 
	public static void main(String args[]){
		Pass1 p1 = new Pass1();
		Pass2 p2 = new Pass2();
		sc= new Scanner(System.in);
		try{
//			p1.doPassOne();
//			System.out.println("Pass1 complete");
//			System.out.println("Continue with pass 2?(Y/N)");
//			String ans = sc.next();
//			if(ans.equals("Y") || ans.equals("y") || ans.toUpperCase().equals("YES"))
			p2.doPass2();
		}
		catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
