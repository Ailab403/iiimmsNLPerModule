package ims.nlp.entity.model;

import ims.crawlerLog.model.TaskLog;
import ims.site.model.Site;

import java.sql.Timestamp;

public class CorpusText {

	// 语料信息表

	private int textId;

	private int blongSetId; // 映射到分类主题信息表
	private int labeledSetId; // 映射到分类主题信息表

	// 映射到mongodb数据库和mysql采集器部分关系表
	private String taskLogId;
	private int siteId;
	private String nodeId;

	private String filePath;
	private int inTestFolder;
	private double polarityScore;
	private double subjectScore;
	private Timestamp buildTime;

	// 外键实体
	private ClassicTextSet blongClassicTextSet;
	private ClassicTextSet labeledClassicTextSet;
	private TaskLog taskLog;
	private Site site;

	public CorpusText() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CorpusText(int blongSetId, int labeledSetId, String taskLogId,
			int siteId, String nodeId, String filePath, int inTestFolder,
			double polarityScore, double subjectScore, Timestamp buildTime,
			ClassicTextSet blongClassicTextSet,
			ClassicTextSet labeledClassicTextSet, TaskLog taskLog, Site site) {
		super();
		this.blongSetId = blongSetId;
		this.labeledSetId = labeledSetId;
		this.taskLogId = taskLogId;
		this.siteId = siteId;
		this.nodeId = nodeId;
		this.filePath = filePath;
		this.inTestFolder = inTestFolder;
		this.polarityScore = polarityScore;
		this.subjectScore = subjectScore;
		this.buildTime = buildTime;
		this.blongClassicTextSet = blongClassicTextSet;
		this.labeledClassicTextSet = labeledClassicTextSet;
		this.taskLog = taskLog;
		this.site = site;
	}

	public CorpusText(int textId, int blongSetId, int labeledSetId,
			String taskLogId, int siteId, String nodeId, String filePath,
			int inTestFolder, double polarityScore, double subjectScore,
			Timestamp buildTime, ClassicTextSet blongClassicTextSet,
			ClassicTextSet labeledClassicTextSet, TaskLog taskLog, Site site) {
		super();
		this.textId = textId;
		this.blongSetId = blongSetId;
		this.labeledSetId = labeledSetId;
		this.taskLogId = taskLogId;
		this.siteId = siteId;
		this.nodeId = nodeId;
		this.filePath = filePath;
		this.inTestFolder = inTestFolder;
		this.polarityScore = polarityScore;
		this.subjectScore = subjectScore;
		this.buildTime = buildTime;
		this.blongClassicTextSet = blongClassicTextSet;
		this.labeledClassicTextSet = labeledClassicTextSet;
		this.taskLog = taskLog;
		this.site = site;
	}

	public int getTextId() {
		return textId;
	}

	public void setTextId(int textId) {
		this.textId = textId;
	}

	public int getBlongSetId() {
		return blongSetId;
	}

	public void setBlongSetId(int blongSetId) {
		this.blongSetId = blongSetId;
	}

	public int getLabeledSetId() {
		return labeledSetId;
	}

	public void setLabeledSetId(int labeledSetId) {
		this.labeledSetId = labeledSetId;
	}

	public String getTaskLogId() {
		return taskLogId;
	}

	public void setTaskLogId(String taskLogId) {
		this.taskLogId = taskLogId;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getInTestFolder() {
		return inTestFolder;
	}

	public void setInTestFolder(int inTestFolder) {
		this.inTestFolder = inTestFolder;
	}

	public double getPolarityScore() {
		return polarityScore;
	}

	public void setPolarityScore(double polarityScore) {
		this.polarityScore = polarityScore;
	}

	public double getSubjectScore() {
		return subjectScore;
	}

	public void setSubjectScore(double subjectScore) {
		this.subjectScore = subjectScore;
	}

	public Timestamp getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(Timestamp buildTime) {
		this.buildTime = buildTime;
	}

	public ClassicTextSet getBlongClassicTextSet() {
		return blongClassicTextSet;
	}

	public void setBlongClassicTextSet(ClassicTextSet blongClassicTextSet) {
		this.blongClassicTextSet = blongClassicTextSet;
	}

	public ClassicTextSet getLabeledClassicTextSet() {
		return labeledClassicTextSet;
	}

	public void setLabeledClassicTextSet(ClassicTextSet labeledClassicTextSet) {
		this.labeledClassicTextSet = labeledClassicTextSet;
	}

	public TaskLog getTaskLog() {
		return taskLog;
	}

	public void setTaskLog(TaskLog taskLog) {
		this.taskLog = taskLog;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + blongSetId;
		result = prime * result + labeledSetId;
		result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CorpusText other = (CorpusText) obj;
		if (blongSetId != other.blongSetId)
			return false;
		if (labeledSetId != other.labeledSetId)
			return false;
		if (nodeId == null) {
			if (other.nodeId != null)
				return false;
		} else if (!nodeId.equals(other.nodeId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CorpusText [blongClassicTextSet=" + blongClassicTextSet
				+ ", blongSetId=" + blongSetId + ", buildTime=" + buildTime
				+ ", filePath=" + filePath + ", inTestFolder=" + inTestFolder
				+ ", labeledClassicTextSet=" + labeledClassicTextSet
				+ ", labeledSetId=" + labeledSetId + ", nodeId=" + nodeId
				+ ", polarityScore=" + polarityScore + ", site=" + site
				+ ", siteId=" + siteId + ", subjectScore=" + subjectScore
				+ ", taskLog=" + taskLog + ", taskLogId=" + taskLogId
				+ ", textId=" + textId + "]";
	}

}
