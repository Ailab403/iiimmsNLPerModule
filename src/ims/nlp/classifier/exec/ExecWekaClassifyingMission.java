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
 * 分类任务执行类（需注入）
 * 
 * @author superhy
 * 
 */
public class ExecWekaClassifyingMission {

	// 分类总任务信息实体
	private ClassifyMission classifyMission;
	// 执行类将要处理的分类任务列表（多个模型多个任务，串行执行）
	private List<ClassifyLog> classifyLogs = null;

	// 分类总任务信息实体处理类（注入）
	private HandleClassifyMissionResult handleClassifyMissionResult;
	// 分类任务日志实体处理类（注入）
	private HandleClassifyLogResult handleClassifyLogResult;
	// 分类核心操作类（注入）
	private WekaClassifyingTextOnDisk wekaClassifyingTextOnDisk;
	// 分类文本准备操作类（注入）
	private PrepareTextForClassifying prepareTextForClassifying;
	// 分类结果缓存表实体处理类（注入）
	private HandleClassifyResBuffResult handleClassifyResBuffResult;
	// 分类结果测评操作类（注入）
	private EvalutionWekaClassifyRes evalutionWekaClassifyRes;

	/**
	 * 没有已经开始（尚未结束）的任务，新建任务
	 * 
	 * @param classifierModels
	 * @param missionType
	 * @return
	 */
	public Boolean initClassifyMission(List<ClassifierModel> classifierModels,
			int missionType) {
		try {
			// 新建分类任务总日志
			ClassifyMission classifyMission = this.handleClassifyMissionResult
					.createNewClassifyMission(classifierModels.size());
			setClassifyMission(classifyMission);

			// 准备新任务的日志列表（每个模型专配一个日志）
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
	 * 有已经开始（尚未结束）的任务，载入这些任务
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
	 * 执行分类操作和执行分类结果测评的操作需要合在一起调用，但是内核不提供打包方法，分开编写，方便及时返回各步操作的成败信息，前台及时提示
	 */

	/**
	 * 执行分类操作获取并处理分类结果
	 * 
	 * @return
	 */
	public Boolean execClassifyingOpt() {

		// 先进行必要的检查，检查通过返回执行任务信息列表
		List<ClassifyLog> execClassifyLogs = this.execOptNessCheck();
		if (execClassifyLogs == null) {
			return false;
		}

		try {
			// 先将总任务信息的状态改成正在进行
			this.handleClassifyMissionResult.refreshClassifyMission(
					this.classifyMission, 1);

			// 串行逐个完成任务（为了避免任务共享的文件混乱）
			for (ClassifyLog eachExecClassifyLog : execClassifyLogs) {
				// 首先将该分类任务的状态改为1（正在进行中）
				this.handleClassifyLogResult.refreshClassifyLogInHalfway(
						eachExecClassifyLog, 1, null, false);

				// 执行分类操作并获得处理过的分类结果
				List<Map<Integer, Map<String, Double>>> classifyRes = this.wekaClassifyingTextOnDisk
						.execClassifyInstances(
								eachExecClassifyLog.getModelId(), true);

				/*
				 * 将分类结果存入分类结果缓存数据表
				 */
				// 首先按顺序获得所有测试文件的文件名
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
					// 调用本类当中的结果处理方法进行结果处理
					this.dealClassifyRes(eachExecClassifyLog, eachTestText,
							eachFileClassifyRes);
				}

				// 分类结束但是尚没有完成评测，将任务状态和部分数据再度刷新
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
	 * 清空测试文件夹
	 */
	public void clearTestFileFolder() {
		// 分类操作已经结束，将分类测试文件夹内的文件清空，将测试文件缓存池清空
		this.prepareTextForClassifying.clearTestFileFolder();

	}

	/**
	 * 分类器分类结果测评
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
					// 如果是训练任务，才有测评的条件和必要
					this.evalutionWekaClassifyRes
							.calculateAllEvalution(eachClassifyLog);
				}

				this.handleClassifyLogResult.refreshClassifyLogInHalfway(
						eachClassifyLog, 3, null, false);
			}

			// 将总任务信息表修改状态
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
	 * 执行分类操作前进行必要的检查
	 * 
	 * @return
	 */
	public List<ClassifyLog> execOptNessCheck() {
		// 如果测试文件夹文件信息缓存为空，初始化之
		if (this.prepareTextForClassifying.checkTestFilePoolEmpty()) {
			this.prepareTextForClassifying.initTestFilePoolByFolder();
		}
		if (this.prepareTextForClassifying.checkTestFilePoolEmpty()) {
			// 如果初始化之后缓存池还是为空，说明根本没有载入测试文本，返回false
			System.err.println("测试文件夹为空，请先准本测试文件！");
			return null;
		}

		// 得到本次任务的日志列表（按模型区分）
		List<ClassifyLog> execClassifyLogs = getClassifyLogs();
		if (execClassifyLogs == null) {
			// 分类任务日志为空，输出错误 ，返回false代表执行分类任务失败
			System.err.println("分类任务日志为空，请检查并且重新载入！");
			return null;
		}

		return execClassifyLogs;
	}

	/**
	 * 处理分类执行方法得到的最初的分类结果
	 * 
	 * @param execClassifyLog
	 * @param testCorpusText
	 * @param fileClassifyRes
	 */
	public void dealClassifyRes(ClassifyLog execClassifyLog,
			CorpusText testCorpusText,
			Map<Integer, Map<String, Double>> fileClassifyRes) {

		// 遍历map容器中的命中类别编号以及其对应的得分
		for (Integer resSetId : fileClassifyRes.keySet()) {
			// 获得类别阈值和文档在该类别下的实际得分
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
