package ims.subjectTree.dao;


import ims.subjectTree.model.IllegalWords;

import java.util.List;

public interface IllegalWordsMapper {
//	----------------------- 公共方法 --------------------------
	// 返回IllegalWords中所有值
	//List<IllegalWords> loadAllIllegalWords();

	// 删除指定后台行业节点下的所有关键字
	void delIllegalWordsByIllegalWordsId(int IllegalWordsId);
	
	// 添加行业关键词
	void insertIllegalWords(IllegalWords iw);

	// 返回指定行业的所有关键词
	List<IllegalWords> loadIllegalWordsByIllegalWordTreeId(int illegalWordTreeId);
	
}
