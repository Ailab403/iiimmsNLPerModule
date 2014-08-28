package ims.nlp.classifier.weka;

import ims.nlp.cache.WekaClassifierDataPath;
import ims.nlp.entity.model.ClassicTextSet;
import ims.nlp.entity.model.ClassifierModel;
import ims.nlp.entity.service.ClassicTextSetService;
import ims.nlp.entity.service.ClassifierModelService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 执行分类操作，返回分类结果（需要注入）
 * 
 * @author superhy
 * 
 */
public class WekaClassifyingTextOnDisk {

	// 注入需要的服务方法
	private ClassifierModelService classifierModelService;
	private ClassicTextSetService classicTextSetService;

	/**
	 * 加载所有类别的名称
	 * 
	 * @return
	 */
	public List<String> getClassicSetName() {
		// 准备要返回的数据
		List<String> classicSetNames = new ArrayList<String>();

		try {
			File fileTrainDir = new File(
					WekaClassifierDataPath.TRAIN_SEGMENT_SOURCEDIR);
			for (File eachFileInDir : fileTrainDir.listFiles()) {
				if (eachFileInDir.isDirectory()) {
					classicSetNames.add(eachFileInDir.getName());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			System.err.println("扫描训练文件夹发生错误，请检查路径!");
			return null;
		}

		return classicSetNames;
	}

	/**
	 * 加载 所有测试文件的文件名
	 * 
	 * @return
	 */
	public List<String> getTestTextInfo() {
		// 准备要返回的数据
		List<String> testFileNames = new ArrayList<String>();

		try {
			File fileTestDir = new File(
					WekaClassifierDataPath.TEST_FILE_SOURCEFOLDER);
			for (File eachTextFile : fileTestDir.listFiles()) {
				if (eachTextFile.isFile()) {
					testFileNames.add(eachTextFile.getName());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			System.err.println("扫描测试文件夹发生错误，请检查路径或文件夹结构！");
			return null;
		}

		return testFileNames;
	}

	/**
	 * 执行分类操作，并且处理分类的结果
	 * 
	 * @param modelId
	 * @param scoreQuota
	 * @param needArffFlag
	 * @return
	 */
	public List<Map<Integer, Map<String, Double>>> execClassifyInstances(
			int modelId, Boolean needArffFlag) {

		// 准备文本分类需要的参数和模型实体
		ClassifierModel classifierModel = this.classifierModelService
				.loadById(modelId);

		String segmentSourceDir = WekaClassifierDataPath.TEST_SEGMENT_SOURCEDIR;
		String segmentTargetDir = WekaClassifierDataPath.TEST_SEGMENT_TARGETDIR;
		String dataRawPath = WekaClassifierDataPath.TEST_DATARAW_PATH;
		String dataFilterPath = WekaClassifierDataPath.TEST_DATAFILTER_PATH;

		String arffName = classifierModel.getArffName().contains(".arff") ? classifierModel
				.getArffName()
				: (classifierModel.getArffName() + ".arff");
		String unlabeledDataArffPath = WekaClassifierDataPath.TEST_DATADIR_PATH
				+ arffName;
		String labeledDataArffPath = WekaClassifierDataPath.TRAIN_DATADIR_PATH
				+ arffName;

		String modelPath = classifierModel.getModelPath();

		// 取出分类器分类的初步结果
		WekaClassifyingInstances wekaClassifyingInstances = new WekaClassifyingInstances();
		List<Map<String, Object>> classifyFirstRes = new ArrayList<Map<String, Object>>();
		if (!needArffFlag) {
			classifyFirstRes = wekaClassifyingInstances
					.classifyingInstancesWithArff(unlabeledDataArffPath,
							labeledDataArffPath, modelPath);
		} else {
			classifyFirstRes = wekaClassifyingInstances
					.classifyingInstancesWithoutArff(segmentSourceDir,
							segmentTargetDir, dataRawPath, dataFilterPath,
							unlabeledDataArffPath, labeledDataArffPath,
							modelPath);
		}

		// 获得经过评分过滤的分类结果并返回
		return this.filterClassifyRes(classifyFirstRes);
	}

	/**
	 * 过滤分类器返回的初步分类结果
	 * 
	 * @param classifyFirstRes
	 * @return
	 */
	public List<Map<Integer, Map<String, Double>>> filterClassifyRes(
			List<Map<String, Object>> classifyFirstRes) {
		// 加载测试文件夹中所有分类类别的名称
		List<String> classicSetNames = this.getClassicSetName();

		/*
		 * 准备第二遍经过处理过后的分类结果，每个文档详细的分类情况映射对：文档名对应每个命中分类的编号，在对应这个命中分类下的阈值和实际得分
		 */
		List<Map<Integer, Map<String, Double>>> classifySecRes = new ArrayList<Map<Integer, Map<String, Double>>>();

		// 按照顺序对每个文档的分类结果进行处理
		for (int i = 0; i < classifyFirstRes.size(); i++) {
			// 加载每个文档分类的初步结果
			Map<String, Object> eachFileClassifyFirstResMap = classifyFirstRes
					.get(i);

			// 准备这个文件的命中信息
			Map<Integer, Map<String, Double>> eachClassifyRes = new HashMap<Integer, Map<String, Double>>();
			// 检查每个分类的得分有没有达到阈值
			for (String classicSetNickName : classicSetNames) {

				ClassicTextSet classicTextSet = this.classicTextSetService
						.loadByNickName(classicSetNickName);
				if (classicTextSet != null
						&& eachFileClassifyFirstResMap
								.containsKey(classicSetNickName)) {

					// 获得真实得分和阈值
					Double limitScore = classicTextSet.getSetLimitScore();
					Double trulyScore = (Double) eachFileClassifyFirstResMap
							.get(classicSetNickName);
					// 判断是否达标
					if (limitScore <= trulyScore) {
						Map<String, Double> scoreInfoMap = new HashMap<String, Double>();
						scoreInfoMap.put("limitScore", limitScore);
						scoreInfoMap.put("trulyScore", trulyScore);

						eachClassifyRes.put(classicTextSet.getSetId(),
								scoreInfoMap);
					}
				}
			}

			classifySecRes.add(eachClassifyRes);
		}

		return classifySecRes;
	}

	public ClassifierModelService getClassifierModelService() {
		return classifierModelService;
	}

	public void setClassifierModelService(
			ClassifierModelService classifierModelService) {
		this.classifierModelService = classifierModelService;
	}

	public ClassicTextSetService getClassicTextSetService() {
		return classicTextSetService;
	}

	public void setClassicTextSetService(
			ClassicTextSetService classicTextSetService) {
		this.classicTextSetService = classicTextSetService;
	}

}
