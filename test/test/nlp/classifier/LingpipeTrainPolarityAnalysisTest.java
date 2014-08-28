package test.nlp.classifier;

import ims.nlp.cache.LingpipeAnalysisDataPath;
import ims.nlp.classifier.lingpipe.LingpipeTrainPolarityAnalysis;

import java.util.Scanner;

public class LingpipeTrainPolarityAnalysisTest {

	public static void main(String[] args) {
		int nGram = new Scanner(System.in).nextInt();

		LingpipeTrainPolarityAnalysis lingpipeTrainPolarityAnalysis = new LingpipeTrainPolarityAnalysis(
				LingpipeAnalysisDataPath.LINGPIPE_POLARITYTRAIN_DIR,
				LingpipeAnalysisDataPath.LINGPIPE_POLARITYCLASSIFIER_MODEL_PATH,
				nGram);
		
		lingpipeTrainPolarityAnalysis.trainPolarityAnalysisModel();
	}
}
