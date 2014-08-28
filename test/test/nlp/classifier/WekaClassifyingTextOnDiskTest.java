package test.nlp.classifier;

import ims.crawler.cache.ApplicationContextFactory;
import ims.nlp.classifier.weka.WekaClassifyingTextOnDisk;

import java.util.List;

import org.junit.Test;

public class WekaClassifyingTextOnDiskTest {

	WekaClassifyingTextOnDisk wekaClassifyingTextOnDisk = (WekaClassifyingTextOnDisk) ApplicationContextFactory.appContext
			.getBean("wekaClassifyingTextOnDisk");

	@Test
	public void testGetClassicSetName() {
		List<String> setNames = wekaClassifyingTextOnDisk.getClassicSetName();

		for (String string : setNames) {
			System.out.println(string);
		}
	}
	
	@Test
	public void testGetTestTextInfo() {
		List<String> textNames = wekaClassifyingTextOnDisk.getTestTextInfo();
		
		for (String string : textNames) {
			System.out.println(string);
		}
	}
}
