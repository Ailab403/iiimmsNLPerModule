package ims.nlp.entity.model;

import java.sql.Timestamp;

public class Index {

	// 索引信息表

	private int indexId;

	private int setId;

	private String indexName;
	private String indexPath;

	private int docNum;
	private int docDeletedNum;
	private Timestamp refreshTime;
	private String indexExp;

	private ClassicTextSet classicTextSet;

	public Index() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Index(int setId, String indexName, String indexPath, int docNum,
			int docDeletedNum, Timestamp refreshTime, String indexExp,
			ClassicTextSet classicTextSet) {
		super();
		this.setId = setId;
		this.indexName = indexName;
		this.indexPath = indexPath;
		this.docNum = docNum;
		this.docDeletedNum = docDeletedNum;
		this.refreshTime = refreshTime;
		this.indexExp = indexExp;
		this.classicTextSet = classicTextSet;
	}

	public Index(int indexId, int setId, String indexName, String indexPath,
			int docNum, int docDeletedNum, Timestamp refreshTime,
			String indexExp, ClassicTextSet classicTextSet) {
		super();
		this.indexId = indexId;
		this.setId = setId;
		this.indexName = indexName;
		this.indexPath = indexPath;
		this.docNum = docNum;
		this.docDeletedNum = docDeletedNum;
		this.refreshTime = refreshTime;
		this.indexExp = indexExp;
		this.classicTextSet = classicTextSet;
	}

	public int getIndexId() {
		return indexId;
	}

	public void setIndexId(int indexId) {
		this.indexId = indexId;
	}

	public int getSetId() {
		return setId;
	}

	public void setSetId(int setId) {
		this.setId = setId;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getIndexPath() {
		return indexPath;
	}

	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}

	public int getDocNum() {
		return docNum;
	}

	public void setDocNum(int docNum) {
		this.docNum = docNum;
	}

	public int getDocDeletedNum() {
		return docDeletedNum;
	}

	public void setDocDeletedNum(int docDeletedNum) {
		this.docDeletedNum = docDeletedNum;
	}

	public Timestamp getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(Timestamp refreshTime) {
		this.refreshTime = refreshTime;
	}

	public String getIndexExp() {
		return indexExp;
	}

	public void setIndexExp(String indexExp) {
		this.indexExp = indexExp;
	}

	public ClassicTextSet getClassicTextSet() {
		return classicTextSet;
	}

	public void setClassicTextSet(ClassicTextSet classicTextSet) {
		this.classicTextSet = classicTextSet;
	}

	@Override
	public String toString() {
		return "Index [classicTextSet=" + classicTextSet + ", docDeletedNum="
				+ docDeletedNum + ", docNum=" + docNum + ", indexExp="
				+ indexExp + ", indexId=" + indexId + ", indexName="
				+ indexName + ", indexPath=" + indexPath + ", refreshTime="
				+ refreshTime + ", setId=" + setId + "]";
	}

}
