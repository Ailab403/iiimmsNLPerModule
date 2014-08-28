package ims.subjectTree.dao;

import ims.subjectTree.model.NetWords;

import java.util.List;

public interface NetWordsMapper {
	// ����NetWords������ֵ
	 List<NetWords> loadAllNetWords();

	// ɾ��ָ����̨��ҵ�ڵ��µ����йؼ���
	void delNetWordsByNetWordTreeId(int netWordTreeId);
	
	// �����ҵ�ؼ���
	void insertNetWords(NetWords nw);

	// ����ָ����ҵ�����йؼ���
	List<NetWords> loadNetWordsByNetWordTreeId(int netWordTreeId);

}
