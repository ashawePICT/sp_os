package pass2;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

public class Pass2 {
	private static Scanner SC;
	private FileReader op;
	private FileReader stb;
	private FileReader ltb;
	private FileReader ptb;
	
	public void doPass2() throws Exception
	{
		op = new FileReader("/home/TE/Documents/SPOS_31332/SP/is.txt");
		stb = new FileReader("/home/TE/Documents/SPOS_31332/SP/syntab.txt");
		ltb = new FileReader("/home/TE/Documents/SPOS_31332/SP/littab.txt");
		ptb = new FileReader("/home/TE/Documents/SPOS_31332/SP/pooltab.txt");
		
		Vector<Map<String, Integer>> symbolTable = new Vector<>();
		Vector<Map<String, Integer>> literalTable = new Vector<>();
		Vector<Integer> poolTable = new Vector<>();
		Vector<String> symbols = new Vector<>();
		
		// read stb.txt
		SC = new Scanner(op);
		while(SC.hasNext())
		{
			String symbol_entry = SC.nextLine();
			String words[] = symbol_entry.split(":");
			symbols.add(words[0]);
			Map<String,Integer> test = new HashMap<>();
			test.put(words[0], Integer.valueOf(words[1]));
		}
		
		for(Map<String,Integer> m : symbolTable){
			String key = m.entrySet().iterator().next().getKey();
			System.out.println(key+":"+m.get(key));
		}
		
		
		

		op.close();
		stb.close();
		ltb.close();
		ptb.close();
	}

}
