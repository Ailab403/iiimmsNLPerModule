package ims.subjectTree.dao;


import ims.subjectTree.model.StopWords;

import java.util.List;

public interface StopWordsMapper {
	// 返回StopWords中所有值
	List<StopWords> loadAllStopWords();

	// 删除指定后台行业节点下的所有关键字
	void delStopWordsByStopWordsId(int backNodeId);
	
	// 添加行业关键词
	void insertStopWords(StopWords sw);

	// 返回指定行业的所有关键词
	List<StopWords> loadStopWordsByStopWordTreeId(int stopWordTreeId);

}
