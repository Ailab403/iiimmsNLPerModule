package test.nlp.classifier.util;

import ims.nlp.cache.WekaClassifierDataPath;
import ims.nlp.classifier.util.ScanAllFileInTrainFolder;

import org.junit.Test;

public class ScanAllFileInTrainFolderTest {

	@Test
	public void testExecCheck() {
		ScanAllFileInTrainFolder scanAllFileInTrainFolder = new ScanAllFileInTrainFolder();
		int checkRes = scanAllFileInTrainFolder
				.execCheck(WekaClassifierDataPath.TRAIN_SEGMENT_SOURCEDIR);

		System.out.println("checkRes01:" + checkRes);

		scanAllFileInTrainFolder.setCheckflag(false);
		checkRes = scanAllFileInTrainFolder
				.execCheck(WekaClassifierDataPath.TRAIN_SEGMENT_SOURCEDIR);

		System.out.println("checkRes02:" + checkRes);
	}
}
