package ims.subjectTree.dao;


import ims.subjectTree.model.IllegalWords;

import java.util.List;

public interface IllegalWordsMapper {
//	----------------------- �������� --------------------------
	// ����IllegalWords������ֵ
	//List<IllegalWords> loadAllIllegalWords();

	// ɾ��ָ����̨��ҵ�ڵ��µ����йؼ���
	void delIllegalWordsByIllegalWordsId(int IllegalWordsId);
	
	// �����ҵ�ؼ���
	void insertIllegalWords(IllegalWords iw);

	// ����ָ����ҵ�����йؼ���
	List<IllegalWords> loadIllegalWordsByIllegalWordTreeId(int illegalWordTreeId);
	
}
