package ims.subjectTree.service;

import ims.subjectTree.model.NetWords;

import java.util.List;

public interface NetWordsService {
	// 添加行业关键词
	void addNetWords(NetWords netWords);
	
	// 根据netWordTreeId返回关键词
	List<NetWords> findNetWordsByNetWordTreeId(int netWordTreeId);

	// 删除字符串中的空格
	String del_space(String str);
	
	// 将关键词列表加工(去空格、去重)后形成NetWords列表
	List<NetWords> formNetWordsList(int subjectId , String[] keyList) throws Exception;
}
