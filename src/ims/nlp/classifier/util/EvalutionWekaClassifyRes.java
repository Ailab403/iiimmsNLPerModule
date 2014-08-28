package ims.nlp.classifier.util;

import ims.nlp.entity.model.ClassicTextSet;
import ims.nlp.entity.model.ClassifyLog;
import ims.nlp.entity.model.ClassifySetEvalution;
import ims.nlp.entity.service.ClassicTextSetService;
import ims.nlp.entity.service.ClassifyLogService;
import ims.nlp.entity.service.ClassifyResBuffService;
import ims.nlp.entity.service.ClassifySetEvalutionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * weka���Խ������ִ���ࣨ��ע�룩
 * 
 * @author superhy
 * 
 */
public class EvalutionWekaClassifyRes {

	// ����ͳ��������ӳ�伯��
	private Map<Integer, Map<String, Integer>> evalutionNumMap = new HashMap<Integer, Map<String, Integer>>();

	// ע���õ��ķ�����
	private ClassifyLogService classifyLogService;
	private ClassifyResBuffService classifyResBuffService;
	private ClassifySetEvalutionService classifySetEvalutionService;
	private ClassicTextSetService classicTextSetService;

	/**
	 * ������������õ�ͳ��������ʼ��ͳ��������ӳ�伯��
	 * 
	 * @param classifyLogId
	 */
	public void initEvalutionNum(String classifyLogId) {
		// �õ����з����������ʵ��
		List<ClassicTextSet> classicTextSets = this.classicTextSetService
				.listAllSetNeg();

		// ����ÿ�����ķ������ָ��ͳ����Ŀ����������ͳ����ӳ�伯��
		for (ClassicTextSet classicTextSet : classicTextSets) {
			Map<String, Object> setIdAndLogIdMap = new HashMap<String, Object>();
			setIdAndLogIdMap.put("setId", classicTextSet.getSetId());
			setIdAndLogIdMap.put("logId", classifyLogId);

			int Li = this.classifyResBuffService.listByResLabeledSetIdAndLogId(
					setIdAndLogIdMap).size();
			int Mi = this.classifyResBuffService.listByResSetIdAndLogId(
					setIdAndLogIdMap).size();
			int Ni = this.classifyResBuffService
					.listByLabeledSetIdAndLogIdGroupTextId(setIdAndLogIdMap)
					.size();
			Map<String, Integer> setEvalutionNumMap = new HashMap<String, Integer>();
			setEvalutionNumMap.put("Li", Li);
			setEvalutionNumMap.put("Mi", Mi);
			setEvalutionNumMap.put("Ni", Ni);

			this.evalutionNumMap.put(classicTextSet.getSetId(),
					setEvalutionNumMap);
		}
	}

	/**
	 * ���㵥�����Ĳ����ɼ������������ݿ�
	 * 
	 * @param classifyLogId
	 */
	public void calculateSetEvalution(String classifyLogId) {

		for (Integer setId : this.evalutionNumMap.keySet()) {
			int Li = this.evalutionNumMap.get(setId).get("Li");
			int Mi = this.evalutionNumMap.get(setId).get("Mi");
			int Ni = this.evalutionNumMap.get(setId).get("Ni");

			Double Pi = Mi != 0 ? Li * 1.0 / Mi : 0.0;
			Double Ri = Ni != 0 ? Li * 1.0 / Ni : 0.0;
			Double F1i = (Pi + Ri != 0.0) ? (Pi * Ri * 2) / (Pi + Ri) : 0.0;

			ClassifySetEvalution classifySetEvalution = new ClassifySetEvalution(
					classifyLogId, setId, Pi, Ri, F1i);
			this.classifySetEvalutionService.add(classifySetEvalution);
		}
	}

	/**
	 * ʹ��΢ƽ��������ȫ�ֲ����ɼ������������ݿ�
	 * 
	 * @param classifyLog
	 */
	public void calculateLogEvalution(ClassifyLog classifyLog) {

		int sumLi = 0;
		int sumMi = 0;
		int sumNi = 0;
		for (Integer setId : this.evalutionNumMap.keySet()) {
			sumLi += this.evalutionNumMap.get(setId).get("Li");
			sumMi += this.evalutionNumMap.get(setId).get("Mi");
			sumNi += this.evalutionNumMap.get(setId).get("Ni");
		}

		Double mP = sumMi != 0 ? sumLi * 1.0 / sumMi : 0.0;
		Double mR = sumNi != 0 ? sumLi * 1.0 / sumNi : 0.0;
		Double mF1 = (mP + mR != 0.0) ? (mP * mR * 2) / (mP + mR) : 0.0;

		classifyLog.setPrecisionRatio(mP);
		classifyLog.setRecallRatio(mR);
		classifyLog.setF1TestValue(mF1);
		this.classifyLogService.update(classifyLog);
	}

	/**
	 * ִ�з���������ڷ�������ȫ�ֲ�������������
	 * 
	 * @param classifyLog
	 */
	public void calculateAllEvalution(ClassifyLog classifyLog) {
		this.initEvalutionNum(classifyLog.getClassifyLogId());
		this.calculateSetEvalution(classifyLog.getClassifyLogId());
		this.calculateLogEvalution(classifyLog);
	}

	public ClassifyLogService getClassifyLogService() {
		return classifyLogService;
	}

	public void setClassifyLogService(ClassifyLogService classifyLogService) {
		this.classifyLogService = classifyLogService;
	}

	public ClassifyResBuffService getClassifyResBuffService() {
		return classifyResBuffService;
	}

	public void setClassifyResBuffService(
			ClassifyResBuffService classifyResBuffService) {
		this.classifyResBuffService = classifyResBuffService;
	}

	public ClassifySetEvalutionService getClassifySetEvalutionService() {
		return classifySetEvalutionService;
	}

	public void setClassifySetEvalutionService(
			ClassifySetEvalutionService classifySetEvalutionService) {
		this.classifySetEvalutionService = classifySetEvalutionService;
	}

	public ClassicTextSetService getClassicTextSetService() {
		return classicTextSetService;
	}

	public void setClassicTextSetService(
			ClassicTextSetService classicTextSetService) {
		this.classicTextSetService = classicTextSetService;
	}

}
