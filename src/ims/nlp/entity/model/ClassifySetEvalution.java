package ims.nlp.entity.model;

public class ClassifySetEvalution {

	private int evalutionId;

	private String classifyLogId; // 映射到分类任务日志表
	private int setId; // 映射到分类主体信息表

	private double setPrecisionRatio;
	private double setRecallRatio;
	private double setF1TestValue;

	// 外键关联
	private ClassifyLog classifyLog;
	private ClassicTextSet classicTextSet;

	public ClassifySetEvalution() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClassifySetEvalution(String classifyLogId, int setId,
			double setPrecisionRatio, double setRecallRatio,
			double setF1TestValue) {
		super();
		this.classifyLogId = classifyLogId;
		this.setId = setId;
		this.setPrecisionRatio = setPrecisionRatio;
		this.setRecallRatio = setRecallRatio;
		this.setF1TestValue = setF1TestValue;
	}

	public ClassifySetEvalution(String classifyLogId, int setId,
			double setPrecisionRatio, double setRecallRatio,
			double setF1TestValue, ClassifyLog classifyLog,
			ClassicTextSet classicTextSet) {
		super();
		this.classifyLogId = classifyLogId;
		this.setId = setId;
		this.setPrecisionRatio = setPrecisionRatio;
		this.setRecallRatio = setRecallRatio;
		this.setF1TestValue = setF1TestValue;
		this.classifyLog = classifyLog;
		this.classicTextSet = classicTextSet;
	}

	public ClassifySetEvalution(int evalutionId, String classifyLogId,
			int setId, double setPrecisionRatio, double setRecallRatio,
			double setF1TestValue, ClassifyLog classifyLog,
			ClassicTextSet classicTextSet) {
		super();
		this.evalutionId = evalutionId;
		this.classifyLogId = classifyLogId;
		this.setId = setId;
		this.setPrecisionRatio = setPrecisionRatio;
		this.setRecallRatio = setRecallRatio;
		this.setF1TestValue = setF1TestValue;
		this.classifyLog = classifyLog;
		this.classicTextSet = classicTextSet;
	}

	public int getEvalutionId() {
		return evalutionId;
	}

	public void setEvalutionId(int evalutionId) {
		this.evalutionId = evalutionId;
	}

	public String getClassifyLogId() {
		return classifyLogId;
	}

	public void setClassifyLogId(String classifyLogId) {
		this.classifyLogId = classifyLogId;
	}

	public int getSetId() {
		return setId;
	}

	public void setSetId(int setId) {
		this.setId = setId;
	}

	public double getSetPrecisionRatio() {
		return setPrecisionRatio;
	}

	public void setSetPrecisionRatio(double setPrecisionRatio) {
		this.setPrecisionRatio = setPrecisionRatio;
	}

	public double getSetRecallRatio() {
		return setRecallRatio;
	}

	public void setSetRecallRatio(double setRecallRatio) {
		this.setRecallRatio = setRecallRatio;
	}

	public double getSetF1TestValue() {
		return setF1TestValue;
	}

	public void setSetF1TestValue(double setF1TestValue) {
		this.setF1TestValue = setF1TestValue;
	}

	public ClassifyLog getClassifyLog() {
		return classifyLog;
	}

	public void setClassifyLog(ClassifyLog classifyLog) {
		this.classifyLog = classifyLog;
	}

	public ClassicTextSet getClassicTextSet() {
		return classicTextSet;
	}

	public void setClassicTextSet(ClassicTextSet classicTextSet) {
		this.classicTextSet = classicTextSet;
	}

	@Override
	public String toString() {
		return "ClassifySetEvalution [classicTextSet=" + classicTextSet
				+ ", classifyLog=" + classifyLog + ", classifyLogId="
				+ classifyLogId + ", evalutionId=" + evalutionId
				+ ", setF1TestValue=" + setF1TestValue + ", setId=" + setId
				+ ", setPrecisionRatio=" + setPrecisionRatio
				+ ", setRecallRatio=" + setRecallRatio + "]";
	}

}
