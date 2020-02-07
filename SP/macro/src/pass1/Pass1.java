package pass1;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

public class Pass1 {
	private static Scanner SC = null;
	Vector<Kpdtab_entry> KPDTAB = null;
	private FileWriter op;
	private FileWriter mntab;
	private FileWriter mdtab;
	private FileWriter kpdtab;
	
	Pass1(){
		KPDTAB = new Vector<>();
	}
	
	public void doPass1() throws Exception{
		FileReader ip = new FileReader("/home/TE/Documents/SPOS_31332/SP/inputm.asm");
		op = new FileWriter("/home/TE/Documents/SPOS_31332/SP/inputwm.asm");
		mntab = new FileWriter("/home/TE/Documents/SPOS_31332/SP/mnt.txt");
		mdtab = new FileWriter("/home/TE/Documents/SPOS_31332/SP/mdt.txt");
		kpdtab = new FileWriter("/home/TE/Documents/SPOS_31332/SP/kpdt.txt");
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
				System.out.println("PNTAB:");
				for (int i = 0; i < params.size(); i++) {
					System.out.println( i + "\t" + params.get(i));
				}
				params.clear();
				flag = false;
				continue;
			}
			
			if( words[1].equals("MACRO"))
			{
				line = SC.nextLine();
				words = line.split(" ");
				String name = words[1];
				words = words[2].split(",");
				flag = true;
				
				MNT_entry mnt_entry = new MNT_entry();
				mnt_entry.setName(name);
				mnt_entry.setKPDTPointer(KPDTAB.size());
				mnt_entry.setMDTPointer( mdt.getMdt().size() );
				
				
				
				for(int i = 0; i < words.length; i++)
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
			else
			{
				op.write(line + "\n");
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
			
			mntab.write("" + i + " " + mnt_entry1.getName() + " "
					+ mnt_entry1.getNoOfPositionalParams() + " "
					+ mnt_entry1.getNoOfKeywordParams() + " "
					+ mnt_entry1.getMDTPointer() + " "
					+ mnt_entry1.getKPDTPointer() + " "
					+ mnt_entry1.getSSTPointer() + " "
					+ "\n");
		}
		
		
		System.out.println("KPDTAB:");
		for(int i = 0; i < KPDTAB.size(); i++)
		{
			Kpdtab_entry kpdtab_entr = KPDTAB.elementAt(i);
			System.out.println("" + i + "\t" +  kpdtab_entr.getName() + "\t" + kpdtab_entr.getValue());
			kpdtab.write("" + i + " " +  kpdtab_entr.getName() + " " + kpdtab_entr.getValue() + "\n");
		}
		
		System.out.println("MDT:");
		Vector<String> mdt1 = mdt.getMdt();
		for (int i = 0; i < mdt1.size(); i++) {
			System.out.println("" + i + "\t" + mdt1.elementAt(i));
			mdtab.write("" + i + " " + mdt1.elementAt(i) + "\n");
		}
		
		op.close();
		mntab.close();
		mdtab.close();
		kpdtab.close();
		
	}
}
