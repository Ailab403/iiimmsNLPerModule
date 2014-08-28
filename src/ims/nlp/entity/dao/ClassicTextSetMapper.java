package ims.nlp.entity.dao;

import java.util.List;

import ims.nlp.entity.model.ClassicTextSet;

public interface ClassicTextSetMapper {

	void add(ClassicTextSet classicTextSet);

	void deleteById(int setId);

	void update(ClassicTextSet classicTextSet);

	ClassicTextSet loadById(int setId);

	ClassicTextSet loadByNickName(String nickName);

	List<ClassicTextSet> listAll();

	// 列出除了总类的所有记录
	List<ClassicTextSet> listAllSetNeg();
}
