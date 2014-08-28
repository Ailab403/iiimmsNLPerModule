package ims.nlp.handle;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import ims.crawler.grab.util.AnalyzerTime;
import ims.nlp.entity.model.ClassifierModel;
import ims.nlp.entity.model.ClassifyLog;
import ims.nlp.entity.model.ClassifyMission;
import ims.nlp.entity.service.ClassifyLogService;

/**
 * 分类任务日志数据库操作类（需注入）
 * 
 * @author superhy
 * 
 */
public class HandleClassifyLogResult {

	private ClassifyLogService classifyLogService;

	public ClassifyLog createNewClassifyLog(ClassifyMission classifyMission,
			ClassifierModel classifierModel, int missionType) {
		// 初始化Id是当前时间戳+所使用模型id（中间用'-'隔开）
		String classifyLogId = classifyMission.getMissionId() + "-"
				+ classifierModel.getModelId();
		// 所使用模型id
		int modelId = classifierModel.getModelId();
		// 分类任务类型
		int classifyType = missionType;
		// 任务开始时间
		Timestamp startTime = Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(new Date()));
		// 任务耗时
		String costTime = "00:00:00";
		// 处理的文档数目
		int handleFileNum = 0;
		// 各种测评指标数值
		double precisionRatio = 0.0;
		double recallRatio = 0.0;
		double f1TestValue = 0.0;
		// 分类任务状态
		int classifyStatus = 0;
		// 分类任务说明
		String classifyExp = "{}";

		// 生成新的分类日志实体
		ClassifyLog classifyLog = new ClassifyLog(classifyLogId,
				classifyMission.getMissionId(), modelId, classifyType,
				startTime, costTime, handleFileNum, precisionRatio,
				recallRatio, f1TestValue, classifyStatus, classifyExp,
				classifyMission, classifierModel);
		// 往数据库中添加纪录
		this.classifyLogService.add(classifyLog);

		// 返回新生成的分类日志实体
		return classifyLog;
	}

	/**
	 * 在分类任务进行之中修改任务信息到数据库中
	 * 
	 * @param classifyStatus
	 * @param handleFileNum
	 * @param calculateTime
	 */
	public void refreshClassifyLogInHalfway(ClassifyLog oldClassifyLog,
			int classifyStatus, Integer handleFileNum, Boolean calculateTime) {

		oldClassifyLog.setClassifyStatus(classifyStatus);
		if (handleFileNum != null) {
			oldClassifyLog.setHandleFileNum(handleFileNum);
		}
		if (calculateTime) {
			Timestamp startTime = oldClassifyLog.getStartTime();
			Timestamp nowTime = Timestamp.valueOf(new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(new Date()));

			String costTime = AnalyzerTime.anayTimeRange(startTime, nowTime);
			oldClassifyLog.setCostTime(costTime);
		}

		this.classifyLogService.update(oldClassifyLog);
	}

	public ClassifyLogService getClassifyLogService() {
		return classifyLogService;
	}

	public void setClassifyLogService(ClassifyLogService classifyLogService) {
		this.classifyLogService = classifyLogService;
	}
}
