package ims.nlp.classifier.weka;

import ims.nlp.classifier.util.AnalyzeSegmentSourceFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weka.classifiers.Classifier;
import weka.core.Instances;

public class WekaClassifyingInstances {

	/**
	 * 创建arff文件
	 */
	public void createArffSource(String segmentSourceDir,
			String segmentTargetDir, String dataRawPath, String dataFilterPath) {
		// 对源文件进行分词并存储在本地磁盘
		AnalyzeSegmentSourceFile segmentSourceFile = new AnalyzeSegmentSourceFile(
				segmentSourceDir, segmentTargetDir);
		segmentSourceFile.analyzeFileSegment();

		// 根据中文分词处理后的源文件创建arff文件
		WekaProduceArffFile produceArffFile = new WekaProduceArffFile();
		produceArffFile.produceArffFile(segmentTargetDir, dataRawPath,
				dataFilterPath);
	}

	/**
	 * 在有arff文件的情况下进行分类
	 */
	public List<Map<String, Object>> classifyingInstancesWithArff(
			String unlabeledDataArffPath, String labeledDataArffPath,
			String modelPath) {

		// 准备要返回的结果
		List<Map<String, Object>> textClassifyResList = new ArrayList<Map<String, Object>>();

		try {
			// 载入未分类数据的arff
			Instances unlabeled = new Instances(new BufferedReader(
					new FileReader(unlabeledDataArffPath)));
			// 载入训练数据arff
			Instances labeled = new Instances(new BufferedReader(
					new FileReader(labeledDataArffPath)));
			// 设置未分类数据arff的数据标签
			unlabeled.setClassIndex(0);
			// 设置分类数据arff的数据标签
			labeled.setClassIndex(0);

			// 载入分类模型实体
			Classifier classifier = WekaSaveLoadingClassifierModel
					.deserializingModel(modelPath);

			// TODO delete print
			System.out.println("分类模型信息：" + classifier);
			System.out.println("未标记实体数：" + unlabeled.numInstances());
			System.out.println("分类主题数：" + labeled.numClasses());

			// 得出并输出分类结果
			for (int i = 0; i < unlabeled.numInstances(); i++) {

				// 准备单个文档的分类结果
				Map<String, Object> textClassifyResMap = new HashMap<String, Object>();

				System.out.println("测试文档：" + i);

				// 加入文件的排号
				textClassifyResMap.put("fileNo", i);

				// 得出最优的的分类结果
				double clsLabel = classifier.classifyInstance(unlabeled
						.instance(i));
				// 得出所有类别的分类分布
				double distLabel[] = classifier
						.distributionForInstance(unlabeled.instance(i));

				System.out.println("分类结果：" + clsLabel + "->"
						+ labeled.classAttribute().value((int) clsLabel) + "："
						+ distLabel[(int) clsLabel]);
				System.out.println("分类细节：");
				for (int j = 0; j < distLabel.length; j++) {
					System.out.println(labeled.classAttribute().value(j) + "："
							+ distLabel[j]);

					// 加入文档对于每个类别的评分
					textClassifyResMap.put(labeled.classAttribute().value(j),
							distLabel[j]);
				}
				System.out.println("\n");

				// 将每个文档的分类结果加入总的结果集
				textClassifyResList.add(textClassifyResMap);
			}

			return textClassifyResList;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 在没有arff文件的情况下进行分类
	 */
	public List<Map<String, Object>> classifyingInstancesWithoutArff(
			String segmentSourceDir, String segmentTargetDir,
			String dataRawPath, String dataFilterPath,
			String unlabeledDataArffPath, String labeledDataArffPath,
			String modelPath) {

		this.createArffSource(segmentSourceDir, segmentTargetDir, dataRawPath,
				dataFilterPath);

		return this.classifyingInstancesWithArff(unlabeledDataArffPath,
				labeledDataArffPath, modelPath);
	}

}
