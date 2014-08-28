package ims.subjectTree.service;

import ims.subjectTree.model.StopWords;
import java.util.List;

public interface StopWordsService {
	
	List<StopWords> loadAllStopWords();
	// �����ҵ�ؼ���
	void addKeyWords(StopWords stopWords);
	
	// ɾ��ָ����ҵ��ȫ���ؼ���
	boolean delKeyWordsByBackId(int stopWordTreeId);
	
	// ����illegalWordTreeId���عؼ���
	List<StopWords> findStopWordsByStopWordTreeId(int stopWordTreeId);

	// ɾ���ַ����еĿո�
	String del_space(String str);
	
	// ���ؼ����б�ӹ�(ȥ�ո�ȥ��)���γ�IllegalWords�б�
	List<StopWords> formIllegalWordsList(int subjectId , String[] keyList) throws Exception;
}
