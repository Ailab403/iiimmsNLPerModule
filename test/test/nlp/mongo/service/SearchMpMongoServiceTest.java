package test.nlp.mongo.service;

import ims.crawler.cache.ApplicationContextFactory;
import ims.nlp.mongo.service.SearchMpMongoService;

import org.junit.Test;

public class SearchMpMongoServiceTest {

	@Test
	public void testUpdate() {
		SearchMpMongoService searchMpMongoService = (SearchMpMongoService) ApplicationContextFactory.appContext
				.getBean("searchMpMongoService");

		searchMpMongoService.updateSearchMap("test123456", "test",
				"testCollection");
	}
}
