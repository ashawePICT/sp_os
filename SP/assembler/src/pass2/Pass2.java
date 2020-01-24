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
	private FileWriter mc;
	
	public void doPass2() throws Exception
	{
		op = new FileReader("/home/TE/Documents/SPOS_31332/SP/is.txt");
		stb = new FileReader("/home/TE/Documents/SPOS_31332/SP/syntab.txt");
		ltb = new FileReader("/home/TE/Documents/SPOS_31332/SP/littab.txt");
		ptb = new FileReader("/home/TE/Documents/SPOS_31332/SP/pooltab.txt");
		
		mc = new FileWriter("/home/TE/Documents/SPOS_31332/SP/mc.txt");
		
		Vector<Map<String, Integer>> symbolTable = new Vector<>();
		Vector<Map<String, Integer>> literalTable = new Vector<>();
//		Vector<Integer> poolTable = new Vector<>();
		Vector<String> symbols = new Vector<>();
		
		// read stb.txt
		SC = new Scanner(stb);
		while(SC.hasNext())
		{
			String symbol_entry = SC.nextLine();
			String words[] = symbol_entry.split(":");
			symbols.add(words[0]);
			Map<String,Integer> test = new HashMap<>();
			test.put(words[0], Integer.valueOf(words[1]));
			symbolTable.add(test);
		}
		
		SC.close();
		SC = new Scanner(ltb);
		
		while(SC.hasNext())
		{
			String literal_entry = SC.nextLine();
			String words[] = literal_entry.split(":");
			Map<String,Integer> test = new HashMap<>();
			test.put(words[0], Integer.valueOf(words[1]));
			literalTable.add(test);
		}
		
		SC.close();
		SC = new Scanner(op);
		while(SC.hasNext())
		{
			String ic_line = SC.nextLine();
			String words[] = ic_line.split(" ");
			mc.write(words[0] + " "); // wrote LC
			String ic = words[1];
			if( ic.charAt(1)=='I' )
			{
				System.out.println("IS");
				// IS
				
				mc.write(ic.charAt(4) + "" + "" + ic.charAt(5) + " ");
				try{
					String op1 = words[2];
					mc.write(op1.charAt(1) + "" +  op1.charAt(2) + " ");
					
					String op2 = words[3];
					if( op2.charAt(1) == 'L' )
					{
						// literal
						Map<String,Integer> test = literalTable.get( Integer.parseInt( op2.charAt(3) + "" ) -1 );
						String key = test.entrySet().iterator().next().getKey();
						mc.write( "" + test.get( key ) );
					}
					else if(op2.charAt(1) == 'S')
					{
						// symbol
						Map<String,Integer> test = symbolTable.get( Integer.parseInt( op2.charAt(3) + "" )-1 );
						String key = test.entrySet().iterator().next().getKey();
						mc.write( "" + test.get( key ) );
					}
				}
				catch(Exception e)
				{
					System.out.println("Here");
				}
				
			}
			else if( ic.charAt(1)=='D')
			{
				if(ic.charAt(5)=='1')
				{
					mc.write("00 00 ");
					
					String str = "";
					
					int i = 3;
					String op1 = words[2];
					while( op1.charAt(i) != ')' )
					{
						str += op1.charAt(i);
						i++;
					}
					if(str.length() == 1)
					{
						mc.write("00");
					}
					if(str.length() == 2)
					{
						mc.write("0");
					}
					mc.write(str);
				}
			}
			mc.write("\n");
		}
		mc.close();
		
		
		for(Map<String,Integer> m : literalTable){
			String key = m.entrySet().iterator().next().getKey();
			System.out.println(key+":"+m.get(key));
		}
		
		
		

		op.close();
		stb.close();
		ltb.close();
		ptb.close();
	}

}
