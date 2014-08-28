package ims.nlp.lucene.analyzer.util;

import ims.crawler.cache.ApplicationContextFactory;
import ims.synWords.model.SynWords;
import ims.synWords.service.SynWordsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadSynWordContext implements SynWordContext {

	// 同义词映射集合
	private Map<String, String[]> maps = new HashMap<String, String[]>();
	// 同义词表服务实体
	private SynWordsService synWordsService = (SynWordsService) ApplicationContextFactory.appContext
			.getBean("synWordsService");

	public LoadSynWordContext() {
		List<SynWords> synWordsList = this.synWordsService.listAll();
		for (SynWords synWords : synWordsList) {
			// 向同义词映射集合中添加同义词映射对
			maps.put(synWords.getMainWordCnt(), synWords.getSynWordStr().split(
					";"));
		}
	}

	public String[] getSamewords(String name) {
		return this.maps.get(name);
	}

}
