package ims.nlp.entity.model;

import java.sql.Timestamp;

public class ClassifyMission {

	private String missionId;
	private Timestamp startTime;
	private Timestamp endTime;
	private int modelNum;
	private int missionStatus;
	private String missionExp;

	public ClassifyMission() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClassifyMission(Timestamp startTime, Timestamp endTime,
			int modelNum, int missionStatus, String missionExp) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.modelNum = modelNum;
		this.missionStatus = missionStatus;
		this.missionExp = missionExp;
	}

	public ClassifyMission(String missionId, Timestamp startTime,
			Timestamp endTime, int modelNum, int missionStatus,
			String missionExp) {
		super();
		this.missionId = missionId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.modelNum = modelNum;
		this.missionStatus = missionStatus;
		this.missionExp = missionExp;
	}

	public String getMissionId() {
		return missionId;
	}

	public void setMissionId(String missionId) {
		this.missionId = missionId;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public int getModelNum() {
		return modelNum;
	}

	public void setModelNum(int modelNum) {
		this.modelNum = modelNum;
	}

	public int getMissionStatus() {
		return missionStatus;
	}

	public void setMissionStatus(int missionStatus) {
		this.missionStatus = missionStatus;
	}

	public String getMissionExp() {
		return missionExp;
	}

	public void setMissionExp(String missionExp) {
		this.missionExp = missionExp;
	}

	@Override
	public String toString() {
		return "ClassifyMission [endTime=" + endTime + ", missionExp="
				+ missionExp + ", missionId=" + missionId + ", missionStatus="
				+ missionStatus + ", modelNum=" + modelNum + ", startTime="
				+ startTime + "]";
	}

}
