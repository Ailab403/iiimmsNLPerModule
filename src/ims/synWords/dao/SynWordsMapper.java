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
	
	List<SynWords> listSynWordsAll();           // չʾ����ͬ���
	 
	void addSynWords(SynWords synWords);       // ���ͬ���

	void deleteSynWordsById(int synWordId);    // ɾ��ͬ���

	void updateSynWords(SynWords synWords);    // �༭ͬ���

	SynWords loadSynWordsById(int synWordId);  // ����ͬ���

}
