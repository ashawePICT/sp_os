package pass1;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

public class Pass1 {
	private static Scanner SC = null;
	Vector<Kpdtab_entry> KPDTAB = null;
	
	Pass1(){
		KPDTAB = new Vector<>();
	}
	
	public void doPass1() throws Exception{
		FileReader ip = new FileReader("/home/TE/Documents/SPOS_31332/SP/inputm.asm");
		Vector<MNT_entry> MNT = new Vector<>();
		MDT mdt = new MDT();
		SC = new Scanner(ip);
		Vector<String> params = new Vector<>();
		boolean flag = false;
		
		
		while(SC.hasNext())
		{
			String line = SC.nextLine();
			String[] words = line.split(" ");
			
			if( words[1].equals("MEND"))
			{
				mdt.getMdt().add("MEND");
				flag = false;
				continue;
			}
			
			if( words[1].equals("MACRO"))
			{
				line = SC.nextLine();
				words = line.split(" ");
				flag = true;
				
				MNT_entry mnt_entry = new MNT_entry();
				mnt_entry.setName(words[1]);
				mnt_entry.setKPDTPointer(KPDTAB.size());
				mnt_entry.setMDTPointer( mdt.getMdt().size() );
				
				
				
				for(int i = 2; i < words.length; i++)
				{
					if(words[i].contains("="))
					{
						mnt_entry.setNoOfKeywordParams(mnt_entry.getNoOfKeywordParams() + 1);
						int x = words[i].indexOf('=');
						KPDTAB.add( new Kpdtab_entry(words[i].substring(1,x) , words[i].substring(x+1)));
						params.add(words[i].substring(1,x));
					}
					else
					{
						mnt_entry.setNoOfPositionalParams(mnt_entry.getNoOfPositionalParams() + 1);
						params.add(words[i].substring(1));
					}
				}
				MNT.add(mnt_entry);
				continue;
			}
			
			if(flag)
			{
				String s = new String();
				
				for( int i = 0; i < words.length; i++)
				{
					if( words[i].contains("&") )
					{
						String word = words[i].substring(1);
						int x = params.indexOf(word);
						s = s + " (P," + x + ")";
					}
					else
					{
						s += words[i];
					}
				}
				mdt.getMdt().add(s);
//				System.out.println(s);
			}
		}
		
		System.out.println("MNT:");
		System.out.println("i\tName\t#PP\t#KP\tMDTP\tKPDTP\tSSTP");
		for(int i = 0; i < MNT.size(); i++)
		{
			MNT_entry mnt_entry1 = MNT.elementAt(i);
			System.out.println("" + i + "\t" + mnt_entry1.getName() + "\t"
					+ mnt_entry1.getNoOfPositionalParams() + "\t"
					+ mnt_entry1.getNoOfKeywordParams() + "\t"
					+ mnt_entry1.getMDTPointer() + "\t"
					+ mnt_entry1.getKPDTPointer() + "\t"
					+ mnt_entry1.getSSTPointer() + "\t"
					);
		}
		
		
		System.out.println("KPDTAB:");
		for(int i = 0; i < KPDTAB.size(); i++)
		{
			Kpdtab_entry kpdtab_entr = KPDTAB.elementAt(i);
			System.out.println("" + i + "\t" +  kpdtab_entr.getName() + "\t" + kpdtab_entr.getValue());
		}
		
		System.out.println("MDT:");
		Vector<String> mdt1 = mdt.getMdt();
		for (int i = 0; i < mdt1.size(); i++) {
			System.out.println("" + i + "\t" + mdt1.elementAt(i));
		}
	}
}
