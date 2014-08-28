package ims.nlp.classifier.exec;

import ims.nlp.cache.ClassifyTestFileMapPool;
import ims.nlp.cache.WekaClassifyMissionInfo;
import ims.nlp.classifier.util.EvalutionWekaClassifyRes;
import ims.nlp.classifier.util.PrepareTextForClassifying;
import ims.nlp.classifier.weka.WekaClassifyingTextOnDisk;
import ims.nlp.entity.model.ClassifierModel;
import ims.nlp.entity.model.ClassifyLog;
import ims.nlp.entity.model.ClassifyMission;
import ims.nlp.entity.model.CorpusText;
import ims.nlp.handle.HandleClassifyLogResult;
import ims.nlp.handle.HandleClassifyMissionResult;
import ims.nlp.handle.HandleClassifyResBuffResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ��������ִ���ࣨ��ע�룩
 * 
 * @author superhy
 * 
 */
public class ExecWekaClassifyingMission {

	// ������������Ϣʵ��
	private ClassifyMission classifyMission;
	// ִ���ཫҪ����ķ��������б����ģ�Ͷ�����񣬴���ִ�У�
	private List<ClassifyLog> classifyLogs = null;

	// ������������Ϣʵ�崦���ࣨע�룩
	private HandleClassifyMissionResult handleClassifyMissionResult;
	// ����������־ʵ�崦���ࣨע�룩
	private HandleClassifyLogResult handleClassifyLogResult;
	// ������Ĳ����ࣨע�룩
	private WekaClassifyingTextOnDisk wekaClassifyingTextOnDisk;
	// �����ı�׼�������ࣨע�룩
	private PrepareTextForClassifying prepareTextForClassifying;
	// �����������ʵ�崦���ࣨע�룩
	private HandleClassifyResBuffResult handleClassifyResBuffResult;
	// ���������������ࣨע�룩
	private EvalutionWekaClassifyRes evalutionWekaClassifyRes;

	/**
	 * û���Ѿ���ʼ����δ�������������½�����
	 * 
	 * @param classifierModels
	 * @param missionType
	 * @return
	 */
	public Boolean initClassifyMission(List<ClassifierModel> classifierModels,
			int missionType) {
		try {
			// �½�������������־
			ClassifyMission classifyMission = this.handleClassifyMissionResult
					.createNewClassifyMission(classifierModels.size());
			setClassifyMission(classifyMission);

			// ׼�����������־�б�ÿ��ģ��ר��һ����־��
			List<ClassifyLog> newClassifyLogs = new ArrayList<ClassifyLog>();
			for (ClassifierModel eachClassifierModel : classifierModels) {
				ClassifyLog eachNewClassifyLog = this.handleClassifyLogResult
						.createNewClassifyLog(classifyMission,
								eachClassifierModel, missionType);
				newClassifyLogs.add(eachNewClassifyLog);
			}

			setClassifyLogs(newClassifyLogs);

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return false;
		}
	}

	/**
	 * ���Ѿ���ʼ����δ������������������Щ����
	 * 
	 * @param oldClassifyLogs
	 * @return
	 */
	@Deprecated
	public Boolean loadClassifyMission(List<ClassifyLog> oldClassifyLogs) {
		try {
			setClassifyMission(oldClassifyLogs.get(0).getClassifyMission());
			setClassifyLogs(oldClassifyLogs);

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return false;
		}
	}

	/*
	 * ִ�з��������ִ�з����������Ĳ�����Ҫ����һ����ã������ں˲��ṩ����������ֿ���д�����㼰ʱ���ظ��������ĳɰ���Ϣ��ǰ̨��ʱ��ʾ
	 */

	/**
	 * ִ�з��������ȡ�����������
	 * 
	 * @return
	 */
	public Boolean execClassifyingOpt() {

		// �Ƚ��б�Ҫ�ļ�飬���ͨ������ִ��������Ϣ�б�
		List<ClassifyLog> execClassifyLogs = this.execOptNessCheck();
		if (execClassifyLogs == null) {
			return false;
		}

		try {
			// �Ƚ���������Ϣ��״̬�ĳ����ڽ���
			this.handleClassifyMissionResult.refreshClassifyMission(
					this.classifyMission, 1);

			// ��������������Ϊ�˱�����������ļ����ң�
			for (ClassifyLog eachExecClassifyLog : execClassifyLogs) {
				// ���Ƚ��÷��������״̬��Ϊ1�����ڽ����У�
				this.handleClassifyLogResult.refreshClassifyLogInHalfway(
						eachExecClassifyLog, 1, null, false);

				// ִ�з����������ô�����ķ�����
				List<Map<Integer, Map<String, Double>>> classifyRes = this.wekaClassifyingTextOnDisk
						.execClassifyInstances(
								eachExecClassifyLog.getModelId(), true);

				/*
				 * ������������������������ݱ�
				 */
				// ���Ȱ�˳�������в����ļ����ļ���
				List<String> testFileNames = this.wekaClassifyingTextOnDisk
						.getTestTextInfo();
				for (int i = 0; i < classifyRes.size(); i++) {
					String eachTestFileName = testFileNames.get(i);
					CorpusText eachTestText = ClassifyTestFileMapPool
							.loadTextFromPool(eachTestFileName);
					if (eachTestText == null) {
						continue;
					}

					Map<Integer, Map<String, Double>> eachFileClassifyRes = classifyRes
							.get(i);
					// ���ñ��൱�еĽ�����������н������
					this.dealClassifyRes(eachExecClassifyLog, eachTestText,
							eachFileClassifyRes);
				}

				// �������������û��������⣬������״̬�Ͳ��������ٶ�ˢ��
				this.handleClassifyLogResult.refreshClassifyLogInHalfway(
						eachExecClassifyLog, 2, ClassifyTestFileMapPool
								.getFileOrderMapPoolSize(), true);
			}

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			for (ClassifyLog eachExecClassifyLog : this.classifyLogs) {
				this.handleClassifyLogResult.refreshClassifyLogInHalfway(
						eachExecClassifyLog, 4, 0, false);
			}
			this.handleClassifyMissionResult.refreshClassifyMission(
					this.classifyMission, 3);

			return false;
		}
	}

	/**
	 * ��ղ����ļ���
	 */
	public void clearTestFileFolder() {
		// ��������Ѿ�����������������ļ����ڵ��ļ���գ��������ļ���������
		this.prepareTextForClassifying.clearTestFileFolder();

	}

	/**
	 * ����������������
	 * 
	 * @return
	 */
	public Boolean evalutionClassifyRes() {
		if (classifyLogs == null) {
			return false;
		}

		try {
			for (ClassifyLog eachClassifyLog : classifyLogs) {
				if (eachClassifyLog.getClassifyType() == WekaClassifyMissionInfo.TRAIN_CLASSIFY_MISSION) {
					// �����ѵ�����񣬲��в����������ͱ�Ҫ
					this.evalutionWekaClassifyRes
							.calculateAllEvalution(eachClassifyLog);
				}

				this.handleClassifyLogResult.refreshClassifyLogInHalfway(
						eachClassifyLog, 3, null, false);
			}

			// ����������Ϣ���޸�״̬
			this.handleClassifyMissionResult.refreshClassifyMission(
					this.classifyMission, 2);

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			for (ClassifyLog eachExecClassifyLog : this.classifyLogs) {
				this.handleClassifyLogResult.refreshClassifyLogInHalfway(
						eachExecClassifyLog, 4, 0, false);
			}
			this.handleClassifyMissionResult.refreshClassifyMission(
					this.classifyMission, 3);

			return false;
		}
	}

	/**
	 * ִ�з������ǰ���б�Ҫ�ļ��
	 * 
	 * @return
	 */
	public List<ClassifyLog> execOptNessCheck() {
		// ��������ļ����ļ���Ϣ����Ϊ�գ���ʼ��֮
		if (this.prepareTextForClassifying.checkTestFilePoolEmpty()) {
			this.prepareTextForClassifying.initTestFilePoolByFolder();
		}
		if (this.prepareTextForClassifying.checkTestFilePoolEmpty()) {
			// �����ʼ��֮�󻺴�ػ���Ϊ�գ�˵������û����������ı�������false
			System.err.println("�����ļ���Ϊ�գ�����׼�������ļ���");
			return null;
		}

		// �õ������������־�б���ģ�����֣�
		List<ClassifyLog> execClassifyLogs = getClassifyLogs();
		if (execClassifyLogs == null) {
			// ����������־Ϊ�գ�������� ������false����ִ�з�������ʧ��
			System.err.println("����������־Ϊ�գ����鲢���������룡");
			return null;
		}

		return execClassifyLogs;
	}

	/**
	 * �������ִ�з����õ�������ķ�����
	 * 
	 * @param execClassifyLog
	 * @param testCorpusText
	 * @param fileClassifyRes
	 */
	public void dealClassifyRes(ClassifyLog execClassifyLog,
			CorpusText testCorpusText,
			Map<Integer, Map<String, Double>> fileClassifyRes) {

		// ����map�����е�����������Լ����Ӧ�ĵ÷�
		for (Integer resSetId : fileClassifyRes.keySet()) {
			// ��������ֵ���ĵ��ڸ�����µ�ʵ�ʵ÷�
			Double limitScore = fileClassifyRes.get(resSetId).get("limitScore");
			Double trulyScore = fileClassifyRes.get(resSetId).get("trulyScore");

			this.handleClassifyResBuffResult.createNewResBuff(execClassifyLog,
					testCorpusText, resSetId, limitScore, trulyScore);
		}
	}

	public ClassifyMission getClassifyMission() {
		return classifyMission;
	}

	public void setClassifyMission(ClassifyMission classifyMission) {
		this.classifyMission = classifyMission;
	}

	public List<ClassifyLog> getClassifyLogs() {
		return classifyLogs;
	}

	public void setClassifyLogs(List<ClassifyLog> classifyLogs) {
		this.classifyLogs = classifyLogs;
	}

	public HandleClassifyMissionResult getHandleClassifyMissionResult() {
		return handleClassifyMissionResult;
	}

	public void setHandleClassifyMissionResult(
			HandleClassifyMissionResult handleClassifyMissionResult) {
		this.handleClassifyMissionResult = handleClassifyMissionResult;
	}

	public HandleClassifyLogResult getHandleClassifyLogResult() {
		return handleClassifyLogResult;
	}

	public void setHandleClassifyLogResult(
			HandleClassifyLogResult handleClassifyLogResult) {
		this.handleClassifyLogResult = handleClassifyLogResult;
	}

	public WekaClassifyingTextOnDisk getWekaClassifyingTextOnDisk() {
		return wekaClassifyingTextOnDisk;
	}

	public void setWekaClassifyingTextOnDisk(
			WekaClassifyingTextOnDisk wekaClassifyingTextOnDisk) {
		this.wekaClassifyingTextOnDisk = wekaClassifyingTextOnDisk;
	}

	public PrepareTextForClassifying getPrepareTextForClassifying() {
		return prepareTextForClassifying;
	}

	public void setPrepareTextForClassifying(
			PrepareTextForClassifying prepareTextForClassifying) {
		this.prepareTextForClassifying = prepareTextForClassifying;
	}

	public HandleClassifyResBuffResult getHandleClassifyResBuffResult() {
		return handleClassifyResBuffResult;
	}

	public void setHandleClassifyResBuffResult(
			HandleClassifyResBuffResult handleClassifyResBuffResult) {
		this.handleClassifyResBuffResult = handleClassifyResBuffResult;
	}

	public EvalutionWekaClassifyRes getEvalutionWekaClassifyRes() {
		return evalutionWekaClassifyRes;
	}

	public void setEvalutionWekaClassifyRes(
			EvalutionWekaClassifyRes evalutionWekaClassifyRes) {
		this.evalutionWekaClassifyRes = evalutionWekaClassifyRes;
	}

}
