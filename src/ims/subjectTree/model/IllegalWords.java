package ims.subjectTree.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * √Ù∏–¥ ±Ì
 * @author DD
 *
 */
public class IllegalWords {
	private int illegalWordId;
	private int illegalWordTreeId;
	private String illegalWordCnt;
	private Timestamp saveDate;
	
	public IllegalWords() {
		super();
	}

	
	public IllegalWords(int illegalWordTreeId, String illegalWordCnt,
			Timestamp saveDate) {
		super();
		this.illegalWordTreeId = illegalWordTreeId;
		this.illegalWordCnt = illegalWordCnt;
		this.saveDate = saveDate;
	}


	public IllegalWords(int illegalWordId, int illegalWordTreeId,
			String illegalWordCnt, Timestamp saveDate) {
		super();
		this.illegalWordId = illegalWordId;
		this.illegalWordTreeId = illegalWordTreeId;
		this.illegalWordCnt = illegalWordCnt;
		this.saveDate = saveDate;
	}

	public int getIllegalWordId() {
		return illegalWordId;
	}

	public void setIllegalWordId(int illegalWordId) {
		this.illegalWordId = illegalWordId;
	}

	public int getIllegalWordTreeId() {
		return illegalWordTreeId;
	}

	public void setIllegalWordTreeId(int illegalWordTreeId) {
		this.illegalWordTreeId = illegalWordTreeId;
	}

	public String getIllegalWordCnt() {
		return illegalWordCnt;
	}

	public void setIllegalWordCnt(String illegalWordCnt) {
		this.illegalWordCnt = illegalWordCnt;
	}

	public Timestamp getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(Timestamp saveDate) {
		this.saveDate = saveDate;
	}
	
	

}
