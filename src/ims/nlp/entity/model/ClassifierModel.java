package ims.nlp.entity.model;

import java.sql.Timestamp;

public class ClassifierModel {

	private int modelId;
	private String modelName;
	private String modelObj;
	private String arffName;
	private String modelPath;
	private String modelTool;
	private String modelParame;
	private String backloadParame;
	private int activeState;
	private Timestamp modelCreateTime;
	private String modelExp;

	public ClassifierModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClassifierModel(String modelName, String modelObj, String arffName,
			String modelPath, String modelTool, String modelParame,
			String backloadParame, int activeState, Timestamp modelCreateTime,
			String modelExp) {
		super();
		this.modelName = modelName;
		this.modelObj = modelObj;
		this.arffName = arffName;
		this.modelPath = modelPath;
		this.modelTool = modelTool;
		this.modelParame = modelParame;
		this.backloadParame = backloadParame;
		this.activeState = activeState;
		this.modelCreateTime = modelCreateTime;
		this.modelExp = modelExp;
	}

	public ClassifierModel(int modelId, String modelName, String modelObj,
			String arffName, String modelPath, String modelTool,
			String modelParame, String backloadParame, int activeState,
			Timestamp modelCreateTime, String modelExp) {
		super();
		this.modelId = modelId;
		this.modelName = modelName;
		this.modelObj = modelObj;
		this.arffName = arffName;
		this.modelPath = modelPath;
		this.modelTool = modelTool;
		this.modelParame = modelParame;
		this.backloadParame = backloadParame;
		this.activeState = activeState;
		this.modelCreateTime = modelCreateTime;
		this.modelExp = modelExp;
	}

	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelObj() {
		return modelObj;
	}

	public void setModelObj(String modelObj) {
		this.modelObj = modelObj;
	}

	public String getArffName() {
		return arffName;
	}

	public void setArffName(String arffName) {
		this.arffName = arffName;
	}

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

	public String getModelTool() {
		return modelTool;
	}

	public void setModelTool(String modelTool) {
		this.modelTool = modelTool;
	}

	public String getModelParame() {
		return modelParame;
	}

	public void setModelParame(String modelParame) {
		this.modelParame = modelParame;
	}

	public String getBackloadParame() {
		return backloadParame;
	}

	public void setBackloadParame(String backloadParame) {
		this.backloadParame = backloadParame;
	}

	public int getActiveState() {
		return activeState;
	}

	public void setActiveState(int activeState) {
		this.activeState = activeState;
	}

	public Timestamp getModelCreateTime() {
		return modelCreateTime;
	}

	public void setModelCreateTime(Timestamp modelCreateTime) {
		this.modelCreateTime = modelCreateTime;
	}

	public String getModelExp() {
		return modelExp;
	}

	public void setModelExp(String modelExp) {
		this.modelExp = modelExp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((modelPath == null) ? 0 : modelPath.hashCode());
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
		ClassifierModel other = (ClassifierModel) obj;
		if (modelPath == null) {
			if (other.modelPath != null)
				return false;
		} else if (!modelPath.equals(other.modelPath))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClassifierModel [modelId=" + modelId + ", modelName="
				+ modelName + ", modelObj=" + modelObj + ", arffName="
				+ arffName + ", modelPath=" + modelPath + ", modelTool="
				+ modelTool + ", modelParame=" + modelParame
				+ ", backloadParame=" + backloadParame + ", activeState="
				+ activeState + ", modelCreateTime=" + modelCreateTime
				+ ", modelExp=" + modelExp + "]";
	}

}
