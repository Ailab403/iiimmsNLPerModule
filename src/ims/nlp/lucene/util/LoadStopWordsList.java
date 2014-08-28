package ims.nlp.lucene.util;

import java.util.ArrayList;
import java.util.List;

import ims.crawler.cache.ApplicationContextFactory;
import ims.subjectTree.model.StopWords;
import ims.subjectTree.service.StopWordsService;

public class LoadStopWordsList {

	private static StopWordsService stopWordsService = (StopWordsService) ApplicationContextFactory.appContext
			.getBean("stopWordsService");

	/**
	 * 加载停用词队列
	 * 
	 * @return
	 */
	public static List<String> getStopWordsList() {
		List<String> stopWordList = new ArrayList<String>();

		List<StopWords> stopWords = stopWordsService.loadAllStopWords();
		for (StopWords stopWord : stopWords) {
			stopWordList.add(stopWord.getStopWordCnt());
		}

		return stopWordList;
	}
}
