package test.nlp.entity.service;

import java.util.List;

import org.junit.Test;

import ims.crawler.cache.ApplicationContextFactory;
import ims.nlp.entity.model.ClassicTextSet;
import ims.nlp.entity.service.ClassicTextSetService;

public class ClassicTextSetServiceTest {

	private static ClassicTextSetService classicTextSetService = (ClassicTextSetService) ApplicationContextFactory.appContext
			.getBean("classicTextSetService");

	@Test
	public void testListAll() {
		List<ClassicTextSet> classicTextSets = classicTextSetService.listAll();

		for (ClassicTextSet classicTextSet : classicTextSets) {
			System.out.println(classicTextSet.toString());
		}
	}
}
