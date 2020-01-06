package pass1;
import pass1.Mottab;
public class Opcode {
	
	final String OPCODE;
	final String OP_CLASS;
	final String CODE;
	
	public Opcode(String op, String code, String op_class){
		this.OPCODE = op;
		this.CODE =  code;
		this.OP_CLASS = op_class;
	}
	
	public String get_OPCODE(){
		return OPCODE;
	}
	
	public String get_CODE(){
		return CODE;
	}
	
	public String get_OP_CLASS(){
		return OP_CLASS;
	}
}
