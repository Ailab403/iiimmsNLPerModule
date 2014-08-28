package ims.subjectTree.service;

import ims.subjectTree.model.NetWords;

import java.util.List;

public interface NetWordsService {
	// �����ҵ�ؼ���
	void addNetWords(NetWords netWords);
	
	// ����netWordTreeId���عؼ���
	List<NetWords> findNetWordsByNetWordTreeId(int netWordTreeId);

	// ɾ���ַ����еĿո�
	String del_space(String str);
	
	// ���ؼ����б�ӹ�(ȥ�ո�ȥ��)���γ�NetWords�б�
	List<NetWords> formNetWordsList(int subjectId , String[] keyList) throws Exception;
}
