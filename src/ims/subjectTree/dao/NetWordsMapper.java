package ims.subjectTree.dao;

import ims.subjectTree.model.NetWords;

import java.util.List;

public interface NetWordsMapper {
	// 返回NetWords中所有值
	 List<NetWords> loadAllNetWords();

	// 删除指定后台行业节点下的所有关键字
	void delNetWordsByNetWordTreeId(int netWordTreeId);
	
	// 添加行业关键词
	void insertNetWords(NetWords nw);

	// 返回指定行业的所有关键词
	List<NetWords> loadNetWordsByNetWordTreeId(int netWordTreeId);

}
