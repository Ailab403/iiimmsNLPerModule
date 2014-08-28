package ims.nlp.classifier.lingpipe;

import ims.nlp.cache.LingpipeAnalysisDataPath;

public class LingpipePolarityModelManagment {

	public static Boolean buildRunFlag = false;

	/**
	 * 新建极性分类模型
	 * 
	 * @param nGram
	 * @return
	 */
	public Boolean reBuildPolarityModel(Integer nGram) {
		try {
			buildRunFlag = true;
			// 默认设置权重为5克
			if (nGram == null || nGram == 0) {
				nGram = 1;
			}
			LingpipeTrainPolarityAnalysis lingpipeTrainPolarityAnalysis = new LingpipeTrainPolarityAnalysis(
					LingpipeAnalysisDataPath.LINGPIPE_POLARITYTRAIN_DIR,
					LingpipeAnalysisDataPath.LINGPIPE_POLARITYCLASSIFIER_MODEL_PATH,
					nGram);
			lingpipeTrainPolarityAnalysis.trainPolarityAnalysisModel();
			buildRunFlag = false;

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return false;
		}
	}
}
