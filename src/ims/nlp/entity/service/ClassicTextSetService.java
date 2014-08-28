package ims.nlp.entity.service;

import ims.nlp.entity.model.ClassicTextSet;

import java.util.List;

public interface ClassicTextSetService {

	public void add(ClassicTextSet classicTextSet);

	public void deleteById(int setId);

	public void update(ClassicTextSet classicTextSet);

	public ClassicTextSet loadById(int setId);

	public ClassicTextSet loadByNickName(String nickName);

	public List<ClassicTextSet> listAll();

	// �г�������������м�¼
	List<ClassicTextSet> listAllSetNeg();

	// �γ�classicTextSet����� dd
	public String formClassicTextSetTree();

	public String readFileInfo(String filePath);

}
