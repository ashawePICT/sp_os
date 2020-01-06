package pass1;
import java.io.IOException;

public class MyDriver {
	public static void main(String args[]){
		Pass1 p1 = new Pass1();
		try{
			p1.doPassOne();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
