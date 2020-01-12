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
		FileReader ip = new FileReader("/home/TE/Documents/SPOS_31332/SP/tp.asm");
		FileWriter op = new FileWriter("/home/TE/Documents/SPOS_31332/SP/is.txt");
		FileWriter stb = new FileWriter("/home/TE/Documents/SPOS_31332/SP/syntab.txt");
		FileWriter ltb = new FileWriter("/home/TE/Documents/SPOS_31332/SP/littab.txt");
		FileWriter ptb = new FileWriter("/home/TE/Documents/SPOS_31332/SP/pooltab.txt");
		
		Vector<Map<String, Integer>> symbolTable = new Vector<>();
		Vector<Map<String, Integer>> literalTable = new Vector<>();
		Vector<Integer> poolTable = new Vector<>();
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
					if( !words[1].equals("END"))
						op.write("" + lc + " (DL,01) (C," + key.charAt(2) + ")" +"\n");
					lc++;
				}
				poolTable.add(lastLiteralTableSize);
				lastLiteralTableSize = literalTable.size();
				// continue;
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
						if(words[1].equals("DS") || words[1].equals("DC"))
						{
							// do not add into any table
						}
						else if(!symbols.contains(variable2))
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

			if(opcode.get_OP_CLASS().equals("AD")){
				continue;
			}

			if(length == 2)
			{
				op.write("" + lc + " (" + opcode.get_OP_CLASS() + "," + opcode.get_CODE()+")"+"\n");
			}
			if(length == 3)
			{
				if( isVariable ){
					int value1 = symbols.indexOf(variable2)+1;
					if(isLiteral(variable2))
					{
						op.write("" + lc + " (" + opcode.get_OP_CLASS() + "," + opcode.get_CODE() + ") " + "(L," + value1 + ")"+"\n");
					}
					else if( isNumeric(variable2) )
						op.write("" + lc + " (" + opcode.get_OP_CLASS() + "," + opcode.get_CODE() + ") " + "(C," + variable2 + ")"+"\n");
					else
						op.write("" + lc + " (" + opcode.get_OP_CLASS() + "," + opcode.get_CODE() + ") " + "(S," + value1 + ")"+"\n");
				}
				else
					op.write("" + lc+ " (" + opcode.get_OP_CLASS() + "," + opcode.get_CODE() + ") " + "(" + operand1.get_OP_CLASS() + "," + operand1.get_CODE() + ") "+"\n");
			}
			if(length == 4)
			{
				if(isVariable)
					{
						int value1 = 0;
						if(isLiteral(variable2))
						{
							value1 = literalTable.size();
							if( operand1.get_OP_CLASS().equals("") )
								op.write("" + lc + " (" + opcode.get_OP_CLASS() + "," + opcode.get_CODE() + ") " + "(" + operand1.get_CODE() + ") " + "(L," + value1 + ")"+"\n");
							else
								op.write("" + lc + " (" + opcode.get_OP_CLASS() + "," + opcode.get_CODE() + ") " + "(" + operand1.get_OP_CLASS() + "," + operand1.get_CODE() + ") " + "(L," + value1 + ")"+"\n");
						}
						else
						{
							value1 = symbols.indexOf(variable2)+1;
							if( operand1.get_OP_CLASS().equals("") )
								op.write("" + lc + " (" + opcode.get_OP_CLASS() + "," + opcode.get_CODE() + ") " + "(" + operand1.get_CODE() + ") " + "(S," + value1+")"+"\n");
							else
								op.write("" + lc + " (" + opcode.get_OP_CLASS() + "," + opcode.get_CODE() + ") " + "(" + operand1.get_OP_CLASS() + "," + operand1.get_CODE() + ") " + " " + "(S," + value1+")"+"\n");
						}
					}
				else
					{
						op.write("" + lc + " (" + opcode.get_OP_CLASS() + "," + opcode.get_CODE() + ") " + "(" + operand1.get_OP_CLASS() + "," + operand1.get_CODE() + ")" + " (" + operand2.get_OP_CLASS() + "," + operand2.get_CODE() + ") "+"\n");
					}
			}
			
			lc++;
			
		}
		ip.close();
		//op.close();
		
		System.out.println();

		for(Map<String,Integer> m : literalTable){
			String key = m.entrySet().iterator().next().getKey();
			ltb.write(key + ":" + m.get(key)+"\n");
		}
		
		System.out.println();
		
		
		for(Map<String,Integer> m : symbolTable){
			String key = m.entrySet().iterator().next().getKey();
			System.out.println(key+":"+m.get(key));
			if(m.get(key) != -1)
				stb.write(key + ":" + m.get(key)+"\n");
			else
				throw new Exception("Undefined Symbol: " + key);
		}
		
		if(literalTable.size()!=0)
		for(int i : poolTable)
		{
			ptb.write(i+"\n");
		}

		op.close();
		ltb.close();
		stb.close();
		ptb.close();
		
	}
	
	private boolean isNumeric(String variable2) {
	try {  
	    Double.parseDouble(variable2);  
	    return true;
	  } catch(NumberFormatException e){  
	    return false;  
	  }  
	}

	private boolean isLiteral(String word)
	{
		if(word.charAt(0) == '\'')
			return true;
		return false;
	}
	
}
