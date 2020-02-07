package pass2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import pass1.Kpdtab_entry;

public class Pass2 {
	private static Scanner SC;
	Vector<Kpdtab_entry> KPDTAB = null;
	private FileReader inputwm;
	private FileReader mntab;
	private FileReader mdtab;
	private FileReader kpdtab;
	private FileWriter input;
	
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
		
		input = new FileWriter("/home/TE/Documents/SPOS_31332/SP/input.asm");
	}
}
