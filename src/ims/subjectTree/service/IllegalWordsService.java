package ims.subjectTree.service;


import ims.subjectTree.model.IllegalWords;

import java.util.List;

public interface IllegalWordsService {
	// 添加行业关键词
	void addKeyWords(IllegalWords illegalWords);
	
	// 根据illegalWordTreeId返回关键词
	List<IllegalWords> findIdTreeWords(int illegalWordTreeId);

	// 删除字符串中的空格
	String del_space(String str);
	
	// 将关键词列表加工(去空格、去重)后形成IllegalWords列表
	List<IllegalWords> formIllegalWordsList(int subjectId , String[] keyList) throws Exception;

}
