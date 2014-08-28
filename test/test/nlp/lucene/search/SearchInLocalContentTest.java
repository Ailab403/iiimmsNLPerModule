package test.nlp.lucene.search;

import ims.nlp.cache.IndexDirectoryLocPath;
import ims.nlp.lucene.analyzer.AnalyzerFactory;
import ims.nlp.lucene.search.SearchInLocalContent;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.lucene.analysis.Analyzer;
import org.junit.Test;

public class SearchInLocalContentTest {

	@Test
	public void testPhraseQuerySearcher() {
		SearchInLocalContent searchInLocalContent = new SearchInLocalContent(
				IndexDirectoryLocPath.LUCENE_ALL_INDEX);

		String keyValue = new Scanner(System.in).next();

		// µÃµ½·Ö´ÊÆ÷
		AnalyzerFactory.setAnalyzerName("mmseg4j");
		Analyzer analyzer = AnalyzerFactory.produceDiyAnalyzer(null);

		List<Map<String, Object>> resMaps = searchInLocalContent
				.phraseQuerySearcher(keyValue, 1000, analyzer);

		System.out.println(resMaps.size());
		for (Map<String, Object> map : resMaps) {
			System.out.println(map.toString());
		}
	}
}
