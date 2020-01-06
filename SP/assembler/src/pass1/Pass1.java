package pass1;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

public class Pass1 {
	private static Scanner SC;
	private String lc;
	private int lastLiteralTableSize;
	
	public void doPassOne() throws Exception{
		FileReader ip = new FileReader("/home/TE/Documents/SPOS_31332/SP/input.asm");
		FileWriter op = new FileWriter("/home/TE/Documents/SPOS_31332/SP/output.obj");
		FileWriter tb = new FileWriter("/home/TE/Documents/SPOS_31332/SP/tables.obj");
		
		Vector<Map<String, String>> symbolTable = new Vector<>();
		Vector<Map<String, String>> literalTable = new Vector<>();
		Vector<Map<String, String>> poolTable = new Vector<>();
		
		SC = new Scanner(ip);
		
		while(SC.hasNext())
		{
			Opcode opcode,operand1,operand2,operand3;
			String variable;
			//reading line
			String line = SC.nextLine();
			//converting into words
			String[] words = line.split(" ");
			int length = words.length;			
			String label = "";
			boolean isLabel = false;
			boolean isVariable = false;
			
			if(words[0] == "START")
			{
				lc = words[1];
				continue;
			}
			
			
			if( length > 0){
				try{
					opcode = Mottab.getClass(words[0]);
				}catch(Exception e){
					// first word is a label
					label = words[0];
					isLabel = true;
				}
			}
			if( length > 1){
				if(isLabel)
				{
					// skipping check as if first is label, second has to be an opcode
					opcode = Mottab.getClass(words[1]);
				}
				else{
					// can be an opcode or a symbol or a literal
					try{
						opcode = Mottab.getClass(words[1]);
					}catch(Exception e){
						// is a symbol or a literal
						isVariable = true;
						
					}
				}
				
			}
			if( length > 2){
				// can be literal or a symbol or a code
			}
			if( length > 3){
				// always a symbol or literal
			}
			
			
			
			
			
		}
		ip.close();
		op.close();
	}
	
}
