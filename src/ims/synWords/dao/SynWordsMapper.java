package ims.synWords.dao;

import ims.synWords.model.SynWords;

import java.util.List;
import java.util.Set;
/**
 * 
 * @author WQS
 *
 */

public interface SynWordsMapper {
	
	List<SynWords> listSynWordsAll();           // 展示所有同义词
	 
	void addSynWords(SynWords synWords);       // 添加同义词

	void deleteSynWordsById(int synWordId);    // 删除同义词

	void updateSynWords(SynWords synWords);    // 编辑同义词

	SynWords loadSynWordsById(int synWordId);  // 查找同义词

}
