package ims.nlp.entity.model;

import ims.site.model.Site;

public class ClassifyResBuff {

	private int classifyResId;

	private String classifyLogId; // ӳ�䵽����������־��
	private int textId; // ӳ�䵽������Ϣ��
	private int resSetId; // ӳ�䵽���������������
	private int blongSetId; // ӳ�䵽����������Ϣ���������
	private int labeledSetId; // ӳ�䵽����������Ϣ��������
	private int siteId;

	private double limitScore;
	private double trulyScore;
	private String classifyFilePath;

	// �������
	private ClassifyLog classifyLog;
	private CorpusText corpusText;
	private ClassicTextSet resClassicTextSet;
	private ClassicTextSet labeledClassicTextSet;
	private Site site;

	public ClassifyResBuff() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClassifyResBuff(String classifyLogId, int textId, int resSetId,
			int blongSetId, int labeledSetId, int siteId, double limitScore,
			double trulyScore, String classifyFilePath) {
		super();
		this.classifyLogId = classifyLogId;
		this.textId = textId;
		this.resSetId = resSetId;
		this.blongSetId = blongSetId;
		this.labeledSetId = labeledSetId;
		this.siteId = siteId;
		this.limitScore = limitScore;
		this.trulyScore = trulyScore;
		this.classifyFilePath = classifyFilePath;
	}

	public ClassifyResBuff(String classifyLogId, int textId, int resSetId,
			int blongSetId, int labeledSetId, int siteId, double limitScore,
			double trulyScore, String classifyFilePath,
			ClassifyLog classifyLog, CorpusText corpusText,
			ClassicTextSet resClassicTextSet,
			ClassicTextSet labeledClassicTextSet, Site site) {
		super();
		this.classifyLogId = classifyLogId;
		this.textId = textId;
		this.resSetId = resSetId;
		this.blongSetId = blongSetId;
		this.labeledSetId = labeledSetId;
		this.siteId = siteId;
		this.limitScore = limitScore;
		this.trulyScore = trulyScore;
		this.classifyFilePath = classifyFilePath;
		this.classifyLog = classifyLog;
		this.corpusText = corpusText;
		this.resClassicTextSet = resClassicTextSet;
		this.labeledClassicTextSet = labeledClassicTextSet;
		this.site = site;
	}

	public ClassifyResBuff(int classifyResId, String classifyLogId, int textId,
			int resSetId, int blongSetId, int labeledSetId, int siteId,
			double limitScore, double trulyScore, String classifyFilePath,
			ClassifyLog classifyLog, CorpusText corpusText,
			ClassicTextSet resClassicTextSet,
			ClassicTextSet labeledClassicTextSet, Site site) {
		super();
		this.classifyResId = classifyResId;
		this.classifyLogId = classifyLogId;
		this.textId = textId;
		this.resSetId = resSetId;
		this.blongSetId = blongSetId;
		this.labeledSetId = labeledSetId;
		this.siteId = siteId;
		this.limitScore = limitScore;
		this.trulyScore = trulyScore;
		this.classifyFilePath = classifyFilePath;
		this.classifyLog = classifyLog;
		this.corpusText = corpusText;
		this.resClassicTextSet = resClassicTextSet;
		this.labeledClassicTextSet = labeledClassicTextSet;
		this.site = site;
	}

	public int getClassifyResId() {
		return classifyResId;
	}

	public void setClassifyResId(int classifyResId) {
		this.classifyResId = classifyResId;
	}

	public String getClassifyLogId() {
		return classifyLogId;
	}

	public void setClassifyLogId(String classifyLogId) {
		this.classifyLogId = classifyLogId;
	}

	public int getTextId() {
		return textId;
	}

	public void setTextId(int textId) {
		this.textId = textId;
	}

	public int getResSetId() {
		return resSetId;
	}

	public void setResSetId(int resSetId) {
		this.resSetId = resSetId;
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

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public double getLimitScore() {
		return limitScore;
	}

	public void setLimitScore(double limitScore) {
		this.limitScore = limitScore;
	}

	public double getTrulyScore() {
		return trulyScore;
	}

	public void setTrulyScore(double trulyScore) {
		this.trulyScore = trulyScore;
	}

	public String getClassifyFilePath() {
		return classifyFilePath;
	}

	public void setClassifyFilePath(String classifyFilePath) {
		this.classifyFilePath = classifyFilePath;
	}

	public ClassifyLog getClassifyLog() {
		return classifyLog;
	}

	public void setClassifyLog(ClassifyLog classifyLog) {
		this.classifyLog = classifyLog;
	}

	public CorpusText getCorpusText() {
		return corpusText;
	}

	public void setCorpusText(CorpusText corpusText) {
		this.corpusText = corpusText;
	}

	public ClassicTextSet getResClassicTextSet() {
		return resClassicTextSet;
	}

	public void setResClassicTextSet(ClassicTextSet resClassicTextSet) {
		this.resClassicTextSet = resClassicTextSet;
	}

	public ClassicTextSet getLabeledClassicTextSet() {
		return labeledClassicTextSet;
	}

	public void setLabeledClassicTextSet(ClassicTextSet labeledClassicTextSet) {
		this.labeledClassicTextSet = labeledClassicTextSet;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Override
	public String toString() {
		return "ClassifyResBuff [blongSetId=" + blongSetId
				+ ", classifyFilePath=" + classifyFilePath + ", classifyLog="
				+ classifyLog + ", classifyLogId=" + classifyLogId
				+ ", classifyResId=" + classifyResId + ", corpusText="
				+ corpusText + ", labeledClassicTextSet="
				+ labeledClassicTextSet + ", labeledSetId=" + labeledSetId
				+ ", limitScore=" + limitScore + ", resClassicTextSet="
				+ resClassicTextSet + ", resSetId=" + resSetId + ", site="
				+ site + ", siteId=" + siteId + ", textId=" + textId
				+ ", trulyScore=" + trulyScore + "]";
	}

}
