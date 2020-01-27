package pass1;

public class MNT_entry {

	public MNT_entry() {
		super();
		noOfKeywordParams = 0;
		noOfPositionalParams = 0;
	}
	String name;
	int noOfPositionalParams, noOfKeywordParams, MDTPointer, KPDTPointer, SSTPointer;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNoOfPositionalParams() {
		return noOfPositionalParams;
	}
	public void setNoOfPositionalParams(int noOfPositionalParams) {
		this.noOfPositionalParams = noOfPositionalParams;
	}
	public int getNoOfKeywordParams() {
		return noOfKeywordParams;
	}
	public void setNoOfKeywordParams(int noOfKeywordParams) {
		this.noOfKeywordParams = noOfKeywordParams;
	}
	public int getMDTPointer() {
		return MDTPointer;
	}
	public void setMDTPointer(int mDTPointer) {
		MDTPointer = mDTPointer;
	}
	public int getKPDTPointer() {
		return KPDTPointer;
	}
	public void setKPDTPointer(int kPDTPointer) {
		KPDTPointer = kPDTPointer;
	}
	public int getSSTPointer() {
		return SSTPointer;
	}
	public void setSSTPointer(int sSTPointer) {
		SSTPointer = sSTPointer;
	}
}
