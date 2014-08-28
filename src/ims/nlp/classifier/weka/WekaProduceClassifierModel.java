package ims.nlp.classifier.weka;

import ims.nlp.classifier.util.AnalyzeSegmentSourceFile;

/**
 * 
 * @author superhy
 * 
 */
public class WekaProduceClassifierModel {

	/**
	 * 创建arff文件，传入参数（1.分词源文件夹，2.分词目标文件夹，3.流式arff目标路径，4.过滤器arff目标路径）
	 * 
	 * @param segmentSourceDir
	 * @param segmentTargetDir
	 * @param dataRawPath
	 * @param dataFilterPath
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
	 * 在有arff文件的情况下训练分类模型，传入参数（1.训练arff源数据，2.模型实现类，3.模型存储路径，4.模型额外参数）
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
	 * 在没有arff文件的情况下训练分类模型
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
