package ims.nlp.classifier.weka;

import ims.nlp.classifier.util.AnalyzeSegmentSourceFile;

/**
 * 
 * @author superhy
 * 
 */
public class WekaProduceClassifierModel {

	/**
	 * ����arff�ļ������������1.�ִ�Դ�ļ��У�2.�ִ�Ŀ���ļ��У�3.��ʽarffĿ��·����4.������arffĿ��·����
	 * 
	 * @param segmentSourceDir
	 * @param segmentTargetDir
	 * @param dataRawPath
	 * @param dataFilterPath
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
	 * ����arff�ļ��������ѵ������ģ�ͣ����������1.ѵ��arffԴ���ݣ�2.ģ��ʵ���࣬3.ģ�ʹ洢·����4.ģ�Ͷ��������
	 * 
	 * @param trainDataArffPath
	 * @param modelObj
	 * @param modelPath
	 * @param modelParame
	 */
	public void trainClassifierModelWithArff(String trainDataArffPath,
			String modelObj, String modelPath, String modelParame) {

		WekaSaveLoadingClassifierModel.sericalizingModel(trainDataArffPath,
				modelObj, modelPath, modelParame);
	}

	/**
	 * ��û��arff�ļ��������ѵ������ģ��
	 * 
	 * @param segmentSourceDir
	 * @param segmentTargetDir
	 * @param dataRawPath
	 * @param dataFilterPath
	 * @param trainDataArffPath
	 * @param modelObj
	 * @param modelPath
	 * @param modelParame
	 */
	public void trainClassifierModelWithoutArff(String segmentSourceDir,
			String segmentTargetDir, String dataRawPath, String dataFilterPath,
			String trainDataArffPath, String modelObj, String modelPath,
			String modelParame) {

		this.createArffSource(segmentSourceDir, segmentTargetDir, dataRawPath,
				dataFilterPath);

		WekaSaveLoadingClassifierModel.sericalizingModel(trainDataArffPath,
				modelObj, modelPath, modelParame);
	}

}
