package ims.subjectTree.service;

import ims.subjectTree.model.StopWords;
import java.util.List;

public interface StopWordsService {
	
	List<StopWords> loadAllStopWords();
	// 添加行业关键词
	void addKeyWords(StopWords stopWords);
	
	// 删除指定行业的全部关键词
	boolean delKeyWordsByBackId(int stopWordTreeId);
	
	// 根据illegalWordTreeId返回关键词
	List<StopWords> findStopWordsByStopWordTreeId(int stopWordTreeId);

	// 删除字符串中的空格
	String del_space(String str);
	
	// 将关键词列表加工(去空格、去重)后形成IllegalWords列表
	List<StopWords> formIllegalWordsList(int subjectId , String[] keyList) throws Exception;
}
