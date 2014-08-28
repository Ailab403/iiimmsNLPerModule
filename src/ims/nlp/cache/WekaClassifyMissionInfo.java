package ims.nlp.cache;


public class WekaClassifyMissionInfo {
	
	public static Boolean MISSION_RUN_FLAG = false;

	/*
	 * mission type
	 */
	// 训练型分类任务
	public static int TRAIN_CLASSIFY_MISSION = 0;
	// 正式型分类任务
	public static int OFFICIAL_CLASSIFY_MISSION = 1;

	/*
	 * mission status
	 */
	public static String[] CLASSIFY_MISSION_STATUS = new String[4];
	static {
		CLASSIFY_MISSION_STATUS[0] = "分类总任务尚未开始";
		CLASSIFY_MISSION_STATUS[1] = "分类总任务正在运行";
		CLASSIFY_MISSION_STATUS[2] = "分类总任务顺利完成";
		CLASSIFY_MISSION_STATUS[3] = "分类总任务执行失败";
	}

	/*
	 * log status
	 */
	public static String[] CLASSIFY_LOG_STATUS = new String[5];
	static {
		CLASSIFY_LOG_STATUS[0] = "分类任务尚未开始";
		CLASSIFY_LOG_STATUS[1] = "分类任务正在进行";
		CLASSIFY_LOG_STATUS[2] = "分类任务完成分类工作";
		CLASSIFY_LOG_STATUS[3] = "分类任务完成评测工作";
		CLASSIFY_LOG_STATUS[4] = "分类任务失败";
	}
}
