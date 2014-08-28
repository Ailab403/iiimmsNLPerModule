package ims.synWords.service;

import ims.synWords.model.SynWords;

import java.util.List;

public interface SynWordsService {

	public List<SynWords> listAll();            // չʾ����ͬ���
	 
	public String add(SynWords synWords);       // ���ͬ���

	public String deleteById(int synWordId);    // ɾ��ͬ���

	public String update(SynWords synWords);    // �༭ͬ���

	public SynWords loadById(int synWordId);    // ����ͬ���
}
