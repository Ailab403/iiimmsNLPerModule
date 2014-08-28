package ims.nlp.handle;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import ims.nlp.entity.model.ClassifyMission;
import ims.nlp.entity.service.ClassifyMissionService;

/**
 * 分类总任务信息表操作类（需注入）
 * 
 * @author superhy
 * 
 */
public class HandleClassifyMissionResult {

	private ClassifyMissionService classifyMissionService;

	public ClassifyMission createNewClassifyMission(int modelNum) {

		String missionId = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		Timestamp startTime = Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(new Date()));
		Timestamp endTime = null;
		// modelNum
		int missionStatus = 0;
		String missionExp = "{}";

		ClassifyMission classifyMission = new ClassifyMission(missionId,
				startTime, endTime, modelNum, missionStatus, missionExp);
		this.classifyMissionService.add(classifyMission);

		return classifyMission;
	}

	public void refreshClassifyMission(ClassifyMission oldClassifyMission,
			int missionStatus) {

		Timestamp endTime = null;
		if (missionStatus >= 2) {
			endTime = Timestamp.valueOf(new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(new Date()));
		}

		oldClassifyMission.setEndTime(endTime);
		oldClassifyMission.setMissionStatus(missionStatus);
		this.classifyMissionService.update(oldClassifyMission);
	}

	public ClassifyMissionService getClassifyMissionService() {
		return classifyMissionService;
	}

	public void setClassifyMissionService(
			ClassifyMissionService classifyMissionService) {
		this.classifyMissionService = classifyMissionService;
	}

}
