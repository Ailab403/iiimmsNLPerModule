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
 * ִ�з�����������ط���������Ҫע�룩
 * 
 * @author superhy
 * 
 */
public class WekaClassifyingTextOnDisk {

	// ע����Ҫ�ķ��񷽷�
	private ClassifierModelService classifierModelService;
	private ClassicTextSetService classicTextSetService;

	/**
	 * ����������������
	 * 
	 * @return
	 */
	public List<String> getClassicSetName() {
		// ׼��Ҫ���ص�����
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

			System.err.println("ɨ��ѵ���ļ��з�����������·��!");
			return null;
		}

		return classicSetNames;
	}

	/**
	 * ���� ���в����ļ����ļ���
	 * 
	 * @return
	 */
	public List<String> getTestTextInfo() {
		// ׼��Ҫ���ص�����
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

			System.err.println("ɨ������ļ��з�����������·�����ļ��нṹ��");
			return null;
		}

		return testFileNames;
	}

	/**
	 * ִ�з�����������Ҵ������Ľ��
	 * 
	 * @param modelId
	 * @param scoreQuota
	 * @param needArffFlag
	 * @return
	 */
	public List<Map<Integer, Map<String, Double>>> execClassifyInstances(
			int modelId, Boolean needArffFlag) {

		// ׼���ı�������Ҫ�Ĳ�����ģ��ʵ��
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

		// ȡ������������ĳ������
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

		// ��þ������ֹ��˵ķ�����������
		return this.filterClassifyRes(classifyFirstRes);
	}

	/**
	 * ���˷��������صĳ���������
	 * 
	 * @param classifyFirstRes
	 * @return
	 */
	public List<Map<Integer, Map<String, Double>>> filterClassifyRes(
			List<Map<String, Object>> classifyFirstRes) {
		// ���ز����ļ��������з�����������
		List<String> classicSetNames = this.getClassicSetName();

		/*
		 * ׼���ڶ��龭���������ķ�������ÿ���ĵ���ϸ�ķ������ӳ��ԣ��ĵ�����Ӧÿ�����з���ı�ţ��ڶ�Ӧ������з����µ���ֵ��ʵ�ʵ÷�
		 */
		List<Map<Integer, Map<String, Double>>> classifySecRes = new ArrayList<Map<Integer, Map<String, Double>>>();

		// ����˳���ÿ���ĵ��ķ��������д���
		for (int i = 0; i < classifyFirstRes.size(); i++) {
			// ����ÿ���ĵ�����ĳ������
			Map<String, Object> eachFileClassifyFirstResMap = classifyFirstRes
					.get(i);

			// ׼������ļ���������Ϣ
			Map<Integer, Map<String, Double>> eachClassifyRes = new HashMap<Integer, Map<String, Double>>();
			// ���ÿ������ĵ÷���û�дﵽ��ֵ
			for (String classicSetNickName : classicSetNames) {

				ClassicTextSet classicTextSet = this.classicTextSetService
						.loadByNickName(classicSetNickName);
				if (classicTextSet != null
						&& eachFileClassifyFirstResMap
								.containsKey(classicSetNickName)) {

					// �����ʵ�÷ֺ���ֵ
					Double limitScore = classicTextSet.getSetLimitScore();
					Double trulyScore = (Double) eachFileClassifyFirstResMap
							.get(classicSetNickName);
					// �ж��Ƿ���
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
