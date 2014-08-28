package ims.subjectTree.dao;


import ims.subjectTree.model.StopWords;

import java.util.List;

public interface StopWordsMapper {
	// ����StopWords������ֵ
	List<StopWords> loadAllStopWords();

	// ɾ��ָ����̨��ҵ�ڵ��µ����йؼ���
	void delStopWordsByStopWordsId(int backNodeId);
	
	// �����ҵ�ؼ���
	void insertStopWords(StopWords sw);

	// ����ָ����ҵ�����йؼ���
	List<StopWords> loadStopWordsByStopWordTreeId(int stopWordTreeId);

}
