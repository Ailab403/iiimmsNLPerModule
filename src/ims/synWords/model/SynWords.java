package ims.synWords.model;

/**
 * Model层总同义词相关参数
 * @author WQS
 *
 */
public class SynWords implements java.io.Serializable{
	private static final long serialVersionUID = -4794151864336777944L;
	
	private int         synWordId;        // 同义词编号
	private String      mainWordCnt;      // 同义词主词
	private String 		synWordStr;       // 同义词集合字符串
	private String      saveDate;         // 创建时间
	
	//---------------------------------SynWords的构造方法-------------------------------------------
	public SynWords() {
		super();
	}
	
	public SynWords(int synWordId, String mainWordCnt, String synWordStr,
			String saveDate) {
		super();
		this.synWordId = synWordId;
		this.mainWordCnt = mainWordCnt;
		this.synWordStr = synWordStr;
		this.saveDate = saveDate;
	}

	//-------------------------------------setter/getter--------------------------------------------
	public int getSynWordId() {
		return synWordId;
	}
	public void setSynWordId(int synWordId) {
		this.synWordId = synWordId;
	}
	public String getMainWordCnt() {
		return mainWordCnt;
	}
	public void setMainWordCnt(String mainWordCnt) {
		this.mainWordCnt = mainWordCnt;
	}
	public String getSynWordStr() {
		return synWordStr;
	}
	public void setSynWordStr(String synWordStr) {
		this.synWordStr = synWordStr;
	}
	public String getSaveDate() {
		return saveDate;
	}
	public void setSaveDate(String saveDate) {
		this.saveDate = saveDate;
	}
}
