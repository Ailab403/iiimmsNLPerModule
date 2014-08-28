package ims.subjectTree.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Õ£”√¥ ±Ì
 * @author DD
 *
 */
public class StopWords {
	private int stopWordId;
	private int stopWordTreeId;
	private String stopWordCnt;
	private Timestamp saveDate;
	
	public StopWords() {
		super();
	}
	public StopWords(int stopWordId, int stopWordTreeId, String stopWordCnt,
			Timestamp saveDate) {
		super();
		this.stopWordId = stopWordId;
		this.stopWordTreeId = stopWordTreeId;
		this.stopWordCnt = stopWordCnt;
		this.saveDate = saveDate;
	}
	public int getStopWordId() {
		return stopWordId;
	}
	public void setStopWordId(int stopWordId) {
		this.stopWordId = stopWordId;
	}
	public int getStopWordTreeId() {
		return stopWordTreeId;
	}
	public void setStopWordTreeId(int stopWordTreeId) {
		this.stopWordTreeId = stopWordTreeId;
	}
	public String getStopWordCnt() {
		return stopWordCnt;
	}
	public void setStopWordCnt(String stopWordCnt) {
		this.stopWordCnt = stopWordCnt;
	}
	public Timestamp getSaveDate() {
		return saveDate;
	}
	public void setSaveDate(Timestamp saveDate) {
		this.saveDate = saveDate;
	}
	
	

}
