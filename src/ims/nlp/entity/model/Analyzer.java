package ims.nlp.entity.model;

public class Analyzer {

	// 分词器信息表

	private int analyzerId;
	private String analyzerName;
	private String analyzerObj;
	private String wordDicPath;
	private String analyzerExp;

	public Analyzer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Analyzer(String analyzerName, String analyzerObj,
			String wordDicPath, String analyzerExp) {
		super();
		this.analyzerName = analyzerName;
		this.analyzerObj = analyzerObj;
		this.wordDicPath = wordDicPath;
		this.analyzerExp = analyzerExp;
	}

	public Analyzer(int analyzerId, String analyzerName, String analyzerObj,
			String wordDicPath, String analyzerExp) {
		super();
		this.analyzerId = analyzerId;
		this.analyzerName = analyzerName;
		this.analyzerObj = analyzerObj;
		this.wordDicPath = wordDicPath;
		this.analyzerExp = analyzerExp;
	}

	public int getAnalyzerId() {
		return analyzerId;
	}

	public void setAnalyzerId(int analyzerId) {
		this.analyzerId = analyzerId;
	}

	public String getAnalyzerName() {
		return analyzerName;
	}

	public void setAnalyzerName(String analyzerName) {
		this.analyzerName = analyzerName;
	}

	public String getAnalyzerObj() {
		return analyzerObj;
	}

	public void setAnalyzerObj(String analyzerObj) {
		this.analyzerObj = analyzerObj;
	}

	public String getWordDicPath() {
		return wordDicPath;
	}

	public void setWordDicPath(String wordDicPath) {
		this.wordDicPath = wordDicPath;
	}

	public String getAnalyzerExp() {
		return analyzerExp;
	}

	public void setAnalyzerExp(String analyzerExp) {
		this.analyzerExp = analyzerExp;
	}

	@Override
	public String toString() {
		return "Analyzer [analyzerId=" + analyzerId + ", analyzerName="
				+ analyzerName + ", analyzerObj=" + analyzerObj
				+ ", wordDicPath=" + wordDicPath + ", analyzerExp="
				+ analyzerExp + "]";
	}

}
