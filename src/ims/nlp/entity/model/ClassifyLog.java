package ims.nlp.entity.model;

import java.sql.Timestamp;

public class ClassifyLog {

	private String classifyLogId;

	private String missionId; // 映射到分类总任务信息表
	private int modelId; // 映射到分类模型表

	private int classifyType;
	private Timestamp startTime;
	private String costTime;
	private int handleFileNum;
	private double precisionRatio;
	private double recallRatio;
	private double f1TestValue;
	private int classifyStatus;
	private String classifyExp;

	// 外键关联
	private ClassifyMission classifyMission;
	private ClassifierModel classifierModel;

	public ClassifyLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClassifyLog(String missionId, int modelId, int classifyType,
			Timestamp startTime, String costTime, int handleFileNum,
			double precisionRatio, double recallRatio, double f1TestValue,
			int classifyStatus, String classifyExp,
			ClassifyMission classifyMission, ClassifierModel classifierModel) {
		super();
		this.missionId = missionId;
		this.modelId = modelId;
		this.classifyType = classifyType;
		this.startTime = startTime;
		this.costTime = costTime;
		this.handleFileNum = handleFileNum;
		this.precisionRatio = precisionRatio;
		this.recallRatio = recallRatio;
		this.f1TestValue = f1TestValue;
		this.classifyStatus = classifyStatus;
		this.classifyExp = classifyExp;
		this.classifyMission = classifyMission;
		this.classifierModel = classifierModel;
	}

	public ClassifyLog(String classifyLogId, String missionId, int modelId,
			int classifyType, Timestamp startTime, String costTime,
			int handleFileNum, double precisionRatio, double recallRatio,
			double f1TestValue, int classifyStatus, String classifyExp,
			ClassifyMission classifyMission, ClassifierModel classifierModel) {
		super();
		this.classifyLogId = classifyLogId;
		this.missionId = missionId;
		this.modelId = modelId;
		this.classifyType = classifyType;
		this.startTime = startTime;
		this.costTime = costTime;
		this.handleFileNum = handleFileNum;
		this.precisionRatio = precisionRatio;
		this.recallRatio = recallRatio;
		this.f1TestValue = f1TestValue;
		this.classifyStatus = classifyStatus;
		this.classifyExp = classifyExp;
		this.classifyMission = classifyMission;
		this.classifierModel = classifierModel;
	}

	public String getClassifyLogId() {
		return classifyLogId;
	}

	public void setClassifyLogId(String classifyLogId) {
		this.classifyLogId = classifyLogId;
	}

	public String getMissionId() {
		return missionId;
	}

	public void setMissionId(String missionId) {
		this.missionId = missionId;
	}

	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public int getClassifyType() {
		return classifyType;
	}

	public void setClassifyType(int classifyType) {
		this.classifyType = classifyType;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public String getCostTime() {
		return costTime;
	}

	public void setCostTime(String costTime) {
		this.costTime = costTime;
	}

	public int getHandleFileNum() {
		return handleFileNum;
	}

	public void setHandleFileNum(int handleFileNum) {
		this.handleFileNum = handleFileNum;
	}

	public double getPrecisionRatio() {
		return precisionRatio;
	}

	public void setPrecisionRatio(double precisionRatio) {
		this.precisionRatio = precisionRatio;
	}

	public double getRecallRatio() {
		return recallRatio;
	}

	public void setRecallRatio(double recallRatio) {
		this.recallRatio = recallRatio;
	}

	public double getF1TestValue() {
		return f1TestValue;
	}

	public void setF1TestValue(double f1TestValue) {
		this.f1TestValue = f1TestValue;
	}

	public int getClassifyStatus() {
		return classifyStatus;
	}

	public void setClassifyStatus(int classifyStatus) {
		this.classifyStatus = classifyStatus;
	}

	public String getClassifyExp() {
		return classifyExp;
	}

	public void setClassifyExp(String classifyExp) {
		this.classifyExp = classifyExp;
	}

	public ClassifyMission getClassifyMission() {
		return classifyMission;
	}

	public void setClassifyMission(ClassifyMission classifyMission) {
		this.classifyMission = classifyMission;
	}

	public ClassifierModel getClassifierModel() {
		return classifierModel;
	}

	public void setClassifierModel(ClassifierModel classifierModel) {
		this.classifierModel = classifierModel;
	}

	@Override
	public String toString() {
		return "ClassifyLog [classifierModel=" + classifierModel
				+ ", classifyExp=" + classifyExp + ", classifyLogId="
				+ classifyLogId + ", classifyMission=" + classifyMission
				+ ", classifyStatus=" + classifyStatus + ", classifyType="
				+ classifyType + ", costTime=" + costTime + ", f1TestValue="
				+ f1TestValue + ", handleFileNum=" + handleFileNum
				+ ", missionId=" + missionId + ", modelId=" + modelId
				+ ", precisionRatio=" + precisionRatio + ", recallRatio="
				+ recallRatio + ", startTime=" + startTime + "]";
	}

}
