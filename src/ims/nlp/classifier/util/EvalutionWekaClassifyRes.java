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
 * weka测试结果评估执行类（需注入）
 * 
 * @author superhy
 * 
 */
public class EvalutionWekaClassifyRes {

	// 测评统计量缓存映射集合
	private Map<Integer, Map<String, Integer>> evalutionNumMap = new HashMap<Integer, Map<String, Integer>>();

	// 注入用到的服务类
	private ClassifyLogService classifyLogService;
	private ClassifyResBuffService classifyResBuffService;
	private ClassifySetEvalutionService classifySetEvalutionService;
	private ClassicTextSetService classicTextSetService;

	/**
	 * 计算出评估所用的统计量并初始化统计量缓存映射集合
	 * 
	 * @param classifyLogId
	 */
	public void initEvalutionNum(String classifyLogId) {
		// 得到所有非总类的类型实体
		List<ClassicTextSet> classicTextSets = this.classicTextSetService
				.listAllSetNeg();

		// 计算每个类别的分类测试指标统计数目，并加入总统计量映射集合
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
	 * 计算单个类别的测评成绩，并存入数据库
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
	 * 使用微平均法计算全局测评成绩，并存入数据库
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
	 * 执行分类任务对于分类器的全局测评和类别单项测评
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
