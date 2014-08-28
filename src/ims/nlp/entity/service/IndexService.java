package ims.nlp.entity.service;

import ims.nlp.entity.model.Index;

import java.util.List;

public interface IndexService {

	public void add(Index index);

	public void deleteById(int indexId);

	public void deleteBySetId(int setId);

	public void update(Index index);

	public Index loadById(int indexId);

	public Index loadBySetId(int setId);

	public List<Index> listAll();
	
	public List<Index> listAllIdNeg(int indexId);

	public Index loadByIndexName(String indexName);
}
