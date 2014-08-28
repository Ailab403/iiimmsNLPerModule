package test.nlp.classifier;

import ims.nlp.cache.LingpipeAnalysisDataPath;
import ims.nlp.classifier.lingpipe.LingpipeBasicPolarityAnalysis;

@SuppressWarnings("deprecation")
public class LingpipeBasicPolarityAnalysisTest {

	public static void main(String[] args) {
		LingpipeBasicPolarityAnalysis lingpipeBasicPolarityAnalysis = new LingpipeBasicPolarityAnalysis(
				LingpipeAnalysisDataPath.LINGPIPE_POLARITYTRAIN_DIR);
		
		lingpipeBasicPolarityAnalysis.train();
		lingpipeBasicPolarityAnalysis.evaluate();
	}
}
