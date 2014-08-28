package test.nlp.entity.service;

import ims.crawler.cache.ApplicationContextFactory;
import ims.nlp.entity.model.Index;
import ims.nlp.entity.service.IndexService;

import java.util.List;

import org.junit.Test;

public class IndexServiceTest {

	private static IndexService indexService = (IndexService) ApplicationContextFactory.appContext
			.getBean("indexService");

	@Test
	public void testIndexService() {
		List<Index> indexs = indexService.listAll();

		for (Index index : indexs) {
			System.out.println(index.toString());
		}
	}
}
