package ims.synWords.model;

/**
 * Model����ͬ�����ز���
 * @author WQS
 *
 */
public class SynWords implements java.io.Serializable{
	private static final long serialVersionUID = -4794151864336777944L;
	
	private int         synWordId;        // ͬ��ʱ��
	private String      mainWordCnt;      // ͬ�������
	private String 		synWordStr;       // ͬ��ʼ����ַ���
	private String      saveDate;         // ����ʱ��
	
	//---------------------------------SynWords�Ĺ��췽��-------------------------------------------
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
