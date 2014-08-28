package ims.subjectTree.model;

import java.sql.Timestamp;
import java.util.Date;
/**
 * Õ¯¬Á»»¥ 
 * @author DD
 *
 */
public class NetWords {
	private int netWordId;
	private int netWordTreeId;
	private String netWordCnt;
	private Timestamp saveDate;
	
	public NetWords() {
		super();
	}
	public NetWords(int netWordId, int netWordTreeId, String netWordCnt,
			Timestamp saveDate) {
		super();
		this.netWordId = netWordId;
		this.netWordTreeId = netWordTreeId;
		this.netWordCnt = netWordCnt;
		this.saveDate = saveDate;
	}
	public int getNetWordId() {
		return netWordId;
	}
	public void setNetWordId(int netWordId) {
		this.netWordId = netWordId;
	}
	public int getNetWordTreeId() {
		return netWordTreeId;
	}
	public void setNetWordTreeId(int netWordTreeId) {
		this.netWordTreeId = netWordTreeId;
	}
	public String getNetWordCnt() {
		return netWordCnt;
	}
	public void setNetWordCnt(String netWordCnt) {
		this.netWordCnt = netWordCnt;
	}
	public Timestamp getSaveDate() {
		return saveDate;
	}
	public void setSaveDate(Timestamp saveDate) {
		this.saveDate = saveDate;
	}
	
	

}
