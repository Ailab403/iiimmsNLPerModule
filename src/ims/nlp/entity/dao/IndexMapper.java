package ims.nlp.entity.dao;

import java.util.List;

import ims.nlp.entity.model.Index;

public interface IndexMapper {

	void add(Index index);

	void deleteById(int indexId);

	void deleteBySetId(int setId);

	void update(Index index);

	Index loadById(int indexId);

	Index loadBySetId(int setId);

	List<Index> listAll();
	
	List<Index> listAllIdNeg(int indexId);
	
	Index loadByIndexName(String indexName);
}
