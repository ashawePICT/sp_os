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

	MDT(){
		mdt = new Vector<>();
	}
}
