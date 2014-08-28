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
 * ����������־���ݿ�����ࣨ��ע�룩
 * 
 * @author superhy
 * 
 */
public class HandleClassifyLogResult {

	private ClassifyLogService classifyLogService;

	public ClassifyLog createNewClassifyLog(ClassifyMission classifyMission,
			ClassifierModel classifierModel, int missionType) {
		// ��ʼ��Id�ǵ�ǰʱ���+��ʹ��ģ��id���м���'-'������
		String classifyLogId = classifyMission.getMissionId() + "-"
				+ classifierModel.getModelId();
		// ��ʹ��ģ��id
		int modelId = classifierModel.getModelId();
		// ������������
		int classifyType = missionType;
		// ����ʼʱ��
		Timestamp startTime = Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(new Date()));
		// �����ʱ
		String costTime = "00:00:00";
		// ������ĵ���Ŀ
		int handleFileNum = 0;
		// ���ֲ���ָ����ֵ
		double precisionRatio = 0.0;
		double recallRatio = 0.0;
		double f1TestValue = 0.0;
		// ��������״̬
		int classifyStatus = 0;
		// ��������˵��
		String classifyExp = "{}";

		// �����µķ�����־ʵ��
		ClassifyLog classifyLog = new ClassifyLog(classifyLogId,
				classifyMission.getMissionId(), modelId, classifyType,
				startTime, costTime, handleFileNum, precisionRatio,
				recallRatio, f1TestValue, classifyStatus, classifyExp,
				classifyMission, classifierModel);
		// �����ݿ�����Ӽ�¼
		this.classifyLogService.add(classifyLog);

		// ���������ɵķ�����־ʵ��
		return classifyLog;
	}

	/**
	 * �ڷ����������֮���޸�������Ϣ�����ݿ���
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
