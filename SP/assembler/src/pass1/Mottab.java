package pass1;
import org.omg.CORBA.UserException;

import pass1.Opcode;

public class Mottab {
	public static Opcode getClass(String opcode) throws Exception
	{	
		switch(opcode)
		{
			// IMPERATIVE STATEMENTS
			case "STOP":
				return new Opcode(opcode, "00", "IS");
			case "ADD":
				return new Opcode(opcode, "01", "IS");
			case "SUB":
				return new Opcode(opcode, "02", "IS");
			case "MULT":
				return new Opcode(opcode, "03", "IS");
			case "MOVER":
				return new Opcode(opcode, "04", "IS");
			case "MOVEM":
				return new Opcode(opcode, "05", "IS");
			case "COMP":
				return new Opcode(opcode, "06", "IS");
			case "BC":
				return new Opcode(opcode, "07", "IS");
			case "DIV":
				return new Opcode(opcode, "08", "IS");
			case "READ":
				return new Opcode(opcode, "09", "IS");
			case "PRINT":
				return new Opcode(opcode, "10", "IS");
				

			// ASSEMBLER DIRECTIVE
			case "START":
				return new Opcode(opcode, "01", "AD");
			case "END":
				return new Opcode(opcode, "02", "AD");
			case "ORIGIN":
				return new Opcode(opcode, "03", "AD");
			case "EQU":
				return new Opcode(opcode, "04", "AD");
			case "LTORG":
				return new Opcode(opcode, "05", "AD");
				
				
			// DECLARATIVES
			case "DC":
				return new Opcode(opcode, "01", "DL");
			case "DS":
				return new Opcode(opcode, "02", "DL");
			
			
			// REGISTERS
			case "AREG":
				return new Opcode(opcode, "01", "");
			case "BREG":
				return new Opcode(opcode, "02", "");
			case "CREG":
				return new Opcode(opcode, "03", "");
			case "DREG":
				return new Opcode(opcode, "04", "");
				
				
			// CONDITION CODES
			case "LT":
				return new Opcode(opcode, "01", "");
			case "LE":
				return new Opcode(opcode, "02", "");
			case "EQ":
				return new Opcode(opcode, "03", "");
			case "GT":
				return new Opcode(opcode, "04", "");
			case "GE":
				return new Opcode(opcode, "05", "");
			case "ANY":
				return new Opcode(opcode, "06", "");
			
				
			default:
					throw new UserException("No opcode found"){}; 
		}
	}
}
