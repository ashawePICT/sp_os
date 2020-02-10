package pass2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import pass1.Kpdtab_entry;
import pass1.MDT;
import pass1.MNT_entry;

public class Pass2 {
	private static Scanner SC;
	Vector<Kpdtab_entry> KPDTAB = null;
	private FileReader inputwm;
	private FileReader mntab;
	private FileReader mdtab;
	private FileReader kpdtab;
	private FileWriter output;
	
	public Pass2()
	{
		KPDTAB = new Vector<Kpdtab_entry>();
	}
	
	public void doPass2() throws IOException
	{
		inputwm = new FileReader("/home/TE/Documents/SPOS_31332/SP/inputwm.asm");
		mntab = new FileReader("/home/TE/Documents/SPOS_31332/SP/mnt.txt");
		mdtab = new FileReader("/home/TE/Documents/SPOS_31332/SP/mdt.txt");
		kpdtab = new FileReader("/home/TE/Documents/SPOS_31332/SP/kpdt.txt");
		
		output = new FileWriter("/home/TE/Documents/SPOS_31332/SP/output.asm");
		Vector<String> macro_names = new Vector<>();
		Vector<MNT_entry> MNT = new Vector<>();
		MDT mdt = new MDT();
		String[] APTAB;
		
		// filling MNTAB again by reading from file
		SC = new Scanner(mntab);
		while(SC.hasNext())
		{
			MNT_entry entry = new MNT_entry(); 
			String mnt_entry = SC.nextLine();
			String[] words = mnt_entry.split(" ");
			entry.setName(words[1]);
			entry.setNoOfPositionalParams(Integer.valueOf(words[2]));
			entry.setNoOfKeywordParams(Integer.valueOf(words[3]));
			entry.setMDTPointer(Integer.valueOf(words[4]));
			entry.setKPDTPointer(Integer.valueOf(words[5]));
			entry.setSSTPointer(Integer.valueOf(words[6]));
			macro_names.add(words[1]);
			MNT.add(entry);
		}
		SC.close();
		
		//filling mdt again by reading from file
		SC = new Scanner(mdtab);
		Vector<String> mdt_from_file = new Vector<String>();
		while(SC.hasNext())
		{
			mdt_from_file.add(SC.nextLine());
		}
		mdt.setMdt(mdt_from_file);
		SC.close();
		
		//filling kpdtab from file
		SC = new Scanner(kpdtab);
		while(SC.hasNext())
		{
			String[] words = SC.nextLine().split(" ");
			Kpdtab_entry kpdtab_entry;
			if(words.length < 3)
				kpdtab_entry = new Kpdtab_entry(words[1],"");
			else
				kpdtab_entry = new Kpdtab_entry(words[1],words[2]);			
			KPDTAB.add(kpdtab_entry);
		}
		SC.close();
		
		// creating output.asm file from 'input without macro' asm file
		SC = new Scanner(inputwm);

		while(SC.hasNext())
		{
			String code_line = SC.nextLine();
			String[] words = code_line.split(" ");
			int index = macro_names.indexOf(words[0]);
			if( !words[0].equals("") && index != -1 )
			{
				// CREATING APTAB
				MNT_entry mnt_entry = MNT.elementAt(index);
				int numKP = mnt_entry.getNoOfKeywordParams();
				int numPP = mnt_entry.getNoOfPositionalParams();
				int kpPtr = mnt_entry.getKPDTPointer();
				System.out.println("#PP:" + numPP + "#KP" + numKP);
				int aptabSize =  numPP + numKP;
				APTAB = new String[aptabSize];
				
				// COPYING Default values of KPDTAB TO APTAB
				for( int i = numPP; i < aptabSize; i++)
				{
					Kpdtab_entry kpdtab_entry = KPDTAB.elementAt(kpPtr); kpPtr++;
					APTAB[i] = kpdtab_entry.getValue();
				}				
				
				// COPYING Positional Parameters from Macro Call to APTAB
				int aptabPtr = 0;
				kpPtr = mnt_entry.getKPDTPointer();
				

				for(int i = 1; i < words.length; i++)
				{
					if(words[i].contains("="))
					{
						String[] value = words[i].split("=");
						if(!value[1].equals(""))
						{
							int ndex =0 ;
							for(int j = kpPtr; j < kpPtr+numKP ; j++ )
							{
								Kpdtab_entry kpdtab_entry = KPDTAB.get(j);
								if(kpdtab_entry.getName().equals(value[0]))
								{
									ndex = j;
									break;
								}
							}
							int q = ndex;
							System.out.println("val: " + value[0] + "pp " + numPP + "q " + q + "kpte " + kpPtr);
							APTAB[ numPP + q - kpPtr ] = value[1]; 
						}
					}
					else
						APTAB[aptabPtr] = words[i];
				}
				
				// COPYING Keywords Parameters from Macro Call to APTAB
				for(String s : APTAB)
					System.out.println(s);
				
			}
			else
			{
				output.write(code_line+"\n");
			}
		}
		SC.close();
		
		
		
		output.close();
	}
}
