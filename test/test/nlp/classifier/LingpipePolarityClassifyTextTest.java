package test.nlp.classifier;

import ims.nlp.cache.LingpipeAnalysisDataPath;
import ims.nlp.classifier.lingpipe.LingpipePolarityClassifyText;

public class LingpipePolarityClassifyTextTest {

	public static void main(String[] args) {
		LingpipePolarityClassifyText lingpipePolarityClassifyText = new LingpipePolarityClassifyText(
				LingpipeAnalysisDataPath.LINGPIPE_POLARITYCLASSIFIER_MODEL_PATH,
				LingpipeAnalysisDataPath.LINGPIPE_POLARITYTEST_FOLDER);

		lingpipePolarityClassifyText.polarityClassifyText();
	}
}
