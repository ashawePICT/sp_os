package pass1;

import java.util.Vector;

public class MDT {
	Vector<String> mdt = null;
	
	public Vector<String> getMdt() {
		return mdt;
	}

	public void setMdt(Vector<String> mdt) {
		this.mdt = mdt;
	}

	public MDT(){
		mdt = new Vector<>();
	}
}
