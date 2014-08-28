package test.nlp.classifier;

import ims.crawler.cache.ApplicationContextFactory;
import ims.nlp.classifier.weka.WekaClassifierModelManagment;

import java.util.Scanner;

import org.junit.Test;

public class WekaClassifierModelManagmentTest {

	@Test
	public void testSwitchDataArffKind() {
		int modelId = new Scanner(System.in).nextInt();

		WekaClassifierModelManagment wekaClassifierModelManagment = (WekaClassifierModelManagment) ApplicationContextFactory.appContext
				.getBean("wekaClassifierModelManagment");
		wekaClassifierModelManagment.switchDataArffKind(modelId);
	}
}
