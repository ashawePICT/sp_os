package pass1;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

public class Pass1 {
	private static Scanner SC;
	private int lc = -1;
	private int lastLiteralTableSize = 0;
	private int literalTableIndex = 0;
	private int poolTableIndex = 0;
	private int symbolTableIndex = 0;
	
	public void doPassOne() throws Exception{
		FileReader ip = new FileReader("/home/TE/Documents/SPOS_31332/SP/input.asm");
		FileWriter op = new FileWriter("/home/TE/Documents/SPOS_31332/SP/output.obj");
		FileWriter tb = new FileWriter("/home/TE/Documents/SPOS_31332/SP/tables.obj");
		
		Vector<Map<String, Integer>> symbolTable = new Vector<>();
		Vector<Map<String, Integer>> literalTable = new Vector<>();
		Vector<Map<String, Integer>> poolTable = new Vector<>();
		Vector<String> symbols = new Vector<>();
		
		SC = new Scanner(ip);
		
		while(SC.hasNext())
		{
			Opcode opcode,operand1=null,operand2=null;
			String variable2 = "NAN";
			//reading line
			String line = SC.nextLine();
			//converting into words
			String[] words = line.split(" ");
			int length = words.length;			
			String label = "";
			boolean isLabel = false;
			boolean isVariable = false;
			
//			for( int i = 0; i < length; i++ )
//				System.out.println(words[i]);
			
			if(words[1].equals("START"))
			{
				lc = Integer.valueOf(words[2]);
				continue;
			}
			
			if( words[1].equals("LTORG") || words[1].equals("END") )
			{
				// System.out.println("HERE" + literalTable.size() + "lc:" + lc);
				for(int i = lastLiteralTableSize; i < literalTable.size(); i++ ){
					String key = literalTable.elementAt(i).entrySet().iterator().next().getKey();
					literalTable.elementAt(i).replace(key,lc);
					lc++;
				}
				lastLiteralTableSize = literalTable.size();
				continue;
			}
			
			if( words[1].equals("ORIGIN")){
				int x = words[2].indexOf('+');
				String var = words[2].substring(0,x);
				int addingPointer = Integer.valueOf(words[2].substring(x+1));
				
				// find referenced
				int index = symbols.lastIndexOf(var);
				int val = symbolTable.get(index).get(var);
				lc = val+addingPointer;
				continue;
			}
			
			
			label = words[0];
			
			if(!label.equals(""))
			{
				isLabel = true;
				Map<String,Integer> test =  new HashMap<>();
				// if new label is found add it to symbol table with current lc
				if(!symbols.contains(label))
				{
					symbols.add(label);
					test.put(label,lc);
					symbolTable.add(test);
					symbolTableIndex++;	
				}
				// if a forward reference is found
				// find it and replace -1 with current lc
				else
				{
					test.put(label,-1);
					int index = symbolTable.lastIndexOf(test);
					symbolTable.get(index).replace(label, lc);
				}
				
				if( words[1].equals("DS") )
				{
					lc += (Integer.valueOf(words[2])-1);
					continue;
				}
				
			}
			
			opcode = Mottab.getClass(words[1]);
			
			if( length == 3 )
			{
				try{
					operand1 = Mottab.getClass(words[2]);
				}catch(Exception e){
					variable2 = words[2];
					isVariable = true;
					if(isLiteral(variable2)){
						Map<String,Integer> test =  new HashMap<>();
						test.put(variable2,-1);
						literalTable.add(test);
						literalTableIndex++;
					}
					else{
						if(!symbols.contains(variable2))
						{
							int x = variable2.indexOf('+');
							// new symbol
							if(x == -1)
							{
								Map<String,Integer> test =  new HashMap<>();
								test.put(variable2,-1);
								symbolTable.add(test);
								symbols.add(variable2);
								symbolTableIndex++;	
							}
							else
							{
								// referenced symbol
								String var = variable2.substring(0,x);
								int addingPointer = Integer.valueOf(variable2.substring(x+1));
								
								// find referenced
								int index = symbols.lastIndexOf(var);
								int val = symbolTable.get(index).get(var);
								
								Map<String,Integer> test =  new HashMap<>();
								test.put(variable2,val+addingPointer);
								symbolTable.add(test);
								symbols.add(variable2);
								symbolTableIndex++;	
							}
						}
					}
				}
			}
			if( length == 4 )
			{
				try{
					operand1 = Mottab.getClass(words[2]);
					operand2 = Mottab.getClass(words[3]);	
				}catch(Exception e){
					variable2 = words[3];
					isVariable = true;
					if(isLiteral(variable2)){
						Map<String,Integer> test =  new HashMap<>();
						test.put(variable2,-1);
						literalTable.add(test);
						literalTableIndex++;
					}else{
						if(!symbols.contains(variable2))
						{
							Map<String,Integer> test =  new HashMap<>();
							test.put(variable2,-1);
							symbolTable.add(test);
							symbols.add(variable2);
							symbolTableIndex++;
						}
					}
				}
			}
			
			if(length == 2)
			{
				System.out.println("" + lc + " (" + opcode.get_OP_CLASS() + "," + opcode.get_CODE()+")");
			}
			if(length == 3)
			{
				if( isVariable ){
					if( isLiteral(variable2))
						System.out.println("" + lc + " (" + opcode.get_OP_CLASS() + "," + opcode.get_CODE() + ") " + literalTableIndex);
					else
						System.out.println("" + lc + " (" + opcode.get_OP_CLASS() + "," + opcode.get_CODE() + ") " + symbolTableIndex);
				}
				else
					System.out.println("" + lc+ " (" + opcode.get_OP_CLASS() + "," + opcode.get_CODE() + ") " + " (" + operand1.get_OP_CLASS() + "," + operand1.get_CODE() + ") ");
			}
			if(length == 4)
			{
				if( isVariable)
					System.out.println("" + lc + " (" + opcode.get_OP_CLASS() + "," + opcode.get_CODE() + ") " + " (" + operand1.get_OP_CLASS() + "," + operand1.get_CODE() + ") " + " " + variable2);
				else
					System.out.println("" + lc + " (" + opcode.get_OP_CLASS() + "," + opcode.get_CODE() + ") " + " (" + operand1.get_OP_CLASS() + "," + operand1.get_CODE() + ") " + " (" + operand2.get_OP_CLASS() + "," + operand2.get_CODE() + ") ");
			}
			
			lc++;
			
		}
		ip.close();
		op.close();
		
		for(Map<String,Integer> m : literalTable){
			String key = m.entrySet().iterator().next().getKey();
			System.out.println(key + ":" + m.get(key));
		}
		
		System.out.println();
		
		
		for(Map<String,Integer> m : symbolTable){
			String key = m.entrySet().iterator().next().getKey();
			System.out.println(key + ":" + m.get(key));
		}
		
	}
	
	private boolean isLiteral(String word)
	{
		if(word.charAt(0) == '\'')
			return true;
		return false;
	}
	
}
