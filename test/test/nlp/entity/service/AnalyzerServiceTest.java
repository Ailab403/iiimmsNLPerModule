package test.nlp.entity.service;

import ims.crawler.cache.ApplicationContextFactory;
import ims.nlp.entity.model.Analyzer;
import ims.nlp.entity.service.AnalyzerService;

import java.util.List;

import org.junit.Test;

public class AnalyzerServiceTest {

	private static AnalyzerService analyzerService = (AnalyzerService) ApplicationContextFactory.appContext
			.getBean("analyzerService");

	@Test
	public void testListAll() {
		List<Analyzer> analyzers = analyzerService.listAll();

		for (Analyzer analyzer : analyzers) {
			System.out.println(analyzer.toString());
		}
	}
}
