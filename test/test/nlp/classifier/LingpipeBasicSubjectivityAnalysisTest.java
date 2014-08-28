package test.nlp.classifier;

import ims.nlp.cache.LingpipeAnalysisDataPath;
import ims.nlp.classifier.lingpipe.LingpipeBasicSubjectivityAnalysis;

public class LingpipeBasicSubjectivityAnalysisTest {

	public static void main(String[] args) throws Exception {
		LingpipeBasicSubjectivityAnalysis lingpipeBasicSubjectivityAnalysis = new LingpipeBasicSubjectivityAnalysis(
				LingpipeAnalysisDataPath.LINGPIPE_SUBJECTIVITY_DIR);

		lingpipeBasicSubjectivityAnalysis.train();
		// lingpipeBasicSubjectivityAnalysis.evaluate();
		lingpipeBasicSubjectivityAnalysis.test();
	}
}
