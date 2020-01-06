package pass1;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Pass1 {
	private static Scanner SC;
	
	public void doPassOne() throws IOException{
		FileReader fr = new FileReader("/home/TE/Documents/SPOS_31332/SP/input.asm");
		SC = new Scanner(fr);
		
		while(SC.hasNext())
		{
			//reading line
			String line = SC.nextLine();
			//converting into words
			String[] words = line.split(" ");
			
		}
		fr.close();
	}
	
}
