package ims.nlp.entity.model;

import java.sql.Timestamp;

public class ClassicTextSet {

	// 经典分类主题信息表

	private int setId;
	private String setName;
	private String setNickName;
	private int rankNum;
	private double setLimitScore;
	private String setFolderPath;
	private int setAuthor;
	private Timestamp buildTime;
	private String setExp;

	public ClassicTextSet() {
		super();
		// TODO Auto-generated constructor stub
	}

	private ClassicTextSet(String setName, String setNickName, int rankNum,
			double setLimitScore, String setFolderPath, int setAuthor,
			Timestamp buildTime, String setExp) {
		super();
		this.setName = setName;
		this.setNickName = setNickName;
		this.rankNum = rankNum;
		this.setLimitScore = setLimitScore;
		this.setFolderPath = setFolderPath;
		this.setAuthor = setAuthor;
		this.buildTime = buildTime;
		this.setExp = setExp;
	}

	private ClassicTextSet(int setId, String setName, String setNickName,
			int rankNum, double setLimitScore, String setFolderPath,
			int setAuthor, Timestamp buildTime, String setExp) {
		super();
		this.setId = setId;
		this.setName = setName;
		this.setNickName = setNickName;
		this.rankNum = rankNum;
		this.setLimitScore = setLimitScore;
		this.setFolderPath = setFolderPath;
		this.setAuthor = setAuthor;
		this.buildTime = buildTime;
		this.setExp = setExp;
	}

	public int getSetId() {
		return setId;
	}

	public void setSetId(int setId) {
		this.setId = setId;
	}

	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public String getSetNickName() {
		return setNickName;
	}

	public void setSetNickName(String setNickName) {
		this.setNickName = setNickName;
	}

	public int getRankNum() {
		return rankNum;
	}

	public void setRankNum(int rankNum) {
		this.rankNum = rankNum;
	}

	public double getSetLimitScore() {
		return setLimitScore;
	}

	public void setSetLimitScore(double setLimitScore) {
		this.setLimitScore = setLimitScore;
	}

	public String getSetFolderPath() {
		return setFolderPath;
	}

	public void setSetFolderPath(String setFolderPath) {
		this.setFolderPath = setFolderPath;
	}

	public int getSetAuthor() {
		return setAuthor;
	}

	public void setSetAuthor(int setAuthor) {
		this.setAuthor = setAuthor;
	}

	public Timestamp getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(Timestamp buildTime) {
		this.buildTime = buildTime;
	}

	public String getSetExp() {
		return setExp;
	}

	public void setSetExp(String setExp) {
		this.setExp = setExp;
	}

	@Override
	public String toString() {
		return "ClassicTextSet [buildTime=" + buildTime + ", rankNum="
				+ rankNum + ", setAuthor=" + setAuthor + ", setExp=" + setExp
				+ ", setFolderPath=" + setFolderPath + ", setId=" + setId
				+ ", setLimitScore=" + setLimitScore + ", setName=" + setName
				+ ", setNickName=" + setNickName + "]";
	}

}
