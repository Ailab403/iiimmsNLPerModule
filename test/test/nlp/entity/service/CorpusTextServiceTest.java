package test.nlp.entity.service;

import ims.crawler.cache.ApplicationContextFactory;
import ims.nlp.entity.model.CorpusText;
import ims.nlp.entity.service.CorpusTextService;

import java.util.Set;

import org.junit.Test;

public class CorpusTextServiceTest {

	private static CorpusTextService corpusTextService = (CorpusTextService) ApplicationContextFactory.appContext
			.getBean("corpusTextService");

	@Test
	public void testListAll() {
		Set<CorpusText> corpusTexts = corpusTextService.listAll();

		for (CorpusText corpusText : corpusTexts) {
			System.out.println(corpusText.toString());
		}
	}
}
