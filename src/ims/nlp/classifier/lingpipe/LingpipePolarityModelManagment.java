package ims.nlp.classifier.lingpipe;

import ims.nlp.cache.LingpipeAnalysisDataPath;

public class LingpipePolarityModelManagment {

	public static Boolean buildRunFlag = false;

	/**
	 * �½����Է���ģ��
	 * 
	 * @param nGram
	 * @return
	 */
	public Boolean reBuildPolarityModel(Integer nGram) {
		try {
			buildRunFlag = true;
			// Ĭ������Ȩ��Ϊ5��
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
