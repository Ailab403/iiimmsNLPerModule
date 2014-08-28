package ims.subjectTree.service;


import ims.subjectTree.model.IllegalWords;

import java.util.List;

public interface IllegalWordsService {
	// �����ҵ�ؼ���
	void addKeyWords(IllegalWords illegalWords);
	
	// ����illegalWordTreeId���عؼ���
	List<IllegalWords> findIdTreeWords(int illegalWordTreeId);

	// ɾ���ַ����еĿո�
	String del_space(String str);
	
	// ���ؼ����б�ӹ�(ȥ�ո�ȥ��)���γ�IllegalWords�б�
	List<IllegalWords> formIllegalWordsList(int subjectId , String[] keyList) throws Exception;

}
