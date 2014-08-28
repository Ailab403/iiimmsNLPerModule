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
	 * ����arff�ļ�
	 */
	public void createArffSource(String segmentSourceDir,
			String segmentTargetDir, String dataRawPath, String dataFilterPath) {
		// ��Դ�ļ����зִʲ��洢�ڱ��ش���
		AnalyzeSegmentSourceFile segmentSourceFile = new AnalyzeSegmentSourceFile(
				segmentSourceDir, segmentTargetDir);
		segmentSourceFile.analyzeFileSegment();

		// �������ķִʴ�����Դ�ļ�����arff�ļ�
		WekaProduceArffFile produceArffFile = new WekaProduceArffFile();
		produceArffFile.produceArffFile(segmentTargetDir, dataRawPath,
				dataFilterPath);
	}

	/**
	 * ����arff�ļ�������½��з���
	 */
	public List<Map<String, Object>> classifyingInstancesWithArff(
			String unlabeledDataArffPath, String labeledDataArffPath,
			String modelPath) {

		// ׼��Ҫ���صĽ��
		List<Map<String, Object>> textClassifyResList = new ArrayList<Map<String, Object>>();

		try {
			// ����δ�������ݵ�arff
			Instances unlabeled = new Instances(new BufferedReader(
					new FileReader(unlabeledDataArffPath)));
			// ����ѵ������arff
			Instances labeled = new Instances(new BufferedReader(
					new FileReader(labeledDataArffPath)));
			// ����δ��������arff�����ݱ�ǩ
			unlabeled.setClassIndex(0);
			// ���÷�������arff�����ݱ�ǩ
			labeled.setClassIndex(0);

			// �������ģ��ʵ��
			Classifier classifier = WekaSaveLoadingClassifierModel
					.deserializingModel(modelPath);

			// TODO delete print
			System.out.println("����ģ����Ϣ��" + classifier);
			System.out.println("δ���ʵ������" + unlabeled.numInstances());
			System.out.println("������������" + labeled.numClasses());

			// �ó������������
			for (int i = 0; i < unlabeled.numInstances(); i++) {

				// ׼�������ĵ��ķ�����
				Map<String, Object> textClassifyResMap = new HashMap<String, Object>();

				System.out.println("�����ĵ���" + i);

				// �����ļ����ź�
				textClassifyResMap.put("fileNo", i);

				// �ó����ŵĵķ�����
				double clsLabel = classifier.classifyInstance(unlabeled
						.instance(i));
				// �ó��������ķ���ֲ�
				double distLabel[] = classifier
						.distributionForInstance(unlabeled.instance(i));

				System.out.println("��������" + clsLabel + "->"
						+ labeled.classAttribute().value((int) clsLabel) + "��"
						+ distLabel[(int) clsLabel]);
				System.out.println("����ϸ�ڣ�");
				for (int j = 0; j < distLabel.length; j++) {
					System.out.println(labeled.classAttribute().value(j) + "��"
							+ distLabel[j]);

					// �����ĵ�����ÿ����������
					textClassifyResMap.put(labeled.classAttribute().value(j),
							distLabel[j]);
				}
				System.out.println("\n");

				// ��ÿ���ĵ��ķ����������ܵĽ����
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
	 * ��û��arff�ļ�������½��з���
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
