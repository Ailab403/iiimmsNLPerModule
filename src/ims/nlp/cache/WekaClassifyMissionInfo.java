package ims.nlp.cache;


public class WekaClassifyMissionInfo {
	
	public static Boolean MISSION_RUN_FLAG = false;

	/*
	 * mission type
	 */
	// ѵ���ͷ�������
	public static int TRAIN_CLASSIFY_MISSION = 0;
	// ��ʽ�ͷ�������
	public static int OFFICIAL_CLASSIFY_MISSION = 1;

	/*
	 * mission status
	 */
	public static String[] CLASSIFY_MISSION_STATUS = new String[4];
	static {
		CLASSIFY_MISSION_STATUS[0] = "������������δ��ʼ";
		CLASSIFY_MISSION_STATUS[1] = "������������������";
		CLASSIFY_MISSION_STATUS[2] = "����������˳�����";
		CLASSIFY_MISSION_STATUS[3] = "����������ִ��ʧ��";
	}

	/*
	 * log status
	 */
	public static String[] CLASSIFY_LOG_STATUS = new String[5];
	static {
		CLASSIFY_LOG_STATUS[0] = "����������δ��ʼ";
		CLASSIFY_LOG_STATUS[1] = "�����������ڽ���";
		CLASSIFY_LOG_STATUS[2] = "����������ɷ��๤��";
		CLASSIFY_LOG_STATUS[3] = "��������������⹤��";
		CLASSIFY_LOG_STATUS[4] = "��������ʧ��";
	}
}
